import edu.princeton.cs.algs4.Quick;

import java.util.ArrayList;

public class find {
    ArrayList<Point> open = new ArrayList<>();
    ArrayList<Point> close = new ArrayList<>();

    public find(block[][] in) {
        initialOClist(in);
        int initialSize= open.size();
        for (int i = 0; i <initialSize; i++) {
            renewOpenList(open.get(i));
        }
    }

    public int[][] finalState(block[][] in) {
        int row = in.length;
        int col = in[0].length;
        Integer[] In = new Integer[row * col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                In[i * col + j] = in[i][j].getData();
            }
        }
        Quick.sort(In);
        int count = 0;
        for (int i = 0; i < In.length; i++) {
            if (In[i] == 0) {
                count++;
            } else {
                break;
            }
        }
        int[][] result = new int[in.length][in[0].length];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i * col + j < In.length - count) {
                    result[i][j] = In[count + i * col + j];
                } else {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }

    public void initialOClist(block[][] in) {
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                if (in[i][j].getData() == 0) {//将棋盘中的零加入openlist
                    Point point = new Point(i, j, in);
                    open.add(point);
                }
            }
        }
    }

    private int measureH(Point point) {//计算H(n),对未来的估计
        block[][] board = point.getBoard();
        int row = board.length;
        int col = board[0].length;
        int Hn = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                block sourcePoint = board[i][j];
                if (sourcePoint.mainPoint == sourcePoint) {//跳过非block点
                    int data = sourcePoint.getData();
                    if (data != 0 && data != i * col + j + 1) {
                        int targetRow = (data - 1) / col;
                        int targetCol = (data - 1) % col;
                        Hn += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                    }
                }
            }
        }
        return Hn;
    }

    private block[][] copyBoard(block[][] board) {
        block[][] copy = new block[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    public boolean isSameBoard(block[][] board1, int[][] board2) {
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[0].length; j++) {
                if (board1[i][j].getData() != board2[i][j]) {
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
            if (isSameBoard(list.get(i).getBoard(), toInt(point.getBoard()))) {//&& list.get(i).getRow() == point.getRow() && list.get(i).getCol() == point.getCol()
                return true;
            }
        }
        return false;
    }

    public int[][] toInt(block[][] block) {
        int[][] copy = new int[block.length][block[0].length];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[0].length; j++) {
                copy[i][j] = block[i][j].getData();
            }
        }
        return copy;
    }

    public void addSinglePoint(Point parent, int row, int col) {//将该点加入开列表中,row和col为该移动到的位置//移动的为单点
        Point newPoint = new Point(row, col);
        block[][] newBoard = copyBoard(parent.getBoard());
        block tmp = newBoard[row][col];//记录目标点的数
        newBoard[row][col] = newBoard[parent.getRow()][parent.getCol()];
        newBoard[parent.getRow()][parent.getCol()] = tmp;
        newPoint.setBoard(newBoard);
        newPoint.setParent(parent);
        newPoint.setF(measureF(newPoint));
        newPoint.setG(newPoint.getG() + 1);
        if (isInList(close, newPoint)) {//若在close表中则代表该点不用考虑
            return;
        }
        if (!isInList(open, newPoint)) {//若不在open表中则加入//该处是否不会出现其他情况？
            open.add(newPoint);
        }
    }

    public void addPoint(Point parent, Point newPoint, block[][] newBoard) {
        newPoint.setBoard(newBoard);
        newPoint.setParent(parent);
        newPoint.setF(measureF(newPoint));
        newPoint.setG(newPoint.getG() + 1);
        if (isInList(close, newPoint)) {//若在close表中则代表该点不用考虑
            return;
        }
        if (!isInList(open, newPoint)) {//若不在open表中则加入//该处是否不会出现其他情况？
            open.add(newPoint);
        }
    }

    public void renewOpenList(Point point) {//更新该点的周围点
        int boardRow = point.getBoard().length;
        int boardCol = point.getBoard()[0].length;
        int sourceRow = point.getRow();
        int sourceCol = point.getCol();
        block[][] board = point.getBoard();
        block up = null, down = null, left = null, right = null;//记录空点的上下左右点
        if (sourceRow > 0) {
            up = board[sourceRow - 1][sourceCol];
        }
        if (sourceRow < boardRow - 1) {
            down = board[sourceRow + 1][sourceCol];
        }
        if (sourceCol > 0) {
            left = board[sourceRow][sourceCol - 1];
        }
        if (sourceCol < boardCol - 1) {
            right = board[sourceRow][sourceCol + 1];
        }
        Point newPoint1 = null;
        if (up != null) {
            block[][] newBoard = copyBoard(board);
            double type = up.getType();
            if (type == 0 && up.getData() != 0) {
                addSinglePoint(point, point.getRow() - 1, point.getCol());
            } else if ((int) type == 1) {//1*2
                if (type == 1.1) {
                    if (board[sourceRow][sourceCol + 1].getData() == 0) {
                        newPoint1 = new Point(sourceRow - 1, sourceCol);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow][sourceCol + 1];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow - 1][sourceCol];
                        newBoard[sourceRow][sourceCol + 1] = newBoard[sourceRow - 1][sourceCol + 1];
                        newBoard[sourceRow - 1][sourceCol] = tmp1;
                        newBoard[sourceRow - 1][sourceCol + 1] = tmp2;
                    }
                } else if (type == 1.2) {
                    if (board[sourceRow][sourceCol - 1].getData() == 0) {
                        newPoint1 = new Point(sourceRow - 1, sourceCol);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow][sourceCol - 1];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow - 1][sourceCol];
                        newBoard[sourceRow][sourceCol - 1] = newBoard[sourceRow - 1][sourceCol - 1];
                        newBoard[sourceRow - 1][sourceCol] = tmp1;
                        newBoard[sourceRow - 1][sourceCol - 1] = tmp2;
                    }
                }
            } else if (type == 2.2) {//2*1
                newPoint1 = new Point(sourceRow - 2, sourceCol);
                block tmp1 = newBoard[sourceRow][sourceCol];

                newBoard[sourceRow][sourceCol] = newBoard[sourceRow - 1][sourceCol];
                newBoard[sourceRow - 1][sourceCol] = newBoard[sourceRow - 2][sourceCol];
                newBoard[sourceRow - 2][sourceCol] = tmp1;
            } else if ((int) type == 3) {
                if (type == 3.3) {
                    if (board[sourceRow][sourceCol + 1].getData() == 0) {
                        newPoint1 = new Point(sourceRow - 2, sourceCol);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow][sourceCol + 1];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow - 1][sourceCol];
                        newBoard[sourceRow][sourceCol + 1] = newBoard[sourceRow - 1][sourceCol + 1];
                        newBoard[sourceRow - 1][sourceCol] = newBoard[sourceRow - 2][sourceCol];
                        newBoard[sourceRow - 1][sourceCol + 1] = newBoard[sourceRow - 2][sourceCol + 1];
                        newBoard[sourceRow - 2][sourceCol] = tmp1;
                        newBoard[sourceRow - 2][sourceCol + 1] = tmp2;
                    }
                } else if (type == 3.4) {
                    if (board[sourceRow][sourceCol - 1].getData() == 0) {
                        newPoint1 = new Point(sourceRow - 2, sourceCol);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow][sourceCol - 1];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow - 1][sourceCol];
                        newBoard[sourceRow][sourceCol - 1] = newBoard[sourceRow - 1][sourceCol - 1];
                        newBoard[sourceRow - 1][sourceCol] = newBoard[sourceRow - 2][sourceCol];
                        newBoard[sourceRow - 1][sourceCol - 1] = newBoard[sourceRow - 2][sourceCol - 1];
                        newBoard[sourceRow - 2][sourceCol] = tmp1;
                        newBoard[sourceRow - 2][sourceCol - 1] = tmp2;
                    }
                }
            }
            if (newPoint1 != null) {
                addPoint(point, newPoint1, newBoard);
//                int[][] a=toInt(newBoard);
//                printBoard(a);
            }
        }
        Point newPoint2 = null;
        if (down != null) {
            block[][] newBoard = copyBoard(board);
            double type = down.getType();
            if (type == 0 && down.getData() != 0) {
                addSinglePoint(point, point.getRow() + 1, point.getCol());
            } else if ((int) type == 1) {//1*2
                if (type == 1.1) {
                    if (board[sourceRow][sourceCol + 1].getData() == 0) {
                        newPoint2 = new Point(sourceRow + 1, sourceCol);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow][sourceCol + 1];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow + 1][sourceCol];
                        newBoard[sourceRow][sourceCol + 1] = newBoard[sourceRow + 1][sourceCol + 1];
                        newBoard[sourceRow + 1][sourceCol] = tmp1;
                        newBoard[sourceRow + 1][sourceCol + 1] = tmp2;
                    }
                } else if (type == 1.2) {
                    if (board[sourceRow][sourceCol - 1].getData() == 0) {
                        newPoint2 = new Point(sourceRow + 1, sourceCol);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow][sourceCol - 1];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow + 1][sourceCol];
                        newBoard[sourceRow][sourceCol - 1] = newBoard[sourceRow + 1][sourceCol - 1];
                        newBoard[sourceRow + 1][sourceCol] = tmp1;
                        newBoard[sourceRow + 1][sourceCol - 1] = tmp2;
                    }
                }
            } else if (type == 2.1) {//2*1
                newPoint2 = new Point(sourceRow + 2, sourceCol);
                block tmp1 = newBoard[sourceRow][sourceCol];

                newBoard[sourceRow][sourceCol] = newBoard[sourceRow + 1][sourceCol];
                newBoard[sourceRow + 1][sourceCol] = newBoard[sourceRow + 2][sourceCol];
                newBoard[sourceRow + 2][sourceCol] = tmp1;
            } else if ((int) type == 3) {
                if (type == 3.1) {
                    if (board[sourceRow][sourceCol + 1].getData() == 0) {
                        newPoint2 = new Point(sourceRow + 2, sourceCol);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow][sourceCol + 1];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow + 1][sourceCol];
                        newBoard[sourceRow][sourceCol + 1] = newBoard[sourceRow + 1][sourceCol + 1];
                        newBoard[sourceRow + 1][sourceCol] = newBoard[sourceRow + 2][sourceCol];
                        newBoard[sourceRow + 1][sourceCol + 1] = newBoard[sourceRow + 2][sourceCol + 1];
                        newBoard[sourceRow + 2][sourceCol] = tmp1;
                        newBoard[sourceRow + 2][sourceCol + 1] = tmp2;
                    }
                } else if (type == 3.2) {
                    if (board[sourceRow][sourceCol - 1].getData() == 0) {
                        newPoint2 = new Point(sourceRow + 2, sourceCol);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow][sourceCol - 1];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow + 1][sourceCol];
                        newBoard[sourceRow][sourceCol - 1] = newBoard[sourceRow + 1][sourceCol - 1];
                        newBoard[sourceRow + 1][sourceCol] = newBoard[sourceRow + 2][sourceCol];
                        newBoard[sourceRow + 1][sourceCol - 1] = newBoard[sourceRow + 2][sourceCol - 1];
                        newBoard[sourceRow + 2][sourceCol] = tmp1;
                        newBoard[sourceRow + 2][sourceCol - 1] = tmp2;
                    }
                }
            }
            if (newPoint2 != null) {
                addPoint(point, newPoint2, newBoard);
//                int[][] b=toInt(newBoard);
//                printBoard(b);
            }
        }
        Point newPoint3 = null;
        if (left != null) {
            block[][] newBoard = copyBoard(board);
            double type = left.getType();
            if (type == 0 && left.getData() != 0) {
                addSinglePoint(point, point.getRow(), point.getCol() - 1);
            } else if (type == 1.2) {//1*2
                newPoint3 = new Point(sourceRow, sourceCol - 2);
                block tmp1 = newBoard[sourceRow][sourceCol];

                newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol - 1];
                newBoard[sourceRow][sourceCol - 1] = newBoard[sourceRow][sourceCol - 2];
                newBoard[sourceRow][sourceCol - 2] = tmp1;
            } else if ((int) type == 2) {//2*1
                if (type == 2.1) {
                    if (board[sourceRow + 1][sourceCol].getData() == 0) {
                        newPoint3 = new Point(sourceRow, sourceCol - 1);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow + 1][sourceCol];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol - 1];
                        newBoard[sourceRow + 1][sourceCol] = newBoard[sourceRow + 1][sourceCol - 1];
                        newBoard[sourceRow][sourceCol - 1] = tmp1;
                        newBoard[sourceRow + 1][sourceCol - 1] = tmp2;
                    }
                } else if (type == 2.2) {
                    if (board[sourceRow - 1][sourceCol].getData() == 0) {
                        newPoint3 = new Point(sourceRow, sourceCol - 1);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow - 1][sourceCol];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol - 1];
                        newBoard[sourceRow - 1][sourceCol] = newBoard[sourceRow - 1][sourceCol - 1];
                        newBoard[sourceRow][sourceCol - 1] = tmp1;
                        newBoard[sourceRow - 1][sourceCol - 1] = tmp2;
                    }
                }
            } else if ((int) type == 3) {
                if (type == 3.2) {
                    if (board[sourceRow + 1][sourceCol].getData() == 0) {
                        newPoint3 = new Point(sourceRow, sourceCol - 2);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow + 1][sourceCol];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol - 1];
                        newBoard[sourceRow + 1][sourceCol] = newBoard[sourceRow + 1][sourceCol - 1];
                        newBoard[sourceRow][sourceCol - 1] = newBoard[sourceRow][sourceCol - 2];
                        newBoard[sourceRow + 1][sourceCol - 1] = newBoard[sourceRow + 1][sourceCol - 2];
                        newBoard[sourceRow][sourceCol - 2] = tmp1;
                        newBoard[sourceRow + 1][sourceCol - 2] = tmp2;
                    }
                } else if (type == 3.4) {
                    if (board[sourceRow - 1][sourceCol].getData() == 0) {
                        newPoint3 = new Point(sourceRow, sourceCol - 2);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow - 1][sourceCol];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol - 1];
                        newBoard[sourceRow - 1][sourceCol] = newBoard[sourceRow - 1][sourceCol - 1];
                        newBoard[sourceRow][sourceCol - 1] = newBoard[sourceRow][sourceCol - 2];
                        newBoard[sourceRow - 1][sourceCol - 1] = newBoard[sourceRow - 1][sourceCol - 2];
                        newBoard[sourceRow][sourceCol - 2] = tmp1;
                        newBoard[sourceRow - 1][sourceCol - 2] = tmp2;
                    }
                }
            }
            if (newPoint3 != null) {
                addPoint(point, newPoint3, newBoard);
//                int[][] c=toInt(newBoard);
//                printBoard(c);
            }
        }
        Point newPoint4 = null;
        if (right != null) {
            block[][] newBoard = copyBoard(board);
            double type = right.getType();
            if (type == 0 && right.getData() != 0) {
                addSinglePoint(point, point.getRow(), point.getCol() + 1);
            } else if (type == 1.1) {//1*2
                newPoint4 = new Point(sourceRow, sourceCol + 2);
                block tmp1 = newBoard[sourceRow][sourceCol];

                newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol + 1];
                newBoard[sourceRow][sourceCol + 1] = newBoard[sourceRow][sourceCol + 2];
                newBoard[sourceRow][sourceCol + 2] = tmp1;
            } else if ((int) type == 2) {//2*1
                if (type == 2.1) {
                    if (board[sourceRow +1][sourceCol].getData() == 0) {
                        newPoint4 = new Point(sourceRow, sourceCol + 1);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow + 1][sourceCol];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol + 1];
                        newBoard[sourceRow + 1][sourceCol] = newBoard[sourceRow + 1][sourceCol + 1];
                        newBoard[sourceRow][sourceCol + 1] = tmp1;
                        newBoard[sourceRow + 1][sourceCol + 1] = tmp2;
                    }
                } else if (type == 2.2) {
                    if (board[sourceRow - 1][sourceCol].getData() == 0) {
                        newPoint4 = new Point(sourceRow, sourceCol + 1);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow - 1][sourceCol];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol + 1];
                        newBoard[sourceRow - 1][sourceCol] = newBoard[sourceRow - 1][sourceCol + 1];
                        newBoard[sourceRow][sourceCol + 1] = tmp1;
                        newBoard[sourceRow - 1][sourceCol + 1] = tmp2;
                    }
                }
            } else if ((int) type == 3) {
                if (type == 3.1) {
                    if (board[sourceRow + 1][sourceCol].getData() == 0) {
                        newPoint4 = new Point(sourceRow, sourceCol+ 2);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow + 1][sourceCol];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol + 1];
                        newBoard[sourceRow + 1][sourceCol] = newBoard[sourceRow + 1][sourceCol + 1];
                        newBoard[sourceRow][sourceCol + 1] = newBoard[sourceRow][sourceCol + 2];
                        newBoard[sourceRow + 1][sourceCol + 1] = newBoard[sourceRow + 1][sourceCol + 2];
                        newBoard[sourceRow][sourceCol + 2] = tmp1;
                        newBoard[sourceRow + 1][sourceCol + 2] = tmp2;
                    }
                } else if (type == 3.3) {
                    if (board[sourceRow - 1][sourceCol].getData() == 0) {
                        newPoint4 = new Point(sourceRow, sourceCol + 2);
                        block tmp1 = newBoard[sourceRow][sourceCol];
                        block tmp2 = newBoard[sourceRow - 1][sourceCol];//记录目标点的数

                        newBoard[sourceRow][sourceCol] = newBoard[sourceRow][sourceCol + 1];
                        newBoard[sourceRow - 1][sourceCol] = newBoard[sourceRow - 1][sourceCol + 1];
                        newBoard[sourceRow][sourceCol + 1] = newBoard[sourceRow][sourceCol + 2];
                        newBoard[sourceRow - 1][sourceCol + 1] = newBoard[sourceRow - 1][sourceCol + 2];
                        newBoard[sourceRow][sourceCol + 2] = tmp1;
                        newBoard[sourceRow - 1][sourceCol + 2] = tmp2;
                    }
                }
            }
            if (newPoint4 != null) {
                addPoint(point, newPoint4, newBoard);
//                int[][] d=toInt(newBoard);
//                printBoard(d);
            }
        }
    }

    public void renew(Point point){
        block[][] board= point.getBoard();
        for(int i=0;i<board.length;i++){
            for(int j=0;j< board[0].length;j++){
                if(board[i][j].getData()==0){
                    Point newPoint=new Point(i,j,board);
                    newPoint.setParent(point);
                    renewOpenList(newPoint);
                }
            }
        }
    }

    public void printBoard(int[][] in) {
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                System.out.printf("%-6d", in[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean reach(Point point, int[][] result) {
        block[][] nowBoard = point.getBoard();
        return isSameBoard(nowBoard, result);
    }
}
