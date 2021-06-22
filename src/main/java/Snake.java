import java.util.List;
import java.util.Random;

public class Snake {
    final char snakeChar = 'o';
    private int length;
    private List<Position> position;

    public Snake(int length) {
        this.length = length;
    }

    public List<Position> getPosition() {
        return position;
    }

    public void setPosition(List<Position> position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Position getStartPosition() {
        Random r = new Random();
        int low1 = 20;
        int high1 = 50;
        int low2 = 10;
        int high2 = 20;

        int randomHorizontal = r.nextInt(high1-low1) + low1;
        int randomVertical = r.nextInt(high2-low2) + low2;
        return new Position(randomHorizontal, randomVertical);
    }
}
