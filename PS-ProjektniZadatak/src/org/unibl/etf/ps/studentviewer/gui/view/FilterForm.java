package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.gui.controler.FilterFormController;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormController;
import org.unibl.etf.ps.studentviewer.utility.Filter;

public class FilterForm extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox;
	private ArrayList<Object> params = new ArrayList<Object>();
	
	JPanel panel = null;
	
	private JTextArea textArea = null;
	
	private JButton btnIspit = null;
	private JButton btnKolokvijum = null;
	private JButton btnPrakticniIspit = null;
	private JButton btnFilter = null;	
	
	private MainFormController mainFormControler = null;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public FilterForm(MainFormController mainFormControler) {
		this.mainFormControler = mainFormControler;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 358, 345);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 139));
		panel.setBounds(10, 11, 152, 285);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		initButtons();
		
		JCheckBox chckbxIdeNaElektrijadu = new JCheckBox("Ide na elektrijadu");
		chckbxIdeNaElektrijadu.setFont(new Font("Century Gothic", Font.BOLD, 12));
		chckbxIdeNaElektrijadu.setForeground(new Color(255, 255, 255));
		chckbxIdeNaElektrijadu.setBackground(new Color(0, 0, 139));
		chckbxIdeNaElektrijadu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (chckbxIdeNaElektrijadu.isSelected()) {
					params.add(Filter.IDE_NA_ELEKTRIJADU);
				}else {
					params.remove(Filter.IDE_NA_ELEKTRIJADU);
				}
			}
		});
		panel.add(chckbxIdeNaElektrijadu);
		
		JCheckBox chckbxNeIdeNa = new JCheckBox("Ne ide na elektrijadu");
		chckbxNeIdeNa.setFont(new Font("Century Gothic", Font.BOLD, 12));
		chckbxNeIdeNa.setForeground(new Color(255, 255, 255));
		chckbxNeIdeNa.setBackground(new Color(0, 0, 139));
		chckbxNeIdeNa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (chckbxNeIdeNa.isSelected())
					params.add(Filter.IDE_NA_ELEKTRIJADU);
				else
					params.remove(Filter.NE_IDE_NA_ELEKTRIJADU);
			}
		});
		panel.add(chckbxNeIdeNa);
		
		JCheckBox chckbxPozitivanKomentar = new JCheckBox("Pozitivan komentar");
		chckbxPozitivanKomentar.setFont(new Font("Century Gothic", Font.BOLD, 12));
		chckbxPozitivanKomentar.setForeground(new Color(255, 255, 255));
		chckbxPozitivanKomentar.setBackground(new Color(0, 0, 139));
		chckbxPozitivanKomentar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chckbxPozitivanKomentar.isSelected())
					params.add(Filter.POZITIVAN_KOMENTAR);
				else
					params.add(Filter.NEGATIVAN_KOMENTAR);
			}
		});
		panel.add(chckbxPozitivanKomentar);
		
		JCheckBox chckbxNegativanKomentar = new JCheckBox("Negativan komentar");
		chckbxNegativanKomentar.setFont(new Font("Century Gothic", Font.BOLD, 12));
		chckbxNegativanKomentar.setForeground(new Color(255, 255, 255));
		chckbxNegativanKomentar.setBackground(new Color(0, 0, 139));
		panel.add(chckbxNegativanKomentar);
		
		textArea = new JTextArea();
		textArea.setBounds(172, 11, 155, 213);
		contentPane.add(textArea);

		initComboBoxes();
	}

	private void initButtons() {
		btnKolokvijum = new JButton("Kolokvijum");
		btnKolokvijum.setPreferredSize(new Dimension(130,50));;
		panel.add(btnKolokvijum);
		
		btnIspit = new JButton("Ispit");
		btnIspit.setPreferredSize(new Dimension(130,50));;
		panel.add(btnIspit);
		
		btnPrakticniIspit = new JButton("Prakticni ispit");
		btnPrakticniIspit.setPreferredSize(new Dimension(130,50));;
		panel.add(btnPrakticniIspit);
		
		btnFilter = new JButton("Filter");
		btnFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new FilterFormController(mainFormControler).filter(FilterForm.this);
			}
		});
		btnFilter.setBounds(172, 235, 155, 61);
		contentPane.add(btnFilter);
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
