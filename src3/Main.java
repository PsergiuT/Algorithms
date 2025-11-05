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
        Maze maze = new Maze(rows, cols, cellSize);

        GenerateMaze genMaze = new GenerateMaze(maze);
        MazeUI mazeUI = new MazeUI(genMaze, Start);
        genMaze.setRenderer(mazeUI);

        JFrame frame = new JFrame("Maze Viewer");
        frame.add(mazeUI);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mazeUI.drawMaze();


    }
}
