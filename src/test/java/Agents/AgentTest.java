package Agents;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AgentTest {

    @Test
    public void testIsMovable(){
        Enemy movableAgent = new Enemy(0,0, 0, false,0);
        Enemy unmovableAgent = new Enemy(1,1,10,true,20);
        MainCharacter mainCharacter = new MainCharacter(2,2);
        assertEquals(true,unmovableAgent.isMovable(),"Agent is not movable");
        assertEquals(false,movableAgent.isMovable(),"Agent is movable");
    }

    @Test
    public void testGetMoveSpeed(){
        Enemy movableAgent = new Enemy(0,0, 0, false,0);
        Enemy unmovableAgent = new Enemy(1,1,10,true,20);
        MainCharacter mainCharacter = new MainCharacter(2,2);
        assertEquals(10, unmovableAgent.getMoveSpeed(),"Agent has 0 speed");
        assertEquals(0, movableAgent.getMoveSpeed(),"agent hs 10 speed");
    }
}