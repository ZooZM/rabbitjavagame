
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;

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
        add(glcanvas, "Center");
        Animator animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();
        setTitle("Catch The rabbit!!");
        setDefaultCloseOperation(3);
        setSize(700, 700);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
//        glcanvas.requestFocus();
    }
}
