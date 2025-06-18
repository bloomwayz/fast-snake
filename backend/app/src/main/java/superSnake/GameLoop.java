package superSnake;

import superSnake.State.GameState;

public class GameLoop extends Thread {
    private final int speed; // ticks per second
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

            Game.update(); // update game state

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
}
