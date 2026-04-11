package pkgSrRenderEngine;
import java.util.*;
import java.io.*;


public class SrVertexDataReader {
    protected float[][] vertsArray;
    protected final String COMMENT_CHAR = "#";
    protected List<float[]> vertsRow;

    public SrVertexDataReader(String str){
        this.vertsRow = new ArrayList<>();
        loadFile(str);
        vertsArray = new float[vertsRow.size()][];
        for(int col = 0; col < vertsRow.size(); ++col){
            vertsArray[col] = vertsRow.get(col);
        }
    }



    public float[] getVertexCoordsArray(int a){
        return Arrays.copyOf(vertsArray[a], vertsArray[a].length);
    }
    public int getNumVertices(){
        return vertsArray.length;
    }

    private void loadFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith(COMMENT_CHAR)) continue;
                String[] parts = line.split("\\s+");
                if (parts.length == 9) {
                    float[] row = new float[9];
                    for (int i = 0; i < 9; i++) {
                        row[i] = Float.parseFloat(parts[i]);
                    }
                    vertsRow.add(row);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
