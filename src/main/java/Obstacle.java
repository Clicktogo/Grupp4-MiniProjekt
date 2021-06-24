import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.Terminal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obstacle {

    public final char wall = '\u2588';
    List<Position> obstacleList = new ArrayList<>();

    public List<Position> getObstacleList() {
        return obstacleList;
    }

    public void clearObstacle(Terminal terminal) throws Exception {
        for (Position p : obstacleList) {
            terminal.setCursorPosition(p.getX(), p.getY());
            terminal.putCharacter(' ');
        }
        obstacleList.clear();
    }

    public Position randomObstaclePosition() {
        Random r = new Random();
        int low1 = 1;
        int high1 = 69;
        int low2 = 1;
        int high2 = 29;
        int randomHorizontal = r.nextInt(high1-low1) + low1;
        int randomVertical = r.nextInt(high2-low2) + low2;
        return new Position(randomHorizontal, randomVertical);
    }

    public void addObstacle(int x, int y) throws Exception {
        for(int i = 0; i < 5; i++) {
            Position position = new Position(x + i, y);
            obstacleList.add(position);
        }
    }
    public void printObstacle(List<Position> obstacleList, Terminal terminal) throws Exception {
        for (Position p : obstacleList) {
            terminal.setCursorPosition(p.getX(), p.getY());
            terminal.putCharacter(wall);
        }
    }
}
