package pkgSrUtils;

public class SrPingPongArray extends SrIntArray{

    private final String DEFAULT_OUTPUT_FILE = "src/main/output/Assignment_5_output.txt";
    protected int DEFAULT_VALUE = 0;
    private int MIN_VALUE, MAX_VALUE;
    private int[][] nextCellArray;

    // one constructor takes rows and cols and initializes everything to 0
    // second one takes row col and random int low, random int high don't make this one yet

    public SrPingPongArray(int row, int col){
        arrayData = new int[row][col];
    }
    // second constructor goes in here but for future assignment

    // need default empty constructor for the constructor inside SrGoLArray
    public SrPingPongArray(){}

    protected void swapLiveAndNext(){

    }
    protected void setCell(int row, int col, int val){

    }
    protected int getN2NeighborsSum(int row, int col){

    }
    public void loadFile(String file){
        super.loadFile(file);
    }

}
