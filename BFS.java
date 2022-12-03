import java.util.*;


public class BFS {
    static int[] goal = new int[30];//目标状态数组
    static int[] fa = new int[20000000];//”父亲“编号数组
    static int[] dist = new int[20000000];//距离数组
    static int[][] st = new int[20000000][30];//过程状态数组
//    static ArrayList<Integer>
    static int dx[] = {-1,1,0,0};
    static int dy[] = {0,0,-1,1};
    static int row = 0;
    static int line = 0;
    static Set<Integer> vis = new HashSet<Integer>();//存放没一步结果，防止回到原来已经走过的路。

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        row = sc.nextInt();
        line = sc.nextInt();
        for (int i = 0; i < row*line; i++) {
            st[1][i] = sc.nextInt();
        }

        for (int i = 0; i < row*line; i++) {
            if (i == row*line-1){
                goal[i] = 0;
            }
            else {
                goal[i] = i+1;
            }
        }
        int v=0;
        for (int i = 0; i < row*line; i++) {v=v*10+goal[i];}
        vis.add(v);
        int ans = bfs();
        if(dist[ans]>0) {
            System.out.println(dist[ans]);
            }
        else
            System.out.println(0);

        int[][] process = new int[dist[ans]+1][row*line];    //本来dist[ans]为所需的步数，
        print(process,ans);

    }
    /*
     * 输出 结果
     */
    private static void print(int[][] process,int ans) {
        int i=dist[ans]+1;
        while(true) {
            process[--i] = st[ans];
            if(ans==1)
                break;
            ans = fa[ans];
        }

        for (int j = 0; j < process.length; j++) {
            for (int j2 = 0; j2 < row*line; j2++) {
                System.out.print(process[j][j2]+"\t");

                if(j2%line==line-1)
                    System.out.println();

            }
            System.out.println();

        }

    }
    /*
     * 返回 最终结果 fornt 堆首 或 0未找到
     */
    private static int bfs() {
        init_lookup_table();
        int front=1,rear=2;

        while(front<rear) {
            int[] s = st[front];

            if(Arrays.equals(s, goal)) {
                fa[rear] = front;
                return front;}
            int z;
            for ( z = 0; z < row*line; z++)
                if(s[z]==0)
                    break;

            int x = z/line,y=z%line;
            for (int d = 0; d < 4; d++) {
                int newx = x+dx[d];
                int newy = y+dy[d];
                int newz = newx*line+newy;
                if(newx>=0&&newx<row&&newy>=0&&newy<line) {
                    for (int i = 0; i <row*line; i++)
                        st[rear][i] = s[i];

                    st[rear][newz] =s[z];//每次只有两个位置改变
                    st[rear][z] = s[newz];

                    dist[rear] = dist[front]+1;//再栈首的基础上加一

                    fa[rear] = front;//给任意一个 fornt，都能fornt = fa[fornt],构造过程
                    if(try_to_insert(rear)==1) rear++;
                }//end if
            }// end for
            front++;
        }//end while
        return 0;
    }
    /*
     * 初始化
     */
    private static void init_lookup_table() {
        vis.clear();
        for (int i = 0; i < 20000000; i++) {
            dist[i] = 0;
        }

    }

    private static int try_to_insert(int rear) {
        int v=0;
        for (int i = 0; i < row*line; i++) v=v*10+st[rear][i];
        if(vis.contains(v)) return 0;
        vis.add(v);
        return 1;

    }

//    private static void judgesolution(int[] array){
//        for (int i = 0;i<array.length;i++){
//            int count = 0;
//            for (int j = 0;j<i;j++){
//                if ()
//            }
//        }
//    }

}
