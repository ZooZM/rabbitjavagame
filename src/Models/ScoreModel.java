package Models;

public class ScoreModel {
    UserModel user;
    int highScore;
    int lives;

    public ScoreModel(UserModel user, int highScore, int lives) {
        this.user = user;
        this.highScore = highScore;
        this.lives = lives;
    }

    public void itFall(){
        this.lives--;
    }

    public void setHighScore(int highScore) {
        if(highScore > this.highScore) {
            this.highScore = highScore;
        }
    }
}
