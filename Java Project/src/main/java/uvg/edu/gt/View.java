package uvg.edu.gt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de la interaccion con el usuario, provee una interfaz grafica con la cual interactuar
 * @author Jose Merida
 * @author Adrian Lopez
 * @since 25-05-2024
 * @version 1.0
 */
public class View extends JFrame{
    private Color darkBlack;
    private Color lightBlack;
    private Color lightBlue;
    private Color white;
    private JPanel loginPanel;
    private JPanel titlePanel;
    private JPanel loginContentPanel;
    private JPanel mainPanel;
    private JPanel mainContentPanel;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel recommendationsLabel;
    private JLabel resultsLabel;
    private JLabel findGameLabel;
    private JLabel favoritesLabel;
    private JTextField usernameTextfield;
    private JTextField findGameTextfield;
    private JPasswordField passwordTextfield;
    private JButton loginButton;
    private JButton registerButton;
    private JButton findGameButton;
    private JButton favoriteButton;
    private JButton removeButton;
    private JButton recommendButton;
    private JComboBox<String> favoritesBox;
    private JComboBox<String> gameResultsBox;
    private JComboBox<String> recommendationResultsBox;
    private JToggleButton freeButton;

    /**
     * Constructor para la clase View, crea los componentes
     */
    public View(){
        initComponents();
    }

    /**
     * Este metodo se encarga de crear los componentes de la interfaz con los que interactua el usuario
     */
    private void initComponents(){
        //Variables de colores utilizados en la interfaz
        darkBlack = new Color(34,40,49);
        white = new Color(238, 238, 238);
        lightBlack = new Color(49, 54, 63);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(34,40,49));
        setSize(760,540);
        setPreferredSize(new Dimension(760,540));
        getContentPane().setLayout(null);

