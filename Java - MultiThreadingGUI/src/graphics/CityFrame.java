package graphics;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import vehicles.Vehicle;

/**
 * Singleton Class!
 * 
 * @author benja
 *
 */
public class CityFrame extends JFrame implements ActionListener {

	/* Frame properties */
	private static JFrame mainFrame = new JFrame("City"); /* public because we use it also in Vehicle Class */

	/* Menu bar fields */
	private JMenuBar menuBar = new JMenuBar();
	private JMenu FileMenu = new JMenu("File");
	private JMenu HelpMenu = new JMenu("Help");
	private JMenuItem exitItem = new JMenuItem("Exit");
	private JMenuItem helpItem = new JMenuItem("Help");

	/**
	 * Default Constructor
	 */
	private CityFrame() {
		/* Setting action listener to buttons */
		this.exitItem.addActionListener(this);
		this.helpItem.addActionListener(this);

		/* setting Menu */
		this.FileMenu.add(this.exitItem);
		this.HelpMenu.add(this.helpItem);
		this.menuBar.add(this.FileMenu);
		this.menuBar.add(this.HelpMenu);

		/* setting frame components & commands */
		try {
			Vehicle.imgCity = ImageIO.read(this.getClass().getResource(IDrawable.PICTURE_PATH + "cityBackground.png"));
		} catch (IOException e) {
		}
		
		Vehicle.pan = CityPanel.getInstance();
		mainFrame.setJMenuBar(this.menuBar);
		mainFrame.add(Vehicle.pan);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setSize(800, 635);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	/**
	 * Getter
	 * 
	 * @return mainFrame : JFrame
	 */
	public static JFrame getInstance() {
		return mainFrame;
	}

	/**
	 * Setting action listener for each button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* exitItem button */
		if (e.getSource() == this.exitItem) {
			System.exit(0);
		}

		/* Help Item button */
		if (e.getSource() == this.helpItem) {
			JOptionPane.showMessageDialog(null, "Multi-Threading & basic gui");
		}
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new CityFrame();
		
		JLabel nameLabel = new JLabel("<html>"
				+ "-------------------------------------<br/>"
				+ "Made by Binyamin Yakobi<br/>"
				+ "-------------------------------------<br/><br/>"
				+ "In this program:<br/><br/>"
				+ "I am using Design Patterns & GUI (swing) to visualize<br/>"
				+ "Multi threading with synchronization.<br/><br/>"
				+ "Deisgn patterns: Singleton, Threadpool,<br/>"
				+ "Decorator, Memento, Observer, Factory Method<br/><br/>"
				+ "It ain't much, but it's honest work :)<br/><br/>", SwingConstants.CENTER);
		
		nameLabel.setFont(new Font("David", Font.PLAIN, 17));
		JOptionPane.showMessageDialog(null, nameLabel, "Hello User!", JOptionPane.PLAIN_MESSAGE);

	}

}
