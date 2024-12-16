import Models.ScoreModel;
import Models.ShapeModel;
import Models.UserModel;
import Texture.TextureReader;
import states.GameState;
import states.PlayState;
import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RabbitGLEvrntListener extends RabbitListener {
    public static final int MAX_WIDTH = 100, MAX_HEIGHT = 100; // set max height and width to translate sprites using integers
    int xClicked = 200;
    int yClicked = 200;
    int xMotion = 0, yMotion = 0;
    int xkey = 10 , ykey = 50 ;

    int speed = 0;
    GLUT glut = new GLUT();
    GameState gameState;
    PlayState playState;
    UserModel userModel;
    UserModel userModel2;
    ArrayList<ShapeModel> holes;
    ShapeModel hammer;
    ShapeModel pause;
    ShapeModel resume;
    ShapeModel restart;
    ShapeModel exit;
    ScoreModel score ;
    ScoreModel score2 ;


    String[] textureNames = new String[]{"rabbit2.png", "Hammer.png", "Hole.png", "Boom.png", "Hit.png"
            , "Back.jpeg", "play.png", "exit.png", "soundOn.png", "soundOff.png",
            "easy.png", "medium.png", "hard.png", "backbtn.png", "hammer3.png", "HowToPlay.png",
            "playAgain.png","q.png","w.png","e.png","a.png","s.png","d.png",
            "home.png", "restart.png", "resume.png", "Back1.png",
            "Hamme2r.png", "Hamer3.png", "Hammer4.png", "gameOver.png", "puase.png", "ins.png" , "levels.png","Back.png"};

    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        gl.glEnable(3553);
        gl.glBlendFunc(770, 771);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; ++i) {
            try {
                this.texture[i] = TextureReader.readTexture(RabbitFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(3553, this.textures[i]);
                (new GLU()).gluBuild2DMipmaps(3553, 6408, texture[i].getWidth(), texture[i].getHeight(), 6408, 5121, texture[i].getPixels());
            } catch (IOException var5) {
                System.out.println(var5);
                var5.printStackTrace();
            }
        }
        gameState = new GameState();

        gameState.setStartPlay();
        playState = new PlayState(2);
        playState.setEasyMode();
        userModel = new UserModel("as",0);
        userModel2 = new UserModel("mm",0);
        score = new ScoreModel(userModel,0,7);
        score2 = new ScoreModel(userModel2,0,7);
        pause=new ShapeModel(92,92,textureNames.length-4);
        resume=new ShapeModel(50,70,textureNames.length-10);
        restart=new ShapeModel(50,50,textureNames.length-11);
        exit=new ShapeModel(50,30,7);

        holes = new ArrayList<ShapeModel>();

        holes.add(new ShapeModel(20, 50, 2)); // Top-left
        holes.add(new ShapeModel(50, 50, 2)); // Top-center
        holes.add(new ShapeModel(80, 50, 2)); // Top-right
        holes.add(new ShapeModel(20, 30, 2)); // Bottom-left
        holes.add(new ShapeModel(50, 30, 2)); // Bottom-center
        holes.add(new ShapeModel(80, 30, 2)); // Bottom-right
        hammer=new ShapeModel(0,0,1);

        generateRabbit();
    }

    long currentTimeMillis;
    long elapsedTimeSeconds;
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();


        switch (gameState.getGameState()) {
            case "start": {
                DrawBackground(gl , 28);

                DrawImage(gl, 50, 70, 6, 1.5f, 1f);
                DrawImage(gl, 50, 50, 15, 1.5f, 1f);
                DrawImage(gl, 90, 90, 8, 1f, 1f);
                DrawImage(gl, 50, 30, 27, 1.7f, 2f);//back

            }
            break;

            case "instruction":
                DrawBackground(gl , 26);
                DrawImage(gl, 90, 90, 13, 1f, 1f);

                break;

            case "chooseMode":
                DrawBackground(gl , 28);
                DrawImage(gl, 50, 50, 11, 1.4f, 1f);
                DrawImage(gl, 50, 70, 10, 1.4f, 1f);
                DrawImage(gl, 50, 30, 12, 1.4f, 1f);
                DrawImage(gl, 90, 90, 13, 1f, 1f);

                break;

            case "chooseNumberOfPlayers":

                break;

            case "startPlay":
                DrawBackground(gl , 26);
            {
                if (playState.isLose) {


                } else if (playState.isPaused) {
                    //--------------------------------------------------Pause menu--------------------------------------------------

                    DrawImage(gl, pause.x, pause.y,pause.index,0.75f,0.75f);
                    DrawImage(gl, resume.x, resume.y, resume.index, 1.1f,1f);
                    DrawImage(gl, restart.x, restart.y, restart.index, 1.1f,1f);
                    DrawImage(gl, exit.x, exit.y, exit.index, 1.1f,1f);

                    //--------------------------------------------------Pause menu--------------------------------------------------

                } else {
                    DrawImage(gl, pause.x, pause.y,pause.index,0.75f,0.75f);

                    currentTimeMillis = System.currentTimeMillis();
                    elapsedTimeSeconds = (currentTimeMillis - playState.sTimer) / 1000L;


                    for (ShapeModel hole : holes) {
                        DrawImage(gl, hole.x, hole.y, hole.index, 1, 1);

                        if (hole.hasRabbit) {
                            DrawImage(gl, hole.x, hole.y + 5, 0, 0.8f, 0.8f);

                        }
                        //--------------------------------------------------DrawBoom--------------------------------------------------
                        //for first hammer
                        if(hole.isBoom){
                            DrawImage(gl,hole.x,hole.y,3 , 1 ,1);
                            hole.isBoom=false;
                        }

                        //--------------------------------------------------DrawBoom--------------------------------------------------
                    }
                    //--------------------------------------------------GenerateRabbitSpeed--------------------------------------------------
                    speed += 1;
                    if (speed % playState.gameSpeed == 0) {
                        generateRabbit();
                    }
                    //--------------------------------------------------GenerateRabbitSpeed--------------------------------------------------

                    if(playState.numOfPlayers == 1){
                        hammer.x = xMotion+5;
                        hammer.y = yMotion+1;
                        DrawImage(gl, hammer.x, hammer.y ,hammer.index,0.8f,0.8f);
                        drawWord(gl,-0.9F,0.9f,"Timer", elapsedTimeSeconds);

                        drawWord(gl,-0.9F,0.8f,"Score", (long) score.user.score);

                        drawWord(gl,-0.9F,0.7f,"Lives", (long) score.user.lives);
                    }else{
                            hammer.x = xMotion+5;
                            hammer.y = yMotion+1;
                            DrawImage(gl, hammer.x, hammer.y ,hammer.index,0.8f,0.8f);
                            drawWord(gl,-0.9F,0.9f,"Timer", elapsedTimeSeconds);

                            drawWord(gl,0.5F,0.8f,"Score", (long) score.user.score);

                            drawWord(gl,0.5F,0.7f,"Lives", (long) score.user.lives);

                            drawWord(gl,-0.9F,0.8f,"Score2", (long) score2.user.score);

                            drawWord(gl,-0.9F,0.7f,"Lives2", (long) score2.user.lives);


                        //--------------------------------------------------MultiPlayer--------------------------------------------------
                        DrawImage(gl ,20 , 40 ,17 ,0.4f , 0.4f);//Q
                        DrawImage(gl , 50, 40 ,18 ,0.4f , 0.4f);//W
                        DrawImage(gl , 80, 40 ,19 ,0.4f , 0.4f);//E
                        DrawImage(gl , 20, 20 ,20 ,0.4f , 0.4f);//A
                        DrawImage(gl , 50, 20 ,21 ,0.4f , 0.4f);//S
                        DrawImage(gl , 80, 20 ,22 ,0.4f , 0.4f);//D


                        DrawImage(gl , xkey ,ykey,1 ,-1f , 1f);
                        for(Models.ShapeModel hole:holes){
                            if(isCatch(xkey,ykey,hole.x,hole.y,6)&&hole.hasRabbit){
                                hole.isBoom =true;
                                hole.hasRabbit = false;
                                generateRabbit();
                                speed = 0;
                                score2.user.score++;
                                score2.setHighScore(score2.user.score);
                                System.out.println(xkey +" "+ykey);
                                System.out.println("Catch");
                                xkey=10;
                                ykey=50;

                            }
                            else if (isCatch(xkey, ykey, hole.x, hole.y, 6)) {
                                if(score2.user.isLose()){
                                    playState.setLose();
                                }
                                score2.itFall();
                                xkey=10;
                                ykey=50;
                                System.out.println("fall");
                            }

                        }
                        //--------------------------------------------------MultiPlayer--------------------------------------------------

                    }

                }


            }
            break;

            default:
                System.out.println("Unknown state.");
                break;
        }


    }


    int previousNum = -1;
    void generateRabbit() {

        for (ShapeModel hole : holes) {
            hole.hasRabbit = false;
        }

        Random random = new Random();
        int num;

        do {
            num = random.nextInt(6);
        } while (num == previousNum);

        previousNum = num;
        holes.get(num).hasRabbit = true;
    }


    public void DrawBackground(GL gl , int i) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0F, 0.0F);
        gl.glVertex3f(-1.0F, -1.0F, -1.0F);
        gl.glTexCoord2f(1.0F, 0.0F);
        gl.glVertex3f(1.0F, -1.0F, -1.0F);
        gl.glTexCoord2f(1.0F, 1.0F);
        gl.glVertex3f(1.0F, 1.0F, -1.0F);
        gl.glTexCoord2f(0.0F, 1.0F);
        gl.glVertex3f(-1.0F, 1.0F, -1.0F);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawImage(GL gl, int x, int y, int index, float scaleX, float scaleY) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);
        gl.glPushMatrix();
        gl.glTranslated(x / (MAX_WIDTH / 2.0) - 1, y / (MAX_HEIGHT / 2.0) - 1, 0);
        gl.glScaled(0.15 * scaleX, 0.15 * scaleY, 1);
