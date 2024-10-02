package conwaygame;
import java.util.ArrayList;
/**
* Conway's Game of Life Class holds various methods that will
* progress the state of the game's board through it's many iterations/generations.
*
* Rules
* Alive cells with 0-1 neighbors die of loneliness.
* Alive cells with >=4 neighbors die of overpopulation.
* Alive cells with 2-3 neighbors survive.
* Dead cells with exactly 3 neighbors become alive by reproduction.


* @author Seth Kelley
* @author Maxwell Goldberg
*/
public class GameOfLife {


   // Instance variables
   private static final boolean ALIVE = true;
   private static final boolean  DEAD = false;


   private boolean[][] grid;    // The board has the current generation of cells
   private int totalAliveCells; // Total number of alive cells in the grid (board)


   /**
   * Default Constructor which creates a small 5x5 grid with five alive cells.
   * This variation does not exceed bounds and dies off after four iterations.
   */
   public GameOfLife() {
       grid = new boolean[5][5];
       totalAliveCells = 5;
       grid[1][1] = ALIVE;
       grid[1][3] = ALIVE;
       grid[2][2] = ALIVE;
       grid[3][2] = ALIVE;
       grid[3][3] = ALIVE;
   }


   /**
   * Constructor used that will take in values to create a grid with a given number
   * of alive cells
   * @param file is the input file with the initial game pattern formatted as follows:
   * An integer representing the number of grid rows, say r
   * An integer representing the number of grid columns, say c
   * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
   */

