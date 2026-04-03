package pkgSrRenderEngine;

import pkgSrUtils.SrWindowManager;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SrLMRenderer {
    SrLMRenderer(int , SrWindowManager myW){

    }

    private int viewMatLocation;
    private SrVertexDataManager curWM;
    private SrVertexDataManager myVDMgr;
    private int projMatLocation;


    public void render(){

    }
    private void fillVertexCoordinates(){

    }
    public void renderScene(){

    }
    public void initOpenGL(){
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glViewport(0, 0, SrWindowManager.getWinWidth(), SrWindowManager.getWinHeight());
        // This  changes the color of the window
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        int shader_program = glCreateProgram();
        int vs = glCreateShader(GL_VERTEX_SHADER);

        if (glGetError() != GL_NO_ERROR) {
            String infoLog = glGetShaderInfoLog(vs, 500);
            System.err.println(infoLog);
        }  //  if (glError != GL_NO_ERROR)

        final String stringVS = "#version 410 core\n" +
                "layout (location=0) in vec3 aPos;" +
                "layout (location=1) in vec2 aTexCoords;" +
                "layout (location=2) in vec4 aColor;" +
                "uniform mat4 uProjMatrix;" +
                "uniform mat4 uViewMatrix;" +
                "out vec2 fTexCoords;" +
                "out vec4 fColor;" +
                "void main(void) {" +
                "   fTexCoords = aTexCoords;" +
                "   fColor = aColor;" +
                "   gl_Position = uProjMatrix * uViewMatrix * vec4(aPos,1.0);" +
                "}";
        glShaderSource(vs, stringVS);
        glCompileShader(vs);
        glAttachShader(shader_program, vs);
        int fs = glCreateShader(GL_FRAGMENT_SHADER);

        final String stringPS = "#version 410 core\n" +
                "in vec4 fColor;" +
                "out vec4 outColor;" +
                "void main(void) {" +
                "   outColor = fColor;" +
                "}";
        glShaderSource(fs, stringPS);
        glCompileShader(fs);
        glAttachShader(shader_program, fs);
        glLinkProgram(shader_program);
        glUseProgram(shader_program);
        projMatLocation = glGetUniformLocation(shader_program, "uProjMatrix");
        viewMatLocation = glGetUniformLocation(shader_program, "uViewMatrix");
    }


}
