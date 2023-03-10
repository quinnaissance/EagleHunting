package Game;

import Agents.Agent;
import Agents.Direction;
import Agents.Enemy;
import Agents.MainCharacter;
import Fixed.BonusReward;
import Fixed.EventTile;
import Fixed.Lootable;
import Fixed.Reward;
import Levels.Level;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

/**
 * Holds all Maze objects: Array, lists, etc.,
 * and handles all maze logic: tile interactions, agent movements
 */
public class Maze {

    private int width;
    private int height;
    private Cell[][] cells;
    private List<Agent> agentList;
    private List<Lootable> lootableList;
    private List<EventTile> eventTileList;
    private MainCharacter mainChar;

    public Queue<Pair<MazeObject, Direction>> movementQueue;
    public boolean endTileRevealed;
    public int rewardTotal = 0;
    public int rewardsRemaining = 0;
    public boolean rewardTriggered = false;
    public boolean bonusRewardLooted = false;

    /**
     * Constructor receives a Level object with dimensions, objects and lists
     *
     * @param lvl
     */
    public Maze(Level lvl) {
        loadLevel(lvl);

        // Get total number of non-bonus rewards
        for (Lootable ltb : getLootableList()) {
            if (ltb.toString().equals("Reward")) {
                rewardTotal++;
            }
        }
        rewardsRemaining = rewardTotal;
    }

    /**
     * Receive lists from Level object
     *
     * @param lvl Level object
     */
    private void loadLevel(Level lvl) {
        this.width = lvl.getWidth();
        this.width = lvl.getWidth();
        this.height = lvl.getHeight();
        this.cells = lvl.getCellArray();
        this.mainChar = lvl.getMainChar();
        this.agentList = lvl.getAgentList();
        this.lootableList = lvl.getLootableList();
        this.eventTileList = lvl.getEventTileList();
        this.endTileRevealed = false;
        spawnMainChar();
        movementQueue = new LinkedList<>();
    }

    /**
     * Spawns MainCharacter into the maze
     */
    private void spawnMainChar() {
        // Finds the Start tile and puts MainCharacter on it
        for (EventTile tile : eventTileList) {
            if (tile.getEventType() == EventTile.eventType.START_TILE) {
                getCellXY(tile.getX(), tile.getY()).setSpace(true);
                mainChar.setX(tile.getX());
                mainChar.setY(tile.getY());
                getCellXY(mainChar.getX(), mainChar.getY()).addObject(mainChar);
            }
        }
    }

    /**
     * Returns information of the mainCharacter
     * @return mainChar
     */
    public MainCharacter getMainChar() {
        return this.mainChar;
    }

    /**
     * Returns agent list
     * @return agentList
     */
    public List<Agent> getAgentList() {
        return this.agentList;
    }

    /**
     * Returns lootable object list
     * @return lootableList
     */
    public List<Lootable> getLootableList() {
        return this.lootableList;
    }

    /**
     * Returns event tile list
     * @return eventTileList
     */
    public List<EventTile> getEventTileList() {
        return this.eventTileList;
    }

    /**
     * Move desired object directionally
     *
     * @param dir
     * @param obj
     */
    public void mazeObjectDirectionalMove(MazeObject obj, Direction dir) {

        if (!hasObstacle(obj, dir)) {
            // No obstacles, move object
            Cell originalCell = getCellXY(obj.getX(), obj.getY());
            Cell newCell = getCellInDirection(originalCell, dir);
            newCell.addObject(originalCell.removeObject(obj));

            // Add to movement queue
            if (obj instanceof Agent)
                movementQueue.add(new Pair<>(obj, dir));

            // Check collision with enemy/reward
            checkCollisionWithEnemy();
            checkCollisionWithReward();
        }

    }

    /**
     * Reduce the lifespan of bonusReward
     */
    private void reduceLifespan(BonusReward bonusReward) {
        if (bonusReward.getLifespan() > 0){
        bonusReward.reduceLifespan();
        }
        else {
            bonusReward.setRetired(true);
            getCellXY(bonusReward.getX(), bonusReward.getY()).removeObject(bonusReward);
        }
    }

    /**
     * Find bonusReward object in Maze
     */
    public void findBonusReward(){
        for (MazeObject obj : getLootableList()) {
            if (obj instanceof BonusReward && !((BonusReward) obj).isRetired()){
                reduceLifespan((BonusReward) obj);
            }
        }
    }

    /**
     * Set all bonus reward in maze as not retired
     */
    public void setNotRetiredBonusReward(){
        for (MazeObject obj : getLootableList()) {
            if (obj instanceof BonusReward ){
                ((BonusReward) obj).setRetired(false);
            }
        }
    }

