package Service;

import Domain.Cell;
import Repo.Maze;
import UI.MazeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenerateMaze {
    private final Maze maze;
    private final boolean[][] visited;

    private MazeRenderer renderer;

    public Maze getMaze(){
        return maze;
    }

    public void setRenderer(MazeRenderer rend){
        this.renderer = rend;
    }

    public GenerateMaze(Maze m) {
        this.maze = m;
        visited = new boolean[maze.rows()][maze.cols()];

        //initialize visited
        for(int r =  0; r < maze.rows(); r++){
            for(int c = 0; c < maze.cols(); c++){
                visited[r][c] = false;
            }
        }


    }

    //verifies if a cell is available
    private boolean checkCell(int row, int col){
        if(row < 0 || row >= maze.rows()){
            return false;
        }

        if(col < 0 || col >= maze.cols()){
            return false;
        }

        return !visited[row][col];
    }

    // verifies all 4 cells near a given one(north ,south, east, west)
    // and return a random one, or a null if none are valid
    private Optional<Cell> checkAll4Cells(int row, int col){
        List<Cell> cells = new ArrayList<>();

        //check south
        if(checkCell(row + 1, col)){
            cells.add(maze.getCell(row + 1, col));
        }

        //check north
        if(checkCell(row - 1, col)){
            cells.add(maze.getCell(row - 1, col));
        }

        //check east
        if(checkCell(row, col + 1)){
            cells.add(maze.getCell(row, col + 1));
        }

        //check west
        if(checkCell(row, col - 1)){
            cells.add(maze.getCell(row, col - 1));
        }

        if (cells.isEmpty()) {
            return Optional.empty();
        }

        int random = (int) (Math.random() * cells.size());
        return Optional.of(cells.get(random));
    }

    //deletes the walls between the cells
    private void deleteWalls(int row_c, int col_c, int row_n, int col_n){
        if(row_n < row_c){
            //north
            maze.setNorthWall(row_c, col_c, false);
            maze.setSouthWall(row_n, col_n, false);
        }

        if(row_n > row_c){
            //south
            maze.setSouthWall(row_c, col_c, false);
            maze.setNorthWall(row_n, col_n, false);
        }

        if(col_n < col_c){
            //west
            maze.setWestWall(row_c, col_c, false);
            maze.setEastWall(row_n, col_n, false);
        }

        if(col_n > col_c){
            //east
            maze.setEastWall(row_c, col_c, false);
            maze.setWestWall(row_n, col_n, false);
        }
    }

    private void paintCell(int row, int col, Color color){
        renderer.drawCell(row, col, color);
    }


    public void generateLinksRecursively(Cell current){
        int row_c = current.getRow();
        int col_c = current.getCol();

        //paint current cell while and wait 50 miliseconds
        paintCell(row_c, col_c, Color.WHITE);
        try{ Thread.sleep(1); } catch(InterruptedException e){ }

        Optional<Cell> cell = checkAll4Cells(row_c, col_c);

        while(cell.isPresent()){

            //paint current cell light gray if we advance to the next one
            paintCell(row_c, col_c, Color.LIGHT_GRAY);
            int row_n = cell.get().getRow();
            int col_n = cell.get().getCol();

            //mark it as visited
            visited[cell.get().getRow()][cell.get().getCol()] = true;

            //delete the wall(make the connection)
            deleteWalls(row_c, col_c, row_n, col_n);

            //call function with cell argument
            generateLinksRecursively(cell.get());

            cell = checkAll4Cells(row_c, col_c);
        }

        paintCell(row_c, col_c, Color.LIGHT_GRAY);
    }


}
