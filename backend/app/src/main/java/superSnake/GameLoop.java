package superSnake;

import java.util.Optional;
import superSnake.State.GameState;

public class GameLoop extends Thread {
    private volatile int speed; // ticks per second
    private volatile boolean running = true;

    public GameLoop(int speed) {
        this.speed = speed;
    }

    @Override
    public void run() {
        while (running) {
            // when not playing
            if (Game.getState().gameState != GameState.Playing) {
                try {
                    Thread.sleep(100); // 100ms wait
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }

            Optional<Integer> speedOpt = Game.update(); // update game state
            if (speedOpt.isPresent()) {
                int newSpeed = speedOpt.get();
                setSpeed(newSpeed);
            }

            try {
                Thread.sleep(1000 / speed); // milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopLoop() {
        running = false;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
