package org.unibl.etf.ps.studentviewer.gui.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.pdfbox.debugger.treestatus.TreeStatusPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.bouncycastle.asn1.cmp.KeyRecRepContent;
import org.unibl.etf.ps.studentviewer.command.Command;
import org.unibl.etf.ps.studentviewer.command.DodajNapomenuTestCommand;
import org.unibl.etf.ps.studentviewer.command.DodajStudenteTestCommand;
import org.unibl.etf.ps.studentviewer.command.IzmjenaNazivaTestaCommand;
import org.unibl.etf.ps.studentviewer.command.UkloniStudenteTestCommand;
import org.unibl.etf.ps.studentviewer.command.IzmjenaDatumaTestCommand;
import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.gui.controler.TestController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPrinterGraphics2D;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.InputStream;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import datechooser.events.CommitEvent;
import datechooser.events.CommitListener;
import datechooser.model.multiple.MultyModelBehavior;
import datechooser.beans.DateChooserCombo;
import datechooser.beans.DateChooserComboBeanInfo;
import datechooser.beans.DateChooserPanelCustomizer;
import datechooser.beans.DateChooserVisual;
import datechooser.controller.DateChooseController;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;
import javax.swing.JComboBox;

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

	private TestDTO test;
	private TestController testController;
	private DateChooserCombo dateChooserCombo;
	private boolean update = false;
	private boolean needsRefresh = false;

	private TestForm testForm;
	private JButton btnImport;

	private Logger logger = Logger.getLogger(TestForm.class);
	private JTextArea statistikaTextArea;
	private JComboBox procenatComboBox;

	public TestForm(TestDTO testParam) {
		setResizable(false);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 10, 540, 686);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});

		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.testForm = this;

		if (testParam != null) {
			test = new TestDTO(testParam.getTestId(), testParam.getNaziv(), testParam.getDatum(),
					testParam.getNapomena(), testParam.getProcenat(), testParam.getPredmetId());
			test.setStudenti(testParam.getStudenti());
			update = true;
		} else 
			test = new TestDTO();

		testController = new TestController(test, this);
		
		try {
			logger.addAppender(new FileAppender(new SimpleLayout(), TestForm.class.getSimpleName() + ".log"));
		} catch (IOException e1) {
			this.dispose();
			throw new RuntimeException(e1);
		}

		JLabel lblNaziv = new JLabel("Naziv:");
		lblNaziv.setForeground(Color.WHITE);
		lblNaziv.setHorizontalAlignment(SwingConstants.CENTER);
		lblNaziv.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNaziv.setBounds(41, 11, 70, 20);
		contentPane.add(lblNaziv);

		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setForeground(Color.WHITE);
		lblDatum.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatum.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDatum.setBounds(41, 55, 70, 20);
		contentPane.add(lblDatum);

		JLabel lblNapomena = new JLabel("Napomena:");
		lblNapomena.setForeground(Color.WHITE);
		lblNapomena.setHorizontalAlignment(SwingConstants.CENTER);
		lblNapomena.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNapomena.setBounds(41, 104, 70, 20);
		contentPane.add(lblNapomena);

		nazivTextField = new JTextField();
		nazivTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});
		nazivTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				IzmjenaNazivaTestaCommand command = new IzmjenaNazivaTestaCommand(test, nazivTextField);
				testController.executeCommand(command);
			}
		});
		nazivTextField.setBounds(144, 12, 280, 20);
		contentPane.add(nazivTextField);
		nazivTextField.setColumns(10);

		JScrollPane napomenaScrollPane = new JScrollPane();
		napomenaScrollPane.setBounds(144, 103, 280, 100);
		contentPane.add(napomenaScrollPane);

		napomenaTextArea = new JTextArea();
		napomenaTextArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Command command = new DodajNapomenuTestCommand(test, napomenaTextArea);
				testController.executeCommand(command);
			}
		});
		napomenaTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});
		napomenaScrollPane.setViewportView(napomenaTextArea);

		studentiScrollPane = new JScrollPane();
		studentiScrollPane.setBackground(new Color(0, 0, 139));
		studentiScrollPane.setBounds(10, 420, 514, 196);
		studentiScrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				studentiTable.clearSelection();
				refreshStatistics();
			}
		});
		contentPane.add(studentiScrollPane);

		
		StudentTableModel tableModel = new StudentTableModel(test.getStudenti());
		tableModel.setTestController(testController);
		
		
		studentiTable = new JTable(tableModel);
		studentiTable.setForeground(new Color(0, 0, 139));
		studentiTable.setBackground(new Color(173, 216, 230));
		studentiTable.setFont(new Font("Century Gothic", Font.BOLD, 11));
		studentiTable.setFillsViewportHeight(true);
		studentiTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (studentiTable.getSelectedRow() != -1)
					btnUkloni.setEnabled(true);
				else {
					btnUkloni.setEnabled(false);
					studentiTable.clearSelection();
					refreshStatistics();
				}
			}
		});

		studentiTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					refreshStatistics();
			}
		});

		studentiScrollPane.setViewportView(studentiTable);

		btnSacuvaj = new JButton("Sa\u010Duvaj");
		btnSacuvaj.setBackground(new Color(0, 0, 139));
		btnSacuvaj.setBounds(454, 627, 70, 23);
		contentPane.add(btnSacuvaj);

		searchTextField = new JTextField();
		searchTextField.setForeground(new Color(0, 0, 139));
		searchTextField.setBackground(new Color(173, 216, 230));
		searchTextField.setFont(new Font("Century Gothic", Font.BOLD, 11));
		searchTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				needsRefresh = true;
				final String searchText = searchTextField.getText();
				testController.initiateStudentSearch(
						test,
						(StudentTableModel)studentiTable.getModel(),
						searchText);
			}
		});
		searchTextField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		searchTextField.setBounds(10, 389, 414, 20);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);

		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});

		btnPretrazi = new JButton("Pretra\u017Ei");
		btnPretrazi.setBackground(new Color(0, 0, 139));
		btnPretrazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				needsRefresh = true;
				final String searchText = searchTextField.getText();
				testController.initiateStudentSearch(
						test,
						(StudentTableModel)studentiTable.getModel(),
						searchText);
			}
		});
		btnPretrazi.setBounds(434, 389, 90, 22);
		contentPane.add(btnPretrazi);

		btnPrint = new JButton("Print");
		btnPrint.setBackground(new Color(0, 0, 139));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					testController.print(test);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnPrint.setBounds(10, 627, 70, 23);
		contentPane.add(btnPrint);

		btnEksport = new JButton("Eksport");
		btnEksport.setBackground(new Color(0, 0, 139));
		btnEksport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					testController.export(test);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnEksport.setBounds(90, 627, 70, 23);
		contentPane.add(btnEksport);

		btnDodaj = new JButton("Dodaj");
		btnDodaj.setBackground(new Color(0, 0, 139));
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (needsRefresh) {
					testForm.resetSearch();
					needsRefresh = false;
				}
				TestDodajStudenteDialog dodajStudenteDialog = new TestDodajStudenteDialog(testForm, testController);
				dodajStudenteDialog.setVisible(true);
			}
		});
		btnDodaj.setBounds(170, 627, 70, 23);
		contentPane.add(btnDodaj);

		btnUkloni = new JButton("Ukloni");
		btnUkloni.setBackground(new Color(0, 0, 139));
		btnUkloni.setEnabled(false);
		btnUkloni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!needsRefresh && studentiTable.getSelectedRows().length > 0) {
					StudentTableModel model = (StudentTableModel) studentiTable.getModel();
					List<StudentNaTestuDTO> studentList = new ArrayList<>(model.getData());
					List<StudentNaTestuDTO> forDelete = new ArrayList<>();
					for (int x : studentiTable.getSelectedRows()) {
						forDelete.add(studentList.get(x));
					}
					studentList.removeAll(forDelete);
					testController.executeCommand(new UkloniStudenteTestCommand(test, model, studentList));
					refreshStatistics();
				}
			}
		});
		btnUkloni.setBounds(330, 627, 70, 23);
		contentPane.add(btnUkloni);

		dateChooserCombo = new DateChooserCombo();
		dateChooserCombo.setBehavior(MultyModelBehavior.SELECT_SINGLE);
		dateChooserCombo.setBounds(144, 55, 280, 20);
		dateChooserCombo.setCalendarBackground(Color.WHITE);
		dateChooserCombo.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));

		dateChooserCombo.addCommitListener(new CommitListener() {

			@Override
			public void onCommit(CommitEvent arg0) {
				Command command = new IzmjenaDatumaTestCommand(test, dateChooserCombo);
				testController.executeCommand(command);

			}
		});

		dateChooserCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});



		contentPane.add(dateChooserCombo);

		btnImport = new JButton("Import");
		btnImport.setBackground(new Color(0, 0, 139));
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					List<StudentNaTestuDTO> data = testController.importFromExcel();

					if (data != null) {
						testController.executeCommand(
								new DodajStudenteTestCommand(
										test, 
										(StudentTableModel) studentiTable.getModel(), 
										data));
						refreshStatistics();
						searchTextField.setText("");
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnImport.setBounds(250, 627, 70, 23);
		contentPane.add(btnImport);

		JLabel lblStatistika = new JLabel("Statistika:");
		lblStatistika.setForeground(Color.WHITE);
		lblStatistika.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStatistika.setBounds(41, 233, 70, 20);
		contentPane.add(lblStatistika);

		statistikaTextArea = new JTextArea();
		statistikaTextArea.setEditable(false);
		statistikaTextArea.setBounds(144, 231, 280, 147);
		statistikaTextArea.setText(testController.getStatistika());
		contentPane.add(statistikaTextArea);
		
		JLabel lblProcenat = new JLabel("Procenat:");
		lblProcenat.setHorizontalAlignment(SwingConstants.CENTER);
		lblProcenat.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblProcenat.setForeground(Color.WHITE);
		lblProcenat.setBounds(434, 24, 90, 20);
		contentPane.add(lblProcenat);
		
		procenatComboBox = new JComboBox();
		procenatComboBox.setBounds(434, 55, 90, 20);
		contentPane.add(procenatComboBox);
		
		int tmpProcenat = 0;
		while (tmpProcenat <= 100) {
			procenatComboBox.addItem("" + tmpProcenat);
			tmpProcenat += 5;
		}
		
		procenatComboBox.setSelectedItem("" + 50);
		
		
		if (update)
			setFields();
	}


	public JTable getStudentiTable() {
		return studentiTable;
	}

	public TestDTO getModel() {
		return test;
	}

	public void refreshStatistics() {
		statistikaTextArea.setText(testController.getStatistika());
	}
	public void refreshStudentiTable() {
		((StudentTableModel) studentiTable.getModel()).fireTableDataChanged();
	}
	
	private void setFields() {
		procenatComboBox.setSelectedItem("" + test.getProcenat());
		procenatComboBox.setEnabled(false);
		nazivTextField.setText(test.getNaziv());
		Calendar cal = Calendar.getInstance();
		cal.setTime(test.getDatum());
		dateChooserCombo.setSelectedDate(cal);
		napomenaTextArea.setText(test.getNapomena());
		StudentTableModel model = (StudentTableModel) studentiTable.getModel();
		if (model == null) 
			model = new StudentTableModel(test.getStudenti());
		else
			model.setData(test.getStudenti());
		
	}
	
	private void resetSearch() {
		StudentTableModel model = (StudentTableModel) studentiTable.getModel();
		model.setData(test.getStudenti());
		model.fireTableDataChanged();
		searchTextField.setText("");
	}
}
