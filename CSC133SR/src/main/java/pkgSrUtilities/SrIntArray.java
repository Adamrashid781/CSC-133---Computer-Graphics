package pkgSrUtilities;
import java.io.*;
import java.util.*;

import static java.lang.System.out;

public class SrIntArray {
    SrRCPair[] myRCPArray = new SrRCPair[8];
    // 1. sets the size of int[][] arrayData
    public SrIntArray(int rows, int cols){
        arrayData = new int[rows][cols];
    }
    // 2. Reads the data from the test file
    public SrIntArray(String someS){
        // calls on loadFile()
        loadFile(someS);
    }
    // 3. gets a deep COPY of the data in the test file
    public SrIntArray(int[][] data){
        arrayData = new int[data.length][data[0].length];
        for(int i = 0; i < data.length; i++){
            arrayData[i] = Arrays.copyOf(data[i], data[i].length);
        }
    }

    protected int[][] arrayData;


    // File data should conform to the file format expected, myArray should be pre-allocated large enough to
    // accommodate the array. Format:
    // <default_value>
    // <ROWS> <COLS>
    // <row_num> <col_offset_n> <c_n> <c_n+1> ... with missing columns filled by default value
    public void loadFile(String dataFilePath) {
        try (BufferedReader myReader = new BufferedReader(new FileReader(dataFilePath))) {
            String inputLine;
            int DEFAULT_VALUE = Integer.parseInt(myReader.readLine());
            inputLine = myReader.readLine();
            int[] rowCol = Arrays.stream(inputLine.split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            final int NUM_ROWS = rowCol[0], NUM_COLS = rowCol[1];
            if ((arrayData == null || NUM_ROWS > arrayData.length) || (NUM_COLS > arrayData[0].length)) {
                try {
                    arrayData = new int[NUM_ROWS][NUM_COLS];
                } catch (OutOfMemoryError e) {
                    arrayData = null;
                }
            }  //  ((NUM_ROWS >arrayData.length) || (NUM_COLS >arrayData[0].length))
            // fillup with default values first: what is not overwritten will be default values:
            for (int row = 0; row <arrayData.length; ++row) {
                for (int col = 0; col <arrayData[0].length; ++col) {
                    arrayData[row][col] = DEFAULT_VALUE;
                }  //  for(int col = 0; col <arrayData[0].length; ++col)
            }  //  for(int row = 0; row <arrayData; ++row)

            if (arrayData != null) {
                final int ROWNUM_INDEX = 0, COLOFFSET_INDEX = 1;
                int curRow = 0, colOffset = 0, rowLength = 0;
                while ((inputLine = myReader.readLine()) != null) {
                    if (inputLine.isBlank() || inputLine.isEmpty()) {
                        continue;
                    }  //  if (inputLine.isBlank() || inputLine.isEmpty())
                    // Process each inputLine here
                    int[] readRow = Arrays.stream(inputLine.split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    curRow = readRow[ROWNUM_INDEX];
                    colOffset = readRow[COLOFFSET_INDEX];
                    int readColOffset = 2, curWriteCol = colOffset;  // we start reading data from this column
                    while (curWriteCol < NUM_COLS && readColOffset < readRow.length) {
                        arrayData[curRow][curWriteCol++] = readRow[readColOffset++];
                    }  //  while (readColOffset < readRow.length && curWriteCol < NUM_COLS)
                }  //  while ((inputLine = myReader.readLine()) != null)
            }  //  if (retVal)
        } catch (IOException e) {
            e.printStackTrace();
            arrayData = null;
        }  // try ... catch.``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````

    }  //  public void loadFile(...)

    public int[] getNumRowsCols(){
        int[] rc = new int[2];
        rc[0] = arrayData.length;
        rc[1] = arrayData[0].length;
        return rc ;
    }

    public void printArray(String someString){
        String array = "";
        out.println(someString);
        for(int row = 0; row < arrayData.length; row++){
            for(int col = 0; col < arrayData[0].length; col++){
                 out.printf("%3d", arrayData[row][col]);
            }
            out.println();
        }
    }

    public void randomizeViaFisherYatesKnuth(){

    }

    public int[][] getClone(){
        int[][] copy = new int[arrayData.length][arrayData[0].length];
            for(int r = 0; r < arrayData.length; r++){
                System.arraycopy(arrayData[r], 0, copy[r], 0, arrayData[0].length);
            }
        return copy;
    }

    public boolean saveToFiles(String someString, int x){

        return false;
    }

    public SrRCPair[] getNextNearestNeighborsArray(int row, int col){

        ///  1%4 = 1
        ///  2%4 = 2
        ///  3%4 = 3
        ///  4%4 = 0

        // creating Record of pairs for surrounding neighbors
        SrRCPair[] myRCPArray = new SrRCPair[8];

        // neighbor addresses
        // (-1,+1), (-1,0), (-1,-1), (0,-1), (+1,-1), (+1,0), (+1,+1), (0,+1)
        // row and col are (0, 0)
        int nextR = (row + 1) % arrayData.length;
        int nextC = (col + 1) % arrayData[0].length;

        int prevR = (arrayData.length + row-1) % arrayData.length;
        int prevC = (arrayData[0].length + col-1) % arrayData[0].length;

        myRCPArray[0] = new SrRCPair(prevR, nextC); //  (-1, +1)
        myRCPArray[1] = new SrRCPair(prevR, col); //    (-1,0)
        myRCPArray[2] = new SrRCPair(prevR, prevC); //  (-1,-1)
        myRCPArray[3] = new SrRCPair(row, prevC); //    (0,-1)
        myRCPArray[4] = new SrRCPair(nextR, prevC); //  (+1,-1)
        myRCPArray[5] = new SrRCPair(nextR, col); //    (+1,0)
        myRCPArray[6] = new SrRCPair(nextR, nextC); //  (+1,+1)
        myRCPArray[7] = new SrRCPair(row, nextC); //    (0,+1)

        return myRCPArray;
    }


} // end SrIntArray()
