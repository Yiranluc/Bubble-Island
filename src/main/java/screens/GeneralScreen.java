package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.BubbleSpinner;
import sprite.ScreenElement;

/**
 * This abstract class defines the basic elements in methods
 * that most, if not all, of our screens have in common.
 */
public abstract class GeneralScreen implements Screen {

    transient BubbleSpinner game;
    transient float buttonSize;
    transient Stage stage;
    transient Texture background;
    private transient Texture name;


    /**
     * Basic constructor that sets up the class attributes.
     * @param game the current BubbleSpinner instance.
     */
    public GeneralScreen(BubbleSpinner game) {
        this.game = game;
        // Create new Stage
        stage = new Stage(game.getGamePort());
        // Set stage to InputProcessor in order to handle events
        Gdx.input.setInputProcessor(stage);
        // Initialize the background image
        background = new Texture(ScreenElement.BACKGROUND.getPath());
        // Calculate  size for small buttons
        buttonSize = game.getGamePort().getWorldHeight() * 0.125F;
        name = new Texture(ScreenElement.NAME.getPath());
    }

    /**
     * Creates an ImageButton according to the parameters.
     * @param path file path to the sprite.
     * @param size width and height of the button.
     * @param x x-coordinate of the button.
     * @param y y-coordinate of the button.
     * @return a new ImageButton.
     */
    ImageButton createImageButton(String path, float size, float x, float y) {
        // Create new Texture for the Image button
        Texture texture = new Texture(path);

        // Create background region
        TextureRegion textureRegion = new TextureRegion(texture);

        // Create drawable background region
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);

        // Create image button
        ImageButton imageButton = new ImageButton(textureRegionDrawable);

        // Set width of button
        imageButton.setWidth(size);

        // Set height of button
        imageButton.setHeight(size);

        // Set position of button
        imageButton.setPosition(x, y, Align.center);

        return imageButton;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); //clears color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clears screen

        // Draw background
        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().end();

        // Handle events and draw the buttons
        stage.act(delta);
        stage.draw();
    }

    /**
     * Resize the screen according to the new width and height.
     * @param width new screen width.
     * @param height new screen height.
     */
    @Override
    public void resize(int width, int height) {
        game.getGamePort().update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    /**
     * Render the name.
     */
    public void renderName() {
        game.getBatch().begin();
        game.getBatch().draw(name,
                getGamePort().getWorldWidth() * 3 / 8F, getGamePort().getWorldHeight() / 2F,
                getGamePort().getWorldWidth() / 4F, getGamePort().getWorldHeight()  / 4F);
        game.getBatch().end();
    }

    public Stage getStage() {
        return this.stage;
    }

    public Viewport getGamePort() {
        return game.getGamePort();
    }

    public GeneralScreen getThis() {
        return this;
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
