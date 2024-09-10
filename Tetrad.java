import java.awt.*;
import java.util.Random;

public class Tetrad {
    private Block[] blocks;

    public Tetrad(BoundedGrid<Block> grid) {

        blocks = new Block[4];
        Color color; // Step 2 (Exercise 3)
        Location[] locs = new Location[4]; // Step 3 (Exercise 3)
        int shape = 0; // will eventually be replaced with a random number (Exercise 4)
        shape = (int) (Math.random() * 7);

        // Step 5 and 6 (Exercise 3)
        if (shape == 0) {
            color = Color.RED;
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 3);
            locs[2] = new Location(0, 5);
            locs[3] = new Location(0, 6);
        } else if (shape == 1) {
            color = Color.GRAY;
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 3);
            locs[2] = new Location(0, 5);
            locs[3] = new Location(1, 4);
        } else if (shape == 2) {
            color = Color.CYAN;
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 5);
            locs[2] = new Location(1, 4);
            locs[3] = new Location(1, 5);
        } else if (shape == 3) {
            color = Color.YELLOW;
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 3);
            locs[2] = new Location(0, 5);
            locs[3] = new Location(1, 3);
        } else if (shape == 4) {
            color = Color.MAGENTA;
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 3);
            locs[2] = new Location(0, 5);
            locs[3] = new Location(1, 5);
        } else if (shape == 5) {
            color = Color.BLUE;
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 5);
            locs[2] = new Location(1, 3);
            locs[3] = new Location(1, 4);
        } else if (shape == 6) {
            color = Color.GREEN;
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 3);
            locs[2] = new Location(1, 4);
            locs[3] = new Location(1, 5);
        } else
            throw new RuntimeException("bad shape number:  " + shape);

        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block();
            blocks[i].setColor(color); // Needed?
        }
        addToLocations(grid, locs);
    }

    // precondition: blocks are not in any grid;
    // locs.length = 4.
    // postcondition: The locations of blocks match locs,
    // and blocks have been put in the grid.
    private void addToLocations(BoundedGrid<Block> grid, Location[] locs) {
        for (int i = 0; i < locs.length; i++) {
            blocks[i].putSelfInGrid(grid, locs[i]);
        }
    }

    // precondition: Blocks are in the grid.
    // postcondition: Returns old locations of blocks;
    // blocks have been removed from grid.
    private Location[] removeBlocks() {

        Location[] past = new Location[blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            past[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return past;
    }

    // postcondition: Returns true if each of locs is
    // valid (on the board) AND empty in
    // grid; false otherwise.
    private boolean areEmpty(BoundedGrid<Block> grid, Location[] locs) {
        for (Location loc : locs) {
            if (grid.isValid(loc) == false || grid.get(loc) != null) {
                return false;
            }
        }
        return true;
    }

    // postcondition: Attempts to move this tetrad deltaRow
    // rows down and deltaCol columns to the
    // right, if those positions are valid
    // and empty; returns true if successful
    // and false otherwise.
    public boolean translate(int deltaRow, int deltaCol) {
        BoundedGrid<Block> grid;

        grid = blocks[0].getGrid();

        Location[] oldLocs = removeBlocks();
        Location[] newLocs = new Location[blocks.length];

        for (int i = 0; i < newLocs.length; i++)
            newLocs[i] = new Location(oldLocs[i].getRow() + deltaRow, oldLocs[i].getCol() + deltaCol);

        if (areEmpty(grid, newLocs) == true) {
            addToLocations(grid, newLocs);
            return true;
        } else {
            addToLocations(grid, oldLocs);
            return false;
        }
    }

    // postcondition: Attempts to rotate this tetrad
    // clockwise by 90 degrees about its
    // center, if the necessary positions
    // are empty; returns true if successful
    // and false otherwise.
    public boolean rotate() {
        BoundedGrid<Block> grid;
        grid = blocks[0].getGrid();

        Location[] oldLocs = removeBlocks();
        Location[] newLocs = new Location[blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            newLocs[i] = new Location(oldLocs[0].getRow() - oldLocs[0].getCol() + oldLocs[i].getCol(),
                    oldLocs[0].getRow() + oldLocs[0].getCol() - oldLocs[i].getRow());
        }

        if (areEmpty(grid, newLocs) == true) {
            addToLocations(grid, newLocs);
            return true;
        } else {
            addToLocations(grid, oldLocs);
            return false;
        }
    }
}