package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.unibl.etf.ps.studentviewer.gui.StudentiZaElektrijaduTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.DodavanjeStudentaZaElektrijaduForm;
import org.unibl.etf.ps.studentviewer.gui.view.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class DodavanjeStudentaZaElektrijaduController {

	public DodavanjeStudentaZaElektrijaduController() {
		// TODO Auto-generated constructor stub
	}

	public void zatvoriProzor(ElektrijadaForm forma, WindowEvent e) {
		forma.setEnabled(true);
		e.getWindow().dispose();
	}

	public void dodajStudentaControl(DodavanjeStudentaZaElektrijaduForm dodavanjeForma, ElektrijadaForm forma,
			JTable tableStudenti, JTable tableStudentiPredmeti,
			StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel, ElektrijadaController kontroler) {
		int[] redovi = tableStudentiPredmeti.getSelectedRows();
		if (redovi.length == 0) {
			JOptionPane.showMessageDialog(dodavanjeForma, "Nije selektovan niti jedan student.");
		} else {
			ArrayList<StudentZaElektrijaduDTO> studentiZaUndoRedo = new ArrayList<>();
			for (int i : redovi) {
				StudentZaElektrijaduDTO st = new StudentZaElektrijaduDTO(
						(String) tableStudentiPredmeti.getValueAt(i, 0),
						(String) tableStudentiPredmeti.getValueAt(i, 1),
						(String) tableStudentiPredmeti.getValueAt(i, 2),
						(String) tableStudentiPredmeti.getValueAt(i, 3));
				if (kontroler.ubaciStudentaUListu(st)) {
					studentiZaUndoRedo.add(st);
				}
			}
			kontroler.dodavanjeStudenta(studentiZaUndoRedo);
			studentiZaElektrijaduDataModel.fireTableDataChanged();
			tableStudenti.setModel(studentiZaElektrijaduDataModel);
			tableStudenti.repaint();
			dodavanjeForma.dispose();
			forma.setEnabled(true);

		}
	}

	public void opcijaNazad(ElektrijadaForm forma, DodavanjeStudentaZaElektrijaduForm dodavanjeForma) {
		dodavanjeForma.setVisible(false);
		dodavanjeForma.dispose();
		forma.setEnabled(true);
	}
}
