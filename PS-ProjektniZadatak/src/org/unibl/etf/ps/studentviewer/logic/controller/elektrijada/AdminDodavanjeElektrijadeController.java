package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminDodavanjeElektrijadeForm;
import org.unibl.etf.ps.studentviewer.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;

public class AdminDodavanjeElektrijadeController {

	private AdminDodavanjeElektrijadeForm adminDodavanjeElektrijadeForm;

	public AdminDodavanjeElektrijadeController(AdminDodavanjeElektrijadeForm adminDodavanjeElektrijadeForm) {
		this.adminDodavanjeElektrijadeForm = adminDodavanjeElektrijadeForm;
	}

	public void zatvoriProzor() {
		adminDodavanjeElektrijadeForm.dispose();

	}

	public void dodajElektrijadu(JTextField textFieldDatum, JTextField textFieldLokacija) {
		if (textFieldDatum.getText().length() == 0 || textFieldLokacija.getText().length() == 0) {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					JOptionPane.showMessageDialog(adminDodavanjeElektrijadeForm, "Popunite oba polja.");
				}
			});
		} else {
			String lokacija = textFieldLokacija.getText();
			String datumString = textFieldDatum.getText();

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
						JOptionPane.showMessageDialog(adminDodavanjeElektrijadeForm, "Greška u formi datuma.");
					}
				});
				
				adminDodavanjeElektrijadeForm.dispose();
			}

			if (datumString.equals(sdf.format(datum))) {
				
				ElektrijadaDTO elektrijadaDTO = new ElektrijadaDTO(0, datum, lokacija);
				MySQLDAOFactory factory = new MySQLDAOFactory();
				ElektrijadaDAO elektrijadaDAO = factory.getElektrijadaDAO();
				if (elektrijadaDAO.addElektrijada(elektrijadaDTO)){
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminDodavanjeElektrijadeForm, "Uspješno dodana Elektrijada.");
						}
					});
					adminDodavanjeElektrijadeForm.dispose();
				}else{
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(adminDodavanjeElektrijadeForm, "Nije moguce dodati Elektrijadu.");
						}
					});
				}
			} else {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(adminDodavanjeElektrijadeForm, "Greška u formi datuma.");
					}
				});
				
			}

		}

	}

}
