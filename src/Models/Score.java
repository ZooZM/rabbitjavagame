package Models;

public class Score {
    UserModel user;
    int highScore;
    int lives;

    public Score(UserModel user, int highScore, int lives) {
        this.user = user;
        this.highScore = highScore;
        this.lives = lives;
    }

    public void isFall(){
        this.lives--;
    }

    public void setHighScore(int highScore) {
        if(highScore > this.highScore) {
            this.highScore = highScore;
        }
    }
}
