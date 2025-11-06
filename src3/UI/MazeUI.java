package UI;
import Domain.Cell;
import Repo.Maze;
import Service.GenerateMaze;
import Service.SolverAStar;
import Service.SolverPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MazeUI extends JPanel implements MazeRenderer{
    private final GenerateMaze generatedMaze;
    private final Maze maze;
    private final Cell Start;
    private final Cell End;
    private Color[][] cellColors;
    private SolverAStar solver;
    private SolverPlayer solverPlayer;

    private final List<Runnable> drawCommands = new ArrayList<>();


    public void drawMaze(){
        generatedMaze.generateLinksRecursively(Start);
    }

    public void solveMaze(){
        this.solver = new SolverAStar(Start, End, maze, this);
        solver.solve();
        solver.reconstructPath();
    }

    public void playerSolveMaze(){
        this.solverPlayer = new SolverPlayer(Start, End, maze, this);
        runKeyStrokes();
    }

    private void runKeyStrokes(){
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        //im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "checkpoint");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");

        am.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Move Up");
                solverPlayer.checkUp();
            }
        });

        am.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Move Down");
                solverPlayer.checkDown();
            }
        });

        am.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Move Left");
                solverPlayer.checkLeft();
            }
        });

        am.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Move Right");
                solverPlayer.checkRight();
            }
        });
    }


    public MazeUI(GenerateMaze genMaze, Cell Start, Cell End) {
        this.generatedMaze = genMaze;
        this.Start = Start;
        this.End = End;
        this.maze = generatedMaze.getMaze();
        cellColors = new Color[maze.rows()][maze.cols()];

        setPreferredSize(new Dimension(maze.cols() * maze.cellSize() + 10, maze.rows() * maze.cellSize() + 10));
        setBackground(Color.GRAY);

        for(int r = 0; r < maze.rows(); r++)
            for (int c = 0; c < maze.cols(); c++)
                cellColors[r][c] = Color.GRAY;
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
        for(int r = 0; r < maze.rows(); r++) {
            for (int c = 0; c < maze.cols(); c++) {
                cellColors[r][c] = Color.GRAY;
            }
        }
        repaint();
    }

    @Override
    public void drawCell(int row, int col, Color color) {
        cellColors[row][col] = color;
        repaint();
    }
}
