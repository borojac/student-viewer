package org.unibl.etf.ps.studentviewer.logic.controller;

import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.view.DodavanjeDodatneNastaveForm;
import org.unibl.etf.ps.studentviewer.gui.view.IzborDatumaForm;



public class IzborDatumaController {
	public IzborDatumaController() {
		// TODO Auto-generated constructor stub
	}
	
	public void zatvoriProzor(JTextField textFieldDatum, DodavanjeDodatneNastaveForm nastavaForm, JFrame frame, IzborDatumaForm izborDatumaForma) {
		textFieldDatum.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(izborDatumaForma.getDate()));
		nastavaForm.setEnabled(true);
		frame.setVisible(false);
		frame.dispose();
	}
}
