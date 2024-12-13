package states;

public class PlayState {
    public Long sTimer;
    public boolean isPaused;
    public boolean isLose;
    int gameSpeed;
    public PlayState(){
        isPaused=false;
        isLose=false;
        sTimer=System.currentTimeMillis();
    }
    public void setPaused() {
        isPaused = true;
    }
    public void setEasyMode(){
        this.gameSpeed=40;
    }
    public void setMediumMode(){
        this.gameSpeed=60;
    }
    public void setHardMode(){
        this.gameSpeed=80;
    }
}
