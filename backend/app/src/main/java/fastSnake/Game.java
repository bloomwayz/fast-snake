package fastSnake;

public class Game {
    private static State state;
    private static Snake snake;
    private static Position food;

    public Game() {
        state = new State();
        snake = new Snake();
        food = new Position(7, 12);
        
        for (int i = 0; i < snake.getLength(); i++) {
            Position pos = snake.getBody().get(i);
            state.grid[pos.x][pos.y] = State.Cell.Snake;
        }

        state.grid[food.x][food.y] = State.Cell.Food;
    }
    
    // public static void init() {
    //     Game game = new Game();
    // }

    public static State getState() {
        return state;
    }

    public static Snake getSnake() {
        return snake;
    }
}
