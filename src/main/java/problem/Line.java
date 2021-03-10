package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.Random;


public class Line {

    private double a;
    private double b;
    private double c;
    double x1; double y1;double x2; double y2;
    public Line(double x1, double y1, double x2, double y2){

        this.a = y1 - y2;
        this.b = x2- x1;
        this.c = x1*y2 - x2*y1;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

//    public Line(Point p1, Point p2) {
//
//    }

    /**
     * Получить случайную точку
     *
     * @return случайная точка
     */
    static Line getRandomLine() {
        Random r = new Random();
        double x1 = r.nextDouble()*2-1;
        double y1 =  r.nextDouble()*2 - 1;
        double x2 = r.nextDouble()*2-1;
        double y2 =  r.nextDouble() *2 - 1;
        return new Line(x1, y1, x2, y2);
        //  double nx = (double) r.nextInt(50) / 25 - 1;
     //   double ny = (double) r.nextInt(50) / 25 - 1;
     //   int nSetVal = r.nextInt(2);
     //   return new Point(nx, ny, nSetVal);
    }

    public void renderLine(GL2 gl){
        gl.glPointSize(1.0f);
        gl.glBegin(GL.GL_LINES);
        int samples = 100;
        double dx = x2 - x1;
        double dy = y2 - y1;
        double l = Math.sqrt(dx*dx+dy*dy);
        double ndx = dx/l*10;
        double ndy = dy/l*10;
        gl.glVertex2d( x1+ndx, y1+ndy);
        gl.glVertex2d( x1-ndx, y1-ndy);
        gl.glEnd();//end drawing of points
    }
    // @Override
    public String toString() {
        boolean fb = b < 0;
        boolean fc = c < 0;
        b = Math.abs(b);
        c = Math.abs(c);
        String s = String.format("%.2fx " + ((fb)? "-":"+") + " %.2fy " + ((fc)? "-":"+") + " %.2f = 0", a, b, c);
        return s;
    }
    public double distanceToPoint( Point p ){
        double f = Math.sqrt(a*a + b*b) ;
        double k = a*p.x + b*p.y + c;
        double q = Math.abs(k / f);
        return q;                            //расст от точки p до прямой line
    }



}