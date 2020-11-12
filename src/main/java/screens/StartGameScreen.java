package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game.BubbleSpinner;
import sprite.ScreenElement;

/**
 * This class offers the player the option to start a new game,
 * sign out or visit the leader board.
 */
public class StartGameScreen extends GeneralScreen {

    private transient ImageButton play;
    private transient ImageButton ranking;

    /**
     * The screen the user sees after successfully logging in.
     *
     * @param game The bubble spinner game passed to that screen.
     */
    public StartGameScreen(BubbleSpinner game) {
        super(game);

        setUpButtons();
    }

    @Override
    public void render(float dt) {
        super.render(dt);
        renderName();
    }

    private void setUpButtons() {
        // Create Play button
        play = createImageButton(ScreenElement.PLAY.getPath(), 2 * buttonSize,
                game.getGamePort().getWorldWidth() * 0.5F,
                game.getGamePort().getWorldHeight() * 5 / 16F);

        // Add listener to button
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                dispose();
                StartGameScreen.this.game.setScreen(new PlayScreen(StartGameScreen.this.game));
            }
        });

        // Add button to stage
        stage.addActor(play);

        //Create new Skin
        Skin skin = new Skin(Gdx.files.internal("assets/uiSkin.json"));

        // Add button to sign out
        TextButton signOut = new TextButton("Sign out", skin);
        signOut.setPosition(game.getGamePort().getWorldWidth() * 0F,
                game.getGamePort().getWorldHeight() * 0.875F);
        signOut.setSize(game.getGamePort().getWorldWidth() * 0.25F,
                game.getGamePort().getWorldHeight() * 0.125F);
        signOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                dispose();
                game.setScreen(new MainScreen(game));
            }
        });

        // Add button to sign out to the stage
        stage.addActor(signOut);

        // Create ranking button
        ranking = createImageButton(ScreenElement.RANKING.getPath(), buttonSize,
                game.getGamePort().getWorldWidth() * 1 / 12F,
                game.getGamePort().getWorldHeight() * 0.15F);

        // Add listener to button
        ranking.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                dispose();
                game.setScreen(new LeaderBoardScreen(game));
            }
        });

        // Add button to stage
        stage.addActor(ranking);
    }
}
