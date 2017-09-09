package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.control.MainFormController;
import org.unibl.etf.ps.studentviewer.gui.control.SortFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.Sort;

public class SortForm extends JFrame {

	private JPanel contentPane;

	private JCheckBox imeCheckBox = null;
	private JCheckBox prezimeCheckBox = null;
	private JCheckBox brojIndeksaCheckBox = null;
	private JCheckBox komentarCheckBox = null;
	private JCheckBox elektrijadaCheckBox = null;
	private JCheckBox ispitCheckBox = null;
	private JCheckBox kolokvijumCheckBox = null;

	private JTextArea textArea = null;
	private MainFormController mainFormControler = null;

	private JPanel panel = null;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public SortForm(MainFormController mainFormControler) {
		
		addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
				   mainFormControler.resetSortFormOpened();
			   }
			  });
		
		this.mainFormControler = mainFormControler;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 318, 465);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 139));
		panel.setBounds(10, 159, 121, 205);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(414, 111, -143, 276);
		contentPane.add(panel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(141, 159, 151, 205);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JLabel label = new JLabel("");
		label.setBounds(66, 0, 170, 120);
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
		label_1.setBounds(0, 0, 66, 120);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setBackground(Color.WHITE);
		label_2.setOpaque(true);
		label_2.setBounds(236, 0, 66, 120);
		contentPane.add(label_2);
		
		JButton sortButton = new JButton("Sort");
		sortButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new SortFormController(mainFormControler, SortForm.this);
				   mainFormControler.resetSortFormOpened();
				   SortForm.this.dispose();
			}
		});
		sortButton.setBounds(104, 387, 89, 29);
		contentPane.add(sortButton);

		initCheckBoxes();
		initCheckBoxesListeners();
	}

	public boolean getStateOfCheckBox(String TYPE) {
		if (Sort.IME.equals(TYPE)) {
			return imeCheckBox.isSelected();
		}else if (Sort.PREZIME.equals(TYPE)) {
			return prezimeCheckBox.isSelected();
		}else if (Sort.BROJ_INDEKSA.equals(TYPE)) {
			return brojIndeksaCheckBox.isSelected();
		}else if (Sort.ELEKTRIJADA.equals(TYPE)) {
			return elektrijadaCheckBox.isSelected();
		}
		return false;
	}
	
	private void initCheckBoxesListeners() {
		
		imeCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SortFormController().addToSortParams(Sort.IME, SortForm.this);
			}
		});

		prezimeCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SortFormController().addToSortParams(Sort.PREZIME, SortForm.this);
			}
		});

		brojIndeksaCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SortFormController().addToSortParams(Sort.BROJ_INDEKSA, SortForm.this);
			}
		});

		komentarCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SortFormController().addToSortParams(Sort.KOMENTAR, SortForm.this);
			}
		});

		elektrijadaCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SortFormController().addToSortParams(Sort.ELEKTRIJADA, SortForm.this);
			}
		});

	}

	public String getSortParams() {
		return textArea.getText();
	}

	public void setSortParams(String params) {
		textArea.setText(params);
	}

	private void initCheckBoxes() {

		imeCheckBox = new JCheckBox("Ime");
		
		imeCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		imeCheckBox.setBackground(new Color(0, 0, 139));
		imeCheckBox.setForeground(new Color(255, 255, 255));
		panel.add(imeCheckBox);

		prezimeCheckBox = new JCheckBox("Prezime");
		prezimeCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		prezimeCheckBox.setBackground(new Color(0, 0, 139));
		prezimeCheckBox.setForeground(new Color(255, 255, 255));
		panel.add(prezimeCheckBox);

		brojIndeksaCheckBox = new JCheckBox("Broj indeksa");
		brojIndeksaCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		brojIndeksaCheckBox.setBackground(new Color(0, 0, 139));
		brojIndeksaCheckBox.setForeground(new Color(255, 255, 255));
		panel.add(brojIndeksaCheckBox);

		komentarCheckBox = new JCheckBox("Komentar");
		komentarCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		komentarCheckBox.setBackground(new Color(0, 0, 139));
		komentarCheckBox.setForeground(new Color(255, 255, 255));
		panel.add(komentarCheckBox);

		elektrijadaCheckBox = new JCheckBox("Elektrijada");
		elektrijadaCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		elektrijadaCheckBox.setBackground(new Color(0, 0, 139));
		elektrijadaCheckBox.setForeground(new Color(255, 255, 255));
		panel.add(elektrijadaCheckBox);

		ispitCheckBox = new JCheckBox("Ispit");
		ispitCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		ispitCheckBox.setBackground(new Color(0, 0, 139));
		ispitCheckBox.setForeground(new Color(255, 255, 255));
		panel.add(ispitCheckBox);

		kolokvijumCheckBox = new JCheckBox("Kolokvijum");
		kolokvijumCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		kolokvijumCheckBox.setBackground(new Color(0, 0, 139));
		kolokvijumCheckBox.setForeground(new Color(255, 255, 255));
		panel.add(kolokvijumCheckBox);
	}
	
}
