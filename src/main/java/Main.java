import com.googlecode.lanterna.TerminalSize;
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

        System.out.println(terminal.getTerminalSize());

        Arena arena = new Arena();
        arena.generateHorizontalWall(terminal, ts);
        arena.generateVerticalWall(terminal, ts);
    }
}
