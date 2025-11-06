package Service;

import Domain.AStarCell;
import Domain.Cell;
import Repo.Maze;
import UI.MazeRenderer;

import java.awt.*;

public class SolverPlayer {
    private Cell current;
    private final Cell End;
    private Maze maze;
    private final MazeRenderer renderer;
    private boolean[][] visited;

    public SolverPlayer(Cell Start,Cell End, Maze maze,  MazeRenderer mazeRenderer) {

        this.current = maze.getCell(Start.getRow(), Start.getCol());
        this.End = maze.getCell(End.getRow(), End.getCol());
        this.maze = maze;
        this.renderer = mazeRenderer;

        this.visited = new boolean[maze.rows()][maze.cols()];
        for(int i = 0; i < maze.rows(); i++)
            for(int j = 0; j < maze.cols(); j++)
                visited[i][j] = false;

    }

    private void paint_red(Cell cell){
        renderer.drawCell(cell.getRow(), cell.getCol(), Color.RED);
    }

    private void paint_white(Cell cell){
        renderer.drawCell(cell.getRow(), cell.getCol(), Color.WHITE);
    }

    private void paint_green(Cell cell){
        renderer.drawCell(cell.getRow(), cell.getCol(), Color.GREEN);
    }

    private void paint_cell(int next_row,  int next_col){
        if(!visited[next_row][next_col]){
            visited[next_row][next_col] = true;
            paint_red(current);
        }
        else{
            visited[current.getRow()][current.getCol()] = false;
            paint_green(current);
        }
        current = maze.getCell(next_row, next_col);
        paint_white(current);
    }

    private boolean checkValidCell(int row, int col){
        return row >= 0 && row < maze.rows() && col >= 0 && col < maze.cols();
    }


    public void checkUp(){
        int next_row = current.getRow() - 1;
        int next_col = current.getCol();

        if(!current.isNorthWall() && checkValidCell(next_row, next_col)){
            paint_cell(next_row,  next_col);
            return;
        }

        System.out.println("Invalid move!");
    }


    public void checkDown(){
        int next_row = current.getRow() + 1;
        int next_col = current.getCol();

        if(!current.isSouthWall() && checkValidCell(next_row, next_col)){
            paint_cell(next_row,  next_col);
            return;
        }

        System.out.println("Invalid move!");
    }


    public void checkLeft(){
        int next_row = current.getRow();
        int next_col = current.getCol() - 1;

        if(!current.isWestWall() && checkValidCell(next_row, next_col)){
            paint_cell(next_row,  next_col);
            return;
        }

        System.out.println("Invalid move!");
    }


    public void checkRight(){
        int next_row = current.getRow();
        int next_col = current.getCol() + 1;

        if(!current.isEastWall() && checkValidCell(next_row, next_col)){
            paint_cell(next_row,  next_col);
            return;
        }

        System.out.println("Invalid move!");
    }
}
