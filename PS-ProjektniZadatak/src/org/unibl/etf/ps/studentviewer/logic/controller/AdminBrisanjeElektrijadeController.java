package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdminBrisanjeElektrijadeForm;
import org.unibl.etf.ps.studentviewer.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;

public class AdminBrisanjeElektrijadeController {

	private AdminBrisanjeElektrijadeForm adminBrisanjeElektrijadeForm;
	
	public AdminBrisanjeElektrijadeController(AdminBrisanjeElektrijadeForm adminBrisanjeElektrijadeForm) {
		this.adminBrisanjeElektrijadeForm = adminBrisanjeElektrijadeForm;
	}

	public void zatvoriProzor() {
		adminBrisanjeElektrijadeForm.dispose();
		
	}

	public void obrisiElektrijadu(JComboBox elektrijadeCB) {
		if (elektrijadeCB.getItemCount() > 0){
			MySQLDAOFactory dao = new MySQLDAOFactory();
			ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
			ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO
					.getSveElektrijade();
			ElektrijadaDTO elektrijadaDTO = elektrijade.get(elektrijadeCB.getSelectedIndex());
			
			if (eleDAO.deleteElektrijada(elektrijadaDTO)){
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminBrisanjeElektrijadeForm, "Uspjesno obrisana Elektrijada.");
					}
				});
				adminBrisanjeElektrijadeForm.dispose();
			}else{
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminBrisanjeElektrijadeForm, "Neuspjesno obrisana Elektrijada.");
					}
				});
			}
			
			
		}else{
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					JOptionPane.showMessageDialog(adminBrisanjeElektrijadeForm, "Selektujte Elektrijadu.");
				}
			});
		}
		
	}

}
