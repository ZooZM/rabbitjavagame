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
import Texture.TextureReader;
import com.sun.opengl.util.j2d.TextRenderer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Random;

public class RabbitGLEvrntListener extends RabbitListener {
    public static final int MAX_WIDTH = 100, MAX_HEIGHT = 100;
    int xClicked = 200;
    int yClicked = 200;
    int xMotion = 0, yMotion = 0;
    int xkey = 10, ykey = 50;

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
    ScoreModel score;
    ScoreModel score2;
    CollisionManger collisionManger = new CollisionManger();

    ShapeModel soundButton;
    private String soundsFolderPath = "src/sounds/";
    private Clip backgroundMusic;
    private Clip hammerSound;
    private Clip winSound;
    private Clip loseSound;
    boolean isSoundEnabled;


    String[] textureNames = new String[]{"rabbit2.png", "Hammer.png", "Hole.png", "Boom.png", "Hit.png",
            "Back.jpeg", "play.png", "exit.png", "soundOn.png", "soundOff.png",
            "easy.png", "medium.png", "hard.png", "backbtn.png", "hammer3.png", "HowToPlay.png",
            "playAgain.png", "q.png", "w.png", "e.png", "a.png", "s.png", "d.png",
            "home.png", "restart.png", "resume.png", "Back1.png",
            "Hamme2r.png", "Hamer3.png", "Hammer4.png", "gameOver.png", "puase.png", "inst.jpg",
            "levels.png", "Back.png","Multi PLayer.JPG","Single PLayer.JPG","heart.png"};

    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        initSounds();
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

//        gameState.setChooseMode();
        gameState.setStartPlay();

        holes = new ArrayList<ShapeModel>();
        holes.add(new ShapeModel(50,70,6));
        holes.add(new ShapeModel(50,50,15));
        holes.add(new ShapeModel(10,90,8));
        holes.add(new ShapeModel(50,30,33)); // menu

        holes.add(new ShapeModel(50, 30, 12));
        holes.add(new ShapeModel(50, 70, 10));
        holes.add(new ShapeModel(50, 50, 11));
        holes.add(new ShapeModel(90,90,8));
        holes.add(new ShapeModel(90,90,13));
        holes.add(new ShapeModel(50,10,7));

        gameState.setStart();

        playState = new PlayState(1);
        playState.setEasyMode();
        userModel = new UserModel(null, 0);
        userModel2 = new UserModel(null, 0);
        score = new ScoreModel(userModel, 0, 7);
        score2 = new ScoreModel(userModel2, 0, 7);
        pause = new ShapeModel(92, 92, 31);
        soundButton = new ShapeModel(90, 90, 8);
        resume = new ShapeModel(50, 70, 25);
        restart = new ShapeModel(50, 50, 24);
        exit = new ShapeModel(50, 30, 7);

        holes = new ArrayList<ShapeModel>();

        holes.add(new ShapeModel(20, 50, 2)); // Top-left
        holes.add(new ShapeModel(50, 50, 2)); // Top-center
        holes.add(new ShapeModel(80, 50, 2)); // Top-right
        holes.add(new ShapeModel(20, 30, 2)); // Bottom-left
        holes.add(new ShapeModel(50, 30, 2)); // Bottom-center
        holes.add(new ShapeModel(80, 30, 2)); // Bottom-right
        hammer = new ShapeModel(0, 0, 1);

