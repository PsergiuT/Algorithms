import Domain.Cell;
import Repo.Maze;
import Service.GenerateMaze;
import UI.MazeRenderer;
import UI.MazeUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int rows = 19;
        int cols = 38;
        int cellSize = 40;

        Cell Start = new Cell(0, 0);
        Cell End = new Cell(rows - 1, cols - 1);
        Maze maze = new Maze(rows, cols, cellSize);

        GenerateMaze genMaze = new GenerateMaze(maze);
        MazeUI mazeUI = new MazeUI(genMaze, Start, End);
        genMaze.setRenderer(mazeUI);

        JFrame frame = new JFrame("Maze Viewer");
        frame.add(mazeUI);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mazeUI.drawMaze();
        try{Thread.sleep(1000);} catch(InterruptedException e){}
        mazeUI.solveMaze();
        try{Thread.sleep(1000);} catch(InterruptedException e){}
        mazeUI.redraw();
        //mazeUI.drawMaze(); - clear visited table
        try{Thread.sleep(1000);} catch(InterruptedException e){}
        mazeUI.playerSolveMaze();

    }
}
