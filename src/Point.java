import java.util.Comparator;
import java.util.Iterator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new PointSlopeComparator();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    public int GetX(){
        return x;
    }
    public int GetY(){
        return y;
    }
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if(that.x == this.x && that.y==this.y){
            return Double.NEGATIVE_INFINITY;
        }
        if(that.y == this.y) return 0;
        if(that.x - this.x == 0) {
            return Double.POSITIVE_INFINITY;
        }


        return ((double)(that.y - this.y)/(double)(that.x - this.x));
    }

    public int compareTo(Point that) {
        int diff = this.y - that.y;
        if(diff < 0){
            return -1;
        }
        if(diff >0)
        {
            return 1;
        }
        if(this.x <that.x)
        {
            return -1;
        }
        if(this.x > that.x)
        {
            return 1;
        }
        return 0;
    }

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }

    private class PointSlopeComparator implements Comparator<Point>
    {
        @Override
        public int compare(Point firstPoint, Point secondPoint) {
            double slopeToFirst =slopeTo(firstPoint);

            double slopeToSecond = slopeTo(secondPoint);

            if(slopeToFirst < slopeToSecond) return -1;
            if(slopeToFirst > slopeToSecond) return 1;
            return 0;
        }
    }
}