   public GameOfLife (String file) { 
       
        StdIn.setFile(file);

        int row = StdIn.readInt();
        int col = StdIn.readInt();

        totalAliveCells = 0;
        grid = new boolean[row][col];
        
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){

                grid[i][j] = StdIn.readBoolean();

                if (grid[i][j] == ALIVE) {
                    totalAliveCells++;
                }

            }
        }

   }


   /**
    * Returns grid
    * @return boolean[][] for current grid
    */
   public boolean[][] getGrid () {
       return grid;
   }
  
   /**
    * Returns totalAliveCells
    * @return int for total number of alive cells in grid
    */
   public int getTotalAliveCells () {
       return totalAliveCells;
   }


   /**
    * Returns the status of the cell at (row,col): ALIVE or DEAD
    * @param row row position of the cell
    * @param col column position of the cell
    * @return true or false value "ALIVE" or "DEAD" (state of the cell)
    */
   public boolean getCellState (int row, int col) { 
       
        if (grid[row][col] == ALIVE) {
            return ALIVE;
        }

        return DEAD; // update this line, provided so that code compiles
   }


   /**
    * Returns true if there are any alive cells in the grid
    * @return true if there is at least one cell alive, otherwise returns false
    */
   public boolean isAlive () {   /// might change if NOT EACH STEP CHANGES IT?

        totalAliveCells = 0;

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (grid[i][j] == ALIVE) {
                    return true;
                }
            }
        }

        return false; // update this line, provided so that code compiles

   }


   /**
    * Determines the number of alive cells around a given cell.
    * Each cell has 8 neighbor cells which are the cells that are
    * horizontally, vertically, or diagonally adjacent.
    *
    * @param col column position of the cell
    * @param row row position of the cell
    * @return neighboringCells, the number of alive cells (at most 8).
    */
   public int numOfAliveNeighbors (int row, int col) { 

    int aN = 0; // number of alive neighbors

    int r = grid.length;
    int c = grid[0].length;

    if (row == 0){ // first row
        if (col == 0) {

            if (grid[r - 1][c - 1]) { aN++; } //
            if (grid[r - 1][col]) { aN++; } //
            if (grid[r - 1][col + 1]) { aN++; } //

            if (grid[row][c - 1]) { aN++; } //
            if (grid[row][col + 1]) { aN++; } //

            if (grid[row + 1][c - 1]) { aN++; } //
            if (grid[row + 1][col]) { aN++; } //
            if (grid[row + 1][col + 1]) { aN++; } //

        }
        else if (col == c - 1){

            if (grid[r - 1][0]) { aN++; } //
            if (grid[r - 1][col]) { aN++; } //
            if (grid[r - 1][col - 1]) { aN++; } //

            if (grid[row][col - 1]) { aN++; } //
            if (grid[row][0]) { aN++; } //

            if (grid[row + 1][col - 1]) { aN++; } //
            if (grid[row + 1][col]) { aN++; } //
            if (grid[row + 1][0]) { aN++; } //

        }
        else {

            if (grid[r - 1][col - 1]) { aN++; } //
            if (grid[r - 1][col]) { aN++; } //
            if (grid[r - 1][col + 1]) { aN++; } //

            if (grid[row][col - 1]) { aN++; } //
            if (grid[row][col + 1]) { aN++; } //

            if (grid[row + 1][col - 1]) { aN++; } //
            if (grid[row + 1][col]) { aN++; } //
            if (grid[row + 1][col + 1]) { aN++; } //

        }
    }

    else if (row == r - 1){ // last row
            if (col == 0 ){

                if (grid[0][c - 1]) { aN++; } //
                if (grid[0][col]) { aN++; } //
                if (grid[0][col + 1]) { aN++; } //

                if (grid[row][col + 1]) { aN++; } //
                if (grid[row][c - 1]) { aN++; } //

                if (grid[row - 1][c - 1]) { aN++; } //
                if (grid[row - 1][col]) { aN++; } //
                if (grid[row - 1][col + 1]) { aN++; } //

            }
            else if (col == c - 1){

                if (grid[0][0]) { aN++; } //
                if (grid[0][col]) { aN++; } //
                if (grid[0][col - 1]) { aN++; } //

                if (grid[row][0]) { aN++; } //
                if (grid[row][col - 1]) { aN++; } //

                if (grid[row - 1][0]) { aN++; } //
                if (grid[row - 1][col]) { aN++; } //
                if (grid[row - 1][col - 1]) { aN++; } //

            }
            else {

                if (grid[0][col + 1]) { aN++; } //
                if (grid[0][col]) { aN++; } //
                if (grid[0][col - 1]) { aN++; } //

                if (grid[row][col + 1]) { aN++; } //
                if (grid[row][col - 1]) { aN++; } //

                if (grid[row - 1][col + 1]) { aN++; } //
                if (grid[row - 1][col]) { aN++; } //
                if (grid[row - 1][col - 1]) { aN++; } //

            }

        }
        else if (col == 0){ // first col (not row 0 or last)
            
            if (grid[row + 1][c - 1]) { aN++; } //
            if (grid[row + 1][col]) { aN++; } //
            if (grid[row + 1][col + 1]) { aN++; } //

            if (grid[row][c - 1]) { aN++; } //
            if (grid[row][col + 1]) { aN++; } //

            if (grid[row - 1][c - 1]) { aN++; } //
            if (grid[row - 1][col]) { aN++; } //
            if (grid[row - 1][col + 1]) { aN++; } //

        }
        else if (col == c - 1){ // last col (not row 0 or last)
            
            if (grid[row + 1][0]) { aN++; } //
            if (grid[row + 1][col]) { aN++; } //
            if (grid[row + 1][col - 1]) { aN++; } //

            if (grid[row][0]) { aN++; } //
            if (grid[row][col - 1]) { aN++; } //

            if (grid[row - 1][0]) { aN++; } //
            if (grid[row - 1][col]) { aN++; } //
            if (grid[row - 1][col - 1]) { aN++; } //

        }
        else { // anywhere else (middle)

            if (grid[row - 1][col - 1]) { aN++; } //
            if (grid[row - 1][col]) { aN++; } //
            if (grid[row - 1][col + 1]) { aN++; } //

            if (grid[row][col - 1]) { aN++; } //
            if (grid[row][col + 1]) { aN++; } //

            if (grid[row + 1][col - 1]) { aN++; } //
            if (grid[row + 1][col]) { aN++; } //
            if (grid[row + 1][col + 1]) { aN++; } //

        }



        
       return aN; // update this line, provided so that code compiles
   }



   /**
    * Creates a new grid with the next generation of the current grid using
    * the rules for Conway's Game of Life.
    *
    * @return boolean[][] of new grid (this is a new 2D array)
    */
   public boolean[][] computeNewGrid () { //////////////////////////////////

    boolean[][] newGrid = new boolean[grid.length][grid[0].length];

    for (int i = 0; i < grid.length; i++){
        for (int j = 0; j < grid[i].length; j++){

            int neighbors = numOfAliveNeighbors(i, j);

            if (grid[i][j] == ALIVE){

                if (neighbors == 0 || neighbors == 1){
                    newGrid[i][j] = DEAD;
                }
                if (neighbors == 2 || neighbors == 3){
                    newGrid[i][j] = ALIVE;
                }
                if (neighbors >= 4){
                    newGrid[i][j] = DEAD;
                }

            }
            else if (neighbors == 3){
                newGrid[i][j] = ALIVE;
            }
        }
    }
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                grid[i][j] = newGrid[i][j];
            }
        }
       return grid;// update this line, provided so that code compiles
   }


   /**
    * Updates the current grid (the grid instance variable) with the grid denoting
    * the next generation of cells computed by computeNewGrid().
    *
    * Updates totalAliveCells instance variable
    */
   public void nextGeneration () { ////////////////////////////////// call compute new

        computeNewGrid();

        totalAliveCells = 0;  // ???? y can i see

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){

                if (grid[i][j] == ALIVE) {
                    totalAliveCells++;
                }

            }
        }

   }


   /**
    * Updates the current grid with the grid computed after multiple (n) generations.
    * @param n number of iterations that the grid will go through to compute a new grid
    */
   public void nextGeneration (int n) { //////////////////////////////////  n of generations
        for (int i = 0; i < n; i++){
            nextGeneration();
        }
   }


   /**
    * Determines the number of separate cell communities in the grid
    * @return the number of communities in the grid, communities can be formed from edges
    */
   public int numOfCommunities() { //////////////////////////////////

        int rows = grid.length;
        int cols = grid[0].length;
        
        //int numCells = rows * cols;
        //int communities = 0;

        ArrayList <Integer> numCommunities = new ArrayList<>();
        WeightedQuickUnionUF id = new WeightedQuickUnionUF (rows, cols);

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                
                if (grid[i][j] == ALIVE && numOfAliveNeighbors(i, j) > 0){ // all alive cells with neighbors
                    
                    for (int k = 0; k < rows; k++){
                        for (int l = 0; l < cols; l++){

                            if (isAliveNeighbor(i, j, k, l) && (id.find(i, j) != id.find(k, l))){ 
                                id.union(i, j, k, l); 
                            }

                        }
                    }

                }

            }
        }

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){

                int parent = id.find(i, j);
                if (grid[i][j] == ALIVE){

                    if (!numCommunities.contains(parent)){

                        numCommunities.add(parent);

                    }

                }

            }
        }

       return numCommunities.size(); // update this line, provided so that code compiles 
   }

   private boolean isAliveNeighbor(int row1, int col1, int row2, int col2){

        if (grid[row2][col2] == DEAD){
            return false;
        }

        boolean rowNeighbor = false;
        boolean colNeighbor = false;
        
        if (Math.abs(row1 - row2) <= 1 || Math.abs(row1 - row2) == (grid.length - 1)){ 
            rowNeighbor = true; 
        }

        if (Math.abs(col1 - col2) <= 1 || Math.abs(col1 - col2) == (grid[0].length - 1)){ 
            colNeighbor = true; 
        }

        return rowNeighbor && colNeighbor;

   }


}

