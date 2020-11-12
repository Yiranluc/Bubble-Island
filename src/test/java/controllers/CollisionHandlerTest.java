package controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.viewport.Viewport;
import factories.BubbleFactory;
import gameobjects.Bubble;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import screens.PlayScreen;
import sprite.Color;


public class CollisionHandlerTest {
    private transient PlayScreen playScreen;

    private transient CollisionHandler collisionHandler;

    private transient Contact contact;

    private transient Fixture fixtureA;

    private transient Fixture fixtureB;

    private transient Bubble bubbleA;
    private transient Bubble bubbleB;

    private transient Body body1;
    private transient Body body2;

    private transient Viewport viewport;

    private transient String dynamic = "dynamic";
    private transient String horizontal = "horizontalBorder";
    private transient String vertical = "verticalBorder";
    private transient String staticState = "static";


    @BeforeEach
    void init() {
        playScreen = Mockito.mock(PlayScreen.class);

        collisionHandler = new CollisionHandler(playScreen);

        contact = Mockito.mock(Contact.class);
        fixtureA = Mockito.mock(Fixture.class);
        fixtureB = Mockito.mock(Fixture.class);

        bubbleA = Mockito.mock(Bubble.class);
        bubbleB = Mockito.mock(Bubble.class);

        body1 = Mockito.mock(Body.class);
        body2 = Mockito.mock(Body.class);

        viewport = Mockito.mock(Viewport.class);

        Mockito.when(contact.getFixtureA()).thenReturn(fixtureA);
        Mockito.when(contact.getFixtureB()).thenReturn(fixtureB);

        Mockito.when(fixtureA.getBody()).thenReturn(body1);
        Mockito.when(fixtureB.getBody()).thenReturn(body2);

        Mockito.when(playScreen.getGamePort()).thenReturn(viewport);
    }


    @Test
    void testBubbleXBubbleSameColor() {

        Mockito.when(contact.getFixtureA()).thenReturn(fixtureA);
        Mockito.when(contact.getFixtureB()).thenReturn(fixtureB);

        Mockito.when(fixtureA.getBody()).thenReturn(body1);
        Mockito.when(fixtureB.getBody()).thenReturn(body2);

        Mockito.when(fixtureA.getUserData()).thenReturn("dg");
        Mockito.when(fixtureB.getUserData()).thenReturn("vbcv");

        Mockito.when(body1.getUserData()).thenReturn(bubbleA);
        Mockito.when(body2.getUserData()).thenReturn(bubbleB);

        Mockito.when(bubbleA.getColorType()).thenReturn(Color.BLUE);
        Mockito.when(bubbleB.getColorType()).thenReturn(Color.BLUE);

        Mockito.when(playScreen.getShotBubble()).thenReturn(bubbleA);


        ArrayList<Bubble> chain = new ArrayList<>();
        chain.add(bubbleB);

        Mockito.when(bubbleB.getChain()).thenReturn(chain);



        collisionHandler.beginContact(contact);

        Mockito.verify(bubbleB, Mockito.times(3)).getChain();

    }

    @Test
    void testBubbleXBubbleDiffColor() {

        Mockito.when(contact.getFixtureA()).thenReturn(fixtureA);
        Mockito.when(contact.getFixtureB()).thenReturn(fixtureB);

        Mockito.when(fixtureA.getBody()).thenReturn(body1);
        Mockito.when(fixtureB.getBody()).thenReturn(body2);

        Mockito.when(fixtureA.getUserData()).thenReturn("fg");
        Mockito.when(fixtureB.getUserData()).thenReturn("df");

        Mockito.when(body1.getUserData()).thenReturn(bubbleA);
        Mockito.when(body2.getUserData()).thenReturn(bubbleB);

        Mockito.when(bubbleA.getColorType()).thenReturn(Color.BLUE);
        Mockito.when(bubbleB.getColorType()).thenReturn(Color.RED);

        Mockito.when(playScreen.getShotBubble()).thenReturn(bubbleA);

        Mockito.when(bubbleA.getX()).thenReturn(1f);
        Mockito.when(bubbleB.getX()).thenReturn(1f);



        ArrayList<Bubble> chain = new ArrayList<>();
        chain.add(bubbleB);

        Mockito.when(bubbleB.getChain()).thenReturn(chain);

        collisionHandler.beginContact(contact);

        Assertions.assertEquals(1, chain.size());

        Mockito.verify(bubbleA).getChain();

        Mockito.when(viewport.getWorldHeight()).thenReturn(0f);
        Mockito.when(viewport.getWorldWidth()).thenReturn(0f);

        collisionHandler.setBubbleX(10);
        collisionHandler.setBubbleY(-1);

        collisionHandler.stopRotate();


        Mockito.verify(playScreen).stopBubble(Mockito.anyFloat());


        Mockito.when(body1.getUserData()).thenReturn(bubbleB);
        Mockito.when(body2.getUserData()).thenReturn(bubbleA);

        collisionHandler.beginContact(contact);
    }

