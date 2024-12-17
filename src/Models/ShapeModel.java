package Models;

public class ShapeModel {
    public int x;
    public int y;
    public boolean hasRabbit;
    public boolean isBoom;
    public int  index;
    public ShapeModel(int x, int y, int index){
        this.x = x;
        this.y = y;
        this.index = index;
        hasRabbit =false;
        isBoom =false;
    }
}
