import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        int row = StdIn.readInt();
        int col = StdIn.readInt();
        block[][] inputBoard = new block[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int data = StdIn.readInt();
                inputBoard[i][j] = new block(data, 0);
                inputBoard[i][j].setMainPoint(inputBoard[i][j]);
            }
        }
        int blockNum = StdIn.readInt();
        if(blockNum>0){
        for (int i = 0; i < blockNum; i++) {
            int mainPoint = StdIn.readInt();
            int j = 0, k = 0;
            here:
            for (int l = 0; l < row; l++) {
                for (int m = 0; m < col; m++) {
                    if (mainPoint == inputBoard[l][m].getData()) {
                        j = l;
                        k = m;
                        break here;
                    }
                }
            }
            String type = StdIn.readString();
            if (type.equals("1*2")) {//检测不同类型的block
                inputBoard[j][k].setMainPoint(inputBoard[j][k]);
                inputBoard[j][k].setType(1.1);
                inputBoard[j][k + 1].setMainPoint(inputBoard[j][k]);
                inputBoard[j][k + 1].setType(1.2);
            } else if (type.equals("2*1")) {
                inputBoard[j][k].setMainPoint(inputBoard[j][k]);
                inputBoard[j][k].setType(2.1);
                inputBoard[j + 1][k].setMainPoint(inputBoard[j][k]);
                inputBoard[j + 1][k].setType(2.2);
            } else if (type.equals("2*2")) {
                inputBoard[j][k].setMainPoint(inputBoard[j][k]);
                inputBoard[j][k].setType(3.1);
                inputBoard[j][k + 1].setMainPoint(inputBoard[j][k]);
                inputBoard[j][k + 1].setType(3.2);
                inputBoard[j + 1][k].setMainPoint(inputBoard[j][k]);
                inputBoard[j + 1][k].setType(3.3);
                inputBoard[j + 1][k + 1].setMainPoint(inputBoard[j][k]);
                inputBoard[j + 1][k + 1].setType(3.4);
            }
        }}
        find find=new find(inputBoard);
        int[][] result= find.finalState(inputBoard);
        int i=0;
        while(find.open.size()!=0){
            //给open列表中的point按fn排序
            Collections.sort(find.open);
            Point topPoint=find.open.get(0);
            if(find.reach(topPoint,result)){
                Stack<Point> stack=new Stack<>();
                Point point=topPoint;
                while(point!=null){
                    stack.push(point);
                    point=point.getParent();
                }
                int step=1;
                int[][] board= find.toInt(stack.pop().getBoard());
                while(!stack.isEmpty()){
                    block[][] boardNew= stack.pop().getBoard();
                    if(!find.isSameBoard(boardNew,board)){
                        System.out.println("step:"+step);
                        step++;
                        find.printBoard(find.toInt(boardNew));
                        board=find.toInt(boardNew);
                    }

                }
                return;
            }else{
                find.close.add(topPoint);
                find.open.remove(0);
                find.renew(topPoint);
//                System.out.println(i++);
//                find.printBoard(find.toInt(topPoint.getBoard()));
            }
        }
//        Point topPoint=find.open.get(0);
//        find.renewOpenList(topPoint);
//        Collections.sort(find.open);
//
//        System.out.println("000");
    }
}
