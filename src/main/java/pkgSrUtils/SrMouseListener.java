package pkgSrUtils;

import static org.lwjgl.glfw.GLFW.*;

public class SrMouseListener {

    private static double mouseX;
    private static double mouseY;
    private static boolean mouseButtonPressed;

    public static void mousePosCallBack(long window, double xPos, double yPos){
        mouseX = xPos;
        mouseY = yPos;

    }
    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(button == GLFW_MOUSE_BUTTON_LEFT){
            if( action == GLFW_PRESS) mouseButtonPressed = true;
            else if (action == GLFW_RELEASE) mouseButtonPressed = false;
        }
    }

    public double getX() {return mouseX;}
    public double getY(){return mouseY;}
    public boolean isPressed(){return mouseButtonPressed;}
}