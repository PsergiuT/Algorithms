package UI;

import java.awt.*;

public interface MazeRenderer {
    void drawCell(int row, int col, Color color);
    void repaint();
}
