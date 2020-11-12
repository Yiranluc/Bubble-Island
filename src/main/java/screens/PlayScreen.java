package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import controllers.CollisionHandler;
import factories.BubbleFactory;

import game.BubbleSpinner;
import gameobjects.Arrow;
import gameobjects.Borders;
import gameobjects.Bubble;
import gameobjects.BubbleBoard;
import gameobjects.Calculator;

import java.util.Iterator;
import javax.swing.JOptionPane;

import scenes.Hud;
import sprite.ScreenElement;
import states.Static;


@SuppressWarnings({"PMD.NullAssignment"})
public class PlayScreen extends GeneralScreen {

    private static final float FPS = 60;
    private static final int MAXLEVEL = 2;
    // Indicates whether a button is pressed, which means no Bubble should be shot
    private transient boolean buttonPressed;
    // Indicates whether the game is active or paused
    private transient boolean paused = false;
    //change level from 1 to 0 for testing detachment
    private transient int level = 1;

    private transient Arrow arrow;
    private transient Bubble shotBubble; // The currently active Bubble
    private transient BubbleFactory bubbleFactory;
    private transient Hud hud;
    private transient Iterable<Bubble> board;
    private transient Stage stage1; // Corresponds to a paused game
    // The menu that pops up when the game is paused
    private transient Texture pauseScreen = new Texture(ScreenElement.PAUSED_SCREEN.getPath());
    private transient Texture topDisplay = new Texture("assets/displays/up.png");
    private transient Texture bottomDisplay = new Texture("assets/displays/down.png");

    private transient World world;
    private transient Box2DDebugRenderer b2dr;

    private transient float angle;

    /**
     * Constructor for PlayScreen.
     * Initializes all fields.
     *
     * @param game the game for which this screen is used.
     */
    public PlayScreen(BubbleSpinner game) {
        super(game);

        stage1 = new Stage(game.getGamePort());

        hud = new Hud(new Stage(game.getGamePort()));
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        BubbleBoard bubbleBoard = new BubbleBoard(world, level,
                (int) getGamePort().getWorldWidth() / 2,
                (int) getGamePort().getWorldHeight() / 2);
        board = bubbleBoard;
        setUpArrow();
        setUpButtons();
        setUpCollisionListener();

        bubbleFactory = new BubbleFactory(this, getGamePort().getWorldWidth() / 2,
                getGamePort().getWorldHeight() * 0.05f);

        new Borders(game, world);

        bubbleBoard.getClustersOfSameColor();

    }

    /**
     * Creates all buttons with corresponding clickListeners
     * and adds them to the right stages.
     */
    private void setUpButtons() {

        ImageButton restart1 = createImageButton(ScreenElement.RESTART.getPath(), buttonSize,
                game.getGamePort().getWorldWidth() * (11F / 12F),
                game.getGamePort().getWorldHeight() * 0.15F);

        ImageButton restart2 = createImageButton(ScreenElement.RESTART.getPath(), 1.5f * buttonSize,
                game.getGamePort().getWorldWidth() * (7.5F / 12F),
                game.getGamePort().getWorldHeight() * (5f / 12F));

        ClickListener restartListener = new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {

                int reply = JOptionPane.showConfirmDialog(null,
                        "Do you want to restart the game?", "Warning!", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    StartGameScreen gameScreen = new StartGameScreen(game);
                    game.setScreen(gameScreen);
                    dispose();
                } else {
                    buttonPressed = true;
                }

            }
        };
        restart1.addListener(restartListener);
        restart2.addListener(restartListener);

        ImageButton pauseButton = createImageButton(ScreenElement.PAUSED.getPath(), buttonSize,
                game.getGamePort().getWorldWidth() * (10F / 12F),
                game.getGamePort().getWorldHeight() * 0.15F);

