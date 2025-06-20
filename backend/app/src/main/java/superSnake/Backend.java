/**
 * SNU Computer Programming Spring 2025
 * SuperSnake Game
 * 
 * @author Junyoung Park <bloomwayz@snu.ac.kr>
 */
package superSnake;

import io.javalin.Javalin;

public class Backend {
    public static Game game = new Game();
    public static GameLoop loop = new GameLoop(5);

    public static void main(String[] args) {
        // start the game loop
        loop.start();

        // start the server
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.allowHost("http://localhost:3000");
                });
            });
        }).start(7070);

        app.post("/initialize", ctx -> {
            InitObject submission = ctx.bodyAsClass(InitObject.class);
            if (submission.initialized) {
                System.out.println("Frontend initialized!");
                loop.stopLoop();
                game = new Game();
                loop = new GameLoop(10);
                loop.start();
                ctx.result("Backend initialized!");
            }
        });

        app.get("/game-data", ctx -> {
            State gameData = Game.getState();
            ctx.json(gameData);
            // System.out.println("Game data sent!");
        });

        app.post("/set-direction", ctx -> {
            DirObject submission = ctx.bodyAsClass(DirObject.class);
            Snake.Direction direction = submission.direction;
            Game.getSnake().setDirection(direction);
            System.out.println("Direction set to: " + direction);
            ctx.result("Direction set to: " + direction);
        });

        app.post("/toggle-pause", ctx -> {
            PausedObject submission = ctx.bodyAsClass(PausedObject.class);
            if (submission.isPaused) {
                Game.getState().gameState = State.GameState.Paused;
                System.out.println("Game paused.");
            } else if (Game.getState().gameState == State.GameState.Paused) {
                Game.getState().gameState = State.GameState.Playing;
                System.out.println("Game resumed.");
            }
            ctx.result("Pause toggled.");
        });
    }

    public record InitObject(boolean initialized) {}
    public record DirObject(Snake.Direction direction) {}
    public record PausedObject(boolean isPaused) {}
}
