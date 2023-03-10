package Fixed;

import Fixed.Wall;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Wall object
 */
public class WallTest {


    /**
     * Test coordinates of Wall
     */
    @Test
    public void testCoordinates() {
        Wall exampleWall = new Wall(6,3, false);
        Wall exampleWallDefaultConstructor = new Wall(1,2);
        // Test wall
        assertEquals(6, exampleWall.getX(), "Wall x value is 6");
        assertEquals(3, exampleWall.getY(), "Wall y value is 3");
        exampleWall.setX(12);
        exampleWall.setY(4);
        assertEquals(12, exampleWall.getX(), "Wall x value is 12");
        assertEquals(4, exampleWall.getY(), "Wall y value is 4");

        // Test default constructor wall
        assertEquals(1, exampleWallDefaultConstructor.getX(), "Wall x value is 1");
        assertEquals(2, exampleWallDefaultConstructor.getY(), "Wall y value is 2");
        exampleWallDefaultConstructor.setX(0);
        exampleWallDefaultConstructor.setY(0);
        assertEquals(0, exampleWallDefaultConstructor.getX(), "Wall x value is 0");
        assertEquals(0, exampleWallDefaultConstructor.getY(), "Wall y value is 0");
    }

    /**
     * Test Wall permeability
     */
    @Test
    public void testPermeability() {
        Wall exampleWall = new Wall(6,3, false);
        Wall exampleWallDefaultConstructor = new Wall(1,2);
        // Test wall
        assertFalse(exampleWall.isPermeable(), "Wall is impermeable");
        exampleWall.setPermeable(true);
        assertTrue(exampleWall.isPermeable());

        // Test default constructor wall
        assertTrue(exampleWallDefaultConstructor.isPermeable(), "Default constructor wall is permeable");
        exampleWallDefaultConstructor.setPermeable(false);
        assertFalse(exampleWallDefaultConstructor.isPermeable(), "Default constructor wall is NOT permeable");
    }
}
