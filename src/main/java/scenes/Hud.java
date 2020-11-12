package scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

public class Hud implements Disposable {

    private transient Stage stage;

    private transient int score = 0;

    private transient float time = 0;

    private transient Label countdownLabel;
    private transient Label scoreLabel;

    /**
     * Constructor for Hud.
     * Initializes all starting fields.
     * @param stage of the game.
     */
    public Hud(Stage stage) {

        //create viewport and stage with game width and height
        this.stage = stage;

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our top labels using the String, and a Label style consisting of a font and color
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.25f);

        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        Label scoreTLabel = new Label("SCORE", style);
        Label bubbleLabel = new Label("BUBBLE", style);
        Label timeLabel = new Label("TIME", style);

        //add our top row labels to our table, padding the top, and giving them all equal width
        table.add(scoreTLabel).expandX().padTop(10);
        table.add(bubbleLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        //add a second row to our table
        table.row();

        //define our second row labels
        scoreLabel = new Label(String.format("%06d", score), style);
        Label spinnerLabel = new Label("ISLAND", style);
        countdownLabel = new Label(String.format("%03d", (int) time), style);

        //add our second row labels to our table and giving them all equal width
        table.add(scoreLabel).expandX();
        table.add(spinnerLabel).expandX();
        table.add(countdownLabel).expandX();

        //add our table to the stage
        getStage().addActor(table);
    }

    /**
     * Updates the Hud.
     * Adds 1 to the time every second.
     * @param dt the amount of frames/second.
     */
    public void update(float dt) {
        time += dt;
        countdownLabel.setText(String.format("%03d", (int) time));
    }

    @Override
    public void dispose() {
        getStage().dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public int getTime() {
        return (int) time;
    }

    public void resetWorldTimer() {
        time = 0;
    }

    public int getScore() {
        return score;
    }

    /**
     * Adds a score to the Hud.
     * @param value the amount that needs to be added.
     */
    public void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }
}
