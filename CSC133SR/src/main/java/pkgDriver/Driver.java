package pkgDriver;


import pkgSrUtilities.*; // import all files in package

import static java.lang.System.out;
//import pkgSrUtilities.SrRCPair;


public class Driver {
    public static void main(String[] args) {
        SrIntArray myIA = new SrIntArray("ppa_test1.txt");
        myIA.printArray("ppa_test1.txt");
        out.println();

        int[][] rgRowsCols = {{0,0}, {4, 0}, {4, 6}, {0, 6}, {2, 3} };

        out.println("N1Neighbors Computation check:");
        for (int[] rcPair : rgRowsCols) {
            SrRCPair[] myRCP = myIA.getNextNearestNeighborsArray(rcPair[0],rcPair[1]);
            out.printf("[%d][%d]: ", rcPair[0],rcPair[1]);
            for (SrRCPair myP : myRCP) {
                out.printf("{%d, %d}, ", myP.myRow(), myP.myCol());
            }  //  for (SlRCPair myP : myRCP)
            out.println();
        }  //  for (int[] rcPair : rgRowsCols)
        out.println();

        myIA.randomizeViaFisherYatesKnuth();
        myIA.printArray("After Fisher-Yates-Knuth Shuffle:");

//        if(myIA.saveToFile("ppa_test1.txt", 1)){
//            out.println("Successfully appended shuffled array to the file");
//        }
    }  //  public static void main(String[] args)
}  //  public class Driver
