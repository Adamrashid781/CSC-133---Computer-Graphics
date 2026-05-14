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






}
