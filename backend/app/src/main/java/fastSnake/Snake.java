package fastSnake;

import java.util.Optional;
import java.util.Deque;
import java.util.ArrayDeque;

public class Snake {
    public enum Direction {
        Up, Down, Left, Right;
    }

    private Deque<Position> body;
    private Direction direction;
    // private boolean willTurn = false;

    public Snake() {
        body = new ArrayDeque<>();
        body.addFirst(new Position(7, 2));
        body.addFirst(new Position(7, 3));
        body.addFirst(new Position(7, 4));
        direction = Direction.Right;
    }

    public int getLength() {
        return body.size();
    }

    public Position getHead() {
        return body.getFirst();
    }

    public Position getTail() {
        return body.getLast();
    }

    public Deque<Position> getBody() {
        return body;
    }

    public void setDirection(Direction direction) {
        if (direction != this.direction) {
            // willTurn = true;
            this.direction = direction;
        }
    }

    public Optional<Position> getNewHead() {
        Position newHead;
        Position head = getHead();
        int headRow = head.row;
        int headCol = head.col;

        switch (direction) {
            case Up:
                newHead = new Position(headRow - 1, headCol);
                break;
            case Down:
                newHead = new Position(headRow + 1, headCol);
                break;
            case Left:
                newHead = new Position(headRow, headCol - 1);
                break;
            case Right:
                newHead = new Position(headRow, headCol + 1);
                break;
            default:
                throw new IllegalStateException("Unexpected direction");
        }

        if (!newHead.isValid()) {
            // willTurn = false;
            return Optional.empty();
        }

        return Optional.of(newHead);
    }

    public void move(Position newHead, boolean ateFood) {
        body.addFirst(newHead);
        if (!ateFood) {
            body.removeLast();
        }
    }
}