package org.unibl.etf.ps.studentviewer.gui.controler;

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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

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
import org.unibl.etf.ps.studentviewer.command.Command;
import org.unibl.etf.ps.studentviewer.command.CommandStack;
import org.unibl.etf.ps.studentviewer.command.IzmjenaBrojaBodovaTestCommand;
import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.TestForm;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.TestDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TestController {

	private CommandStack undoStack = new CommandStack();
	private CommandStack redoStack = new CommandStack();

	private static TestController instance = null;

	public static TestController getInstance() {
		if (instance == null)
			instance = new TestController();
		return instance;
	}

	private TestController() {}

	public void undoRedoAction(TestForm testForm, KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_Z && ke.isControlDown()) {
			instance.undo();
			testForm.refreshStatistics();
			testForm.refreshStudentiTable();
		} else if (ke.getKeyCode() == KeyEvent.VK_Y && ke.isControlDown()) {
			instance.redo();
			testForm.refreshStatistics();
			testForm.refreshStudentiTable();
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

	public List<StudentNaTestuDTO> importFromExcel() throws FileNotFoundException, IOException {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		fileChooser.showOpenDialog(null);
		File chosenFile = fileChooser.getSelectedFile();
		List<StudentNaTestuDTO> data = new ArrayList<>();

		if (chosenFile != null && chosenFile.exists() && chosenFile.getAbsolutePath().endsWith(".xls")) {
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(chosenFile));
			HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowIndex = 0;
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); ++i) {
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell(1);
				if ("Prezime".equalsIgnoreCase(cell.getStringCellValue().trim())) {
					rowIndex = i + 1;
					break;
				}
			}

			for (int i = rowIndex; i < sheet.getPhysicalNumberOfRows(); ++i) {
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell(0);
				String brojIndeksa = cell.getStringCellValue().trim();

//				TODO - potreban StudentDAO za dobijanje informacija o ispitu
//				StudentNaTestuDTO tmp = new StudentNaTestuDTO(studentId, brojIndeksa, ime, prezime, 0, "");
//				data.add(tmp);


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
				Cell cell = row.getCell(1);
				if ("Prezime".equalsIgnoreCase(cell.getStringCellValue().trim())) {
					rowIndex = i + 1;
					break;
				}
			}

			for (int i = rowIndex; i < sheet.getPhysicalNumberOfRows(); ++i) {
				Row row = sheet.getRow(i);
				Cell cell = row.getCell(0);
				String brojIndeksa = cell.getStringCellValue().trim();
				System.out.println(brojIndeksa);
//				TODO - potreban StudentDAO za dobijanje informacija o ispitu
//				StudentNaTestuDTO tmp = new StudentNaTestuDTO(studentId, brojIndeksa, ime, prezime, 0, "");
//				data.add(tmp);


			}
			
			workbook.close();
			chosenFileInputStream.close();
		} else
			return null;
		
		return data;
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

	public void print(TestDTO test) throws IOException, DocumentException, PrinterException {
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

	public String getStatistika(TestDTO test) {
		//		TODO - dodati ukupan broj studenata na predmetu - potreban StudentDTO
		StringBuilder statisticsBuilder = new StringBuilder();

		int izaslo = test.getStudenti().size();
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

		statisticsBuilder.append("Izaslo: " + izaslo).append('\n');
		statisticsBuilder.append("Polozilo: " + polozilo
				+ String.format("(%.2f %%)", (double)polozilo / (double) izaslo * 100.0)).append('\n');
		statisticsBuilder.append("Palo: " + (izaslo - polozilo)
				+ String.format("(%.2f %%)", (double)(izaslo-polozilo) / (double)izaslo * 100.0)).append('\n');
		statisticsBuilder.append("Odlican-izuzetan (10): " + deset
				+ String.format("(%.2f %%)", (double)deset / (double)polozilo * 100.0)).append('\n');
		statisticsBuilder.append("Odlican (9): " + devet + String.format("(%.2f %%)", (double)devet / (double)polozilo * 100.0)).append('\n');
		statisticsBuilder.append("Vrlo dobar (8): " + osam + String.format("(%.2f %%)", (double)osam / (double)polozilo * 100.0)).append('\n');
		statisticsBuilder.append("Dobar (7): " + sedam + String.format("(%.2f %%)", (double)sedam / (double)polozilo * 100.0)).append('\n');
		statisticsBuilder.append("Zadovoljava (6): " + sest + String.format("(%.2f %%)", (double)sest / (double)polozilo * 100.0));

		return statisticsBuilder.toString();
	}
	/**
	 * NETESTIRANO ***********************
	 * @param test
	 * @param model
	 * @param searchText
	 */
	public void initiateStudentSearch(TestDTO test, StudentTableModel model, String searchText) {
		if (test == null || model == null || searchText == null)
			return;
		if ("".equals(searchText)) {
			model.setData(test.getStudenti());
			model.fireTableDataChanged();
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
				searchedList = this.filter(test, brojBodova, diskriminator);
			} else {
				searchedList = this.pretraga(test, searchText);
			}
		} else {
			try {
				int brojBodova = Integer.parseInt(searchText);
				searchedList = this.filter(test, brojBodova, "");
			} catch (NumberFormatException e) {
				searchedList = this.pretraga(test, searchText);
			}
		}
		
		model.setData(searchedList);
		model.fireTableDataChanged();
		
	}
	
	public void resetSearch(TestDTO test, StudentTableModel model, JTextField searchField) {
		if (test == null || model == null || searchField == null)
			return;
		List<StudentNaTestuDTO> data = test.getStudenti();
		test.setStudenti(data);
		model.setData(data);
		model.fireTableDataChanged();
		searchField.setText("");
	}
	
	public List<StudentNaTestuDTO> filter(TestDTO test, int brojBodova, String diskriminator) {
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
	
	public List<StudentNaTestuDTO> pretraga(TestDTO test, String query) {
		List<StudentNaTestuDTO> retList = new ArrayList<>();
		for (StudentNaTestuDTO student : test.getStudenti()) {
			if (student.getIme().startsWith(query) || student.getPrezime().startsWith(query) || student.getBrojIndeksa().startsWith(query))
				retList.add(student);
		}
		return retList;
	}

}
