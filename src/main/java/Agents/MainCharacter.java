package Agents;

/**
 * Character that will be controlled by user
 *
 * @author Wenhui Wu
 */
public class MainCharacter extends Agent {

    private int health;
    private int maxHealth;
    private int characterScore;

    /**
     * Constructor
     * @param x x-val
     * @param y y-val
     */
    public MainCharacter(int x, int y) {
        super(x,y);
        this.health = 100;
        this.maxHealth = 100;
        this.characterScore = 0;
    }

    /**
     * Constructor with hp/maxhp specifications
     * @param x x-pos
     * @param y y-pos
     * @param hp Health
     * @param maxHp Max health
     */
    public MainCharacter(int x, int y, int hp, int maxHp) {
        super(x,y);
        this.health = hp;
        this.maxHealth = maxHp;
        this.characterScore = 0;
    }

    /**
     * Get the health of the character
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the character's score
     * @return score
     */
    public int getCharacterScore(){
        return characterScore;
    }

    /**
     * Increment character's score
     * @param score
     * @return
     */
    public int addCharacterScore(int score) {
        return characterScore += score;
    }

    /**
     * Set character's score
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Get character's maximum health
     * @return
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Set character's maximum health
     * @param max
     */
    public void setMaxHealth(int max) {
        this.maxHealth = max;
    }

}
