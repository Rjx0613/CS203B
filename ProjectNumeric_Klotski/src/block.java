import edu.princeton.cs.algs4.StdOut;

public class block {
    private int data;
    private boolean isBlock;
    private double type;//1*1==0;1*2==1;2*1==2;2*2==3
    block mainPoint;

    public block(int data, float type) {
        this.data = data;
        this.type = type;
        if (!isBlock()) {
            this.setMainPoint(this);
        }
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public double getType() {
        return type;
    }

    public void setType(double type) {
        if (getType() == 0) {
            this.type = type;
        }else{
            throw new IllegalArgumentException("block cross!");
        }
    }

    public block getMainPoint() {
        return mainPoint;
    }

    public void setMainPoint(block mainPoint) {
        this.mainPoint = mainPoint;
    }
}
