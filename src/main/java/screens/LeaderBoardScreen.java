package screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dao.UserDao;
import game.BubbleSpinner;
import javax.swing.JOptionPane;
import sprite.ScreenElement;



public class LeaderBoardScreen extends GeneralScreen {

    private transient Texture display;
    private transient BitmapFont font;
    private transient ImageButton back;
    private transient Label rank;
    private transient Label username;
    private transient Label highScore;
    private transient UserDao userDao;
    private static final float FONT_SIZE = 1.5f;

    /**
     * Basic constructor that sets up the class attributes.
     *
     * @param game the current BubbleSpinner instance.
     */
    public LeaderBoardScreen(BubbleSpinner game) {
        super(game);
        display = new Texture(ScreenElement.GENERAL_SCREEN.getPath());
        userDao = new UserDao();
        setUpButtons();

        initialize();
    }

    /**
     * Basic constructor that sets up the class attributes.
     * It will ask the user to enter their name.
     *
     * @param game the current BubbleSpinner instance.
     * @param score the new score to be added
     */
    public LeaderBoardScreen(BubbleSpinner game, int score) {
        super(game);
        display = new Texture(ScreenElement.GENERAL_SCREEN.getPath());
        userDao = new UserDao();
        setUpButtons();

        addScore(score);

        initialize();
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
                dispose();
                game.setScreen(new StartGameScreen(game));
            }
        });

        // Add button to stage
        stage.addActor(back);
    }

    /**
     * Method to make a pop-up screen, where the user can enter their name.
     * This name will be stored in the database, together with the score.
     *
     */
    public void addScore(int score) {
        userDao.insertName_Score(score, makeUsernameDialog());
    }

    private void initialize() {

        Table table = new Table();
        font = new BitmapFont();


        table.top();
        table.setFillParent(true);

        rank = new Label("Rank", new Label.LabelStyle(font, Color.BLACK));
        username = new Label("Username", new Label.LabelStyle(font, Color.BLACK));
        highScore = new Label("High Score", new Label.LabelStyle(font, Color.BLACK));

        rank.setFontScale(FONT_SIZE);
        username.setFontScale(FONT_SIZE);
        highScore.setFontScale(FONT_SIZE);

        table.add(rank).expandX().padTop(120);
        table.add(username).expandX().padTop(120);
        table.add(highScore).expandX().padTop(120);

        for (int i = 1; i < userDao.retrieveName().size(); i++) {
            table.row().padTop(10);

            Label number = new Label(Integer.toString(i), new Label.LabelStyle(font, Color.BLACK));
            number.setFontScale(FONT_SIZE);
            table.add(number).expandX();

            Label name = new Label(userDao.retrieveName()
                    .get(i), new Label.LabelStyle(font, Color.BLACK));
            name.setFontScale(FONT_SIZE);
            table.add(name).expandX();

            Label score = new Label(userDao.retrieveScore()
                    .get(i), new Label.LabelStyle(font,Color.BLACK));
            score.setFontScale(FONT_SIZE);
            table.add(score);

        }
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.getBatch().begin();

        game.getBatch().draw(display, 0f, 0f,
                getGamePort().getWorldWidth() * 1.02f, getGamePort().getWorldHeight() * 0.98f);

        game.getBatch().end();

        stage.act(delta);
        stage.draw();

    }

    public String makeUsernameDialog() {
        return JOptionPane.showInputDialog(null, "Please enter your username",
                "The game has ended", 1);
    }
}
