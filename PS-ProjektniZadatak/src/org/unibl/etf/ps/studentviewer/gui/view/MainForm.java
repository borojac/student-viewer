package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.MainTableModel;
import java.awt.Color;
import java.awt.Font;

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
		setBounds(100, 100, 1000, 589);
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
		
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 994, 119);
		img = Scalr.resize(img, label.getWidth(), label.getHeight(), null);
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		contentPane.add(label);

		initButtons();
		initTable();
		setButtonsSize();

	}

	private void initTable() {
		mainTable = new MainTable();
		mainTable.setFont(new Font("Century Gothic", Font.BOLD, 15));
		mainTable.setForeground(new Color(0, 0, 139));
		mainTable.setBackground(new Color(176, 224, 230));
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
