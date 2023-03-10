package Agents;

/**
 * Enemy class
 */
public class Enemy extends Agent {

    private int punishment;
    private int countdown = 0;
    private boolean retired;
    private boolean stuck;

    /**
     * Game.Main constructor
     * @param x xpos
     * @param y ypos
     * @param speed speed
     * @param movable moving/nonmoving
     * @param punishment punishment
     */
    public Enemy(int x, int y, double speed, boolean movable, int punishment) {
        super(x, y, speed, movable);
        this.moveSpeed = speed;
        this.punishment = punishment;
        this.movable = movable;
        this.retired = false;
        this.stuck = false;
    }

    /**
     * Get the number of random moves
     * @return numberOfRandomMoves
     */
    public int getCountdown() {
        return countdown;
    }

    /**
     * Sets the number of random moves
     * @param moves number of moves an enemy will take
     */
    public void setCountdown(int moves) {
        this.countdown = moves;
    }

    /**
     * Get the punishment
     * @return
     */
    public int getPunishment() {
        return punishment;
    }

    /**
     * Checks if Agent is retired
     * @return
     */
    public boolean isRetired() {
        return this.retired;
    }

    /**
     * Checks if Agent is stuck.
     */
    public boolean isStuck() {
        return this.stuck;
    }

    /**
     * Set the retired status.
     * @param retired
     */
    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    /**
     * Set the stuck status.
     * @param stuck
     */
    public void setStuck(boolean stuck) {this.stuck = stuck;}

    /**
     * Decrement the countdown.
     */
    public void minusCountdown() {
        this.countdown--;
    }

    /**
     * toString method to differentiate moving/nonmoving enemies
     * @return
     */
    @Override
    public String toString() {
        if (this.isMovable()) {
            return "EnemyB";
        } else {
            return "EnemyA";
        }
    }

}
