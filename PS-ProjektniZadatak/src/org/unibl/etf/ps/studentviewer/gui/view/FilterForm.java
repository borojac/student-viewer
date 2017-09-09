package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.control.MainFormController;

public class FilterForm extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox;
	private ArrayList<Object> params = new ArrayList<Object>();

	private JComboBox elektrijadaComboBox = null;
	private JComboBox komentarComboBox = null;

	JPanel panel = null;

	private JTextArea textArea = null;

	private JButton btnIspit = null;
	private JButton btnKolokvijum = null;
	private JButton btnPrakticniIspit = null;
	private JButton btnFilter = null;
	private JButton dodajButton = null;
	
	private MainFormController mainFormControler = null;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public FilterForm(MainFormController mainFormControler) {
		this.mainFormControler = mainFormControler;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setForeground(new Color(0, 0, 205));
		panel_1.setBounds(10, 130, 115, 166);
		contentPane.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblElektrijada = new JLabel("Elektrijada: ");
		lblElektrijada.setForeground(new Color(255, 255, 255));
		lblElektrijada.setBackground(new Color(0, 0, 205));
		lblElektrijada.setFont(new Font("Century Gothic", Font.BOLD, 13));
		panel_1.add(lblElektrijada);

		JLabel lblKomenta = new JLabel("Komentar: ");
		lblKomenta.setForeground(new Color(255, 255, 255));
		lblKomenta.setBackground(new Color(0, 0, 205));
		lblKomenta.setFont(new Font("Century Gothic", Font.BOLD, 13));
		panel_1.add(lblKomenta);
		
		JLabel lblProvjeraZnanja = new JLabel("Provjera znanja:");
		lblProvjeraZnanja.setForeground(new Color(255, 255, 255));
		lblProvjeraZnanja.setFont(new Font("Century Gothic", Font.BOLD, 13));
		panel_1.add(lblProvjeraZnanja);

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBounds(135, 130, 103, 166);
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		elektrijadaComboBox = new JComboBox();
		panel_2.add(elektrijadaComboBox);
		elektrijadaComboBox.setPreferredSize(new Dimension(83, 19));
		elektrijadaComboBox.addItem("");
		elektrijadaComboBox.addItem("DA");
		elektrijadaComboBox.addItem("NE");

		komentarComboBox = new JComboBox();
		panel_2.add(komentarComboBox);
		komentarComboBox.setPreferredSize(new Dimension(83, 19));
		komentarComboBox.addItem("");
		komentarComboBox.addItem("Pozitivan");
		komentarComboBox.addItem("Negativan");
		
		JComboBox provjeraZnanjaComboBox = new JComboBox();
		panel_2.add(provjeraZnanjaComboBox);
		provjeraZnanjaComboBox.setPreferredSize(new Dimension(83, 19));
		provjeraZnanjaComboBox.addItem("Kontinualna");
		provjeraZnanjaComboBox.addItem("Integralna");
		provjeraZnanjaComboBox.addItem("Usmena");
		
		dodajButton = new JButton("Dodaj");
		dodajButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_2.add(dodajButton);
		dodajButton.setPreferredSize(new Dimension(82, 25));
		
		
		JLabel whiteCorrection1 = new JLabel("");
		whiteCorrection1.setOpaque(true);
		whiteCorrection1.setBackground(new Color(255, 255, 255));
		whiteCorrection1.setBounds(0, 0, 90, 120);
		contentPane.add(whiteCorrection1);

		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(90, 0, 170, 120);
		contentPane.add(headerPictureLabel);

		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		JLabel whiteCorrection2 = new JLabel("");
		whiteCorrection2.setBackground(new Color(255, 255, 255));
		whiteCorrection2.setOpaque(true);
		whiteCorrection2.setForeground(new Color(255, 255, 255));
		whiteCorrection2.setBounds(260, 0, 90, 120);
		contentPane.add(whiteCorrection2);

		initButtons();

		initComboBoxes();
	}

	private void initButtons() {
	}

	public ArrayList<Object> getFilterParams() {
		return params;
	}

	private void initComboBoxes() {
		initExamTypeComboBox();
		initExamStatusComboBox();
	}

	private void initExamStatusComboBox() {
	}

	private void initExamTypeComboBox() {
	}
}
