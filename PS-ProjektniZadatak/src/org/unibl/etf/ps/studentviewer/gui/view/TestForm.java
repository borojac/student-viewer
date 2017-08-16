package org.unibl.etf.ps.studentviewer.gui.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
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
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;

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
	private DateChooserCombo dateChooserCombo;
	private boolean update = false;
	private boolean needsRefresh = false;

	private JFrame thisFrame;
	private JButton btnImport;

	private Logger logger = Logger.getLogger(TestForm.class);
	private JTextArea statistikaTextArea;

	public TestForm(TestDTO testParam) {
		setResizable(false);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.getInstance().focusLostAction((TestForm) thisFrame, e);
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 10, 540, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.getInstance().focusLostAction((TestForm) thisFrame, e);
			}
		});

		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.thisFrame = this;

		if (testParam != null) {
			test = testParam;
			update = true;
		}

		try {
			logger.addAppender(new FileAppender(new SimpleLayout(), TestForm.class.getSimpleName() + ".log"));
		} catch (IOException e1) {
			this.dispose();
			throw new RuntimeException(e1);
		}

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
		nazivTextField.setBorder(new MatteBorder(2, 2, 2, 2, (Color) SystemColor.textHighlight));
		nazivTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.getInstance().focusLostAction((TestForm) thisFrame, e);
			}
		});
		nazivTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				IzmjenaNazivaTestaCommand command = new IzmjenaNazivaTestaCommand(test, nazivTextField);
				TestController.getInstance().executeCommand(command);
			}
		});
		nazivTextField.setBounds(144, 12, 271, 20);
		contentPane.add(nazivTextField);
		nazivTextField.setColumns(10);

		JScrollPane napomenaScrollPane = new JScrollPane();
		napomenaScrollPane.setBounds(144, 103, 271, 100);
		contentPane.add(napomenaScrollPane);

		napomenaTextArea = new JTextArea();
		napomenaTextArea.setBorder(new MatteBorder(2, 2, 2, 2, (Color) SystemColor.textHighlight));
		napomenaTextArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Command command = new DodajNapomenuTestCommand(test, napomenaTextArea);
				TestController.getInstance().executeCommand(command);
			}
		});
		napomenaTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.getInstance().focusLostAction((TestForm) thisFrame, e);
			}
		});
		napomenaScrollPane.setViewportView(napomenaTextArea);

		studentiScrollPane = new JScrollPane();
		studentiScrollPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) SystemColor.textHighlight));
		studentiScrollPane.setBounds(10, 420, 504, 196);
		studentiScrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				studentiTable.clearSelection();
				refreshStatistics();
			}
		});
		contentPane.add(studentiScrollPane);

		// TODO - popuniti tabelu

		List<StudentNaTestuDTO> studenti = test.getStudenti();
		studenti.add(new StudentNaTestuDTO(1, "1145/14", "Nemanja", "Stokuca", 65, "Hahhahahah"));
		test.setStudenti(studenti);
		StudentTableModel model = new StudentTableModel(studenti);
		model.setTestDTO(test);

		studentiTable = new JTable(model);
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
				TestController.getInstance().focusLostAction((TestForm) thisFrame, e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					refreshStatistics();
			}
		});

		studentiScrollPane.setViewportView(studentiTable);

		btnSacuvaj = new JButton("Sa\u010Duvaj");
		btnSacuvaj.setBounds(444, 627, 70, 23);
		contentPane.add(btnSacuvaj);

		searchTextField = new JTextField();
		searchTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				needsRefresh = true;
				final String searchText = searchTextField.getText();
				TestController.getInstance().initiateStudentSearch(
						test,
						(StudentTableModel)studentiTable.getModel(),
						searchText);
			}
		});
		searchTextField.setBorder(new MatteBorder(2, 2, 2, 2, (Color) SystemColor.textHighlight));
		searchTextField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		searchTextField.setBounds(10, 389, 405, 20);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);

		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.getInstance().focusLostAction((TestForm) thisFrame, e);
			}
		});

		btnPretrazi = new JButton("Pretra\u017Ei");
		btnPretrazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				needsRefresh = true;
				final String searchText = searchTextField.getText();
				TestController.getInstance().initiateStudentSearch(
						test,
						(StudentTableModel)studentiTable.getModel(),
						searchText);
			}
		});
		btnPretrazi.setBounds(424, 388, 90, 23);
		contentPane.add(btnPretrazi);

		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					TestController.getInstance().print(test);
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
		btnEksport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					TestController.getInstance().export(test);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnEksport.setBounds(90, 627, 70, 23);
		contentPane.add(btnEksport);

		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (needsRefresh) {
					TestController.getInstance().resetSearch(test, (StudentTableModel) studentiTable.getModel(), searchTextField);
					needsRefresh = false;
				}
				JDialog dodajStudenteDialog = new TestDodajStudenteDialog((TestForm) thisFrame);
				dodajStudenteDialog.setVisible(true);
			}
		});
		btnDodaj.setBounds(170, 627, 70, 23);
		contentPane.add(btnDodaj);

		btnUkloni = new JButton("Ukloni");
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
					TestController.getInstance().executeCommand(new UkloniStudenteTestCommand(test, model, studentList));
					refreshStatistics();
				}
			}
		});
		btnUkloni.setBounds(330, 627, 70, 23);
		contentPane.add(btnUkloni);

		dateChooserCombo = new DateChooserCombo();
		dateChooserCombo.setBehavior(MultyModelBehavior.SELECT_SINGLE);
		test.setDatum(dateChooserCombo.getSelectedDate().getTime());
		dateChooserCombo.setBounds(144, 55, 271, 20);
		dateChooserCombo.setCalendarBackground(Color.WHITE);
		dateChooserCombo.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));

		dateChooserCombo.addCommitListener(new CommitListener() {

			@Override
			public void onCommit(CommitEvent arg0) {
				Command command = new IzmjenaDatumaTestCommand(test, dateChooserCombo);
				TestController.getInstance().executeCommand(command);

			}
		});

		dateChooserCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.getInstance().focusLostAction((TestForm) thisFrame, e);
			}
		});



		contentPane.add(dateChooserCombo);

		btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					List<StudentNaTestuDTO> data = TestController.getInstance().importFromExcel();

					if (data != null) {
						TestController.getInstance().executeCommand(
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
		lblStatistika.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStatistika.setBounds(41, 233, 70, 20);
		contentPane.add(lblStatistika);

		statistikaTextArea = new JTextArea();
		statistikaTextArea.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(51, 153, 255)));
		statistikaTextArea.setEditable(false);
		statistikaTextArea.setBounds(144, 231, 271, 147);
		statistikaTextArea.setText(TestController.getInstance().getStatistika(test));
		contentPane.add(statistikaTextArea);
	}


	/**
	 * @deprecated - trebalo bi ukloniti prije posljenje verzije
	 * @param data
	 */
	public void setTableData(List<StudentNaTestuDTO> data) {
		if (studentiTable != null) {
			StudentTableModel model = (StudentTableModel) studentiTable.getModel();
			if (model == null)
				model = new StudentTableModel(data);
			else
				model.setData(data);
			test.setStudenti(data);
			model.fireTableDataChanged();
		}
		else {
			studentiTable = new JTable(new StudentTableModel(data));
		}
	}

	public JTable getStudentiTable() {
		return studentiTable;
	}

	public TestDTO getModel() {
		return test;
	}

	public void refreshStatistics() {
		statistikaTextArea.setText(TestController.getInstance().getStatistika(test));
	}
}
