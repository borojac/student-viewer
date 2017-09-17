package org.unibl.etf.ps.studentviewer.gui.view;

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
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.lf5.viewer.LogFactor5LoadingDialog;
import org.jdesktop.swingx.color.GradientTrackRenderer;
import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.gui.GradingTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.GradeGenerationController;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dao.StudentDAO;
import org.unibl.etf.ps.studentviewer.model.dto.GradingInfoDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

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

public class GradeGenerationForm extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private GradeGenerationController controller;

	private JTextField brojIndeksaTextField;
	private JTextField imeTextField;
	private JTextField prezimeTextField;
	private JTextField ocjenaTextField;
	private JTable table;
	private JButton okButton;
	private JButton cancelButton;

	public GradeGenerationForm(PredmetDTO predmet, List<StudentNaPredmetuDTO> students) {

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
		brojIndeksaTextField.setBounds(120, 12, 86, 20);
		contentPanel.add(brojIndeksaTextField);
		brojIndeksaTextField.setColumns(10);

		imeTextField = new JTextField();
		imeTextField.setBounds(120, 43, 204, 20);
		contentPanel.add(imeTextField);
		imeTextField.setColumns(10);

		prezimeTextField = new JTextField();
		prezimeTextField.setBounds(120, 74, 204, 20);
		contentPanel.add(prezimeTextField);
		prezimeTextField.setColumns(10);

		JLabel lblOcjena = new JLabel("Ocjena:");
		lblOcjena.setForeground(Color.WHITE);
		lblOcjena.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOcjena.setBounds(10, 104, 100, 20);
		contentPanel.add(lblOcjena);

		ocjenaTextField = new JTextField();
		ocjenaTextField.setBounds(120, 105, 86, 20);
		contentPanel.add(ocjenaTextField);
		ocjenaTextField.setColumns(10);

		initTable();

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			okButton = new JButton("Ocijeni");
			okButton.setEnabled(false);
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							controller.gradeBtnAction(e);
							
						}
					}).start();
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							okButton.setEnabled(false);

						}
					});
				}
			});
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);

			cancelButton = new JButton("Dalje");
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					controller.loadStudentInfo();
				}
			});
			buttonPane.add(cancelButton);
			buttonPane.add(okButton);

		}

		controller.loadStudentInfo();
	}

	public void printStudent(StudentNaPredmetuDTO student) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				brojIndeksaTextField.setText(student.getBrojIndeksa());
				imeTextField.setText(student.getIme());
				prezimeTextField.setText(student.getPrezime());
				ocjenaTextField.setText("-");
			}
		});
	}

	private void initTable() {

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 314, 182);
		contentPanel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						if (table.getSelectedRowCount() > 0) {
							EventQueue.invokeLater(new Runnable() {

								@Override
								public void run() {
									okButton.setEnabled(true);

								}
							});
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									controller.calculateGrade();
								}
							}).start();
						} else {
							EventQueue.invokeLater(new Runnable() {

								@Override
								public void run() {
									okButton.setEnabled(false);

								}
							});
						}
					}
				}).start();
			}
		});
		scrollPane.setViewportView(table);

		table.setModel(new GradingTableModel());
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

	public int getOcjena() {
		return Integer.parseInt(ocjenaTextField.getText());
	}
}


