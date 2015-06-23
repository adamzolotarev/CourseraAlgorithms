public class PercolationStats {
    private double _mean;
    private double _standardDeviation;
    private double _confidenceLevelLow;
    private double _confidenceLevelHigh;

    public PercolationStats(int gridLength, int numberOfExperiments) {
        if (gridLength <= 0 || numberOfExperiments <= 0) throw new IllegalArgumentException();

        runExperiment(gridLength, numberOfExperiments);
    }

    private void runExperiment(int gridLength, int numberOfExperiments) {
        double[] openToTotalRations = new double[numberOfExperiments];
        for (int i = 0; i < numberOfExperiments; i++) {
            Percolation percolation = new Percolation(gridLength);
            int numberOfOpenSites = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, gridLength + 1);
                int column = StdRandom.uniform(1, gridLength + 1);
                if(!percolation.isOpen(row, column))
                {
                    percolation.open(row, column);
                    numberOfOpenSites++;
                }
            }
            openToTotalRations[i]= (double)numberOfOpenSites/(double)(gridLength*gridLength);
        }

        CalculateStatistics(openToTotalRations);
    }

    private void CalculateStatistics(double[] openToTotalRations) {
        _mean = StdStats.mean(openToTotalRations);
        _standardDeviation = StdStats.stddev(openToTotalRations);
        _confidenceLevelLow = _mean - 1.96 * _standardDeviation;
        _confidenceLevelHigh = _mean + 1.96 * _standardDeviation;
    }

    public double mean() {
        return _mean;
    }

    public double stddev() {
        return _standardDeviation;
    }

    public double confidenceLo() {
        return _confidenceLevelLow;
    }

    public double confidenceHi() {
        return _confidenceLevelHigh;
    }

    public static void main(String[] args) {
        int gridLength = Integer.parseInt(args[0]);
        int numberOfExperiments = Integer.parseInt(args[1]);

        //Stopwatch stopwatch = new Stopwatch();
        PercolationStats stats = new PercolationStats(gridLength,numberOfExperiments);
        //StdOut.println(stopwatch.elapsedTime());
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval =" + stats.confidenceLo() +","+stats.confidenceHi());
    }
}