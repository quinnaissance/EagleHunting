package Fixed;

import Game.MazeObject;

/**
 * Wall object, can be permeable or impermeable (meaning user can walk through)
 */
public class Wall extends MazeObject {

    private boolean permeable;

    /**
     * By default, walls are not walkable
     * @param x x-pos
     * @param y y-pos
     */
    public Wall(int x, int y) {
        super(x,y);
        this.permeable = true;
    }

    /**
     * Option to define walkability
     * @param permeable
     */
    public Wall(int x, int y, boolean permeable) {
        super(x,y);
        this.permeable = permeable;
    }

    /**
     * Checks if the Wall is permeable (can walk through)
     * @return
     */
    public boolean isPermeable() {
        return this.permeable;
    }

    /**
     * Make the Wall permeable
     */
    public void setPermeable(boolean permeable) {
        this.permeable = permeable;
    }
}
