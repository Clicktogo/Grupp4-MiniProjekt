public class Position {
    int x;
    int y;
    int oldX;
    int oldY;

    public Position(int x, int y, int oldX, int oldY) {
        this.x = x;
        this.y = y;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }
    void setX(int x) {
        this.x = x;
    }
    int getY() {
        return y;
    }
    void setY(int y) {
        this.y = y;
    }


}
