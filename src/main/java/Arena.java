import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {

    public final char wall = '\u2588';
    List<Position> wallsList = new ArrayList<>();

    public List<Position> getWallsList() {
        return wallsList;
    }

    public void generateHorizontalWall(Terminal terminal, TerminalSize ts) throws Exception {
        for (int i = 0; i < ts.getColumns()-15; i++) {
            terminal.setCursorPosition(i, 0);
            terminal.putCharacter(wall);
            wallsList.add(new Position(i, 0));
            terminal.setCursorPosition(i, ts.getRows());
            terminal.putCharacter(wall);
            wallsList.add(new Position(i, ts.getRows()));
        }
    }
    public void generateVerticalWall(Terminal terminal, TerminalSize ts) throws Exception {
        for (int i = 0; i < ts.getRows(); i++) {
            terminal.setCursorPosition(0, i);
            terminal.putCharacter(wall);
            wallsList.add(new Position(0, i));
            terminal.setCursorPosition(ts.getColumns()-15, i);
            terminal.putCharacter(wall);
            wallsList.add(new Position(ts.getColumns()-15, i));
        }
    }
}
