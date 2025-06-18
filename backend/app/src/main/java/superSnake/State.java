package superSnake;

import java.util.List;
import java.util.ArrayList;

public class State {
    public static final int ROWS = Position.ROW_MAX;
    public static final int COLS = Position.COL_MAX;

    public enum Cell {
        Snake ("*"),
        Food  ("@"),
        Empty (" ");

        private final String value;

        Cell(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }

    public enum GameState {
        Playing, Paused, GameOver;
    }

    public final List<List<Cell>> grid;
    private int score;
    public GameState gameState;

    public State() {
        grid = new ArrayList<>(ROWS);
        for (int i = 0; i < ROWS; i++) {
            List<Cell> row = new ArrayList<>(COLS);
            for (int j = 0; j < COLS; j++) {
                row.add(Cell.Empty);
            }
            grid.add(row);
        }

        score = 0;
        gameState = GameState.Playing;
    }

    public Cell getCell(Position pos) {
        return grid.get(pos.row).get(pos.col);
    }

    public void setCell(Position pos, Cell cell) {
        int row = pos.row;
        int col = pos.col;
        grid.get(row).set(col, cell);
    }

    public void setToSnake(Position pos) {
        setCell(pos, Cell.Snake);
    }

    public void setToFood(Position pos) {
        setCell(pos, Cell.Food);
    }

    public void setToEmpty(Position pos) {
        setCell(pos, Cell.Empty);
    }

    public int getScore() {
        return score;
    }

    public void upScore() {
        score++;
    }
}
