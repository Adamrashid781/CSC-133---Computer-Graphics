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
                {0, 2},
                {2, 0},
                {2, 2}
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
                tttBoard[row][col] = '_';
            }
        }
    }

    // to protect the board from being accessed, i copy it to a temporary board
    // and i return the copy
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
    protected char getContent(int col, int row){
        return tttBoard[row][col];

    }
    // Sets the value at the specified [][] index
    protected boolean setContent(int row, int col, char mp ){
        if(tttBoard[row][col] == '_'){
            tttBoard[row][col] = mp;
            return true;
        }
        else {
            out.print("That spot is taken already! Try a new one");
            return false;

        }

    }
}
