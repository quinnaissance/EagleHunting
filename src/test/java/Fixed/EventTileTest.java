package Fixed;

import Display.Assets;
import Fixed.EventTile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EventTileTest {

    @Test
    public void testCheckpoint() {
        EventTile myTile = new EventTile(2,3, EventTile.eventType.START_TILE,true);
        int pos_x = myTile.getX();
        int pox_y = myTile.getY();
        assertEquals(2,pos_x);
        assertEquals(3,pox_y);
        assertTrue(myTile.isRevealed());
    }

    @Test
    public void testeventType() {
        EventTile myTile1 = new EventTile(2,3, EventTile.eventType.START_TILE,true);
        EventTile.eventType TheType1 = myTile1.getEventType();
        assertEquals(EventTile.eventType.START_TILE, TheType1);

        EventTile myTile2 = new EventTile(2,3, EventTile.eventType.END_TILE,true);
        EventTile.eventType TheType2 = myTile2.getEventType();
        assertEquals(EventTile.eventType.END_TILE, TheType2);

    }

    @Test
    public void testrevealed() {
        EventTile myTile1 = new EventTile(2,3, EventTile.eventType.START_TILE,true);
        Boolean Therevealed1 = myTile1.isRevealed();
        assertEquals(true, Therevealed1);

        EventTile myTile2 = new EventTile(2,3, EventTile.eventType.START_TILE,false);
        Boolean Therevealed2 = myTile2.isRevealed();
        assertEquals(false, Therevealed2);

        EventTile myTile3 = new EventTile(2,3, EventTile.eventType.START_TILE,false);
        myTile3.reveal();
        Boolean Therevealed3 = myTile3.isRevealed();
        assertEquals(true, Therevealed3);
    }

    @Test
    public void testtoString() {
        EventTile myTile1 = new EventTile(2,3, EventTile.eventType.START_TILE,true);
        String TheString1 = myTile1.toString();
        assertEquals("StartTile",TheString1);

        EventTile myTile2 = new EventTile(2,3, EventTile.eventType.END_TILE,true);
        String TheString2 = myTile2.toString();
        assertEquals("EndTile",TheString2);


    }


}
