import java.util.*;

public class BFSgui {
    static ArrayList<int[]> goal = new ArrayList<>();//目标状态数组
    static ArrayList<Integer> dist = new ArrayList<>();//深度
    static ArrayList<Integer> fa = new ArrayList<>();//父节点编号
    static ArrayList<int[]> st = new ArrayList<>();//过程状态
    static int blocknumber;
    static ArrayList<Integer> number = new ArrayList<>();
    static ArrayList<String> direction = new ArrayList<>();
    static ArrayList<Integer> zero = new ArrayList<>();
    static Set<Integer> vis = new HashSet<>();//防止走回头路

    static int dx[] = {-1, 1, 0, 0};//上下
    static int dy[] = {0, 0, -1, 1};//左右
    static int row = 0;
    static int line = 0;

    static ArrayList<int[]> goal1 = new ArrayList<>();//目标状态数组
    static ArrayList<Integer> dist1 = new ArrayList<>();//深度
    static ArrayList<Integer> fa1 = new ArrayList<>();//父节点编号
    static ArrayList<int[]> st1 = new ArrayList<>();//过程状态
    static ArrayList<Integer> number1 = new ArrayList<>();
    static ArrayList<String> direction1 = new ArrayList<>();
    static ArrayList<Integer> zero1 = new ArrayList<>();
    static Set<Integer> vis1 = new HashSet<>();//防止走回头路

    static ArrayList<int[][]> answer = new ArrayList<>();
    static ArrayList<Integer> movenumber = new ArrayList<>();
    static ArrayList<String> movedirection = new ArrayList<>();
    
    private static void read(ArrayList<String> in){
        row = Integer.parseInt(in.get(0));
        line = Integer.parseInt(in.get(1));
        int[] a = new int[row * line];
        for (int i = 0; i < row * line; i++) {
            a[i] = Integer.parseInt(in.get(2+i));
        }
        blocknumber = Integer.parseInt(in.get(2+row*line));
        if (blocknumber == 0) {
            st.add(a);
            goal1.add(a);
            int count = 0;
            for (int i = 0; i < row * line; i++) {
                if (a[i] == 0) {
                    count = count + 1;
                }
            }
            //预计达到的最后结果
            int[] gg = new int[row * line];
            for (int i = 0; i < row * line; i++) {
                if (i > row * line - count - 1) {
                    gg[i] = 0;
                } else {
                    gg[i] = i + 1;
                }
            }
            goal.add(gg);
            st1.add(gg);
        } else {
            int[] gg = new int[row * line];
            int count = 0;
            for (int i = 0; i < row * line; i++) {
                if (a[i] == 0) {
                    count = count + 1;
                }
            }
            for (int i = 0; i < row * line; i++) {
                if (i > row * line - count - 1) {
                    gg[i] = 0;
                } else {
                    gg[i] = i + 1;
                }
            }
            goal.add(gg);
            st.add(a);
            goal1.add(a);
            st1.add(gg);
            for (int i = 0; i < blocknumber; i++) {
                String type;
                int main = 0;
                main = Integer.parseInt(in.get(3+row*line+i));
                type = in.get(3+row*line+i+1);
                if (type.equals("1*2")) {
                    int z = 0;
                    for (z = 0; z < row * line; z++)
                        if (a[z] == main)
                            break;
                    int x = z / line, y = z % line;
                    a[z] = 30 + main;
                    a[z + 1] = 30 + main + 1;
                    st.set(0, a);
                    goal1.set(0, a);
                    int[] g = goal.get(0);
                    for (int ii = 0; ii < row * line; ii++)
                        if (g[ii] == main) {
                            g[ii] = main + 30;
                            g[ii + 1] = main + 31;
                        }
                    goal.set(0, g);
                    st1.set(0, g);
                } else if (type.equals("2*1")) {
                    int z = 0;
                    for (z = 0; z < row * line; z++)
                        if (a[z] == main)
                            break;
                    int x = z / line, y = z % line;
                    a[z] = 60 + main;
                    a[z + line] = 60 + main + line;
                    st.set(0, a);
                    goal1.set(0, a);
                    int[] g = goal.get(0);
                    for (int ii = 0; ii < row * line; ii++)
                        if (g[ii] == main) {
                            g[ii] = main + 60;
                            g[ii + line] = main + 60 + line;
                        }
                    goal.set(0, g);
                    st1.set(0, g);
                } else if (type.equals("2*2")) {
                    int z = 0;
                    for (z = 0; z < row * line; z++)
                        if (a[z] == main)
                            break;
                    int x = z / line, y = z % line;
                    a[z] = 90 + main;
                    a[z + 1] = 90 + main + 1;
                    a[z + line] = 90 + main + line;
                    a[z + line + 1] = 90 + main + line + 1;
                    st.set(0, a);
                    goal1.set(0, a);
                    int[] g = goal.get(0);
                    for (int ii = 0; ii < row * line; ii++)
                        if (g[ii] == main) {
                            g[ii] = main + 90;
                            g[ii + 1] = main + 91;
                            g[ii + line] = main + line + 90;
                            g[ii + line + 1] = main + line + 91;
                        }
                    goal.set(0, g);
                    st1.set(0, g);
                }
            }
        }
        int v = 0;
        for (int j = 0; j < row * line; j++) {
            v = v * 10 + st1.get(0)[j];
        }
        vis1.add(v);
        v = 0;
        for (int i = 0; i < row * line; i++) {
            v = v * 10 + a[i];
        }
        vis.add(v);

        //ans代表最后还原的节点值
        int ans1, ans2;
        String ans = bfs();
        String[] arr1 = ans.split(" ");
        ans1 = Integer.parseInt(arr1[0]);
        ans2 = Integer.parseInt(arr1[1]);
        //打印出所需要的移动步数
        if (dist.get(ans1) > 0) {
            System.out.println(dist.get(ans1) + dist1.get(ans2));
        } else
            System.out.println(0);
        //打印每一步的运行结果
        int[][] process = new int[dist.get(ans1) + 1][row * line];
        int[][] process1 = new int[dist1.get(ans2) + 1][row * line];
        print(process, process1, ans1, ans2);
    }

