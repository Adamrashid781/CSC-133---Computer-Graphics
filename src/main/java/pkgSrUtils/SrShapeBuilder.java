// package pkgSrRenderEngine;

//public class SrShapeBuilder {
//    private final SrTMRenderer renderer;
//    public SrShapeBuilder(SrTMRenderer renderer){
//        this.renderer = renderer;
//    }
//
//    public void rect(float x, float y, float w, float h, float r, float g, float b, float a){
//
//        float x0 = x,       y0 = y;
//        float x1 = x + w,   y1 = y;
//        float x2 = x + w,   y2 = y + h;
//        float x3 = x,       y3 = y + h;
//
//        renderer.fillVertices(x0, y0,  x1, y1,  x2, y2,  r, g, b, a);
//        renderer.fillVertices(x0, y0,  x2, y2,  x3, y3,  r, g, b, a);
//    }
//
//    public void triangle(float x0, float y0, float x1, float y1, float x2,
//                         float y2, float r, float g, float b, float a ){
//        renderer.fillVertices(x0, y0,  x1, y1,  x2, y2,  r, g, b, a);
//    }
//}
