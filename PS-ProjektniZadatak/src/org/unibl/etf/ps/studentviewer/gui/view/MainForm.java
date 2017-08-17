package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.poi.ss.usermodel.Table;
import org.imgscalr.Main;
import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.MainTableModel;
import org.unibl.etf.ps.studentviewer.gui.TestoviTableModel;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormControler;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private MainFormControler mainFormControler = new MainFormControler(this);
	
	// ------- Components!!! ------- //
	ArrayList<JButton> buttons = new ArrayList<JButton>();

	private JButton searchButton = null;
	private JButton showViewBtn = null;
	private JButton sortBtn = null;
	private JButton filterBtn = null;
	private JButton addBtn = null;
	private JButton deleteBtn = null;
	private JButton changeBtn = null;
	private JButton exportBtn = null;
	private JButton accountBtn = null;
	private JButton restoreButton = null;

	private JPanel panel = null;
	private JPanel panel_1 = null;
	private JPanel buttonPanel;

	private JScrollPane scrollPane = null;

	private MainTable mainTable = null;

	private JPanel testoviPanel;
	private JTable testoviTable;
	private JScrollPane testoviScrollPane;
	private JButton btnDodaj;
	private JButton btnIzmjeni;
	private JButton btnBrisi;
	private JLabel correct2Label;
	private JTextField textField;

	// ------- EndComponents!!! ------- //

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
	 * @throws IOException Create the frame. @throws
	 */
	public MainForm() throws IOException {
		setResizable(false);
		setTitle("StudentViewer_v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 10, 1200, 640);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(UIManager.getBorder("Button.border"));
		scrollPane.setBounds(10, 219, 558, 382);
		contentPane.add(scrollPane);

		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(0, 0, 139));
		buttonPanel.setBounds(578, 219, 147, 382);
		contentPane.add(buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 12));

		BufferedImage img = ImageIO.read(new File("img\\BellTower-RGB(JPG).jpg"));
		BufferedImage correctionImage = ImageIO.read(new File("img\\whiteCorrection.png"));

		JLabel label = new JLabel("");
		label.setBounds(515, 0, 170, 120);

		img = Scalr.resize(img, Scalr.Mode.FIT_EXACT, label.getWidth(), label.getHeight(), null);
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		contentPane.add(label);

		testoviPanel = new JPanel();
		testoviPanel.setBounds(735, 401, 449, 200);
		contentPane.add(testoviPanel);
		testoviPanel.setLayout(null);

		testoviTable = new JTable();
		testoviTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (testoviTable.getSelectedRow() != -1)
					btnIzmjeni.setEnabled(true);
			}
		});
		testoviTable.setModel(new TestoviTableModel());

		testoviScrollPane = new JScrollPane();
		testoviScrollPane.setBounds(10, 11, 429, 145);
		testoviPanel.add(testoviScrollPane);
		testoviScrollPane.setViewportView(testoviTable);

		JLabel correct1Label = new JLabel("STUDENT");
		correct1Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct1Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct1Label.setOpaque(true);
		correct1Label.setForeground(new Color(0, 0, 139));
		correct1Label.setBackground(new Color(255, 255, 255));
		correct1Label.setBounds(0, 0, 515, 120);
		contentPane.add(correct1Label);

		correct2Label = new JLabel("VIEWER");
		correct2Label.setBackground(SystemColor.text);
		correct2Label.setForeground(new Color(0, 0, 139));
		correct2Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct2Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct2Label.setOpaque(true);
		correct2Label.setBounds(685, 0, 509, 120);
		contentPane.add(correct2Label);

		JLabel lblPretraga = new JLabel("Pretraga:");
		lblPretraga.setForeground(Color.WHITE);
		lblPretraga.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblPretraga.setBounds(10, 172, 78, 25);
		contentPane.add(lblPretraga);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.toString().contains("keyText=Enter"))
					mainFormControler.search(MainForm.this);
			}
		});
		textField.setForeground(new Color(0, 0, 139));
		textField.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 15));
		textField.setBounds(97, 172, 367, 25);
		contentPane.add(textField);
		textField.setColumns(10);

		initButtons();
		initButtonsListeners();
		initTable();
		setButtonsSize();
	}

	public String getSearchParams() {
		return textField.getText();
	}

	public void setSearchParams(String params) {
		textField.setText(params);
	}

	private void initButtonsListeners() {
		
		sortBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormControler.createSortForm();
			}
		});

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFormControler.search(MainForm.this);
			}
		});
		
		restoreButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormControler.restoreTable();
			}
		});
		
		showViewBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormControler.createShowForm();
			}
		});
		
	}

	private void initTable() {
		mainTable = new MainTable();
		mainTable.setFont(new Font("Century Gothic", Font.BOLD, 15));
		mainTable.setForeground(new Color(0, 0, 139));
		mainTable.setBackground(new Color(173, 216, 230));
		mainTable.setModel(new MainTableModel());
		mainTable.setStudents(StudentsForMainTable.getAllStudents());
		
		scrollPane.setViewportView(mainTable);
	}

	private void setButtonsSize() {
		for (JButton btn : buttons) {
			btn.setPreferredSize(new Dimension(135, 35));
		}
	}

	private void initButtons() {

		searchButton = new JButton("");
		searchButton.setToolTipText("Pretrazi");

		searchButton.setBounds(474, 171, 42, 26);
		try {
			BufferedImage searchImg = ImageIO.read(new File("img" + File.separator +"searchIcon.png"));
			searchImg = Scalr.resize(searchImg, Scalr.Mode.FIT_EXACT, searchButton.getWidth(),
					searchButton.getHeight(), null);
			searchButton.setIcon(new ImageIcon(searchImg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		contentPane.add(searchButton);

		/* Buttons by Boroja */
		showViewBtn = new JButton("Prikaz");
		
		buttonPanel.add(showViewBtn);
		buttons.add(showViewBtn);

		sortBtn = new JButton("Sortiraj");
		buttonPanel.add(sortBtn);
		buttons.add(sortBtn);

		filterBtn = new JButton("Filtriraj");
		buttonPanel.add(filterBtn);
		buttons.add(filterBtn);

		addBtn = new JButton("Dodaj studente");
		buttonPanel.add(addBtn);
		buttons.add(addBtn);

		deleteBtn = new JButton("Obri\u0161i studente");
		buttonPanel.add(deleteBtn);
		buttons.add(deleteBtn);

		changeBtn = new JButton("Izmijeni studente");
		buttonPanel.add(changeBtn);
		buttons.add(changeBtn);

		exportBtn = new JButton("Eksportuj");
		buttonPanel.add(exportBtn);
		buttons.add(exportBtn);

		accountBtn = new JButton("Nalog");
		buttonPanel.add(accountBtn);
		buttons.add(accountBtn);


		restoreButton = new JButton("");
		restoreButton.setToolTipText("Ucitaj podatke o svim studentima");
		restoreButton.setBounds(526, 171, 42, 26);
		try {
			BufferedImage restoreImg = ImageIO.read(new File("img" + File.separator + "restoreIcon.png"));
			restoreImg = Scalr.resize(restoreImg, Scalr.Mode.FIT_EXACT, restoreButton.getWidth(), restoreButton.getHeight(), null);
			restoreButton.setIcon(new ImageIcon(restoreImg));
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		contentPane.add(restoreButton);
		
		/* Buttons by Stokuca */
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestForm tf = new TestForm(null);
				tf.setVisible(true);
			}
		});
		btnDodaj.setBounds(10, 166, 89, 23);
		testoviPanel.add(btnDodaj);

		btnIzmjeni = new JButton("Izmjeni");
		btnIzmjeni.setEnabled(false);
		btnIzmjeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int row = testoviTable.getSelectedRow();
				TestDTO selected = ((TestoviTableModel) testoviTable.getModel()).getRowAt(row);
				TestForm tf = new TestForm(selected);
				tf.setVisible(true);
			}
		});
		btnIzmjeni.setBounds(109, 166, 89, 23);
		testoviPanel.add(btnIzmjeni);

		btnBrisi = new JButton("Bri\u0161i");
		btnBrisi.setBounds(208, 166, 89, 23);
		testoviPanel.add(btnBrisi);
		

	}

	public MainTable getMainTable() {
		return mainTable;
	}
}
