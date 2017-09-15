package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.gui.view.EditorZaElektrijaduForm;
import org.unibl.etf.ps.studentviewer.gui.view.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.DodatnaNastavaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class EditorZaElektrijaduController {
	private EditorZaElektrijaduForm editorForma;
	
	public EditorZaElektrijaduController(EditorZaElektrijaduForm editorForma) {
		this.editorForma = editorForma;
	}

	public void zatvoriProzor(WindowEvent e, ElektrijadaForm forma) {
		forma.setEnabled(true);
		e.getWindow().dispose();
	}

	public void izvrsiIzmjene(boolean izbor, ElektrijadaController elektrijadaController, JTable target,
			JTextArea textArea, AbstractTableModel dataModel) {
		if (!izbor) {
			String izmjena = "";
			String procitano = textArea.getText();
			DAOFactory dao = new MySQLDAOFactory();
			DodatnaNastavaDAO dnDAO = dao.getDodatnaNastavaDAO();
			if (procitano.length() > 0) {
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();
				DodatnaNastavaDTO dodatnaNastava = elektrijadaController.getListaDodatnihNastava().get(row);
				if (column == 0) {

					elektrijadaController.izmjenaNazivaDodatneNastave(dodatnaNastava, procitano);
				} else if (column == 1) {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
					Date startDate = null;
					try {
						startDate = df.parse(procitano);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (elektrijadaController.validnostDatuma(procitano)) {

						elektrijadaController.izmjenaDatumaDodatneNastave(dodatnaNastava, startDate);
					} else
						JOptionPane.showMessageDialog(editorForma, "Greska u formi datuma.");
				} else if (column == 2) {

					elektrijadaController.izmjenaNapomeneDodatneNastave(dodatnaNastava, procitano);
				}

				target.setModel(dataModel);
				editorForma.setVisible(false);
				editorForma.dispose();
				elektrijadaController.getForma().setEnabled(true);
			}
		} else {
			String izmjena = "";
			String procitano = textArea.getText();
			if (procitano.length() > 0) {
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();
				StudentZaElektrijaduDTO st = elektrijadaController.getListaStudenata().get(row);
				if (column == 3) {
					elektrijadaController.izmjenaPodatkaNapomena(st, procitano);
				}

				target.setModel(dataModel);
				editorForma.setVisible(false);
				editorForma.dispose();
				elektrijadaController.getForma().setEnabled(true);
			}
		}
	}
}
