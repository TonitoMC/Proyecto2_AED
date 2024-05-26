package uvg.edu.gt;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.neo4j.driver.Values.parameters;


import org.mindrot.jbcrypt.BCrypt;

public class Model {
    String uri = "neo4j+s://040a63b9.databases.neo4j.io";
    String user = "neo4j";
    String password = "mwY1aRjtaKL9XzH3Pp6QJIDfGf-VvetrkEzZGCX-5y8";
    ArrayList<String> gameTitles;
    private Driver driver;
    /**
     * Constructor para la clase Model, inicializa la conexion a la base de datos.
     */
    public Model(){
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
            System.out.println("Conexion a la base de datos exitosa");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutdown hook triggered. Closing Neo4j driver...");
                if (driver != null) {
                    driver.close();
                    System.out.println("Neo4j driver closed.");
                }
            }));
        } catch (Exception e) {
            System.err.println("Error conectandose a la base de datos");
            e.printStackTrace();
        }

        gameTitles = updateGameTitles();
    }
    public ArrayList<String> updateGameTitles(){
        ArrayList<String> titles = new ArrayList<String>();
        try(Session session = driver.session()){
            String query = "MATCH (g:Game) RETURN g.title AS title";
            Result result = session.run(query);
            while (result.hasNext()){
                Record record = result.next();
                titles.add(record.get("title").asString());
            }
        }
        return titles;
    }

    /**
     * Este metodo se utiliza para obtener una lista de los juegos que le gustan al usuario
     * @param username el nombre del usuario
     * @return un ArrayList con el titulo de los juegos
     */
    public ArrayList<String> getLikedGames(String username){
        ArrayList<String> likedGames = new ArrayList<String>();
        try (Session session = driver.session()){
            String query = "MATCH (u:User {username: $username})-[:LIKES]->(g:Game) RETURN g.title AS title";
            Result result = session.run(query, parameters("username", username));
            while (result.hasNext()){
                Record record = result.next();
                likedGames.add(record.get("title").asString());
            }
        } catch (Exception e){
            System.err.println("Error obteniendo los juegos que le gustan al usuario");
            e.printStackTrace();
        }
        return likedGames;
    }

    /**
     * Este metodo se utiliza para facilitar la busqueda de juegos por parte del usuario, al contener los nombres
     * exactos de los juegos puede ser algo complicado  encontrar un juego en especifico.
     * @param input el nombre (o parcial) del titulo del juego
     * @return un ArrayList con titulos similares lexicograficamente
     */
    public ArrayList<String> gameSearch(String input){
        ArrayList<String> results = new ArrayList<String>();
        for (String game : gameTitles){
            if (game.toLowerCase().contains(input.toLowerCase()) || levenshteinDistance(game.toLowerCase(), input.toLowerCase()) <= 3){
                if (!results.contains(game)) {
                    results.add(game);
                }
            }
        }
        return results;
    }
    private int levenshteinDistance(String a, String b){
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++){
            for (int j = 0; j <= b.length(); j++){
                if (i == 0){
                    dp[i][j] = j;
                } else if (j == 0){
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(Math.min(
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1));
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    /**
     * Este metodo
     * @param username
     * @return
     */
    public ArrayList<String> getRecommendations(String username) {
        ArrayList<String> likedGameList = getLikedGames(username);
        ArrayList<String> recommendedGameList = new ArrayList<String>();
        HashMap<String, Double> affinityScoreMap = new HashMap<>();
        try (Session session = driver.session()) {
            String tagsQuery = "MATCH (u:User {username: $username})-[r:LIKES_TAG]->(t:Tag) " +
                    "RETURN t.name AS tag, r.Strength AS strength";
            Result tagsResult = session.run(tagsQuery, Values.parameters("username", username));

            while (tagsResult.hasNext()) {
                Record record = tagsResult.next();
                String tag = record.get("tag").asString();
                double strength = record.get("strength").asDouble();

                String gamesQuery = "MATCH (g:Game)-[:HAS_TAG]->(t:Tag {name: $tag}) " +
                        "RETURN g.title AS game";
                Result gamesResult = session.run(gamesQuery, Values.parameters("tag", tag));

                while (gamesResult.hasNext()) {
                    Record gameRecord = gamesResult.next();
                    String gameTitle = gameRecord.get("game").asString();

                    affinityScoreMap.put(gameTitle, affinityScoreMap.getOrDefault(gameTitle, 0.0) + strength);
                }
            }
            for (String likedGame : likedGameList){
                Set<String> keySet = affinityScoreMap.keySet();
                if (keySet.contains(likedGame)){
                    affinityScoreMap.remove(likedGame);
                }
            }
            List<Map.Entry<String, Double>> sortedEntries = affinityScoreMap.entrySet().stream()
                    .sorted((Map.Entry.<String, Double>comparingByValue().reversed()))
                    .collect(Collectors.toList());


            int count = 0;
            for (Map.Entry<String, Double> entry : sortedEntries) {
                if (count >= 10) break;
                recommendedGameList.add(entry.getKey());
                count++;
            }
            return recommendedGameList;
        } catch (Exception e) {
            System.err.println("Error retrieving recommendations for user: " + username + ". " + e.getMessage());
        }
        return recommendedGameList;
    }

    /**
     * Este metodo  actualiza la afinidad del usuario hacia ciertos tags
     * @param username
     * @return
     */
    public boolean setTagAffinity(String username){
        HashMap<String, Integer> frequencyMap = new HashMap<String, Integer>();
        HashMap<String, Double> relativeFrequencyMap = new HashMap<String, Double>();
        ArrayList<String> likedGames = getLikedGames(username);
        int totalTagCount = 0;
        try (Session session = driver.session()) {
            String delPrevQuery = "MATCH (:User {username: $username})-[r:LIKES_TAG]->() DELETE r";
            session.run(delPrevQuery, Values.parameters("username", username));
            for (String gameName : likedGames) {
                String getTagsQuery = "MATCH (g:Game {title: $gameName})-[:HAS_TAG]->(t:Tag) RETURN t.name AS tag";
                Result result = session.run(getTagsQuery, parameters("gameName", gameName));

                while (result.hasNext()){
                    Record record = result.next();
                    String tag = record.get("tag").asString();
                    frequencyMap.put(tag, frequencyMap.getOrDefault(tag, 0) + 1);
                    totalTagCount++;
                }
            }
            for (String key : frequencyMap.keySet()){
                Double relativeFrequency = (double) frequencyMap.get(key) / totalTagCount;
                relativeFrequencyMap.put(key, relativeFrequency);
            }
            for (String tagName : relativeFrequencyMap.keySet()){
                Double affinity = relativeFrequencyMap.get(tagName);
                try{
                    String relQuery = "MATCH (u: User {username: $username}), (t:Tag {name: $tag}) " +
                            "MERGE (u)-[r:LIKES_TAG]->(t) " +
                            "SET r.Strength = $affinity";
                    session.run(relQuery, parameters("username", username, "tag", tagName, "affinity", affinity));
                } catch (Exception e){
                    System.err.println("Error creando las asociaciones entre el usuario y los tags");
                }
            }
            return true;
        } catch (Exception e){
            System.err.println("Error actualizando la afinidad de los tags");
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Este metodo registra a un nuevo usuario en la base de datos, encripta su contrasena
     * @param username el nombre de usuario del usuario
     * @param passwordUnhashed la contrasena del usuario
     * @return true si la operacion fue exitosa, false de lo contrario
     */
    public boolean registerUser(String username, String passwordUnhashed) {
        //Encripta la contrasena del usuario para almacenar en la base de datos
        String password = BCrypt.hashpw(passwordUnhashed, BCrypt.gensalt());
        //Verifica que el nombre de usuario sea unico, retorna false si ya existe un usuario con ese nombre
        ArrayList<String> usernameList = getAllUsernames();
        for (int i = 0; i < usernameList.size(); i++) {
            if (username.equalsIgnoreCase(usernameList.get(i))) {
                return false;
            }
        }
        //Corre el query para registrar al usuario
        try (Session session = driver.session()) {
            String query = "CREATE (:User {username: $username, password: $password})";
            session.run(query, parameters("username", username, "password", password));
            System.out.println("Usuario registrado correctamente.");
            return true;
        } catch (Exception e){
            System.err.println("Error registrando usuario:");
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Este metodo se utiliza para validar el login del usuario
     * @param username el nombre de usuario
     * @param passwordUnhashed la contrasena del usuario
     * @return true si la validacion es exitosa, false de lo contrario
     */
    public boolean validateUser(String username, String passwordUnhashed){
        try (Session session = driver.session()){
            //Busca entre los nodos de usuario y retorna el password encriptado
            String query = "MATCH (u:User {username: $username}) RETURN u.password AS password";
            Result result = session.run(query, parameters("username", username));
            if (result.hasNext()){
                String storedPassword = result.next().get("password").asString();
                //Verifica con BCrypt si el password es correspondiente al almacenado (encriptado)
                return BCrypt.checkpw(passwordUnhashed, storedPassword);
            } else {
                return false;
            }
        } catch (Exception e){
            System.err.println("Error autenticando el usuario");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Este metodo se utiliza para agregar un juego favorito del usuario
     * @param username el nombre del usuario
     * @param gameName el nombre del juego
     * @return true si la operacion fue exitosa, false de lo contrario
     */
    public boolean addLikedGame(String username, String gameName){
        ArrayList<String> gamesList = getGamesList();
        for (int i = 0; i < gamesList.size(); i++){
            if (gamesList.get(i).equalsIgnoreCase(gameName)){
                return false;
            }
        }
        //Se puede verificar si al usuario ya le gusta el juego
        try (Session session = driver.session()){
            String checkQuery = "MATCH (u:User {username: $username})-[:LIKES]->(g:Game {title: $gameName}) RETURN g";
            Result checkResult = session.run(checkQuery, parameters("username", username, "gameName", gameName));
            if (checkResult.hasNext()){
                System.err.println("Al usuario ya le gusta el juego");
                return false;
            }
            //Crea la relacion entre el usuario y el juego
            String query = "MATCH (u:User {username: $username}), (g:Game {title: $gameName}) " +
                    "CREATE (u)-[:LIKES]->(g)";
            session.run(query, parameters("username", username, "gameName", gameName));
            return true;
        } catch (Exception e){
            System.err.println("Error agregando el juego");
            e.printStackTrace();
            return false;
        }
    }
    public boolean removeLikedGame(String username, String gameName){
        try (Session session = driver.session()){
            String query = "MATCH (u:User {username: $username})-[r:LIKES]->(g:Game {title: $gameName}) DELETE r";
            session.run(query, Values.parameters("username", username, "gameName", gameName));
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public ArrayList<String> getGamesList(){
        ArrayList<String> gameList = new ArrayList<String>();
        return gameList;
    }
    /**
     * Este metodo se utiliza para obtener todos los nombres de usuario de las personas registradas
     * @return ArrayList con los nombres de usuario
     */
    public ArrayList<String> getAllUsernames(){
        ArrayList<String> usernameList = new ArrayList<String>();
        try (Session session = driver.session()){
            String query = "MATCH (u:User) RETURN u.username AS username";
            Result result = session.run(query);

            while (result.hasNext()){
                Record record = result.next();
                String username = record.get("username").asString();
                usernameList.add(username);
            }
        } catch (Exception e){
            System.err.println("Error obteniendo los usuarios:");
            e.printStackTrace();
        }
        return usernameList;
    }
    public void disconnectDB(){
        if (driver != null){
            driver.close();
            System.out.println("Desconectado de la base de datos");
        }
    }
}

