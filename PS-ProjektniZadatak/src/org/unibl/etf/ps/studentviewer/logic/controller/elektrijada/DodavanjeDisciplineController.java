package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.DodavanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.AccountFormController;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ZahtjevDisciplinaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDisciplinaDTO;

public class DodavanjeDisciplineController {

	private DodavanjeDisciplineForm dodavanjeDisciplineForm;

	public DodavanjeDisciplineController(DodavanjeDisciplineForm dodavanjeDisciplineForm) {
		this.dodavanjeDisciplineForm = dodavanjeDisciplineForm;
	}

	public void slanjeZahtjeva(JComboBox elektrijadeCB, JComboBox disciplineCB) {
		if (disciplineCB.getItemCount() <= 0) {
			JOptionPane.showMessageDialog(dodavanjeDisciplineForm, "Selektujte disciplinu.");
		} else {
			MySQLDAOFactory dao = new MySQLDAOFactory();
			ElektrijadaDAO elektrijadaDAO = dao.getElektrijadaDAO();
			ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) elektrijadaDAO.getSveElektrijade();
			
			ElektrijadaDTO elektrijadaDTO = elektrijade.get(elektrijadeCB.getSelectedIndex());
			
			DisciplinaDTO disciplinaDTO = new DisciplinaDTO((String)disciplineCB.getSelectedItem(),
					elektrijadaDTO.getId());

			NalogDTO nalogDTO = dodavanjeDisciplineForm.getNalogDTO();

			ZahtjevDisciplinaDTO zahtjevDTO = new ZahtjevDisciplinaDTO(nalogDTO.getNalogId(), elektrijadaDTO.getId(), disciplinaDTO.getNaziv());
			
			MySQLDAOFactory zahtjevFactory = new MySQLDAOFactory();
			ZahtjevDisciplinaDAO zahtjevDAO = zahtjevFactory.getZahtjevDiciplinaDAO();

			if (zahtjevDAO.addZahtjev(zahtjevDTO)) {
				JOptionPane.showMessageDialog(dodavanjeDisciplineForm, "Zahtjev je uspje�no poslan.");
				AccountFormController.resetDodavanjeDisciplineFormOpened();
				dodavanjeDisciplineForm.dispose();
				
			} else {
				JOptionPane.showMessageDialog(dodavanjeDisciplineForm, "Zahtjev nije uspje�no poslan.");
			}

		}
	}

	

}
