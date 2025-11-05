package Repo;

import Domain.Cell;

public class Maze {
    private Cell[][] cells;
    private final int rows;
    private final int cols;
    private final int cellSize;

    public int rows(){
        return rows;
    }

    public int cols(){
        return cols;
    }

    public int cellSize(){
        return cellSize;
    }


    public Maze(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;

        cells = new Cell[rows][cols];

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                cells[r][c] = new Cell(r, c);

    }

    public Cell getCell(int row, int col){
        return cells[row][col];
    }

    public boolean isEastWall(int row, int col) {

        return cells[row][col].isEastWall();
    }

    public boolean isWestWall(int row, int col) {

        return cells[row][col].isWestWall();
    }

    public boolean isNorthWall(int row, int col) {

        return cells[row][col].isNorthWall();
    }

    public boolean isSouthWall(int row, int col) {

        return cells[row][col].isSouthWall();
    }

    public void setEastWall(int row, int col, boolean value) {
        if(row < 0 || row >= rows || col < 0 || col >= cols)
            return;
        cells[row][col].setEastWall(value);
    }

    public void setWestWall(int row, int col, boolean value) {
        if(row < 0 || row >= rows || col < 0 || col >= cols)
            return;
        cells[row][col].setWestWall(value);
    }

    public void setSouthWall(int row, int col, boolean value) {
        if(row < 0 || row >= rows || col < 0 || col >= cols)
            return;
        cells[row][col].setSouthWall(value);
    }

    public void setNorthWall(int row, int col, boolean value) {
        if(row < 0 || row >= rows || col < 0 || col >= cols)
            return;
        cells[row][col].setNorthWall(value);
    }
}
