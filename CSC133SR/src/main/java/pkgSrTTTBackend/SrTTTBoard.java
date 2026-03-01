package pkgSrTTTBackend;
import static java.lang.System.out;

public class SrTTTBoard {
    int[][] cornerCells;
    int[][] sideCells ;
    private char[][] tttBoard = new char[SrTTTSPOT.NUM_ROWS][SrTTTSPOT.NUM_COLS];

    void SrTTTBoard(){
        clearBoard();
        cornerCells = new int[][]{
                {0,0},
                {0, SrTTTSPOT.NUM_COLS - 1},
                {SrTTTSPOT.NUM_ROWS - 1, 0},
                {SrTTTSPOT.NUM_ROWS - 1, SrTTTSPOT.NUM_COLS - 1}
        };
        sideCells = new int[][]{
                {1, 0},
                {0, 1},
                {1, 2},
                {2, 1}
        };
    }

// resets the board after every game to ensure no values are stored
    protected void clearBoard(){
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                tttBoard[row][col] = SrTTTSPOT.DEFAULT_CHAR;
            }
        }
    }

    // to protect the board from being accessed, i copy it to a temporary board
    // and return the copy
    protected char[][] getBoard(){
        int r = SrTTTSPOT.NUM_ROWS;
        int c = SrTTTSPOT.NUM_COLS;
        char[][] copy = new char[r][c];
        for(int row = 0; row < r; row++){
            for(int col = 0; col < c; col++){
                copy[row][col] = tttBoard[row][col];
            }
        }

        return copy;
    }
    // returns the value at the specific [][] index
    protected char getContent(int row, int col){
        return tttBoard[row][col];

    }
    // Sets the value at the specified [][] index
    protected boolean setContent(int row, int col, char mp ){
        // validate indices
        if(row < 0 || row >= SrTTTSPOT.NUM_ROWS || col < 0 || col >= SrTTTSPOT.NUM_COLS){
            return false;
        }
        if(tttBoard[row][col] == SrTTTSPOT.DEFAULT_CHAR){
            tttBoard[row][col] = mp;
            return true;
        }
        return false; // meaning cell is already taken
    }

    public boolean Get() {
    }
}
