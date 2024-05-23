package uvg.edu.gt;

import java.util.ArrayList;

public class App
{
    public static void main( String[] args )
    {
        Model model = new Model();
        ArrayList<String> gameList = model.getLikedGames("Prueba1");
        for (String s : gameList){
            System.out.println(s);
        }
        model.disconnectDB();
    }
}
