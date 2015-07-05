import java.io.Console;
import java.util.Arrays;

public class Brute{
    private static Point[] points;
    public static void main(String[] args){
       String filename = args[0];
        In fileStream = new In(filename);
        int N = fileStream.readInt();
        points = new Point[N];

        for (int i = 0; i < N; i++) {
            int x = fileStream.readInt();
            int y = fileStream.readInt();
            points[i] = new Point(x, y);
        }

        /* points = new Point[8];
        points[0] = new Point(10000,0);
        points[1] = new Point(0,10000);
        points[2] = new Point(3000,7000);
        points[3] = new Point(7000,3000);
        points[4] = new Point(20000,21000);
        points[5] = new Point(3000,4000);
        points[6] = new Point(14000,15000);
        points[7] = new Point(6000,7000);
*/
        realMain(points);
    }

    private static boolean areOnTheSameLine(Point first, Point second, Point third, Point fourth)
    {
        double slopeFirstToSecond = first.slopeTo(second);
        return slopeFirstToSecond == first.slopeTo(third) && slopeFirstToSecond== first.slopeTo(fourth);
    }

    private static boolean areOnTheSameLine(Point first, Point second, Point third)
    {
        double slopeFirstToSecond = first.slopeTo(second);
        return slopeFirstToSecond == first.slopeTo(third);
    }

    private static void realMain(Point[] points){
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for(int i=0; i< points.length; i++) {
            points[i].draw();
        }

        for(int a=0;a<points.length; a++)
        {
            for(int b= a+1; b< points.length; b++){
                for(int c= b+1; c< points.length; c++){
                    if(areOnTheSameLine(points[a],points[b],points[c])){
                        for(int d= c+1; d< points.length; d++){
                            if(areOnTheSameLine(points[a],points[b],points[c], points[d])) {

                                Point[] linePoints = new Point[4];
                                linePoints[0] = points[a];
                                linePoints[1] = points[b];
                                linePoints[2] = points[c];
                                linePoints[3] = points[d];
                                Arrays.sort(linePoints);

                                String result = linePoints[0].toString() +" -> " + linePoints[1].toString()
                                        +" -> " + linePoints[2].toString() +" -> " + linePoints[3].toString();
                                StdOut.println(result);

                                linePoints[0].drawTo(linePoints[3]);
                            }
                        }
                    }
                }
            }
        }
    }
}