        isSoundEnabled = true;
        generateRabbit();
    }

    long currentTimeMillis;
    long elapsedTimeSeconds;

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

                DrawBackground(gl, 34);

        switch (gameState.getGameState()) {
            case "start": {

                DrawImage(gl, 50, 70, 6, 1.5f, 1f);
                DrawImage(gl, 50, 50, 15, 1.5f, 1f);
                DrawImage(gl, 90, 90, soundButton.index, 1f, 1f);
                DrawImage(gl, 50, 30, exit.index, 1f, 1f);
            }
            break;

            case "instruction":
                DrawBackground(gl, 32);

                DrawImage(gl, 90, 90, 13, 1f, 1f);

                break;

            case "chooseMode":

                DrawImage(gl, 50, 50, 11, 1.4f, 1f);
                DrawImage(gl, 50, 70, 10, 1.4f, 1f);
                DrawImage(gl, 50, 30, 12, 1.4f, 1f);
                DrawImage(gl, 90, 90, 13, 1f, 1f);

                break;

            case "chooseNumberOfPlayers":

                DrawImage(gl, 50, 30, 35, 1.4f, 1f);
                DrawImage(gl, 50, 70, 36, 1.4f, 1f);//single
//                DrawImage(gl, 50, 30, 37, 1.4f, 1f);   //VS AI
                DrawImage(gl, 90, 90, 13, 1f, 1f);
                break;

            case "chooseModeFor2Players":

                DrawImage(gl, 50, 50, 11, 1.4f, 1f);
                DrawImage(gl, 50, 70, 10, 1.4f, 1f);
                DrawImage(gl, 50, 30, 12, 1.4f, 1f);
                DrawImage(gl, 90, 90, 13, 1f, 1f);

                break;

            case "startPlay":

            {
                DrawBackground(gl, 26);
                if (playState.isLose) {
                    if(playState.numOfPlayers == 1) {

                        DrawImage(gl, 50, 60, 30, 5f, 5f);
                        drawWord(gl, -0.18F, -0.3f, "High Score of "+score.user.username, (long) score.highScore);
                        drawWord(gl, -0.18F, -0.4f, "This round score of "+score.user.username, (long) score.user.score);
                        DrawImage(gl, 50, 20, 16, 1f, 0.8f);
                        DrawImage(gl, 50, 7,  7, 1f, 0.8f);
                    }
                    else {
                        DrawImage(gl, 50, 60, textureNames.length - 8, 5f, 5f);

                        if (score2.user.isLose()) {
                            drawWord(gl, -0.15F, -0.2f, "Player " + userModel2.username, "Losses");

                        } else {
                            drawWord(gl, -0.15F, -0.2f, "Player " + userModel.username, "Losses");
                        }

                        drawWord(gl, 0.15F, -0.3f, "High score of "+score.user.username, (long) score.highScore);
                        drawWord(gl, 0.15F, -0.4f, "This round score of "+score.user.username, (long) score.user.score);

                        // if multi
                        drawWord(gl, -0.6F, -0.3f, "High score of "+score2.user.username, (long) score2.highScore);
                        drawWord(gl, -0.6F, -0.4f, "This round score of "+score2.user.username, (long) score2.user.score);
                        //if multi
                        DrawImage(gl, 50, 20, 16, 1f, 0.8f);
                        DrawImage(gl, 50, 7,  7, 1f, 0.8f);
                    }


                } else if (playState.isPaused) {
                    //--------------------------------------------------Pause menu--------------------------------------------------

                    DrawImage(gl, pause.x, pause.y, pause.index, 0.75f, 0.75f);
                    DrawImage(gl, resume.x, resume.y, resume.index, 1.1f, 1f);
                    DrawImage(gl, restart.x, restart.y, restart.index, 1.1f, 1f);
                    DrawImage(gl, exit.x, exit.y, exit.index, 1.1f, 1f);

                    //--------------------------------------------------Pause menu--------------------------------------------------

                } else {

                    DrawImage(gl, pause.x, pause.y, pause.index, 0.75f, 0.75f);

                    currentTimeMillis = System.currentTimeMillis();
                    elapsedTimeSeconds = (currentTimeMillis - playState.sTimer) / 1000L;


                    for (ShapeModel hole : holes) {
                        DrawImage(gl, hole.x, hole.y, hole.index, 1, 1);

                        if (hole.hasRabbit) {
                            DrawImage(gl, hole.x, hole.y + 5, 0, 0.8f, 0.8f);

                        }
                        //--------------------------------------------------DrawBoom--------------------------------------------------
                        //for first hammer
                        if (hole.isBoom) {
                            DrawImage(gl, hole.x, hole.y, 3, 1, 1);
                            hole.isBoom = false;
                        }

                        //--------------------------------------------------DrawBoom--------------------------------------------------
                    }
                    //--------------------------------------------------GenerateRabbitSpeed--------------------------------------------------
                    speed += 1;
                    if (speed % (playState.gameSpeed - playState.levelUp) == 0) {
                        generateRabbit();
                    }
                    //--------------------------------------------------GenerateRabbitSpeed--------------------------------------------------
                    int currentHearts = 7;
                    if (playState.numOfPlayers == 1) {
                        hammer.x = xMotion + 5;
                        hammer.y = yMotion + 1;
                        if(!collisionManger.isCollision(hammer.x,hammer.y,pause.x, pause.y, 12)) {
                            DrawImage(gl, hammer.x-5, hammer.y-1, hammer.index, 0.8f, 0.8f);

                        }
                        LevelUp();


                        drawWord(gl, -0.9F, 0.9f, "Timer", elapsedTimeSeconds);

                        drawWord(gl, -0.9F, 0.8f, "Player", userModel.username);

                        drawWord(gl, -0.9F, 0.7f, "Score", (long) score.user.score);

                        drawWord(gl, -0.9F, 0.6f, "Lives", "");
//                        DrawImage(gl, 13,82,37, 0.2F, 0.2F);
                        int x=3;
                        for (int i = 0; i < score.user.lives; i++) {
                            DrawImage(gl, 12+x,80,37, 0.2F, 0.2F);
                            x+=3;
                        }
                    } else {
                        hammer.x = xMotion + 5;
                        hammer.y = yMotion + 1;
                        if(!collisionManger.isCollision(hammer.x,hammer.y,pause.x, pause.y, 12)) {
                            DrawImage(gl, hammer.x-5, hammer.y-1, hammer.index, 0.8f, 0.8f);

                        }
                        LevelUp();


                        drawWord(gl, -0.9F, 0.9f, "Timer", elapsedTimeSeconds);

                        drawWord(gl, 0.4F, 0.8f, "Player 1", userModel.username);

                        drawWord(gl, 0.4F, 0.7f, "Score", (long) score.user.score);

                        drawWord(gl, 0.4F, 0.6f, "Lives", "");
                        int x=3;
                        for (int i = 0; i < score.user.lives; i++) {
                            DrawImage(gl, 76+x,81,37, 0.2F, 0.2F);
                            x+=3;
                        }

                        drawWord(gl, -0.9F, 0.8f, "Player 2", userModel2.username);

                        drawWord(gl, -0.9F, 0.7f, "Score2", (long) score2.user.score);

                        drawWord(gl, -0.9F, 0.6f, "Lives2", "");
                        int x2=3;
                        for (int i = 0; i < score2.user.lives; i++) {
                            DrawImage(gl, 12+x2,81,37, 0.2F, 0.2F);
                            x2+=3;
                        }


                        //--------------------------------------------------MultiPlayer--------------------------------------------------
                        DrawImage(gl, 20, 40, 17, 0.4f, 0.4f);//Q
                        DrawImage(gl, 50, 40, 18, 0.4f, 0.4f);//W
                        DrawImage(gl, 80, 40, 19, 0.4f, 0.4f);//E
                        DrawImage(gl, 20, 20, 20, 0.4f, 0.4f);//A
                        DrawImage(gl, 50, 20, 21, 0.4f, 0.4f);//S
                        DrawImage(gl, 80, 20, 22, 0.4f, 0.4f);//D


                        DrawImage(gl, xkey, ykey, 1, -1f, 1f);
                        MultiPlayer();

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

    //--------------------------------------------------Level Up--------------------------------------------------
    void LevelUp(){
        int c = playState.c;
        int d = playState.d;
        if(playState.numOfPlayers == 1){
            if((score.user.score+1)%d==0) {
                playState.setLevelUp();
            }
        }
        else {
            if((score2.user.score+1)%c==0||(score.user.score+1)%d==0) {
                playState.setLevelUp();
            }
        }
    }
    //--------------------------------------------------Level Up--------------------------------------------------

    //--------------------------------------------------MultiPlayer--------------------------------------------------
    void MultiPlayer() {

        for (Models.ShapeModel hole : holes) {

            if (collisionManger.isCollision(xkey, ykey, hole.x, hole.y, 6) && hole.hasRabbit) {
                hole.isBoom = true;
                hole.hasRabbit = false;
                generateRabbit();
                speed = 0;
                score2.user.score++;
                score2.setHighScore(score2.user.score);
                System.out.println(xkey + " " + ykey);
                System.out.println("Catch");
                xkey = 10;
                ykey = 50;
            }

            else if (collisionManger.isCollision(xkey, ykey, hole.x, hole.y, 6)) {

                if (score2.user.isLose()) {

                    if (!score.user.isLose()) {
                        System.out.println("Player 2 lost all lives. Player 1 wins!");
                    } else {
                        System.out.println("Both players lost all lives. It's a draw!");
                    }

                    playState.setLose();
                    break;
                }

                score2.itFall();
                xkey = 10;
                ykey = 50;
                System.out.println("fall");
            }
        }
    }

    //--------------------------------------------------MultiPlayer--------------------------------------------------
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



    public void DrawBackground(GL gl, int i) {
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

    private void drawWord(GL gl, float x, float y, String word, Long var) {
        gl.glRasterPos2f(x, y);
        String livesString = word + ": " + var;
        char[] var3 = livesString.toCharArray();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            char c = var3[var5];
            glut.glutBitmapCharacter(8, c);
        }

    }
    private void drawWord(GL gl, float x, float y, String word, String var) {
        gl.glRasterPos2f(x, y);
        String livesString = word + ": " + var;
        char[] var3 = livesString.toCharArray();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
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
//        PlayState multiPlayerer =  new PlayState(2);

        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
        xClicked = (int) (x / width * 100.0);
        yClicked = (int) (y / height * 100.0);
        yClicked = 100 - yClicked;
        playSound(hammerSound);

        switch (gameState.getGameState()) {
            case "start": {

                if ( collisionManger.isCollision(xClicked, yClicked, soundButton.x, soundButton.y, 6)) {
                    isSoundEnabled = !isSoundEnabled;
                    if (!isSoundEnabled) {
                        stopAllSounds();
                    } else if (backgroundMusic != null) {
                        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                    System.out.println(isSoundEnabled);
                    soundButton.index = isSoundEnabled ? 8 : 9;
                    System.out.println(soundButton.index);

                }

                if ( collisionManger.isCollision(xClicked, yClicked, 50, 70, 8)) { // Play button

                    gameState.setChooseNumberOfPlayers();
                } else if ( collisionManger.isCollision(xClicked, yClicked, 50, 50, 8)) { // Instructions button
                    gameState.setInstruction();
                } else if (collisionManger.isCollision(xClicked, yClicked, 50, 30, 8)) {
                    String message;
                    if (userModel != null && userModel.username != null && !userModel.username.trim().isEmpty()) {
                        message = userModel.username + ", are you sure you want to leave us ??";
                    } else {
                        message = "Are you sure you want to exit the game?";
                    }

                    // Show confirmation dialog
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            message,
                            "Exit Confirmation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        System.out.println("Exiting the game...");
                        System.exit(0); // Exit the game
                    } else {
                        System.out.println("Player chose to stay in the game.");
                    }
                }

            }
            break;

            case "instruction": {
                if ( collisionManger.isCollision(xClicked, yClicked, 90, 90, 5)) { // Back button
                    gameState.setStart();
                }
            }
            break;

            case "chooseMode": {

                if ( collisionManger.isCollision(xClicked, yClicked, 50, 70, 8)) { // Easy mode
                    playState.setEasyMode();
                    gameState.setStartPlay();
                    playState.sTimer = System.currentTimeMillis();
                } else if ( collisionManger.isCollision(xClicked, yClicked, 50, 50, 8)) { // Medium mode
                    playState.setMediumMode();
                    gameState.setStartPlay();
                    playState.sTimer = System.currentTimeMillis();
                } else if ( collisionManger.isCollision(xClicked, yClicked, 50, 30, 8)) { // Hard mode
                    playState.setHardMode();
                    gameState.setStartPlay();
                    playState.sTimer = System.currentTimeMillis();
                } else if ( collisionManger.isCollision(xClicked, yClicked, 90, 90, 5)) { // Back button
                    gameState.setStart();
                }
            }
            break;


            case "chooseNumberOfPlayers": {

//                if (isCatch(xClicked, yClicked, 50, 50, 5)) { // Multi Pl butt                    gameState.setStart();
//                }

                if ( collisionManger.isCollision(xClicked, yClicked, 50, 70, 10)) { // single Pl butt
                    String playerName = null;
                    boolean validInput = false;

                    do {
                        playerName = JOptionPane.showInputDialog(null, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);

                        if (playerName != null && !playerName.trim().isEmpty()) {
                            validInput = true;
                            userModel.setUsername(playerName.trim());
                            System.out.println(userModel.username);

                            playState= new PlayState(1);
                            gameState.setChooseMode();
                        } else {
                            int choice = JOptionPane.showConfirmDialog(
                                    null,
                                    "Name cannot be empty. Would you like to try again?",
                                    "Error",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.ERROR_MESSAGE
                            );

                            if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                break;
                            }
                        }
                    } while (!validInput);
                }
                if (collisionManger.isCollision(xClicked, yClicked, 50, 30, 10)) {
                    String player1Name = null;
                    String player2Name = null;
                    boolean valid1Input = false;
                    boolean valid2Input = false;

                    do {
                        player1Name = JOptionPane.showInputDialog(null, "Enter Player 1's name:", "Player Name", JOptionPane.PLAIN_MESSAGE);

                        if (player1Name != null && !player1Name.trim().isEmpty()) {
                            valid1Input = true;
                        } else {
                            int choice = JOptionPane.showConfirmDialog(
                                    null,
                                    "Player 1's name cannot be empty. Would you like to try again?",
                                    "Error",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.ERROR_MESSAGE
                            );

                            if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                break;
                            }
                        }
                    } while (!valid1Input);

                    if (valid1Input) {
                        do {
                            player2Name = JOptionPane.showInputDialog(null, "Enter Player 2's name:", "Player Name", JOptionPane.PLAIN_MESSAGE);

                            if (player2Name != null && !player2Name.trim().isEmpty()) {
                                valid2Input = true;
                            } else {
                                int choice = JOptionPane.showConfirmDialog(
                                        null,
                                        "Player 2's name cannot be empty. Would you like to try again?",
                                        "Error",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.ERROR_MESSAGE
                                );

                                if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                    break;
                                }
                            }
                        } while (!valid2Input);


                        if (valid2Input) {
                            userModel.setUsername(player1Name.trim());
                            System.out.println("Player 1: " + userModel.username);

                            userModel2.setUsername(player2Name.trim());
                            System.out.println("Player 2: " + userModel2.username);


                            playState = new PlayState(2);
                            score = new ScoreModel(new UserModel(player1Name.trim(), 0), 0, 7);
                            score2 = new ScoreModel(new UserModel(player2Name.trim(), 0), 0, 7);

                            gameState.chooseModeFor2Players();
                        }
                    }

                    if (!valid1Input || !valid2Input) {
                        System.out.println("Game did not start due to invalid input.");
                    }
                }


                if ( collisionManger.isCollision(xClicked, yClicked, 90, 90, 5)) { // Back button
                    gameState.setStart();
                }
            }
            break;
            case "chooseModeFor2Players": {
                if ( collisionManger.isCollision(xClicked, yClicked, 50, 70, 8)) { // Easy mode
                    playState.setEasyMode();
                    gameState.setStartPlay();
                    playState.sTimer = System.currentTimeMillis();
                } else if ( collisionManger.isCollision(xClicked, yClicked, 50, 50, 8)) { // Medium mode
                    playState.setMediumMode();
                    gameState.setStartPlay();
                    playState.sTimer = System.currentTimeMillis();
                } else if ( collisionManger.isCollision(xClicked, yClicked, 50, 30, 8)) { // Hard mode
                    playState.setHardMode();
                    gameState.setStartPlay();
                    playState.sTimer = System.currentTimeMillis();
                } else if ( collisionManger.isCollision(xClicked, yClicked, 90, 90, 5)) {
                    gameState.setStart();
                }
            }
            break;
            case "startPlay": {

                if (playState.isLose) {
                    if( collisionManger.isCollision(xClicked,yClicked,50,20,10)){
                        playState.isLose =false;
                        score.user.lives = 7;
                        score.user.score=0;
                        // if multi
                        score2.user.lives = 7;
                        score2.user.score=0;
                        // if multi
                        playState.sTimer =System.currentTimeMillis();
                        playState.setRestart();
                        System.out.println(playState.gameSpeed - playState.levelUp);
                    }
                    if (collisionManger.isCollision(xClicked, yClicked, 50, 7, 8)) {
                        String exitMessage = (userModel != null && userModel.username != null && !userModel.username.trim().isEmpty())
                                ? userModel.username + ", are you sure you want to exit to the home page?"
                                : "Are you sure you want to exit to the home page?";

                        int choice = JOptionPane.showConfirmDialog(
                                null,
                                exitMessage,
                                "Exit Confirmation",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            // Reset game state and go to the start screen
                            gameState.setStart();
                            playState.isPaused = false;
                            score.user.lives = 7;
                            score.user.score = 0;
                            score2.user.lives = 7;
                            score2.user.score = 0;

                            System.out.println("Returned to Start Screen");
                        } else {
                            // Stay in the current loss state
                            System.out.println("Player chose to stay on the loss page.");
                            playState.setLose();
                        }
                    } else {
                        // Ensure the game stays in the loss state if the exit button wasn't clicked
                        if (playState.isPaused) {
                            playState.setLose();
                        }
                    }




                } else if (playState.isPaused) {
                    if ( collisionManger.isCollision(xClicked, yClicked, pause.x, pause.y, 5)) {
                        playState.isPaused = !playState.isPaused;
                        System.out.println("Resume");
                    }
                    if ( collisionManger.isCollision(xClicked, yClicked, resume.x, resume.y, 8)) {
                        playState.isPaused = false;
                        System.out.println("Resume");
                    }
                    if (collisionManger.isCollision(xClicked, yClicked, restart.x, restart.y, 8)) {
                        // Check if the userModel is available and has a valid username
                        if (userModel != null && userModel.username != null && !userModel.username.trim().isEmpty()) {
                            // Ask with player's name
                            int choice = JOptionPane.showConfirmDialog(
                                    null,
                                    "Your score will be removed "+userModel.username + ", are you sure you want to restart the game?",
                                    "Restart Confirmation",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (choice == JOptionPane.YES_OPTION) {
                                // Reset game state and restart
                                playState.isPaused = false;
                                score.user.lives = 7;
                                score.user.score = 0;
                                score2.user.lives = 7;
                                score2.user.score = 0;
                                playState.sTimer = System.currentTimeMillis();
                                playState.setRestart();
                                System.out.println("Game Restarted");
                                System.out.println("Game Speed: " + (playState.gameSpeed - playState.levelUp));
                            } else {
                                System.out.println(userModel.username + " chose not to restart the game.");
                            }
                        } else {
                            // Default confirmation if player name is not available
                            int choice = JOptionPane.showConfirmDialog(
                                    null,
                                    "Are you sure you want to restart the game?",
                                    "Restart Confirmation",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (choice == JOptionPane.YES_OPTION) {
                                // Reset game state and restart
                                playState.isPaused = false;
                                score.user.lives = 7;
                                score.user.score = 0;
                                score2.user.lives = 7;
                                score2.user.score = 0;
                                playState.sTimer = System.currentTimeMillis();
                                playState.setRestart();
                                System.out.println("Game Restarted");
                                System.out.println("Game Speed: " + (playState.gameSpeed - playState.levelUp));
                            } else {
                                System.out.println("User chose not to restart the game.");
                            }
                        }
                    }

                    if ( collisionManger.isCollision(xClicked, yClicked, exit.x, exit.y, 8)) {
                        if (userModel != null && userModel.username != null && !userModel.username.trim().isEmpty()) {
                        // Ask with player's name
                        int choice = JOptionPane.showConfirmDialog(
                                null,
                                "your score will be removed "+userModel.username + ", "+"Do you want to exit to the home page? ",
                                "Exit Confirmation",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            // Reset game state and return to start screen
                            gameState.setStart();
                            playState.isPaused = false;

                            // Reset scores and lives for the next game
                            score.user.lives = 7;
                            score.user.score = 0;
                            score2.user.lives = 7;
                            score2.user.score = 0;

                            System.out.println("Returned to Start Screen");
                        } else {
                            System.out.println(userModel.username + " choose to stay in the game.");
                        }
                    }
                        else {
                        // Default confirmation if player name is not available
                        int choice = JOptionPane.showConfirmDialog(
                                null,
                                "Do you want to exit to the home page?",
                                "Exit Confirmation",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            // Reset game state and return to start screen
                            gameState.setStart();
                            playState.isPaused = false;

                            // Reset scores and lives for the next game
                            score.user.lives = 7;
                            score.user.score = 0;
                            score2.user.lives = 7;
                            score2.user.score = 0;

                            System.out.println("Returned to Start Screen");
                        } else {
                            System.out.println("User choose to stay in the game.");
                        }
                    }
                    }
                } else {
                    if ( collisionManger.isCollision(xClicked, yClicked, pause.x, pause.y, 5)) {
                        playState.isPaused = !playState.isPaused;
                        System.out.println("Pause");
                    }

                    int r = 10;
                    for (ShapeModel holeAxis : holes) {
                        if ( collisionManger.isCollision(xClicked, yClicked, holeAxis.x+5, holeAxis.y-1, r) && holeAxis.hasRabbit) {
                            holeAxis.isBoom = true;
                            holeAxis.hasRabbit = false;
                            generateRabbit();
                            speed = 0;
                            score.user.score++;
                            score.setHighScore(score.user.score);
                            System.out.println("Catch");
                        } else if ( collisionManger.isCollision(xClicked, yClicked, holeAxis.x+5, holeAxis.y-1, r)) {
                            if (score.user.isLose()) {
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


    @Override
    public void mousePressed(MouseEvent e) {
        if (!(playState.isLose || playState.isPaused)) {
            hammer.index = textureNames.length - 9;

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!(playState.isLose || playState.isPaused)) {
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
        playSound(hammerSound);

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            xkey = 20;
            ykey = 50;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            xkey = 50;
            ykey = 50;
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            xkey = 80;
            ykey = 50;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            xkey = 20;
            ykey = 30;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            xkey = 50;
            ykey = 30;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            xkey = 80;
            ykey = 30;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playState.isPaused = !playState.isPaused;
        }


    }

    private void initSounds() {
        try {

            File backgroundFile = new File(soundsFolderPath + "song.wav");
            if (!backgroundFile.exists()) {
                System.out.println("Warning: Background music file not found at: " + backgroundFile.getAbsolutePath());
                return;
            }

            AudioInputStream backgroundStream = AudioSystem.getAudioInputStream(backgroundFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(backgroundStream);



            File hammerFile = new File(soundsFolderPath + "hammer.wav");
            if (!hammerFile.exists()) {
                System.out.println("Warning: Hammer sound file not found at: " + hammerFile.getAbsolutePath());
                return;
            }

            AudioInputStream hammerStream = AudioSystem.getAudioInputStream(hammerFile);
            hammerSound = AudioSystem.getClip();
            hammerSound.open(hammerStream);


            File winFile = new File(soundsFolderPath + "win.wav");
            if (!winFile.exists()) {
                System.out.println("Warning: Win sound file not found at: " + winFile.getAbsolutePath());
                return;
            }

            AudioInputStream winStream = AudioSystem.getAudioInputStream(winFile);
            winSound = AudioSystem.getClip();
            winSound.open(winStream);


            File loseFile = new File(soundsFolderPath + "gameover.wav");
            if (!loseFile.exists()) {
                System.out.println("Warning: Game over sound file not found at: " + loseFile.getAbsolutePath());
                return;
            }

            AudioInputStream loseStream = AudioSystem.getAudioInputStream(loseFile);
            loseSound = AudioSystem.getClip();
            loseSound.open(loseStream);


            if (backgroundMusic != null) {
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);

                FloatControl gainControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-30.0f);
            }

        } catch (Exception e) {
            System.out.println("Error initializing sounds: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void playSound(Clip clip) {
        if (clip != null && isSoundEnabled) {
            try {
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                System.out.println("Error playing sound: " + e.getMessage());
            }
        }
    }

    public void toggleSound() {
        isSoundEnabled = !isSoundEnabled;
        if (!isSoundEnabled) {
            stopAllSounds();
        } else if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private void stopAllSounds() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
        if (hammerSound != null && hammerSound.isRunning()) {
            hammerSound.stop();
        }
        if (winSound != null && winSound.isRunning()) {
            winSound.stop();
        }
        if (loseSound != null && loseSound.isRunning()) {
            loseSound.stop();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }
    private void handleGameOver(boolean won) {
        if (won) {
            playSound(winSound);
        } else {
            playSound(loseSound);
        }
        stopBackgroundMusic();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}