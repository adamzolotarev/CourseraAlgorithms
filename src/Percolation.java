public class Percolation {
    private int gridLength;
    private QuickUnionUF sortingAlgorithm;
    private QuickUnionUF sortingAlgorithmForFull;
    private int lastSiteIndex;
    private boolean[][] openSites;

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
        openSites = new boolean[gridLength][gridLength];
    }

    private int GetIndex(int row, int column, int retrievedIndex) {
        if (retrievedIndex > 0) {
            return retrievedIndex;
        }
        return getSiteIndexFromCoordinates(row, column);
    }

    public void open(int row, int column) {
        validateRowColumn(row, column);
        int adjustedRow = row - 1;
        int adjustedColumn = column - 1;
        if (openSites[adjustedRow][adjustedColumn]) return;
        int correspondingSiteIndex = 0;

        if (isFirstRow(row)) {
            correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
            connectSites(0, correspondingSiteIndex);
            if (gridLength > 1 && openSites[adjustedRow + 1][adjustedColumn] || gridLength == 1) {
                connectSites(getSiteIndexFromCoordinates(row + 1, column), correspondingSiteIndex);
            }
        } else if (isLastRow(row)) {
            correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
            connectSites(lastSiteIndex, correspondingSiteIndex);
            if (gridLength > 1 && openSites[adjustedRow - 1][adjustedColumn]) {
                connectSites(getSiteIndexFromCoordinates(row - 1, column), correspondingSiteIndex);
            }
        } else {
            if (openSites[adjustedRow + 1][adjustedColumn]) {
                correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                connectSites(correspondingSiteIndex + gridLength, correspondingSiteIndex);
            }
            if (openSites[adjustedRow - 1][adjustedColumn]) {
                correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                connectSites(correspondingSiteIndex - gridLength, correspondingSiteIndex);
            }
        }

        if (gridLength > 1) {
            if (column == gridLength) {
                if (openSites[adjustedRow][adjustedColumn - 1]) {
                    correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                    connectSites(getSiteIndexFromCoordinates(row, column - 1), correspondingSiteIndex);
                }
            } else if (column == 1) {
                if (openSites[adjustedRow][adjustedColumn + 1]) {
                    correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                    connectSites(getSiteIndexFromCoordinates(row, column + 1), correspondingSiteIndex);
                }
            } else {
                if (openSites[adjustedRow][adjustedColumn - 1]) {
                    correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                    connectSites(correspondingSiteIndex - 1, correspondingSiteIndex);
                }
                if (openSites[adjustedRow][adjustedColumn + 1]) {
                    correspondingSiteIndex = GetIndex(row, column, correspondingSiteIndex);
                    connectSites(correspondingSiteIndex + 1, correspondingSiteIndex);
                }
            }
        }
        openSites[adjustedRow][adjustedColumn] = true;
    }

    private void connectSites(int neighborSiteIndex, int correspondingSiteIndex) {
        sortingAlgorithm.union(neighborSiteIndex, correspondingSiteIndex);
        if (neighborSiteIndex < lastSiteIndex) {
            sortingAlgorithmForFull.union(neighborSiteIndex, correspondingSiteIndex);
        }
    }

    public boolean isOpen(int row, int column) {
        validateRowColumn(row, column);
        return openSites[row - 1][column - 1];
    }

    public boolean isFull(int row, int column) {
        validateRowColumn(row, column);
        return isFullWithoutValidation(row, column);
    }

    private boolean isFullWithoutValidation(int row, int column) {
        if (!openSites[row - 1][column - 1]) return false;
        int correspondingSiteIndex = getSiteIndexFromCoordinates(row, column);
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