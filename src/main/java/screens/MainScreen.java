package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game.BubbleSpinner;


public class MainScreen extends GeneralScreen {

    private transient Skin skin;
    private transient Texture name;

    /**
     * The start up screen of the game.
     * @param game The reference to the current game.
     */
    public MainScreen(BubbleSpinner game) {
        super(game);

        //Create new Skin
        skin = new Skin(Gdx.files.internal("assets/uiSkin.json"));

        // Create button to switch to sign up
        TextButton textButtonSignUp = new TextButton("Sign up", skin);
        textButtonSignUp.setPosition(game.getGamePort().getWorldWidth() * 0F,
                game.getGamePort().getWorldHeight() * 0.875F);
        textButtonSignUp.setSize(game.getGamePort().getWorldWidth() * 0.5F,
                game.getGamePort().getWorldHeight() * 0.125F);
        textButtonSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                game.setScreen(new SignUpScreen(game));
                dispose();
            }
        });

        // Add button to switch to sign up to stage
        stage.addActor(textButtonSignUp);

        // Create button to switch to login in
        TextButton textButtonLogIn = new TextButton("Log in", skin);
        textButtonLogIn.setPosition(game.getGamePort().getWorldWidth() * 0.5F,
                game.getGamePort().getWorldHeight() * 0.875F);
        textButtonLogIn.setSize(game.getGamePort().getWorldWidth() * 0.5F,
                game.getGamePort().getWorldHeight() * 0.125F);
        textButtonLogIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                game.setScreen(new LogInScreen(game));
                dispose();
            }
        });

        // Add button to switch to login in to stage
        stage.addActor(textButtonLogIn);

    }

    @Override
    public void render(float dt) {
        super.render(dt);
        renderName();
    }

}
