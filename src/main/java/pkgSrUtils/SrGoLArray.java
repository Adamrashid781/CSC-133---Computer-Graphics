package pkgSrUtils;

import static java.lang.System.out;

public class SrGoLArray extends SrPingPongArray{
    private final int ALIVE = 1;
    private final int DEAD = 0;

    public SrGoLArray(int row, int col){
        super(row, col);
    }
    public SrGoLArray(){}

    // returns to pingPongArray if a cell is alive or dead. that's it
    public boolean isCellAlive(int row, int col){
        return arrayData[row][col] == ALIVE;
    }

    public void onTickUpdate(){
        // hold the count of how many neighbors are alive
        int count = 0;
        // 1. need to set all values in NEXT to DEFAULT_VALUE
        for(int row = 0; row < arrayData.length; row++) {
            for (int col = 0; col < arrayData[0].length; col++) {
                setCell(row, col, DEFAULT_VALUE);
            }
        }

        // 2. loop for getting the number live neighbors in each cell and
        // 3. setting cell values in NEXT array
        for(int row = 0; row < arrayData.length; row++){
            for(int col = 0; col < arrayData[0].length; col++){
                // getting LIVE count
                count = getN2NeighborsSum(row, col);

                // setting all cell values
                // A. Survival: # Cell is alive & # aliveNeighbors == 2 or # aliveNeighbors  = 3
                if((count == 2 || count ==3) && isCellAlive(row, col)) setCell(row, col, ALIVE);

                // B. Dead due to overpopulation: # aliveNeighbors  > 3
                if(count > 3)  setCell(row, col, DEFAULT_VALUE);

                // C. Dead due to loneliness: # aliveNeighbors  < 2
                if(count < 2) setCell(row, col, DEFAULT_VALUE);

                // D. Reproduce: Now dead and # aliveNeighbors  == 3
                if(arrayData[row][col] == DEFAULT_VALUE && count == 3) setCell(row, col, ALIVE);
                

            }
        }


        // 4. Final part of method - swap arrays
        swapLiveAndNext();

    } // End onTickUpdate
}
