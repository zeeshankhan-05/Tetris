import java.awt.Color;

/**
 * A Block is a colored entity in a grid.
 */
public class Block
{
    private BoundedGrid<Block> grid;
    private Location location;
    private Color color;

    /**
     * Constructs a blue block.
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * Gets the color of this block.
     * @return the color of this block
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Sets the color of this block.
     * @param newColor the new color
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Gets the grid in which this block is located.
     * @return the grid of this block, or null if this block is
     * not contained in a grid
     */
    public BoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * Gets the location of this block.
     * @return the location of this block, or null if this block is
     * not contained in a grid
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Puts this block into a grid. If there is another block at the given
     * location, it is removed.
     * Precondition: (1) This block is not contained in a grid (2)
     * loc is valid in gr
     * @param gr the grid into which this block should be placed
     * @param loc the location into which the block should be placed
     */
    public void putSelfInGrid(BoundedGrid<Block> gr, Location loc)
    {
        if (grid != null)
            throw new IllegalStateException(
                    "This block is already contained in a grid.");

        Block block = gr.get(loc);
        if (block != null)
            block.removeSelfFromGrid();
        gr.put(loc, this);
        grid = gr;
        location = loc;
    }

    /**
     * Removes this block from its grid.
     * Precondition: This block is contained in a grid
     */
    public void removeSelfFromGrid()
    {
        if (grid == null)
            throw new IllegalStateException(
                    "This block is not contained in a grid.");
        if (grid.get(location) != this)
            throw new IllegalStateException(
                    "The grid contains a different block at location "
                            + location + ".");

        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * Moves this block to a new location. If there is another block at the
     * given location, it is removed.
     * Precondition: (1) This block is contained in a grid (2)
     * newLocation is valid in the grid of this block
     * @param newLocation the new location
     */
    public void moveTo(Location newLocation)
    {
        if (grid == null)
            throw new IllegalStateException("This block is not in a grid.");
        if (grid.get(location) != this)
            throw new IllegalStateException(
                    "The grid contains a different block at location "
                            + location + ".");
        if (!grid.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");

        if (newLocation.equals(location))
            return;
        grid.remove(location);
        Block other = grid.get(newLocation);
        if (other != null)
            other.removeSelfFromGrid();
        location = newLocation;
        grid.put(location, this);
    }

    /**
     * Creates a string that describes this block.
     * @return a string with the location and color of this block
     */
    public String toString()
    {
        return getClass().getName() + "[location=" + location + ",color=" + color + "]";
    }
}