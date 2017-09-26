package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminDodavanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.DisciplinaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ElektrijadaDTO;

public class AdminDodavanjeDisciplineController {

	private AdminDodavanjeDisciplineForm adminDodavanjeDisciplineForm;

	public AdminDodavanjeDisciplineController(AdminDodavanjeDisciplineForm adminDodavanjeDisciplineForm) {
		this.adminDodavanjeDisciplineForm = adminDodavanjeDisciplineForm;
	}

	public void dodajDisciplinu(JComboBox elektrijadeCB, JTextField textFieldNazivDiscipline) {
		if (elektrijadeCB.getItemCount() > 0) {
			if (textFieldNazivDiscipline.getText().length() > 0) {

				MySQLDAOFactory dao = new MySQLDAOFactory();
				ElektrijadaDAO elektrijadaDAO = dao.getElektrijadaDAO();
				DisciplinaDAO disciplinaDAO = dao.getDisciplinaDAO();
				ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) elektrijadaDAO.getSveElektrijade();

				ElektrijadaDTO elektrijadaDTO = elektrijade.get(elektrijadeCB.getSelectedIndex());

				DisciplinaDTO disciplinaDTO = new DisciplinaDTO(textFieldNazivDiscipline.getText(),
						elektrijadaDTO.getId());
				
				if (disciplinaDAO.addDisciplinu(disciplinaDTO)){
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminDodavanjeDisciplineForm, "Uspješno dodana disciplina.");
						}
					});
					adminDodavanjeDisciplineForm.dispose();
				}else{
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminDodavanjeDisciplineForm, "Nije moguce dodati disciplinu.");
						}
					});
				}

			} else {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminDodavanjeDisciplineForm, "Nije unesen naziv discipline.");
					}
				});
			}
		} else {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					JOptionPane.showMessageDialog(adminDodavanjeDisciplineForm, "Nije selektovana elektrijada.");
				}
			});
		}

	}

	public void zatvoriProzor() {
		adminDodavanjeDisciplineForm.dispose();
		
	}

}
