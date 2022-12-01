public class Point implements Comparable<Point> {
    private int col, row;
    private int[][] board;
    private int F, G;

    Point father;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Point(int row, int col, int[][] board) {
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

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
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

    public Point getFather() {
        return father;
    }

    public void setFather(Point father) {
        this.father = father;
    }

    @Override
    public int compareTo(Point o) {
        return this.F - o.F;
    }
}