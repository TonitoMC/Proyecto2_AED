package uvg.edu.gt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        view.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == view.getLoginButton()){
            if (model.validateUser(view.getUsernameInput(), view.getPasswordInput())) {
                view.goToMainPanel();
            }
        }
        if (e.getSource() == view.getRegisterButton()){
            if (model.registerUser(view.getUsernameInput(), view.getPasswordInput())){
                view.goToMainPanel();
            }
        }
        if (e.getSource() == view.getRecommendButton()){
            model.disconnectDB();
        }
        if (e.getSource() == view.getFindGameButton()){
            //Look up games w/ algorithm and update JCombo
        }
        if (e.getSource() == view.getFavoriteButton()){
            //Add JCombo selected to favorites
        }
        if (e.getSource() == view.getRemoveButton()){
            //Remove JCombo from favorites
        }
    }
    public void test(){
        model.setTagAffinity("Prueba1");
        model.getRecommendations("Prueba1");
        model.disconnectDB();
    }


}