    // If a bubble from the board collides with a horizontal border, then the game is lost.
    @Test
    void testBoardXHorizontalBorder() {
        Mockito.when(contact.getFixtureA().getUserData()).thenReturn(staticState);
        Mockito.when(contact.getFixtureB().getUserData()).thenReturn(horizontal);
        collisionHandler.beginContact(contact);
        verify(playScreen).lose();
    }

    // If a bubble from the board collides with a horizontal border, then the game is lost.
    @Test
    void testHorizontalBorderXBoard() {
        Mockito.when(contact.getFixtureA().getUserData()).thenReturn(horizontal);
        Mockito.when(contact.getFixtureB().getUserData()).thenReturn(staticState);
        collisionHandler.beginContact(contact);
        verify(playScreen).lose();
    }

    // If a bubble from the board collides with a vertical border, then the game is lost.
    @Test
    void testBoardXVerticalBorder() {
        Mockito.when(contact.getFixtureA().getUserData()).thenReturn(staticState);
        Mockito.when(contact.getFixtureB().getUserData()).thenReturn(vertical);
        collisionHandler.beginContact(contact);
        verify(playScreen).lose();
    }

    // If a bubble from the board collides with a vertical border, then the game is lost.
    @Test
    void testVerticalBorderXBoard() {
        Mockito.when(contact.getFixtureA().getUserData()).thenReturn(vertical);
        Mockito.when(contact.getFixtureB().getUserData()).thenReturn(staticState);
        collisionHandler.beginContact(contact);
        verify(playScreen).lose();
    }

    // If a bubble from the board collides with the bubble at the arrow site, then the game is lost.
    @Test
    void testBoardXArrow() {
        // mock the newly created shooting bubble by bubbleFactory
        Bubble bubbleA = mock(Bubble.class);
        Bubble bubbleB = mock(Bubble.class);
        BubbleFactory bubbleFactory = mock(BubbleFactory.class);

        Mockito.when(playScreen.getBubbleFactory()).thenReturn(bubbleFactory);
        Mockito.when(playScreen.getBubbleFactory().getBubble()).thenReturn(bubbleB);

        // assign fixtures with corresponding user data: one static, one static shot bubble
        Mockito.when(contact.getFixtureA().getUserData()).thenReturn(staticState);
        Mockito.when(contact.getFixtureA().getBody().getUserData()).thenReturn(bubbleA);
        Mockito.when(contact.getFixtureB().getBody().getUserData()).thenReturn(bubbleB);
        Mockito.when(contact.getFixtureB().getUserData()).thenReturn(staticState);

        // call the method
        collisionHandler.beginContact(contact);
        verify(playScreen).lose();
    }

    // If a bubble from the board collides with the bubble at the arrow site, then the game is lost.
    @Test
    void testArrowXBoard() {
        // mock the newly created shooting bubble by bubbleFactory
        Bubble bubble = mock(Bubble.class);
        BubbleFactory bubbleFactory = mock(BubbleFactory.class);

        Mockito.when(playScreen.getBubbleFactory()).thenReturn(bubbleFactory);
        Mockito.when(playScreen.getBubbleFactory().getBubble()).thenReturn(bubble);

        // assign fixtures with corresponding user data: one static, one static shot bubble
        Mockito.when(contact.getFixtureB().getUserData()).thenReturn(staticState);
        Mockito.when(contact.getFixtureA().getBody().getUserData()).thenReturn(bubble);
        Mockito.when(contact.getFixtureA().getUserData()).thenReturn(staticState);

        // call the method
        collisionHandler.beginContact(contact);
        verify(playScreen).lose();
    }

}
