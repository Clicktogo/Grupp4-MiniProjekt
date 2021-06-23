import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    static int moveCounter = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("Lets do this");

        TerminalSize ts = new TerminalSize(70, 30);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();

        Arena arena = new Arena();
        arena.generateHorizontalWall(terminal, ts);
        arena.generateVerticalWall(terminal, ts);

        System.out.println(terminal.getTerminalSize());

        Snake snake = new Snake(4);
        Position start = snake.getStartPosition();

        for (int i = 0; i < snake.getLength(); i++) {
            snake.addPosition(new Position(start.getX() + i, start.getY()));
            terminal.setCursorPosition(start.getX() + i, start.getY());
            terminal.putCharacter(snake.snakeChar);
        }

        Movement direction = Movement.LEFT;
        boolean continueReadingInput = true;

        while (continueReadingInput) {
            newFood(terminal);
            Position snakeHead = snake.getPosition().get(snake.getPosition().size()-1);
            int x = snakeHead.getX();
            int y = snakeHead.getY();
            KeyStroke keyStroke = null;

            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();

                if (keyStroke != null) {
                    KeyType type = keyStroke.getKeyType();
                    switch (type) {
                        case Escape:
                            terminal.exitPrivateMode();
                            return;
                        case ArrowLeft:
                            if (direction != Movement.LEFT)
                                direction = Movement.RIGHT;
                            break;
                        case ArrowRight:
                            if (direction != Movement.LEFT)
                                direction = Movement.RIGHT;
                            break;
                        case ArrowDown:
                            if (direction != Movement.UP)
                                direction = Movement.DOWN;
                            break;
                        case ArrowUp:
                            if (direction != Movement.DOWN)
                                direction = Movement.UP;
                            break;
                    }
                } else {
                        x++;

                        snake.addPosition(new Position(x, y));
                        snake.removeTail(terminal);
                        terminal.setCursorPosition(x, y);
                        terminal.putCharacter(snake.snakeChar);

                        System.out.println(snake.getPosition().size());
                    System.out.println(snake.getPosition().get(0).getX() + " " + snake.getPosition().get(0).getY());
                    System.out.println(x + " " + y);
                        terminal.flush();
                        Thread.sleep(1000);

                    }
            } while (keyStroke == null);

            Character c = keyStroke.getCharacter();

            if (c == Character.valueOf('q')) {
                continueReadingInput = false;
                System.out.println("Quit");
                terminal.close();
            }

            if (x == 100) {
                break;
            }
        }


    }

    private static void newFood(Terminal terminal) throws Exception {
        Food food = new Food();
        Position foodPosition = food.randomFoodPosition();
        terminal.setCursorPosition(foodPosition.getX(), foodPosition.getY());
        terminal.putCharacter(food.fruit);
    }

}
