package pkgSrTTTBackend;


public class SrMachinePlayer {
    // Class instance variables
    private SrTTTBoard myBoard ;
    private SrIOManager IO;
    private int gameStatus;

    // Constructor
    public SrMachinePlayer(){
        // create instance of the board and set the game status to NOT STARTED
        myBoard = new SrTTTBoard();
        IO = new SrIOManager();
        gameStatus = SrTTTSPOT.NOT_STARTED ;
    }

    // Class Methods
    // All PLAY methods will look for the default char to place a char there
    private boolean playTheCol(int col){
        // looking for empty space in the column, to put machine char there
        // for loop to check the rows in the col, if def char is found, set pos to machine char on the first find then break out of loop
        // if true call startMovePlay()
        int countMachine = 0;
        int emptyRow = -1;
        char current ;

        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            current = myBoard.getContent(row, col);

            if(current == SrTTTSPOT.MACHINE_CHAR) countMachine++;
            else if (current == SrTTTSPOT.DEFAULT_CHAR) emptyRow = row;
        }
        // if exaclty 2 M's and 1 empty spot
        if(countMachine == SrTTTSPOT.NUM_ROWS - 1 && emptyRow != -1){
            myBoard.setContent(emptyRow, col, SrTTTSPOT.MACHINE_CHAR);
            return true;
        }

