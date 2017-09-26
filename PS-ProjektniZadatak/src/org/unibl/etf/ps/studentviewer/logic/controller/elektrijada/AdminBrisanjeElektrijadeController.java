package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminBrisanjeElektrijadeForm;
import org.unibl.etf.ps.studentviewer.model.dao.DisciplinaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.DodatnaNastavaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dao.StudentDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDisciplinaDAO;
import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class AdminBrisanjeElektrijadeController {

	private AdminBrisanjeElektrijadeForm adminBrisanjeElektrijadeForm;

	public AdminBrisanjeElektrijadeController(AdminBrisanjeElektrijadeForm adminBrisanjeElektrijadeForm) {
		this.adminBrisanjeElektrijadeForm = adminBrisanjeElektrijadeForm;
	}

	public void zatvoriProzor() {
		adminBrisanjeElektrijadeForm.dispose();

	}

	public void obrisiElektrijadu(JComboBox elektrijadeCB) {
		if (elektrijadeCB.getItemCount() > 0) {
			MySQLDAOFactory dao = new MySQLDAOFactory();
			ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
			ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO.getSveElektrijade();
			ElektrijadaDTO elektrijadaDTO = elektrijade.get(elektrijadeCB.getSelectedIndex());
			DisciplinaDAO discDAO = dao.getDisciplinaDAO();
			ArrayList<DisciplinaDTO> disciplinePoEl = (ArrayList<DisciplinaDTO>) discDAO
					.getDisciplinePoElektrijadi(elektrijadaDTO.getId());
			if (disciplinePoEl.size() == 0) {
				if (eleDAO.deleteElektrijada(elektrijadaDTO)) {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminBrisanjeElektrijadeForm,
									"Uspješno obrisana Elektrijada.");
						}
					});
					adminBrisanjeElektrijadeForm.dispose();
				} else {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminBrisanjeElektrijadeForm,
									"Neuspješno obrisana Elektrijada.");
						}
					});
				}

			} else {

				NalogDAO nalogDao = dao.getNalogDAO();
				StudentDAO studentDao = dao.getStudentDAO();
				ZahtjevDisciplinaDAO zatjevDao = dao.getZahtjevDiciplinaDAO();
				DodatnaNastavaDAO dodatnaNastavaDao = dao.getDodatnaNastavaDAO();
				dodatnaNastavaDao.obrisiDodatnuNastavuPoElektrijadi(elektrijadaDTO.getId());
				zatjevDao.deleteZahtjevPoElektrijadi(elektrijadaDTO.getId());

				nalogDao.ukloniZaduzenja(elektrijadaDTO.getId());

				studentDao.ukloniUcesce(elektrijadaDTO.getId());

				for (DisciplinaDTO d : disciplinePoEl) {
					discDAO.deleteDisciplinu(d);
				}
				if (eleDAO.deleteElektrijada(elektrijadaDTO)) {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminBrisanjeElektrijadeForm,
									"Uspješno obrisana Elektrijada.");
						}
					});
					adminBrisanjeElektrijadeForm.dispose();
				} else {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminBrisanjeElektrijadeForm,
									"Neuspješno obrisana Elektrijada.");
						}
					});
				}

			}
		} else {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					JOptionPane.showMessageDialog(adminBrisanjeElektrijadeForm, "Prazna lista.");
				}
			});
		}
	}

}
