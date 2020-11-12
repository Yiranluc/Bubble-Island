package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import gameobjects.Bubble;

import java.util.ArrayList;

import screens.PlayScreen;

/**
 * implements libGDX ContactListener, to add collision physics to the bubbles.
 */
@SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
public class CollisionHandler implements ContactListener {

    private transient Fixture fixtureA;
    private transient Fixture fixtureB;
    private transient PlayScreen playScreen;

    // added attribute to fix pmd error
    private transient String dynamic = "dynamic";
    private transient String horizontal = "horizontalBorder";
    private transient String vertical = "verticalBorder";
    private transient String staticState = "static";

    private final transient int maxChainSize = 3;

    private transient Bubble activeBubble;


    private transient float bubbleY;
    private transient float bubbleX;
    private transient float directionX;
    private transient float directionY;

    private transient CollisionHelper collisionHelper;

    /**
     * uses the super constructor and give the handler an instance of the game.
     *
     * @param playScreen the game the class was created with.
     */
    public CollisionHandler(PlayScreen playScreen) {
        super();

        this.playScreen = playScreen;

        collisionHelper = new CollisionHelper(playScreen);
    }


    /**
     * when two objects collide.
     * Bubble X Bubble.
     * Bubble X wall.
     *
     * @param contact the contact that occurred.
     */
    @Override
    public void beginContact(Contact contact) {

        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        // If the board hits the border, then the game is lost.
        // A pop-up message will be shown.
        if (checkGameLost(fixtureA, fixtureB)) {
            playScreen.lose();
        }

        // If the bubbleBoard collides the shoot bubble in the bottom of the screen,
        // the game is also lost.
        if (fixtureA.getUserData().equals(staticState) && fixtureB.getUserData()
                .equals(staticState)) {
            gameLost();
        }

        if (playScreen.getShotBubble() != null) {
            setUpValues();
        }

        if (activeBubble != null && fixtureA.getBody().getUserData() instanceof Bubble
                && fixtureB.getBody().getUserData() instanceof Bubble) {
            checkBubbleXBubble();
        }
        if (!checkBorder() && activeBubble != null && (fixtureA.getUserData().equals(dynamic)
                || fixtureB.getUserData().equals(dynamic))) {

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {

                    collisionHelper.stopRotate(bubbleX, bubbleY, directionX, directionY);

                }
            });

        }
    }

    private boolean checkBorder() {
        if (fixtureA.getUserData().equals(vertical)
                || fixtureB.getUserData().equals(vertical)) {
            Fixture border = fixtureA.getUserData().equals(vertical) ? fixtureA : fixtureB;
            Fixture object = border.equals(fixtureA) ? fixtureB : fixtureA;

            if (object.getUserData() != null && object.getUserData().equals(dynamic)) {

                setMoveDirection(-(activeBubble.getxdir()),
                        activeBubble.getydir());
                return true;
            }

        } else if (fixtureA.getUserData().equals(horizontal)
                || fixtureB.getUserData().equals(horizontal)) {
            Fixture border = fixtureA.getUserData().equals(horizontal)
                    ? fixtureA : fixtureB;
            Fixture object = border.equals(fixtureA) ? fixtureB : fixtureA;

            if (object.getUserData() != null && object.getUserData().equals(dynamic)) {
                setMoveDirection(activeBubble.getxdir(), (-activeBubble.getydir()));
                return true;
            }
        }
        return false;
    }

    private void checkBubbleXBubble() {
        if (fixtureA.getBody().getUserData() == activeBubble) {

            extractBubbles(fixtureA, fixtureB);
            collisionHelper.checkBubbleConnectionToCenter();

        } else if (fixtureB.getBody().getUserData() == activeBubble) {
            extractBubbles(fixtureB, fixtureA);
            collisionHelper.checkBubbleConnectionToCenter();
        }
    }

    private void gameLost() {
        if ((fixtureA.getBody().getUserData().equals(playScreen.getBubbleFactory().getBubble()))
                || (fixtureB.getBody().getUserData()
                .equals(playScreen.getBubbleFactory().getBubble()))) {
            playScreen.lose();
        }
    }

    /**
     * used for testing.
     */
    public void stopRotate() {
        collisionHelper.stopRotate(bubbleX, bubbleY, directionX, directionY);

    }


    /**
     * Check which bubble  is hit and check if chains are formed.
     *
     * @param fixtureA bubble.
     * @param fixtureB bubble.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")  //false positive?
    private void extractBubbles(Fixture fixtureA, Fixture fixtureB) {

        Bubble shot = (Bubble) fixtureA.getBody().getUserData();
        Bubble hit = (Bubble) fixtureB.getBody().getUserData();

        // add each other as a neighbor
        shot.getNeighbors().add(hit);
        hit.getNeighbors().add(shot);


        //this will make sure that all the collided bubbles have the same chain.
        if (hit.getColorType().equals(shot.getColorType())) {

            ArrayList<Bubble> newChain = new ArrayList<>();


            if (!hit.getChain().contains(hit)) {
                hit.getChain().add(hit);
            }

            if (!shot.getChain().contains(shot)) {
                shot.getChain().add(shot);
            }

            newChain.addAll(hit.getChain());

            newChain.addAll(shot.getChain());

            hit.setChain(newChain);
            shot.setChain(newChain);

            if (hit.getChain().size() >= maxChainSize) {
                for (int i = 0; i < hit.getChain().size(); i++) {
                    Bubble bubble = hit.getChain().get(i);
                    bubble.remove();
                    // update the neighbors of the neighbors of the current bubble.
                    bubble.eliminateBubbleInNeighbors();
                }
            }
        } else {
            shot.setChain(new ArrayList<Bubble>());
            shot.getChain().add(shot);
        }


    }

    /**
     * checks if the game is lost.
     *
     * @param fixtureA fixture A.
     * @param fixtureB fixture B.
     * @return if the game is lost return true.
     */
    boolean checkGameLost(Fixture fixtureA, Fixture fixtureB) {
        return (fixtureA.getUserData().equals(vertical) && fixtureB.getUserData()
                .equals(staticState))
                || (fixtureA.getUserData().equals(horizontal) && fixtureB.getUserData()
                .equals(staticState))
                || (fixtureB.getUserData().equals(vertical) && fixtureA.getUserData()
                .equals(staticState))
                || (fixtureB.getUserData().equals(horizontal) && fixtureA.getUserData()
                .equals(staticState));
    }

    /**
     * prints some useful info about contacts.
     *
     * @param pos where in the screen.
     */
    private void logHit(String pos) {
        Gdx.app.log("hit", pos);

        Gdx.app.log("direction", "dX = "
                + directionX + "  dY = "
                + directionY);

    }

    private void setUpValues() {

        activeBubble = playScreen.getShotBubble();
        bubbleY = activeBubble.getY();
        bubbleX = activeBubble.getX();
        directionX = activeBubble.getxdir();
        directionY = activeBubble.getydir();


    }

    private void setMoveDirection(float x, float y) {
        activeBubble.setMoveDirection(x,
                y);
    }


    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        fixtureA.getBody().setLinearVelocity(0, 0);
        fixtureB.getBody().setLinearVelocity(0, 0);

    }

    public void setBubbleY(float bubbleY) {
        this.bubbleY = bubbleY;
    }

    public void setBubbleX(float bubbleX) {
        this.bubbleX = bubbleX;
    }


}
