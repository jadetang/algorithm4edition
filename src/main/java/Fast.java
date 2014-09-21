import java.util.Arrays;

/**
 * Created by jadetang on 14-9-21.
 */
public class Fast {
    public static void main(String[] args) {
        //String fileName = args[0];
        String fileName = "collinear/input8.txt";

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
        Arrays.sort(points);
        Arrays.sort(points, points[0].SLOPE_ORDER);


        Point[] copy = Arrays.copyOfRange(points, 1, points.length);
        Arrays.sort(copy, copy[0].SLOPE_ORDER);

        System.out.println("-------");


        for (int i = 0; i < points.length; i++) {
            probeAndPrint(points, i, 4);
        }
    }

    private static void probeAndPrint(Point[] points, int compareIndex, int minStep) {
        Point comparePoint = points[compareIndex];
        for (int i = compareIndex + 1; i < points.length - 1; ) {
            int temp = i;
            int step = 2;
            while (comparePoint.SLOPE_ORDER.compare(points[i], points[i + 1]) == 0 && (temp + step) < points.length) {
                i++;
                step++;
            }
            if (step >= minStep) {
                printOneLine(points, temp, temp + step);
                i += step;
            } else {
                i++;
            }
        }
    }

    private static void printOneLine(Point[] points, int beginIndex, int endIndexExclusive) {
        StringBuilder sb = new StringBuilder();
        for (int i = beginIndex; i < endIndexExclusive; i++) {
            sb.append(points[i] + " -> ");
        }
        sb.replace(sb.lastIndexOf(" -> "), sb.length(), "");
        System.out.println(sb.toString());
    }
}
