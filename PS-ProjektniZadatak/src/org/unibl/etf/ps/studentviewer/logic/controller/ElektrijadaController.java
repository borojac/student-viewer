package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;
import org.unibl.etf.ps.studentviewer.gui.DodatnaNastavaDataTableModel;
import org.unibl.etf.ps.studentviewer.gui.StudentiZaElektrijaduTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.DodavanjeDodatneNastaveForm;
import org.unibl.etf.ps.studentviewer.gui.view.DodavanjeStudentaZaElektrijaduForm;
import org.unibl.etf.ps.studentviewer.gui.view.EditorZaElektrijaduForm;
import org.unibl.etf.ps.studentviewer.gui.view.EksportStudentiZaElektrijaduForm;
import org.unibl.etf.ps.studentviewer.gui.view.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.logic.command.BrisanjeDodatneNastaveCommand;
import org.unibl.etf.ps.studentviewer.logic.command.BrisanjeStudentaZaElektrijaduCommand;
import org.unibl.etf.ps.studentviewer.logic.command.Command;
import org.unibl.etf.ps.studentviewer.logic.command.DodavanjeDodatneNastaveCommand;
import org.unibl.etf.ps.studentviewer.logic.command.DodavanjeStudentaZaElektrijaduCommand;
import org.unibl.etf.ps.studentviewer.logic.command.IzmjenaDatumaDodatneNastaveCommand;
import org.unibl.etf.ps.studentviewer.logic.command.IzmjenaNapomeneDodatneNastaveCommand;
import org.unibl.etf.ps.studentviewer.logic.command.IzmjenaNazivaDodatneNastaveCommand;
import org.unibl.etf.ps.studentviewer.logic.command.IzmjenaPodatkaNapomenaStudentaZaElektrijadu;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class ElektrijadaController {
	private ElektrijadaForm forma;
	private ElektrijadaDTO elektrijada;
	private NalogDTO nalogDTO;
	private Stack<Command> undoKomande = new Stack<Command>();
	private Stack<Command> redoKomande = new Stack<Command>();
	public static ArrayList<DodatnaNastavaDTO> listaDodatnihNastava = new ArrayList<>();
	public static ArrayList<StudentZaElektrijaduDTO> listaStudenata = new ArrayList<>();

	public ElektrijadaController(ElektrijadaForm forma, ElektrijadaDTO elektrijada, NalogDTO nalogDTO) {
		this.forma = forma;
		this.elektrijada = elektrijada;
		this.nalogDTO = nalogDTO;
	}

	public ElektrijadaDTO getElektrijada() {
		return elektrijada;
	}
	
	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}

	public ElektrijadaForm getForma() {
		return forma;
	}

	public void undo(int broj) {
		for (int i = 0; i < broj; i++) {
			if (!undoKomande.isEmpty()) {
				Command komanda = undoKomande.pop();
				komanda.unExecute();
				redoKomande.push(komanda);
			}
		}
	}

	public void redo(int broj) {
		for (int i = 0; i < broj; i++) {
			if (!redoKomande.isEmpty()) {
				Command komanda = redoKomande.pop();
				komanda.execute();
				undoKomande.push(komanda);
			}
		}
	}

	public void izmjenaNazivaDodatneNastave(DodatnaNastavaDTO dodatnaNastava, String noviNaziv) {
		Command komanda = new IzmjenaNazivaDodatneNastaveCommand(dodatnaNastava, noviNaziv);
		undoKomande.add(komanda);
		redoKomande.clear();
	}

	public void izmjenaNapomeneDodatneNastave(DodatnaNastavaDTO dodatnaNastava, String novaNapomena) {
		Command komanda = new IzmjenaNapomeneDodatneNastaveCommand(dodatnaNastava, novaNapomena);
		undoKomande.add(komanda);
		redoKomande.clear();
	}

	public void izmjenaDatumaDodatneNastave(DodatnaNastavaDTO dodatnaNastava, Date datum) {
		Command komanda = new IzmjenaDatumaDodatneNastaveCommand(dodatnaNastava, datum);
		undoKomande.add(komanda);
		redoKomande.clear();
	}

	public void izmjenaPodatkaNapomena(StudentZaElektrijaduDTO student, String novaNapomena) {
		Command komanda = new IzmjenaPodatkaNapomenaStudentaZaElektrijadu(student, novaNapomena);
		undoKomande.add(komanda);
		redoKomande.clear();
	}

	public void dodavanjeNastave(DodatnaNastavaDTO nt) {
		Command komanda = new DodavanjeDodatneNastaveCommand(nt);
		undoKomande.add(komanda);
		redoKomande.clear();
	}

	public void brisanjeNastave(DodatnaNastavaDTO dodatnaNastava) {
		Command komanda = new BrisanjeDodatneNastaveCommand(dodatnaNastava);
		undoKomande.add(komanda);
		redoKomande.clear();
	}

	public void dodavanjeStudenta(ArrayList<StudentZaElektrijaduDTO> st) {
		Command komanda = new DodavanjeStudentaZaElektrijaduCommand(st);
		undoKomande.add(komanda);
		redoKomande.clear();
	}

	public void brisanjeStudenta(ArrayList<StudentZaElektrijaduDTO> st) {
		Command komanda = new BrisanjeStudentaZaElektrijaduCommand(st);
		undoKomande.add(komanda);
		redoKomande.clear();
	}

	public void otvaranjeEditora(MouseEvent e, AbstractTableModel dataModel, boolean b) {
		ElektrijadaController controller = this;
		JTable target = (JTable) e.getSource();
		int row = target.getSelectedRow();
		int col = target.getSelectedColumn();
		if (col != 3 && b == true)
			return;
		String sadrzajEditora = (String) target.getValueAt(row, col);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				forma.setEnabled(false);
				EditorZaElektrijaduForm frame = new EditorZaElektrijaduForm(target, dataModel, controller,
						sadrzajEditora, b);
				frame.setVisible(true);
			}
		});

	}

	public boolean validnostDatuma(String vrijednost) {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			Date datum = sdf.parse(vrijednost);
			if (!vrijednost.equals(sdf.format(datum)))
				return false;
			return true;
		} catch (ParseException e) {

			return false;

		}
	}

	public boolean izbaciNastavuIzListe(String naziv, String datum, String napomena) {
		for (DodatnaNastavaDTO dodatnaNastava : listaDodatnihNastava) {
			if (dodatnaNastava.getNaziv().equals(naziv) && dodatnaNastava.getDatum().equals(datum)
					&& dodatnaNastava.getNapomena().equals(napomena)) {
				listaDodatnihNastava.remove(dodatnaNastava);
				return true;
			}
		}
		return false;
	}

	public boolean ubaciStudentaUListu(StudentZaElektrijaduDTO student) {
		for (StudentZaElektrijaduDTO s : listaStudenata) {
			if (s.getIndeks().equals(student.getIndeks())) {
				return false;
			}
		}
		listaStudenata.add(student);
		return true;
	}

	public boolean izbaciStudentaProvjera(StudentZaElektrijaduDTO student) {
		for (StudentZaElektrijaduDTO s : listaStudenata) {
			if (s.getIndeks().equals(student.getIndeks())) {
				return true;
			}
		}
		return false;
	}

	public void izbaciListuIzListe(ArrayList<StudentZaElektrijaduDTO> listaUndoRedo) {
		for (StudentZaElektrijaduDTO st : listaUndoRedo) {
			listaStudenata.remove(st);
		}

	}

	public void brisanjeListeControl(JTable tableStudenti,
			StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel) {
		int redovi = tableStudenti.getRowCount();
		ArrayList<StudentZaElektrijaduDTO> listaUndoRedo = new ArrayList<>();

		for (int i = 0; i < redovi; i++) {
			listaUndoRedo.add(listaStudenata.get(i));
		}
		this.izbaciListuIzListe(listaUndoRedo);
		this.brisanjeStudenta(listaUndoRedo);
		studentiZaElektrijaduDataModel.fireTableDataChanged();
		tableStudenti.setModel(studentiZaElektrijaduDataModel);
		tableStudenti.repaint();

	}

	public void brisanjeNastaveControl( JTable tableNastavneTeme,
			DodatnaNastavaDataTableModel dodatnaNastavaDataModel) {
		int row = tableNastavneTeme.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(forma, "Nije selektovana ni jedna dodatna nastava.");
		} else {
			this.brisanjeNastave(listaDodatnihNastava.get(row));
			listaDodatnihNastava.remove(row);
			dodatnaNastavaDataModel.fireTableDataChanged();
			tableNastavneTeme.setModel(dodatnaNastavaDataModel);
			tableNastavneTeme.repaint();

		}

	}

	public void brisanjeStudentaControl( JTable tableStudenti,
			StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel) {
		int[] redovi = tableStudenti.getSelectedRows();
		if (redovi.length == 0) {
			JOptionPane.showMessageDialog(forma, "Nije selektovan ni jedan student.");
		} else {
			ArrayList<StudentZaElektrijaduDTO> listaUndoRedo = new ArrayList<>();
			for (int i : redovi) {
					listaUndoRedo.add(listaStudenata.get(i));				
			}
			this.izbaciListuIzListe(listaUndoRedo);
			this.brisanjeStudenta(listaUndoRedo);
			studentiZaElektrijaduDataModel.fireTableDataChanged();
			tableStudenti.setModel(studentiZaElektrijaduDataModel);
			tableStudenti.repaint();
		}
	}

	public void dodavanjeNastaveControl( JTable tableNastavneTeme,
			DodatnaNastavaDataTableModel dodatnaNastavaDataModel) {
		ElektrijadaController kontroler = this;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				forma.setEnabled(false);
				DodavanjeDodatneNastaveForm frame = new DodavanjeDodatneNastaveForm(tableNastavneTeme, kontroler,
						dodatnaNastavaDataModel);
				frame.setVisible(true);
			}
		});

	}

	public void dodavanjeStudentaControl( JTable tableStudenti,
			StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel) {
		ElektrijadaController kontroler = this;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				forma.setEnabled(false);
				DodavanjeStudentaZaElektrijaduForm frame = new DodavanjeStudentaZaElektrijaduForm(forma, tableStudenti,
						kontroler, studentiZaElektrijaduDataModel);
				frame.setVisible(true);
				studentiZaElektrijaduDataModel.fireTableDataChanged();
				tableStudenti.setModel(studentiZaElektrijaduDataModel);
				tableStudenti.repaint();

			}
		});

	}

	public void undoOpcija(DodatnaNastavaDataTableModel dodatnaNastavaDataModel, JTable tableDodatneNastave,
			StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel, JTable tableStudenti) {
		this.undo(1);
		dodatnaNastavaDataModel.fireTableDataChanged();
		tableDodatneNastave.repaint();
		studentiZaElektrijaduDataModel.fireTableDataChanged();
		tableStudenti.repaint();
	}

	public void redoOpcija(DodatnaNastavaDataTableModel dodatnaNastavaDataModel, JTable tableDodatneNastave,
			StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel, JTable tableStudenti) {
		this.redo(1);
		dodatnaNastavaDataModel.fireTableDataChanged();
		tableDodatneNastave.repaint();
		studentiZaElektrijaduDataModel.fireTableDataChanged();
		tableStudenti.repaint();
	}

	public void zatvoriProzor(ElektrijadaForm forma) {

	}

	public void exportPdf(Logger logger) {
		ElektrijadaController kontroler = this;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				forma.setEnabled(false);
				EksportStudentiZaElektrijaduForm eksportForma = new EksportStudentiZaElektrijaduForm(kontroler, logger);
				eksportForma.setVisible(true);
			}
		});
		
	}

	public void exportStampac() throws DocumentException, InvalidPasswordException, IOException, PrinterException {
		Font font = FontFactory.getFont("fonts/tahoma.ttf", BaseFont.IDENTITY_H, 12);
		Document doc = new Document();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		Paragraph title = new Paragraph();
		title.setIndentationLeft(60f);
		title.setFont(font);
		title.add("Elektrijada - "+this.getElektrijada().getLokacija());
		title.add("\n\n");
		title.add("Datum: " + new SimpleDateFormat("dd.MM.yyyy").format(this.getElektrijada().getDatum()));
		title.add("\n\n");
		
		Paragraph spacing = new Paragraph("\n\n");
		spacing.add("Studenti za Elektrijadu:");
		spacing.add("\n\n");
		
		Paragraph body = new Paragraph();
		body.setIndentationLeft(60f);
		body.setFont(font);
		body.setTabSettings(new TabSettings());
		for (StudentZaElektrijaduDTO student : this.listaStudenata) {
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

		PDDocument printDoc = PDDocument.load(os.toByteArray());

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(new PDFPageable(printDoc));
		if (job.printDialog()) {
			job.print();
		}
		printDoc.close();
		
	}

}
