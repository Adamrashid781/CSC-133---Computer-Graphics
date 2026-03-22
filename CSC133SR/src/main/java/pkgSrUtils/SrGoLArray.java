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
}
//// creating a record to hold all the neighbors of a cell
//SrRCPair[] myRCP = getNextNearestNeighborsArray(row, col);
//        int cell, aliveNeighbors = 0; ;
//
//        for (SrRCPair myP : myRCP) {
////            out.printf("{%d, %d}, ", myP.myRow(), myP.myCol());
//
//cell = getCell(row, col);
//            if(cell == 1) aliveNeighbors++;
//        }  //  for (SlRCPair myP : myRCP)
//        if(aliveNeighbors == 2 || aliveNeighbors == 3) return true;