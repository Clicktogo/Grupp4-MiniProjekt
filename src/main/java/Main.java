import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Lets do this");

        TerminalSize ts = new TerminalSize(70, 30);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();

        System.out.println(terminal.getTerminalSize());

        Snake snake = new Snake(4);
        Position start = snake.getStartPosition();
        terminal.setCursorPosition(start.getX(),start.getY());
        terminal.putCharacter(snake.snakeChar);

        for(int i = 0; i<snake.getLength(); i++){
            terminal.setCursorPosition(start.getX()+i,start.getY());
            terminal.putCharacter(snake.snakeChar);
        }

        Arena arena = new Arena();
        arena.generateHorizontalWall(terminal, ts);
        arena.generateVerticalWall(terminal, ts);
    }
}
