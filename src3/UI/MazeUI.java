package UI;
import Domain.Cell;
import Repo.Maze;
import Service.GenerateMaze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazeUI extends JPanel implements MazeRenderer {
    private final GenerateMaze generatedMaze;
    private final Maze maze;
    private final Cell Start;
    private Color[][] cellColors;

    private final List<Runnable> drawCommands = new ArrayList<>();

    public void generateMaze(Cell Start){
        generatedMaze.generateLinksRecursively(Start);
    }

    public MazeUI(GenerateMaze genMaze, Cell Start) {
        this.generatedMaze = genMaze;
        this.Start = Start;
        this.maze = generatedMaze.getMaze();
        cellColors = new Color[maze.cellSize()][maze.cellSize()];

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

    public void drawMaze(){
        generateMaze(Start);
    }

    public void redraw(){
        repaint();
    }

    @Override
    public void drawCell(int row, int col, Color color) {
//        drawCommands.add(() -> {
//            Graphics g = getGraphics();
//            if(g != null){
//                g.setColor(color);
//                g.fillRect(row * maze.cellSize(), col *  maze.cellSize(), maze.cellSize(), maze.cellSize());
//            }
//        });
        cellColors[row][col] = color;
        repaint();
    }
}
