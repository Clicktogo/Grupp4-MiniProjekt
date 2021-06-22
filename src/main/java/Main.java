import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.util.Timer;
import java.util.TimerTask;

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

        for(int i = 0; i<snake.getLength(); i++){
            snake.addPosition(new Position(start.getX()+i,start.getY()));
            terminal.setCursorPosition(start.getX()+i,start.getY());
            terminal.putCharacter(snake.snakeChar);
        }

        Movement direction = Movement.LEFT;
        KeyStroke keyStroke = null;
        while(true){

            if(keyStroke == null){
                switch(direction){
                    case LEFT:
                        snake.addPosition(new Position(start.getX()-1,start.getY()));
                        snake.getPosition().remove(snake.getPosition().size()-1);
                        break;
                }
            }
            terminal.flush();
            Character c = keyStroke.getCharacter();
            if(c == Character.valueOf('q')){
                break;
            }

        }

        Arena arena = new Arena();
        arena.generateHorizontalWall(terminal, ts);
        arena.generateVerticalWall(terminal, ts);
    }
}
