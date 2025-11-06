package UI;
import Domain.Cell;
import Repo.Maze;
import Service.GenerateMaze;
import Service.Solver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazeUI extends JPanel implements MazeRenderer {
    private final GenerateMaze generatedMaze;
    private final Maze maze;
    private final Cell Start;
    private final Cell End;
    private Color[][] cellColors;
    private Solver solver;

    private final List<Runnable> drawCommands = new ArrayList<>();


    public void drawMaze(){
        generatedMaze.generateLinksRecursively(Start);
    }

    public void solveMaze(){
        this.solver = new Solver(Start, End, maze, this);
        solver.solve();
        solver.reconstructPath();
    }


    public MazeUI(GenerateMaze genMaze, Cell Start, Cell End) {
        this.generatedMaze = genMaze;
        this.Start = Start;
        this.End = End;
        this.maze = generatedMaze.getMaze();
        cellColors = new Color[maze.rows()][maze.cols()];

        setPreferredSize(new Dimension(maze.cols() * maze.cellSize() + 1, maze.rows() * maze.cellSize() + 1));
        setBackground(Color.GRAY);

        for(int r = 0; r < maze.rows(); r++) {
            for (int c = 0; c < maze.cols(); c++) {
                cellColors[r][c] = Color.GRAY;
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        for(int r = 0; r < maze.rows(); r++){
            for(int c = 0; c < maze.cols(); c++){
                int x = c *  maze.cellSize();
                int y = r *  maze.cellSize();

                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));

                g2d.setColor(cellColors[r][c]);
                g2d.fillRect(x , y , maze.cellSize(), maze.cellSize());

                g2d.setColor(Color.BLACK);
                if(maze.isNorthWall(r, c))
                    g2d.drawLine(x, y, x + maze.cellSize(), y);

                if(maze.isSouthWall(r, c))
                    g2d.drawLine(x, y + maze.cellSize(), x + maze.cellSize(), y + maze.cellSize());

                if(maze.isEastWall(r, c))
                    g2d.drawLine(x + maze.cellSize(), y, x + maze.cellSize(), y + maze.cellSize());

                if(maze.isWestWall(r, c))
                    g2d.drawLine(x, y, x,  y + maze.cellSize());
            }
        }

    }

    public void redraw(){
        repaint();
    }

    @Override
    public void drawCell(int row, int col, Color color) {
        cellColors[row][col] = color;
        repaint();
    }
}
