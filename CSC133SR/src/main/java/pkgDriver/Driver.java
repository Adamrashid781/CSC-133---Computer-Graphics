package pkgDriver;


import pkgSrUtilities.*; // import all files in package
//import pkgSrUtilities.SrRCPair;


public class Driver {
    public static void main(String[] args) {
        int abc = 0;
        SrIntArray myIA = new SrIntArray("ppa_test1.txt");
        myIA.printArray("ppa_test1.txt");
        System.out.println();

        int[][] rgRowsCols = {{0,0}, {4, 0}, {4, 6}, {0, 6}, {2, 3} };

        System.out.println("N1Neighbors Computation check:");
        for (int[] rcPair : rgRowsCols) {
            SrRCPair[] myRCP = myIA.getNextNearestNeighborsArray(rcPair[0],rcPair[1]);
            System.out.printf("[%d][%d]: ", rcPair[0],rcPair[1]);
            for (SrRCPair myP : myRCP) {
                System.out.printf("{%d, %d}, ", myP.myRow(), myP.myCol());
            }  //  for (SlRCPair myP : myRCP)
            System.out.println();
        }  //  for (int[] rcPair : rgRowsCols)
        System.out.println();

        myIA.randomizeViaFisherYatesKnuth();
        myIA.printArray("After Fisher-Yates-Knuth Shuffle:");
    }  //  public static void main(String[] args)
}  //  public class Driver