    /**
     * Check whether Game.Main Character collides with a reward.
     */
    private void checkCollisionWithReward() {

        Cell mainCharCell = getCellXY(mainChar.getX(), mainChar.getY());

        // List to hold rewards that have been looted
        List<Reward> rewardList = new ArrayList<>();

        for (MazeObject obj : mainCharCell.getObjectList()) {
            if (obj instanceof Reward) {


                // Special case for BonusReward
                if (obj instanceof BonusReward) {
                    if (!((BonusReward) obj).isRetired()) {
                        mainChar.addCharacterScore(((BonusReward) obj).getScore());
                        rewardList.add((BonusReward) obj);
                        bonusRewardLooted = true;
                    }
                } else {
                    mainChar.addCharacterScore(((Reward) obj).getScore());
                    rewardList.add((Reward) obj);
                    rewardsRemaining--;
                    rewardTriggered = true;
                }
            }
        }

        // If something was looted, remove it from its Cell
        for (Reward rw : rewardList) {
            getCellXY(rw.getX(), rw.getY()).removeObject(rw);
            removeReward(rw);
            checkExitCondition();

        }
    }

    /**
     * Removes Reward from lootable list
     * @param object Bonus Rewards
     */
    private void removeReward(Reward object) {
        lootableList.remove(object);
    }

    /**
     *  Checks whether or not exit should be permeable or impermeable
     */
    private void checkExitCondition() {
        int lootSize = lootableList.size();
        for (Lootable rewards : lootableList) {
            if (rewards instanceof BonusReward) {
                lootSize--;
            }
        }
        if (lootSize <= 0) {
            for (EventTile tile : eventTileList) {
                if (tile.getEventType() == EventTile.eventType.END_TILE) {
                    tile.reveal();
                    getCellXY(tile.getX(), tile.getY()).setSpace(true);
                    endTileRevealed = true;
                }
            }
        }

    }

    /**
     * Checks whether Game.Main Character holds the same cell as an enemy
     */
    private void checkCollisionWithEnemy() {

        Cell mainCharCell = getCellXY(mainChar.getX(), mainChar.getY());

        // List to hold enemies that damage MainCharacter
        List<Enemy> punishmentList = new ArrayList<>();

        // Loop through objects in Cell
        for (MazeObject obj : mainCharCell.getObjectList()) {
            if (obj instanceof Enemy) {
                mainChar.setHealth(mainChar.getHealth() - ((Enemy) obj).getPunishment());
                punishmentList.add((Enemy) obj);
            }
        }

        // If an enemy damaged MainCharacter, remove it from its Cell
        for (Enemy nme : punishmentList) {
            getCellXY(nme.getX(), nme.getY()).removeObject(nme);
            nme.setRetired(true);
        }

    }

    /**
     * Get the width of the maze
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get height of the maze
     * @return height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Check if an object attempting to move in a direction has an obstacle
     * An obstacle is either out of bounds OR an impermeable wall
     *
     * @param obj
     * @param dir
     * @return
     */
    public boolean hasObstacle(MazeObject obj, Direction dir) {
        try {
            switch (dir) {
                // if there is an obstacle, return true (else return false)
                // First condition: cell is within bounds
                // Second condition: cell is not an impermeable wall
                case UP:
                    return (obj.getY() + dir.getVal() < 0) // out of bounds
                            || (getCellXY(obj.getX(), obj.getY() + dir.getVal()).hasImpermeableWall());
                case DOWN:
                    return (obj.getY() + dir.getVal() > getHeight() - 1) // out of bounds
                            || (getCellXY(obj.getX(), obj.getY() + dir.getVal()).hasImpermeableWall());
                case LEFT:
                    return (obj.getX() + dir.getVal() < 0) // out of bounds
                            || (getCellXY(obj.getX() + dir.getVal(), obj.getY()).hasImpermeableWall());
                case RIGHT:
                    return (obj.getX() + dir.getVal() > getWidth() - 1) // out of bounds
                            || (getCellXY(obj.getX() + dir.getVal(), obj.getY()).hasImpermeableWall());
                case STAY:
                    return false;
            }
        } catch (Exception e) {
            System.out.println("cells[" + obj.getY() + "][" + obj.getX() + "] does not exist");
            throw new IndexOutOfBoundsException();
        }
        return false;
    }

    /**
     * Gets the possible directions each enemy can move and moves them randomly
     */
    public void randomEnemyMove(MazeObject obj) {
        if (obj instanceof Enemy && ((Enemy) obj).isMovable() && !((Enemy) obj).isRetired()) {

            // Get possible movement directions
            List<Direction> allPossibleMoves = new ArrayList<>();
            for (Direction dir : Direction.values()) {
                // Add to list if clear
                if (dir != Direction.STAY && !hasObstacle(obj, dir)) {
                    allPossibleMoves.add(dir);
                }
            }
            // allPossibleMoves now has possible moves
            // if its not empty, move enemy a random direction
            if (allPossibleMoves.size() > 0) {
                int randDir = ThreadLocalRandom.current().nextInt(0, allPossibleMoves.size());
                mazeObjectDirectionalMove(obj, allPossibleMoves.get(randDir));
            }
        }
    }

    /**
     * Determining moving behaviors of enemies
     */
    public void movingEnemies(){
        for (MazeObject obj : getAgentList()) {
            if (obj instanceof Enemy && ((Enemy) obj).isMovable() && !((Enemy) obj).isRetired()) {
                if (((Enemy) obj).getCountdown() > 0) {
                    randomEnemyMove(obj);
                    ((Enemy) obj).minusCountdown();
                }
                else if (((Enemy) obj).isStuck() == false)
                    findClosestMove(obj);

                else {movesWhenStuck(obj); }
            }
        }
    }

