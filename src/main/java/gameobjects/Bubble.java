package gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import sprite.Color;
import states.State;
import states.Static;



public class Bubble extends Sprite {
    private transient Color color;

    private transient World world;
    private transient Body b2body;

    private static final float RADIUS = 25;
    private static final float bubbleSpeed = 350;

    private transient boolean toRemove = false;
    private transient float xdir;
    private transient float ydir;
    private transient int reward = 10;

    // the cluster of neighboring bubbles with the same color as the current bubble.
    private ArrayList<Bubble> chain = new ArrayList<>();

    // The State of the Bubble can either be Dynamic of Fixed
    private transient State state;

    //The neighbors of a bubble in the board.
    private transient ArrayList<Bubble> neighbors = new ArrayList<Bubble>();

    /**
     * Bubble class that represents every bubble in the game.
     *
     * @param world the world that the bubble is in.
     * @param x     the x location of the bubble
     * @param y     the y location of the bubble
     */
    public Bubble(World world, float x, float y) {
        this.color = Color.random();
        this.world = world;
        this.xdir = 0;
        this.ydir = 0;

        defineBubbleDynamic(x, y);

        setBounds(0, 0, RADIUS * 2, RADIUS * 2);

        // By default, a Bubble is Static
        this.state = new Static(this);

        // add itself to the chains
        ArrayList<Bubble> itself = new ArrayList<Bubble>();
        itself.add(this);
        this.chain = itself;

        try {
            setRegion(new Texture(color.getPath()));
        } catch (Exception e) {
            System.out.println("Could not find texture");
        }

    }

    private void defineBubbleDynamic(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);

        fdef.shape = shape;
        getB2body().createFixture(fdef);

        world.createBody(bdef);

        b2body.setUserData(this);
    }


    /**
     * Updates the bubble according to its State.
     */
    public void update(float dt) {
        state.update(dt);
    }

    /**
     * Updates the position of the Bubble.
     */
    public void updatePosition() {
        float xpos = b2body.getPosition().x - getWidth() / 2;
        float ypos = b2body.getPosition().y - getHeight() / 2;
        setPosition(xpos, ypos);
    }


    /**
     * Depends on the angle the board should rotate, each bubble rotates accordingly.
     *
     * @param angle the angle the board should rotate with.
     * @param x the offset on x axis to get the center of the screen.
     * @param y the offset on y axis to get the center of the screen.
     */
    public void rotateWithBoard(float angle, float x, float y) {

        b2body.setAwake(true);

        setOriginCenter();
        setRotation(getRotation() + angle);
        angle = (float) Math.toRadians(angle);
        float newx = (float) ((b2body.getPosition().x - x) * Math.cos(angle)
                - (b2body.getPosition().y - y) * Math.sin(angle) + x);
        float newy = (float) ((b2body.getPosition().x - x) * Math.sin(angle)
                + (b2body.getPosition().y - y) * Math.cos(angle) + y);

        b2body.setTransform(newx, newy, 0);
    }

    /**
     * Sets in which direction the bubble needs to move.
     */
    public void setDirection(float xdir, float ydir) {
        float dirVecSize = Math.abs(xdir - getB2body().getPosition().x) + ydir;
        this.xdir = ((xdir - getB2body().getPosition().x) / dirVecSize) * bubbleSpeed;
        this.ydir = ((ydir - getB2body().getPosition().y) / dirVecSize) * bubbleSpeed;
    }

    public void setMoveDirection(float xdir, float ydir) {
        this.xdir = xdir;
        this.ydir = ydir;
    }

    /**
     * Remove itself from its neighbors' instance 'neighbors'.
     */
    public void eliminateBubbleInNeighbors() {
        for (int i = 0; i < this.getNeighbors().size(); i++) {
            this.getNeighbors().get(i).getNeighbors().remove(this);
        }
    }

    /**
     * Breadth-first recursion to check whether a bubble is connected
     * to the center of the bubble board.
     * @return true or false
     */
    public boolean checkConnectedToCenter() {
        Queue<Bubble> queue = new LinkedList();
        queue.add(this);
        Set<Bubble> traversed = new HashSet();
        traversed.add(this);
        while (queue.size() > 0) {
            Bubble curBubble = queue.remove();
            for (int i = 0; i < curBubble.getNeighbors().size(); i++) {
                Bubble bubble = curBubble.getNeighbors().get(i);
                if (bubble instanceof Star) {
                    return true;
                } else if (!traversed.contains(bubble)) {
                    traversed.add(bubble);
                    queue.add(bubble);
                }
            }
        }
        return false;
    }

    public void remove() {
        toRemove = true;
    }

    public boolean shouldRemove() {
        return toRemove;
    }

    /**
     * This method name prevents collision with the
     * "getColor" method of badLogic.
     * Be aware to use the right one.
     *
     * @return Color of the bubble.
     */
    public Color getColorType() {
        return this.color;
    }

    /**
     * Method to change the State of the Bubble.
     * @param state The new State.
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Method to retrieve the State of the Bubble.
     * @return state The current State.
     */
    public State getState() {
        return this.state;
    }

    public Body getB2body() {
        return b2body;
    }

    public float getxdir() {
        return xdir;
    }

    public float getydir() {
        return ydir;
    }

    public static float getRadius() {
        return RADIUS;
    }

    public ArrayList<Bubble> getChain() {
        return chain;
    }

    public void setChain(ArrayList<Bubble> component) {
        chain = component;
    }

    public int getReward() {
        return reward;
    }

    /**
     * The bubbles is not worth points anymore (due to expiration).
     */
    public void noReward() {
        reward = 0;
    }

    /**
     * Set the color of a bubble to enable testing for whether a cluster of same color bubbles
     * are stored correctly.
     * @param color a new color
     */
    public void setColor(Color color) {
        this.color = color;
        try {
            setRegion(new Texture(color.getPath()));
        } catch (Exception e) {
            System.out.println("Could not find texture");
        }
    }

    public ArrayList<Bubble> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Bubble> neighbors) {
        this.neighbors = neighbors;
    }
}