    private static int[][] print(int steps){
        System.out.print(movenumber.get(steps-1)+ "\t");
        System.out.println(movedirection.get(steps-1));
        return answer.get(steps - 1);
    }

    //该方法用于打印每一步的步骤
    private static void print(int[][] process, int[][] process1, int ans1, int ans2) {
        int i = dist.get(ans1);
        int[] nn = new int[dist.get(ans1)];
        String[] dd = new String[dist.get(ans1)];
        int aa[][] = new int[row][line];
        while (true) {
            if (i == 0) {
                process[i] = st.get(ans1);
                for (int j =0 ; j< row ;j++){
                    for (int k = 0;k<line;k++){
                        aa[j][k] = process[i][j*line+k];
                    }
                }
                answer.add(aa);
                i = i - 1;
                if (ans1 == 0)
                    break;
                ans1 = fa.get(ans1) - 1;
            } else {
                process[i] = st.get(ans1);
                for (int j =0 ; j< row ;j++){
                    for (int k = 0;k<line;k++){
                        aa[j][k] = process[i][j*line+k];
                    }
                }
                answer.add(aa);
                nn[i - 1] = number.get(ans1);
                dd[i - 1] = direction.get(ans1);
                i = i - 1;
                if (ans1 == 0)
                    break;
                ans1 = fa.get(ans1) - 1;
            }
        }
        for (int j =1 ; j< process.length  ;j++){
            movenumber.add(nn[j-1]);
        }
        for (int j =1 ; j< process.length;j++){
            movedirection.add(dd[j-1]);
        }

        int ii = dist1.get(ans2);
        int[] nnn = new int[dist1.get(ans2)];
        String[] ddd = new String[dist1.get(ans2)];
        while (true) {
            if (ii == 0) {
                process1[ii] = st1.get(ans2);
                for (int j =0 ; j< row ;j++){
                    for (int k = 0;k<line;k++){
                        aa[j][k] = process1[ii][j*line+k];
                    }
                }
                answer.add(aa);
                ii = ii - 1;
                if (ans2 == 0)
                    break;
                ans2 = fa1.get(ans2) - 1;
            } else {
                process1[ii] = st1.get(ans2);
                for (int j =0 ; j< row ;j++){
                    for (int k = 0;k<line;k++){
                        aa[j][k] = process1[ii][j*line+k];
                    }
                }
                answer.add(aa);
                nnn[ii - 1] = number1.get(ans2);
                ddd[ii - 1] = direction1.get(ans2);
                ii = ii - 1;
                if (ans2 == 0)
                    break;
                ans2 = fa1.get(ans2) - 1;
            }
        }
        for (int j = process1.length - 1; j > 0; j--){
            movenumber.add(nnn[j-1]);
        }
        for (int j = process1.length - 1; j > 0; j--){
            movedirection.add(ddd[j-1]);
        }


    }

