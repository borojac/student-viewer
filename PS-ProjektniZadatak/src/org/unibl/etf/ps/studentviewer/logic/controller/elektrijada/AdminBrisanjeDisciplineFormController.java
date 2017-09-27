package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminBrisanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminElektrijadaForm;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.DisciplinaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.DodatnaNastavaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.StudentDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ZahtjevDisciplinaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;

public class AdminBrisanjeDisciplineFormController {

	private AdminBrisanjeDisciplineForm adminBrisanjeDisciplineForm;
	private AdministratorElektrijadaFormController adminElektFormKontroler;
	public AdminBrisanjeDisciplineFormController(AdminBrisanjeDisciplineForm adminBrisanjeDisciplineForm, AdministratorElektrijadaFormController adminElektFormKontroler) {
		this.adminBrisanjeDisciplineForm = adminBrisanjeDisciplineForm;
		this.adminElektFormKontroler = adminElektFormKontroler;
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
			ZahtjevDisciplinaDAO zahtjevDAO = dao.getZahtjevDiciplinaDAO();
			zahtjevDAO.deleteZahtjevPoDisciplini(disciplinaDTO);
			DodatnaNastavaDAO dodatnaNastavaDao = dao.getDodatnaNastavaDAO();
			dodatnaNastavaDao.obrisiDodatnuNastavuPoDisciplini(disciplinaDTO);
			NalogDAO nalogDao = dao.getNalogDAO();
			nalogDao.ukloniZaduzenjaPoDisciplini(disciplinaDTO);
			StudentDAO studentDao = dao.getStudentDAO();
			studentDao.ukloniUcescePoDisciplini(disciplinaDTO);

			if (discDAO.deleteDisciplinu(disciplinaDTO)){
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminBrisanjeDisciplineForm, "Uspješno obrisana disciplina.");
					}
				});
				adminElektFormKontroler.getAdminElektrijadaForm().resetujStanje();
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
