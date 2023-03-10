package Fixed;

/**
 * Tiles that trigger special events
 * example: Start tiles, End tiles
 * enum to handle type
 */
public class EventTile extends Wall {
    private Boolean revealed;
    private eventType type;

    public enum eventType { START_TILE, END_TILE };

    /**
     * Constructor
     * @param x x-pos
     * @param y y-pos
     * @param type START_TILE or END_TILE
     * @param visible visibility status
     */
    public EventTile(int x, int y, eventType type, Boolean visible) {
        super(x,y,true); // EventTile is walkable
        this.type = type;
        this.revealed = visible;
    }

    /**
     * Reveal the tile
     */
    public void reveal() {
        revealed = true;
    }

    /**
     * Check revealed status
     * @return
     */
    public Boolean isRevealed() {
        return revealed;
    }

    /**
     * Get the event type
     * @return enum
     */
    public eventType getEventType() {
        return type;
    }

    /**
     * Overrided toString to differentiate StartTile/EndTile
     * @return String
     */
    @Override
    public String toString() {
        if (type == eventType.START_TILE)
            return "StartTile";
        else
            return "EndTile";
    }
}
