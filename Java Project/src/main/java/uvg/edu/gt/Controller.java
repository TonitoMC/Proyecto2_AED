package uvg.edu.gt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Esta clase se encarga de manejar la comunicacion entre la clase con la que interactua el usuario "View" y la clase
 * que maneja el funcionamiento interno del programa "Model"
 * @author Jose Merida
 * @author Adrian Lopez
 * @since 25-05-2024
 * @version 1.0
 */

public class Controller implements ActionListener{
    private Model model;
    private View view;
    private String currentUser;

    /**
     * Constructor para la clase Controll9er
     * @param model el model a utilizar
     * @param view el view a utilizar
     */
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        view.addActionListener(this);
    }

    /**
     * Utiliza un actionlistener para obtener los eventos que suceden en View (Interacciones del usuario)
     * @param e el evento a procesar
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == view.getLoginButton()){
            if (model.validateUser(view.getUsernameInput(), view.getPasswordInput())) {
                currentUser = view.getUsernameInput();
                ArrayList<String> likedGames = model.getLikedGames(currentUser);
                for (String game : likedGames){
                    view.addFavorite(game);
                }
                view.goToMainPanel();
            } else{
                System.out.println("Error al ingresar, compruebe credencialies");
            }
        }
        if (e.getSource() == view.getRegisterButton()){
            if (model.registerUser(view.getUsernameInput(), view.getPasswordInput())){
                currentUser = view.getUsernameInput();
                ArrayList<String> likedGames = model.getLikedGames(currentUser);
                for (String game : likedGames){
                    view.addFavorite(game);
                }
                view.goToMainPanel();
            } else {
                System.out.println("Error al registrarse, pruebe con otro nombre de usuario");
            }
        }
        if (e.getSource() == view.getRecommendButton()){
            model.setTagAffinity(currentUser);
            view.clearRecommendations();
            ArrayList<String> recommendations = model.getRecommendations(currentUser, view.getFreeButton());
            for (String gameName : recommendations){
                if (!gameName.isEmpty()) {
                    view.addRecommendation(gameName);
                }
            }
        }
        if (e.getSource() == view.getFindGameButton()){
            System.out.println(view.getGameSelection());
            ArrayList<String> searchResults = model.gameSearch(view.getGameSearch());
            view.updateSearchOutput(searchResults);
        }
        if (e.getSource() == view.getFavoriteButton()){
            model.addLikedGame(currentUser, view.updateFavorites());
        }
        if (e.getSource() == view.getRemoveButton()){
            model.removeLikedGame(currentUser, view.getFavoriteSelection());
            view.removeFromFavorites();
        }
    }
}
