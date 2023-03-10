package Game;

/**
 * Parent class to all other objects in MazeObject array
 * Contains x and y position values
 */
public abstract class MazeObject {

    private int x;
    private int y;

    /**
     * Takes in positional arguments
     * @param x x-position
     * @param y y-position
     */
    public MazeObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x position
     * @return x-position
     */
    public int getX() {
        return x;
    }

    /**
     * Set the x position
     * @param x x-position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the y position
     * @return y-position
     */
    public int getY() {
        return y;
    }

    /**
     * Set the y position
     * @param y y-position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * toString requires class name
     * @return Class name as string
     */
    public String toString() {
        return getClass().getSimpleName();
    }
}
