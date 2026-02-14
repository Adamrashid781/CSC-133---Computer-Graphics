package pkgDriver;
import pkgSrRenderEngine.SrTMRenderer;
import pkgSrRenderEngine.SrTMRenderer.*;
public class Driver {
    public static void main(String[] args) {
        final int trianglesToRender = 20;
        SrTMRenderer currentScene = new SrTMRenderer(trianglesToRender);
        currentScene.render();
    } // public static void main(String[] args)
} // public class Driver

