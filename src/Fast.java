import java.util.Arrays;

public class Fast {
    private static Point[] points;
    private static Point[] pointsSorted;
    private static Point[][] printedOut;
    private static int printedIndex;

    public static void main(String[] args) {
        String filename = args[0];
        printedOut = null;
        printedIndex = 0;
        //String filename = "E:\\Downloads\\collinear-testing\\collinear\\input8.txt";
        In fileStream = new In(filename);
        int N = fileStream.readInt();
        points = new Point[N];
        pointsSorted = new Point[N];

        for (int i = 0; i < N; i++) {
            int x = fileStream.readInt();
            int y = fileStream.readInt();
            points[i] = new Point(x, y);
            pointsSorted[i] = new Point(x, y);
        }

        realMain(points);
    }

    private static void AddToPrinted(Point[] points) {
        printedOut[printedIndex] = points;
        printedIndex++;
    }

    private static boolean isAlreadyPrintedOut(Point[] points) {
        for (int i = 0; i < printedIndex; i++) {
            Point printedPoint = printedOut[i][0];
            if(printedPoint.equals(points[0]))
            {
                printedPoint = printedOut[i][1];
            }

            double pointToPrinted = points[0].slopeTo(printedPoint);
            double pointToPoint =points[0].slopeTo(points[1]);

            if ( pointToPrinted == pointToPoint)
                return true;
        }
        return false;
    }


    private static void realMain(Point[] points) {
        printedOut = new Point[points.length][points.length];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < points.length; i++) {
            points[i].draw();
        }

        for (int x = 0; x < points.length; x++) {

            Arrays.sort(pointsSorted, points[x].SLOPE_ORDER);

            for (int a = 0; a < points.length - 1; a++) {
                int start = a + 1;
                double initialSlope = pointsSorted[0].slopeTo(pointsSorted[start]);

                int end = start;
                //double test = pointsSorted[end].slopeTo(pointsSorted[end + 1]);
                while (end + 1 < pointsSorted.length && initialSlope == pointsSorted[end].slopeTo(pointsSorted[end + 1])) {
                    end++;
                }

                int numberOfPoints = end - start + 2;

                if (numberOfPoints >= 4) {
                    Point[] linePoints = new Point[numberOfPoints];
                    linePoints[0] = pointsSorted[0];
                    for (int m = 0; m < numberOfPoints - 1; m++) {
                        linePoints[m + 1] = pointsSorted[start + m];
                    }

                    Arrays.sort(linePoints);

                    if (!isAlreadyPrintedOut(linePoints)) {
                        String result = linePoints[0].toString() + " -> ";
                        for (int m = 1; m < linePoints.length - 1; m++) {
                            result += linePoints[m].toString() + " -> ";
                        }
                        result += linePoints[linePoints.length - 1].toString();

                        StdOut.println(result);

                        linePoints[0].drawTo(linePoints[linePoints.length - 1]);
                        AddToPrinted(linePoints);
                        break;
                    }
                }
            }
        }
    }
}