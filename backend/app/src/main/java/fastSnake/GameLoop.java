package fastSnake;

import fastSnake.State.GameState;

public class GameLoop extends Thread {
    private final int speed; // ticks per second
    private volatile boolean running = true;

    public GameLoop(int speed) {
        this.speed = speed;
    }

    @Override
    public void run() {
        while (running) {
            if (Game.getState().gameState == GameState.GameOver) {
                try {
                    Thread.sleep(100); // 잠시 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }

            Game.update(); // 게임 상태 업데이트

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
