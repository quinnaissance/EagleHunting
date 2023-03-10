package Fixed;

/**
 * Bonus rewards that disappear after a while
 */
public class BonusReward extends Reward {

    private int lifespan = 30;
    private boolean retired = true;

    /**
     * Constructor
     * @param x x-pos
     * @param y y-pos
     */
    public BonusReward(int x, int y) {
        super(x,y);
    }

    /**
     * Constructor with lifespan
     * @param x x-pos
     * @param y y-pos
     * @param lifespan Time to disappear
     */
    public BonusReward(int x, int y, int lifespan) {
        super(x,y);
        this.lifespan = lifespan;
    }

    /**
     * Check if item has retired (invisible and doesn't give points)
     * @return
     */
    public boolean isRetired() {
        return this.retired;
    }

    /**
     * Set the retired status
     * @param retired
     */
    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    /**
     * Decrement lifespan of item
     */
    public void reduceLifespan() {
        lifespan = lifespan - 1;
    }

    /**
     * Get lifespan of item
     * @return
     */
    public int getLifespan() {
        return lifespan;
    }
}
