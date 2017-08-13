package org.unibl.etf.ps.studentviewer.gui.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPrinterGraphics2D;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.InputStream;
import javax.swing.JLabel;
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
import datechooser.beans.DateChooserCombo;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

	private JFrame thisFrame;
	private JButton btnImport;

	public TestForm(TestDTO testParam) {
		setResizable(false);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				TestController.getInstance().focusLostAction(ke);
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 10, 540, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.thisFrame = this;

		if (testParam != null) {
			test = testParam;
			update = true;
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
		nazivTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TestController.getInstance().focusLostAction(e);
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
				TestController.getInstance().focusLostAction(e);
			}
		});
		napomenaScrollPane.setViewportView(napomenaTextArea);
		napomenaTextArea.setRows(5);

		studentiScrollPane = new JScrollPane();
		studentiScrollPane.setBounds(10, 420, 504, 196);
		contentPane.add(studentiScrollPane);

		// TODO - popuniti tabelu

		List<StudentNaTestuDTO> studenti = new ArrayList<>();
		studenti.add(new StudentNaTestuDTO(1, "1145/14", "Nemanja", "Stokuca", 65, "Hahhahahah"));
		test.setStudenti(studenti);
		StudentTableModel model = new StudentTableModel(studenti);


		studentiTable = new JTable(model);
		studentiTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (studentiTable.getSelectedRow() != -1)
					btnUkloni.setEnabled(true);
				else
					btnUkloni.setEnabled(false);
			}
		});
		studentiScrollPane.setViewportView(studentiTable);

		btnSacuvaj = new JButton("Sa\u010Duvaj");
		btnSacuvaj.setBounds(444, 627, 70, 23);
		contentPane.add(btnSacuvaj);

		searchTextField = new JTextField();
		searchTextField.setBounds(10, 389, 405, 20);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);

		btnPretrazi = new JButton("Pretra\u017Ei");
		btnPretrazi.setBounds(424, 388, 90, 23);
		contentPane.add(btnPretrazi);

		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					print();
				} catch (Exception e) {
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
					export();
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
				if (studentiTable.getSelectedRows().length > 0) {
					StudentTableModel model = (StudentTableModel) studentiTable.getModel();
					List<StudentNaTestuDTO> studentList = new ArrayList<>(model.getData());
					List<StudentNaTestuDTO> forDelete = new ArrayList<>();
					for (int x : studentiTable.getSelectedRows()) {
						forDelete.add(studentList.get(x));
					}
					studentList.removeAll(forDelete);
					TestController.getInstance().executeCommand(new UkloniStudenteTestCommand(test, model, studentList));
				}
			}
		});
		btnUkloni.setBounds(330, 627, 70, 23);
		contentPane.add(btnUkloni);

		dateChooserCombo = new DateChooserCombo();
		test.setDatum(dateChooserCombo.getSelectedDate().getTime());
		dateChooserCombo.setBounds(144, 55, 271, 20);
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
				TestController.getInstance().focusLostAction(e);
			}
		});
		contentPane.add(dateChooserCombo);
		
		btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					List<StudentNaTestuDTO> data = TestController.getInstance().importFromExcel();
					
					
					TestController.getInstance().executeCommand(
							new DodajStudenteTestCommand(
									test, 
									(StudentTableModel) studentiTable.getModel(), 
									data));
					
					
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
	}

	private void export() throws Exception {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		int retVal = fileChooser.showSaveDialog(this);
		if (retVal != JFileChooser.APPROVE_OPTION)
			return;
		File chosenFile = fileChooser.getSelectedFile();
		if (!chosenFile.getAbsolutePath().endsWith(".pdf")) {
			final String aPath = chosenFile.getAbsolutePath();
			chosenFile = new File(aPath + ".pdf");
		}
		Document doc = new Document();
		OutputStream os = new FileOutputStream(chosenFile);
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		Paragraph title = new Paragraph();
		title.add(test.getNaziv());
		title.add("\n\n");
		title.add("Datum: " + new SimpleDateFormat("dd.MM.yyyy").format(test.getDatum()));
		title.add("\n\n");
		title.add("Napomena: " + test.getNapomena());
		title.add("\n\n");
		Paragraph body = new Paragraph();
		for (StudentNaTestuDTO student : test.getStudenti()) {
			final String studentString = student.getBrojIndeksa() + " " + student.getIme() + " " + student.getPrezime()
			+ " " + student.getBrojBodova() + " " + student.getKomentar();
			body.add(studentString);
			body.add("\n");
		}
		doc.open();
		doc.add(title);
		doc.add(body);
		doc.close();
		writer.flush();
		writer.close();
		os.close();
	}

	// TODO - ne radi u ovom obliku
	private void print() throws Exception {
		Document doc = new Document();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		Paragraph title = new Paragraph();
		title.add(test.getNaziv());
		title.add("\n\n");
		title.add("Datum: " + new SimpleDateFormat("dd.MM.yyyy").format(test.getDatum()));
		title.add("\n\n");
		title.add("Napomena: " + test.getNapomena());
		title.add("\n\n");
		Paragraph body = new Paragraph();
		for (StudentNaTestuDTO student : test.getStudenti()) {
			final String studentString = student.getBrojIndeksa() + " " + student.getIme() + " " + student.getPrezime()
			+ " " + student.getBrojBodova() + " " + student.getKomentar();
			body.add(studentString);
			body.add("\n");
		}
		doc.open();
		doc.add(title);
		doc.add(body);
		doc.close();
		writer.flush();
		writer.close();
		
		PDDocument printDoc = PDDocument.load(os.toByteArray());
		
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(new PDFPageable(printDoc));
		if (job.printDialog()) {
			job.print();
		}
		printDoc.close();
	}

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
}
