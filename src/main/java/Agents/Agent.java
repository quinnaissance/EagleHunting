package Agents;

import Game.MazeObject;

/**
 * An agent is anything that interacts with MainCharacter
 *
 */
public abstract class Agent extends MazeObject {

    protected double moveSpeed;
    protected boolean movable;

    /**
     * Default constructor
     * @param x
     * @param y
     */
    public Agent(int x, int y) {
        super(x,y);
        this.moveSpeed = 0.0;
        this.movable = false;
    }

    /**
     * Constructor
     * @param x x-pos
     * @param y y-pos
     * @param speed movement speed
     * @param movable whether agent moves or not
     */
    public Agent(int x, int y, double speed, boolean movable) {
        super(x,y);
        this.moveSpeed = speed;
        this.movable = movable;
    }

    /**
     * Get the movement speed
     * @return movement speed
     */
    public double getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Check if Agent can move
     * @return
     */
    public boolean isMovable() {
        return movable;
    }
}
