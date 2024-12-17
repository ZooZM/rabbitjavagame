package Models;

public class UserModel {
    public int score;
    public String username;
    public int lives;

    public UserModel(String username, int score) {
        this.username = username;
        this.score = score;
    }
    public boolean isLose(){
        return lives ==1;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
