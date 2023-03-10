package Levels;

import Agents.Agent;
import Agents.Enemy;
import Agents.MainCharacter;
import Fixed.*;

import java.util.ArrayList;
import java.util.List;

public final class LevelTest extends Level {
    private Wall[] wallArray = {
            new Wall(2,0),
            new Wall(2,1),
            new Wall(2,2),
    };



    public LevelTest(){
        super(3,3);
        // MainCharacter
        MainCharacter main = new MainCharacter(1,1);
        setMainChar(main);
        //addMazeObjectToHomeCell(main);

        // Walls
        for (Wall wall : wallArray) {
            getCellArray()[wall.getY()][wall.getX()].setSpace(false);
        }

        // Start & End Tiles
        EventTile startTile = new EventTile(1,1, EventTile.eventType.START_TILE, true);
        EventTile endTile = new EventTile(2,2, EventTile.eventType.END_TILE, false);
        addMazeObjectToHomeCell(startTile);
        addMazeObjectToHomeCell(endTile);
        List<EventTile> eventTileList = new ArrayList<>();
        eventTileList.add(startTile);
        eventTileList.add(endTile);
        setEventTileList(eventTileList);


        // Agents
        List<Agent> agentList = new ArrayList<>();
        Agent enemy1 = new Enemy(0,1, 1.0, true, 30);
        Agent enemy2 = new Enemy(0,2, 1.0, false, 30);
        agentList.add(enemy1);
        agentList.add(enemy2);
        setAgentList(agentList);
        addMazeObjectToHomeCell(enemy1);
        addMazeObjectToHomeCell(enemy2);

        // Lootables
        List<Lootable> lootList = new ArrayList<>();
        Lootable reward1 = new Reward(1,0);
        Lootable bonus1 = new BonusReward(0, 0);
        lootList.add(reward1);

        lootList.add(bonus1);
        setLootableList(lootList);
        addMazeObjectToHomeCell(reward1);

        addMazeObjectToHomeCell(bonus1);
    }

}