    //广度搜索找到可行结果
    private static String bfs() {
        if (blocknumber == 0) {
            int front = 0, rear = 1;
            int front1 = 0, rear1 = 1;
            dist.add(0);
            fa.add(0);
            direction.add("0");
            number.add(0);
            dist1.add(0);
            fa1.add(0);
            direction1.add("0");
            number1.add(0);
            while (front < rear && front1 < rear1) {

                //检查数组是否已与结果一致
                int[] ss = st1.get(front1);

                //找到0所在的位置
                int zz;
                for (zz = 0; zz < row * line; zz++)
                    if (ss[zz] == 0)
                        break;
                //从上下左右的顺序进行广搜
                int xx = zz / line, yy = zz % line;
                for (int d = 0; d < 4; d++) {
                    int newx = xx + dx[d];
                    int newy = yy + dy[d];
                    int newz = newx * line + newy;
                    if (newx >= 0 && newx < row && newy >= 0 && newy < line) {
                        //b[]数组代表移动一次之后的棋盘情况
                        int[] b = new int[row * line];
                        if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                        b[newz] = ss[zz];
                        b[zz] = ss[newz];

                        st1.add(rear1, b);
                        dist1.add(rear1, dist1.get(front) + 1);
                        fa1.add(rear1, front1 + 1);
                        number1.add(rear1, ss[newz]);
                        if (d == 0) {
                            direction1.add(rear1, "U");
                        } else if (d == 1) {
                            direction1.add(rear1, "D");
                        } else if (d == 2) {
                            direction1.add(rear1, "L");
                        } else if (d == 3) {
                            direction1.add(rear1, "R");
                        }
                        if (judge1(rear1) == 0) {
                            for (int i = 0; i < st.size(); i++) {
                                if (Arrays.equals(st1.get(rear1), st.get(i))) {
                                    return i + " " + rear1;
                                }
                            }
                        }
                        if (try_to_insert1(rear1) == 1) {
                            rear1 = rear1 + 1;
                        }
                    }
                }

                front1 = front1 + 1;

                //检查数组是否已与结果一致
                int[] s = st.get(front);
                if (Arrays.equals(s, goal.get(0))) {
                    fa.set(rear, front);
                    return front + " " + 0;
                }

                //找到0所在的位置
                int z;
                for (z = 0; z < row * line; z++)
                    if (s[z] == 0)
                        break;
                //从上下左右的顺序进行广搜
                int x = z / line, y = z % line;
                for (int d = 0; d < 4; d++) {
                    int newx = x + dx[d];
                    int newy = y + dy[d];
                    int newz = newx * line + newy;
                    if (newx >= 0 && newx < row && newy >= 0 && newy < line) {
                        //b[]数组代表移动一次之后的棋盘情况
                        int[] b = new int[row * line];
                        if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                        b[newz] = s[z];
                        b[z] = s[newz];

                        st.add(rear, b);
                        dist.add(rear, dist.get(front) + 1);
                        fa.add(rear, front + 1);
                        number.add(rear, s[newz]);
                        if (d == 0) {
                            direction.add(rear, "D");
                        } else if (d == 1) {
                            direction.add(rear, "U");
                        } else if (d == 2) {
                            direction.add(rear, "R");
                        } else if (d == 3) {
                            direction.add(rear, "L");
                        }
                        if (judge(rear) == 0) {
                            for (int i = 0; i < st1.size(); i++) {
                                if (Arrays.equals(st.get(rear), st1.get(i))) {
                                    return rear + " " + i;
                                }
                            }
                        }
                        if (Arrays.equals(st.get(rear), goal.get(0))) {
                            return rear + " " + 0;
                        }
                        if (try_to_insert(rear) == 1) {
                            rear = rear + 1;
                        }
                    }
                }
                front = front + 1;

            }
            System.out.println("end");
            return "0" + " " + "0";
        } else {
            int front = 0, rear = 1;
            int front1 = 0, rear1 = 1;
            dist.add(0);
            fa.add(0);
            direction.add("0");
            number.add(0);
            dist1.add(0);
            fa1.add(0);
            direction1.add("0");
            number1.add(0);
            while (front < rear && front1 < rear1) {
                //检查数组是否已与结果一致
                int[] ss = st1.get(front1);
                //找到0所在的位置
                zero1.clear();
                int zz;
                for (zz = 0; zz < row * line; zz++)
                    if (ss[zz] == 0)
                        zero1.add(zz);

                //从上下左右的顺序进行广搜
                for (int i = 0; i < zero1.size(); i++) {
                    int x = zero1.get(i) / line, y = zero1.get(i) % line;
                    for (int d = 0; d < 4; d++) {
                        int newx = x + dx[d];
                        int newy = y + dy[d];
                        int newz = newx * line + newy;
                        if (newx >= 0 && newx < row && newy >= 0 && newy < line) {
                            if (d == 0) {
                                direction1.add(rear1, "U");
                                if (ss[newz] > 30 && ss[newz] < 60) {
                                    if (newz + 1 < row * line && zero1.get(i) + 1 < row * line) {
                                        if (ss[newz + 1] == ss[newz] + 1 && ss[zero1.get(i) + 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[zero1.get(i)];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + 1] = ss[zero1.get(i) + 1];
                                            b[zero1.get(i) + 1] = ss[newz + 1];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                    if (newz - 1 >= 0 && zero1.get(i) - 1 >= 0) {
                                        if (ss[newz - 1] == ss[newz] - 1 && zero1.get(i) - 1 >= 0) {
                                            if (ss[zero1.get(i) - 1] == 0) {
                                                int[] b = new int[row * line];
                                                if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                                b[newz] = ss[zero1.get(i)];
                                                b[zero1.get(i)] = ss[newz];
                                                b[newz - 1] = ss[zero1.get(i) - 1];
                                                b[zero1.get(i) - 1] = ss[newz - 1];
                                                st1.add(rear1, b);
                                                dist1.add(rear1, dist1.get(front1) + 1);
                                                fa1.add(rear1, front1 + 1);
                                                number1.add(rear1, ss[newz - 1]);
                                                if (judge1(rear1) == 0) {
                                                    for (int j = 0; j < st.size(); j++) {
                                                        if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                            return j + " " + rear1;
                                                        }
                                                    }
                                                }
                                                if (try_to_insert1(rear1) == 1) {
                                                    rear1 = rear1 + 1;
                                                }
                                            }
                                        }
                                    }
                                } else if (ss[newz] > 60 && ss[newz] < 90) {
                                    if (newz - line >= 0) {
                                        if (ss[newz - line] == ss[newz] - line) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz - line];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - line] = ss[zero1.get(i)];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - line]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                    if (newz - 1 >= 0 && zero1.get(i) - 1 >= 0) {
                                        if (ss[newz - 1] == ss[newz] - 1 && ss[zero1.get(i) - 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz - line];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - 1] = ss[newz - 1 - line];
                                            b[zero1.get(i) - 1] = ss[newz - 1];
                                            b[newz - 1 - line] = ss[zero1.get(i)];
                                            b[newz - line] = ss[zero1.get(i) - 1];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - 1 - line]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else if (ss[newz] > 90) {
                                    if (newz + 1 < row * line && zero1.get(i) + 1 < row * line) {
                                        if (ss[newz + 1] == ss[newz] + 1 && ss[zero1.get(i) + 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz - line];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + 1] = ss[newz + 1 - line];
                                            b[zero1.get(i) + 1] = ss[newz + 1];
                                            b[newz - line] = ss[zero1.get(i)];
                                            b[newz + 1 - line] = ss[zero1.get(i) + 1];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - line]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else {
                                    int[] b = new int[row * line];
                                    if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                    b[newz] = ss[zero1.get(i)];
                                    b[zero1.get(i)] = ss[newz];
                                    st1.add(rear1, b);
                                    dist1.add(rear1, dist1.get(front1) + 1);
                                    fa1.add(rear1, front1 + 1);
                                    number1.add(rear1, ss[newz]);
                                    if (judge1(rear1) == 0) {
                                        for (int j = 0; j < st.size(); j++) {
                                            if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                return j + " " + rear1;
                                            }
                                        }
                                    }
                                    if (try_to_insert1(rear1) == 1) {
                                        rear1 = rear1 + 1;
                                    }
                                }
                            }
                            if (d == 1) {
                                direction1.add(rear1, "D");
                                if (ss[newz] > 30 && ss[newz] < 60) {
                                    if (newz + 1 < row * line && zero1.get(i) + 1 < row * line) {
                                        if (ss[newz + 1] == ss[newz] + 1 && ss[zero1.get(i) + 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[zero1.get(i)];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + 1] = ss[zero1.get(i) + 1];
                                            b[zero1.get(i) + 1] = ss[newz + 1];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                    if (newz - 1 >= 0 && zero1.get(i) - 1 >= 0) {
                                        if (ss[newz - 1] == ss[newz] - 1 && ss[zero1.get(i) - 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[zero1.get(i)];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - 1] = ss[zero1.get(i) - 1];
                                            b[zero1.get(i) - 1] = ss[newz - 1];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - 1]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else if (ss[newz] > 60 && ss[newz] < 90) {
                                    if (newz + line < row * line) {
                                        if (ss[newz + line] == ss[newz] + line) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz + line];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + line] = ss[zero1.get(i)];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else if (ss[newz] > 90) {
                                    if (newz - 1 >= 0 && zero1.get(i) - 1 >= 0) {
                                        if (ss[newz - 1] == ss[newz] - 1 && ss[zero1.get(i) - 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz + line];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - 1] = ss[newz - 1 + line];
                                            b[zero1.get(i) - 1] = ss[newz - 1];
                                            b[newz + line] = ss[zero1.get(i)];
                                            b[newz - 1 + line] = ss[zero1.get(i) - 1];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - 1]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                    if (newz + 1 < row * line && zero1.get(i) + 1 < row * line) {
                                        if (ss[newz + 1] == ss[newz] + 1 && ss[zero1.get(i) + 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz + line];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + 1] = ss[newz + 1 + line];
                                            b[zero1.get(i) + 1] = ss[newz + 1];
                                            b[newz + line] = ss[zero1.get(i)];
                                            b[newz + 1 + line] = ss[zero1.get(i) + 1];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else {
                                    int[] b = new int[row * line];
                                    if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                    b[newz] = ss[zero1.get(i)];
                                    b[zero1.get(i)] = ss[newz];
                                    st1.add(rear1, b);
                                    dist1.add(rear1, dist1.get(front1) + 1);
                                    fa1.add(rear1, front1 + 1);
                                    number1.add(rear1, ss[newz]);
                                    if (judge1(rear1) == 0) {
                                        for (int j = 0; j < st.size(); j++) {
                                            if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                return j + " " + rear1;
                                            }
                                        }
                                    }
                                    if (try_to_insert1(rear1) == 1) {
                                        rear1 = rear1 + 1;
                                    }
                                }
                            }
                            if (d == 2) {
                                direction1.add(rear1, "L");
                                if (ss[newz] > 30 && ss[newz] < 60) {
                                    if (newz - 1 >= 0) {
                                        if (ss[newz - 1] == ss[newz] - 1) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz - 1];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - 1] = ss[zero1.get(i)];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - 1]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else if (ss[newz] > 60 && ss[newz] < 90) {
                                    if (newz + line < row * line && zero1.get(i) + line < row * line) {
                                        if (ss[newz + line] == ss[newz] + line && ss[zero1.get(i) + line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[zero1.get(i)];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + line] = ss[zero1.get(i) + line];
                                            b[zero1.get(i) + line] = ss[newz + line];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                    if (newz - line >= 0 && zero1.get(i) - line >= 0) {
                                        if (ss[newz - line] == ss[newz] - line && ss[zero1.get(i) - line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[zero1.get(i)];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - line] = ss[zero1.get(i) - line];
                                            b[zero1.get(i) - line] = ss[newz - line];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - line]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else if (ss[newz] > 90) {
                                    if (newz + line < row * line && zero1.get(i) + line < row * line) {
                                        if (ss[newz + line] == ss[newz] + line && ss[zero1.get(i) + line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz - 1];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - 1] = ss[zero1.get(i)];
                                            b[zero1.get(i) + line] = ss[newz + line];
                                            b[newz + line] = ss[newz + line - 1];
                                            b[newz - 1 + line] = ss[zero1.get(i) + line];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                    if (newz - line >= 0 && zero1.get(i) - line >= 0) {
                                        if (ss[newz - line] == ss[newz] - line && ss[zero1.get(i) - line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz - 1];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - 1] = ss[zero1.get(i)];
                                            b[zero1.get(i) - line] = ss[newz - line];
                                            b[newz - line] = ss[newz - line - 1];
                                            b[newz - 1 - line] = ss[zero1.get(i) - line];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - line]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else {
                                    int[] b = new int[row * line];
                                    if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                    b[newz] = ss[zero1.get(i)];
                                    b[zero1.get(i)] = ss[newz];
                                    st1.add(rear1, b);
                                    dist1.add(rear1, dist1.get(front1) + 1);
                                    fa1.add(rear1, front1 + 1);
                                    number1.add(rear1, ss[newz]);
                                    if (judge1(rear1) == 0) {
                                        for (int j = 0; j < st.size(); j++) {
                                            if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                return j + " " + rear1;
                                            }
                                        }
                                    }
                                    if (try_to_insert1(rear1) == 1) {
                                        rear1 = rear1 + 1;
                                    }
                                }
                            }
                            if (d == 3) {
                                direction1.add(rear1, "R");
                                if (ss[newz] > 30 && ss[newz] < 60) {
                                    if (newz + 1 < row * line && zero1.get(i) + 1 < row * line) {
                                        if (ss[newz + 1] == ss[newz] + 1) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz + 1];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + 1] = ss[zero1.get(i)];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else if (ss[newz] > 60 && ss[newz] < 90) {
                                    if (newz + line < row * line && zero1.get(i) + line < row * line) {
                                        if (ss[newz + line] == ss[newz] + line && ss[zero1.get(i) + line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[zero1.get(i)];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + line] = ss[zero1.get(i) + line];
                                            b[zero1.get(i) + line] = ss[newz + line];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                    if (newz - line >= 0 && zero1.get(i) - line >= 0) {
                                        if (ss[newz - line] == ss[newz] - line && ss[zero1.get(i) - line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[zero1.get(i)];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz - line] = ss[zero1.get(i) - line];
                                            b[zero1.get(i) - line] = ss[newz - line];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - line]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else if (ss[newz] > 90) {
                                    if (newz + line < row * line && zero1.get(i) + line < row * line) {
                                        if (ss[newz + line] == ss[newz] + line && ss[zero1.get(i) + line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz + 1];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + 1] = ss[zero1.get(i)];
                                            b[zero1.get(i) + line] = ss[newz + line];
                                            b[newz + line] = ss[newz + line + 1];
                                            b[newz + 1 + line] = ss[zero1.get(i) + line];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                    if (newz - line >= 0 && zero.get(i) - line >= 0) {
                                        if (ss[newz - line] == ss[newz] - line && ss[zero1.get(i) - line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                            b[newz] = ss[newz + 1];
                                            b[zero1.get(i)] = ss[newz];
                                            b[newz + 1] = ss[zero1.get(i)];
                                            b[zero1.get(i) - line] = ss[newz - line];
                                            b[newz - line] = ss[newz - line + 1];
                                            b[newz + 1 - line] = ss[zero1.get(i) - line];
                                            st1.add(rear1, b);
                                            dist1.add(rear1, dist1.get(front1) + 1);
                                            fa1.add(rear1, front1 + 1);
                                            number1.add(rear1, ss[newz - line]);
                                            if (judge1(rear1) == 0) {
                                                for (int j = 0; j < st.size(); j++) {
                                                    if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                        return j + " " + rear1;
                                                    }
                                                }
                                            }
                                            if (try_to_insert1(rear1) == 1) {
                                                rear1 = rear1 + 1;
                                            }
                                        }
                                    }
                                } else {
                                    int[] b = new int[row * line];
                                    if (row * line >= 0) System.arraycopy(ss, 0, b, 0, row * line);
                                    b[newz] = ss[zero1.get(i)];
                                    b[zero1.get(i)] = ss[newz];
                                    st1.add(rear1, b);
                                    dist1.add(rear1, dist1.get(front1) + 1);
                                    fa1.add(rear1, front1 + 1);
                                    number1.add(rear1, ss[newz]);
                                    if (judge1(rear1) == 0) {
                                        for (int j = 0; j < st.size(); j++) {
                                            if (Arrays.equals(st1.get(rear1), st.get(j))) {
                                                return j + " " + rear1;
                                            }
                                        }
                                    }
                                    if (try_to_insert1(rear1) == 1) {
                                        rear1 = rear1 + 1;
                                    }
                                }
                            }
                        }
                    }
                }
                front1 = front1 + 1;


                //检查数组是否已与结果一致
                int[] s = st.get(front);
                if (Arrays.equals(s, goal.get(0))) {
                    fa.set(rear, front);
                    return front + " " + 0;
                }

                //找到0所在的位置
                zero.clear();
                int z;
                for (z = 0; z < row * line; z++)
                    if (s[z] == 0)
                        zero.add(z);

                //从上下左右的顺序进行广搜
                for (int i = 0; i < zero.size(); i++) {
                    int x = zero.get(i) / line, y = zero.get(i) % line;
                    for (int d = 0; d < 4; d++) {
                        int newx = x + dx[d];
                        int newy = y + dy[d];
                        int newz = newx * line + newy;
                        if (newx >= 0 && newx < row && newy >= 0 && newy < line) {
                            if (d == 0) {
                                direction.add(rear, "D");
                                if (s[newz] > 30 && s[newz] < 60) {
                                    if (newz + 1 < row * line && zero.get(i) + 1 < row * line) {
                                        if (s[newz + 1] == s[newz] + 1 && s[zero.get(i) + 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[zero.get(i)];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + 1] = s[zero.get(i) + 1];
                                            b[zero.get(i) + 1] = s[newz + 1];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (Arrays.equals(b, goal.get(0))) {
                                                return rear + " " + 0;
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                    if (newz -1 >= 0) {
                                        if (s[newz - 1] == s[newz] - 1 && zero.get(i) - 1 >= 0) {
                                            if (s[zero.get(i) - 1] == 0) {
                                                int[] b = new int[row * line];
                                                if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                                b[newz] = s[zero.get(i)];
                                                b[zero.get(i)] = s[newz];
                                                b[newz - 1] = s[zero.get(i) - 1];
                                                b[zero.get(i) - 1] = s[newz - 1];
                                                st.add(rear, b);
                                                dist.add(rear, dist.get(front) + 1);
                                                fa.add(rear, front + 1);
                                                number.add(rear, s[newz - 1]);
                                                if (judge(rear) == 0) {
                                                    for (int j = 0; j < st1.size(); j++) {
                                                        if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                            return rear + " " + j;
                                                        }
                                                    }
                                                }
                                                if (try_to_insert(rear) == 1) {
                                                    rear = rear + 1;
                                                }
                                            }
                                        }
                                    }
                                } else if (s[newz] > 60 && s[newz] < 90) {
                                    if (newz - line >= 0) {
                                        if (s[newz - line] == s[newz] - line) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz - line];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - line] = s[zero.get(i)];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - line]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                    if (newz - 1 >= 0 && zero.get(i) - 1 >= 0) {
                                        if (s[newz - 1] == s[newz] - 1 && s[zero.get(i) - 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz - line];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - 1] = s[newz - 1 - line];
                                            b[zero.get(i) - 1] = s[newz - 1];
                                            b[newz - 1 - line] = s[zero.get(i)];
                                            b[newz - line] = s[zero.get(i) - 1];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - 1 - line]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else if (s[newz] > 90) {
                                    if (newz + 1 < row * line && zero.get(i) + 1 < row * line) {
                                        if (s[newz + 1] == s[newz] + 1 && s[zero.get(i) + 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz - line];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + 1] = s[newz + 1 - line];
                                            b[zero.get(i) + 1] = s[newz + 1];
                                            b[newz - line] = s[zero.get(i)];
                                            b[newz + 1 - line] = s[zero.get(i) + 1];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - line]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else {
                                    int[] b = new int[row * line];
                                    if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                    b[newz] = s[zero.get(i)];
                                    b[zero.get(i)] = s[newz];
                                    st.add(rear, b);
                                    dist.add(rear, dist.get(front) + 1);
                                    fa.add(rear, front + 1);
                                    number.add(rear, s[newz]);
                                    if (try_to_insert(rear) == 1) {
                                        rear = rear + 1;
                                    }
                                }
                            }

                            if (d == 1) {
                                direction.add(rear, "U");
                                if (s[newz] > 30 && s[newz] < 60) {
                                    if (newz+1<row*line && zero.get(i)<row*line) {
                                        if (s[newz + 1] == s[newz] + 1 && s[zero.get(i) + 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[zero.get(i)];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + 1] = s[zero.get(i) + 1];
                                            b[zero.get(i) + 1] = s[newz + 1];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                    if (newz - 1 >= 0 && zero.get(i) - 1 >= 0) {
                                        if (s[newz - 1] == s[newz] - 1 && s[zero.get(i) - 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[zero.get(i)];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - 1] = s[zero.get(i) - 1];
                                            b[zero.get(i) - 1] = s[newz - 1];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - 1]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else if (s[newz] > 60 && s[newz] < 90) {
                                    if (newz+line<row*line ) {
                                        if (s[newz + line] == s[newz] + line) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz + line];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + line] = s[zero.get(i)];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else if (s[newz] > 90) {
                                    if (newz - 1 >= 0 && zero.get(i) - 1 >= 0) {
                                        if (s[newz - 1] == s[newz] - 1 && s[zero.get(i) - 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz + line];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - 1] = s[newz - 1 + line];
                                            b[zero.get(i) - 1] = s[newz - 1];
                                            b[newz + line] = s[zero.get(i)];
                                            b[newz - 1 + line] = s[zero.get(i) - 1];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - 1]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                    if (newz + 1 < row * line && zero.get(i) + 1 < row * line) {
                                        if (s[newz + 1] == s[newz] + 1 && s[zero.get(i) + 1] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz + line];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + 1] = s[newz + 1 + line];
                                            b[zero.get(i) + 1] = s[newz + 1];
                                            b[newz + line] = s[zero.get(i)];
                                            b[newz + 1 + line] = s[zero.get(i) + 1];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else {
                                    int[] b = new int[row * line];
                                    if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                    b[newz] = s[zero.get(i)];
                                    b[zero.get(i)] = s[newz];
                                    st.add(rear, b);
                                    dist.add(rear, dist.get(front) + 1);
                                    fa.add(rear, front + 1);
                                    number.add(rear, s[newz]);
                                    if (try_to_insert(rear) == 1) {
                                        rear = rear + 1;
                                    }
                                }
                            }
                            if (d == 2) {
                                direction.add(rear, "R");
                                if (s[newz] > 30 && s[newz] < 60) {
                                    if (newz-1>=0) {
                                        if (s[newz - 1] == s[newz] - 1) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz - 1];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - 1] = s[zero.get(i)];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - 1]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else if (s[newz] > 60 && s[newz] < 90) {
                                    if (newz + line < row * line && zero.get(i) + line < row * line) {
                                        if (s[newz + line] == s[newz] + line && s[zero.get(i) + line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[zero.get(i)];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + line] = s[zero.get(i) + line];
                                            b[zero.get(i) + line] = s[newz + line];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                    if (newz - line >= 0 && zero.get(i) - line >= 0) {
                                        if (s[newz - line] == s[newz] - line && s[zero.get(i) - line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[zero.get(i)];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - line] = s[zero.get(i) - line];
                                            b[zero.get(i) - line] = s[newz - line];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - line]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else if (s[newz] > 90) {
                                    if (newz + line < row * line && zero.get(i) + line < row * line) {
                                        if (s[newz + line] == s[newz] + line && s[zero.get(i) + line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz - 1];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - 1] = s[zero.get(i)];
                                            b[zero.get(i) + line] = s[newz + line];
                                            b[newz + line] = s[newz + line - 1];
                                            b[newz - 1 + line] = s[zero.get(i) + line];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                    if (newz - line >= 0 && zero.get(i) - line >= 0) {
                                        if (s[newz - line] == s[newz] - line && s[zero.get(i) - line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz - 1];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - 1] = s[zero.get(i)];
                                            b[zero.get(i) - line] = s[newz - line];
                                            b[newz - line] = s[newz - line - 1];
                                            b[newz - 1 - line] = s[zero.get(i) - line];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - line]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else {
                                    int[] b = new int[row * line];
                                    if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                    b[newz] = s[zero.get(i)];
                                    b[zero.get(i)] = s[newz];
                                    st.add(rear, b);
                                    dist.add(rear, dist.get(front) + 1);
                                    fa.add(rear, front + 1);
                                    number.add(rear, s[newz]);
                                    if (try_to_insert(rear) == 1) {
                                        rear = rear + 1;
                                    }
                                }
                            }
                            if (d == 3) {
                                direction.add(rear, "L");
                                if (s[newz] > 30 && s[newz] < 60) {
                                    if (newz + 1 < row * line && zero.get(i) + 1 < row * line) {
                                        if (s[newz + 1] == s[newz] + 1) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz + 1];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + 1] = s[zero.get(i)];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else if (s[newz] > 60 && s[newz] < 90) {
                                    if (newz + line < row * line && zero.get(i) + line < row * line) {
                                        if (s[newz + line] == s[newz] + line && s[zero.get(i) + line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[zero.get(i)];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + line] = s[zero.get(i) + line];
                                            b[zero.get(i) + line] = s[newz + line];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                    if (newz - line >= 0 && zero.get(i) - line >= 0) {
                                        if (s[newz - line] == s[newz] - line && s[zero.get(i) - line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[zero.get(i)];
                                            b[zero.get(i)] = s[newz];
                                            b[newz - line] = s[zero.get(i) - line];
                                            b[zero.get(i) - line] = s[newz - line];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - line]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else if (s[newz] > 90) {
                                    if (newz + line < row * line && zero.get(i) + line < row * line) {
                                        if (s[newz + line] == s[newz] + line && s[zero.get(i) + line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz + 1];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + 1] = s[zero.get(i)];
                                            b[zero.get(i) + line] = s[newz + line];
                                            b[newz + line] = s[newz + line + 1];
                                            b[newz + 1 + line] = s[zero.get(i) + line];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                    if (newz - line >= 0 && zero.get(i) - line >= 0) {
                                        if (s[newz - line] == s[newz] - line && s[zero.get(i) - line] == 0) {
                                            int[] b = new int[row * line];
                                            if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                            b[newz] = s[newz + 1];
                                            b[zero.get(i)] = s[newz];
                                            b[newz + 1] = s[zero.get(i)];
                                            b[zero.get(i) - line] = s[newz - line];
                                            b[newz - line] = s[newz - line + 1];
                                            b[newz + 1 - line] = s[zero.get(i) - line];
                                            st.add(rear, b);
                                            dist.add(rear, dist.get(front) + 1);
                                            fa.add(rear, front + 1);
                                            number.add(rear, s[newz - line]);
                                            if (judge(rear) == 0) {
                                                for (int j = 0; j < st1.size(); j++) {
                                                    if (Arrays.equals(st.get(rear), st1.get(j))) {
                                                        return rear + " " + j;
                                                    }
                                                }
                                            }
                                            if (try_to_insert(rear) == 1) {
                                                rear = rear + 1;
                                            }
                                        }
                                    }
                                } else {
                                    int[] b = new int[row * line];
                                    if (row * line >= 0) System.arraycopy(s, 0, b, 0, row * line);
                                    b[newz] = s[zero.get(i)];
                                    b[zero.get(i)] = s[newz];
                                    st.add(rear, b);
                                    dist.add(rear, dist.get(front) + 1);
                                    fa.add(rear, front + 1);
                                    number.add(rear, s[newz]);
                                    if (try_to_insert(rear) == 1) {
                                        rear = rear + 1;
                                    }
                                }
                            }
                        }
                    }
                }
                front = front + 1;
            }
            return "0";
        }

    }

    private static int judge1(int rear1) {
        int v = 0;
        int[] c = st1.get(rear1);
        for (int i = 0; i < row * line; i++) {
            v = v * 10 + c[i];
        }
        if (vis.contains(v)) return 0;
        else return 1;
    }

    private static int try_to_insert1(int rear1) {
        int v = 0;
        int[] c = st1.get(rear1);
        for (int i = 0; i < row * line; i++) {
            v = v * 10 + c[i];
        }
        if (vis1.contains(v)) return 0;
        vis1.add(v);
        return 1;
    }


    //该方法用于判断是否走了回头路
    private static int try_to_insert(int rear) {
        int v = 0;
        int[] c = st.get(rear);
        for (int i = 0; i < row * line; i++) {
            v = v * 10 + c[i];
        }
        if (vis.contains(v)) return 0;
        vis.add(v);
        return 1;
    }

    private static int judge(int rear) {
        int v = 0;
        int[] c = st.get(rear);
        for (int i = 0; i < row * line; i++) {
            v = v * 10 + c[i];
        }
        if (vis1.contains(v)) return 0;
        else return 1;
    }
}

