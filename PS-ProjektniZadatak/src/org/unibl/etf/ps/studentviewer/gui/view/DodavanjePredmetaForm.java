package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.controller.AccountFormController;

public class DodavanjePredmetaForm extends JFrame {

	private JPanel contentPane;
	
	private JComboBox<String> predmetiCB;
	private JButton posaljiZahtjevBtn;

	/**
	 * Create the frame.
	 */
	public DodavanjePredmetaForm() {
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AccountFormController.resetDodavanjePredmetaFormOpened();
			}
		});
		
		setTitle("Dodavanje predmeta");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 600, 280);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(215, 0, 170, 120);
		contentPane.add(headerPictureLabel);

		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel whiteCorrectionLabel = new JLabel("");
		whiteCorrectionLabel.setOpaque(true);
		whiteCorrectionLabel.setBackground(Color.WHITE);
		whiteCorrectionLabel.setBounds(0, 0, 215, 120);
		contentPane.add(whiteCorrectionLabel);

		JLabel whiteCorrectionLabel2 = new JLabel("");
		whiteCorrectionLabel2.setBackground(Color.WHITE);
		whiteCorrectionLabel2.setOpaque(true);
		whiteCorrectionLabel2.setBounds(385, 0, 215, 120);
		contentPane.add(whiteCorrectionLabel2);
		
		predmetiCB = new JComboBox<>();
		predmetiCB.setBounds(20, 165, 380, 35);
		contentPane.add(predmetiCB);
		
		posaljiZahtjevBtn = new JButton("Posalji zahtjev");
		posaljiZahtjevBtn.setBounds(420, 165, 150, 35);
		contentPane.add(posaljiZahtjevBtn);
	}

}
