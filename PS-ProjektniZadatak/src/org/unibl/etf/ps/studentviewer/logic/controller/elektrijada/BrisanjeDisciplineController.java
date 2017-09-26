package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.BrisanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.AccountFormController;
import org.unibl.etf.ps.studentviewer.model.dao.DisciplinaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDisciplinaDAO;
import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDisciplinaDTO;

public class BrisanjeDisciplineController {

	private BrisanjeDisciplineForm brisanjeDisciplineForm;

	public BrisanjeDisciplineController(BrisanjeDisciplineForm brisanjeDisciplineForm) {
		this.brisanjeDisciplineForm = brisanjeDisciplineForm;
	}

	public void ukloniDisciplinu(JComboBox elektrijadeCB, JComboBox disciplineCB) {
		if (elektrijadeCB.getSelectedIndex() == -1 || disciplineCB.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(brisanjeDisciplineForm, "Selektujte disciplinu.");
		} else {
			NalogDTO nalogDTO = brisanjeDisciplineForm.getNalogDTO();
			MySQLDAOFactory dao = new MySQLDAOFactory();
			ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
			ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO.getListuElektrijada(nalogDTO.getNalogId()); // umjesto
			int indeks = elektrijadeCB.getSelectedIndex();
			ElektrijadaDTO selektovanaElektrijada = elektrijade.get(indeks);
			DisciplinaDAO discDAO = dao.getDisciplinaDAO();
			ArrayList<DisciplinaDTO> discipline = (ArrayList<DisciplinaDTO>) discDAO
					.getDiscipline(selektovanaElektrijada.getId(), nalogDTO.getNalogId());// nalogDTO
																		// umjesto 2
			
			DisciplinaDTO disciplinaDTO = discipline.get(disciplineCB.getSelectedIndex());

			MySQLDAOFactory factory = new MySQLDAOFactory();
			NalogDAO nalogDAO = factory.getNalogDAO();
			ZahtjevDisciplinaDAO zahtjevDAO = factory.getZahtjevDiciplinaDAO();
			
			if (zahtjevDAO.deleteZahtjevPoNaloguIDisciplini(nalogDTO,disciplinaDTO) &&  nalogDAO.removeDisciplina(disciplinaDTO, nalogDTO)) {
				JOptionPane.showMessageDialog(brisanjeDisciplineForm, "Disciplina uspješno uklonjena.");
				brisanjeDisciplineForm.getMainForm().resetElektrijadaComboBox();
				brisanjeDisciplineForm.dispose();
				AccountFormController.resetBrisanjeDisciplineFormOpened();
			} else {
				JOptionPane.showMessageDialog(brisanjeDisciplineForm, "Disciplina nije uspješno uklonjena.");
			}

		}

	}

}
