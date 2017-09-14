/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.view.ExportStudentsForm;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;

public class ChooseExportTypeController {
	MainFormController mainFormController = null;

	public ChooseExportTypeController(MainFormController mainFormController, boolean print, boolean pdf,
			ExportStudentsForm form) throws IOException, DocumentException, PrinterException {

		this.mainFormController = mainFormController;
		ArrayList<String[]> podaci = new ArrayList<String[]>();
		MainTable table = mainFormController.getMainTable();
		int columns = table.getColumnCount();
		int rows = table.getRowCount();

		String header[] = new String[columns];
		for (int i = 0; i < columns; i++)
			header[i] = table.getColumnName(i);

		for (int i = 0; i < rows; i++) {
			String tmp[] = new String[columns];
			for (int j = 0; j < columns; j++) {
				tmp[j] = (String) table.getValueAt(i, j);
			}
			podaci.add(tmp);
		}

		if (!(print || pdf)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(null, message,"Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
			form.setVisible(true);
		} else if (print) {
			print(podaci, header);
			mainFormController.resetExporting();

		} else {
			savePDF(podaci, header);
			mainFormController.resetExporting();
		}

	}

	public void print(ArrayList<String[]> studentiTabela, String[] header)
			throws IOException, DocumentException, PrinterException {

		Font font = FontFactory.getFont("fonts/tahoma.ttf", BaseFont.IDENTITY_H, 12);
		Document doc = new Document();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		if(header.length > 4)
			writer.addPageDictEntry(PdfName.DEFAULT, PdfPage.LANDSCAPE);
		
		Paragraph title = new Paragraph();
		title.setIndentationLeft(60f);
		title.setFont(font);
		for (int i = 0; i < header.length; i++) {
			title.add(header[i]);
			title.setTabSettings(new TabSettings(80));
			title.add(Chunk.TABBING);
		}


		Paragraph spacing = new Paragraph(System.lineSeparator());

		Paragraph body = new Paragraph();
		body.setIndentationLeft(60f);
		body.setFont(font);
		body.setTabSettings(new TabSettings(80));
		for (String[] student : studentiTabela) {
			for (int i = 0; i < student.length; i++) {
				body.add(student[i]);
					body.add(Chunk.TABBING);
			}			
			body.add(System.lineSeparator());
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

	public void savePDF(ArrayList<String[]> studentiTabela, String[] header) throws DocumentException, IOException {
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
		if(header.length > 4)
			writer.addPageDictEntry(PdfName.DEFAULT, PdfPage.LANDSCAPE);

		Paragraph title = new Paragraph();
		title.setIndentationLeft(60f);
		title.setFont(font);
		for (int i = 0; i < header.length; i++) {
			title.add(header[i]);
			title.setTabSettings(new TabSettings(80));
			title.add(Chunk.TABBING);
		}

		Paragraph spacing = new Paragraph(System.lineSeparator());

		Paragraph body = new Paragraph();
		body.setIndentationLeft(60f);
		body.setFont(font);
		body.setTabSettings(new TabSettings(80));
		for (String[] student : studentiTabela) {
			for (int i = 0; i < student.length; i++) {
				body.add(student[i]);
					body.add(Chunk.TABBING);
			}			
			body.add(System.lineSeparator());
		}
		doc.open();
		doc.add(title);
		doc.add(spacing);
		doc.add(body);
		doc.close();
		writer.flush();
		writer.close();
		os.close();
		
		final String message = "Uspjesno cuvanje PDF dokumenta!";
		JOptionPane.showMessageDialog(null, message,"Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
		}

}
