package pkgSrTTTBackend;

public class SrTTTBoard {
    int[][] cornerCells;
    int[][] sideCells ;
    private char[][] tttBoard = {
            {'_', '_', '_'},
            {'_', '_', '_'},
            {'_', '_', '_'}
    };

    void SrTTTBoard(){

    }




    protected void clearBoard(){
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                tttBoard[col][row] = '_';
            }
        }
    }

    protected char[][] getBoard(){

    }

    protected char getContent(int, int){

    }

    protected boolean setContent(int, int, char ){

    }
}
