package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.*;

/**
 * Класс задачи
 */
public class Problem {
    /**
     * текст задачи
     */
    public static final String PROBLEM_TEXT = "ПОСТАНОВКА ЗАДАЧИ:\n" +
            "Дано множество точек на плоскости. Выберем из этого множества две точки и проведем через них прямую.\n" +
            "Назовем дистанцией такую максимальную величину, что на расстоянии \n" +
            "от прямой не меньше, чем дистанция лежит хотя бы половина оставшихся точек \n" +
            "множества (кроме этих двух). Найти такую пару точек, у которой дистанция будет минимальна.\n" +
            " В качестве ответа: выделить эти две точки, нарисовать проходящую через них прямую,выделить \n" +
            "точки, лежащие на дистанции, нарисовать дистанцию (отрезок от одной из самых удаленных\n" +
            "точек до прямой), а также <<коридор>> (две прямые, параллельные \n" +
            "найденной прямой, находящиеся на найденной дистанции)";
    /**
     * заголовок окна
     */
    public static final String PROBLEM_CAPTION = "Итоговый проект ученицы 10-7 Галяутдиновой Динары";

    /**
     * путь к файлу
     */
    private static final String FILE_NAME = "points.txt";

    /**
     * список точек
     */
    public ArrayList<Point> points;

    public Point resA = null;
    public Point resB = null;
    Line answline = null;
    Line rangeALine = null;
    Line rangeBLine = null;

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        points = new ArrayList<>();
//        linesLines.add(new Line(0.99, 0.5, 0.3, 0.7));
//        Lines.add(new Line(0.98, 0.5, 0.3, 0.7));
//        Lines.add(new Line(0.99, 0.5, 0.3, 0.7));
    }

    /**
     * Добавить точку
     *
     * @param x координата X точки
     * @param y координата Y точки
     */
    public void addPoint(double x, double y) {
        Point point = new Point(x, y);
        points.add(point);
    }

    public void addRandomPoints(int n) {
        for (int i = 0; i < n; i++) {
            Point p = Point.getRandomPoint();
            points.add(p);
        }
    }


    public void clear() {
        points.clear();
        resA = null;
        resB = null;
        answline = null;
    }

    /**
     * Решить задачу
     */
    public void solve() {
        double minDist = 1e10;
        // перебираем пары точек
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Line line = new Line(points.get(i).x, points.get(i).y, points.get(j).x, points.get(j).y);
                double[] tDist = new double[points.size()];
                for (int k = 0; k < points.size(); k++) {
                    tDist[k] = line.distanceToPoint(points.get(k));
                }
                Arrays.sort(tDist);
                //System.out.println(Arrays.toString(tDist));
                double dist;
//                if (tDist.length % 2 == 0) {
//                    dist = tDist[tDist.length / 2];  ///////!!!!!!!!!!!
//                } else {
//                    dist = tDist[tDist.length / 2] - 0.5;
//                }

                dist = tDist[tDist.length / 2];
                if (dist < minDist) {
                    minDist = dist;
                    resA = points.get(i);
                    resB = points.get(j);
                    answline = line;
                    rangeALine = line.getRightRange(dist);
                    rangeBLine = line.getLeftRange(dist);
                }
            }
        }

    }

    /**
     * Загрузить задачу из файла
     */
    public void loadFromFile() {
        points.clear();
        try {
            File file = new File(FILE_NAME);
            Scanner sc = new Scanner(file);
            // пока в файле есть непрочитанные строки
            while (sc.hasNextLine()) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                int setVal = sc.nextInt();
                sc.nextLine();
                Point point = new Point(x, y);
                points.add(point);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка чтения из файла: " + ex);
        }
    }

    /**
     * Сохранить задачу в файл
     */
    public void saveToFile() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME));
            for (Point point : points) {
                out.printf("%.2f %.2f %d\n", point.x, point.y);
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex);
        }
    }


    /**
     * Нарисовать задачу
     *
     * @param gl переменная OpenGL для рисования
     */
    public void render(GL2 gl) {
        gl.glColor3d(1, 1, 1);
        for (Point point : points) {
            point.render(gl);
        }
        gl.glColor3d(1, 0.2, 0.7);
        if (answline != null && resA != null && resB != null) {
            answline.renderLine(gl);
            Figure.renderPoint(gl, resA.x, resA.y, 7);
            Figure.renderPoint(gl, resB.x, resB.y, 7);
            rangeALine.renderLine(gl);
            rangeBLine.renderLine(gl);
        }


    }


}
