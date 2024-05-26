package uvg.edu.gt;

import java.util.ArrayList;

/**
 * Este programa es un sistema de recomendacion de juegos de Steam, se conecta a una base de datos basada en grafos
 * en Neo4j para realizar las recomendaciones y guardar informacion de los usuarios.
 * @author Jose Merida
 * @author Adrian Lopez
 * @since 25-05-2024
 * @version 1.0
 */
public class App
{
    public static void main( String[] args )
    {
        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(model, view);
    }
}