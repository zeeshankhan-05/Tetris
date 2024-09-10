public class Tetris implements ArrowListener {
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.play();
    }

    private BoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad activeTetrad;

    public Tetris() {
        grid = new BoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        display.setArrowListener(this);
        activeTetrad = new Tetrad(grid);
        display.showBlocks();
    }

    public void upPressed() {
        activeTetrad.rotate();

        display.showBlocks();
    }

    public void downPressed() {
        activeTetrad.translate(1, 0);

        display.showBlocks();
    }

    public void leftPressed() {
        activeTetrad.translate(0, -1);

        display.showBlocks();
    }

    public void rightPressed() {
        activeTetrad.translate(0, 1);

        display.showBlocks();
    }

    public void play() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            if (activeTetrad.translate(1,0)) {
                    display.showBlocks();
            } else {
                clearCompletedRows();
                activeTetrad = new Tetrad(grid);
                display.showBlocks();
            }

            display.showBlocks();
        }
    }

    // precondition: 0 <= row < number of rows
    // postcondition: Returns true if every cell in the
    // given row is occupied;
    // returns false otherwise.
    private boolean isCompletedRow(int row) {
        for (int i = 0; i < grid.getNumCols(); i++) {
            Location loc = new Location(row, i);
            if (grid.get(loc )== null) {
                return false;
            }
        }
        return true;
    }

    // precondition: 0 <= row < number of rows;
    // given row is full of blocks
    // postcondition: Every block in the given row has been
    // removed, and every block above row
    // has been moved down one row.
    private void clearRow(int row) {
        for (int i = 0; i < grid.getNumCols(); i++) {
            Location loc = new Location(row, i);
            Block bl = grid.get(loc);
            bl.removeSelfFromGrid();
        }
        for (int i = row-1; i >= 0; i--) {
            for (int j = 0; j < grid.getNumCols(); j++) {
                Location loc = new Location(i, j);
                if (grid.get(loc) != null) {
                    Location newLoc = new Location(i + 1, j);
                    grid.get(loc).moveTo(newLoc);
                }   
            }
        }
    }

    // postcondition: All completed rows have been cleared.
    private void clearCompletedRows() {
        for (int i = 0; i < grid.getNumRows(); i++) {
            if (isCompletedRow(i)) {
                clearRow(i);
            }
        }
    }
}