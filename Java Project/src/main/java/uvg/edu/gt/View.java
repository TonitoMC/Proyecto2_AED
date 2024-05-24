package uvg.edu.gt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
    private JLabel userWelcomeLabel;
    private JLabel findGameLabel;
    private JLabel favoritesLabel;
    private JTextField usernameTextfield;
    private JTextField findGameTextfield;
    private JTextField favoritesTextfield;
    private JPasswordField passwordTextfield;
    private JButton loginButton;
    private JButton registerButton;
    private JButton findGameButton;
    private JButton favoriteButton;
    private JButton removeButton;
    private JButton recommendButton;
    private JComboBox<String> gameResultsBox;

    public View(){
        initComponents();
    }
    private void initComponents(){

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
        titlePanel.setPreferredSize(new Dimension(760,100));
        titlePanel.setBounds(0,0,760,540);
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

        //Labels
        findGameLabel = new JLabel();
        findGameLabel.setText("Busca un Juego:");
        setFont(findGameLabel, 18);
        findGameLabel.setBounds(40,40,200,30);
        mainContentPanel.add(findGameLabel);

        favoritesLabel = new JLabel();
        favoritesLabel.setText("Tus Favoritos:");
        setFont(favoritesLabel, 18);
        favoritesLabel.setBounds(40, 180, 200, 30);
        mainContentPanel.add(favoritesLabel);

        //Text fields
        findGameTextfield = new JTextField();
        findGameTextfield.setText("");
        setFont(findGameTextfield, 18);
        findGameTextfield.setBounds(40,80,270,40);
        mainContentPanel.add(findGameTextfield);

        favoritesTextfield = new JTextField();
        favoritesTextfield.setText("");
        setFont(favoritesTextfield, 18);
        favoritesTextfield.setBounds(40, 220, 270, 40);
        mainContentPanel.add(favoritesTextfield);

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
        removeButton.setBounds(330,220,100,40);
        mainContentPanel.add(removeButton);

        recommendButton = new JButton();
        recommendButton.setBackground(white);
        recommendButton.setText("Generar Recomendaciones");
        recommendButton.setForeground(darkBlack);
        recommendButton.setBounds(130,320,260,40);
        mainContentPanel.add(recommendButton);

        //JComboBox
        gameResultsBox = new JComboBox<String>();
        gameResultsBox.setBounds(460,80,280,40);
        mainContentPanel.add(gameResultsBox);
        mainPanel.add(mainContentPanel);

        setContentPane(loginPanel);


        setVisible(true);

    }
    public void setFont(JLabel label, int size){
        label.setFont(new Font("JetBrains Mono SemiBold", 0, size));
        label.setForeground(white);
    }
    public void setFont(JTextField textField, int size){
        textField.setFont(new Font("JetBrains Mono SemiBold", 0, size));
    }
    public void setFont(JButton button, int size){
        button.setFont(new Font("JetBrains Mono SemiBold", 0, size));
    }
    public void addActionListener(ActionListener listener){
        loginButton.addActionListener(listener);
        registerButton.addActionListener(listener);
        recommendButton.addActionListener(listener);
    }
    public String getUsernameInput(){
        return usernameTextfield.getText();
    }
    public String getPasswordInput() {
        return String.valueOf(passwordTextfield.getPassword());
    }
    public void goToMainPanel(){
        setContentPane(mainPanel);
    }
    public JButton getLoginButton(){
        return loginButton;
    }
    public JButton getRegisterButton(){
        return registerButton;
    }
    public JButton getRecommendButton(){
        return recommendButton;
    }
    public JButton getFindGameButton(){
        return findGameButton;
    }
    public JButton getFavoriteButton(){
        return favoriteButton;
    }
    public JButton getRemoveButton(){
        return removeButton;
    }
    public void updateSearchOutput(){

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
