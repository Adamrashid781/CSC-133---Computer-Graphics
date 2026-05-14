package pkgSrRenderEngine;
import pkgMineSweeper.SrMSBoard;
import pkgSrRenderEngine.SrRenderer;
import pkgSrUtils.SrWindowManager;

import static pkgDriver.SrSpot.*;


public class SrMSRenderer extends SrRenderer{
    private SrMSBoard board;
    private SrTextureObject diamondTex;
    private SrTextureObject mineTex;
    private SrTextureObject unrevealedTex;
    private String imgPath = System.getProperty("user.dir") + "/assets/images/";

    public SrMSRenderer(SrWindowManager wm, SrCamera cam, SrShaderObject so, SrMSBoard board){
        super(wm, cam, so);
        this.board = board;
        myVDMgr = new SrVertexDataManager(BOARD_ROWS * BOARD_COLS * 6 * 9, BOARD_ROWS * BOARD_COLS * 6);

        // Texture addresses
        mineTex = new SrTextureObject(imgPath + "MineBomb_2.PNG");
        unrevealedTex = new SrTextureObject(imgPath + "MysteryBox_2.PNG");
        diamondTex = new SrTextureObject(imgPath + "ShiningDiamond_2.PNG");

    }

    @Override
    protected void fillVertexCoordinates() {
        for(int row = 0; row < BOARD_ROWS; row++){
            for(int col = 0; col < BOARD_COLS; col++){
                int xmin = TILE_OFFSET_X + (TILE_SIZE + PADDING_X) * col;
                int ymin = (WIN_HEIGHT - TILE_OFFSET_Y - TILE_SIZE) - (TILE_SIZE + PADDING_Y) * row;
            }
        }

    }
    @Override
    protected void renderScene(){

    }
}
