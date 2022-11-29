import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Main {
    public static void main(String[] args) {
        find find=new find();
        int row= StdIn.readInt();
        int col=StdIn.readInt();
        int[][] inputBoard=new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                inputBoard[i][j]= StdIn.readInt();
            }
        }
        int[][] result= find.finalState(inputBoard);

        while(find.open.size()!=0){

        }
    }
}
