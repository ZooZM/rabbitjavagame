package states;

public class PlayState {
    public Long sTimer;
    public boolean isPaused;
    public boolean isLose;
    public boolean isPlay;
    public  int gameSpeed;
    public int numOfPlayers;
    public int defLevelUp;
    public int levelUp;
    public int c = 11  , d = 11;

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
        defLevelUp =10;
        levelUp = 10;
    }
    public void setMediumMode(){
        this.gameSpeed=20;
        levelUp=5;
        defLevelUp = 5;
    }
    public void setHardMode(){
        this.gameSpeed=10;
        levelUp=3;
        defLevelUp =3;
    }
    public void setLevelUp(){
        int  i = gameSpeed- levelUp;
        if(i>0){
            levelUp+=levelUp;
            System.out.println("speed up");
            c+=10;
            d+=10;
        }
    }
    public void setLose() {
        isLose = true;
    }
    public void setPlay() {
        isLose = false;
        isPaused = false;
    }
    public void setRestart(){
       levelUp = defLevelUp;
     c =11;
       d=11;
    }

}
