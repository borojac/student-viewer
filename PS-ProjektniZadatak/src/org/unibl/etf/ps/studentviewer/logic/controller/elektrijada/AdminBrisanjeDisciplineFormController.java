package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminBrisanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.model.dao.DisciplinaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class AdminBrisanjeDisciplineFormController {

	private AdminBrisanjeDisciplineForm adminBrisanjeDisciplineForm;
	public AdminBrisanjeDisciplineFormController(AdminBrisanjeDisciplineForm adminBrisanjeDisciplineForm) {
		this.adminBrisanjeDisciplineForm = adminBrisanjeDisciplineForm;
	}

	public void obrisiDisciplinu(JComboBox elektrijadeCB, JComboBox disciplineCB) {
		if (elektrijadeCB.getSelectedIndex() == -1 || disciplineCB.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(adminBrisanjeDisciplineForm, "Selektujte disciplinu.");
		} else {
			MySQLDAOFactory dao = new MySQLDAOFactory();
			ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
			ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO.getSveElektrijade();
			int indeks = elektrijadeCB.getSelectedIndex();
			ElektrijadaDTO selektovanaElektrijada = elektrijade.get(indeks);
			DisciplinaDAO discDAO = dao.getDisciplinaDAO();
			ArrayList<DisciplinaDTO> discipline = (ArrayList<DisciplinaDTO>) discDAO.getDisciplinePoElektrijadi(selektovanaElektrijada.getId());
							
			DisciplinaDTO disciplinaDTO = discipline.get(disciplineCB.getSelectedIndex());
			

			if (discDAO.deleteDisciplinu(disciplinaDTO)){
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminBrisanjeDisciplineForm, "Uspjesno obrisana disciplina.");
					}
				});
				adminBrisanjeDisciplineForm.dispose();
			}else{
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminBrisanjeDisciplineForm, "Nije moguce obrisati disciplinu.");
					}
				});
			}

		}
	}

	public void zatvoriProzor() {
		adminBrisanjeDisciplineForm.dispose();
		
	}

}
