import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 14-10-5.
 */
public class PointSET {

    private Set<Point2D> pointSet;

    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    public void draw() {
        Iterator<Point2D> it = pointSet.iterator();
        StdDraw.clear();
        StdDraw.setPenColor(Color.BLACK);
        while (it.hasNext()) {
            Point2D p = it.next();
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> result = new HashSet<Point2D>();
        Iterator<Point2D> it = pointSet.iterator();
        while (it.hasNext()) {
            Point2D p = it.next();
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        if (pointSet.isEmpty()) return null;
        Iterator<Point2D> it = pointSet.iterator();
        Double distance = Double.MAX_VALUE;
        Point2D nearestPoint = new Point2D(0, 0);
        while (it.hasNext()) {
            Point2D tempPoint = it.next();
            Double tempDistance = tempPoint.distanceTo(p);
            if (tempDistance < distance) {
                distance = tempDistance;
                nearestPoint = tempPoint;
            }
        }
        return nearestPoint;
    }


}
