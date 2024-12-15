import Models.ShapeModel;

public class CollisionManger {

    RabbitGLEvrntListener listener;

    CollisionManger(){}

    public boolean isCollision(int x,int y,int r){
        for(ShapeModel hole : listener.holes){

            if(x<=(hole.x+r)&&x>=(hole.x-r)&&y<=(hole.y+r)&&y>=(hole.y-r) && hole.hasRabbit)
            {
            listener.score.itFall();
                hole.hasRabbit =false;
                return true;
            }
        }
        return false;
    }
}
