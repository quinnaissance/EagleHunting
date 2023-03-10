package Levels;

import Agents.Agent;
import Agents.Enemy;
import Agents.MainCharacter;
import Fixed.*;
import Game.Cell;
import Game.MazeObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Template for generating mazes
 * Includes one MainCharacter, two EventTiles and lists for walls, enemies and rewards
 * Outermost ring consists of walls and beginning/end tiles
 */
public abstract class Level {

    private int width;
    private int height;
    private Cell[][] cells;
    private List<Agent> agentList;
    private List<Lootable> lootableList;
    private List<EventTile> eventTileList;
    private MainCharacter mainChar;

    /**
     * Constructor
     *
     * @param width  Width of maze
     * @param height Height of maze
     */
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        initializeCells();
    }

    /**
     * Creates Level object from txt file input
     * PRECONDITION: txt file must account for outer ring of walls
     * @param levelFileName
     */
    public Level(String levelFileName) {
        readLevelTxt(levelFileName);
    }

    /**
     * Get the width of the maze
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the maze
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the entire cells array with start objects
     *
     * @return 2D array of Cells
     */
    public Cell[][] getCellArray() {
        return cells;
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
     * Get MainCharacter object
     *
     * @return MainCharacter
     */
    public MainCharacter getMainChar() {
        return mainChar;
    }

    /**
     * Sets the MainCharacter object
     *
     * @param main MainCharacter obj
     */
    public void setMainChar(MainCharacter main) {
        this.mainChar = main;
    }

    /**
     * Get a list of all agents in the maze
     *
     * @return
     */
    public List<Agent> getAgentList() {
        return agentList;
    }

    /**
     * Sets the agent list for the scene
     *
     * @param list List of agents
     */
    public void setAgentList(List<Agent> list) {
        this.agentList = list;
    }

    /**
     * Get a list of all lootables in the maze
     *
     * @return List of lootables
     */
    public List<Lootable> getLootableList() {
        return lootableList;
    }

    /**
     * Sets the list of lootables in the scene
     *
     * @param list Lootable list
     */
    public void setLootableList(List<Lootable> list) {
        this.lootableList = list;
    }

    /**
     * Gets the event tile list
     *
     * @return
     */
    public List<EventTile> getEventTileList() {
        return eventTileList;
    }

    /**
     * Sets the event tile list
     *
     * @param eventTileList List of event tiles
     */
    public void setEventTileList(List<EventTile> eventTileList) {
        this.eventTileList = eventTileList;
    }

    /**
     * Makes outer ring of maze all impermeable walls
     */
    private void initializeCells() {
        // Traverse 2D array
        for (int row = 0; row < cells.length; row++) { // Traverse rows
            for (int col = 0; col < cells[row].length; col++) { // Traverse columns
                // Make outer border all walls
                if (row == 0 || col == 0 || row == cells.length - 1 || col == cells[row].length - 1) {
                    // Make wall
                    cells[row][col] = new Cell(col, row);
                    cells[row][col].setSpace(false);
                } else {
                    // Make space
                    cells[row][col] = new Cell(col, row);
                }
            }
        }
    }

    /**
     * Build level from txt file
     * WARNING: File must be formatted as follows:
     * First line: [width],[height] ... e.g. "32,16"
     * Subsequent lines: [x],[y],[type] ... e.g. "0,0,Wall"
     * @param fileName Name/path of txt file
     */
    private void readLevelTxt(String fileName) {

        // Read txt file with objects and construct level
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String firstLine = br.readLine();
            String[] firstLineSplit = firstLine.split(",");

            // Check first line width/height parameters
            if (firstLineSplit.length != 2) {
                throw new IOException(fileName + "first line is improper format");
            } else {
                // Generate maze size
                this.width = Integer.parseInt(firstLineSplit[0]);
                this.height = Integer.parseInt(firstLineSplit[1]);
                this.cells = new Cell[height][width];
                initializeCells();
            }

            // Initialize lists & mainchar
            agentList = new ArrayList<>();
            lootableList = new ArrayList<>();
            eventTileList = new ArrayList<>();
            mainChar = new MainCharacter(0,0);
            setMainChar(mainChar);

            // Traverse through text file
            int lineCounter = 2;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (!line.isBlank()) {
                    String[] tokenized = line.split(",");

                    // Check length
                    if (tokenized.length != 3) {
                        throw new IOException(fileName + " line " + lineCounter + " is improper format");
                    }

                    // Tokenize: 0-x, 1-y, 2-type
                    int x = Integer.parseInt(tokenized[0]);
                    int y = Integer.parseInt(tokenized[1]);
                    String type = tokenized[2].trim();

                    switch (type) {
                        case "Wall":
                            cells[y][x].setSpace(false);
                            break;
                        case "Start":
                            EventTile start = new EventTile(x, y, EventTile.eventType.START_TILE, true);
                            addMazeObjectToHomeCell(start);
                            eventTileList.add(start);
                            break;
                        case "End":
                            EventTile end = new EventTile(x, y, EventTile.eventType.END_TILE, false);
                            addMazeObjectToHomeCell(end);
                            eventTileList.add(end);
                            break;
                        case "Reward":
                            Reward rwd = new Reward(x,y);
                            addMazeObjectToHomeCell(rwd);
                            lootableList.add(rwd);
                            break;
                        case "Bonus":
                            BonusReward bonus = new BonusReward(x,y);
                            bonus.setScore(50);
                            addMazeObjectToHomeCell(bonus);
                            lootableList.add(bonus);
                            break;
                        case "EnemyA":
                            Enemy stillEnemy = new Enemy(x,y, 1.0, false, 15);
                            addMazeObjectToHomeCell(stillEnemy);
                            agentList.add(stillEnemy);
                            break;
                        case "EnemyB":
                            Enemy movingEnemy = new Enemy(x, y, 1.0, true, 30);
                            addMazeObjectToHomeCell(movingEnemy);
                            agentList.add(movingEnemy);
                            break;
                    }
                    lineCounter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
