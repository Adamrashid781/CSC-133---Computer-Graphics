package pkgDriver;

import pkgSrUtils.SrGoLArray;

public class Driver {
    public static void main(String[] strArgs) {
//        SrGoLArray golBoard = new SrGoLArray();
//        golBoard = new SrGoLArray();
//        golBoard.loadFile("gol_input.txt");
//
//        golBoard.printArray("Initial Array:");
//        final int MAX_LOOP_COUNT = 47;
//        for (int curLoop = 0; curLoop < MAX_LOOP_COUNT; ++curLoop) {
//            golBoard.onTickUpdate();
//            // golBoard.printArray("current board");
//        }  //  for(int curLoop = 0; curLoop < MAX_LOOP_COUNT; ++curLoop)
//
//        golBoard.printArray("After " + MAX_LOOP_COUNT + " loops:");


        final int trianglesToRender = 20;
        SlWindowManager curWM = SlWindowManager.get(WIN_WIDTH, WIN_HEIGHT);
        SlLMRenderer currentScene = new SlLMRenderer(trianglesToRender, curWM);
        currentScene.render();




    }  //  public static void main(String[] strArgs)
    
}  //  public class Driver
