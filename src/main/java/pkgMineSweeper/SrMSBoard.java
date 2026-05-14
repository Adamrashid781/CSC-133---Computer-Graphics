package pkgMineSweeper;

import java.util.Random;

public class SrMSBoard {

    private int[][] board;           // 9x7 grid, -1 = mines, 1 = diamonds
    private boolean[][] revealed;    // tracks which tiles have been clicked
    private int totalScore;          // keeps track of running score
    private int rows, cols;

    public SrMSBoard(int rows, int cols){
        board = new int[rows][cols];
        revealed = new boolean[rows][cols];
        initBoard(rows, cols);
        this.rows = rows;
        this.cols = cols;
    }

    private void initBoard(int rows, int cols){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                board[row][col] = 1;
                revealed[row][col] = true;
            }
        }
        int count = 0;
        Random rand = new Random();
        while( count < 14){
            int row = rand.nextInt(rows);
            int col = rand.nextInt(cols);
            if(board[row][col] == 1){
                board[row][col] = -1;
                count++;
            }
        }
    }





}
