import java.util.Arrays;

/**
 * Created by jadetang on 14-9-20.
 */
public class Brute {
    public static void main(String[] args) {
        //String fileName = args[0];
        String fileName = "collinear/input100.txt";

        In in = new In(fileName);

        int pointsCount = in.readInt();
        Point[] points = new Point[pointsCount];
        int count = 0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            points[count] = new Point(i, j);
            count++;
        }
        drawLines(points);

    }

    private static void drawLines(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (onSameLine(points[i], points[j], points[k], points[l])) {
                            printOneLine(points[i], points[j], points[k], points[l]);
                        }
                    }
                }
            }
        }
    }

    private static boolean onSameLine(Point... points) {
        for (int i = 1; i < points.length - 1; i++) {
            if (points[0].SLOPE_ORDER.compare(points[i], points[i + 1]) != 0) {
                return false;
            }
        }
        return true;
    }

    private static void printOneLine(Point... points) {

        // Point[] copyPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(points);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < points.length; i++) {
            sb.append(points[i] + " -> ");
        }
        sb.replace(sb.lastIndexOf(" -> "), sb.length(), "");
        System.out.println(sb.toString());
    }

}
