package uvg.edu.gt;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import static org.neo4j.driver.Values.parameters;


import org.mindrot.jbcrypt.BCrypt;

public class Model {
    String uri = "neo4j+s://040a63b9.databases.neo4j.io";
    String user = "neo4j";
    String password = "mwY1aRjtaKL9XzH3Pp6QJIDfGf-VvetrkEzZGCX-5y8";
    private Driver driver;
    /**
     * Constructor para la clase Model, inicializa la conexion a la base de datos.
     */
    public Model(){
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
            System.out.println("Conexion a la base de datos exitosa");
        } catch (Exception e) {
            System.err.println("Error conectandose a la base de datos");
            e.printStackTrace();
        }
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