        //Setup de loginPanel
        loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(760,540));
        loginPanel.setLayout(null);

        //Setup de titlePanel
        titlePanel = new JPanel();
        titlePanel.setBackground(darkBlack);
        titlePanel.setLayout(new GridBagLayout());

        titleLabel = new JLabel();
        setFont(titleLabel, 36);
        titleLabel.setText("FindMyGame");
        titlePanel.add(titleLabel, new GridBagConstraints());
        titlePanel.setBounds(0,0,760,100);
        loginPanel.add(titlePanel);

        //Setup de loginContentPanel
        loginContentPanel = new JPanel();
        loginContentPanel.setBackground(lightBlack);
        loginContentPanel.setBounds(0,100,760,440);
        loginContentPanel.setLayout(null);

        usernameLabel = new JLabel();
        usernameLabel.setText("Nombre de Usuario");
        setFont(usernameLabel, 18);
        usernameLabel.setBounds(240,60,200,30);
        loginContentPanel.add(usernameLabel);

        passwordLabel = new JLabel();
        passwordLabel.setText("Contraseña");
        setFont(passwordLabel, 18);
        passwordLabel.setBounds(240,170,200,30);
        loginContentPanel.add(passwordLabel);

        usernameTextfield = new JTextField();
        usernameTextfield.setText("");
        usernameTextfield.setBounds(240,90,270,40);
        setFont(usernameTextfield, 18);
        loginContentPanel.add(usernameTextfield);

        passwordTextfield = new JPasswordField();
        passwordTextfield.setText("");
        passwordTextfield.setFont(new Font("JetBrains Mono SemiBold", 0, 18));
        passwordTextfield.setBounds(240,200,270,40);

        loginButton = new JButton();
        loginButton.setBackground(white);
        loginButton.setText("Ingresar");
        setFont(loginButton, 12);
        loginButton.setForeground(darkBlack);
        loginButton.setBounds(380,270,100,40);
        loginContentPanel.add(loginButton);

        registerButton = new JButton();
        registerButton.setText("Registrar");
        registerButton.setBackground(white);
        setFont(registerButton, 12);
        registerButton.setForeground(darkBlack);
        registerButton.setBounds(260,270,100,40);
        loginContentPanel.add(registerButton);

        loginContentPanel.add(passwordTextfield);
        loginPanel.add(loginContentPanel);
        setContentPane(loginPanel);

        //Setup de mainPanel
        mainPanel = new JPanel();
        mainPanel.setBounds(0,0,760,540);
        mainPanel.setBackground(lightBlack);
        mainPanel.setLayout(null);

        //Setup de mainContentPanel
        mainContentPanel = new JPanel();
        mainContentPanel.setBounds(0,100,760,440);
        mainContentPanel.setBackground(lightBlack);
        mainContentPanel.setLayout(null);

        //ToggleButton
        freeButton = new JToggleButton();
        freeButton.setText("Unicamente Gratis");
        freeButton.setBounds(330,320, 270, 40);
        freeButton.setBackground(white);
        freeButton.setForeground(darkBlack);
        mainContentPanel.add(freeButton);

        //Labels
        findGameLabel = new JLabel();
        findGameLabel.setText("Busca un Juego:");
        setFont(findGameLabel, 18);
        findGameLabel.setBounds(40,40,200,30);
        mainContentPanel.add(findGameLabel);

        resultsLabel = new JLabel();
        resultsLabel.setText("Resultados:");
        setFont(resultsLabel, 18);
        resultsLabel.setBounds(460,40,200,30);
        mainContentPanel.add(resultsLabel);

        favoritesLabel = new JLabel();
        favoritesLabel.setText("Tus Favoritos:");
        setFont(favoritesLabel, 18);
        favoritesLabel.setBounds(40, 190, 200, 30);
        mainContentPanel.add(favoritesLabel);

        recommendationsLabel = new JLabel();
        recommendationsLabel.setText("Recomendaciones:");
        setFont(recommendationsLabel, 18);
        recommendationsLabel.setBounds(460,190,200,30);
        mainContentPanel.add(recommendationsLabel);

        //Text fields
        findGameTextfield = new JTextField();
        findGameTextfield.setText("");
        setFont(findGameTextfield, 18);
        findGameTextfield.setBounds(40,80,270,40);
        mainContentPanel.add(findGameTextfield);



        //Buttons
        findGameButton = new JButton();
        findGameButton.setBackground(white);
        findGameButton.setText("Buscar");
        findGameButton.setForeground(darkBlack);
        findGameButton.setBounds(330,80,100,40);
        mainContentPanel.add(findGameButton);

        favoriteButton = new JButton();
        favoriteButton.setBackground(white);
        favoriteButton.setText("Agregar a Favoritos");
        favoriteButton.setForeground(darkBlack);
        favoriteButton.setBounds(460,140,280,40);
        mainContentPanel.add(favoriteButton);

        removeButton = new JButton();
        removeButton.setBackground(white);
        removeButton.setText("Eliminar");
        removeButton.setForeground(darkBlack);
        removeButton.setBounds(330,230,100,40);
        mainContentPanel.add(removeButton);

        recommendButton = new JButton();
        recommendButton.setBackground(white);
        recommendButton.setText("Generar Recomendaciones");
        recommendButton.setForeground(darkBlack);
        recommendButton.setBounds(40,320,270,40);
        mainContentPanel.add(recommendButton);

        //JComboBox
        gameResultsBox = new JComboBox<String>();
        gameResultsBox.setBounds(460,80,280,40);
        mainContentPanel.add(gameResultsBox);

        recommendationResultsBox = new JComboBox<String>();
        recommendationResultsBox.setBounds(460, 230, 280, 40);
        mainContentPanel.add(recommendationResultsBox);

        favoritesBox = new JComboBox<String>();
        favoritesBox.setBounds(40,230,270,40);
        mainContentPanel.add(favoritesBox);

        mainPanel.add(mainContentPanel);

        setContentPane(loginPanel);


        setVisible(true);

    }

    /**
     * Este metodo se utiliza para cambiar el estilo de los labels
     * @param label el label a modificar
     * @param size tamano de la fuente
     */
    public void setFont(JLabel label, int size){
        label.setFont(new Font("JetBrains Mono SemiBold", 0, size));
        label.setForeground(white);
    }

    /**
     * Este metodo se utiliza para cambiar el estilo de los campos de texto
     * @param textField el textField a modificar
     * @param size el tamano de la fuente
     */
    public void setFont(JTextField textField, int size){
        textField.setFont(new Font("JetBrains Mono SemiBold", 0, size));
    }

    /**
     * Este metodo se utiliza para modificar el estilo de los botones
     * @param button el boton a modificar
     * @param size el tamano de la fuente
     */
    public void setFont(JButton button, int size){
        button.setFont(new Font("JetBrains Mono SemiBold", 0, size));
    }

    /**
     * Este metodo se utiliza para poder interpretar las interacciones del usuario con la itnerfaz
     * @param listener el listener
     */
    public void addActionListener(ActionListener listener){
        loginButton.addActionListener(listener);
        registerButton.addActionListener(listener);
        recommendButton.addActionListener(listener);
        removeButton.addActionListener(listener);
        findGameButton.addActionListener(listener);
        favoriteButton.addActionListener(listener);
    }

    /**
     * Este metodo se utiliza para obtener el texto dentro del campo de texto de usuario
     * @return String input del usuario
     */
    public String getUsernameInput(){
        return usernameTextfield.getText();
    }

    /**
     * Este metodo se utiliza para obtener el texto dentro del campo de password
     * @return String input del usuario
     */
    public String getPasswordInput() {
        return String.valueOf(passwordTextfield.getPassword());
    }

    /**
     * Este metodo se utiliza para regresar al panel principal
     */
    public void goToMainPanel(){
        mainPanel.add(titlePanel);
        setContentPane(mainPanel);
    }

    /**
     * Getter para loginButton
     * @return loginButton
     */
    public JButton getLoginButton(){
        return loginButton;
    }

    /**
     * Getter para registerbutton
     * @return registerButton
     */
    public JButton getRegisterButton(){
        return registerButton;
    }

    /**
     * Getter para recommendButton
     * @return recommendButton
     */
    public JButton getRecommendButton(){
        return recommendButton;
    }

    /**
     * Getter para findGameButton
     * @return findGameButton
     */
    public JButton getFindGameButton(){
        return findGameButton;
    }

    /**
     * Getter para el campo de busqueda de juegos
     * @return
     */
    public String getGameSearch(){
        return findGameTextfield.getText();
    }

    /**
     * Getter para favoriteButton
     * @return favoriteButton
     */
    public JButton getFavoriteButton(){
        return favoriteButton;
    }

    /**
     * Getter para removeButton
     * @return removeButton
     */
    public JButton getRemoveButton(){
        return removeButton;
    }

    /**
     * Metodo para obtener la seleccion de los resultados de busqueda de juegos
     * @return String, nombre del juego seleccionado
     */
    public String getGameSelection(){
        return (String) gameResultsBox.getSelectedItem();
    }

    /**
     * Este metodo actualiza el output de la lista de resultados de busqueda
     * @param inputList los resultados de la busqueda para ingresar
     */
    public void updateSearchOutput(ArrayList<String> inputList){
        gameResultsBox.removeAllItems();
        for (String game : inputList){
            gameResultsBox.addItem(game);
        }
    }

    /**
     * Este metodo actualiza los favoritos
     * @return el item seleccionado de gamesResultBox
     */
    public String updateFavorites(){
        favoritesBox.addItem((String) gameResultsBox.getSelectedItem());
        return (String) gameResultsBox.getSelectedItem();
    }

    /**
     * Este metodo agrega un juego nuevo a los favoritos
     * @param gameName el  nombre del juego
     */
    public void addFavorite(String gameName){
        favoritesBox.addItem(gameName);
    }

    /**
     * Este metodo agrega un elemento a la caja de recomendaciones
     * @param gameName el nombre del juego por agregar
     */
    public void addRecommendation(String gameName){
        recommendationResultsBox.addItem(gameName);
    }

    /**
     * Limpia la caja de recomendaciones
     */
    public void clearRecommendations(){
        recommendationResultsBox.removeAllItems();
    }

    /**
     * Getter para la seleccion de favoritos
     * @return String, el nombre del juego seleccionado
     */
    public String getFavoriteSelection(){
        return (String) favoritesBox.getSelectedItem();
    }

    /**
     * Remueve el juego seleccionado de los favoritos
     */
    public void removeFromFavorites(){
        Object selectedItem = favoritesBox.getSelectedItem();
        favoritesBox.removeItem(selectedItem);
        favoritesBox.revalidate();
        favoritesBox.repaint();
    }

    /**
     * Obtiene el valor de FreeButton (si esta presionado o no)
     * @return True si esta presionado, False de lo contrario
     */
    public boolean getFreeButton(){
        return freeButton.isSelected();
    }


}
