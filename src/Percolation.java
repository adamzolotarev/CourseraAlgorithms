public class Percolation {
    private boolean[] sites;
    private int gridLength;
    private QuickUnionUF sortingAlgorithm;
    private QuickUnionUF sortingAlgorithmForFull;
    private int lastSiteIndex;

    public Percolation(int gridLength) {
        if (gridLength <= 0) throw new java.lang.IllegalArgumentException();

        this.gridLength = gridLength;
        initialize();
    }

    private void validateRowColumn(int row, int column) {
        if (row < 1 || column < 1 || row > gridLength || column > gridLength) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    private void initialize() {
        int numberOfVirtualSites = 2;
        int numberOfSites = gridLength * gridLength + numberOfVirtualSites;
        sites = new boolean[numberOfSites];
        lastSiteIndex = sites.length -1;
        sites[0] = true;
        sites[lastSiteIndex] = true;
        sortingAlgorithm = new QuickUnionUF(numberOfSites);
        sortingAlgorithmForFull = new QuickUnionUF((numberOfSites - 1));
    }

    public void open(int row, int column) {
        validateRowColumn(row, column);
        int correspondingSiteIndex = getSiteIndexFromCoordinates(row, column);
        boolean correspondingSite = sites[correspondingSiteIndex];
        if (correspondingSite) return;

        if (isFirstRow(row)) {
            connectSites(0, correspondingSiteIndex);
            int bottom = getSiteIndexFromCoordinates(row + 1, column);
            if(sites[bottom]) {
                connectSites(bottom, correspondingSiteIndex);
            }
        }
        else if(isLastRow(row)){
            connectSites(lastSiteIndex, correspondingSiteIndex);
            connectSites(0, correspondingSiteIndex);
            int top = getSiteIndexFromCoordinates(row - 1, column);
            if(sites[top]) {
                connectSites(top, correspondingSiteIndex);
            }
        }
        else{
            int bottom = getSiteIndexFromCoordinates(row + 1, column);
            if(sites[bottom]) {
                connectSites(bottom, correspondingSiteIndex);
            }
            int top = bottom -2*gridLength;
            if(sites[top]) {
                connectSites(top, correspondingSiteIndex);
            }
        }

        if (column == gridLength) {
            int leftSide = getSiteIndexFromCoordinates(row, column - 1);
            if (sites[leftSide]) {
                connectSites(leftSide, correspondingSiteIndex);
            }
        }
        else if(column ==1) {
            int rightSide = getSiteIndexFromCoordinates(row, column + 1);
            if (sites[rightSide]) {
                connectSites(rightSide, correspondingSiteIndex);
            }
        }
        else {
            int leftSide = getSiteIndexFromCoordinates(row, column - 1);
            if (sites[leftSide]) {
                connectSites(leftSide, correspondingSiteIndex);
            }
            int rightSide = leftSide + 2;
            if (sites[rightSide]) {
                connectSites(rightSide, correspondingSiteIndex);
            }
        }

        sites[correspondingSiteIndex] = true;
    }

    private void connectSites(int neighborSiteIndex, int correspondingSiteIndex) {
        sortingAlgorithm.union(neighborSiteIndex, correspondingSiteIndex);
        if (neighborSiteIndex < lastSiteIndex) {
            sortingAlgorithmForFull.union(neighborSiteIndex, correspondingSiteIndex);
        }
    }

    public boolean isOpen(int row, int column) {
        validateRowColumn(row, column);
        return sites[getSiteIndexFromCoordinates(row, column)];
    }

    public boolean isFull(int row, int column) {
        validateRowColumn(row, column);
        return isFullWithoutValidation(row, column);
    }

    private boolean isFullWithoutValidation(int row, int column) {
        int correspondingSiteIndex = getSiteIndexFromCoordinates(row, column);
        if (!sites[correspondingSiteIndex]) return false;

        return sortingAlgorithmForFull.connected(0, correspondingSiteIndex);
    }

    public boolean percolates() {
        return sortingAlgorithm.connected(0, lastSiteIndex);
    }

    private int getSiteIndexFromCoordinates(int row, int column) {
        return (row - 1) * gridLength + column;
    }

    private boolean isFirstRow(int row) {
        return row == 1;
    }

    private boolean isLastRow(int row) {
        return row == gridLength;
    }
}