public class Percolation {
    private int gridLength;
    private QuickUnionUF sortingAlgorithm;
    private QuickUnionUF sortingAlgorithmForFull;
    private int lastSiteIndex;
    private boolean[][] openSites;
    private boolean percolates;
    private boolean[][] fullSites;

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
        lastSiteIndex = numberOfSites - 1;
        sortingAlgorithm = new QuickUnionUF(numberOfSites);
        sortingAlgorithmForFull = new QuickUnionUF((numberOfSites - 1));
        openSites = new boolean[gridLength+1][gridLength+1];
        fullSites = new boolean[gridLength][gridLength];
    }

    private int GetIndex(int row, int column, int retrievedIndex) {
        if (retrievedIndex > 0) {
            return retrievedIndex;
        }
        return getSiteIndexFromCoordinates(row, column);
    }

    public void open(int row, int column) {
        validateRowColumn(row, column);
        if (openSites[row][column]) return;
        int correspondingSiteIndex = 0;

        if (isFirstRow(row)) {
            correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
            connectSites(0, correspondingSiteIndex);
            if (gridLength > 1 && openSites[row + 1][column] || gridLength == 1) {
                connectSites(getSiteIndexFromCoordinates(row + 1, column), correspondingSiteIndex);
            }
        } else if (isLastRow(row)) {
            correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
            connectSites(lastSiteIndex, correspondingSiteIndex);
            if (gridLength > 1 && openSites[row - 1][column]) {
                connectSites(getSiteIndexFromCoordinates(row - 1, column), correspondingSiteIndex);
            }
        } else {
            if (openSites[row + 1][column]) {
                correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                connectSites(correspondingSiteIndex + gridLength, correspondingSiteIndex);
            }
            if (openSites[row - 1][column]) {
                correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                connectSites(correspondingSiteIndex - gridLength, correspondingSiteIndex);
            }
        }

        if (gridLength > 1) {
            if (column == gridLength) {
                if (openSites[row][column - 1]) {
                    correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                    connectSites(getSiteIndexFromCoordinates(row, column - 1), correspondingSiteIndex);
                }
            } else if (column == 1) {
                if (openSites[row][column + 1]) {
                    correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                    connectSites(getSiteIndexFromCoordinates(row, column + 1), correspondingSiteIndex);
                }
            } else {
                if (openSites[row][column - 1]) {
                    correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                    connectSites(correspondingSiteIndex - 1, correspondingSiteIndex);
                }
                if (openSites[row][column + 1]) {
                    correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                    connectSites(correspondingSiteIndex + 1, correspondingSiteIndex);
                }
            }
        }
        openSites[row][column] = true;
    }

    private void connectSites(int neighborSiteIndex, int correspondingSiteIndex) {
        sortingAlgorithm.union(neighborSiteIndex, correspondingSiteIndex);
        if (neighborSiteIndex < lastSiteIndex) {
            sortingAlgorithmForFull.union(neighborSiteIndex, correspondingSiteIndex);
        }
    }

    public boolean isOpen(int row, int column) {
        validateRowColumn(row, column);
        return openSites[row][column];
    }

    public boolean isFull(int row, int column) {
        validateRowColumn(row, column);
        if (!openSites[row][column]) return false;
        if(fullSites[row-1][column-1]) return true;

        int correspondingSiteIndex = getSiteIndexFromCoordinates(row, column);
        if(sortingAlgorithmForFull.connected(0, correspondingSiteIndex))
        {
            fullSites[row-1][column-1] = true;
            return true;
        }
        return false;
    }

    public boolean percolates() {
        if (percolates) return true;
        if (sortingAlgorithm.connected(0, lastSiteIndex)) {
            percolates = true;
            return true;
        }
        return false;
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