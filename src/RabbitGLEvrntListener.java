import Texture.TextureReader;
import states.GameState;
import states.PlayState;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class RabbitGLEvrntListener extends RabbitListener {
    GameState gameState;
    PlayState playState;
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
         playState = new PlayState();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        switch (gameState.getGameState()) {
            case "start":
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
    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

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

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
