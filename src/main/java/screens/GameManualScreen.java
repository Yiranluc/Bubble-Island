package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game.BubbleSpinner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import sprite.ScreenElement;

public class GameManualScreen extends GeneralScreen {

    private transient BitmapFont font;
    private transient GeneralScreen previous;
    private transient ImageButton back;
    private transient Texture display;

    private static final String filePath = "src/main/resources/gameManual.txt";
    private transient String manual;

    /**
     * The screen the user sees after successfully logging in.
     *
     * @param game The bubble spinner game passed to that screen.
     */
    public GameManualScreen(BubbleSpinner game, GeneralScreen previous) {
        super(game);

        this.previous = previous;

        // Read the filePath from a text file
        try {
            manual = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println("Game filePath could not be found");
            manual = "Manual not available";
        }

        setUpButtons();
        display = new Texture(ScreenElement.GENERAL_SCREEN.getPath());
        
        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.getBatch().begin();

        // Draw the display image
        game.getBatch().draw(display,
                getGamePort().getWorldWidth() / 4f, getGamePort().getWorldHeight() / 12f,
                getGamePort().getWorldWidth() / 2f, getGamePort().getWorldHeight() * 5 / 6f);

        // Draw the text
        font.draw(game.getBatch(), manual,
                game.getGamePort().getWorldWidth() * 0.3F,
                game.getGamePort().getWorldHeight() * 0.75F,
                game.getGamePort().getWorldWidth() * 0.4F,
                8, true);

        game.getBatch().end();
    }

    private void setUpButtons() {
        // create back button
        back = createImageButton(ScreenElement.BACK.getPath(), buttonSize,
                game.getGamePort().getWorldWidth() * (1 / 12F),
                game.getGamePort().getWorldHeight() * 0.85F);

        // Add listener to button
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                game.setScreen(previous);
                Gdx.input.setInputProcessor(previous.getStage());
            }
        });

        // Add button to stage
        stage.addActor(back);
    }
}