package superSnake;

import java.util.Random;
import java.util.Optional;

public class Game {
    private static final int LEVELS = 12;
    private static final int[] SPEED = {5, 7, 9, 11, 13, 14, 15, 16, 17, 18, 19, 20};
    private static final int[] THRESHOLD = {3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 35, 40, 50};

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

    private static Optional<Integer> checkLevel() {
        int currentLevel = state.getLevel();

        // already at the maximum level
        if (currentLevel >= LEVELS) {
            return Optional.empty();
        }

        // if pass the threshold, level up
        if (THRESHOLD[currentLevel] <= snake.getLength()) {
            state.upLevel();
            System.out.println("Level up! New level: " + state.getLevel());
            return Optional.of(SPEED[currentLevel]);
        }

        return Optional.empty();
    }

    public static Optional<Integer> update() {
        // check the next head position
        Optional<Position> newHeadOptional = snake.getNewHead();

        // if hit the wall
        if (newHeadOptional.isEmpty()) {
            state.gameState = State.GameState.GameOver;
            System.out.println("Game Over: Hit the wall");
            return Optional.empty();
        }

        Position newHead = newHeadOptional.get();

        // if collided with itself
        if (snake.getBody().contains(newHead)) {
            state.gameState = State.GameState.GameOver;
            System.out.println("Game Over: Collided with itself");
            return Optional.empty();
        }

        // if ate a food
        if (snake.getHead().equals(food)) {
            snake.move(newHead, true);
            state.setToSnake(newHead);
            state.upScore();
            food = getRandomFood();
            state.setToFood(food);
        } else {
            // otherwise, just move the snake
            state.setToEmpty(snake.getTail());
            state.setToSnake(newHead);
            snake.move(newHead, false);
        }

        return checkLevel();
    }
}
