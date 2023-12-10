import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import java.awt.BorderLayout;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;
public class Main extends  JFrame {

    public static void main(String[] args) {
        new Main();
    }
    GLCanvas glcanvas;
    Animator animator;
    Music mainMusic = new Music("sounds//music//The Verdant Grove LOOP.wav",true);
    eventListener listener = new eventListener();
    public Main(){
        mainMusic.playMusic();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);//add key listener
        animator = new FPSAnimator(18);
        animator.add(glcanvas);
        animator.start();
        glcanvas.setFocusable(true);//set focus for Listener
        glcanvas.requestFocus();
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(1000, 700);
        setLocationRelativeTo(this);
        setVisible(true);
    }
}


