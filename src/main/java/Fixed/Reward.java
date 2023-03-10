package Fixed;

/**
 * A Lootable that provides a reward to the MainCharacter
 */
public class Reward extends Lootable {

    private int score;

    /**
     * Constructor
     * @param x x-pos
     * @param y y-pos
     */
    public Reward(int x, int y) {
        super(x,y);
        this.score = 10; // default score
    }

    /**
     * Constructor with score amount
     * @param x x-pos
     * @param y y-pos
     * @param score score
     */
    public Reward(int x, int y, int score) {
        super(x,y);
        this.score = score;
    }

    /**
     * Get the item's score
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Set item's score
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
}
