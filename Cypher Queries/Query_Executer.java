public class App
{
    public static void main( String[] args )
    {
        String uri = "neo4j+s://040a63b9.databases.neo4j.io";
        String user = "neo4j";
        String password = "mwY1aRjtaKL9XzH3Pp6QJIDfGf-VvetrkEzZGCX-5y8";

        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))) {
            String filePath = "cypher_queries.txt";
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try (Session session = driver.session()) {
                        session.run(line);
                        System.out.println("Executed query: " + line);
                    } catch (Exception e) {
                        System.err.println("Error executing query: " + line);
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + filePath);
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error connecting to Neo4j database");
            e.printStackTrace();
        }
    }
}
