import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PercolationStats {
    public PercolationStats(int gridLength, int numberOfExperiments){
        if(gridLength<=0 || numberOfExperiments<=0) throw new IllegalArgumentException();

    }

    public double mean()                 {
        throw new NotImplementedException();
    }     // sample mean of percolation threshold
    public double stddev()                 {throw new NotImplementedException();}   // sample standard deviation of percolation threshold
    public double confidenceLo()             {throw new NotImplementedException();} // low  endpoint of 95% confidence interval
    public double confidenceHi()              {throw new NotImplementedException();}// high endpoint of 95% confidence interval

    public static void main(String[] args)    {}// test client (described below)
}

