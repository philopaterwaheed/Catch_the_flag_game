package cs304;

import Textures.TextureReader;
import com.sun.opengl.util.FPSAnimator;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashSet;
//import java.awt.event.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class SimpleJoglApp extends JFrame {

    int w = Glob.w, h = Glob.h;
    private GLCanvas glcanvas;
    private SimpleGLEventListener listener = new SimpleGLEventListener();
    static FPSAnimator animator = null;

    public static void main(String[] args) {
        final SimpleJoglApp app = new SimpleJoglApp();
        animator.start();
    }

    public SimpleJoglApp() {
        // set the JFrame title
        super("Simple JOGL Application");

        // kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // only three JOGL lines of code ... and here they are
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);
        animator = new FPSAnimator(glcanvas, 60);

        add(glcanvas, BorderLayout.CENTER);
        setSize(w, h);

        centerWindow();
        setVisible(true);
    }

    public void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();

        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;

        this.setLocation(
                (screenSize.width - frameSize.width) >> 1,
                (screenSize.height - frameSize.height) >> 1);
    }
}

class SimpleGLEventListener implements GLEventListener, KeyListener, MouseListener {
    int w = Glob.w, h = Glob.h;
    int xPos = w / 2, yPos = h / 2, r = 10, orgX = w / 2, orgY = h / 2, c = 5, knockBack = 0;
    int x2 = xPos - 100, y2 = yPos - 100;

