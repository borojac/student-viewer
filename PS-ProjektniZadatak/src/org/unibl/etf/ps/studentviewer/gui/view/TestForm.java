package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import datechooser.beans.DateChooserDialog;
import datechooser.beans.DateChooserCombo;

public class TestForm extends JFrame {

	private JPanel contentPane;
	private JTextField nazivTextField;
	private JTextArea napomenaTextArea;
	private JTable studentiTable;
	private JScrollPane studentiScrollPane;
	private JButton btnSauvaj;
	private JTextField searchTextField;
	private JButton btnPrint;
	private JButton btnPretrazi;
	private JButton btnEksport;

	public TestForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNaziv = new JLabel("Naziv:");
		lblNaziv.setHorizontalAlignment(SwingConstants.CENTER);
		lblNaziv.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNaziv.setBounds(41, 11, 70, 20);
		contentPane.add(lblNaziv);
		
		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatum.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDatum.setBounds(41, 55, 70, 20);
		contentPane.add(lblDatum);
		
		JLabel lblNapomena = new JLabel("Napomena:");
		lblNapomena.setHorizontalAlignment(SwingConstants.CENTER);
		lblNapomena.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNapomena.setBounds(41, 104, 70, 20);
		contentPane.add(lblNapomena);
		
		nazivTextField = new JTextField();
		nazivTextField.setBounds(144, 12, 271, 20);
		contentPane.add(nazivTextField);
		nazivTextField.setColumns(10);

		napomenaTextArea = new JTextArea();
		napomenaTextArea.setRows(5);
		napomenaTextArea.setBounds(144, 103, 271, 100);
		contentPane.add(napomenaTextArea);
		
		studentiScrollPane = new JScrollPane();
		studentiScrollPane.setBounds(10, 319, 504, 196);
		contentPane.add(studentiScrollPane);
		
		studentiTable = new JTable();
		studentiScrollPane.setViewportView(studentiTable);
		
		btnSauvaj = new JButton("Sa\u010Duvaj");
		btnSauvaj.setBounds(425, 527, 89, 23);
		contentPane.add(btnSauvaj);
		
		DateChooserCombo dateChooserCombo = new DateChooserCombo();
		dateChooserCombo.setBounds(144, 55, 271, 20);
		contentPane.add(dateChooserCombo);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(10, 288, 405, 20);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		btnPretrazi = new JButton("Pretra\u017Ei");
		btnPretrazi.setBounds(425, 285, 89, 23);
		contentPane.add(btnPretrazi);
		
		btnPrint = new JButton("Print");
		btnPrint.setBounds(10, 220, 70, 23);
		contentPane.add(btnPrint);
		
		btnEksport = new JButton("Eksport");
		btnEksport.setBounds(10, 254, 70, 23);
		contentPane.add(btnEksport);
	}
}
