package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.FilterChooseFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.FilterFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.Filter;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FilterChooseForm extends JFrame {

	private JPanel contentPane;
	FilterFormController ffc = null;
	FilterForm filterForm;
	
	JComboBox<String> testoviComboBox = null;
	JCheckBox polozenCheckBox = null;
	JCheckBox nepolozenCheckBox = null;
	JCheckBox izasaoCheckBox = null;
	JCheckBox nijeIzasaoCheckBox = null;
	JComboBox<String> donjaGranicaComboBox = null;
	JComboBox<String> gornjaGranicaComboBox = null;
	HashMap<String, String> map = null;
	
	public void putToMap(String s1, String s2) {
		map.put(s1, s2);
	}
	
	public String getTest() {
		return (String) testoviComboBox.getSelectedItem();
	}
	
	public String getGornjaGranica() {
		return (String) gornjaGranicaComboBox.getSelectedItem();
	}
	
	public String getDonjaGranica() {
		return (String) donjaGranicaComboBox.getSelectedItem();
	}
	
	public boolean isNijeIzasao() {
		return nijeIzasaoCheckBox.isSelected();
	}
	
	public boolean isIzasao() {
		return izasaoCheckBox.isSelected();
	}
	
	public boolean isPolozen() {
		return polozenCheckBox.isSelected();
	}

	public boolean isNepolozen() {
		return nepolozenCheckBox.isSelected();
	}
	
	/**
	 * Create the frame.
	 * @param filterForm 
	 * @param filterFormController 
	 */
	public FilterChooseForm(FilterForm filterForm, FilterFormController filterFormController) {
		
		this.ffc = filterFormController;
		this.filterForm = filterForm;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 244, 385);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		testoviComboBox = new JComboBox();
		testoviComboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		testoviComboBox.setBounds(70, 169, 103, 20);
		testoviComboBox.addItem("");
		if (filterForm.getTestovi().size() == StudentsForMainTable.getAllIspiti().length) {
			filterForm.setVisible(true);
			this.dispose();
		}
			
		for (String s : StudentsForMainTable.getAllIspiti())
			if (!filterForm.getTestovi().contains(s))
				testoviComboBox.addItem(s);
		contentPane.add(testoviComboBox);
		
		JLabel lblIspit = new JLabel("Ispit:");
		lblIspit.setForeground(new Color(255, 255, 255));
		lblIspit.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblIspit.setBounds(10, 167, 50, 20);
		contentPane.add(lblIspit);
		
		polozenCheckBox = new JCheckBox("polozen");
		polozenCheckBox.setBackground(new Color(0, 0, 139));
		polozenCheckBox.setForeground(new Color(255, 255, 255));
		polozenCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		polozenCheckBox.setBounds(10, 196, 97, 23);
		contentPane.add(polozenCheckBox);
		
		nepolozenCheckBox = new JCheckBox("nepolozen");
		nepolozenCheckBox.setForeground(new Color(255, 255, 255));
		nepolozenCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		nepolozenCheckBox.setBackground(new Color(0, 0, 139));
		nepolozenCheckBox.setBounds(128, 196, 97, 23);
		contentPane.add(nepolozenCheckBox);
		
		izasaoCheckBox = new JCheckBox("izasao");
		izasaoCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		izasaoCheckBox.setBackground(new Color(0, 0, 139));
		izasaoCheckBox.setForeground(new Color(255, 255, 255));
		izasaoCheckBox.setBounds(10, 222, 97, 23);
		contentPane.add(izasaoCheckBox);
		
		nijeIzasaoCheckBox = new JCheckBox("nije izasao");
		nijeIzasaoCheckBox.setBackground(new Color(0, 0, 139));
		nijeIzasaoCheckBox.setForeground(new Color(255, 255, 255));
		nijeIzasaoCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		nijeIzasaoCheckBox.setBounds(128, 222, 97, 23);
		contentPane.add(nijeIzasaoCheckBox);
		
		JLabel lblDonjaGranica = new JLabel("Donja granica");
		lblDonjaGranica.setForeground(new Color(255, 255, 255));
		lblDonjaGranica.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDonjaGranica.setBounds(10, 252, 97, 20);
		contentPane.add(lblDonjaGranica);
		
		donjaGranicaComboBox = new JComboBox();
		donjaGranicaComboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		donjaGranicaComboBox.setBounds(128, 254, 51, 20);
		for (int i = 0; i <= 100; i ++)
			donjaGranicaComboBox.addItem(new Integer(i).toString());
		contentPane.add(donjaGranicaComboBox);
		
		JLabel lblGornjaGranica = new JLabel("Gornja granica");
		lblGornjaGranica.setForeground(new Color(255, 255, 255));
		lblGornjaGranica.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblGornjaGranica.setBounds(10, 283, 108, 20);
		contentPane.add(lblGornjaGranica);
		
		gornjaGranicaComboBox = new JComboBox();
		gornjaGranicaComboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		gornjaGranicaComboBox.setBounds(128, 285, 51, 20);
		for (int i = 100; i >= 0; i --)
			gornjaGranicaComboBox.addItem(new Integer(i).toString());
		contentPane.add(gornjaGranicaComboBox);
		
		JLabel lblNewLabel = new JLabel("%");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblNewLabel.setBounds(189, 254, 29, 17);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("%");
		label.setForeground(new Color(255, 255, 255));
		label.setFont(new Font("Century Gothic", Font.BOLD, 13));
		label.setBounds(189, 285, 29, 17);
		contentPane.add(label);
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(34, 0, 170, 120);
		contentPane.add(headerPictureLabel);
		
		JLabel label_2 = new JLabel("");
		label_2.setBackground(new Color(255, 255, 255));
		label_2.setOpaque(true);
		label_2.setBounds(0, 0, 34, 120);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setBackground(new Color(255, 255, 255));
		label_3.setOpaque(true);
		label_3.setBounds(204, 0, 34, 120);
		contentPane.add(label_3);
		
		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
			
			JButton btnNewButton = new JButton("Dodaj ispit");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					map = new HashMap<String, String>();
					FilterChooseFormController fcfc = new FilterChooseFormController(FilterChooseForm.this);
					boolean control = fcfc.setAndCheckParameters();
					if (!control)
						return;
					filterForm.setVisible(true);
					filterForm.addToTestoviMap(map.get(Filter.TEST), map);
					FilterChooseForm.this.dispose();
				}
			});
			btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 13));
			btnNewButton.setBounds(65, 323, 108, 23);
			contentPane.add(btnNewButton);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				filterForm.setVisible(true);
			}
		});
	}
}
