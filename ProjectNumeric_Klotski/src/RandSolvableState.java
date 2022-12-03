import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class RandSolvableState {
    public static void main(String[] args) {
        int row = StdIn.readInt();
        int col = StdIn.readInt();
        int blockNum = StdIn.readInt();
        int t1 = 0, t2 = 0;
        ArrayList<Integer> mainPoint = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();
        if (blockNum > 0) {
            for (int i = 0; i < blockNum; i++) {
                int main = StdIn.readInt();
                mainPoint.add(main);
                String t = StdIn.readString();
                if (t.equals("1*2") || t.equals("2*1")) {
                    t1++;
                } else if (t.equals("2*2")) {
                    t2++;
                }
                type.add(t);
            }
            int empty = 1 + t1 + 2 * t2;
            if (row * col - empty - 2 * t1 - 4 * t2 <= 0) {
                StdOut.println("Blocks too many");
            } else {
                block[][] inputBoard = new block[row][col];
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        int now = i * col + j;
                        if (now < row * col - empty) {
                            inputBoard[i][j] = new block(now + 1, 0);
                            inputBoard[i][j].setMainPoint(inputBoard[i][j]);
                        } else {
                            inputBoard[i][j] = new block(0, 0);
                            inputBoard[i][j].setMainPoint(inputBoard[i][j]);
                        }
                    }
                }
                for (int i = 0; i < blockNum; i++) {
                    int r = (mainPoint.get(i) - 1) / col;
                    int c = (mainPoint.get(i) - 1) % col;
                    if (type.get(i).equals("1*2")) {
                        if (c + 1 == col) {
                            throw new ArrayIndexOutOfBoundsException("block in bound!");
                        } else {
                            inputBoard[r][c].setMainPoint(inputBoard[r][c]);
                            inputBoard[r][c].setType(1.1);
                            inputBoard[r][c + 1].setMainPoint(inputBoard[r][c]);
                            inputBoard[r][c + 1].setType(1.2);
                        }
                    } else if (type.get(i).equals("2*1")) {
                        if (r + 1 == row) {
                            throw new ArrayIndexOutOfBoundsException("block in bound!");
                        } else {
                            inputBoard[r][c].setMainPoint(inputBoard[r][c]);
                            inputBoard[r][c].setType(2.1);
                            inputBoard[r + 1][c].setMainPoint(inputBoard[r][c]);
                            inputBoard[r + 1][c].setType(2.2);
                        }
                    } else if (type.get(i).equals("2*2")) {
                        if (c + 1 == col || r + 1 == row) {
                            throw new ArrayIndexOutOfBoundsException("block in bound!");
                        } else {
                            inputBoard[r][c].setMainPoint(inputBoard[r][c]);
                            inputBoard[r][c].setType(3.1);
                            inputBoard[r][c + 1].setMainPoint(inputBoard[r][c]);
                            inputBoard[r][c + 1].setType(3.2);
                            inputBoard[r + 1][c].setMainPoint(inputBoard[r][c]);
                            inputBoard[r + 1][c].setType(3.3);
                            inputBoard[r + 1][c + 1].setMainPoint(inputBoard[r][c]);
                            inputBoard[r + 1][c + 1].setType(3.4);
                        }
                    }
                }
                int shuffle = 0;
                find find = new find(inputBoard);
                int rand = StdRandom.uniformInt(find.open.size());
                Point top = find.open.get(rand);
                while (shuffle < 100) {
                    int before = find.open.size();
                    find.renew(top);
                    int after = find.open.size();
                    if (after > before) {//有些步会把自己卡死而无法让list增加
                        rand = StdRandom.uniformInt(after - before) + before;
                        top = find.open.get(rand);
                    }else{
                        top=find.open.get(before-1);
                    }
                    shuffle++;
                }
                int[][] out = find.toInt(top.getBoard());
                StdOut.printf("%d %d\n", row, col);
                for (int i = 0; i < out.length; i++) {
                    for (int j = 0; j < out[0].length; j++) {
                        StdOut.printf("%d ", out[i][j]);
                    }
                    StdOut.println();
                }
                System.out.println(blockNum);
                for (int i = 0; i < blockNum; i++) {
                    StdOut.printf("%d %s\n", mainPoint.get(i), type.get(i));
                }
            }
        } else {
            block[][] inputBoard = new block[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    int now = i * col + j;
                    if (now < row * col - 1) {
                        inputBoard[i][j] = new block(now + 1, 0);
                        inputBoard[i][j].setMainPoint(inputBoard[i][j]);
                    } else {
                        inputBoard[i][j] = new block(0, 0);
                        inputBoard[i][j].setMainPoint(inputBoard[i][j]);
                    }
                }
            }
            int shuffle = 0;
            find find = new find(inputBoard);
            int rand = StdRandom.uniformInt(find.open.size());
            Point top = find.open.get(rand);
            while (shuffle < 100) {
                int before = find.open.size();
                find.renew(top);
                int after = find.open.size();
                rand = StdRandom.uniformInt(after - before) + before;
                top = find.open.get(rand);
                shuffle++;
            }
            int[][] out = find.toInt(top.getBoard());
            StdOut.printf("%d %d\n", row, col);
            for (int i = 0; i < out.length; i++) {
                for (int j = 0; j < out[0].length; j++) {
                    StdOut.printf("%d ", out[i][j]);
                }
                StdOut.println();
            }
            System.out.println(blockNum);
        }
    }
}
