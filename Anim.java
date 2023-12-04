package cs304;

import Textures.AnimListener;
import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {

  public Anim(AnimListener aListener) {
    GLCanvas glcanvas;
    Animator animator;

    AnimListener listener = aListener;
    glcanvas = new GLCanvas();
    glcanvas.addGLEventListener(listener);
    glcanvas.addKeyListener(listener);
    glcanvas.addMouseListener(listener);
    getContentPane().add(glcanvas, BorderLayout.CENTER);
    animator = new FPSAnimator(30);
    animator.add(glcanvas);
    animator.start();

    setIconImage(Toolkit.getDefaultToolkit()
        .getImage("Assets\\navBarIcon.jpg"));
    setTitle("Catch The Flag");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 500);
    setLocationRelativeTo(null);
    setVisible(true);
    setFocusable(true);
    glcanvas.requestFocus();
  }
}
