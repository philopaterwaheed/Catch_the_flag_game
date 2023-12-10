package cs304;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class InstactionFram extends JFrame implements MouseListener {
    private JLabel background;
    private JTextArea InstractionContant;
    private JButton Back;
    private Border border;
    private Cursor cursor;
    private JScrollPane ScrollPane;
    public void main(Object o) {
        setIconImage(Toolkit.getDefaultToolkit().getImage("Assets\\navBarIcon.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Catch The Flag");
        setSize(900, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        border = BorderFactory.createLineBorder(Color.YELLOW,2);
        cursor = new Cursor(Cursor.HAND_CURSOR);
        Back = new JButton("Back");
        Back.setForeground(Color.YELLOW);
        Back.setFont(new Font("Tahoma", Font.BOLD, 18));
        Back.setBackground(Color.BLACK);
        Back.setBounds(300, 400, 240, 40);
        Back.setBorder(null);
        Back.setFocusable(false);
        Back.addMouseListener(this);
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                BackActionPerformed(evt);
            }
        });
        getContentPane().add(Back);

        String Contant="mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm\n" +
                "mmmmmmmmmmmmmmmmmmmmmmmmmmm\n" +
                "mmmmmmmmmmmmmmmmmmmmmm\n" +
                "mmmmmmmmmmmmmmmmmmm\n" +
                "mmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                "mmmmmmmmmmmmmmmmmm\n" +
                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm\n" +
                "mmmmmmmmmmmmmmmmmmmmmmmm\n" +
                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm\n" +
                "mmmmmmmmmmmmmmmmmmmm\n";
        InstractionContant=new JTextArea();
        InstractionContant.setText(Contant);
        InstractionContant.setFont(new Font("MV Boli",Font.BOLD,30));
        InstractionContant.setBackground(Color.BLACK);
        InstractionContant.setForeground(Color.WHITE);
        ScrollPane= new JScrollPane(InstractionContant
                ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollPane.setBounds(90,30,700,350);
        ScrollPane.setBackground(Color.RED);
        ScrollPane.setBorder(border);
        getContentPane().add(ScrollPane);
        background = new JLabel(new ImageIcon("Assets\\Back100.jpg"));
        background.setBounds(0, 0, 890, 470);
        background.setLayout(new FlowLayout());
        getContentPane().add(background);
    }
    private void BackActionPerformed(ActionEvent evt) {
        new Menu();
        dispose();
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
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
        if(e.getSource()==Back)
        {
            Back.setBorder(border);
            Back.setCursor(cursor);
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==Back)
        {
            Back.setBorder(null);
        }
    }
}
