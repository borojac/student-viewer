package org.unibl.etf.ps.studentviewer.gui.view.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.lf5.viewer.LogFactor5LoadingDialog;
import org.jdesktop.swingx.color.GradientTrackRenderer;
import org.unibl.etf.ps.studentviewer.gui.view.test.TestoviTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.student.GradeGenerationController;
import org.unibl.etf.ps.studentviewer.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.persistence.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.StudentDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.GradingInfoDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.TestDTO;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GradeGenerationForm extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private GradeGenerationController controller;

	private JTextField brojIndeksaTextField;
	private JTextField imeTextField;
	private JTextField prezimeTextField;
	private JTextField ocjenaTextField;
	private JTextField bodoviTextField;
	private JTable table;
	private JButton btnOcijeni;
	private JButton btnDalje;
	private JButton btnNazad;
	private JPanel buttonPane;
	private JButton btnPonisti;

	public GradeGenerationForm(PredmetDTO predmet, List<? extends StudentNaPredmetuDTO> students) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		controller = new GradeGenerationController(this, predmet);
		controller.loadStudentsForGrading(students);

		setBounds(100, 100, 350, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 0, 139));
		contentPanel.setForeground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblBrojIndeksa = new JLabel("Broj indeksa:");
		lblBrojIndeksa.setForeground(Color.WHITE);
		lblBrojIndeksa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBrojIndeksa.setBounds(10, 11, 100, 20);
		contentPanel.add(lblBrojIndeksa);

		JLabel lblIme = new JLabel("Ime:");
		lblIme.setForeground(Color.WHITE);
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIme.setBounds(10, 42, 100, 20);
		contentPanel.add(lblIme);

		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setForeground(Color.WHITE);
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrezime.setBounds(10, 73, 100, 20);
		contentPanel.add(lblPrezime);

		brojIndeksaTextField = new JTextField();
		brojIndeksaTextField.setEditable(false);
		brojIndeksaTextField.setBounds(120, 12, 84, 20);
		contentPanel.add(brojIndeksaTextField);
		brojIndeksaTextField.setColumns(10);

		imeTextField = new JTextField();
		imeTextField.setEditable(false);
		imeTextField.setBounds(120, 43, 204, 20);
		contentPanel.add(imeTextField);
		imeTextField.setColumns(10);

		prezimeTextField = new JTextField();
		prezimeTextField.setEditable(false);
		prezimeTextField.setBounds(120, 74, 204, 20);
		contentPanel.add(prezimeTextField);
		prezimeTextField.setColumns(10);

		JLabel lblOcjena = new JLabel("Ocjena:");
		lblOcjena.setForeground(Color.WHITE);
		lblOcjena.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOcjena.setBounds(10, 104, 100, 20);
		contentPanel.add(lblOcjena);

		ocjenaTextField = new JTextField();
		ocjenaTextField.setEditable(false);
		ocjenaTextField.setBounds(120, 105, 40, 20);
		contentPanel.add(ocjenaTextField);
		ocjenaTextField.setColumns(10);

		initTable();


		buttonPane = new JPanel();
		buttonPane.setBackground(new Color(0, 0, 139));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOcijeni = new JButton("Ocijeni");
		btnOcijeni.setBackground(new Color(0, 0, 139));
		btnOcijeni.setEnabled(false);
		btnOcijeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						controller.gradeBtnAction(e);
					}
				}).start();
			}
		});

		btnNazad = new JButton("Nazad");
		btnNazad.setBackground(new Color(0, 0, 139));
		btnNazad.setEnabled(false);
		btnNazad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.loadStudentInfoPrev(btnNazad);
				btnDalje.setEnabled(true);
			}
		});
		buttonPane.add(btnNazad);

		btnDalje = new JButton("Dalje");
		btnDalje.setBackground(new Color(0, 0, 139));
		btnDalje.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadStudentInfoNext(btnDalje);
				btnNazad.setEnabled(true);
			}
		});
		buttonPane.add(btnDalje);



		controller.loadStudentInfoNext(btnDalje);

		btnPonisti = new JButton("Poni\u0161ti");
		btnPonisti.setBackground(new Color(0, 0, 139));
		btnPonisti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						controller.recallBtnAction(e);
					}
				}).start();
			}
		});
		btnPonisti.setEnabled(false);
		buttonPane.add(btnPonisti);
		buttonPane.add(btnOcijeni);
		if (students.size() == 1) {
			btnDalje.setEnabled(false);
		}
		buttonPane.add(btnOcijeni);
	}

	public void printStudent(StudentNaPredmetuDTO student) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				brojIndeksaTextField.setText(student.getBrojIndeksa());
				imeTextField.setText(student.getIme());
				prezimeTextField.setText(student.getPrezime());
				ocjenaTextField.setText("-");
				table.clearSelection();
			}
		});
	}

	private void initTable() {

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 314, 182);
		contentPanel.add(scrollPane);

		table = new JTable();
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (table.contains(e.getPoint())
						&& table.getModel().getRowCount() >= table.rowAtPoint(e.getPoint())
						&& table.columnAtPoint(e.getPoint()) == 0) {

					int row = table.rowAtPoint(e.getPoint());
					GradingTableModel model = (GradingTableModel) table.getModel();
					table.setToolTipText(model.getDataAt(row).getTest().getNaziv());
				} else {
					table.setToolTipText(null);
				}
			}
		});
		table.setForeground(new Color(0, 0, 139));
		table.setBackground(new Color(173, 216, 230));
		table.setFont(new Font("Century Gothic", Font.BOLD, 11));
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount() > 0) {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							btnOcijeni.setEnabled(true);

						}
					});
					controller.calculateGrade();
				} else {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							btnOcijeni.setEnabled(false);
							ocjenaTextField.setText("-");
							bodoviTextField.setText("");
						}
					});
				}

			}
		});
		scrollPane.setViewportView(table);

		table.setModel(new GradingTableModel());

		bodoviTextField = new JTextField();
		bodoviTextField.setEditable(false);
		bodoviTextField.setBounds(240, 105, 84, 20);
		contentPanel.add(bodoviTextField);
		bodoviTextField.setColumns(10);

		JLabel lblBodovi = new JLabel("Bodovi:");
		lblBodovi.setForeground(Color.WHITE);
		lblBodovi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBodovi.setBounds(170, 105, 60, 20);
		contentPanel.add(lblBodovi);
	}

	public JTable getGradesTable() {
		return table;
	}

	public GradingTableModel getGradesTableModel() {
		return (GradingTableModel) table.getModel();
	}

	public void setOcjena(int ocjena) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				ocjenaTextField.setText("" + ocjena);
			}
		});
	}

	public void setBodovi(double totalPoints) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				bodoviTextField.setText(String.format("%.2f", totalPoints));
			}
		});
	}

	public int getOcjena() {
		return Integer.parseInt(ocjenaTextField.getText());
	}


	public JButton getBtnDalje() {
		return btnDalje;
	}

	public JButton getBtnNazad() {
		return btnNazad;
	}

	public JButton getBtnOcijeni() {
		return btnOcijeni;
	}

	public JButton getBtnPonisti() {
		return btnPonisti;
	}



}


