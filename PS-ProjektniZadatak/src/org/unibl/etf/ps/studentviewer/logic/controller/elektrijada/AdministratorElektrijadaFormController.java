package org.unibl.etf.ps.studentviewer.logic.controller.elektrijada;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminAzuriranjeElektrijadeForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminBrisanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminBrisanjeElektrijadeForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminDodavanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminDodavanjeElektrijadeForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.AdminElektrijadaForm;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ZahtjevDisciplinaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDisciplinaDTO;

public class AdministratorElektrijadaFormController {

	private AdminElektrijadaForm adminElektrijadaForm;

	public AdministratorElektrijadaFormController(AdminElektrijadaForm adminElektrijadaForm) {
		this.adminElektrijadaForm = adminElektrijadaForm;
	}

	public void odobriZahtjev() {
		ZahtjevDisciplinaDTO zahtjevDTO = adminElektrijadaForm.getSelectedZahtjev();
		if (zahtjevDTO!=null){
			MySQLDAOFactory factory = new MySQLDAOFactory();
			ZahtjevDisciplinaDAO zahtjevDAO = factory.getZahtjevDiciplinaDAO();
			NalogDAO nalogDAO = factory.getNalogDAO();
			NalogDTO nalogDTO = nalogDAO.getNalog(zahtjevDTO.getNalogId());
			DisciplinaDTO disciplinaDTO = new DisciplinaDTO(zahtjevDTO.getNaziv(), zahtjevDTO.getElektrijadaId());
	
			if (zahtjevDAO.updateZahtjev(zahtjevDTO) && nalogDAO.addDisciplinuNaNalog(disciplinaDTO, nalogDTO)) {
				JOptionPane.showMessageDialog(adminElektrijadaForm, "Zahtjev odobren.", "Obavještenje",
						JOptionPane.INFORMATION_MESSAGE);
				adminElektrijadaForm.removeSelectedRow();
			}
		}else{
			JOptionPane.showMessageDialog(adminElektrijadaForm, "Prazna lista zahtjeva.");
		}
		
	}

	public void dodajDisciplinu() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				AdminDodavanjeDisciplineForm frame = new AdminDodavanjeDisciplineForm();
				frame.setVisible(true);
			}
		});

	}

	public void odbijZahtjev() {
		ZahtjevDisciplinaDTO zahtjevDTO = adminElektrijadaForm.getSelectedZahtjev();
		if (zahtjevDTO!=null){
			MySQLDAOFactory zahtjevFactory = new MySQLDAOFactory();
			ZahtjevDisciplinaDAO zahtjevDAO = zahtjevFactory.getZahtjevDiciplinaDAO();
	
			if (zahtjevDAO.deleteZahtjev(zahtjevDTO)) {
				JOptionPane.showMessageDialog(adminElektrijadaForm, "Zahtjev odbijen.", "Obavještenje",
						JOptionPane.INFORMATION_MESSAGE);
				adminElektrijadaForm.removeSelectedRow();
			}
		}else{
			JOptionPane.showMessageDialog(adminElektrijadaForm, "Prazna lista zahtjeva.");
		}
	}

	public void nazadNaAdminFormu() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				adminElektrijadaForm.setVisible(false);
				adminElektrijadaForm.dispose();
				adminElektrijadaForm.getAdminForma().setVisible(true);
			}
		});

	}

	public void obrisiDisciplinu() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				AdminBrisanjeDisciplineForm frame = new AdminBrisanjeDisciplineForm();
				frame.setVisible(true);
			}
		});

	}

	

	public void dodajElektrijadu() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				AdminDodavanjeElektrijadeForm frame = new AdminDodavanjeElektrijadeForm();
				frame.setVisible(true);
			}
		});

	}

	public void obrisiElektrijadu() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				AdminBrisanjeElektrijadeForm frame = new AdminBrisanjeElektrijadeForm();
				frame.setVisible(true);
			}
		});

	}

	public void azurirajElektrijadu() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				AdminAzuriranjeElektrijadeForm frame = new AdminAzuriranjeElektrijadeForm();
				frame.setVisible(true);
			}
		});

	}

	public void zatvoriProzor() {
		this.nazadNaAdminFormu();
	}

}
