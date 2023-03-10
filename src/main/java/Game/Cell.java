package Game;

import Fixed.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Cell object that will contain MazeObjects
 */
public class Cell {

    private int x;
    private int y;
    private List<MazeObject> list;
    private boolean isSpace;
    private Wall cellWall;

    /**
     * Constructor
     * @param x x-pos
     * @param y y-pos
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        list = new ArrayList<>();

        // Default: cell is permeable
        cellWall = new Wall(x, y);
        isSpace = true;
        cellWall.setPermeable(true);
    }

    /**
     * Get x-position
     * @return x-pos
     */
    public int getX() {
        return x;
    }

    /**
     * Get y-position
     * @return y-pos
     */
    public int getY() {
        return y;
    }

    /**
     * Add an object to the list
     * @param obj
     */
    public void addObject(MazeObject obj) {
        obj.setX(getX());
        obj.setY(getY());
        list.add(obj);
    }

    /**
     * Remove an object from the list and return it
     * @param obj MazeObject
     * @return MazeObject
     */
    public MazeObject removeObject(MazeObject obj) {
        MazeObject dummy = getObject(obj);
        list.remove(getObject(obj));
        return dummy;
    }

    /**
     * Get a particular object from the list
     * @param obj MazeObject
     * @return MazeObject
     */
    public MazeObject getObject(MazeObject obj) {
        if (contains(obj))
            return list.get(list.indexOf(obj));
        return null;
    }

    /**
     * Check if a particular object is contained in the list
     * @param obj MazeObject
     * @return true/false
     */
    public boolean contains(MazeObject obj) {
        return list.contains(obj);
    }

    /**
     * Get an object with a specific type (uses object toString)
     * @param type Object type as String
     * @return Objects of that type
     */
    public List<MazeObject> getObjectOfType(String type) {
        List<MazeObject> newList = new ArrayList<MazeObject>();
        for (MazeObject obj : list) {
            if (obj.getClass().getSimpleName().toLowerCase().contains(type.toLowerCase())) {
                newList.add(obj);
            }
        }
        return newList;
    }

    /**
     * Check Cell for a Wall object that is not permeable
     *
     * @return
     */
    public boolean hasImpermeableWall() {
        return !cellWall.isPermeable();
    }

    /**
     * Check if object is a space
     * @return
     */
    public boolean isSpace() {
        return this.isSpace;
    }

    /**
     * Get object's Wall object
     * @return
     */
    public Wall getCellWall() {
        return cellWall;
    }

    /**
     * Set object's space value
     * @param spaceVal
     */
    public void setSpace(boolean spaceVal) {
        // If setting to space, Wall must become permeable
        if (spaceVal) {
            cellWall.setPermeable(true);
        } else {
            cellWall.setPermeable(false);
        }
        this.isSpace = spaceVal;
    }

    /**
     * Get list of objects contained in this Cell
     * @return list
     */
    public List<MazeObject> getObjectList() {
        return list;
    }

}
