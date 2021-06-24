import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
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
        int low1 = 2;
        int high1 = 68;
        int low2 = 2;
        int high2 = 28;
        int randomHorizontal = r.nextInt(high1-low1) + low1;
        int randomVertical = r.nextInt(high2-low2) + low2;
        position = new Position(randomHorizontal, randomVertical);
        bombList.add(position);
        return position;
    }

    public Character getBombChar() {
        return bombChar;
    }

    public List<Position> getBombList() {
        return bombList;
    }

    public void clearBombList(Terminal terminal) throws Exception {
        for (Position p : bombList) {
            terminal.setCursorPosition(p.getX(), p.getY());
            terminal.putCharacter(' ');
        }
        bombList.clear();
    }
}
