public class Percolation {
    private Site[] _sites;
    private int _gridLength;

    private QuickUnionUF _sortingAlgorithm;
    public Percolation(int gridLength)  {
        _gridLength = gridLength;
        initialize();
    }

    private void initialize(){
        int numberOfVirtualSites = 2;
        int numberOfSites = _gridLength * _gridLength + numberOfVirtualSites;
        _sites = new Site[numberOfSites];
        for(int i=0;i<numberOfSites;i++)
        {
            Site site = new Site();
            site.Id = i;
            _sites[i] = site;
        }
        _sortingAlgorithm = new QuickUnionUF(numberOfSites);
    }

    public void open(int i, int j)    {
        Site correspondingSite = getSiteFromCoordinates(i,j);
        if(isFirstRow(i)){
            _sortingAlgorithm.union(correspondingSite.Id, _sites[0].Id);
        }
        if(isLastRow(i)){
            _sortingAlgorithm.union(correspondingSite.Id, _sites[0].Id);
        }


    }

    private Site getSiteFromCoordinates(int row, int column) {
        int siteIndex = (row-1)*_gridLength + column;
        return _sites[siteIndex];
    }

    boolean isFirstRow(int row){
        return row == 1;
    }

    boolean isLastRow(int row){
        return row == _gridLength;
    }


         // open site (row i, column j) if it is not open already
    public boolean isOpen(int i, int j){
        return _sortingAlgorithm.connected(i,j);
    }
      // is site (row i, column j) open?
    public boolean isFull(int i, int j){
        return false;
    }     // is site (row i, column j) full?

    public boolean percolates(){
        int firstVirtualSiteId = _sites[0].Id;
        int lastVirtualSiteId = _sites[_sites.length-1].Id;
        return _sortingAlgorithm.connected(firstVirtualSiteId, lastVirtualSiteId);
    }

    public static void main(String[] args){}   // test client (optional)
}

class Site{
    public boolean IsOpen;
    public int Id;
}
