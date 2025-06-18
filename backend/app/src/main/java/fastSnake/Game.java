package fastSnake;

import java.util.Random;
import java.util.Optional;

public class Game {
    private static State state;
    private static Snake snake;
    private static Position food;
    private static Random random = new Random();

    public Game() {
        state = new State();
        snake = new Snake();
        food = new Position(7, 12);

        for (Position pos : snake.getBody()) {
            state.setToSnake(pos);
        }

        state.setToFood(food);
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

    private static Position getRandomFood() {
        Position pos;

        do {
            int row = random.nextInt(Position.ROW_MAX);
            int col = random.nextInt(Position.COL_MAX);
            pos = new Position(row, col);
        } while (snake.getBody().contains(pos));

        return pos;
    }

    public static void update() {
        // check the next head position
        Optional<Position> newHeadOptional = snake.getNewHead();

        // if hit the wall
        if (newHeadOptional.isEmpty()) {
            state.gameState = State.GameState.GameOver;
            System.out.println("Game Over: Hit the wall");
            return;
        }

        Position newHead = newHeadOptional.get();

        // if collided with itself
        if (snake.getBody().contains(newHead)) {
            state.gameState = State.GameState.GameOver;
            System.out.println("Game Over: Collided with itself");
            return;
        }

        // if ate a food
        if (snake.getHead().equals(food)) {
            snake.move(newHead, true);
            state.setToSnake(newHead);
            state.upScore();
            food = getRandomFood();
            state.setToFood(food);
            return;
        }

        // otherwise, just move the snake
        state.setToEmpty(snake.getTail());
        state.setToSnake(newHead);
        snake.move(newHead, false);
    }
}
