package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import screens.MainScreen;
import screens.PlayScreen;

/**
 * Bubble Spinner class.
 */
public class BubbleSpinner extends Game {

    private transient Viewport gamePort;
    private transient OrthographicCamera gameCam;
    private static final int V_WIDTH = 1200;
    private static final int V_HEIGHT = 675;

    private SpriteBatch batch;

    //Creates a new BubbleSpinner game with an empty SpriteBatch and a PlayScreen
    @Override
    public void create() {
        setBatch(new SpriteBatch());
        this.setGameCam(new OrthographicCamera());
        this.setGamePort(new FitViewport(V_WIDTH, V_HEIGHT, this.getGameCam()));
        setScreen(new MainScreen(this));
    }


    //renders the game
    @Override
    public void render() {
        super.render();
    }

    //disposes the game
    @Override
    public void dispose() {
        batch.dispose();
    }

    //Sprite batch field getter
    public SpriteBatch getBatch() {
        return batch;
    }

    //batch field setter
    private void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public Viewport getGamePort() {
        return gamePort;
    }

    public void setGamePort(Viewport gamePort) {
        this.gamePort = gamePort;
    }

    public OrthographicCamera getGameCam() {
        return gameCam;
    }

    public void setGameCam(OrthographicCamera gameCam) {
        this.gameCam = gameCam;
    }
}
