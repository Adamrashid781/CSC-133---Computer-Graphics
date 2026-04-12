package pkgSrRenderEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;


public class SrShaderObject {
    public SrShaderObject(String file1, String file2){
        this.file1 = file1;
        this.file2 = file2;
    }
    private String file1;
    private String file2;
    private String shaderPath = System.getProperty("user.dir") + "/assets/shaders/";
    private int programId;

    protected void compileShader(){
        String path1 = shaderPath + file1;
        String path2 = shaderPath + file2;
        String vsSrc = "";
        String fsSrc = "";

        try{
        // converting file data from paths into strings
        vsSrc = new String(Files.readAllBytes(Paths.get(path1)));
        fsSrc = new String(Files.readAllBytes(Paths.get(path2)));
        }
            catch(IOException e){
            System.err.println("Error reading shader file: " + e.getMessage());
            return;
            }

        int shader_program = glCreateProgram();
        int vs = glCreateShader(GL_VERTEX_SHADER);

        if (glGetError() != GL_NO_ERROR) {
            String infoLog = glGetShaderInfoLog(vs, 500);
            System.err.println(infoLog);
        }  //  if (glError != GL_NO_ERROR)


        glShaderSource(vs, vsSrc);
        glCompileShader(vs);
        if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Vertex shader error: " + glGetShaderInfoLog(vs));
        }

        glAttachShader(shader_program, vs);
        int fs = glCreateShader(GL_FRAGMENT_SHADER);


        glShaderSource(fs, fsSrc);
        glCompileShader(fs);
        if (glGetShaderi(fs, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Fragment shader error: " + glGetShaderInfoLog(fs));
        }
        glAttachShader(shader_program, fs);
        glLinkProgram(shader_program);
        if (glGetProgrami(shader_program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Shader link error: " + glGetProgramInfoLog(shader_program));
        }
        programId = shader_program;


    }

    protected void setShader(){
        glUseProgram(programId);
    }

    protected void offLoadShader(){
        glUseProgram(0);
    }
    protected void loadMatrix4f( String uniformName, Matrix4f mtrx){
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        int location = glGetUniformLocation(programId, uniformName);
        glUniformMatrix4fv(location, false, mtrx.get(fb));
    }
}
