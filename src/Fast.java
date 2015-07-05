import java.util.Arrays;

public class Fast {
    private static Point[] points;
    private static Point[] pointsSorted;
    public static void main(String[] args){
        /*String filename = args[0];
        In fileStream = new In(filename);
        int N = fileStream.readInt();
        Point[] a = new Point[N];

        for (int i = 0; i < N; i++) {
            int x = fileStream.readInt();
            int y = fileStream.readInt();
            a[i] = new Point(x, y);
            a[i].draw();
        }
*/
        points = new Point[8];
        points[0] = new Point(10000,0);
        points[1] = new Point(0,10000);
        points[2] = new Point(3000,7000);
        points[3] = new Point(7000,3000);
        points[4] = new Point(20000,21000);
        points[5] = new Point(3000,4000);
        points[6] = new Point(14000,15000);
        points[7] = new Point(6000,7000);

        pointsSorted = new Point[8];
        pointsSorted[0] = new Point(10000,0);
        pointsSorted[1] = new Point(0,10000);
        pointsSorted[2] = new Point(3000,7000);
        pointsSorted[3] = new Point(7000,3000);
        pointsSorted[4] = new Point(20000,21000);
        pointsSorted[5] = new Point(3000,4000);
        pointsSorted[6] = new Point(14000,15000);
        pointsSorted[7] = new Point(6000,7000);

        realMain(points);
    }


    private static Point[][] printedOut;
    private static int printedIndex =0;
    private static void AddToPrinted(Point[] points) {
        printedOut[printedIndex]=points;
        printedIndex++;
    }

    /* Return 1 if arr2[] is a subset of arr1[] */
    private static boolean isSubset(Point arr1[], Point arr2[])
    {
        int m = arr1.length;
        int n = arr2.length;
        int i = 0;
        int j = 0;
        for (i=0; i<n; i++)
        {
            for (j = 0; j<m; j++) {
                if(arr2[i] == arr1[j])
                    break;
            }

        /* If the above inner loop was not broken at all then
           arr2[i] is not present in arr1[] */
            if (j == m)
                return false;
        }

    /* If we reach here then all elements of arr2[]
      are present in arr1[] */
        return true;
    }
    private static boolean isAlreadyPrintedOut(Point[] points)
    {
        for(int i=0;i<printedIndex;i++){
            if(Arrays.equals(points, printedOut[i])) return true;

            boolean isSubarray = false;
            for(int x=0;x<printedOut[i].length;x++){
                if(isSubset(printedOut[i], points)) return true;
            }
        }
        return false;
    }


    private static void realMain(Point[] points){
        printedOut = new Point[points.length][points.length];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for(int i=0; i< points.length; i++) {
            points[i].draw();
        }

        for(int x=0; x< points.length; x++) {

            Arrays.sort(pointsSorted, points[x].SLOPE_ORDER);

            for(int a=0; a<points.length-1;a++) {
                int start = a+1;
                double initialSlope = pointsSorted[0].slopeTo(pointsSorted[start]);
                while (start + 1 < pointsSorted.length) {
                    if(initialSlope != pointsSorted[start].slopeTo(pointsSorted[start + 1])){
                        start ++;
                    }
                    else
                    {
                        break;
                    }
                }
                int end = start;
                while (end +2 < pointsSorted.length && initialSlope == pointsSorted[end].slopeTo(pointsSorted[end +1])) {
                    end++;
                }

                int numberOfPoints = end - start +2;

                if (numberOfPoints >= 3) {
                    Point[] linePoints = new Point[numberOfPoints];
                    linePoints[0] = pointsSorted[0];
                    for (int m = 0; m< numberOfPoints-1; m++)
                    {
                        linePoints[m+1]=pointsSorted[start+m];
                    }

                    Arrays.sort(linePoints);

                    if(!isAlreadyPrintedOut(linePoints)) {
                                                String result = linePoints[0].toString() + " -> ";
                        for (int m = 1; m < linePoints.length - 1; m++) {
                            result += linePoints[m].toString() + " -> ";
                        }
                        result += linePoints[linePoints.length - 1].toString();

                        StdOut.println(result);

                        linePoints[0].drawTo(linePoints[linePoints.length-1]);
                        AddToPrinted(linePoints);
                        break;
                    }
                }
            }
        }
    }
}
