public class Point implements Comparable<Point> {
    private int col, row;
    private block[][] board;
    private int F, G;

    Point parent;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Point(int row, int col, block[][] board) {
        this.row = row;
        this.col = col;
        this.board = board;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public block[][] getBoard() {
        return board;
    }

    public void setBoard(block[][] board) {
        this.board = board;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public Point getParent() {
        return parent;
    }

    public void setParent(Point parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Point o) {
        return this.F - o.F;
    }
}
