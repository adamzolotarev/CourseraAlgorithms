public class Percolation {
    private boolean[] sites;
    private int gridLength;
    private QuickUnionUF sortingAlgorithm;
    private QuickUnionUF sortingAlgorithmForFull;

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
        sortingAlgorithm = new QuickUnionUF(numberOfSites);
        sortingAlgorithmForFull = new QuickUnionUF((numberOfSites - 1));
    }

    public void open(int row, int column) {
        validateRowColumn(row, column);
        int correspondingSiteIndex = getSiteIndexFromCoordinates(row, column);
        boolean correspondingSite = sites[correspondingSiteIndex];
        if (correspondingSite) return;

        int[] siteIndicesToConnect = getNeighborSites(row, column);

        for (int siteIndex : siteIndicesToConnect) {
            if (shouldConnectSite(siteIndex, row, column)) {
                sortingAlgorithm.union(siteIndex, correspondingSiteIndex);
                if (siteIndex < sites.length - 1) {
                    sortingAlgorithmForFull.union(siteIndex, correspondingSiteIndex);
                }
            }
        }

        sites[correspondingSiteIndex] = true;
    }

    private boolean shouldConnectSite(int siteIndex, int row, int column) {
        return siteIndex != -1 &&
                (isTopLevelSite(siteIndex)
                        || sites[siteIndex])
                || (isBottomLevelSite(siteIndex));
    }

    private boolean isTopLevelSite(int siteIndex) {
        return siteIndex == 0;
    }

    private boolean isBottomLevelSite(int siteIndex) {
        return siteIndex == sites.length - 1;
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

        int topLevelSiteId = 0;
        return sortingAlgorithmForFull.connected(topLevelSiteId, correspondingSiteIndex);
    }

    public boolean percolates() {
        return sortingAlgorithm.connected(0, sites.length - 1);
    }

    private int[] getNeighborSites(int row, int column) {
        int[] neighborSites = new int[4];
        neighborSites[0] = getTopNeighborSite(row, column);
        neighborSites[1] = getBottomSite(row, column);

        if (column != gridLength) {
            neighborSites[2] = getSiteIndexFromCoordinates(row, column + 1);
        } else {
            neighborSites[2] = -1;//stupid way of doing this. But, time.
        }
        if (column != 1) {
            neighborSites[3] = getSiteIndexFromCoordinates(row, column - 1);
        } else {
            neighborSites[3] = getSiteIndexFromCoordinates(row, column + 1);
        }

        return neighborSites;
    }

    private int getBottomSite(int row, int column) {
        if (isLastRow(row)) {
            return sites.length - 1;
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