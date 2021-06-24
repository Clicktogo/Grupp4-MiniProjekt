import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bomb {
    private Position position;
    private final Character bombChar = 'X';

    private List<Position> bombList = new ArrayList<>();

    public Position getPosition() {
        return position;
    }

    public void clearBomb(Terminal terminal) throws Exception {
        terminal.setCursorPosition(position.getX(), position.getY());
        terminal.putCharacter(' ');
    }

    public Position randomBombPosition() {
        Random r = new Random();
        int low1 = 1;
        int high1 = 69;
        int low2 = 1;
        int high2 = 29;
        int randomHorizontal = r.nextInt(high1-low1) + low1;
        int randomVertical = r.nextInt(high2-low2) + low2;
        position = new Position(randomHorizontal, randomVertical);
        return position;
    }

    public Character getBombChar() {
        return bombChar;
    }
}
