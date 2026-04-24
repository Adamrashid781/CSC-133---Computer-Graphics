package pkgSrUtils;

public class SrPingPongArray extends SrIntArray{

    private final String DEFAULT_OUTPUT_FILE = "src/main/output/Assignment_5_output.txt";
    protected final int DEFAULT_VALUE = 0;
    private int MIN_VALUE, MAX_VALUE;
    private int[][] nextCellArray;

    // one constructor takes rows and cols and initializes everything to 0
    // second one takes row col and random int low, random int high don't make this one yet

    public SrPingPongArray(int row, int col){
        arrayData = new int[row][col];
        nextCellArray = new int[row][col];
    }
    // second constructor goes in here but for future assignment

    // need default empty constructor for the constructor inside SrGoLArray
    public SrPingPongArray(){
        super();
    }

    protected void swapLiveAndNext(){
        int[][] tmp ;
        tmp = arrayData;
        arrayData = nextCellArray;
        nextCellArray = tmp;
    }

    protected void setCell(int row, int col, int val){
        nextCellArray[row][col] = val;
    }
    protected int getN2NeighborsSum(int row, int col){
        // creating a record to hold all the neighbors of a cell
        SrRCPair[] myRCP = getNextNearestNeighborsArray(row, col);
                int cell, aliveNeighbors = 0; ;

                for (SrRCPair myP : myRCP) {
                    // out.printf("{%d, %d}, ", myP.myRow(), myP.myCol());
                     cell = getCell(myP.myRow(), myP.myCol());
                    if(cell == 1) aliveNeighbors++;
                }  //  for (SlRCPair myP : myRCP)
                return aliveNeighbors;
    }
    public void loadFile(String file){
        super.loadFile(file);
        nextCellArray = new int[arrayData.length][arrayData[0].length];
    }

}
