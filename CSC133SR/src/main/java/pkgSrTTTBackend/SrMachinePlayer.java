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
//            myBoard.setContent(emptyRow, col, SrTTTSPOT.MACHINE_CHAR);
            startMovePlay(emptyRow, col);
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
        int[] move = getUserInput();
        if(move[0] == SrTTTSPOT.GAME_QUIT) return;
        if(move[0] == 1 && move[1] == 1) startMovePlay(1,1);
        else startMovePlay(0, 0);

        gameStatus = isGameOver();
        if(gameStatus != SrTTTSPOT.GAME_INCOMPLETE) return ;

        midGamePlay();
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
//            myBoard.setContent(1,1, SrTTTSPOT.MACHINE_CHAR);
            startMovePlay(1, 1);
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
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){

                if(myBoard.getContent(row, col) == SrTTTSPOT.DEFAULT_CHAR){
                    myBoard.getBoard()[row][col] = SrTTTSPOT.MACHINE_CHAR;

                    int opportunities = countWinningOpportunities(SrTTTSPOT.MACHINE_CHAR);

                    myBoard.getBoard()[row][col] = SrTTTSPOT.DEFAULT_CHAR;

                    if(opportunities >= 2){
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
            for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
                cur = myBoard.getContent(row, col);

                if(cur == mp)countMark++;
                else if( cur == SrTTTSPOT.DEFAULT_CHAR) countEmpty++;
            }
            if(countMark == SrTTTSPOT.NUM_ROWS -1 && countEmpty == 1) opp++;
        }

        // cols
        countEmpty = 0;
        countMark = 0;
        for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){
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
            else if(cur == SrTTTSPOT.DEFAULT_CHAR) opp++;
        }

        // Trailing Diagonal
        countEmpty = 0;
        countMark = 0;
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            int col = SrTTTSPOT.NUM_COLS-1 - row;
            cur = myBoard.getContent(row,col);
            if(cur == mp) countMark++;
            else if(cur == SrTTTSPOT.DEFAULT_CHAR) opp++;
        }
        return opp;
    }

    public int play(){
        runFirstRound();

        if(gameStatus == SrTTTSPOT.GAME_QUIT) return gameStatus;

        while(gameStatus == SrTTTSPOT.GAME_INCOMPLETE){
            int[] move = getUserInput();
            if(move[0] == SrTTTSPOT.GAME_QUIT){
                gameStatus = SrTTTSPOT.GAME_QUIT;
                break;
            }

            gameStatus = isGameOver();

            if(gameStatus != SrTTTSPOT.GAME_INCOMPLETE) break;

            runMidGame();
        }

        if(gameStatus != SrTTTSPOT.GAME_QUIT) printGameOverMessage(gameStatus);

        return gameStatus;
    }

    private boolean takeOtherCorner(){
        for(int[] pos : myBoard.cornerCells){
            if(myBoard.getContent(pos[0], pos[1]) == SrTTTSPOT.DEFAULT_CHAR){
                // myBoard.setContent(pos[0], pos[1], SrTTTSPOT.MACHINE_CHAR);
                startMovePlay(pos[0], pos[1]);
            }


            return true;

        }
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
        midGamePlay();
    }

    // Why does this method for a machine play require parameters?
    private void startMovePlay(int row, int col){
        myBoard.setContent(row, col, SrTTTSPOT.MACHINE_CHAR);
    }

    private boolean blockFork(){
        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){
            for(int col = 0; col < SrTTTSPOT.NUM_COLS; col++){

                // Simulate player move
                if(myBoard.getContent(row, col) == SrTTTSPOT.DEFAULT_CHAR){

                    myBoard.getBoard()[row][col] = SrTTTSPOT.PLAYER_CHAR;

                    int opportunities =
                            countWinningOpportunities(SrTTTSPOT.PLAYER_CHAR);
                    // undo simulation
                    myBoard.getBoard()[row][col] =
                            SrTTTSPOT.DEFAULT_CHAR;
                    // if player would create fork, block it
                    if(opportunities >= 2){
                        startMovePlay(row, col);
                        return true;
                    }
                }
            }
        }
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
        for(int[] pos : myBoard.sideCells){
            if(myBoard.getContent(pos[0], pos[1]) == SrTTTSPOT.DEFAULT_CHAR){
//                myBoard.setContent(pos[0], pos[1], SrTTTSPOT.MACHINE_CHAR);
                startMovePlay(pos[0], pos[1]);
            }

                return true;
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
        int emptyIndex = -1;

        for(int i = 0; i < SrTTTSPOT.NUM_ROWS; i++){

            char current = myBoard.getContent(i, i);

            if(current == SrTTTSPOT.MACHINE_CHAR){
                countMachine++;
            }
            else if(current == SrTTTSPOT.DEFAULT_CHAR){
                emptyIndex = i;
            }
        }

        if(countMachine == SrTTTSPOT.NUM_ROWS - 1 && emptyIndex != -1){
            startMovePlay(emptyIndex, emptyIndex);
            return true;
        }
        return false;
    }

    private boolean playTDiag(){
        int countMachine = 0;
        int emptyRow = -1;

        for(int row = 0; row < SrTTTSPOT.NUM_ROWS; row++){

            int col = SrTTTSPOT.NUM_COLS - 1 - row;

            char current = myBoard.getContent(row, col);

            if(current == SrTTTSPOT.MACHINE_CHAR){
                countMachine++;
            }
            else if(current == SrTTTSPOT.DEFAULT_CHAR){
                emptyRow = row;
            }
        }

        if(countMachine == SrTTTSPOT.NUM_ROWS - 1 && emptyRow != -1){

            int col = SrTTTSPOT.NUM_COLS - 1 - emptyRow;
            startMovePlay(emptyRow, col);
            return true;
        }
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
        int countMachine = 0;
        int emptyCol = -1;
        char current ;

        for(int col = 0; row < SrTTTSPOT.NUM_COLS; row++){
            current = myBoard.getContent(row, col);

            if(current == SrTTTSPOT.MACHINE_CHAR) countMachine++;
            else if (current == SrTTTSPOT.DEFAULT_CHAR) emptyCol = col;
        }
        // if exaclty 2 M's and 1 empty spot
        if(countMachine == SrTTTSPOT.NUM_COLS - 1 && emptyCol != -1){
            // myBoard.setContent(row, emptyCol, SrTTTSPOT.MACHINE_CHAR);
            startMovePlay(row, emptyCol);
            return true;
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
