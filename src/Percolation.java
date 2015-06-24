public class Percolation {
    private boolean[] _sites;
    private int _gridLength;
    private QuickUnionUF _sortingAlgorithm;

    public Percolation(int gridLength) {
        if (gridLength <= 0) throw new java.lang.IllegalArgumentException();

        _gridLength = gridLength;
        initialize();
    }

    private void validateRowColumn(int row, int column) {
        if (row < 1 || column < 1 || row > _gridLength || column > _gridLength) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    private void initialize() {
        int numberOfVirtualSites = 2;
        int numberOfSites = _gridLength * _gridLength + numberOfVirtualSites;
        _sites = new boolean[numberOfSites];
        _sortingAlgorithm = new QuickUnionUF(numberOfSites);
    }

    public void open(int row, int column) {
        validateRowColumn(row, column);
        int correspondingSiteIndex = getSiteIndexFromCoordinates(row, column);
        boolean correspondingSite = _sites[correspondingSiteIndex];
        if (correspondingSite) return;

        int[] siteIndicesToConnect = getNeighborSites(row, column);

        for (int siteIndex : siteIndicesToConnect) {
            if (shouldConnectSite(siteIndex, row, column)) {
                _sortingAlgorithm.union(siteIndex, correspondingSiteIndex);
            }
        }

        _sites[correspondingSiteIndex]=true;
    }

    private boolean shouldConnectSite(int siteIndex, int row, int column) {
        return siteIndex != -1 &&
                (isTopLevelSite(siteIndex)
                        || (isBottomLevelSite(siteIndex) && isFull(row, column))
                        || _sites[siteIndex]);
    }

    private boolean isTopLevelSite(int siteIndex) {
        return siteIndex == 0;
    }

    private boolean isBottomLevelSite(int siteIndex) {
        return siteIndex == _sites.length - 1;
    }

    public boolean isOpen(int row, int column) {
        validateRowColumn(row, column);
        return _sites[getSiteIndexFromCoordinates(row, column)];
    }

    public boolean isFull(int row, int column) {
        validateRowColumn(row, column);
        return isFullWithoutValidation(row, column);
    }

    private boolean isFullWithoutValidation(int row, int column){
        int correspondingSiteIndex = getSiteIndexFromCoordinates(row, column);
        if (!_sites[correspondingSiteIndex]) return false;

        int topLevelSiteId = 0;
        return _sortingAlgorithm.connected(topLevelSiteId, correspondingSiteIndex);
    }

    public boolean percolates() {
        connectBottomSitesWhenFull();
        return _sortingAlgorithm.connected(0, _sites.length-1);
    }

    private void connectBottomSitesWhenFull(){
        for (int column = 1; column <=_gridLength; column ++){
            if(isFullWithoutValidation(_gridLength, column))  {
                int siteIndex = getSiteIndexFromCoordinates(_gridLength, column);
                _sortingAlgorithm.union(siteIndex, _sites.length - 1);
            }
        }
    }

    private int[] getNeighborSites(int row, int column) {
        int[] neighborSites = new int[4];
        neighborSites[0] = GetTopNeighborSite(row, column);
        neighborSites[1] = getBottomSite(row, column);

        if (column != _gridLength) {
            neighborSites[2] = getSiteIndexFromCoordinates(row, column + 1);
        }
        else{
            neighborSites[2] = -1;//stupid way of doing this. But, time.
        }
        if (column != 1) {
            neighborSites[3] = getSiteIndexFromCoordinates(row, column - 1);
        }
        else{
            neighborSites[3] = getSiteIndexFromCoordinates(row, column + 1);
        }

        return neighborSites;
    }

    private int getBottomSite(int row, int column) {
        if (isLastRow(row)) {
            return _sites.length - 1;
        }

        return getSiteIndexFromCoordinates(row + 1, column);
    }

    private int GetTopNeighborSite(int row, int column) {
        if (isFirstRow(row)) {
            return 0;
        }

        int topNeighbor = getSiteIndexFromCoordinates(row - 1, column);
        return topNeighbor;
    }

    private int getSiteIndexFromCoordinates(int row, int column){
        return (row - 1) * _gridLength + column;
    }

    private boolean isFirstRow(int row) {
        return row == 1;
    }

    private boolean isLastRow(int row) {
        return row == _gridLength;
    }
}