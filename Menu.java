package cs304;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Menu extends JFrame implements MouseListener {
	static Anim anim;
	private JLabel background;
    private JLabel BlueFlag;
    private JLabel RedFlag;
	private JLabel GameName;
	private JButton play;
	private JButton exit;
	private JButton Instraction;
	private Border border;
    private Border border1;
	private Cursor cursor;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try
				{
					new Menu();

				} catch (Exception e)
				{
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
		border = BorderFactory.createLineBorder(Color.YELLOW,2);
        border1 = BorderFactory.createLineBorder(Color.BLACK,2);
		cursor = new Cursor(Cursor.HAND_CURSOR);
		ImageIcon Image1 = new ImageIcon("Assets\\redflagbb.png");
		ImageIcon Image2 = new ImageIcon("Assets\\blueflagbb.png");
		GameName = new JLabel("Catch The Flag");
		GameName.setForeground(Color.YELLOW);
		GameName.setFont(new Font("Tahoma", Font.BOLD, 50));
		GameName.setBackground(Color.BLACK);
		GameName.setBounds(250, 100, 400, 60);
		GameName.setBorder(null);
		GameName.setFocusable(false);
		getContentPane().add(GameName);
		play = new JButton("PLAY");
		play.setForeground(Color.BLACK);
		play.setFont(new Font("Tahoma", Font.BOLD, 18));
		play.setBackground(Color.BLUE);
		play.setBounds(50, 360, 240, 40);
		play.setBorder(border1);
		play.setFocusable(false);
		play.addMouseListener(this);
		play.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				playActionPerformed(evt);
			}
		});
		getContentPane().add(play);
		Instraction = new JButton("Help");
		Instraction.setForeground(Color.BLACK);
		Instraction.setFont(new Font("Tahoma", Font.BOLD, 18));
		Instraction.setBackground(Color.WHITE);
		Instraction.setBounds(320, 360, 240, 40);
		Instraction.setBorder(border1);
		Instraction.setFocusable(false);
		Instraction.addMouseListener(this);
		Instraction.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				InstractionActionPerformed(evt);
			}
		});
		getContentPane().add(Instraction);
		exit = new JButton("Exit");
		exit.setFont(new Font("Tahoma", Font.BOLD, 18));
		exit.setForeground(Color.BLACK);
		exit.setBackground(Color.RED);
		exit.setBounds(600, 360, 240, 40);
		exit.setFocusable(false);
		exit.setBorder(border1);
		exit.addMouseListener(this);
		exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				exitActionPerformed(evt);
			}
		});
		getContentPane().add(exit);
        BlueFlag = new JLabel(new ImageIcon("Assets\\blueflagbb.png"));
        BlueFlag.setBounds(0, 7, 400, 300);
        BlueFlag.setLayout(null);
        getContentPane().add(BlueFlag);
        RedFlag = new JLabel(new ImageIcon("Assets\\redflagbb.png"));
        RedFlag.setBounds(470, 7, 400, 300);
        RedFlag.setLayout(new FlowLayout());
        getContentPane().add(RedFlag);
        background = new JLabel(new ImageIcon("Assets\\Back100.jpg"));
        background.setBounds(0, 0, 890, 470);
        background.setLayout(new FlowLayout());
        getContentPane().add(background);
	}
	private void playActionPerformed(ActionEvent evt) {
		new AnimGLEventListener_temp().main(null);
		dispose();
	}
	private void InstractionActionPerformed(ActionEvent evt) {
		new InstactionFram().main(null);
		dispose();
	}
	private void exitActionPerformed(ActionEvent evt) {
		System.exit(0);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==play)
		{
			play.setBorder(border);
			play.setCursor(cursor);
		}
		if(e.getSource()==exit)
		{
			exit.setBorder(border);
			exit.setCursor(cursor);
		}
		if(e.getSource()==Instraction)
		{
			Instraction.setBorder(border);
			Instraction.setCursor(cursor);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==play)
		{
			play.setBorder(border1);
		}
		if(e.getSource()==exit)
		{
			exit.setBorder(border1);
		}
		if(e.getSource()==Instraction)
		{
			Instraction.setBorder(border1);
		}
	}
}
