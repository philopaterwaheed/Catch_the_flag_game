import java.awt.BorderLayout;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;
public class Main extends  JFrame {
    public static void main(String[] args) {
        new Main();
    }
    GLCanvas glcanvas;
    eventListener listener = new eventListener();
    public Main(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(500, 300);
        setLocationRelativeTo(this);
        setVisible(true);
    }
}