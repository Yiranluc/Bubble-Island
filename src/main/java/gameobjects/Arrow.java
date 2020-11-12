package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import game.BubbleSpinner;
import screens.PlayScreen;

public class Arrow {

    private static final String ARR = "assets/gameObjects/arrow.png";

    private transient Texture arrowImg;
    private transient Sprite arrowSprite;
    private transient Vector3 mousePos;
    private transient float degrees = 0f;

    private transient Vector3 input;
    private transient boolean isPressed;

    private transient int arrowX;
    private transient int arrowY;


    /**
     * Creates an arrow in the bottom middle of the game screen.
     *
     * @param width game screen width.
     */
    public Arrow(float width) {
        arrowImg = new Texture(ARR);


        arrowSprite = new Sprite(arrowImg);

        arrowSprite.setSize(width / 35, width / 7);

        arrowSprite.setOriginCenter();

        arrowX = (int) ((width / 2) - (arrowSprite.getWidth() / 2));
        arrowY = - (int) width * 5 / 100;

        arrowSprite.setX(arrowX);
        arrowSprite.setY(arrowY);

        mousePos = new Vector3();

        input = new Vector3();

        isPressed = false;
    }

    /**
     * rotates the arrow according to touch and mouse location.
     *
     * @param game the game to draw the sprite in.
     */
    public void render(BubbleSpinner game, PlayScreen playScreen) {

        //checks mouse is clicks or touch and rotates the arrow to the position
        if (Gdx.input.isTouched()) {

            input.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            mousePos.set(Gdx.input.getX() + arrowSprite.getX(), Gdx.input.getY(), 0);

            Vector3 i = new Vector3();
            i.x = arrowX - mousePos.x;
            i.y = game.getGamePort().getScreenHeight() - mousePos.y;

            degrees = (float) Math.toDegrees(Math.atan2(i.x + arrowSprite.getX(), i.y
                    - arrowSprite.getY())) + 180f;

            arrowSprite.setRotation(degrees);

            isPressed = true;

        } else {

            if (isPressed) {
                playScreen.handleInput(input);
            }

            isPressed = false;
            arrowSprite.setRotation(180);
        }

        arrowSprite.draw(game.getBatch());

    }

    public void resize(int width) {
        arrowX = width / 2;
    }
}
