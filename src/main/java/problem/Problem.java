package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
   " В качестве ответа: выделить эти две точки, нарисовать проходящую через них прямую,\n" +
    "выделить точки, лежащие на дистанции, \n" +
    "нарисовать дистанцию (отрезок от одной из самых удаленных точек до прямой),\n" +
    "а также <<коридор>> (две прямые, параллельные \n" +
    "найденной прямой, находящиеся на найденной дистанции)";
    /**
     * заголовок окна
     */
    public static final String PROBLEM_CAPTION = "Итоговый проект ученика 10-7 Иванова Ивана";

    /**
     * путь к файлу
     */
    private static final String FILE_NAME = "points.txt";

    /**
     * список точек
     */
    public ArrayList<Point> points;
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
     * @param x      координата X точки
     * @param y      координата Y точки
     */
    public void addPoint(double x, double y) {
        Point point = new Point(x, y);
        points.add(point);
    }

    public void addRandomPoints(int n) {
        for(int i = 0; i < n; i++) {
            Point p = Point.getRandomPoint();
            points.add(p);
        }
    }



    public void clear() {points.clear();}
    /**
     * Решить задачу
     */
    public void solve() {
        // перебираем пары точек
        for (Point p : points) {
            for (Point p2 : points) {
                // если точки являются разными
                if (p != p2) {
                    // если координаты у них совпадают
                    if (Math.abs(p.x - p2.x) < 0.0001 && Math.abs(p.y - p2.y) < 0.0001) {
                        p.isSolution = true;
                        p2.isSolution = true;
                    }
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
        for (Point point : points) {
            point.render(gl);
        }
    }
}
