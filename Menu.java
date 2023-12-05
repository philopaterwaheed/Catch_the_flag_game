package cs304;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;

public class Menu extends JFrame {

	static Anim anim;
	private JLabel background;
	private JButton play;
	private JButton exit;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Menu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Menu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("Assets\\navBarIcon.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Catch The Flag");
		setSize(900, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		play = new JButton("PLAY");
		play.setForeground(Color.YELLOW);
		play.setFont(new Font("Tahoma", Font.BOLD, 18));
		play.setBackground(Color.BLACK);
		play.setBounds(300, 140, 240, 40);
		play.setBorder(null);
		play.setFocusable(false);
		play.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				playActionPerformed(evt);
			}
		});
		getContentPane().add(play);

		exit = new JButton("Exit");
		exit.setFont(new Font("Tahoma", Font.BOLD, 18));
		exit.setForeground(Color.YELLOW);
		exit.setBackground(Color.BLACK);
		exit.setBounds(300, 250, 240, 40);
		exit.setFocusable(false);
		exit.setBorder(null);
		exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				exitActionPerformed(evt);
			}
		});
		getContentPane().add(exit);

		background = new JLabel(new ImageIcon("Assets\\menu.jpg"));
		background.setBounds(0, 0, 880, 460);
		background.setLayout(new FlowLayout());
		getContentPane().add(background);
	}

	private void playActionPerformed(ActionEvent evt) {
		new AnimGLEventListener().main(null);
		dispose();
	}

	private void exitActionPerformed(ActionEvent evt) {
		System.exit(0);
	}
}
