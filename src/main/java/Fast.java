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

        for (int i = 0; i < points.length; i++) {
            Point[] temp = Arrays.copyOfRange(points, i, points.length);
            Arrays.sort(temp);
            Arrays.sort(temp, temp[0].SLOPE_ORDER);
            probeAndPrint(temp, 0, 4);
        }

    }

    private static void probeAndPrint(Point[] points, int compareIndex, int minStep) {
        Point comparePoint = points[compareIndex];
        for (int i = compareIndex; i < points.length - 3; ) {
            int start = i;
            int step = 3;
            while (comparePoint.SLOPE_ORDER.compare(points[i + 1], points[i + 2]) == 0 && i < points.length - 3) {
                i++;
                step++;
            }
            if (step >= minStep) {
                printOneLine(points, start, start + step);
                start += step;
                i = start;
            } else {
                i++;
            }
        }
    }

    private static void printOneLine(Point[] points, int beginIndex, int endIndexExclusive) {
        Point[] temp = Arrays.copyOfRange(points, beginIndex, endIndexExclusive);

        Arrays.sort(temp);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < temp.length; i++) {
            sb.append(temp[i] + " -> ");
        }
        sb.replace(sb.lastIndexOf(" -> "), sb.length(), "");
        System.out.println(sb.toString());
    }
}
