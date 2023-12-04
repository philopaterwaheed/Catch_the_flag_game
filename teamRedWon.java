package cs304;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class teamRedWon extends JFrame {

	private JLabel lblNewLabel;
	private JButton backToMenu;
	private JLabel background;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					teamRedWon frame = new teamRedWon();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public teamRedWon() {
		getContentPane().setBackground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Assets\\navBarIcon.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Catch The Flag");
		setSize(650, 360);
		setVisible(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		lblNewLabel = new JLabel("TEAM RED WON");
		lblNewLabel.setFont(new Font("Wide Latin", Font.ITALIC, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(170, 100, 320, 50);
		getContentPane().add(lblNewLabel);

		backToMenu = new JButton("Back To Menu");
		backToMenu.setFont(new Font("Tahoma", Font.BOLD, 18));
		backToMenu.setForeground(Color.YELLOW);
		backToMenu.setBackground(Color.BLACK);
		backToMenu.setBounds(220, 210, 180, 30);
		backToMenu.setFocusable(false);
		backToMenu.setBorder(null);
		getContentPane().add(backToMenu);
		backToMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuActionPerformed(evt);
			}
		});

		background = new JLabel(new ImageIcon("Assets\\Trophy.jpg"));
		background.setBounds(0, 0, 634, 321);
		background.setLayout(new FlowLayout());
		getContentPane().add(background);

	}

	private void menuActionPerformed(ActionEvent evt) {
		new Menu();
		dispose();
	}
}
