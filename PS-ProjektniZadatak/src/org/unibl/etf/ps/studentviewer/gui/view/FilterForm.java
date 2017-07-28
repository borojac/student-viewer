package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.utility.Filter;

import javax.swing.JLabel;
import javax.swing.JComboBox;

public class FilterForm extends JFrame {

	private JPanel contentPane;

	private JComboBox examTypeComboBox ;
	private JComboBox examStatusComboBox;
	private JLabel lblStatusIspita;
	private JComboBox comboBox;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterForm frame = new FilterForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FilterForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTipIspita = new JLabel("Tip ispita:");
		lblTipIspita.setBounds(10, 54, 86, 14);
		contentPane.add(lblTipIspita);
		
		lblStatusIspita = new JLabel("Status ispita:");
		lblStatusIspita.setBounds(10, 85, 86, 14);
		contentPane.add(lblStatusIspita);
		
		initComboBoxes();
		
		
	}
	
	private void initComboBoxes() {
		examTypeComboBox = new JComboBox();
		examTypeComboBox.setBounds(106, 51, 86, 20);
		contentPane.add(examTypeComboBox);
		initExamTypeComboBox();
		
		examStatusComboBox = new JComboBox();
		examStatusComboBox.setBounds(106, 82, 89, 20);
		contentPane.add(examStatusComboBox);
		initExamStatusComboBox();
	}

	private void initExamStatusComboBox() {
		examStatusComboBox.addItem("");
		examStatusComboBox.addItem("Polozen");
		examStatusComboBox.addItem("Nepolozen");
	}

	private void initExamTypeComboBox() {
		examTypeComboBox.addItem("");
		examTypeComboBox.addItem("Integralni ispit");
		examTypeComboBox.addItem("Kolokvijum");
		examTypeComboBox.addItem("Prakticni ispit");
	}
}
