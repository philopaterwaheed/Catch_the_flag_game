import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class eventListener implements GLEventListener {

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(0.5f, 0.5f, 0.5f, 0.0f); // the color of the canvas ;

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity(); // resets the identity of the matrix ;
        gl.glOrtho(0.0, 100.0, 0.0, 100.0, -1, 1);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);


    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
}