package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.view.DodavanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDisciplinaDAO;
import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDisciplinaDTO;

public class DodavanjeDisciplineController {

	private DodavanjeDisciplineForm dodavanjeDisciplineForm;

	public DodavanjeDisciplineController(DodavanjeDisciplineForm dodavanjeDisciplineForm) {
		this.dodavanjeDisciplineForm = dodavanjeDisciplineForm;
	}

	public void slanjeZahtjeva(JComboBox elektrijadeCB, JTextField textField) {
		if (textField.getText().length() == 0) {
			JOptionPane.showMessageDialog(dodavanjeDisciplineForm, "Unesite naziv discipline.");
		} else {
			MySQLDAOFactory dao = new MySQLDAOFactory();
			ElektrijadaDAO elektrijadaDAO = dao.getElektrijadaDAO();
			ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) elektrijadaDAO.getSveElektrijade();
			
			ElektrijadaDTO elektrijadaDTO = elektrijade.get(elektrijadeCB.getSelectedIndex());
			
			DisciplinaDTO disciplinaDTO = new DisciplinaDTO(textField.getText(),
					elektrijadaDTO.getId());

			NalogDTO nalogDTO = dodavanjeDisciplineForm.getNalogDTO();

			ZahtjevDisciplinaDTO zahtjevDTO = new ZahtjevDisciplinaDTO(nalogDTO.getNalogId(), elektrijadaDTO.getId(), textField.getText());
			System.out.println(zahtjevDTO);
			MySQLDAOFactory zahtjevFactory = new MySQLDAOFactory();
			ZahtjevDisciplinaDAO zahtjevDAO = zahtjevFactory.getZahtjevDiciplinaDAO();

			if (zahtjevDAO.addZahtjev(zahtjevDTO)) {
				JOptionPane.showMessageDialog(dodavanjeDisciplineForm, "Zahtjev je uspjesno poslan.");
				dodavanjeDisciplineForm.dispose();
				AccountFormController.resetDodavanjeDisciplineFormOpened();
			} else {
				JOptionPane.showMessageDialog(dodavanjeDisciplineForm, "Zahtjev nije uspjesno poslan.");
			}

		}
	}

	

}
