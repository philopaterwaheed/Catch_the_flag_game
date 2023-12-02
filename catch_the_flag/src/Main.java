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
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(500, 300);
        setLocationRelativeTo(this);
        setVisible(true);
    }



}


