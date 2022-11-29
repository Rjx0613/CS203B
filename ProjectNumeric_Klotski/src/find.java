import edu.princeton.cs.algs4.Quick;

import java.util.ArrayList;
import java.util.Arrays;

public class find {
    ArrayList<Point> open = new ArrayList<>();
    ArrayList<Point> close = new ArrayList<>();

    public int[][] finalState(int[][] in) {
        int row = in.length;
        int col = in[0].length;
        Integer[] In = new Integer[row * col];
        for (int i = 0; i < row; i++) {
            for(int j=0;j<col;j++){
                In[i*col+j]=in[i][j];
            }
        }
        Quick.sort(In);
        int count = 0;
        for (int i = 0; i < In.length; i++) {
            if (In[i]==0) {
                count++;
            } else {
                break;
            }
        }
        int[][] result = new int[in.length][in[0].length];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(i*col+j<In.length-count){
                    result[i][j] = In[count+i*col+j];
                }else{
                    result[i][j]=0;
                }
            }
        }
        return result;
    }

    public void initialOClist(int[][] in) {
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                if (in[i][j] == 0) {
                    open.add(new Point(i, j, in));
                }
            }
        }
    }

    private int measureH(Point point) {//计算H(n),对未来的估计
        int[][] board = point.getBoard();
        int row = board.length;
        int col = board[0].length;
        int Hn = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int sourcePoint = board[i][j];
                if (board[i][j] !=0 && sourcePoint != i * col + j + 1) {
                    int targetRow = (sourcePoint - 1) / col;
                    int targetCol = (sourcePoint - 1) % col;
                    Hn += Math.abs(i - targetRow) + Math.abs(i - targetCol);
                }
            }
        }
        return Hn;
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return copy;
    }

    private boolean isSameBoard(int[][] board1, int[][] board2) {
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[0].length; j++) {
                if (board1[i][j]!=board2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int measureF(Point point) {//F(n)=G(n)+H(n)，G(n)是对当前消耗的测量
        return point.getG() + measureH(point);
    }

    public boolean isInList(ArrayList<Point> list, Point point) {//判断是否在开列表或闭列表
        for (int i = 0; i < list.size(); i++) {
            if (isSameBoard(list.get(i).getBoard(), point.getBoard())) {
                return true;
            }
        }
        return false;
    }

    public void addPoint(Point fatherPoint, int row, int col) {//将该点加入开列表中,row和col为该移动到的位置
        Point newPoint = new Point(row, col);
        int[][] newBoard = copyBoard(fatherPoint.getBoard());
        int tmp = newBoard[row][col];//记录目标点的数
        newBoard[row][col] = newBoard[fatherPoint.getRow()][fatherPoint.getCol()];
        newBoard[fatherPoint.getRow()][fatherPoint.getCol()] = tmp;
        newPoint.setBoard(newBoard);
        newPoint.setFather(fatherPoint);
        newPoint.setG(fatherPoint.getG() + 1);
        newPoint.setF(measureF(newPoint));
        if (isInList(close, newPoint)) {//若在close表中则代表该点不用考虑
            return;
        }
        if (!isInList(open, newPoint)) {//若不在open表中则加入//该处是否不会出现其他情况？
            open.add(newPoint);
        }
    }

    public void renewOpenList(Point point) {//更新该点的周围点
        int row = point.getBoard().length;
        int col = point.getBoard()[0].length;
        if (point.getRow() > 0) {//Up
            addPoint(point, point.getRow() - 1, point.getCol());
        }
        if (point.getRow() < row - 1) {//down
            addPoint(point, point.getRow() + 1, point.getCol());
        }
        if (point.getCol() > 0) {//left
            addPoint(point, point.getRow(), point.getCol() - 1);
        }
        if (point.getCol() < col - 1) {//right
            addPoint(point, point.getRow(), point.getCol() + 1);
        }
    }

    public boolean reach(Point point, int[][] result) {
        int[][] nowBoard = point.getBoard();
        return isSameBoard(nowBoard, result);
    }
}
