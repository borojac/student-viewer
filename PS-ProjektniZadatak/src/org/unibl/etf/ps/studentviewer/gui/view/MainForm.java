package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.jws.WebParam.Mode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.MainTableModel;
import org.unibl.etf.ps.studentviewer.gui.TestoviTableModel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class MainForm extends JFrame {

	private JPanel contentPane;

	// ------- Components!!! ------- //
	ArrayList<JButton> buttons = new ArrayList<JButton>();

	private JButton showViewBtn = null;
	private JButton sortBtn = null;
	private JButton filterBtn = null;
	private JButton addBtn = null;
	private JButton deleteBtn = null;
	private JButton changeBtn = null;
	private JButton exportBtn = null;
	private JButton accountBtn = null;

	private JPanel panel = null;
	private JPanel panel_1 = null;

	private JScrollPane scrollPane = null;

	private MainTable mainTable = null;
	

	private JPanel testoviPanel;
	private JTable testoviTable;
	private JScrollPane testoviScrollPane;
	private JButton btnDodaj;
	private JButton btnIzmjeni;
	private JButton btnBrii;
	private JLabel correct2Label;

	// ------- EndComponents!!! ------- //

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @throws IOException 
	 * Create the frame.
	 * @throws  
	 */
	public MainForm() throws IOException   {
		setResizable(false);
		setTitle("StudentViewer_v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 589);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 148, 558, 382);
		contentPane.add(scrollPane);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 139));
		panel_1.setBounds(578, 148, 147, 382);
		contentPane.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 12));
		
		BufferedImage img = ImageIO.read(new File("img\\BellTower-RGB(JPG).jpg"));
		BufferedImage correctionImage = ImageIO.read(new File("img\\whiteCorrection.png"));
		
		JLabel label = new JLabel("");
		label.setBounds(515, 0, 170, 120);
		
		img = Scalr.resize(img, Scalr.Mode.FIT_EXACT ,label.getWidth(), label.getHeight(), null);
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		contentPane.add(label);


		testoviPanel = new JPanel();
		testoviPanel.setBounds(735, 330, 449, 200);
		contentPane.add(testoviPanel);
		testoviPanel.setLayout(null);

		testoviTable = new JTable();
		testoviTable.setModel(new TestoviTableModel());
		
		testoviScrollPane = new JScrollPane();
		testoviScrollPane.setBounds(10, 11, 429, 145);
		testoviPanel.add(testoviScrollPane);
		testoviScrollPane.setViewportView(testoviTable);
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestForm tf = new TestForm();
				tf.setVisible(true);
			}
		});
		btnDodaj.setBounds(10, 166, 89, 23);
		testoviPanel.add(btnDodaj);
		
		btnIzmjeni = new JButton("Izmjeni");
		btnIzmjeni.setBounds(109, 166, 89, 23);
		testoviPanel.add(btnIzmjeni);
		
		btnBrii = new JButton("Bri\u0161i");
		btnBrii.setBounds(208, 166, 89, 23);
		testoviPanel.add(btnBrii);
		
		JLabel correct1Label = new JLabel("STUDENT");
		correct1Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct1Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct1Label.setOpaque(true);
		correct1Label.setForeground(new Color(0, 0, 139));
		correct1Label.setBackground(new Color(255, 255, 255));
		correct1Label.setBounds(0, 0, 515, 120);
		contentPane.add(correct1Label);
//		correct1Label.setIcon(new ImageIcon(correctionImage));

		
		correct2Label = new JLabel("VIEWER");
		correct2Label.setBackground(SystemColor.text);
		correct2Label.setForeground(new Color(0, 0, 139));
		correct2Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct2Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct2Label.setOpaque(true);
		correct2Label.setBounds(685, 0, 509, 120);
		contentPane.add(correct2Label);
		
		
		initButtons();
		initTable();
		setButtonsSize();

	}

	private void initTable() {
		mainTable = new MainTable();
		mainTable.setFont(new Font("Century Gothic", Font.BOLD, 15));
		mainTable.setForeground(new Color(0, 0, 139));
		mainTable.setBackground(new Color(173, 216, 230));
		scrollPane.setViewportView(mainTable);
		mainTable.setModel(new MainTableModel());
	}

	private void setButtonsSize() {
		for (JButton btn : buttons) {
			btn.setPreferredSize(new Dimension(135, 35));
		}
	}

	private void initButtons() {
		showViewBtn = new JButton("Prikaz");
		panel_1.add(showViewBtn);
		buttons.add(showViewBtn);

		sortBtn = new JButton("Sortiraj");
		panel_1.add(sortBtn);
		buttons.add(sortBtn);

		filterBtn = new JButton("Filtriraj");
		panel_1.add(filterBtn);
		buttons.add(filterBtn);

		addBtn = new JButton("Dodaj studente");
		panel_1.add(addBtn);
		buttons.add(addBtn);

		deleteBtn = new JButton("Obri\u0161i studente");
		panel_1.add(deleteBtn);
		buttons.add(deleteBtn);

		changeBtn = new JButton("Izmijeni studente");
		panel_1.add(changeBtn);
		buttons.add(changeBtn);

		exportBtn = new JButton("Eksportuj");
		panel_1.add(exportBtn);
		buttons.add(exportBtn);

		accountBtn = new JButton("Nalog");
		panel_1.add(accountBtn);
		buttons.add(accountBtn);

	}
}
