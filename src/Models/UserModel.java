package Models;

public class UserModel {
    public int score;
    String username;
    int lives;

    public UserModel(String username, int score) {
        this.username = username;
        this.score = score;
    }
}
