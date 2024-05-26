package uvg.edu.gt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller implements ActionListener{
    private Model model;
    private View view;
    private String currentUser;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        view.addActionListener(this);
        System.out.println(model.getLikedGames("Prueba1"));
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == view.getLoginButton()){
            if (model.validateUser(view.getUsernameInput(), view.getPasswordInput())) {
                currentUser = view.getUsernameInput();
                ArrayList<String> likedGames = model.getLikedGames(currentUser);
                for (String game : likedGames){
                    view.addFavorite(game);
                }
                view.goToMainPanel();
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
            }
        }
        if (e.getSource() == view.getRecommendButton()){
            model.disconnectDB();
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
    public void test(){
        model.setTagAffinity("Prueba1");
        model.getRecommendations("Prueba1");
        model.disconnectDB();
    }


}
