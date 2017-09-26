package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminAzuriranjeElektrijadeForm;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ElektrijadaDTO;

public class AdminAzuriranjeElektrijadeController {

	private AdminAzuriranjeElektrijadeForm adminAzuriranjeElektrijadeForm;

	public AdminAzuriranjeElektrijadeController(AdminAzuriranjeElektrijadeForm adminAzuriranjeElektrijadeForm) {
		this.adminAzuriranjeElektrijadeForm = adminAzuriranjeElektrijadeForm;
	}

	public void zatvoriProzor() {
		adminAzuriranjeElektrijadeForm.dispose();
	}

	public void azurirajElektrijadu(JComboBox elektrijadeCB, JTextField textFieldDatum, JTextField textFieldLokacija) {
		if (elektrijadeCB.getItemCount() > 0 && textFieldDatum.getText().length() > 0
				&& textFieldLokacija.getText().length() > 0) {

			String datumString = textFieldDatum.getText();
			String lokacija = textFieldLokacija.getText();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			try {
				startDate = df.parse(datumString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date datum = null;
			try {
				datum = sdf.parse(datumString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminAzuriranjeElektrijadeForm, "Greška u formi datuma.");
					}
				});

				adminAzuriranjeElektrijadeForm.dispose();
			}

			if (datumString.equals(sdf.format(datum))) {
				MySQLDAOFactory dao = new MySQLDAOFactory();
				ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
				ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO.getSveElektrijade();
				
				ElektrijadaDTO elektrijadaDTO = elektrijade.get(elektrijadeCB.getSelectedIndex());
				elektrijadaDTO.setDatum(datum);
				elektrijadaDTO.setLokacija(lokacija);
				
				if (eleDAO.updateElektrijada(elektrijadaDTO)) {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminAzuriranjeElektrijadeForm,
									"Uspješno ažurirana Elektrijada.");
						}
					});
					adminAzuriranjeElektrijadeForm.dispose();
				} else {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminAzuriranjeElektrijadeForm,
									"Nije moguce ažurirati Elektrijadu.");
						}
					});
				}
			} else {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminAzuriranjeElektrijadeForm, "Grešska u formi datuma.");
					}
				});

			}

		} else {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					JOptionPane.showMessageDialog(adminAzuriranjeElektrijadeForm,
							"Nema selektovane Elektrijade ili polja nisu popunjena.");
				}
			});
		}
	}

}