    /**
     * When enemy is stuck, set countdown to 5
     * @param obj enemy
     */
    private void movesWhenStuck(MazeObject obj){
        ((Enemy) obj).setCountdown(5);
        ((Enemy) obj).setStuck(false);
    }

    /**
     * Enemy attempts to move closer to mainCharacter
     * Precondition: Object is a movable enemy, not retired, and has countdown of 0
     * @param obj Movable enemy
     *
     */
    private void findClosestMove(MazeObject obj){

        // Get possible movement directions

        List<Direction> possibleClosestMoves = new ArrayList<>();
        int xValue = obj.getX() - mainChar.getX(); //move LEFT, negative
        int yValue = obj.getY() - mainChar.getY(); //move UP, negative
        int compare = Math.abs(xValue) - Math.abs(yValue);
        Direction direction;


        //adds valid move which causes enemies to move closer
        direction = yValue < 0 ? Direction.DOWN : Direction.UP;
        if (!hasObstacle(obj, direction)) {
            if (yValue != 0)
                possibleClosestMoves.add(direction);
        }

        direction = xValue < 0 ? Direction.RIGHT : Direction.LEFT;
        if (!hasObstacle(obj, direction)) {
            if (xValue != 0)
                possibleClosestMoves.add(direction);
        }

        //two valid moves that bring enemy closer to main character
        if (possibleClosestMoves.size() == 2) {
            if (compare > 0) {    //obj is furthest in terms of x
                mazeObjectDirectionalMove(obj, possibleClosestMoves.get(0));

            } else if (compare < 0) {
                mazeObjectDirectionalMove(obj, possibleClosestMoves.get(1));

            } else {
                int randDir = ThreadLocalRandom.current().nextInt(0, possibleClosestMoves.size());
                mazeObjectDirectionalMove(obj, possibleClosestMoves.get(randDir));

            }

            //one valid move that bring enemy closer to main character
        } else if (possibleClosestMoves.size() == 1) {
            mazeObjectDirectionalMove(obj, possibleClosestMoves.get(0));

            //no valid moves
        } else {
            ((Enemy) obj).setStuck(true);
        }
    }

    /**
     * Get cell in a particular direction from another cell
     * Precondition: There is a valid cell in the desired direction
     *
     * @param c   Starting cell
     * @param dir Direction
     * @return Cell in desired direction
     */
    public Cell getCellInDirection(Cell c, Direction dir) {
        int x = c.getX();
        int y = c.getY();

        switch (dir) {
            case UP:
            case DOWN:
                y += dir.getVal();
                break;
            case LEFT:
            case RIGHT:
                x += dir.getVal();
                break;
        }
        return getCellXY(x, y);
    }

    /**
     * Adds object to Cells array in position defined in MazeObject
     *
     * @param obj MazeObject to add
     */
    public void addMazeObjectToHomeCell(MazeObject obj) {
        cells[obj.getY()][obj.getX()].addObject(obj);
    }

    /**
     * Adds MazeObject to a Cell at a particular location
     *
     * @param obj
     * @param x
     * @param y
     */
    public void addMazeObjectToCell(MazeObject obj, int x, int y) {
        cells[y][x].addObject(obj);
    }

    /**
     * Print cells as String
     *
     * @return formatted String
     */
    public String getCellsArrayAsString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {

                // Get list with objects
                List<MazeObject> cellList = cells[row][col].getObjectList();

                // Print proper objects
                if (cells[row][col].getObjectList().size() != 0) {
                    if (cells[row][col].getObjectOfType("MainCharacter").size() != 0) {
                        sb.append("M"); // Prioritize MainCharacter in printing
                    } else if (cells[row][col].getObjectOfType("Enemy").size() != 0) {
                        sb.append("E");
                    } else {
                        sb.append(getMazeObjectString(cells[row][col].getObjectList().get(0)));
                    }
                } else if (cells[row][col].hasImpermeableWall()) {
                    sb.append("X"); // ■
                } else {
                    sb.append("·");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Basically serves as a toString method for each MazeObject
     *
     * @param obj MazeObject object
     * @return character as String
     */
    public static String getMazeObjectString(MazeObject obj) {
        String s = "e";
        if (obj == null)
            s = "0";
        else {
            s = switch (obj.getClass().getSimpleName()) {
                case "Wall" -> "X";
                case "Space" -> "·";
                case "EventTile" -> "@";
                case "Reward" -> "*";
                case "BonusReward" -> "+";
                case "MainCharacter" -> "M";
                case "Enemy" -> "E"; //TODO differentiate moving/non-moving enemy
                default -> "/";
            };
        }
        return s;
    }

    /**
     * Gets the Cell at the given x/y coordinates
     *
     * @param x
     * @param y
     * @return
     */
    public Cell getCellXY(int x, int y) {
        return cells[y][x];
    }
}