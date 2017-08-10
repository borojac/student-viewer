package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.command.Command;
import org.unibl.etf.ps.studentviewer.command.DodajNapomenuTestCommand;
import org.unibl.etf.ps.studentviewer.command.IzmjenaNazivaTestaCommand;
import org.unibl.etf.ps.studentviewer.command.IzmjenaDatumaTestCommand;
import org.unibl.etf.ps.studentviewer.gui.StudentListModel;
import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.gui.controler.TestController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import datechooser.beans.DateChooserDialog;
import datechooser.events.CommitEvent;
import datechooser.events.CommitListener;
import datechooser.beans.DateChooserCombo;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestForm extends JFrame {

	private JPanel contentPane;
	private JTextField nazivTextField;
	private JTextArea napomenaTextArea;
	private JTable studentiTable;
	private JScrollPane studentiScrollPane;
	private JButton btnSacuvaj;
	private JTextField searchTextField;
	private JButton btnPrint;
	private JButton btnPretrazi;
	private JButton btnEksport;
	private JButton btnUkloni;
	private JButton btnDodaj;

	private TestDTO test = new TestDTO();
	private TestController controller = new TestController();
	private DateChooserCombo dateChooserCombo;
	
	public TestForm() {
		setResizable(false);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				TestController.focusLostAction(ke);
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 10, 540, 700);
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
		nazivTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.focusLostAction(e);
			}
		});
		nazivTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				IzmjenaNazivaTestaCommand command = new IzmjenaNazivaTestaCommand(test, nazivTextField);
				controller.executeCommand(command);
			}
		});
		nazivTextField.setBounds(144, 12, 271, 20);
		contentPane.add(nazivTextField);
		nazivTextField.setColumns(10);
		
		JScrollPane napomenaScrollPane = new JScrollPane();
		napomenaScrollPane.setBounds(144, 103, 271, 100);
		contentPane.add(napomenaScrollPane);

		napomenaTextArea = new JTextArea();
		napomenaTextArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Command command = new DodajNapomenuTestCommand(test, napomenaTextArea);
				controller.executeCommand(command);
			}
		});
		napomenaTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.focusLostAction(e);
			}
		});
		napomenaScrollPane.setViewportView(napomenaTextArea);
		napomenaTextArea.setRows(5);
		
		studentiScrollPane = new JScrollPane();
		studentiScrollPane.setBounds(10, 420, 504, 196);
		contentPane.add(studentiScrollPane);
		
		///////////////////////////////////////////////////////////////////
		List<StudentNaTestuDTO> studenti = new ArrayList<>();
		studenti.add(new StudentNaTestuDTO(1, "1145/14", "Nemanja", "Stokuca", 65, "Glup ko tocak"));
		StudentTableModel model = new StudentTableModel(studenti);
		// TODO - popuniti tabelu
		studentiTable = new JTable(model);
		studentiScrollPane.setViewportView(studentiTable);
		
		btnSacuvaj = new JButton("Sa\u010Duvaj");
		btnSacuvaj.setBounds(434, 627, 80, 23);
		contentPane.add(btnSacuvaj);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(10, 389, 405, 20);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		btnPretrazi = new JButton("Pretra\u017Ei");
		btnPretrazi.setBounds(424, 388, 90, 23);
		contentPane.add(btnPretrazi);
		
		btnPrint = new JButton("Print");
		btnPrint.setBounds(10, 627, 80, 23);
		contentPane.add(btnPrint);
		
		btnEksport = new JButton("Eksport");
		btnEksport.setBounds(100, 627, 80, 23);
		contentPane.add(btnEksport);
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JDialog dodajStudenteDialog = new TestDodajStudenteDialog();
				dodajStudenteDialog.setVisible(true);
			}
		});
		btnDodaj.setBounds(190, 627, 80, 23);
		contentPane.add(btnDodaj);
		
		btnUkloni = new JButton("Ukloni");
		btnUkloni.setBounds(280, 627, 80, 23);
		contentPane.add(btnUkloni);
		
		dateChooserCombo = new DateChooserCombo();
		dateChooserCombo.setBounds(144, 55, 271, 20);
		dateChooserCombo.addCommitListener(new CommitListener() {
			
			@Override
			public void onCommit(CommitEvent arg0) {
				Command command = new IzmjenaDatumaTestCommand(test, dateChooserCombo);
				controller.executeCommand(command);
				
			}
		});
		dateChooserCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.focusLostAction(e);
			}
		});
		contentPane.add(dateChooserCombo);
	}
}
