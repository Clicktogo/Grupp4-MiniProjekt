import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.IOSafeTerminal;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;

public class Main {
    static HighScore highScore = new HighScore();
    static Arena arena = new Arena();
    static Snake snake = new Snake(4);
    static Food food = new Food();

    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(85, 30);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        Panel panel = new Panel();
        panel.setBackground(Color.green);

        menu(terminal, ts);
    }

    private static void newFood(Terminal terminal) throws Exception {
        Position foodPosition = food.randomFoodPosition();
        terminal.setCursorPosition(foodPosition.getX(), foodPosition.getY());
        terminal.putCharacter(food.fruit);
    }

    private static void printTextToTerminal(Terminal terminal, String text, int x, int y) throws Exception {
        if (text.equals("Game Over")) {
            terminal.setForegroundColor(TextColor.ANSI.RED);
        }
        for (int i = 0; i < text.length(); i++) {
            terminal.setCursorPosition(x + i, y);
            terminal.putCharacter(text.charAt(i));
        }
    }

    public static void initializeGame(Terminal terminal, TerminalSize ts) throws Exception {
        arena.generateHorizontalWall(terminal, ts);
        arena.generateVerticalWall(terminal, ts);

        Position start = snake.getStartPosition();

        for (int i = 0; i < snake.getLength(); i++) {
            snake.addPosition(new Position(start.getX() + i, start.getY()));
            terminal.setCursorPosition(start.getX() + i, start.getY());
            terminal.putCharacter(snake.snakeChar);
        }

        newFood(terminal);

        printTextToTerminal(terminal, "Highscore: ", 71, 5);
        int size = highScore.getScoreList().size() < 3 ? highScore.getScoreList().size() : 3;
        for (int i = 0; i < size; i++) {
            printTextToTerminal(terminal, String.format("%d. %d", i + 1, highScore.getScoreList().get(i)), 72, 6 + i);
            System.out.println(highScore.getScoreList().get(i));
        }
    }

    public static void runGame(Terminal terminal, TerminalSize ts) throws Exception {
        int foodCounter = 0;
        Movement direction = Movement.RIGHT;
        boolean continueReadingInput = true;
        String gameOver = "Game Over";
        int score = 0;

        while (continueReadingInput) {
            String scoreString = String.format("Score: %d", score);
            printTextToTerminal(terminal, scoreString, 71, 0);

            boolean end = false;
            Position snakeHead = snake.getPosition().get(snake.getPosition().size() - 1);
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
                    switch (direction) {
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
                    snake.addPosition(new Position(x, y));
                    if (food.getPosition().getX() == x && food.getPosition().getY() == y) {
                        newFood(terminal);
                        score++;
                        foodCounter = 0;
                    } else {
                        snake.removeTail(terminal);
                    }

                    terminal.setCursorPosition(x, y);
                    terminal.putCharacter(snake.snakeChar);
                    foodCounter++;

                    //TODO möjligen fixa snyggare kod
                    if (foodCounter == 30) {
                        terminal.setCursorPosition(food.getPosition().getX(), food.getPosition().getY());
                        terminal.putCharacter(' ');
                    } else if (foodCounter == 35) {
                        terminal.setCursorPosition(food.getPosition().getX(), food.getPosition().getY());
                        terminal.putCharacter('A');
                    } else if (foodCounter == 40) {
                        terminal.setCursorPosition(food.getPosition().getX(), food.getPosition().getY());
                        terminal.putCharacter(' ');
                    } else if (foodCounter == 45) {
                        terminal.setCursorPosition(food.getPosition().getX(), food.getPosition().getY());
                        terminal.putCharacter('A');
                    } else if (foodCounter == 50) {
                        {
                            food.clearFood(terminal);
                            newFood(terminal);
                            foodCounter = 0;
                        }
                    }

                        terminal.flush();
                        Thread.sleep(100);

                        for (int i = 0; i < snake.getPosition().size() - 2; i++) {
                            if (snake.getPosition().get(i).getX() == x && snake.getPosition().get(i).getY() == y) {
                                end = true;
                                continueReadingInput = false;
                                break;
                            }
                        }
                        //TODO varför funkar det inte som det ska?
                        for (Position p : arena.getWallsList()) {
                            if (x == 1 || y == 1 || y == ts.getRows() - 2 || x == ts.getColumns() - 17) {
                                end = true;
                                continueReadingInput = false;
                                break;
                            }
                        }

                    }
                    if (end) {
                        break;
                    }
                }
                while (keyStroke == null) ;

            }

        snake.clearSnake(terminal);
        food.clearFood(terminal);
        printTextToTerminal(terminal, gameOver, 32, 12);
        printTextToTerminal(terminal, "Try again [r]", 32, 15);
        printTextToTerminal(terminal, "Quit [q]", 32, 17);
        highScore.addScore(score);

        terminal.flush();
        menu(terminal, ts);
    }

    private static void menu(Terminal terminal, TerminalSize ts) throws Exception {

        printTextToTerminal(terminal, "Start [r]", 34, 15);
        printTextToTerminal(terminal, "Quit [q]", 34, 17);
        KeyStroke keyStroke = null;
        char c = ' ';
        while (keyStroke == null) {
            Thread.sleep(5);
            keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                c = keyStroke.getCharacter();
                break;
            }
        }

        switch (c) {
            case 'r':
                terminal.clearScreen();
                terminal.setForegroundColor(TextColor.ANSI.WHITE);
                initializeGame(terminal, ts);
                runGame(terminal, ts);
                break;
            case 'q':
                terminal.close();
                break;
            default:
                menu(terminal, ts);
                break;
        }
    }
}
