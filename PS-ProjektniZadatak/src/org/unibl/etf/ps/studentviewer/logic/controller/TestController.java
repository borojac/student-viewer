package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.experimental.categories.IncludeCategories;
import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.TestDodajStudenteForm;
import org.unibl.etf.ps.studentviewer.gui.view.TestForm;
import org.unibl.etf.ps.studentviewer.logic.utility.command.Command;
import org.unibl.etf.ps.studentviewer.logic.utility.command.CommandStack;
import org.unibl.etf.ps.studentviewer.logic.utility.command.IzmjenaBrojaBodovaTestCommand;
import org.unibl.etf.ps.studentviewer.logic.utility.command.UkloniStudenteTestCommand;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.StudentDAO;
import org.unibl.etf.ps.studentviewer.model.dao.TestDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class TestController {

	private CommandStack undoStack = new CommandStack();
	private CommandStack redoStack = new CommandStack();
	
	private TestDTO test;
	private TestForm testForm;

	public TestController(TestDTO test, TestForm testForm) {
		super();
		this.test = test;
		this.testForm = testForm;
	}

	public TestDTO getTest() {
		return test;
	}

	public void undoRedoAction(TestForm testForm, KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_Z && ke.isControlDown()) {
			undo();
			testForm.refreshStatistics();
			testForm.resetStudentiTable();
		} else if (ke.getKeyCode() == KeyEvent.VK_Y && ke.isControlDown()) {
			redo();
			testForm.refreshStatistics();
			testForm.resetStudentiTable();
		}
	}

	public void undo() {
		if (!undoStack.isEmpty()) {
			Command command = undoStack.pop();
			command.unExecute();
			redoStack.push(command);
		}
	}
	public void redo() {
		if (!redoStack.isEmpty()) {
			Command command = redoStack.pop();
			command.reExecute();
			undoStack.push(command);
		}
	}
	
	public void windowClosingAction() {
		if (!undoStack.isEmpty()) {
			String[] options = { "	Da	", "	Ne	" };
			int result = JOptionPane.showOptionDialog(testForm,
					"Imate nesačuvanih izmjena. Da li ste sigurni da želite zatvoriti prozor? Izmjene neće biti sačuvane!",
					"Potvrda zatvaranja", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null,
					options, options[1]);
			if (result == JOptionPane.YES_OPTION) {
				testForm.dispose();
			}
		} else
			testForm.dispose();
	}

	public List<StudentNaTestuDTO> importFromExcel() throws FileNotFoundException, IOException {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileFilter excelFileFilter = new FileNameExtensionFilter("Microsoft Excel spreadsheet", "xls", "xlsx");
		fileChooser.addChoosableFileFilter(excelFileFilter);
		fileChooser.setFileFilter(excelFileFilter);
		fileChooser.showOpenDialog(null);
		
		File chosenFile = fileChooser.getSelectedFile();
		
		Set<StudentNaTestuDTO> data = new HashSet<>(test.getStudenti());
		DAOFactory factory = new MySQLDAOFactory();
		StudentDAO studentDAO = factory.getStudentDAO();
		TestDAO testDAO = factory.getTestDAO();
		

		if (chosenFile != null && chosenFile.exists() && chosenFile.getAbsolutePath().endsWith(".xls")) {
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(chosenFile));
			HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowIndex = 0;
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); ++i) {
				HSSFRow row = sheet.getRow(i);
				if ("Br. ind.".equalsIgnoreCase(row.getCell(0).getStringCellValue().trim())
						|| "Prezime".equalsIgnoreCase(row.getCell(1).getStringCellValue().trim())
						|| "Ime".equalsIgnoreCase(row.getCell(3).getStringCellValue().trim())) {
					rowIndex = i + 1;
					break;
				}
			}
			
			HSSFRow titleRow = sheet.getRow(rowIndex - 1);
			
			
			for (int i = rowIndex; i < sheet.getPhysicalNumberOfRows(); ++i) {
				HSSFRow row = sheet.getRow(i);
				String brojIndeksa = row.getCell(0).getStringCellValue().trim();

				for (int c = 0; c < brojIndeksa.length(); ++c) {
					if (brojIndeksa.charAt(c) != '0') {
						brojIndeksa = brojIndeksa.substring(c);
						break;
					}
				}
				
				
				int brojBodova = 0;
				String komentar = "";
				if ("Bodovi".equals(titleRow.getCell(4).getStringCellValue().trim())
						&& "Komentar".equals(titleRow.getCell(5).getStringCellValue().trim())) {
					try {
						brojBodova = Integer.parseInt(row.getCell(4).getStringCellValue().trim());
					} catch (NumberFormatException ex) {}
					komentar = row.getCell(5).getStringCellValue().trim();
				}
				boolean verified = testDAO.verifyStudent(brojIndeksa, test.getTestId());
				
				if (verified) {
					StudentNaPredmetuDTO tmp = studentDAO.getStudentBy(brojIndeksa);
					data.add(new StudentNaTestuDTO(tmp.getStudentId(), brojIndeksa, 
							tmp.getIme(), tmp.getPrezime(), brojBodova, komentar));
				}
				
			}

			workbook.close();
			fileSystem.close();
		} else if (chosenFile != null && chosenFile.exists() && chosenFile.getAbsolutePath().endsWith(".xlsx")) {

			InputStream chosenFileInputStream = 
					new BufferedInputStream(new FileInputStream(chosenFile));

			XSSFWorkbook workbook = new XSSFWorkbook(chosenFileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowIndex = 0;
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); ++i) {
				Row row = sheet.getRow(i);
				if ("Br. ind.".equalsIgnoreCase(row.getCell(0).getStringCellValue().trim())
						|| "Prezime".equalsIgnoreCase(row.getCell(1).getStringCellValue().trim())
						|| "Ime".equalsIgnoreCase(row.getCell(3).getStringCellValue().trim())) {
					rowIndex = i + 1;
					break;
				}
			}
			
			Row titleRow = sheet.getRow(rowIndex - 1);

			for (int i = rowIndex; i < sheet.getPhysicalNumberOfRows(); ++i) {
				Row row = sheet.getRow(i);
				String brojIndeksa = row.getCell(0).getStringCellValue().trim();

				for (int c = 0; c < brojIndeksa.length(); ++c) {
					if (brojIndeksa.charAt(c) != '0') {
						brojIndeksa = brojIndeksa.substring(c);
						break;
					}
				}
				
				
				int brojBodova = 0;
				String komentar = "";
				if ("Bodovi".equals(titleRow.getCell(4).getStringCellValue().trim())
						&& "Komentar".equals(titleRow.getCell(5).getStringCellValue().trim())) {
					try {
						brojBodova = Integer.parseInt(row.getCell(4).getStringCellValue().trim());
					} catch (NumberFormatException ex) {}
					komentar = row.getCell(5).getStringCellValue().trim();
				}
				boolean verified = testDAO.verifyStudent(brojIndeksa, test.getTestId());
				if (verified) {
					StudentNaPredmetuDTO tmp = studentDAO.getStudentBy(brojIndeksa);
					data.add(new StudentNaTestuDTO(tmp.getStudentId(), brojIndeksa, 
							tmp.getIme(), tmp.getPrezime(), brojBodova, komentar));
				}
				


			}

			workbook.close();
			chosenFileInputStream.close();
		} else
			return null;
		return new ArrayList<>(data);
	}

	public void executeCommand(Command command) {
		command.execute();
		undoStack.push(command);
	}


	public void export(TestDTO test) throws DocumentException, IOException {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		int retVal = fileChooser.showSaveDialog(null);
		if (retVal != JFileChooser.APPROVE_OPTION)
			return;
		File chosenFile = fileChooser.getSelectedFile();
		if (!chosenFile.getAbsolutePath().endsWith(".pdf")) {
			final String aPath = chosenFile.getAbsolutePath();
			chosenFile = new File(aPath + ".pdf");
		}
		
		Font font = FontFactory.getFont("fonts/tahoma.ttf", BaseFont.IDENTITY_H, 12);
		Document doc = new Document();
		OutputStream os = new FileOutputStream(chosenFile);
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		
		Paragraph title = new Paragraph();
		title.setIndentationLeft(60f);
		title.setFont(font);
		title.add(test.getNaziv());
		title.add("\n\n");
		title.add("Datum: " + new SimpleDateFormat("dd.MM.yyyy").format(test.getDatum()));
		title.add("\n\n");
		title.add("Napomena:\t" + test.getNapomena());
		title.add("\n\n");
		
		Paragraph spacing = new Paragraph("\n\n");
		spacing.add("Studenti na testu:");
		spacing.add("\n\n");
		
		Paragraph body = new Paragraph();
		body.setIndentationLeft(60f);
		body.setFont(font);
		body.setTabSettings(new TabSettings());
		for (StudentNaTestuDTO student : test.getStudenti()) {
			final String studentString = student.getBrojIndeksa() + " " + student.getIme() + " " + student.getPrezime();
			final String bodovi = "" + student.getBrojBodova();
			final String komentar = student.getKomentar();
			body.add(new Chunk(studentString));
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(new Chunk(bodovi));
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(new Chunk(komentar));
			body.add("\n");
			body.add("\n");
		}
		doc.open();
		doc.add(title);
		doc.add(spacing);
		doc.add(body);
		doc.close();
		writer.flush();
		writer.close();
		os.close();
	}

	public void print(TestDTO test) throws IOException, DocumentException, PrinterException {
		
		Font font = FontFactory.getFont("fonts/tahoma.ttf", BaseFont.IDENTITY_H, 12);
		Document doc = new Document();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		Paragraph title = new Paragraph();
		title.setIndentationLeft(60f);
		title.setFont(font);
		title.add(test.getNaziv());
		title.add("\n\n");
		title.add("Datum: " + new SimpleDateFormat("dd.MM.yyyy").format(test.getDatum()));
		title.add("\n\n");
		title.add("Napomena:\t" + test.getNapomena());
		title.add("\n\n");
		
		Paragraph spacing = new Paragraph("\n\n");
		spacing.add("Studenti na testu:");
		spacing.add("\n\n");
		
		Paragraph body = new Paragraph();
		body.setIndentationLeft(60f);
		body.setFont(font);
		body.setTabSettings(new TabSettings());
		for (StudentNaTestuDTO student : test.getStudenti()) {
			final String studentString = student.getBrojIndeksa() + " " + student.getIme() + " " + student.getPrezime();
			final String bodovi = "" + student.getBrojBodova();
			final String komentar = student.getKomentar();
			body.add(new Chunk(studentString));
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(new Chunk(bodovi));
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(Chunk.TABBING);
			body.add(new Chunk(komentar));
			body.add("\n");
			body.add("\n");
		}
		doc.open();
		doc.add(title);
		doc.add(spacing);
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

	public String getTestStatistics() {
		//		TODO - dodati ukupan broj studenata na predmetu - potreban PredmetDTO
		StringBuilder statisticsBuilder = new StringBuilder();

		int izaslo = test.getStudenti().size();
//		int ukupno = predmetDAO.getAllStudents().size();
		int ukupno = 100;
		int polozilo = 0, deset = 0, devet = 0, osam = 0, sedam = 0, sest = 0;

		for (StudentNaTestuDTO student : test.getStudenti()) {
			int brojBodova = student.getBrojBodova();
			if (brojBodova > 50)
				++polozilo;
			if (brojBodova > 90)
				++deset;
			else if (brojBodova > 80)
				++devet;
			else if (brojBodova > 70)
				++osam;
			else if (brojBodova > 60)
				++sedam;
			else if (brojBodova > 50)
				++sest;
		}

		statisticsBuilder.append("Izaslo: " + izaslo + "/" + ukupno + " " + 
		String.format("(%.2f %%)", (double)izaslo / (double) polozilo * 100.0)).append('\n');
		
		statisticsBuilder.append(">50%: " + polozilo
				+ String.format("(%.2f %%)", (double)polozilo / (double) izaslo * 100.0)).append('\n');
		statisticsBuilder.append("<50%: " + (izaslo - polozilo)
				+ String.format("(%.2f %%)", (double)(izaslo-polozilo) / (double)izaslo * 100.0)).append('\n');
		statisticsBuilder.append(">90%: " + deset
				+ String.format("(%.2f %%)", (double)deset / (double)polozilo * 100.0)).append('\n');
		statisticsBuilder.append("80%-90%: " + devet + String.format("(%.2f %%)", (double)devet / (double)polozilo * 100.0)).append('\n');
		statisticsBuilder.append("70%-80%: " + osam + String.format("(%.2f %%)", (double)osam / (double)polozilo * 100.0)).append('\n');
		statisticsBuilder.append("60%-70%: " + sedam + String.format("(%.2f %%)", (double)sedam / (double)polozilo * 100.0)).append('\n');
		statisticsBuilder.append("50%-60%: " + sest + String.format("(%.2f %%)", (double)sest / (double)polozilo * 100.0));

		return statisticsBuilder.toString();
	}
	public void initiateStudentSearch(StudentTableModel model, String searchText) {
		if (test == null || model == null || searchText == null)
			return;
		if ("".equals(searchText)) {
			model.setData(test.getStudenti());
			return;
		}
		List<StudentNaTestuDTO> searchedList = null;

		Matcher matcher = Pattern.compile("[<,>,=]+").matcher(searchText);
		Matcher numberMatcher = Pattern.compile("\\d+").matcher(searchText);
		int count = 0;
		while (matcher.find()) 
			++count;
		if (count == 1) {
			count = 0;
			matcher = matcher.reset();
			matcher.find();
			String diskriminator = matcher.group();
			while (numberMatcher.find()) 
				++count;
			if (count == 1) {
				numberMatcher = numberMatcher.reset();
				numberMatcher.find();
				int brojBodova = Integer.parseInt(numberMatcher.group());
				searchedList = this.filter(brojBodova, diskriminator);
			} else {
				searchedList = this.pretraga(searchText);
			}
		} else {
			searchedList = this.pretraga(searchText);

		}
		model.setData(searchedList);

	}

	private List<StudentNaTestuDTO> filter(int brojBodova, String diskriminator) {
		List<StudentNaTestuDTO> retList = new ArrayList<>();
		for (StudentNaTestuDTO student : test.getStudenti()) {
			if ("<".equals(diskriminator) && student.getBrojBodova() < brojBodova)
				retList.add(student);
			else if ("<=".equals(diskriminator) && student.getBrojBodova() <= brojBodova)
				retList.add(student);
			else if (("=".equals(diskriminator) || "".equals(diskriminator)) && student.getBrojBodova() == brojBodova)
				retList.add(student);
			else if ("<=".equals(diskriminator) && student.getBrojBodova() <= brojBodova)
				retList.add(student);
			else if (">=".equals(diskriminator) && student.getBrojBodova() >= brojBodova)
				retList.add(student);
			else if (">".equals(diskriminator) && student.getBrojBodova() > brojBodova)
				retList.add(student);
			else if (("<>".equals(diskriminator) || "!=".equals(diskriminator)) && student.getBrojBodova() != brojBodova)
				retList.add(student);
		}
		return retList;
	}

	private List<StudentNaTestuDTO> pretraga(String query) {
		List<StudentNaTestuDTO> retList = new ArrayList<>();
		for (StudentNaTestuDTO student : test.getStudenti()) {
			String ime = student.getIme().toLowerCase();
			String prezime = student.getPrezime().toLowerCase();
			query = query.toLowerCase();
			if (ime.startsWith(query) || prezime.startsWith(query) || student.getBrojIndeksa().startsWith(query))
				retList.add(student);
		}
		return retList;
	}

	public void addTestAction() {
		DAOFactory factory = new MySQLDAOFactory();
		TestDAO testDAO = factory.getTestDAO();
		if (!testDAO.addTest(test))
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					JOptionPane.showMessageDialog(testForm, "Dodavanje nije uspjelo. Pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
				}
			});
		else 
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					testForm.dispose();
				}
			});
		

	}

	public void updateTestAction() {
		DAOFactory factory = new MySQLDAOFactory();
		TestDAO testDAO = factory.getTestDAO();
		
		if (!testDAO.updateTest(test))
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					JOptionPane.showMessageDialog(testForm, "Ažuriranje nije uspjelo. Pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
				}
			});
		else
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					testForm.dispose();
				}
			});
	}
	
	public void addStudents() {
		final TestController testController = this;
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				TestDodajStudenteForm dodajStudenteDialog = new TestDodajStudenteForm(testForm, testController);
				dodajStudenteDialog.setVisible(true);
			}
		});
	}
	public void removeStudents(StudentTableModel model, List<StudentNaTestuDTO> forRemoving) {
		this.executeCommand(new UkloniStudenteTestCommand(test, model, forRemoving));

		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				testForm.refreshStatistics();
				testForm.refreshStudentiTable();
			}
		});
	}
	

}
