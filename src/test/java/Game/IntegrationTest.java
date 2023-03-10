package Game;

import Agents.Direction;
import Agents.Enemy;
import Agents.MainCharacter;
import Display.Assets;
import Fixed.BonusReward;
import Levels.LevelFromTxt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {
    private Maze gameMaze;
    // addMazeObjectToCell, addMazeObjectToHomeCell,
    // getMazeObjectAsString, and getCellsArrayAsString are not tested as
    // both functions function as printing method / clarity for map visualization
    // as symbols, and is not used in the final rendition of the game.

    @Test
    public void testException(){
        //testExceptions
        gameMaze = new Maze( new LevelFromTxt(Assets.LVL_TEST_TWO));
        Enemy enemy = new Enemy(-1,-1,1,true,10);
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            gameMaze.hasObstacle(enemy,Direction.RIGHT);
        });
    }

    @Test
    public void testRewardEndAndWall(){
        gameMaze = new Maze( new LevelFromTxt(Assets.LVL_TEST_TWO));

        // X X X X X
        // X X * X X
        // X + M @ X
        // X X X X X
        // X X X X X

        //mainCharacter stays
        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(),Direction.STAY);
        assertEquals(2,gameMaze.getMainChar().getX(), "mainCharacter stays");
        assertEquals(2,gameMaze.getMainChar().getY(), "mainCharacter stays");
        assertEquals(false,gameMaze.endTileRevealed,"End tile is not revealed");


        //move into wall
        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(),Direction.DOWN);
        assertFalse(gameMaze.endTileRevealed);
        assertEquals(2,gameMaze.getMainChar().getX(), "mainCharacter stays");
        assertEquals(2,gameMaze.getMainChar().getY(), "mainCharacter stays");
        assertEquals(2, gameMaze.getEventTileList().size());

        //move into end (with exit condition false)
        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(),Direction.RIGHT);
        assertEquals(2,gameMaze.getMainChar().getX(), "mainCharacter stays");
        assertEquals(2,gameMaze.getMainChar().getY(), "mainCharacter stays");

        //move into bonus
        gameMaze.setNotRetiredBonusReward();
        gameMaze.findBonusReward();
        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(),Direction.LEFT);
        assertFalse(gameMaze.endTileRevealed);
        assertEquals(1,gameMaze.getMainChar().getX());
        assertEquals(2,gameMaze.getMainChar().getY());
        assertEquals(1,gameMaze.getLootableList().size(), "lootable list stays the same");
        assertEquals(50,gameMaze.getMainChar().getCharacterScore(),"main character does not get score");
        //reward now exist



        //move into reward
        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(),Direction.RIGHT);
        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(),Direction.UP);
        assertTrue(gameMaze.endTileRevealed);
        assertEquals(2,gameMaze.getMainChar().getX());
        assertEquals(1,gameMaze.getMainChar().getY());
        //TODO 0-->1
        assertEquals(0,gameMaze.getLootableList().size(), "lootable list - 1");
        //TODO 20-->10
        assertEquals(60,gameMaze.getMainChar().getCharacterScore(),"main character + 10 score");
        assertTrue(gameMaze.endTileRevealed);

        //move into end
        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(),Direction.DOWN);
        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(),Direction.RIGHT);
        assertEquals(3,gameMaze.getMainChar().getX());
        assertEquals(2,gameMaze.getMainChar().getY());
    }

    @Test
    public void testEnemy(){
        // X X X X X X
        // X M S . . X
        // X X X . E X
        // X . . . . X
        // X E . . E X
        // X X X X X X

        gameMaze = new Maze( new LevelFromTxt((Assets.LVL_TEST_THREE)));



        assertEquals(gameMaze.getMainChar().toString(), "MainCharacter","object at 1,1's string name is called MainCharacter");


        gameMaze.movingEnemies();
        assertFalse(gameMaze.getCellXY(1,3).getObjectList().isEmpty(),"enemy at 1,4 moved to 1,3");
        assertEquals(100,gameMaze.getMainChar().getHealth(), "static enemy at 2,1 has not moved");
        assertFalse(gameMaze.getCellXY(4,1).getObjectList().isEmpty(),"enemy at 4,2 moved to 4,1");
        assertTrue(gameMaze.getCellXY(4,4).getObjectList().isEmpty(), "enemy at 4,4 moved");
        boolean moved;
        if (!(gameMaze.getCellXY(4,3).getObjectList().isEmpty())||
                !(gameMaze.getCellXY(3,4).getObjectList().isEmpty())){
            moved = true;
        }
        else
            moved = false;
        assertTrue(moved, "Enemy at 4,4 moved either up or left");

        gameMaze.movingEnemies();
        assertFalse(gameMaze.getCellXY(1,3).getObjectList().isEmpty(), "enemy at 1,3 got stuck");
        assertFalse(gameMaze.getCellXY(3,1).getObjectList().isEmpty(), "enemy at 4,1 moved to 3,1");

        gameMaze.movingEnemies();
        assertFalse(gameMaze.getCellXY(1,3).getObjectList().isEmpty(), "enemy at 1,3 still stuck, begin random move next time");
        assertFalse(gameMaze.getCellXY(2,1).getObjectList().isEmpty(), "enemy at 3,1 moved to 2,1");

        gameMaze.movingEnemies();
        if (!(gameMaze.getCellXY(2,3).getObjectList().isEmpty())||
                !(gameMaze.getCellXY(1,4).getObjectList().isEmpty())){
            moved = true;
        }
        else
            moved = false;
        assertTrue(moved, " enemy at 1,3 has moved");
        assertFalse(gameMaze.getCellXY(2,1).getObjectList().isEmpty(), "enemy at 1,4 moved to 1,3");

        gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(), Direction.RIGHT);
        assertEquals(55, gameMaze.getMainChar().getHealth(),"main character collides with enemyA and B");
    }

}
