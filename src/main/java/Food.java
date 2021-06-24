import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Food {


    private Position position;
    private List<Position> fruitList = new ArrayList<>();
    final char fruit = 'A';

    public Position getPosition() {
        return position;
    }

    public void clearFood(Terminal terminal) throws Exception {
        terminal.setCursorPosition(position.getX(), position.getY());
        terminal.putCharacter(' ');
    }

    public Position randomFoodPosition() {
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
}
