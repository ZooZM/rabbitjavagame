package Models;

public class ScoreModel {
    public UserModel user;
    public int highScore;


    public ScoreModel(UserModel user, int highScore, int lives) {
        this.user = user;
        this.highScore = highScore;
        user.lives  = lives;
    }

    public void itFall(){
        this.user.lives--;
    }

    public void setHighScore(int highScore) {
        if(highScore > this.highScore) {
            this.highScore = highScore;
        }
    }
}
