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

        return false;
    }

    private boolean midGamePlay(){

        return false;
    }

    private int[] getUserInput(){
        int[] move = IO.readIntegerInput();
        return -1;
    }

    private void runFirstRound(){

    }

    private int findRepeatsInCol(int row, int col){

        return -1;
    }

    public void playAgainMessage(){

    }

    private boolean takeCenter(){

        return false;
    }

    private int findRepeatsLDiagonal(char val){

        return -1;
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

    private int findRepeatsInRow(int row, int col){

        return -1;
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

    private int findRepeatsTDiagonal(char ){
        // counts how many chars are in
        // used in prevent win to see if player has 2 chars in the row, diag, col
        return -1;
    }
    private boolean takeAnySide(){

        return false;
    }

    // returns if all the columns in the specified row are default char
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
        return false;
    }

    private boolean preventWin(){

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



}
