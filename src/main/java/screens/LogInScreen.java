package screens;

import static java.awt.Event.TAB;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dao.UserDao;
import game.BubbleSpinner;
import requests.LogInRequest;


public class LogInScreen extends GeneralScreen {

    private transient Skin skin;
    private transient TextField textFieldUser;
    private transient TextField textFieldPassword;

    /**
     * LogIn Screen.
     * @param game Reference to the current game to be displayed on the screen.
     * */
    public LogInScreen(BubbleSpinner game) {
        super(game);

        //Create new Skin
        skin = new Skin(Gdx.files.internal("assets/uiSkin.json"));

        // Create sign up button
        TextButton textButton = new TextButton("Log in", skin);
        textButton.setPosition(game.getGamePort().getWorldWidth() * 0.25F,
                game.getGamePort().getWorldHeight() * 0.325F);
        textButton.setSize(game.getGamePort().getWorldWidth() * 0.5F,
                game.getGamePort().getWorldHeight() * 0.15F);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                String username = textFieldUser.getText();
                String password = textFieldPassword.getText();
                LogInRequest logInRequest = new LogInRequest(username, password);
                UserDao dao = new UserDao();
                boolean success = dao.login(logInRequest);
                if (success) {
                    game.setScreen(new StartGameScreen(game));
                    dispose();
                }
            }
        });

        // Add sign up button to stage
        stage.addActor(textButton);

        // Create text field to enter in the username
        textFieldUser = new TextField("", skin);
        textFieldUser.setMessageText("Enter Username!");
        textFieldUser.setPosition(game.getGamePort().getWorldWidth() * 0.25F,
                game.getGamePort().getWorldHeight() * 0.625F);
        textFieldUser.setSize(game.getGamePort().getWorldWidth() * 0.5F,
                game.getGamePort().getWorldHeight() * 0.1F);

        // Disable traversing after entering the password textfield
        textFieldUser.setFocusTraversal(false);

        // Go to password textfield when pressing enter
        textFieldUser.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n' || key == TAB)) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                            || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });

        // Add username textfield to stage
        stage.addActor(textFieldUser);

        // Create text field to enter in the password
        textFieldPassword = new TextField("", skin);
        textFieldPassword.setMessageText("Enter Password!");
        textFieldPassword.setPasswordMode(true);
        textFieldPassword.setPasswordCharacter('*');
        textFieldPassword.setPosition(game.getGamePort().getWorldWidth() * 0.25F,
                game.getGamePort().getWorldHeight() * 0.5F);
        textFieldPassword.setSize(game.getGamePort().getWorldWidth() * 0.5F,
                game.getGamePort().getWorldHeight() * 0.1F);

        // IMPLEMENT "ENTER" TRIGGERING THE BUTTON AFTER THE PASSWORD TEXTFIELD

        // Go to next textfield when pressing enter
        //textFieldPassword.setTextFieldListener((textField, key) -> {
        //if ((key == '\r' || key == '\n')) {
        //textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
        // || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
        //}
        //});

        // Add password textfield button to stage
        stage.addActor(textFieldPassword);

        // Add button to go back
        TextButton goBack = new TextButton("Go Back", skin);
        goBack.setPosition(game.getGamePort().getWorldWidth() * 0F,
                game.getGamePort().getWorldHeight() * 0.875F);
        goBack.setSize(game.getGamePort().getWorldWidth() * 0.25F,
                game.getGamePort().getWorldHeight() * 0.125F);
        goBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                game.setScreen(new MainScreen(game));
                dispose();
            }
        });

        // Add button to go back to main screen to stage
        stage.addActor(goBack);

        // Set the focus of the keyboard to the textfield where you put in your username
        stage.setKeyboardFocus(textFieldUser);


    }

}
