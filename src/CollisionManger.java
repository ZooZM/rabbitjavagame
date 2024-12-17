public class CollisionManger {
    public boolean isCollision(int xclicked,int yclicked,int x, int y, int r){
        return (xclicked<=(x+r)&&xclicked>=(x-r)&&yclicked<=(y+r)&&yclicked>=(y-r));
    }
}
