package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import static javax.media.opengl.GL2GL3.GL_QUADS;

public class Figure {

    public static void renderPoint(GL2 gl, double posX, double posY, int size) {
        gl.glPointSize(8);
        gl.glBegin(GL.GL_POINTS);
        // gl.glColor3d(1, 0, 1);
        //  gl.glVertex2d(0.5,0.25);
        //gl.glColor3d(0, 0, 1);
        gl.glVertex2d(posX, posY);
        gl.glEnd();
    }

    public static void renderLine(GL2 gl, double posAX, double posAY, double posBX, double posBY) {

        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(posAX, posAY);
        gl.glVertex2d(posBX, posBY);
        gl.glEnd();
    }

    public static void renderTriangles(GL2 gl, double posAX, double posAY, double posBX, double posBY, double posCX, double posCY, boolean filled) {
        if (filled) {
            gl.glBegin(GL.GL_TRIANGLES);
            gl.glVertex2d(posAX, posAY);
            gl.glVertex2d(posBX, posBY);
            gl.glVertex2d(posCX, posCY);
            gl.glEnd();
        } else {
           // gl.glBegin(GL.GL_TRIANGLES);
            renderLine(gl, posAX, posAY, posBX, posBY);
            gl.glColor3d(1, 0, 0);
            renderLine(gl, posCX, posCY, posBX, posBY);
            gl.glColor3d(0, 0, 1);
            renderLine(gl, posAX, posAY, posCX, posCY);
           // gl.glEnd();
        }
    }
    public static void renderQuad(GL2 gl, double posAX, double posAY, double posBX, double posBY,double posCX, double posCY, double posDX, double posDY, boolean filled) {
     if(filled){
         gl.glBegin(GL_QUADS);
         gl.glVertex2d(posAX, posAY);
         gl.glVertex2d(posBX, posBY);
         gl.glVertex2d(posCX, posCY);
         gl.glVertex2d(posDX, posDY);
         gl.glVertex2d(posAX, posAY);
         gl.glEnd();
     }
     else{
         renderLine(gl, posAX, posAY, posBX, posBY);
         renderLine(gl, posCX, posCY, posBX, posBY);
         renderLine(gl, posDX, posDY, posCX, posCY);
         renderLine(gl, posAX, posAY, posDX, posDY);
     }


    }


}
