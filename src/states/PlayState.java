package states;

public class PlayState {
    public Long sTimer;
    public boolean isPaused;
    public boolean isLose;
    public boolean isPlay;
    public  int gameSpeed;
    public int numOfPlayers;
    public int levelUp;
    public PlayState(int numOfPlayers){
        this.numOfPlayers=numOfPlayers;
        isPaused=false;
        isLose=false;
        sTimer=System.currentTimeMillis();
    }
    public void setPaused() {
        isPaused = true;
    }
    public void setEasyMode(){
        this.gameSpeed=30;
        levelUp = 10;
    }
    public void setMediumMode(){
        this.gameSpeed=20;
        levelUp=5;
    }
    public void setHardMode(){
        this.gameSpeed=10;
        levelUp=3;
    }
    public void setLose() {
        isLose = true;
    }
    public void setPlay() {
        isLose = false;
        isPaused = false;
    }


}
