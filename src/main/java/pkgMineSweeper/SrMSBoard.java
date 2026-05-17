package pkgMineSweeper;

import pkgSrUtils.SrIntArray;
import pkgSrUtils.SrRCPair;

import java.util.Random;

import static java.lang.Math.abs;

public class SrMSBoard extends SrIntArray {

    private boolean[][] revealed;    // tracks which tiles have been clicked
    private boolean gameOver = false;
    private int totalScore;          // keeps track of running score
    private int rows, cols;

    public SrMSBoard(int rows, int cols){
        super(rows, cols);
        revealed = new boolean[rows][cols];
        initBoard(rows, cols);
        this.rows = rows;
        this.cols = cols;
    }

    private void initBoard(int rows, int cols){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                arrayData[row][col] = 1;
                revealed[row][col] = false;
            }
        }
        int count = 0;
        Random rand = new Random();
        while( count < 14){
            int row = rand.nextInt(rows);
            int col = rand.nextInt(cols);
            if(arrayData[row][col] == 1){
                arrayData[row][col] = -1;
                count++;
            }
        }
    }

    public void printBoard(){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                System.out.print(arrayData[row][col] == -1 ? " M " : " D ");
            }
            System.out.print("\n");
        }
    }

    public int calculateScore(int row, int col){
        SrRCPair[] neighbors = getNextNearestNeighborsArray(row, col);
        int mineCount = 0, diamondCount = 0;
        for (SrRCPair neighbor : neighbors){
            if (Math.abs(neighbor.myRow() - row) <= 1 && Math.abs(neighbor.myCol() - col) <= 1) {
                if (arrayData[neighbor.myRow()][neighbor.myCol()] == -1) mineCount++;
                else diamondCount++;
            }
        }
        return (mineCount * 10) + (diamondCount * 5);
    }
    public boolean isGameOver() { return gameOver; }

    public void reveal(int row, int col){
        revealed[row][col] = true;
        totalScore += calculateScore(row, col);
    }
    public boolean isMine(int row, int col){
        return arrayData[row][col] == -1;
    }

    public boolean isRevealed(int row, int col){
        return revealed[row][col];
    }

    public int getTile(int row, int col){
        return arrayData[row][col];
    }

    public int getScore(){
        return totalScore;
    }





}
