package puzzle;

import javafx.scene.image.ImageView;

public class Cell {

    private int x;                  //在nxｍ的矩阵中的x坐标
    private int y;                  //在nxｍ的矩阵中的y坐标
    private int validIndex;         //应该在的正确位置
    private int currentIndex;       //当前位置
    private ImageView ImageView;    //图像

    public Cell(int x, int y, ImageView initialImageView,int validIndex,int currentIndex) {
        this.x = x;
        this.y = y;
        this.ImageView = initialImageView;
        this.validIndex=validIndex;
        this.currentIndex=currentIndex;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public ImageView getImageView() {
        return this.ImageView;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getValidIndex() {
        return validIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setImageView(ImageView imageView){
        this.ImageView=imageView;
    }

    public boolean isEmpty(){
        return this.ImageView==null;
    }

    public boolean isSolved(){
        return this.validIndex==this.currentIndex;
    }
    
}
    