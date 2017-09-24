package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.DodavanjeDodatneNastaveForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.IzborDatumaZaDodatnuNastavuForm;

public class IzborDatumaZaDodatnuNastavuController {
	private IzborDatumaZaDodatnuNastavuForm izborDatumaForma;

	public IzborDatumaZaDodatnuNastavuController(IzborDatumaZaDodatnuNastavuForm izborDatumaForma) {
		this.izborDatumaForma = izborDatumaForma;
	}

	public void zatvoriProzor(JTextField textFieldDatum, DodavanjeDodatneNastaveForm nastavaForm, JFrame frame) {
		textFieldDatum.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(izborDatumaForma.getDate()));
		nastavaForm.setEnabled(true);
		frame.setVisible(false);
		frame.dispose();
	}
}
