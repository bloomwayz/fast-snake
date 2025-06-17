package fastSnake;

public class State {
    public static final int ROWS = 15;
    public static final int COLS = 17;

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

    public Cell[][] grid;
    public int score;
    public GameState gameState;

    public State() {
        grid = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = Cell.Empty;
            }
        }

        score = 10;
        gameState = GameState.Playing;
    }
}
