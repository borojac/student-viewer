package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.MainTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentDAO;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class AdminStudentForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton dodajStudenteBtn;
	private JButton obrisiStudenteBtn;
	private JButton izmijeniBtn;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminStudentForm frame = new AdminStudentForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public AdminStudentForm(AdministratorFormController administratorFormController) {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				administratorFormController.resetStudentFormOpened();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 319);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTable();
		
		dodajStudenteBtn = new JButton("Dodaj");
		dodajStudenteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				administratorFormController.createChooseAddTypeForm();
			}
		});
		dodajStudenteBtn.setBounds(10, 223, 122, 46);
		contentPane.add(dodajStudenteBtn);
		
		obrisiStudenteBtn = new JButton("Obrisi");
		obrisiStudenteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = table.getSelectedRows();
				String[] listaIndeksa = new String[selectedRows.length];
				int i = 0;
				for(int rb : selectedRows) {
					listaIndeksa[i++] = ((String)table.getValueAt(rb, 0)).trim();
				}
				administratorFormController.deleteStudentsControler(listaIndeksa);
			}
		});
		obrisiStudenteBtn.setBounds(302, 223, 122, 46);
		contentPane.add(obrisiStudenteBtn);
		
		izmijeniBtn = new JButton("Izmijeni");
		izmijeniBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRow = table.getSelectedRows();
				administratorFormController.createChangeForm(selectedRow, table);

			}
		});
		izmijeniBtn.setBounds(159, 223, 122, 46);
		contentPane.add(izmijeniBtn);
	}
	
	public void setTable() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(0, 0, 434, 212);
		contentPane.add(scrollPane);
		MySQLStudentDAO dao = new MySQLStudentDAO();
		String [] header = {"Indeks", "Ime", "Prezime"};
		String [][] data = dao.getDataOfAllStudentsFromStudentDatabaseTable();
		table = new JTable(data, header);
		table.setFont(new Font("Century Gothic", Font.BOLD, 15));
		table.setForeground(new Color(0, 0, 139));
		table.setBackground(new Color(173, 216, 230));
		scrollPane.setViewportView(table);
	}
}
