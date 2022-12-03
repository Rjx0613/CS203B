package puzzle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

class AutoBoard {

    private Label numberLabel = new Label("Number of Movements :");
    private Label pathLabel = new Label("Movement Routine :");
    private Button btGetPath = new Button("Get Path");
    private Button btAutoPuzzle = new Button("Auto Puzzle");

    private Text pathText = new Text("");
    private Text numberText = new Text("");

    private static int[] array;        //排列
    private int Row;                    //行数
    private int Line ;                  //列数

    private IDAStar iDAStar;

    private static boolean isMove;     //在获得路径之后，判断数字是否移动过

    public AutoBoard(int[] array,int row, int line) {
        this.Row = row;
        this.Line = line;
        this.array = array;
        init();
    }

    private void init() {
        setPathButton();
        setAutoPuzzleButton();
    }

    private void setPathButton() {
        btGetPath.setOnMouseClicked(e -> {
            iDAStar = new IDAStar(array,Row,Line);
            iDAStar.init();
            pathText.setText(iDAStar.getPath());
            numberText.setText(String.valueOf(iDAStar.getPath().length()));
            isMove = false;
        });
    }
    
    private void setAutoPuzzleButton() {
        EventHandler<ActionEvent> eventHandler = e -> {
        };
        btAutoPuzzle.setOnMouseClicked(e -> {
            if (!isMove && iDAStar.getPath().length()>0) {  //获得路径后未移动拼图且路径长度大于0
                Timeline animation = new Timeline(new KeyFrame(Duration.millis(300), eventHandler));
                animation.setCycleCount(iDAStar.getPath().length());    //设置timeline动画的轮数为路径长度
                animation.play();
                btAutoPuzzle.setDisable(true);
                btGetPath.setDisable(true);
            }
        });
    }

    protected GridPane createBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        gridPane.add(pathLabel, 0, 1);
        gridPane.add(pathText, 1, 1);
        gridPane.add(numberLabel, 0, 2);
        gridPane.add(numberText, 1, 2);
        gridPane.add(btGetPath, 0, 4);
        gridPane.add(btAutoPuzzle, 1, 4);
        return gridPane;

    }




}
