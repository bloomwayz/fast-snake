package fastSnake;

import java.util.List;
import java.util.ArrayList;

public class Snake {
    public enum Direction {
        Up, Down, Left, Right;
    }

    private int length;
    private Position head;
    private List<Position> body;
    private Direction direction;

    public Snake() {
        length = 3;
        head = new Position(7, 4);
        body = new ArrayList<>();
        body.add(new Position(7, 2));
        body.add(new Position(7, 3));
        body.add(new Position(7, 4));
        direction = Direction.Right;
    }

    public int getLength() {
        return length;
    }

    public Position getHead() {
        return head;
    }

    public List<Position> getBody() {
        return body;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}