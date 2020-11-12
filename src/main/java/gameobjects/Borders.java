package gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import game.BubbleSpinner;

public class Borders {

    private transient Body body;
    private transient World world;
    private transient BubbleSpinner game;

    private static final BodyDef bodyDefRightBorder = new BodyDef();
    private static final BodyDef bodyDefLeftBorder = new BodyDef();
    private static final BodyDef bodyDefBottomBorder = new BodyDef();
    private static final FixtureDef fixtureDef = new FixtureDef();
    private static final PolygonShape polygonShape = new PolygonShape();

    /**
     * The edges around the screen that collide with the bubbles.
     * @param game The bubble spinner game being played
     * @param world The world passed with the game.
     */
    public Borders(BubbleSpinner game, World world) {
        this.game = game;
        this.world = world;

        createBorders();
    }

    private void createBorders() {
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(0f, game.getGamePort().getWorldHeight());
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        polygonShape.setAsBox(game.getGamePort().getWorldWidth(), 0f);
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef).setUserData("horizontalBorder");

        // Bottom Border
        bodyDefBottomBorder.position.set(0f, 0f);
        bodyDefBottomBorder.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDefBottomBorder);
        body.createFixture(fixtureDef).setUserData("horizontalBorder");

        // Right border
        bodyDefRightBorder.position.set(game.getGamePort().getWorldWidth(), 0f);
        bodyDefRightBorder.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDefRightBorder);

        polygonShape.setAsBox(0f, game.getGamePort().getWorldHeight());
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef).setUserData("verticalBorder");

        // Left Border
        bodyDefLeftBorder.position.set(0f, 0f);
        bodyDefLeftBorder.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDefLeftBorder);
        body.createFixture(fixtureDef).setUserData("verticalBorder");

    }

}