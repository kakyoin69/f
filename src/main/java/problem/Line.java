package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.Random;


public class Line {

    private double a;
    private double b;
    private double c;

    double x1;
    double y1;
    double x2;
    double y2;

    public Line(double x1, double y1, double x2, double y2) {

        this.a = y1 - y2;
        this.b = x2 - x1;
        this.c = x1 * y2 - x2 * y1;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
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
        double x1 = r.nextDouble() * 2 - 1;
        double y1 = r.nextDouble() * 2 - 1;
        double x2 = r.nextDouble() * 2 - 1;
        double y2 = r.nextDouble() * 2 - 1;
        return new Line(x1, y1, x2, y2);
        //  double nx = (double) r.nextInt(50) / 25 - 1;
        //   double ny = (double) r.nextInt(50) / 25 - 1;
        //   int nSetVal = r.nextInt(2);
        //   return new Point(nx, ny, nSetVal);
    }

    public void renderLine(GL2 gl) {
        gl.glLineWidth(3);
        gl.glBegin(GL.GL_LINES);
        int samples = 100;
        double dx = x2 - x1;
        double dy = y2 - y1;
        double l = Math.sqrt(dx * dx + dy * dy);
        double ndx = dx / l * 10;
        double ndy = dy / l * 10;
        gl.glVertex2d(x1 + ndx, y1 + ndy);
        gl.glVertex2d(x1 - ndx, y1 - ndy);
        gl.glEnd();//end drawing of points
    }

    // @Override
    public String toString() {
        boolean fb = b < 0;
        boolean fc = c < 0;
        b = Math.abs(b);
        c = Math.abs(c);
        String s = String.format("%.2fx " + ((fb) ? "-" : "+") + " %.2fy " + ((fc) ? "-" : "+") + " %.2f = 0", a, b, c);
        return s;
    }

    public double distanceToPoint(Point p) {
        double f = Math.sqrt(a * a + b * b);
        double k = a * p.x + b * p.y + c;
        double q = Math.abs(k / f);
        return q;                            //расст от точки p до прямой line
    }


    public boolean oneSide(Point p1, Point p2) {
        if (((a * p1.x + b * p1.y + c <= 0.001) && (a * p2.x + b * p2.y + c <= 0.001)) || ((a * p1.x + b * p1.y + c >= 0.001) && (a * p2.x + b * p2.y + c >= 0.001))) {
            return true;
        } else {
            return false;
        }
    }


    // public Line parallelLine(Point p){
    //  double a1 = a ;
    //  double b1 = b;
    //   double c1 = c;
    //    c1 = - a * p.xax - b * p.yax;
    //     return new Line (a1, b1, c1);
    //   }


    public ArrayList<Line> getRanges(double dist) {
        double xo = 0.5;
        double yo = (a * xo + c) / (-b);

        double cl = dist / Math.cos(Math.atan2(-a, b));
        double cx = dist / Math.sin(Math.atan2(-a, b));

        Point p1l = new Point(xo, yo + cl);
        Point p2l = new Point(xo - cx, yo);

        Point p1r = new Point(xo + cx, yo);
        Point p2r = new Point(xo, yo - cl);

        ArrayList<Line> ranges = new ArrayList<>();

        if (oneSide(p1l, p2l)) {
            ranges.add(new Line(xo, cl + yo, xo - cx, yo));
        }
        if (oneSide(p1l, p1r)) {
            ranges.add(new Line(xo, cl + yo, xo + cx, yo));
        }
        if (oneSide(p2l, p1r)) {
            ranges.add(new Line(xo - cx, yo, xo + cx, yo));
        }
        if (oneSide(p2l, p2r)) {
            ranges.add(new Line(xo - cx, yo, xo, yo - cl));
        }
        if (oneSide(p1r, p2r)) {
            ranges.add(new Line(xo + cx, yo, xo, yo - cl));
        }
        if (oneSide(p1l, p2r)) {
            ranges.add(new Line(xo, cl + yo, xo, yo - cl));
        }

        return ranges;
    }

//    public Line getRightRange(double dist) {
//        return new Line(1,1,1,1);
//    }
//
//    public Line getLeftRange1(double dist) {
//        return new Line();
//    }

    public ArrayList<Point> getPoints(double dist) {
        double xo = 0.5;
        double yo = (a * xo + c) / (-b);

        double cl = dist / Math.cos(Math.atan2(-a, b));
        double cx = dist / Math.sin(Math.atan2(-a, b));

        Point p1l = new Point(xo, yo + cl);
        Point p2l = new Point(xo - cx, yo);

        Point p1r = new Point(xo + cx, yo);
        Point p2r = new Point(xo, yo - cl);
        ArrayList<Point> res = new ArrayList<>();
        res.add(new Point(xo, yo));
        res.add(p1l);
        res.add(p2l);
        res.add(p1r);
        res.add(p2r);
        return res;
    }

}