package pkgMineSweeper;

public class SrMSBoard {

    private int[][] board;           // 9x7 grid, -1 = mines, 1 = diamonds
    private boolean[][] revealed;    // tracks which tiles have been clicked
    private int totalScore;          // keeps track of running score
}
