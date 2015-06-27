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

        int top = getTopNeighborSite(row, column);
        if (sites[top]) {
            connectSites(top, correspondingSiteIndex);
        }

        int bottom = getBottomSite(row, column);
        if (sites[bottom]) {
            connectSites(bottom, correspondingSiteIndex);
        }

        if (column != gridLength) {
            int rightSide = getSiteIndexFromCoordinates(row, column + 1);
            if (sites[rightSide]) {
                connectSites(rightSide, correspondingSiteIndex);
            }
        }

        if (column != 1) {
            int leftSide = getSiteIndexFromCoordinates(row, column - 1);
            if (sites[leftSide]) {
                connectSites(leftSide, correspondingSiteIndex);
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

    private int getBottomSite(int row, int column) {
        if (isLastRow(row)) {
            return lastSiteIndex;
        }

        return getSiteIndexFromCoordinates(row + 1, column);
    }

    private int getTopNeighborSite(int row, int column) {
        if (isFirstRow(row)) {
            return 0;
        }

        int topNeighbor = getSiteIndexFromCoordinates(row - 1, column);
        return topNeighbor;
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