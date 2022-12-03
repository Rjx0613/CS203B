package puzzle;

public class IDAStar {

    private static final int MAXSTEP = 2000;                         //最大步数
    private static final int[] directionX = {1, 0, -1, 0};          //分别对应下右上左
    private static final int[] directionY = {0, 1, 0, -1};

    private static final char[] direction = {'d', 'r', 'u', 'l'};   //d=0,r=1,u=2,l=3
    private static final int[] oppositeDirection = {2, 3, 0, 1};    //drul的反方向为uldr,分别对应2301

    private int[][] tile;               //存放排列的矩阵
    private int Row;                    //行数
    private int Line;                  //列数
    private int upper = 0;              //初始矩阵的曼哈顿距离
    private boolean pass;               //判断是否找到路径
    private int pathOfLength;           //路径长度
    private StringBuilder routine;      //路径

    public IDAStar(int[] array, int row, int line) {
        Row = row;
        Line = line;
        pathOfLength = 0;
        initializeTile(array);
    }

    /**
     * depth             //函数调用深度
     * row               //0所在行数
     * col               //0所在列数
     * est               //曼哈顿距离
     * preDirection      //上一个移动的方向，避免走回头路
     */
    public void IDAS(int depth, int row, int col, int est, int preDirection) {
        int length = Row * Line;
        if (est == 0 || this.pass) {
            this.pathOfLength = depth;
            this.pass = true;
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (i != preDirection) {                //不走回头路
                int newRow = row + directionX[i];
                int newCol = col + directionY[i];
                int preMht = 0, nextMht = 0, temp = 0;

                if (isValid(newRow, newCol)) {      //判断移动是否有效
                    temp = tile[newRow][newCol];
                    int tx = (temp  - temp  % Line) / Row;
                    int ty = temp  % Line;

                    preMht = getManhattanDistance(newRow, newCol, tx, ty);   //未移动前，被移动数的曼哈顿距离
                    nextMht = getManhattanDistance(row, col, tx, ty);         //移动后，被移动数的曼哈顿距离
                    int h = est + nextMht - preMht + 1;                       //移动后的曼哈顿距离
                    if (depth + h <= upper) {         //当前调用深度+移动后的曼哈顿距离<=最初的曼哈顿距离则接着走
                        tile[row][col] = temp;
                        tile[newRow][newCol] = length - 1;

                        routine.append(direction[i]);
                        routine.setCharAt(depth, direction[i]);

                        IDAS(depth + 1, newRow, newCol, est + nextMht - preMht, oppositeDirection[i]);
                        tile[row][col] = length - 1;
                        tile[newRow][newCol] = temp;
                        if (pass) {
                            return;
                        }
                    }
                }
            }
        }

    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < Row && col >= 0 && col < Line;
    }

    //代价函数
    private int heuristic(int[][] tile) {
        int manhadanDistance = 0;
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Line; j++) {
                if (tile[i][j] != Row * Line - 1) {
                    int tx = (tile[i][j] - tile[i][j] % Line) / Row;
                    int ty = (tile[i][j]) % Line;
                    manhadanDistance += getManhattanDistance(i, j, tx, ty);
                }
            }
        }
        return manhadanDistance;
    }

    //曼哈顿距离计算
    private int getManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    //初始化title矩阵
    private void initializeTile(int[] array) {
        this.tile = new int[Row][Line];
        for (int i = 0; i < array.length; i++) {
            this.tile[(i - i % Line) / Row][i % Line] = array[i];
        }
    }

    //获得路径
    public String getPath() {
        return routine.substring(0, pathOfLength);
    }

    public void init() {
        int length = Row * Line;
        int startRow = Row - 1;
        int startCol = Line - 1;

        for (int i = 0; i < length; i++) {
            if (this.tile[(i - i % Line) / Row][i % Line] == Line * Row - 1) {
                startRow = (i - i % Line) / Row;
                startCol = i % Line;
                break;
            }
        }
        routine = new StringBuilder();
        int cost = heuristic(this.tile);
        this.upper = Math.min(MAXSTEP, cost + 1);
        while (!this.pass) {
            IDAS(0, startRow, startCol, cost, -1);
            this.upper = Math.min(upper + 1, MAXSTEP);
        }
    }
}
