package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.DodatnaNastavaDataTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.DodavanjeDodatneNastaveForm;
import org.unibl.etf.ps.studentviewer.gui.view.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.gui.view.IzborDatumaForm;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;


public class DodavanjeDodatneNastaveController {
	public DodavanjeDodatneNastaveController() {
		// TODO Auto-generated constructor stub
	}
	public void zatvoriProzor(ElektrijadaForm forma, WindowEvent e) {
		forma.setEnabled(true);
		e.getWindow().dispose();
	}

	public void dodajStudentaControl(JTextField textFieldNaziv, JTextField textFieldDatum, JTextArea textAreaNapomena,
			DodavanjeDodatneNastaveForm nastavaForm, ElektrijadaForm forma, JTable tableNastavneTeme,
			ElektrijadaController kontroler, DodatnaNastavaDataTableModel dodatnaNastavaDataModel) {
		String naziv = textFieldNaziv.getText();
		String datum = textFieldDatum.getText();
		String napomena = textAreaNapomena.getText();

		if (kontroler.validnostDatuma(datum)) {
			DodatnaNastavaDTO nastava = new DodatnaNastavaDTO(naziv, datum, napomena);
			if (kontroler.listaDodatnihNastava.add(nastava)) {
				dodatnaNastavaDataModel.fireTableDataChanged();
				tableNastavneTeme.setModel(dodatnaNastavaDataModel);
				tableNastavneTeme.repaint();
				kontroler.dodavanjeNastave(nastava);
				nastavaForm.setVisible(false);
				nastavaForm.dispose();
				forma.setEnabled(true);
			}
		} else {
			JOptionPane.showMessageDialog(nastavaForm, "Greska u formi datuma.");
		}

	}

	public void izborDatuma(DodavanjeDodatneNastaveForm nastavaForm, JTextField textFieldDatum) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				nastavaForm.setEnabled(false);
				IzborDatumaForm izborDatumaForm = new IzborDatumaForm(textFieldDatum, nastavaForm);
			}
		});
	}
}
