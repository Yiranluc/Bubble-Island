package controllers;

import gameobjects.Bubble;
import java.util.Iterator;
import screens.PlayScreen;


public class CollisionHelper {


    private transient PlayScreen playScreen;


    public CollisionHelper(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }


    protected void checkBubbleConnectionToCenter() {
        //for each bubble in the board iterator,
        // check whether it is connected to the centerpiece
        Iterator boardIterator = playScreen.getBoard().iterator();

        while (boardIterator.hasNext()) {


            Bubble next = (Bubble) boardIterator.next();


            Boolean connected = next.checkConnectedToCenter();


            if (connected == false) {


                next.eliminateBubbleInNeighbors();


                next.remove();


            }


        }
    }

    /**
     * this method stops the bubble and rotates.
     */
    public void stopRotate(float bubbleX, float bubbleY, float directionX, float  directionY) {

        if (playScreen.getAngle() == 0) {

            float gameWidth = playScreen.getGamePort().getWorldWidth();


            float gameHeight = playScreen.getGamePort().getWorldHeight();

            if (bubbleX > gameWidth / 2 && bubbleY < gameHeight / 2) {

                //logHit("bottom right hit");

                if (directionX > 0 && directionY > 0) {

                    playScreen.stopBubble(60f);

                } else {
                    playScreen.stopBubble(-60f);
                }

            } else if (bubbleX < gameWidth / 2 && bubbleY < gameHeight / 2) {

                //logHit("bottom left hit");

                if (directionX < 0 && directionY > 0) {
                    playScreen.stopBubble(-60f);
                } else {
                    playScreen.stopBubble(60f);
                }

            } else if (bubbleX > gameWidth / 2 && bubbleY > gameHeight / 2) {

                //logHit("top right hit");

                if (directionX < 0 && directionY > 0) {
                    playScreen.stopBubble(60f);
                } else {
                    playScreen.stopBubble(-60f);
                }

            } else if (bubbleX < gameWidth / 2 && bubbleY > gameHeight / 2) {


                //logHit("top left hit");

                if (directionX > 0 && directionY > 0) {
                    playScreen.stopBubble(-60f);
                } else {
                    playScreen.stopBubble(60f);
                }
            }
        }
    }
}
