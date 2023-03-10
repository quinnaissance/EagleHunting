package Game;

import Agents.Enemy;
import Game.Cell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Game.Cell class
 */
public class CellTest {



    /**
     * Test toggling of cell wall permeability
     */
    @Test
    public void testPermeability() {
        Cell exampleCell = new Cell(1,3);
        assertTrue(exampleCell.isSpace(), "Cell contains permeable wall");
        exampleCell.setSpace(false);
        assertFalse(exampleCell.isSpace(), "Cell is not space");
        assertFalse(exampleCell.getCellWall().isPermeable(), "Cell contains impermeable wall");
        assertTrue(exampleCell.hasImpermeableWall(), "Cell has impermeable wall");
        exampleCell.setSpace(true);
        assertFalse(exampleCell.hasImpermeableWall(), "Cell wall is permeable");
    }

    /**
     * Test getting coordinates and that they match what was instantiated
     */
    @Test
    public void testCoords() {
        Cell exampleCell = new Cell(1,3);
        assertEquals(1, exampleCell.getX(), "Cell x value is 0");
        assertEquals(3, exampleCell.getY(), "Cell x value is 0");
    }

    /**
     * Test object list navigation
     */
    @Test
    public void testObjectList() {
        Cell exampleCell = new Cell(1,3);
        assertNotNull(exampleCell.getObjectList());
        Enemy nme1 = new Enemy(0,0,1,false,30);
        exampleCell.addObject(nme1);
        assertTrue(exampleCell.getObjectList().size() == 1);
        assertNotNull(exampleCell.getObject(nme1));
        assertTrue(exampleCell.contains(nme1));
        assertFalse(exampleCell.getObjectOfType("Enemy").isEmpty());
        exampleCell.removeObject(nme1);
        assertNull(exampleCell.getObject(nme1));
        assertTrue(exampleCell.getObjectList().isEmpty());
    }


}
