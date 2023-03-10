package Agents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void testGetVal(){
        Direction right = Direction.RIGHT;
        Direction left = Direction.LEFT;
        Direction down = Direction.DOWN;
        Direction up = Direction.UP;
        Direction stay = Direction.STAY;
        assertEquals(1,right.getVal(),"right has value of 1");
        assertEquals(-1, left.getVal(),"left has value of -1");
        assertEquals(1, down.getVal(), "down has value of 1");
        assertEquals(-1, up.getVal(), "up has value of -1");
        assertEquals(0, stay.getVal(),"stay has value of 0");
    }
}
