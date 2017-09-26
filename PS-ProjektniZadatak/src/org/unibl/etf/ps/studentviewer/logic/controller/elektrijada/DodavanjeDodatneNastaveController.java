package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.DodatnaNastavaDataTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.DodavanjeDodatneNastaveForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.IzborDatumaZaDodatnuNastavuForm;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.DodatnaNastavaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DodatnaNastavaDTO;

public class DodavanjeDodatneNastaveController {
	private DodavanjeDodatneNastaveForm nastavaForm;
	public DodavanjeDodatneNastaveController(DodavanjeDodatneNastaveForm nastavaForm) {
		this.nastavaForm = nastavaForm;
	}

	public void zatvoriProzor(ElektrijadaForm forma, WindowEvent e) {
		forma.setEnabled(true);
		e.getWindow().dispose();
	}

	public void dodajDodatnuNastavuControl(JTextField textFieldNaziv, JTextField textFieldDatum, JTextArea textAreaNapomena,
			  JTable tableNastavneTeme,
			ElektrijadaController kontroler, DodatnaNastavaDataTableModel dodatnaNastavaDataModel) {
		String naziv = textFieldNaziv.getText();
		String datum = textFieldDatum.getText();
		String napomena = textAreaNapomena.getText();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		Date startDate = null;
		try {
			startDate = df.parse(datum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (kontroler.validnostDatuma(datum)) {
			//dodatnaNastavaDataModel.setDateFormat(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy"));
			DodatnaNastavaDTO nastava = new DodatnaNastavaDTO(0,startDate,napomena,naziv,kontroler.getNalogDTO().getNalogId(),kontroler.getDisciplinaDTO().getNaziv(),kontroler.getElektrijada().getId());
			if (kontroler.getListaDodatnihNastava().add(nastava)) {
				dodatnaNastavaDataModel.fireTableDataChanged();
				tableNastavneTeme.setModel(dodatnaNastavaDataModel);
				tableNastavneTeme.repaint();
				kontroler.dodavanjeNastave(nastava);
				nastavaForm.dispose();
				kontroler.getForma().setEnabled(true);
			}
		} else {
			JOptionPane.showMessageDialog(nastavaForm, "Greška u formi datuma.");
		}

	}

	public void izborDatuma(DodavanjeDodatneNastaveForm nastavaForm, JTextField textFieldDatum) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				nastavaForm.setEnabled(false);
				IzborDatumaZaDodatnuNastavuForm izborDatumaForm = new IzborDatumaZaDodatnuNastavuForm(textFieldDatum,
						nastavaForm);
			}
		});
	}
}