//        System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
    private void drawWord(GL gl,float x,float y,String word,Long var) {
        gl.glRasterPos2f(x, y);
        String livesString = word +": "+ var;
        char[] var3 = livesString.toCharArray();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char c = var3[var5];
            glut.glutBitmapCharacter(8, c);
        }

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
        xClicked = (int) (x / width * 100.0);
        yClicked = (int) (y / height * 100.0);
        yClicked = 100 - yClicked;
        switch (gameState.getGameState()) {
            case "start": {

            }
            break;

            case "instruction":

                break;

            case "chooseMode":

                break;

            case "chooseNumberOfPlayers":

                break;

            case "startPlay": {
                if (playState.isLose) {

                } else if (playState.isPaused) {

                    if(isCatch(xClicked,yClicked, pause.x, pause.y,5)){
                        playState.isPaused= !playState.isPaused;
                        System.out.println("Resume");

                    }

                    if(isCatch(xClicked,yClicked,resume.x, resume.y, 8)){
                        playState.isPaused= false;
                        System.out.println("Resume");

                    }
                    if(isCatch(xClicked,yClicked, restart.x, restart.y, 8)){
                        playState.isPaused= false;
                        score.user.lives = 7;
                        score.user.score=0;
                        // if multi
                        score2.user.lives = 7;
                        score2.user.score=0;
                        // if multi
                        playState.sTimer =System.currentTimeMillis();
                        System.out.println("Restart");

                    }
                    if(isCatch(xClicked,yClicked,exit.x, exit.y, 8)){
                        System.out.println("Exit");

                    }



                } else {

                    if(isCatch(xClicked,yClicked, pause.x, pause.y, 5)){
                        playState.isPaused= !playState.isPaused;
                        System.out.println("Resume");

                    }
                    int r =6;
                    for (ShapeModel holeAxis : holes) {
                        if (isCatch(xClicked, yClicked, holeAxis.x, holeAxis.y, r) && holeAxis.hasRabbit) {
                            holeAxis.isBoom=true;
                            holeAxis.hasRabbit = false;
                            generateRabbit();
                            speed = 0;
                            score.user.score++;
                            score.setHighScore(score.user.score);
                            System.out.println("Catch");
                        }else if (isCatch(xClicked, yClicked, holeAxis.x, holeAxis.y, r)) {
                            if(score.user.isLose()){
                                playState.setLose();
                            }
                            score.itFall();
                            System.out.println("fall");
                        }
                    }
                }
            }
            break;

            default:
                System.out.println("Unknown state.");
                break;
        }
    }
            boolean isCatch(int xclicked,int yclicked,int x, int y, int r){
                return (xclicked<=(x+r)&&xclicked>=(x-r)&&yclicked<=(y+r)&&yclicked>=(y-r));
            }

    @Override
    public void mousePressed(MouseEvent e) {
    if(!(playState.isLose || playState.isPaused)){
        hammer.index = textureNames.length -7;
    }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!(playState.isLose || playState.isPaused)){
            hammer.index = 1;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public double convertX(double x, double width) {
        return (x / width * 100);
    }

    public double convertY(double y, double height) {
        return (100 - y / height * 100);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xMotion = (int) convertX(e.getX(), e.getComponent().getWidth());
        yMotion = (int) convertY(e.getY(), e.getComponent().getHeight());
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_Q){
            xkey=20;
            ykey=50;
        }
        else if(e.getKeyCode() == KeyEvent.VK_W){
            xkey=50;
            ykey=50;
        }
        else if(e.getKeyCode() == KeyEvent.VK_E){
            xkey=80;
            ykey=50;
        }
        else if(e.getKeyCode() == KeyEvent.VK_A){
            xkey=20;
            ykey=30;
        }
        else if(e.getKeyCode() == KeyEvent.VK_S){
            xkey=50;
            ykey=30;
        }
        else if(e.getKeyCode() == KeyEvent.VK_D){
            xkey=80;
            ykey=30;
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
           playState.isPaused=!playState.isPaused;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