    int maxWidth = w;
    int maxHeight = h;
    Ball b1 = new Ball(w / 4, h / 2);
    Ball b2 = new Ball(w / 2 - 100, h / 2 - 100);
    int[] cX = new int[2];
    int[] cY = new int[2];
    String textureNames[] = { "Man1.png", "Man2.png", "Man3.png", "Man4.png", "Back1.png" };
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    Rectangle[] bound = { new Rectangle(w, 0, w + 100, h + 100), new Rectangle(0, h, w, h + 100),
            new Rectangle(-100, 0, 0, h), new Rectangle(0, -100, w, 0), new Rectangle(100, 100, 200, 200),
            new Rectangle(100, 400, 200, 500)
    };

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0, w, 0.0, h, -1.0, 1.0);

        gl.glEnable(GL.GL_TEXTURE_2D); // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture("Assets" + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

    }

    public void drawPoly(GL gl, Color c, int r, double startAngle, int sides, int step, double x_pos,
            double y_pos) {
        // gl.glColor3fv(c.getColorComponents(null),0);
        gl.glBegin(GL.GL_POLYGON);
        double ang = 360 / sides;
        for (int i = 0; i < sides; i++) {
            double x = x_pos + r * Math.cos(Math.toRadians(i * step * ang + startAngle));
            double y = y_pos + r * Math.sin(Math.toRadians(i * step * ang + startAngle));
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    public void drawLine(GL gl, int x, int y) {
        // gl.glColor3fv(c.getColorComponents(null),0);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2i(x, y + r);
        gl.glVertex2i(x, y + r + 12);
        gl.glEnd();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        // translate a position (move x by 100, y by 0, z by 0)
        // note that you translate the whole opengl
        // gl.glPushMatrix();
        // gl.glTranslated(100,0,0);
        // gl.glPopMatrix();
        // use color object new Color(1.0,1.0,1.0)
        // gl.glColor3fv(c.getColorComponents(null),0);

        // scale x,y,z axis
        // gl.glScaled(0.5,0.5,1)
        // change background color
        // gl.glClearColor(1.0f,1.0f,0.0f,0.0f);

        // drawPoly(gl,new Color(1.0f,0.0f,0.0f),50,0,360,1,wid/2,h/2);
        // drawPoly(gl,new Color(1.0f,0.0f,1.0f),50,0,360,1,wid+wid/2,h/2);

        // gl.glBegin(GL.GL_POINTS);
        // gl.glBegin(GL.GL_POLYGON);
        // gl.glColor3fv(new Color(1.0f,0.0f,0.0f).getColorComponents(null),0);
        // draw background
        // System.out.println(animationIndex);
        if (b1.knockBack > 0) {
            b1.knockBack--;
            b1.moveBallX(b1.x_dir, bound);
            b1.moveBallY(b1.y_dir, bound);
        }
        if (b2.knockBack > 0) {
            b2.knockBack--;
            b2.moveBallX(b2.x_dir, bound);
            b2.moveBallY(b2.y_dir, bound);
        }
        drawPoly(gl, new Color(0, 1, 1), r, 0, 360, 1, b1.x, b1.y);
        drawPoly(gl, new Color(0, 1, 1), r, 0, 360, 1, b2.x, b2.y);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2f(100, 100);
        gl.glVertex2f(100, 200);
        gl.glVertex2f(200, 200);
        gl.glVertex2f(200, 100);
        gl.glEnd();

        // drawPoly(gl,new Color(0,1,1),r,0,360,1,b2.x,b2.y);
        // drawPoly(gl,new Color(0,1,1),r,0,360,1,x2,y2);
        // drawLine(gl,xPos,yPos);

        // DrawSprite(gl, 0,0, 5, 1,w,h);
        // DrawSprite(gl, 0,h/2, 4, 1,40,40);

        // gl.glColor3f(0.0f, 1.0f, 0.0f);
        // drawPoly(gl,new Color(1,1,1),30,0,360,1,xPos,yPos);
        // gl.glColor3f(1.0f, 1.0f, 1.0f);

        // DrawGraph(gl);
    }

    public void DrawSprite(GL gl, int x, int y, int index, double scale, int w, int h) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]); // Turn Blending On

        // System.out.println(x +" " + y);
        // gl.glPushMatrix();
        // gl.glScaled(4,4,1);
        gl.glBegin(GL.GL_QUADS);
        // v1
        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(x, y);
        // v2
        gl.glTexCoord2f(0, 1);
        gl.glVertex2i(x, y + h);
        // v3
        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(x + w, y + h);
        // v4
        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(x + w, y);

        gl.glEnd();
        gl.glDisable(GL.GL_BLEND);
        // gl.glPopMatrix();
        // gl.glScaled(1,1,1);
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]); // Turn Blending On

        gl.glPushMatrix();
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

    public void reshape(
            GLAutoDrawable drawable,
            int x,
            int y,
            int width,
            int height) {
    }

    public void displayChanged(
            GLAutoDrawable drawable,
            boolean modeChanged,
            boolean deviceChanged) {
    }

    public void dispose(GLAutoDrawable arg0) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // key typed working
        // if(e.getKeyCode()==KeyEvent.VK_UP)yPos+=c;
        // else if(e.getKeyCode()==KeyEvent.VK_DOWN)yPos-=c;
        // else if(e.getKeyCode()==KeyEvent.VK_RIGHT)xPos+=c;
        // else if(e.getKeyCode()==KeyEvent.VK_LEFT)xPos-=c;
    }

    // Boolean isIntersect(int a,int b,int c,int d){
    // return !(b<c||d<a);
    // }
    // public Boolean isIntersect(Ball b1,Ball b2){
    // int a=b1.x,b=b1.y,c=b2.x,d=b2.y;
    // return isIntersect(a-r,a+r,c-r,c+r)&&isIntersect(b-r,b+r,d-r,d+r);
    // }
    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println(xPos+" "+yPos);

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            b1.knockBack = 20;
            b1.x_dir = -1;
            b1.y_dir = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            b2.knockBack = 10;
            b2.x_dir = 0;
            b2.y_dir = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            b1.y_dir = 1;
            b1.x_dir = 0;
            b1.moveBallY(1, bound);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            b1.y_dir = -1;
            b1.x_dir = 0;
            b1.moveBallY(-1, bound);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            b1.y_dir = 0;
            b1.x_dir = 1;
            b1.moveBallX(1, bound);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            b1.y_dir = 0;
            b1.x_dir = -1;
            b1.moveBallX(-1, bound);
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            b2.y_dir = 1;
            b2.x_dir = 0;
            b2.moveBallY(1, bound);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            b2.y_dir = -1;
            b2.x_dir = 0;
            b2.moveBallY(-1, bound);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            b2.y_dir = 0;
            b2.x_dir = 1;
            b2.moveBallX(1, bound);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            b2.y_dir = 0;
            b2.x_dir = -1;
            b2.moveBallX(-1, bound);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // gets x,y of mouse
        // xPos,yPos now get mouse location
        // System.out.println(w+" "+h);

        // int x=e.getX();
        // int y=e.getY();
        // Component c=e.getComponent();
        // double w1=c.getWidth();
        // double h1=c.getHeight();
        // xPos=(int)((x/w1)*w);
        // yPos=(int)((y/h1)*h);
        // yPos=h-yPos;
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

}