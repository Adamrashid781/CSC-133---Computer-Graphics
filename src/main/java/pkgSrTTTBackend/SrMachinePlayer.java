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
        char current ;

        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            current = myBoard.getContent(row, col);
            if(current == SrTTTSPOT.DEFAULT_CHAR){
                startMovePlay(row, col);
                return true;
            }
        }
        return false;
    }
    private boolean playTheRow(int row){
        char current ;

        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            current = myBoard.getContent(row, col);
            if(current == SrTTTSPOT.DEFAULT_CHAR){
                startMovePlay(row, col);
                return true;
            }
        }
        // if exactly 2 M's and 1 empty spot
        return false;
    }

    private boolean midGamePlay(){
        // 1 Try to win
        if(playToWin()){
            return true;
        }
        // 2 block player win
        else if(preventWin()){
            return true;
        }

        // 4. Block Fork
//        else if(blockFork()){
//            System.out.println("block fork activated");
//            return true;
//        }
        // 5. Create fork
        else if(createFork()){
            System.out.println("create fork activated");
            return true;
        }
////        // 6. Try leading diagonal
//        else if(playLDiag()){
//            System.out.println("playLDiag activated");
//            return true;
//        }
//        // 7. Try Trailing diagonal
//        else if(playTDiag()){
//            System.out.println("playTDiag activated");
//            return true;
//        }
        // 8. Take any side
        else if(takeAnySide()){
            System.out.println("takeAnySide activated");
            return true;
        }
        // 9. Take other corner
        else if(takeOtherCorner() ){
            System.out.println("takeOtherCorner activated");
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
                IO.quitGameMessage();
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
        int[] move = getUserInput();
        if(move[0] == SrTTTSPOT.GAME_QUIT) return;
        if(move[0] == 1 && move[1] == 1) startMovePlay(0,0);
        else startMovePlay(1, 1);

        gameStatus = isGameOver();
//        if(gameStatus != SrTTTSPOT.GAME_INCOMPLETE) return ;
//
//        midGamePlay();
//        gameStatus = isGameOver();
    }



    public void playAgainMessage(){
        IO.playAgainMessage();
    }

    private boolean takeCenter(){
        if(myBoard.getContent(1, 1) == SrTTTSPOT.DEFAULT_CHAR){
//            myBoard.setContent(1,1, SrTTTSPOT.MACHINE_CHAR);
            startMovePlay(1, 1);
            return true;
        }
        return false;
    }

    private boolean createFork() {
        for (int row = 0; row < SrTTTSPOT.NUM_ROWS; row++) {
            for (int col = 0; col < SrTTTSPOT.NUM_COLS; col++) {
                // Only check empty spots
                if (myBoard.getContent(row, col) == SrTTTSPOT.DEFAULT_CHAR) {

                    // 1. Simulate the MACHINE placing a piece
                    myBoard.setTestContent(row, col, SrTTTSPOT.MACHINE_CHAR);

                    // 2. Check how many ways the Machine could win now
                    int opportunities = countWinningOpportunities(SrTTTSPOT.MACHINE_CHAR);

                    // 3. Undo the simulation immediately to keep the board clean
                    myBoard.clearTestContent(row, col);

                    // 4. If this move creates 2+ winning paths, take it for real
                    if (opportunities >= 2) {
                        startMovePlay(row, col);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private int countWinningOpportunities(char mp){
        int countMark = 0;
        int opp = 0; // opportunities
        int countEmpty = 0;
        char cur;

        // rows

        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            countEmpty = 0;
            countMark = 0;
            for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
                cur = myBoard.getContent(row, col);

                if(cur == mp)countMark++;
                else if( cur == SrTTTSPOT.DEFAULT_CHAR) countEmpty++;
            }
            if(countMark == SrTTTSPOT.NUM_ROWS -1 && countEmpty == 1) opp++;
        }

        // cols

        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            countEmpty = 0;
            countMark = 0;
            for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
                cur = myBoard.getContent(row, col);
                if(cur == mp) countMark++;
                else if(cur == SrTTTSPOT.DEFAULT_CHAR) countEmpty++;
            }
            if(countMark == SrTTTSPOT.NUM_ROWS-1 && countEmpty ==1) opp++;
        }

        // Leading Diagonal
        countEmpty = 0;
        countMark = 0;
        for(int i = 0; i < SrTTTSPOT.NUM_ROWS; i++){
            cur = myBoard.getContent(i,i);
            if(cur == mp) countMark++;
            else if(cur == SrTTTSPOT.DEFAULT_CHAR) countEmpty++; //
        }
        if(countMark == SrTTTSPOT.NUM_ROWS-1 && countEmpty == 1) opp++;


        // Trailing Diagonal
        countEmpty = 0;
        countMark = 0;
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            int col = SrTTTSPOT.NUM_COLS-1 - row;
            cur = myBoard.getContent(row,col);
            if(cur == mp) countMark++;
            else if(cur == SrTTTSPOT.DEFAULT_CHAR) countEmpty++; //
        }
        if(countMark == SrTTTSPOT.NUM_ROWS-1 && countEmpty == 1) opp++; // Move this OUTSIDE the loop
        return opp;
    }

    public int play() {
        gameStatus = SrTTTSPOT.GAME_INCOMPLETE; //

        // Show initial empty board
        IO.printBoard(myBoard);

        // Initial specific strategy move
        // System.out.println("DEBUG: Loop is starting. Status is:1 " + gameStatus);
        runFirstRound();
        // System.out.println("DEBUG: Loop is starting. Status is:2 " + gameStatus);

        while (gameStatus == SrTTTSPOT.GAME_INCOMPLETE) {
            // System.out.println("DEBUG: Loop is starting. Status is:3  " + gameStatus);
            // 1. Show the board so player sees the Machine's last move
            IO.printBoard(myBoard);

            // 2. Get validated player input (handles validation and cell-free check)
            int[] move = getUserInput();

            // 3. Handle if the player chose to quit inside getUserInput
            if (move[0] == SrTTTSPOT.GAME_QUIT) {
                gameStatus = SrTTTSPOT.GAME_QUIT;
                break;
            }

            // 4. Check if the player's move ended the game
            gameStatus = isGameOver();

            if (gameStatus != SrTTTSPOT.GAME_INCOMPLETE) {
                break;
            }

            // 5. Machine takes its turn
            runMidGame();

            // 6. Check if the machine's move ended the game
            gameStatus = isGameOver();
        }

        // Final board state and result message
        if (gameStatus != SrTTTSPOT.GAME_QUIT) {
            IO.printBoard(myBoard);
            printGameOverMessage(gameStatus);
        }

        return gameStatus;
    }

    private boolean takeOtherCorner(){
        for(int[] pos : myBoard.cornerCells){
            if(myBoard.getContent(pos[0], pos[1]) == SrTTTSPOT.PLAYER_CHAR && myBoard.getContent(pos[1], pos[0]) == SrTTTSPOT.DEFAULT_CHAR){
                // myBoard.setContent(pos[0], pos[1], SrTTTSPOT.MACHINE_CHAR);
                startMovePlay(pos[1], pos[0]);
            }
            return true;
        }
        return false;
    }
//    private boolean checkCorner(){
//
//    }


    private void runMidGame(){
        midGamePlay();
    }

    // Why does this method for a machine play require parameters?
    private void startMovePlay(int row, int col){
        myBoard.setContent(row, col, SrTTTSPOT.MACHINE_CHAR);
    }

    private boolean blockFork() {
        int opportunities = 0;
        for (int row = 0; row < SrTTTSPOT.NUM_ROWS; row++) {
            for (int col = 0; col < SrTTTSPOT.NUM_COLS; col++) {
                if (myBoard.getContent(row, col) == SrTTTSPOT.DEFAULT_CHAR) {
                    // 1. Simulate the player's move on the ACTUAL board
                    myBoard.setContent(row, col, SrTTTSPOT.PLAYER_CHAR);

                     opportunities = countWinningOpportunities(SrTTTSPOT.PLAYER_CHAR);

                    // 2. ALWAYS clear it immediately after checking
                    myBoard.clearTestContent(row, col);


                }
                if (opportunities >= 2) {
                    startMovePlay(row, col);
                    return true;
                }
            }
        }
        return false;
    }

    public void clearBoard(){
//        SrTTTBoard.clearBoard();
        myBoard.clearBoard();
    }
// need to call this to block player in diag
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
    private int findRepeatsLDiagonal(char mp){
        // counts how many chars are in
        // used in prevent win to see if player has 2 chars in the row, diag, col
        int count = 0;
        for(int i = 0; i < SrTTTSPOT.NUM_ROWS; i++){
            if(myBoard.getContent(i, i) == mp) count++;
        }
        return count;
    }
    private int findRepeatsInRow(int row, char mp){
        int countMark = 0;
        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            if(myBoard.getContent(row, col) == mp) countMark++;
        }
        return countMark;
    }
    private int findRepeatsInCol( int col, char mp){
        int countMark = 0;
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            if(myBoard.getContent(row, col) == mp) countMark++;
        }
        return countMark;
    }

    private boolean takeAnySide(){
        for(int[] pos : myBoard.sideCells){
            if(myBoard.getContent(pos[0], pos[1]) == SrTTTSPOT.DEFAULT_CHAR){
                startMovePlay(pos[0], pos[1]);
                return true;
            }
        }
        return false;
    }

    // returns true if all the columns in the specified row are default char
    private boolean isRowBlank(int row){
        char[][] temp = myBoard.getBoard();
        return temp[row][0] == SrTTTSPOT.DEFAULT_CHAR && temp[row][1] == SrTTTSPOT.DEFAULT_CHAR && temp[row][2] == SrTTTSPOT.DEFAULT_CHAR;
    }

    private int getGameStatus(){
        return isGameOver();
    }

    protected int isGameOver(){
        char first ;
        boolean win;

        // checking rows
        for(int r = 0; r < SrTTTSPOT.NUM_ROWS; r++){
            first = myBoard.getContent(r, 0);
            if(first != SrTTTSPOT.DEFAULT_CHAR){
                win = true;
                for(int c = 1; c < SrTTTSPOT.NUM_COLS; c++){
                    if(myBoard.getContent(r, c) != first){
                        win = false;
                        break;
                    }
                }
                if(win){
                    return (first == SrTTTSPOT.PLAYER_CHAR) ? SrTTTSPOT.GAME_PLAYER : SrTTTSPOT.GAME_MACHINE;
                }
            }

        }

        // Checking Columns
        for(int c = 0; c < SrTTTSPOT.NUM_COLS; c++){
            first = myBoard.getContent(0, c);
            if (first != SrTTTSPOT.DEFAULT_CHAR) {
                win = true;
                for(int r = 1; r < SrTTTSPOT.NUM_ROWS; r++){
                    if(myBoard.getContent(r, c) != first){
                        win = false;
                        break;
                    }
                }
                if(win){
                    return (first == SrTTTSPOT.PLAYER_CHAR) ? SrTTTSPOT.GAME_PLAYER : SrTTTSPOT.GAME_MACHINE;
                }
            }
        }

        // Checking leading Diagonal
        first = myBoard.getContent(0, 0);
        if(first != SrTTTSPOT.DEFAULT_CHAR){
            win = true;
            for(int i = 1; i < SrTTTSPOT.NUM_ROWS; i++){
                if(myBoard.getContent(i, i) != first){
                    win = false;
                    break;
                }
            }
            if(win){
                return (first == SrTTTSPOT.PLAYER_CHAR) ? SrTTTSPOT.GAME_PLAYER : SrTTTSPOT.GAME_MACHINE;
            }
        }

        // Checking Trailing Diagonal
        first = myBoard.getContent(0, SrTTTSPOT.NUM_COLS-1);
        if(first != SrTTTSPOT.DEFAULT_CHAR){
            win = true;
            for(int i = 1; i < SrTTTSPOT.NUM_ROWS; i++){
                if(myBoard.getContent(i, SrTTTSPOT.NUM_COLS-1-i) != first){
                    win = false;
                    break;
                }
            }
            if(win){
                return (first == SrTTTSPOT.PLAYER_CHAR) ? SrTTTSPOT.GAME_PLAYER : SrTTTSPOT.GAME_MACHINE;
            }
        }

        // Checking if its a draw
        boolean boardFull = true;
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
                if(myBoard.getContent(row, col) == SrTTTSPOT.DEFAULT_CHAR){
                    boardFull = false;
                    break;
                }
            }
        }
        if(boardFull) return SrTTTSPOT.GAME_DRAW;

        return SrTTTSPOT.GAME_INCOMPLETE;
    }
    private boolean playLDiag(){
        int countMachine = 0;
        int countPlayer = 0;
        int emptyIndex = -1;

        for(int i = 0; i < SrTTTSPOT.NUM_ROWS; i++){
            char current = myBoard.getContent(i, i);
            if(current == SrTTTSPOT.DEFAULT_CHAR){
                startMovePlay(i,i);
                return true;
            }
        }
        return false;
    }

    private boolean playTDiag(){

        for(int row = 2, col = 0; row >= 0; row--, col++){
            char current = myBoard.getContent(row, col);
            if(current == SrTTTSPOT.DEFAULT_CHAR){
                startMovePlay(row, col);
                return true;
            }
        }

        return false;
    }
    private boolean playToWin(){
        // its the same as preventWin() but only looks for machine char and places it in empty spot for the win
        // Check diagonals and play if possible
        if(findRepeatsLDiagonal( SrTTTSPOT.MACHINE_CHAR) == 2 &&  findRepeatsLDiagonal(SrTTTSPOT.PLAYER_CHAR) == 0) return playLDiag();
        if(findRepeatsTDiagonal( SrTTTSPOT.MACHINE_CHAR) == 2 &&  findRepeatsTDiagonal( SrTTTSPOT.PLAYER_CHAR) == 0) return playTDiag();

        // Check row and play if possible
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            if(findRepeatsInRow(row, SrTTTSPOT.MACHINE_CHAR) == 2 &&  findRepeatsInRow(row, SrTTTSPOT.PLAYER_CHAR) == 0) {
                return playTheRow(row);
            }
        }
        // Check col and play if possible
        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            if(findRepeatsInCol(col, SrTTTSPOT.MACHINE_CHAR) == 2 &&  findRepeatsInCol(col, SrTTTSPOT.PLAYER_CHAR) == 0){
                return playTheCol(col);
            }
        }

        return false;
    }

    // needs to call the findReapeat diag methods so if it returns 2 then it needs to place machine char in empty cell
    private boolean preventWin(){
        if(findRepeatsLDiagonal( SrTTTSPOT.PLAYER_CHAR) == 2 &&  findRepeatsLDiagonal(SrTTTSPOT.MACHINE_CHAR) == 0) return playLDiag();
        if(findRepeatsTDiagonal( SrTTTSPOT.PLAYER_CHAR) == 2 &&  findRepeatsTDiagonal( SrTTTSPOT.MACHINE_CHAR) == 0) return playTDiag();

        // Check row and play if possible
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            if(findRepeatsInRow(row, SrTTTSPOT.PLAYER_CHAR) == 2 &&  findRepeatsInRow(row, SrTTTSPOT.MACHINE_CHAR) == 0) {
                return playTheRow(row);
            }
        }
        // Check col and play if possible
        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
            if(findRepeatsInCol(col, SrTTTSPOT.PLAYER_CHAR) == 2 &&  findRepeatsInCol(col, SrTTTSPOT.MACHINE_CHAR) == 0) {
                return playTheCol(col);
            }
        }
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
            //myBoard.setContent(row, emptyCol, SrTTTSPOT.MACHINE_CHAR);
            startMovePlay(row, emptyCol);
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
            // myBoard.setContent(emptyRow, col, SrTTTSPOT.MACHINE_CHAR);
            startMovePlay(emptyRow, col);
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
            // myBoard.setContent(emptyIndex, emptyIndex, SrTTTSPOT.MACHINE_CHAR);
            startMovePlay(emptyIndex, emptyIndex);
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
            // myBoard.setContent(emptyRow, col, SrTTTSPOT.MACHINE_CHAR);
            startMovePlay(emptyRow, col);
            return true;
        }
        return false;
    }


}
