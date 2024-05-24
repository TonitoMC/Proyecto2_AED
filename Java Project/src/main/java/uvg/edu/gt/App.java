package uvg.edu.gt;

import java.util.ArrayList;

public class App
{
    public static void main( String[] args )
    {
        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(model, view);
    }
}