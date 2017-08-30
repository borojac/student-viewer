package org.unibl.etf.ps.studentviewer.gui.addstudentview;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.addstudentcontroller.ImportController;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormController;

public class MultipleAdditionForm extends JFrame {

	private JPanel contentPane;
	private MainFormController mainFormController = null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//MultipleAdditionForm frame = new MultipleAdditionForm();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MultipleAdditionForm(MainFormController mainFormController) {
		// TODO Auto-generated constructor stub
	
		addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
				   MainFormController.resetAddFormOpened();
				   MainFormController.resetChooseAddTypeFormOpened();
			   }
			  });
		
		this.mainFormController = mainFormController;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 263, 204);
		contentPane =new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setBounds(44, 0, 171, 120);
		try {
			BufferedImage headerImage = ImageIO.read(new File("img\\BellTower-RGB(JPG).jpg"));
			headerImage = Scalr.resize(headerImage, Scalr.Mode.FIT_EXACT, label.getWidth(), label.getHeight(), null);
			label.setIcon(new ImageIcon(headerImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setBackground(Color.WHITE);
		label_1.setOpaque(true);
		label_1.setBounds(0, 0, 52, 120);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setBackground(Color.WHITE);
		label_2.setOpaque(true);
		label_2.setBounds(200, 0, 66, 120);
		contentPane.add(label_2);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(0,0,139));
		panel2.setBounds(68, 129, 118, 33);
		contentPane.add(panel2);
		
		JButton btnPDF = new JButton("Uvezi fajl");
		btnPDF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ImportController importController = new ImportController(mainFormController);
			}
		});
		panel2.add(btnPDF);
	}
}
