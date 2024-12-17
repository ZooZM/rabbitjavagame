
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;
import java.awt.*;


public class RabbitGame extends JFrame {
    RabbitListener listener = new RabbitGLEvrntListener();

    public static void main(String[] args) {
        new RabbitGame();
    }

    public RabbitGame() {

        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseListener(listener);
        glcanvas.addMouseMotionListener(listener);
        glcanvas.addKeyListener(listener);
        add(glcanvas, BorderLayout.CENTER);
        Animator animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();
        setTitle("Catch The rabbit!!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
//        glcanvas.requestFocus();
    }
}
