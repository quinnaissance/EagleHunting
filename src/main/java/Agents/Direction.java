package Agents;

/**
 * An enum class for directional movement
 *
 * @author Wenhui Wu
 */
public enum Direction {
    STAY(0),
    UP(-1),
    DOWN(1),
    LEFT(-1),
    RIGHT(1);

    private int val;

    /**
     * Constructor
     * @param val int value
     */
    Direction(int val) {
        this.val = val;
    }

    /**
     * Get value integer
     * @return value
     */
    public int getVal() {
        return val;
    }
}
