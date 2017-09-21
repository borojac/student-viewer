package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.FilterFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import javax.swing.JCheckBox;

public class FilterForm extends JFrame {

	private JPanel contentPane;

	private JComboBox elektrijadaComboBox = null;
	private JComboBox komentarComboBox = null;
	private JCheckBox imaFormulisanuOcjenuCheckBox = null;
	private JCheckBox polozenPredmetCheckBox;
	private JCheckBox nemaFormulisanuOcjenuCheckBox = null;
	private JCheckBox nepolozenPredmetCheckBox;
	
	JPanel panel = null;


	JList list = null;
	
	private MainFormController mainFormController = null;
	
	public void addToControlParams(String s) {
		controlParams.add(s);
	}

	ArrayList<String> controlParams = new ArrayList<String>();
	
	public ArrayList<String> getControlParams(){
		return controlParams;
	}
	
	HashMap<String, HashMap<String, String>> testoviMap = new HashMap<String, HashMap<String, String>>();
	
	
	public HashMap<String, HashMap<String, String>> getTestoviMap() {
		return testoviMap;
	}

	public void setTestoviMap(HashMap<String, HashMap<String, String>> testoviMap) {
		this.testoviMap = testoviMap;
	}

	public Set<String> getTestovi() {
		return testoviMap.keySet();
	}
	
	public void addToTestoviMap(String test, HashMap<String, String> map) {
		testoviMap.put(test, map);
		DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
		model.addElement(test);
	}
	
	public boolean isPolozen() {
		return polozenPredmetCheckBox.isSelected();
	}

	public boolean isNepolozen() {
		return nepolozenPredmetCheckBox.isSelected();
	}
	
	public String getElektrijada() {
		return (String) elektrijadaComboBox.getSelectedItem();
	}
	
	public String getKomentar() {
		return (String) komentarComboBox.getSelectedItem();
	}
	
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public FilterForm(MainFormController mainFormControler) {
		setTitle("Filtriranje");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mainFormController.resetFilterFormOpened();
			}
		});
		setResizable(false);
		this.mainFormController = mainFormControler;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 418);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setForeground(new Color(0, 0, 205));
		panel_1.setBounds(10, 130, 119, 57);
		contentPane.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblElektrijada = new JLabel("Elektrijada:");
		lblElektrijada.setForeground(new Color(255, 255, 255));
		lblElektrijada.setBackground(new Color(0, 0, 205));
		lblElektrijada.setFont(new Font("Century Gothic", Font.BOLD, 13));
		panel_1.add(lblElektrijada);

		JLabel lblKomenta = new JLabel("Komentar:");
		lblKomenta.setForeground(new Color(255, 255, 255));
		lblKomenta.setBackground(new Color(0, 0, 205));
		lblKomenta.setFont(new Font("Century Gothic", Font.BOLD, 13));
		panel_1.add(lblKomenta);

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBounds(139, 130, 205, 57);
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		elektrijadaComboBox = new JComboBox();
		panel_2.add(elektrijadaComboBox);
		elektrijadaComboBox.setPreferredSize(new Dimension(190, 19));
		elektrijadaComboBox.addItem("");
		elektrijadaComboBox.addItem("Studenti koji idu na elektijadu");
		elektrijadaComboBox.addItem("Studenti koji ne idu na elektrijadu");

		komentarComboBox = new JComboBox();
		panel_2.add(komentarComboBox);
		komentarComboBox.setPreferredSize(new Dimension(190, 19));
		komentarComboBox.addItem("");
		komentarComboBox.addItem("Studenti koji imaju komentar");
		komentarComboBox.addItem("Studenti koji nemaju komentar");
		
		
		JLabel whiteCorrection1 = new JLabel("");
		whiteCorrection1.setOpaque(true);
		whiteCorrection1.setBackground(new Color(255, 255, 255));
		whiteCorrection1.setBounds(0, 0, 92, 120);
		contentPane.add(whiteCorrection1);

		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(92, 0, 170, 120);
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
		whiteCorrection2.setBounds(262, 0, 92, 120);
		contentPane.add(whiteCorrection2);
		
		JButton btnNewButton = new JButton("Dodaj test");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FilterFormController(mainFormControler, FilterForm.this).createChooseForm();
				FilterForm.this.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 13));
		btnNewButton.setBounds(139, 253, 119, 31);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 253, 119, 84);
		contentPane.add(scrollPane);
		
		list = new JList(new DefaultListModel());
		list.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPane.setViewportView(list);
		
		
		JButton btnNewButton_1 = new JButton("Obrisi test");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> model = (DefaultListModel) list.getModel();
				List<String> selectedExams = list.getSelectedValuesList();
				for (String s : selectedExams) {
					testoviMap.remove(s);
					model.removeElement(s);
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(139, 306, 119, 31);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Filtriraj");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FilterFormController ffc = new FilterFormController(mainFormControler, FilterForm.this);
				boolean control = ffc.setParameters();
				if (!control)
					return;
				ffc.filter();
				FilterForm.this.dispose();
				mainFormControler.resetFilterFormOpened();
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.setBounds(119, 348, 119, 31);
		contentPane.add(btnNewButton_2);
		
		polozenPredmetCheckBox = new JCheckBox("polozen predmet");
		polozenPredmetCheckBox.setBackground(new Color(0, 0, 139));
		polozenPredmetCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		polozenPredmetCheckBox.setForeground(new Color(255, 255, 255));
		polozenPredmetCheckBox.setBounds(10, 194, 228, 23);
		contentPane.add(polozenPredmetCheckBox);
		
		nepolozenPredmetCheckBox = new JCheckBox("nepolozen predmet");
		nepolozenPredmetCheckBox.setBackground(new Color(0, 0, 139));
		nepolozenPredmetCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		nepolozenPredmetCheckBox.setForeground(new Color(255, 255, 255));
		nepolozenPredmetCheckBox.setBounds(10, 220, 228, 23);
		contentPane.add(nepolozenPredmetCheckBox);
	}
}
