import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 14-10-5.
 */
public class KdTree {

    private Node root;

    private int size;

    private static Double LOWER_BOUND = 0.0;
    private static Double UPPER_BOUND = 1.0;

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> result = new LinkedList<Point2D>();
        range(root, rect, VERTICAL, result);
        return result;
    }

    private void range(Node x, RectHV rect, boolean direction, List<Point2D> list) {
        if (x == null) return;
        if (isVertical(direction)) {
            if (rect.contains(x.p)) {
                list.add(x.p);
            }
            Double xX = x.p.x();
            Double rectMinX = rect.xmin();
            Double rectMaxX = rect.xmax();
            if (xX > rectMaxX) {
                range(x.lb, rect, HORIZONTAL, list);
            }
            if (xX <= rectMinX) {
                range(x.rt, rect, HORIZONTAL, list);
            }
            if (xX <= rectMaxX && xX > rectMinX) {
                range(x.lb, rect, HORIZONTAL, list);
                range(x.rt, rect, HORIZONTAL, list);
            }
        } else if (isHorizontal(direction)) {
            if (rect.contains(x.p)) {
                list.add(x.p);
            }
            Double xY = x.p.y();
            Double rectMinY = rect.ymin();
            Double rectMaxY = rect.ymax();
            if (xY > rectMaxY) {
                range(x.lb, rect, VERTICAL, list);
            }
            if (xY <= rectMinY) {
                range(x.rt, rect, VERTICAL, list);
            }
            if (xY <= rectMaxY && xY > rectMinY) {
                range(x.lb, rect, VERTICAL, list);
                range(x.rt, rect, VERTICAL, list);
            }
        }
    }

    private static class Node {
        public Node(Point2D p) {
            this.p = p;
        }

        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }

    private static boolean VERTICAL = true;
    private static boolean HORIZONTAL = false;

    public KdTree() {
        this.size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }


    public void insert(Point2D p) {
        if (p == null) return;
        root = insert(root, p, VERTICAL, null);
    }

    private Node insert(Node x, Point2D p, boolean direction, Node previous) {
        if (x == null) {
            RectHV rect = null;
            if (previous == null) {
                rect = new RectHV(LOWER_BOUND, LOWER_BOUND, UPPER_BOUND, UPPER_BOUND);
            } else {
                if (isHorizontal(direction)) {
                    if (isRightChild(previous, p, !direction)) {
                        rect = new RectHV(previous.p.x(), LOWER_BOUND, UPPER_BOUND, UPPER_BOUND);
                    } else {
                        rect = new RectHV(LOWER_BOUND, LOWER_BOUND, previous.p.x(), UPPER_BOUND);
                    }
                }
                if (isVertical(direction)) {
                    if (isRightChild(previous, p, !direction)) {
                        rect = new RectHV(LOWER_BOUND, previous.p.y(), UPPER_BOUND, UPPER_BOUND);
                    } else {
                        rect = new RectHV(LOWER_BOUND, LOWER_BOUND, UPPER_BOUND, previous.p.y());
                    }
                }
            }
            Node n = new Node(p);
            n.rect = rect;
            size++;
            return n;
        }
        if (x.p.equals(p)) {
            return x;
        }
        if (isHorizontal(direction)) {
            Double xY = x.p.y();
            Double pY = p.y();
            int cmp = pY.compareTo(xY);
            if (cmp >= 0) {
                x.rt = insert(x.rt, p, VERTICAL, x);
            }
            if (cmp < 0) {
                x.lb = insert(x.lb, p, VERTICAL, x);
            }
        }
        if (isVertical(direction)) {
            Double xX = x.p.x();
            Double pX = p.x();
            int cmp = pX.compareTo(xX);
            if (cmp >= 0) {
                x.rt = insert(x.rt, p, HORIZONTAL, x);
            }
            if (cmp < 0) {
                x.lb = insert(x.lb, p, HORIZONTAL, x);
            }
        }
        return x;
    }

    private boolean isRightChild(Node parent, Point2D child, boolean direction) {
        if (isVertical(direction)) {
            Double parentX = parent.p.x();
            Double childX = child.x();
            return childX >= parentX;
        } else if (isHorizontal(direction)) {
            Double parentY = parent.p.y();
            Double childY = child.y();
            return childY >= parentY;
        }
        return false;
    }

    public boolean contains(Point2D p) {
        if (p == null) return false;
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node x, Point2D p, boolean direction) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        if (isHorizontal(direction)) {
            Double xY = x.p.y();
            Double pY = p.y();
            int cmp = pY.compareTo(xY);
            if (cmp < 0) return contains(x.lb, p, VERTICAL);
            if (cmp >= 0) return contains(x.rt, p, VERTICAL);
        } else if (isVertical(direction)) {
            Double xX = x.p.x();
            Double pX = p.x();
            int cmp = pX.compareTo(xX);
            if (cmp < 0) return contains(x.lb, p, HORIZONTAL);
            if (cmp >= 0) return contains(x.rt, p, HORIZONTAL);
        }
        return false;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) return null;
        if (root == null) return null;
        Double closetDistance = root.p.distanceSquaredTo(p);
        return nearest(root, p, root.p, closetDistance);
    }

    private Point2D nearest(Node x, Point2D p, Point2D lastNearestP, Double closestDistance) {
        if (x == null) {
            return lastNearestP;
        }
        Double currentDistance = x.p.distanceSquaredTo(p);
        if (currentDistance < closestDistance) {
            lastNearestP = x.p;
            closestDistance = currentDistance;
        }
        Point2D leftNearestP = null;
        Point2D rightNearestP = null;
        if (x.lb != null) {
            Double rectDistance = x.lb.rect.distanceSquaredTo(p);
            if (rectDistance < closestDistance) {
                leftNearestP = nearest(x.lb, p, lastNearestP, closestDistance);
            }
        }
        if (x.rt != null) {
            Double rectDistance = x.rt.rect.distanceSquaredTo(p);
            if (rectDistance < closestDistance) {
                rightNearestP = nearest(x.rt, p, lastNearestP, closestDistance);
            }
        }
        Double leftClosetDistance = leftNearestP == null ? Double.MAX_VALUE : p.distanceSquaredTo(leftNearestP);
        Double rightClosetDistance = rightNearestP == null ? Double.MAX_VALUE : p.distanceSquaredTo(rightNearestP);
        if (leftClosetDistance < closestDistance) {
            closestDistance = leftClosetDistance;
            lastNearestP = leftNearestP;
        }
        if (rightClosetDistance < closestDistance) {
            closestDistance = rightClosetDistance;
            lastNearestP = rightNearestP;
        }
        return lastNearestP;
    }


    private boolean isVertical(boolean direction) {
        return direction == VERTICAL;
    }

    private boolean isHorizontal(boolean direction) {
        return direction == HORIZONTAL;
    }

    public void draw() {
        draw(root, VERTICAL, null);
    }

    private void draw(Node x, boolean direction, Node previousNode) {
        if (x == null) return;
        if (isVertical(direction)) {
            StdDraw.point(x.p.x(), x.p.y());
            StdDraw.setPenColor(Color.RED);
            if (previousNode == null) {
                StdDraw.line(x.p.x(), LOWER_BOUND, x.p.x(), UPPER_BOUND);
            } else if (previousNode != null) {
                if (previousNode.lb == x) {
                    StdDraw.line(x.p.x(), UPPER_BOUND, x.p.x(), previousNode.p.y());
                }
                if (previousNode.rt == x) {
                    StdDraw.line(x.p.x(), LOWER_BOUND, x.p.x(), previousNode.p.y());
                }
            }
            draw(x.lb, HORIZONTAL, x);
            draw(x.rt, HORIZONTAL, x);
        } else if (isHorizontal(direction)) {
            StdDraw.point(x.p.x(), x.p.y());
            StdDraw.setPenColor(Color.BLUE);
            if (previousNode == null) {
                StdDraw.line(LOWER_BOUND, x.p.y(), UPPER_BOUND, x.p.y());
            } else if (previousNode != null) {
                if (previousNode.lb == x) {
                    StdDraw.line(LOWER_BOUND, x.p.y(), previousNode.p.x(), x.p.y());
                }
                if (previousNode.rt == x) {
                    StdDraw.line(UPPER_BOUND, x.p.y(), previousNode.p.x(), x.p.y());
                }
            }
            draw(x.lb, VERTICAL, x);
            draw(x.rt, VERTICAL, x);
        }
    }
}
