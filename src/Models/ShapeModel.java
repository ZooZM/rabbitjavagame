package Models;

public class ShapeModel {
    int x;
    int y;
    boolean hasRabbit;
    boolean isBoom;
    int index;
    public ShapeModel(int x, int y, int index){
        this.x = x;
        this.y = y;
        this.index = index;
        hasRabbit =false;
        isBoom =false;
    }
}
