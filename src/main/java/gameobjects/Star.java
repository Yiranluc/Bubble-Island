package gameobjects;

import com.badlogic.gdx.physics.box2d.World;
import sprite.Color;

public class Star extends Bubble {

    /**
     * Basic constructor.
     * @param world the current world.
     * @param x the x location.
     * @param y the y location.
     */
    public Star(World world, float x, float y) {
        super(world, x, y);
        setColor(Color.STAR);
    }
}
