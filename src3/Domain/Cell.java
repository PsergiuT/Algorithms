package Domain;

public class Cell {
    private boolean northWall;
    private boolean southWall;
    private boolean eastWall;
    private boolean westWall;

    private int row;
    private int col;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        northWall = true;
        southWall = true;
        eastWall = true;
        westWall = true;
    }

    public boolean isNorthWall() {
        return northWall;
    }

    public boolean isSouthWall() {
        return southWall;
    }

    public boolean isEastWall() {
        return eastWall;
    }

    public boolean isWestWall() {
        return westWall;
    }

    public void setNorthWall(boolean northWall) {
        this.northWall = northWall;
    }

    public void setSouthWall(boolean southWall) {
        this.southWall = southWall;
    }

    public void setEastWall(boolean eastWall) {
        this.eastWall = eastWall;
    }

    public void setWestWall(boolean westWall) {
        this.westWall = westWall;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
