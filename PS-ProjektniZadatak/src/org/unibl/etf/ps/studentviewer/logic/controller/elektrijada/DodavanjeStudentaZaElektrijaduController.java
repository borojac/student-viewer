package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.DodavanjeStudentaZaElektrijaduForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.StudentiZaElektrijaduTableModel;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.StudentDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentZaElektrijaduDTO;

public class DodavanjeStudentaZaElektrijaduController {

	private DodavanjeStudentaZaElektrijaduForm dodavanjeForma;
	private ArrayList<StudentZaElektrijaduDTO> listaStudenataIzbor;

	public DodavanjeStudentaZaElektrijaduController(DodavanjeStudentaZaElektrijaduForm dodavanjeForma) {
		this.dodavanjeForma = dodavanjeForma;
		this.listaStudenataIzbor = new ArrayList<>();

	}

	public void zatvoriProzor(ElektrijadaForm forma, WindowEvent e) {
		forma.setEnabled(true);
		e.getWindow().dispose();
	}

	public void dodajStudentaControl(
			JTable tableStudenti, JTable tableStudentiPredmeti,
			StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel,ElektrijadaController kontroler ) {
		int[] redovi = tableStudentiPredmeti.getSelectedRows();
		if (redovi.length == 0) {
			JOptionPane.showMessageDialog(dodavanjeForma, "Nije selektovan niti jedan student.");
		} else {
			ArrayList<StudentZaElektrijaduDTO> studentiZaUndoRedo = new ArrayList<>();
			for (StudentZaElektrijaduDTO student : listaStudenataIzbor){
				for (int i : redovi){
					if (student.getIndeks().equals((String)tableStudentiPredmeti.getValueAt(i, 0))){
						if (kontroler.ubaciStudentaUListu(student))
							studentiZaUndoRedo.add(student);
					}
				}
			}
			if (!studentiZaUndoRedo.isEmpty())
				kontroler.dodavanjeStudenta(studentiZaUndoRedo);
			studentiZaElektrijaduDataModel.fireTableDataChanged();
			tableStudenti.setModel(studentiZaElektrijaduDataModel);
			tableStudenti.repaint();
			
			kontroler.getForma().setEnabled(true);
			dodavanjeForma.dispose();

		}
	}

	public void opcijaNazad(ElektrijadaForm forma, DodavanjeStudentaZaElektrijaduForm dodavanjeForma) {
		dodavanjeForma.setVisible(false);
		dodavanjeForma.dispose();
		forma.setEnabled(true);
	}

	public void setIzborStudenta(ElektrijadaController kontroler) {
		DAOFactory dao = new MySQLDAOFactory();
		StudentDAO dsDAO = dao.getStudentDAO();
		if(!this.listaStudenataIzbor.isEmpty())
			this.listaStudenataIzbor.clear();
		this.listaStudenataIzbor =new ArrayList<>( dsDAO.getIzborStudentaZaElektrijadu(kontroler.getNalogDTO().getNalogId(),kontroler.getDisciplinaDTO().getNaziv(),kontroler.getElektrijada().getId()));
		
	}

	public ArrayList<StudentZaElektrijaduDTO> getListaStudenataIzbor() {
		return listaStudenataIzbor;
	}

	public void setListaStudenataIzbor(ArrayList<StudentZaElektrijaduDTO> listaStudenataIzbor) {
		this.listaStudenataIzbor = listaStudenataIzbor;
	}
	
	
}
