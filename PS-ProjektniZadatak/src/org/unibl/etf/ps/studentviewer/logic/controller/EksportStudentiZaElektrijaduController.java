package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.EksportStudentiZaElektrijaduForm;
import org.unibl.etf.ps.studentviewer.gui.view.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class EksportStudentiZaElektrijaduController {

	private EksportStudentiZaElektrijaduForm eksportForma;
	
	public EksportStudentiZaElektrijaduController(EksportStudentiZaElektrijaduForm eksportForma) {
		this.eksportForma = eksportForma;
	}

	public void kreirajFajl(String nazivFajla, ElektrijadaController elektrijadaKontroler) throws Exception {
		
		if (!nazivFajla.endsWith(".pdf"))
			nazivFajla = nazivFajla+".pdf";
		File chosenFile = new File(nazivFajla);
		if (chosenFile.exists()) throw new Exception();
		
		Font font = FontFactory.getFont("fonts/tahoma.ttf", BaseFont.IDENTITY_H, 12);
		Document doc = new Document();
		OutputStream os = new FileOutputStream(chosenFile);
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		
		Paragraph title = new Paragraph();
		title.setIndentationLeft(60f);
		title.setFont(font);
		title.add("Elektrijada - "+elektrijadaKontroler.getElektrijada().getLokacija());
		title.add("\n\n");
		title.add("Datum: " + new SimpleDateFormat("dd.MM.yyyy").format(elektrijadaKontroler.getElektrijada().getDatum()));
		title.add("\n\n");
		
		Paragraph spacing = new Paragraph("\n\n");
		spacing.add("Studenti za Elektrijadu:");
		spacing.add("\n\n");
		
		Paragraph body = new Paragraph();
		body.setIndentationLeft(60f);
		body.setFont(font);
		body.setTabSettings(new TabSettings());
		for (StudentZaElektrijaduDTO student : elektrijadaKontroler.listaStudenata) {
			final String studentString = student.getIndeks() + " " + student.getIme() + " " + student.getPrezime()+" "+student.getNapomena();
			body.add(new Chunk(studentString));
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
		JOptionPane.showMessageDialog(eksportForma, "Uspjesno kreiran fajl.");
		this.opcijaNazad(elektrijadaKontroler);
	}

	

	public void opcijaNazad(ElektrijadaController elektrijadaKontroler) {
		eksportForma.setVisible(false);
		eksportForma.dispose();
		elektrijadaKontroler.getForma().setEnabled(true);
		
	}

	public void zatvoriProzor(ElektrijadaForm elektrijadaForm, WindowEvent e) {
		elektrijadaForm.setEnabled(true);
		e.getWindow().dispose();
		
	}
	
}
