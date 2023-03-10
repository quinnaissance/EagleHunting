package Fixed;

import Game.MazeObject;

/**
 * Any object that can be picked up by a MainCharacter
 */
public abstract class Lootable extends MazeObject {

    /**
     * Constructor
     * @param x x-pos
     * @param y y-pos
     */
    public Lootable(int x, int y) {
        super(x,y);
    }
}
