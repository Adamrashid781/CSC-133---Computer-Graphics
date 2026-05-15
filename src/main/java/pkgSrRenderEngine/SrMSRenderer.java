package pkgSrRenderEngine;
import org.joml.Vector4f;
import pkgMineSweeper.SrMSBoard;
import pkgSrRenderEngine.SrRenderer;
import pkgSrUtils.SrWindowManager;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11C.*;
import static pkgDriver.SrSpot.*;


public class SrMSRenderer extends SrRenderer{
    private SrMSBoard board;
    private SrTextureObject diamondTex;
    private SrTextureObject mineTex;
    private SrTextureObject unrevealedTex;


    public SrMSRenderer(SrWindowManager wm, SrCamera cam, SrShaderObject so, SrMSBoard board){
        super(wm, cam, so);
        this.board = board;
        myVDMgr = new SrVertexDataManager(BOARD_ROWS * BOARD_COLS * 6 * 9, BOARD_ROWS * BOARD_COLS * 6);



    }

    @Override
    protected void fillVertexCoordinates() {
        myVDMgr.resetNextIIndex();
        myVDMgr.resetNextVCIndex();
        myVDMgr.resetVertexArray();
        myVDMgr.resetIndexArray();
        for(int row = 0; row < BOARD_ROWS; row++){
            for(int col = 0; col < BOARD_COLS; col++){
                int xmin = TILE_OFFSET_X + (TILE_SIZE + PADDING_X) * col;
                int ymin = (WIN_HEIGHT - TILE_OFFSET_Y - TILE_SIZE) - (TILE_SIZE + PADDING_Y) * row;

                if(!board.isRevealed(row, col)){ // grey = unrevealed
                    myVDMgr.setDefaultColor(new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
                }
                else if (board.isMine(row, col)){ // red = mine
                    myVDMgr.setDefaultColor(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
                }
                else{ // blue = diamond
                    myVDMgr.setDefaultColor(new Vector4f(0.0f, 0.5f, 1.0f, 1.0f));
                }
                // triangle 1
                myVDMgr.fillTriangleVertexCoordinates(xmin, ymin, 0, 0,
                        xmin + TILE_SIZE, ymin, 1, 0,
                        xmin + TILE_SIZE, ymin + TILE_SIZE, 1, 1);

                // Triangle 2
                myVDMgr.fillTriangleVertexCoordinates(xmin, ymin, 0, 0,
                        xmin + TILE_SIZE, ymin + TILE_SIZE, 1, 1,
                        xmin, ymin + TILE_SIZE, 0, 1);

            }
        }

    }
    @Override
    protected void renderScene(){
        initTextures();
        while (!curWM.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glDrawElements(GL_TRIANGLES, BOARD_ROWS * BOARD_COLS * 6, GL_UNSIGNED_INT, 0L);
            curWM.swapBuffers();

        }
    }
    private void initTextures(){

        String imgPath = System.getProperty("user.dir") + "/assets/images/";
        System.out.println(System.getProperty("user.dir") + "/assets/images/");
        // Texture addresses
        mineTex = new SrTextureObject(imgPath + "Mine2CCL.PNG");
        unrevealedTex = new SrTextureObject(imgPath + "MysteryBox_2.PNG");
        diamondTex = new SrTextureObject(imgPath + "ShiningDiamond_2.PNG");
    }
}
