package Levels;

import Agents.Agent;
import Agents.Enemy;
import Agents.MainCharacter;
import Fixed.*;

import java.util.ArrayList;
import java.util.List;

/**
 * First level
 */
public final class LevelOne extends Level {

    // Array of wall objects
    private Wall[] wallArray = {
            new Wall(1,2),
            new Wall(2,2),
            new Wall(2,3),
            new Wall(2,4),
            new Wall(2,4),
            new Wall(4,1),
            new Wall(4,2),
            new Wall(6,4),
            new Wall(5,2),
            new Wall(6,2),
            new Wall(3,6),
            new Wall(5,6),
            new Wall(6,6),
            new Wall(4,4),
            new Wall(5,4),
            new Wall(8,2),
            new Wall(8,3),
            new Wall(8,4),
            new Wall(8,5),
            new Wall(10,5),
            new Wall(10,6),
            new Wall(11,4),
            new Wall(10,4),
            new Wall(10,2),
            new Wall(12,2),
            new Wall(10,1),
    };

    public LevelOne() {
        super(14, 8);

        // MainCharacter
        MainCharacter main = new MainCharacter(1,1);
        setMainChar(main);
        //addMazeObjectToHomeCell(main);

        // Walls
        for (Wall wall : wallArray) {
            getCellArray()[wall.getY()][wall.getX()].setSpace(false);
        }

        // Start & End Tiles
        EventTile startTile = new EventTile(0,1, EventTile.eventType.START_TILE, true);
        EventTile endTile = new EventTile(12,7, EventTile.eventType.END_TILE, false);
        addMazeObjectToHomeCell(startTile);
        addMazeObjectToHomeCell(endTile);
        List<EventTile> eventTileList = new ArrayList<>();
        eventTileList.add(startTile);
        eventTileList.add(endTile);
        setEventTileList(eventTileList);


        // Agents
        List<Agent> agentList = new ArrayList<>();
        Agent enemy1 = new Enemy(3,2, 1.0, true, 30);
        Agent enemy2 = new Enemy(4,5, 1.0, false, 30);
        agentList.add(enemy1);
        agentList.add(enemy2);
        setAgentList(agentList);
        addMazeObjectToHomeCell(enemy1);
        addMazeObjectToHomeCell(enemy2);

        // Lootables
        List<Lootable> lootList = new ArrayList<>();
        Lootable reward1 = new Reward(1,3);
        Lootable reward2 = new Reward(1,4);
        Lootable bonus1 = new BonusReward(5, 3);
        lootList.add(reward1);
        lootList.add(reward2);
        lootList.add(bonus1);
        setLootableList(lootList);
        addMazeObjectToHomeCell(reward1);
        addMazeObjectToHomeCell(reward2);
        addMazeObjectToHomeCell(bonus1);
    }

}
