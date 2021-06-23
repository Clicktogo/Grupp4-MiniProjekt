import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Main {
    static HighScore highScore = new HighScore();
    static Arena arena = new Arena();
    static Snake snake = new Snake(4);
    static Food food = new Food();

    public static void main(String[] args) throws Exception {

        TerminalSize ts = new TerminalSize(80, 30);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();

        Menu(terminal, ts);

    }

    private static void newFood(Terminal terminal ) throws Exception {
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

    public static void initializeGame(Terminal terminal, TerminalSize ts) throws Exception{
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
        for(int i=0; i<highScore.getScoreList().size(); i++){
            printTextToTerminal(terminal, String.valueOf(highScore.getScoreList().get(i)), 71, 6+i);
            System.out.println(highScore.getScoreList().get(i));
        }
    }

    public static void runGame(Terminal terminal, TerminalSize ts) throws Exception{
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
                    } else {
                        snake.removeTail(terminal);
                    }

                    terminal.setCursorPosition(x, y);
                    terminal.putCharacter(snake.snakeChar);
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
                        if (x == 1 || y == 1 || y == ts.getRows() - 2 || x == ts.getColumns() - 12) {
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
        printTextToTerminal(terminal, gameOver, 30, 12);
        highScore.addScore(score);

        terminal.flush();
        Menu(terminal, ts);
    }

    private static void Menu(Terminal terminal, TerminalSize ts) throws Exception {

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
                initializeGame(terminal, ts);
                runGame(terminal, ts);
            case 'q':
                terminal.close();

        }
    }

}
