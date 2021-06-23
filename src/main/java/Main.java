import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Main {

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

        Movement direction = Movement.RIGHT;
        boolean continueReadingInput = true;

        Food food = new Food();
        newFood(terminal, food);
        String gameOver = "Game Over";

        while (continueReadingInput) {
            boolean end = false;
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
                            if (direction != Movement.RIGHT)
                                direction = Movement.LEFT;
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
                        default:
                            break;
                    }
                } else {
                    switch(direction) {
                        case LEFT:
                            x--;
                            break;
                        case RIGHT:
                            x++;
                            break;
                        case UP:
                            y--;
                            break;
                        case DOWN:
                            y++;
                            break;
                    }
                    System.out.println("Food: " + food.getPosition().getX() + " " + food.getPosition().getY() );
                    System.out.println("Head: " + snakeHead.getX() + " " + snakeHead.getY());
                        snake.addPosition(new Position(x, y));
                        if(food.getPosition().getX() == x &&
                                food.getPosition().getY() == y)
                            {
                                System.out.println(snake.getPosition().size());
                            } else {
                            snake.removeTail(terminal);
                        }

                        terminal.setCursorPosition(x, y);
                        terminal.putCharacter(snake.snakeChar);
                        terminal.flush();
                        Thread.sleep(500);

                    for(int i =0; i< snake.getPosition().size()-2; i++){
                        if(snake.getPosition().get(i).getX() == x && snake.getPosition().get(i).getY() == y){
                            end = true;
                            continueReadingInput = false;
                            break;
                        }
                      }
                    }
                if(end){ break;}
            } while (keyStroke == null);

        }

        snake.clearSnake(terminal);
        terminal.setForegroundColor(TextColor.ANSI.RED);
        for (int j = 0; j < gameOver.length(); j++) {
            terminal.setCursorPosition(35 + j, 12);
            terminal.putCharacter(gameOver.charAt(j));
        }
        terminal.flush();

    }

    private static void newFood(Terminal terminal, Food food) throws Exception {
        Position foodPosition = food.randomFoodPosition();
        terminal.setCursorPosition(foodPosition.getX(), foodPosition.getY());
        terminal.putCharacter(food.fruit);
    }

}
