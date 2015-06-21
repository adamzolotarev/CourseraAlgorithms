public class Percolation {
    private Site[] _sites;
    private int _gridLength;

    private QuickUnionUF _sortingAlgorithm;

    public Percolation(int gridLength) {
        _gridLength = gridLength;
        initialize();
    }

    private void initialize() {
        int numberOfVirtualSites = 2;
        int numberOfSites = _gridLength * _gridLength + numberOfVirtualSites;
        _sites = new Site[numberOfSites];
        for (int i = 0; i < numberOfSites; i++) {
            Site site = new Site();
            site.Id = i;
            _sites[i] = site;
        }
        _sortingAlgorithm = new QuickUnionUF(numberOfSites);
    }

    public void open(int row, int column) {
        Site correspondingSite = getSiteFromCoordinates(row, column);
        if (correspondingSite.IsOpen) return;

        Site[] sitesToConnect = getNeighborSites(row, column);

        for (Site site : sitesToConnect) {
            if (shouldConnectSite(site)) {
                _sortingAlgorithm.union(site.Id, correspondingSite.Id);
            }
        }

        correspondingSite.IsOpen = true;
    }

    private boolean shouldConnectSite(Site site) {
        return site != null && (isTopLevelSite(site) || isBottomLevelSite(site) || site.IsOpen);
    }

    private boolean isTopLevelSite(Site site) {
        return site.Id == 0;
    }

    private boolean isBottomLevelSite(Site site) {
        return site.Id == _sites.length;
    }

    public boolean isOpen(int row, int column) {
        Site site = getSiteFromCoordinates(row, column);
        return site.IsOpen;
    }

    public boolean isFull(int row, int column) {
        Site correspondingSite = getSiteFromCoordinates(row, column);
        if (!correspondingSite.IsOpen) return false;

        int topLevelSiteId = 0;
        return _sortingAlgorithm.connected(topLevelSiteId, correspondingSite.Id);
    }

    public boolean percolates() {
        int firstVirtualSiteId = _sites[0].Id;
        int lastVirtualSiteId = _sites[_sites.length - 1].Id;
        return _sortingAlgorithm.connected(firstVirtualSiteId, lastVirtualSiteId);
    }

    private Site[] getNeighborSites(int row, int column) {
        Site[] neighborSites = new Site[4];
        int index = 0;

        Site topSite = GetTopNeighborSite(row, column);
        neighborSites[index] = topSite;
        index++;

        Site bottomSite = GetBottomSite(row, column);
        neighborSites[index] = bottomSite;
        index++;

        if (column != _gridLength) {
            Site siteOnTheRight = getSiteFromCoordinates(row, column + 1);
            neighborSites[index] = siteOnTheRight;
            index++;
        }
        if (column != 1) {
            Site siteOnTheLeft = getSiteFromCoordinates(row, column - 1);
            neighborSites[index] = siteOnTheLeft;
        }

        return neighborSites;
    }

    private Site GetBottomSite(int row, int column) {
        if (isLastRow(row)) {
            Site bottomVirtualSite = _sites[_sites.length - 1];
            return bottomVirtualSite;
        }

        return getSiteFromCoordinates(row + 1, column);
    }

    private Site GetTopNeighborSite(int row, int column) {
        if (isFirstRow(row)) {
            Site topVirualSite = _sites[0];
            return topVirualSite;
        }

        Site topNeighbor = getSiteFromCoordinates(row - 1, column);
        return topNeighbor;
    }

    private Site getSiteFromCoordinates(int row, int column) {
        int siteIndex = (row - 1) * _gridLength + column;
        return _sites[siteIndex];
    }

    boolean isFirstRow(int row) {
        return row == 1;
    }

    boolean isLastRow(int row) {
        return row == _gridLength;
    }
}

class Site{
    public boolean IsOpen;
    public int Id;
}
