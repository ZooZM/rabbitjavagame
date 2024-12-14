import Models.ShapeModel;
import Models.UserModel;
import Texture.TextureReader;
import states.GameState;
import states.PlayState;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class RabbitGLEvrntListener extends RabbitListener {
    public static final int MAX_WIDTH = 100, MAX_HEIGHT = 100; // set max height and width to translate sprites using integers
    int xClicked = 200;
    int yClicked = 200;
    int xMotion =0  , yMotion =0;
    GameState gameState;
    PlayState playState;
    UserModel userModel;
    ArrayList<ShapeModel> holes;
    ArrayList<ShapeModel> hammers;
    

    String[] textureNames= new String[]{"rabbit2.png", "Hammer.png", "Hole.png", "Boom.png", "Hit.png", "Back.jpeg", "play.png",
            "exit.png", "soundOn.png", "soundOff.png", "easy.png", "medium.png", "hard.png", "backbtn.png", "hammer3.png",
            "HowToPlay.png", "playAgain.png", "home.png", "restart.png", "resume.png", "Back1.png", "Hamme2r.png", "Hamer3.png",
            "Hammer4.png", "gameOver.png", "puase.png" , "ins.png"};

    TextureReader.Texture[] texture= new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        gl.glEnable(3553);
        gl.glBlendFunc(770, 771);
        gl.glGenTextures(textureNames.length, textures, 0);

        for(int i = 0; i < textureNames.length; ++i) {
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
        playState = new PlayState(1);


        holes = new ArrayList<ShapeModel>();

        holes.add(new ShapeModel(20, 70,2)); // Top-left
        holes.add(new ShapeModel(50, 70,2)); // Top-center
        holes.add(new ShapeModel(80, 70,2)); // Top-right
        holes.add(new ShapeModel(20, 30,2)); // Bottom-left
        holes.add(new ShapeModel(50, 30,2)); // Bottom-center
        holes.add(new ShapeModel(80, 30,2)); // Bottom-right



    }



    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        switch (gameState.getGameState()) {
            case "start":{
                gameState.setInstruction();
                userModel = new UserModel("as",0);

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

            } else {

                for (ShapeModel hole: holes){
                    DrawImage(gl , hole.x , hole.y ,hole.index ,1 , 1);

                    if(hole.hasRabbit){
                        DrawImage(gl , hole.x , hole.y +5,0 ,0.8f , 0.8f);

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
    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[20]);
        gl.glPushMatrix();
        gl.glBegin(7);
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

    public void DrawImage(GL gl,int x, int y, int index, float scaleX,float scaleY){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);
        gl.glPushMatrix();
        gl.glTranslated( x/(MAX_WIDTH/2.0) - 1, y/(MAX_HEIGHT/2.0) - 1, 0);
        gl.glScaled(0.15*scaleX, 0.15*scaleY, 1);
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
        xClicked = (int)(x / width * 100.0);
        yClicked = (int)(y / height * 100.0);
        yClicked = 100 - yClicked;
        switch (gameState.getGameState()) {
            case "start":{

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

                } else {

                }
            }
            break;

            default:
                System.out.println("Unknown state.");
                break;
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
    public double convertX(double x , double width) {
        return (x /width * 100);
    }
    public double convertY(double y , double height) {
        return (100-y / height * 100);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xMotion = (int)convertX(e.getX() , e.getComponent().getWidth());
        yMotion = (int)convertY(e.getY() , e.getComponent().getHeight());
    }


}