        ClickListener pauseListener = new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                pause();
                buttonPressed = true;
            }
        };
        pauseButton.addListener(pauseListener);

        ImageButton info1 = createImageButton(ScreenElement.INFO.getPath(), buttonSize,

                game.getGamePort().getWorldWidth() * (1F / 12F),
                game.getGamePort().getWorldHeight() * 0.15F);

        ImageButton info2 = createImageButton(ScreenElement.INFO.getPath(), 1.5f * buttonSize,
                game.getGamePort().getWorldWidth() * (4.5F / 12F),
                game.getGamePort().getWorldHeight() * (5f / 12F));

        ClickListener infoListener = new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                pause();
                GameManualScreen screen = new GameManualScreen(game, getThis());
                game.setScreen(screen);
                buttonPressed = true;
            }
        };
        info1.addListener(infoListener);
        info2.addListener(infoListener);

        ImageButton resumeButton = createImageButton(ScreenElement.PLAY.getPath(),
                1.5f * buttonSize,
                game.getGamePort().getWorldWidth() * (6F / 12F),
                game.getGamePort().getWorldHeight() * (5f / 12F));

        ClickListener resumeListener = new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                resume();
                buttonPressed = true;
            }
        };
        resumeButton.addListener(resumeListener);

        // Add small buttons to stage
        stage.addActor(restart1);
        stage.addActor(pauseButton);
        stage.addActor(info1);

        // Add large buttons to stage1
        stage1.addActor(resumeButton);
        stage1.addActor(restart2);
        stage1.addActor(info2);
    }

    /**
     * this method is called by the collision handler when a bubble is collided.
     * sets makes the board rotate.
     */
    public void stopBubble(float angle) {

        this.angle = Math.round(angle);

        if (shotBubble != null) {

            shotBubble.setState(new Static(getShotBubble()));
            this.shotBubble = null;

        }
    }

    /**
     * Renders the screen.
     *
     * @param delta how many frames/second.
     */
    @Override
    public void render(float delta) {
        if (!paused) {
            update(delta); //updates the screen
        }

        Gdx.gl.glClearColor(0, 0, 0, 1); //clears color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clears screen

        b2dr.render(getWorld(), game.getGameCam().combined);
        game.getBatch().setProjectionMatrix(game.getGameCam().combined);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        if (!paused) {
            arrow.render(game, this);
        }
        game.getBatch().draw(topDisplay, 0, getGamePort().getWorldHeight() * 5 / 6F,
                getGamePort().getWorldWidth(), getGamePort().getWorldHeight() / 6F);
        game.getBatch().draw(bottomDisplay, 0, 0,
                getGamePort().getWorldWidth(), getGamePort().getWorldHeight() / 10F);

        renderBubbles();

        if (paused) {
            // Draw the pauseScreen
            game.getBatch().draw(pauseScreen,
                    getGamePort().getWorldWidth() / 4F, getGamePort().getWorldHeight() / 4F,
                    getGamePort().getWorldWidth() / 2F, getGamePort().getWorldHeight() / 2F);
        }

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();

        Stage s = (Stage) Gdx.input.getInputProcessor();
        s.act(delta);
        s.draw();
    }

    //Updates the screen
    private void update(float dt) {

        //Sets a step in the world object which handles all the movement
        getWorld().step(1 / FPS, 6, 2);

        //updates the Hud
        hud.update(dt);

        bubbleFactory.update();

        if (this.angle > 0) {
            ((BubbleBoard) board).rotateSlow(this.angle);
            this.angle--;
        } else if (this.angle < 0) {
            ((BubbleBoard) board).rotateSlow(this.angle);
            this.angle++;
        }

        //Update every bubble
        updateBubbles(dt);

        // check if the player has won the game
        if (((BubbleBoard)board).isEmpty()) {
            win();
        }

    }

    /**
     * Handles every input the user is allowed to give.
     * Adds a bubble on mouse location if left clicked last.
     * if there is an active bubble its not allowed to shoot another.
     *
     * @param cursor mouse pos.
     */

    public void handleInput(Vector3 cursor) {

        if (shotBubble == null && !buttonPressed) {
            if (!bubbleFactory.isEmpty()) {
                shotBubble = bubbleFactory.getBubble();
                game.getGameCam().unproject(cursor);
                bubbleFactory.shootBubble(cursor);
                bubbleFactory.setEmpty();
            }
        }
        buttonPressed = false;
    }

    //renders all the bubbles in the bubbles arrayList
    private void renderBubbles() {
        Iterator<Bubble> boardIterator = board.iterator();
        while (boardIterator.hasNext()) {
            Bubble next = boardIterator.next();
            next.draw(game.getBatch());
        }
    }

    //updates all the bubbles in the bubbles arrayList
    private void updateBubbles(float dt) {

        Iterator<Bubble> boardIterator = board.iterator();
        while (boardIterator.hasNext()) {
            Bubble current = boardIterator.next();
            current.update(dt);
            if (current.shouldRemove()) {
                if (current.equals(shotBubble)) {
                    shotBubble = null;
                }
                hud.addScore(current.getReward());
                getWorld().destroyBody(current.getB2body());
                boardIterator.remove();
            }
        }
    }

    /**
     * Calculate the time bonus and go the the next level.
     * A new board should be drawn and the timer is reset.
     */
    public void setUpNextLevel() {
        level++;
        hud.resetWorldTimer();
        Iterator<Bubble> iterator = board.iterator();
        while (iterator.hasNext()) {
            Bubble bubble = iterator.next();
            getWorld().destroyBody(bubble.getB2body());
        }
        BubbleBoard bubbleBoard = new BubbleBoard(world, level,
                (int) getGamePort().getWorldWidth() / 2,
                (int) getGamePort().getWorldHeight() / 2);
        board = bubbleBoard;
        bubbleFactory.setEmpty();

        bubbleBoard.getClustersOfSameColor();
    }

    /**
     *  Notifies the user with a pop-up message that tells the player that they won.
     */
    private void win() {
        Calculator calculator = new Calculator();
        int bonus = calculator.calculateTimeBonus(level, hud.getTime());
        hud.addScore(bonus);
        String message = "Your time bonus: " + bonus;

        int reply = JOptionPane.showConfirmDialog(null,
                message,"Congratulations!", JOptionPane.DEFAULT_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            if (level < MAXLEVEL) {
                setUpNextLevel();
            } else {
                game.setScreen(new LeaderBoardScreen(game, hud.getScore()));
            }
            buttonPressed = true;
        }
    }

    /**
     * Notifies the user with a pop-up message that tells the player that they lost.
     * This method is called by the collisionHandler class when the bubbleboard collides
     * with the border of the screen or the bubble at the site of arrow.
     */
    public void lose() {
        JOptionPane.showMessageDialog(null, "Game Lost!");
        game.setScreen(new LeaderBoardScreen(game, hud.getScore()));
    }

    public GeneralScreen getThis() {
        return this;
    }

    public Bubble getShotBubble() {
        return shotBubble;
    }

    /**
     * Sets up the arrow sprite in the bottom middle of the screen.
     */
    private void setUpArrow() {
        arrow = new Arrow(game.getGamePort().getWorldWidth());
    }

    /**
     * initialize a collision handler for this game.
     */
    private void setUpCollisionListener() {
        this.world.setContactListener(new CollisionHandler(this));

    }

    public float getAngle() {
        return angle;
    }

    @Override
    public void pause() {
        this.paused = true;
        Gdx.input.setInputProcessor(stage1);
    }

    @Override
    public void resume() {
        this.paused = false;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        arrow.resize(width);
    }

    public World getWorld() {
        return world;
    }

    public Iterable<Bubble> getBoard() {
        return board;
    }

    public Stage getStage() {
        return this.stage1;
    }

    public BubbleFactory getBubbleFactory() {
        return bubbleFactory;
    }

}
