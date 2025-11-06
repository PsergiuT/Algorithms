package Service;

import Domain.AStarCell;
import Domain.Cell;
import Repo.Maze;
import UI.MazeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.List;


public class SolverAStar {
    private static final double MAX = Double.POSITIVE_INFINITY;
    private final AStarCell Start;
    private final AStarCell End;
    private final Maze maze;
    private final MazeRenderer renderer;
    private AStarCell[][] aStarCells;
    private boolean[][] visited;
    private PriorityQueue<AStarCell> queue;
    private List<AStarCell> path = new ArrayList<>();
    private AStarCell[][] parents;


    private double distanceCalculator(int row, int col){
        //return Math.sqrt(Math.pow(End.getRow() - row, 2) + Math.pow(End.getCol() - col, 2));
        return Math.abs(End.getRow() - row) + Math.abs(End.getCol() - col); //Manhattan distance
    }

    public SolverAStar(Cell St, Cell E, Maze Maze, MazeRenderer MazeRenderer){
        Cell End = Maze.getCell(E.getRow(), E.getCol());
        Cell Start = Maze.getCell(St.getRow(), St.getCol());

        this.End = new AStarCell(End, 0.0, MAX);
        this.Start = new AStarCell(Start, distanceCalculator(Start.getRow(), Start.getCol()), 0.0);
        this.maze = Maze;
        this.renderer = MazeRenderer;
        this.queue = new PriorityQueue<>((a, b) ->
                Double.compare(a.getDistance() + a.getCost_from_start(),
                        b.getDistance() +  b.getCost_from_start()));
        this.visited = new boolean[maze.rows()][maze.cols()];
        this.parents = new AStarCell[maze.rows()][maze.cols()];

        this.aStarCells = new AStarCell[maze.rows()][maze.cols()];

        //initialize AStarCells
        for(int r = 0; r < maze.rows(); r++){
            for(int c = 0; c < maze.cols(); c++){
                aStarCells[r][c] = new AStarCell(maze.getCell(r, c), distanceCalculator(r, c), MAX);
                visited[r][c] = false;
            }
        }

        //init start cell
        aStarCells[Start.getRow()][Start.getCol()] = this.Start;
        visited[Start.getRow()][Start.getCol()] = true;
        queue.add(this.Start);

        //distance from one cell to another will be always 1
    }

    private boolean checkCell(int row, int col){
        if(row < 0 || row >= maze.rows())
            return false;

        if(col < 0 || col >= maze.cols())
            return false;

        return !visited[row][col];
    }

    private void addNeighbourCells(AStarCell current)
    {
        //check all 4 walls
        if(!current.isEastWall() && checkCell(current.getRow(), current.getCol() + 1)) {
            aStarCells[current.getRow()][current.getCol() + 1].setCost_from_start(current.getCost_from_start() + 1);
            parents[current.getRow()][current.getCol() + 1] = current;
            queue.add(aStarCells[current.getRow()][current.getCol() + 1]);
        }

        if(!current.isWestWall() && checkCell(current.getRow(), current.getCol() - 1)){
            aStarCells[current.getRow()][current.getCol() - 1].setCost_from_start(current.getCost_from_start() + 1);
            parents[current.getRow()][current.getCol() - 1] = current;
            queue.add(aStarCells[current.getRow()][current.getCol() - 1]);
        }


        if(!current.isNorthWall() && checkCell(current.getRow() - 1, current.getCol())){
            aStarCells[current.getRow() - 1][current.getCol()].setCost_from_start(current.getCost_from_start() + 1);
            parents[current.getRow() - 1][current.getCol()] = current;
            queue.add(aStarCells[current.getRow() - 1][current.getCol()]);
        }


        if(!current.isSouthWall() && checkCell(current.getRow() + 1, current.getCol())){
            aStarCells[current.getRow() + 1][current.getCol()].setCost_from_start(current.getCost_from_start() + 1);
            parents[current.getRow() + 1][current.getCol()] = current;
            queue.add(aStarCells[current.getRow() + 1][current.getCol()]);
        }

    }

    private void paint(AStarCell current){
        try{Thread.sleep(5);} catch(InterruptedException e){}
        renderer.drawCell(current.getRow(), current.getCol(), Color.GREEN);
    }



    public void solve(){
        if(queue.isEmpty())
            return;

        AStarCell current = queue.poll();
        visited[current.getRow()][current.getCol()] = true;
        paint(current);

        if(current.getRow() == this.End.getRow() && current.getCol() == this.End.getCol()) {
            return;
        }

        //add all neighbour cells
        addNeighbourCells(current);
        solve();
    }

    //reconstruct path
    public void reconstructPath(){
        AStarCell current = this.End;
        while(current != this.Start){
            path.add(current);
            current =  parents[current.getRow()][current.getCol()];
        }
        path.add(current);

        try{Thread.sleep(700);} catch(InterruptedException e){}
        for(AStarCell cell: path)
            renderer.drawCell(cell.getRow(), cell.getCol(), Color.RED);
    }
}
