package pkgSrRenderEngine;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import pkgMineSweeper.SrMSBoard;
import pkgSrRenderEngine.SrRenderer;
import pkgSrUtils.SrMouseListener;
import pkgSrUtils.SrWindowManager;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL15C.GL_DYNAMIC_DRAW;
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
    protected void fillVertexCoordinates(){

    }
    protected int fillVertexCoordinates(int tileType) {
        myVDMgr.resetNextIIndex();
        myVDMgr.resetNextVCIndex();
        myVDMgr.resetVertexArray();
        myVDMgr.resetIndexArray();

        int count = 0 ;

        for(int row = 0; row < BOARD_ROWS; row++){
            for(int col = 0; col < BOARD_COLS; col++){
                boolean shouldDraw = false;

                if(tileType == 0 && !board.isRevealed(row, col)) shouldDraw = true;
                else if (tileType != 0 && board.isRevealed(row, col) && board.getTile(row, col) == tileType) shouldDraw = true;



                if(shouldDraw) {
                    int xmin = TILE_OFFSET_X + (TILE_SIZE + PADDING_X) * col;
                    int ymin = (WIN_HEIGHT - TILE_OFFSET_Y - TILE_SIZE) - (TILE_SIZE + PADDING_Y) * row;

                    myVDMgr.setDefaultColor(new Vector4f(1, 1, 1, 1));

                    // triangle 1
                    myVDMgr.fillTriangleVertexCoordinates(xmin, ymin, 0, 1,
                            xmin + TILE_SIZE, ymin, 1, 1,
                            xmin + TILE_SIZE, ymin + TILE_SIZE, 1, 0);

                    // Triangle 2
                    myVDMgr.fillTriangleVertexCoordinates(xmin, ymin, 0, 1,
                            xmin + TILE_SIZE, ymin + TILE_SIZE, 1, 0,
                            xmin, ymin + TILE_SIZE, 0, 0);
                    count++;
                }

            }
        }
        return count;

    }
    @Override
    protected void renderScene(){
        initTextures();
        while (!curWM.isGlfwWindowClosed()) {
            glfwPollEvents();

            if(SrMouseListener.isPressed() && !board.isGameOver()){
                double px = SrMouseListener.getX();
                double py = SrMouseListener.getY();

                // flipping y because glfw is top-bottom and opengl is bottom-up
                double flippedY = WIN_HEIGHT - py;

                // convert pixel to tile
                int col = (int)(px - TILE_OFFSET_X) / (TILE_SIZE + PADDING_X);
                int row = (int)(py - TILE_OFFSET_Y) / (TILE_SIZE + PADDING_Y);

                // check bounds
                if(col >= 0 && col < BOARD_COLS && row >= 0 && row < BOARD_ROWS){
                    // check dead zone
                    int localX = (int)(px - TILE_OFFSET_X) % (TILE_SIZE + PADDING_X);
                    int localY = (int)(py - TILE_OFFSET_Y) % (TILE_SIZE + PADDING_Y);

                    if(localX < TILE_SIZE && localY < TILE_SIZE){
                        if (!board.isRevealed(row, col)) {
                            board.reveal(row, col);
                            System.out.println("Mouse click at: (" + row + ", " + col + ")   Score: " + board.getScore());
                        }
                    }
                }
            }




            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Pass 1- unrevealed textures
            int unrevealedCount = fillVertexCoordinates(0);
            reuploadBuffers();
            unrevealedTex.bindTexture();
            glDrawElements(GL_TRIANGLES, unrevealedCount * 6, GL_UNSIGNED_INT, 0L);

            // Pass 2 - diamonds
            int diamondCount = fillVertexCoordinates(1);
            reuploadBuffers();
            diamondTex.bindTexture();
            glDrawElements(GL_TRIANGLES, diamondCount * 6, GL_UNSIGNED_INT, 0L);

            // Pass 3 - mines
            int mineCount =fillVertexCoordinates(-1);
            reuploadBuffers();
            mineTex.bindTexture();
            glDrawElements(GL_TRIANGLES, mineCount * 6, GL_UNSIGNED_INT, 0L);

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

    private void reuploadBuffers() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(myVDMgr.getVertexArrayLength())
                .put(myVDMgr.getVertexArray(), 0, myVDMgr.getVertexArrayLength()).flip(), GL_DYNAMIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.createIntBuffer(myVDMgr.getIndexArray().length)
                .put(myVDMgr.getIndexArray(), 0, myVDMgr.getIndexArray().length).flip(), GL_DYNAMIC_DRAW);

    }
}