        return false;
    }

    private boolean midGamePlay(){
        // 1 Try to win
        if(playToWin()){
            return true;
        }
        // 2 Take center if free
        if(takeCenter()){
            return true;
        }
        // 3. block player win
        if(preventWin()){
            return true;
        }
        // 4. Block Fork
        if(blockFork()){
            return true;
        }
        // 5. Create fork
        if(createFork()){
            return true;
        }
        // 6. Try leading diagonal
        if(playLDiag()){
            return true;
        }
        // 7. Try Trailing diagonal
        if(playTDiag()){
            return true;
        }
        // 8. Take any side
        if(takeAnySide()){
            return true;
        }
        // 9. Take other corner
        if(takeOtherCorner()){
            return true;
        }
        // no move made
        return false;
    }

    private int[] getUserInput(){
        while(true){
            int[] input = IO.readIntegerInput();

            // Quit
            if(input.length == 1 && input[0] == SrTTTSPOT.GAME_QUIT){
                gameStatus = SrTTTSPOT.GAME_QUIT;
                return new int[]{gameStatus};
            }

            // Invalid input letter or format
            if(input.length == 1 && input[0] == SrTTTSPOT.INVALID_INPUT){
                IO.invalidEntryMessage();
                continue;
            }
            int row = input[0];
            int col = input[1];

            // Checking user input is within bounds
            if(!myBoard.setContent(row, col, SrTTTSPOT.PLAYER_CHAR)){
                IO.cellNotFreeMessage(row, col);
                continue;
            }

            // This is a valid move and board will update
            return input;

        }
        // end while loop
    }

    private void runFirstRound(){
    ///  should call getUserInput?

    }

    private int findRepeatsInCol( int col, char mp){
        int countMark = 0;
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            if(myBoard.getContent(row, col) == mp) countMark++;
        }
        return countMark;
    }

    public void playAgainMessage(){
        IO.playAgainMessage();
    }

    private boolean takeCenter(){
        if(myBoard.getContent(1, 1) == SrTTTSPOT.DEFAULT_CHAR){
            myBoard.setContent(1,1, SrTTTSPOT.MACHINE_CHAR);
            return true;
        }
        return false;
    }

    private int findRepeatsLDiagonal(char mp){
        int count = 0;
        int col = 0;
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            if(myBoard.getContent(row, col) == mp) count++;
            col++;
        }
        return count;
    }

    private boolean createFork(){

        return false;
    }

    public int play(){

        return -1;
    }

    private boolean takeOtherCorner(){

        return false;
    }

    private int findRepeatsInRow(int row, char mp){
        int countMark = 0;
        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            if(myBoard.getContent(row, col) == mp) countMark++;
        }
        return countMark;
    }

    private void runMidGame(){

    }

    private void startMovePlay(int row, int col){

    }

    private boolean blockFork(){

        return false;
    }

    public void clearBoard(){
//        SrTTTBoard.clearBoard();
        myBoard.clearBoard();
    }

    private int findRepeatsTDiagonal(char mp){
        // counts how many chars are in
        // used in prevent win to see if player has 2 chars in the row, diag, col
        int count = 0;
        int col = SrTTTSPOT.NUM_COLS - 1;
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            if(myBoard.getContent(row, col) == mp) count++;
            col--;
        }
        return count;
    }
    private boolean takeAnySide(){

        return false;
    }

    // returns true if all the columns in the specified row are default char
    private boolean isRowBlank(int row){
        char[][] temp = myBoard.getBoard();
        return temp[row][0] == SrTTTSPOT.DEFAULT_CHAR && temp[row][1] == SrTTTSPOT.DEFAULT_CHAR && temp[row][2] == SrTTTSPOT.DEFAULT_CHAR;
    }

    private int getGameStatus(){

        return -1;
    }

    protected int isGameOver(){

        return -1;
    }
    private boolean playLDiag(){

        return false;
    }

    private boolean playTDiag(){

        return false;
    }
    private boolean playToWin(){
        // its the same as preventWin() but only looks for machine char and places it in empty spot for the win
        // Check diagonals and play if possible
        if(playTDiag()) return true;
        if(playLDiag()) return true;

        // Check row and play if possible
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            if(playTheRow(row)) return true;
        }
        // Check col and play if possible
        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            if(playTheRow(col)) return true;
        }

        return false;
    }

    private boolean preventWin(){
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row ++){
            if(blockRow(row)) return true;
        }
        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            if(blockCol(col)) return true;
        }
        if(blockLDiag()) return true;
        if(blockTDiag()) return true;
        return false;
    }

    private boolean playTheRow(int row){

        return false;
    }

    private void printGameOverMessage(int status){
        // print certain message based on game status (GAME_PLAYER, GAME_MACHINE, GAME_DRAW)
        if(status == SrTTTSPOT.GAME_PLAYER) {
            IO.playerWinMessage();
        } else if(status == SrTTTSPOT.GAME_MACHINE){
            IO.machineWinMessage();
        } else if (status == SrTTTSPOT.GAME_DRAW) {
            IO.gameDrawMessage();
        }
    }
    private boolean blockRow(int row){
        int countPlayer = 0;
        int emptyCol = -1;
        char current ;

        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            current = myBoard.getContent(row, col);
            if(current == SrTTTSPOT.PLAYER_CHAR) countPlayer ++;
            else if(current == SrTTTSPOT.DEFAULT_CHAR) emptyCol = col;
        }

        if(countPlayer == SrTTTSPOT.NUM_COLS - 1 && emptyCol != -1){
            myBoard.setContent(row, emptyCol, SrTTTSPOT.MACHINE_CHAR);
            return true;
        }
        return false;
    }

    private boolean blockCol(int col){
        int countPlayer = 0;
        int emptyRow = -1;
        char current;

        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            current = myBoard.getContent(row, col);
            if(current == SrTTTSPOT.PLAYER_CHAR) countPlayer++;
            else if(current == SrTTTSPOT.DEFAULT_CHAR) emptyRow = row;
        }
        if(countPlayer == SrTTTSPOT.NUM_ROWS - 1 && emptyRow != -1){
            myBoard.setContent(emptyRow, col, SrTTTSPOT.MACHINE_CHAR);
            return true;
        }
        return false;
    }

    private boolean blockLDiag(){
        int countPlayer = 0;
        int emptyIndex = -1;
        char current;

        for(int i = 0; i < SrTTTSPOT.NUM_COLS; i++){
            current = myBoard.getContent(i, i);

            if(current == SrTTTSPOT.PLAYER_CHAR) countPlayer++;
            else if (current == SrTTTSPOT.DEFAULT_CHAR) emptyIndex = i;
        }

        if(countPlayer == SrTTTSPOT.NUM_ROWS-1 && emptyIndex != -1){
            myBoard.setContent(emptyIndex, emptyIndex, SrTTTSPOT.MACHINE_CHAR);
            return true;
        }
        return false;
    }

    private boolean blockTDiag(){
        int countPlayer = 0;
        int emptyRow = -1;
        int col = SrTTTSPOT.NUM_COLS-1;
        char current;


        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            current = myBoard.getContent(row, col);
            if(current == SrTTTSPOT.PLAYER_CHAR) countPlayer++;

            else if(current == SrTTTSPOT.DEFAULT_CHAR) emptyRow = row;
        }

        if(countPlayer == SrTTTSPOT.NUM_ROWS-1 && emptyRow != -1){
            myBoard.setContent(emptyRow, col, SrTTTSPOT.MACHINE_CHAR);
            return true;
        }
        return false;
    }


}
