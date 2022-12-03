
package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;


public class MainWindow extends Application {

    private Pane pane = new Pane();

    private AutoBoard autoBoard;
    private GridPane autoPane;
    private final double SCENE_WUDTH = 1024;            //游戏窗口大小
    private final double SCENE_HEIGHT = 640;
    public static final double offsetX = 0;             //面板的偏移量
    public static final double offsetY = 30;
    ArrayList<Cell> cellsList = new ArrayList<>();      //存放图块的数组
    public int CELLSIZE = 200;                          //每个图块的大小
    private static int Row;
    private static int Line;

    public void setRow(int num) {
        this.Row = num;
    }

    public void setLine(int num) {
        this.Line = num;
    }

    public void start(Stage stage) {
        CELLSIZE = 100;
        initialize();
        picture();
        setAutoBoard();
        Scene scene = new Scene(pane, SCENE_WUDTH, SCENE_HEIGHT);
        stage.setResizable(false);
        stage.setTitle("HUARONGDAO");
        stage.setScene(scene);
        stage.show();
    }

    private void initialize() {
        Scanner input = new Scanner(System.in);
        int row = input.nextInt();
        int line = input.nextInt();
        int[][] array = new int[row][line];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                array[i][j] = input.nextInt();
            }
        }
        System.out.println();
        for (int[] date : array) {
            System.out.println(Arrays.toString(date));
        }
        Row = row;
        Line = line;

        int[] ran = new int[row * line];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
               ran[i*Line+j]=array[i][j];
            }
        }
        for (int i = 0; i < ran.length; i++) {
            ImageView imageBlock = new ImageView("image/"+ran[i]+".jpg");
            if (ran[i]==0){
                cellsList.add(new Cell(i % line, (i-i%line) / row, imageBlock, i, ran.length-1));
            }
            else {
                cellsList.add(new Cell(i % line, (i - i % line) / row, imageBlock, i, ran[i] - 1));
            }
        }
    }

    private void picture() {
        for (int i = 0; i < cellsList.size(); i++) {
            Cell currentCell = cellsList.get(i);
            Node imageView = currentCell.getImageView();
            relocate(currentCell, imageView);
        }
    }

    private void relocate(Cell currentCell, Node imageView) {
        ImageView currentImageView = currentCell.getImageView();
        imageView.relocate(currentCell.getX() * CELLSIZE + offsetX, currentCell.getY() * CELLSIZE + offsetY);
        pane.getChildren().add(currentImageView);
    }

    //设置自动按钮界面
    private void setAutoBoard() {
        autoBoard = new AutoBoard(cellsList);
        autoPane = autoBoard.createBoard();
        autoPane.relocate(CELLSIZE * Line/2 , 450);
        pane.getChildren().add(autoPane);
    }



    public static void main(String[] args) {
        Application.launch(args);
    }

}
