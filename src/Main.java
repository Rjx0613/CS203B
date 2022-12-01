import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        int row= StdIn.readInt();
        int col=StdIn.readInt();
        int[][] inputBoard=new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                inputBoard[i][j]= StdIn.readInt();
            }
        }
        find find=new find(inputBoard);
        int i=0;
        int[][] result= find.finalState(inputBoard);
        while(find.open.size()!=0){
            //给open列表中的point按fn排序
            Collections.sort(find.open);
            Point topPoint=find.open.get(0);
            if(find.reach(topPoint,result)){
                Stack<Point> stack=new Stack<>();
                Point point=topPoint;
                while(point!=null){
                    stack.push(point);
                    point=point.getFather();
                }
                int step=1;
                while(!stack.isEmpty()){
                    System.out.println("step:"+step);
                    step++;
                    find.printBoard(stack.pop().getBoard());
                }
                return;
            }else{
                find.close.add(topPoint);
                find.open.remove(0);
                find.renewOpenList(topPoint);
            }
        }
    }
}
