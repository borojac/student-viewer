package org.unibl.etf.ps.studentviewer.logic.controller.predmet;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.predmet.DodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.AccountFormController;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;

public class DodavanjePredmetaFormController {
	
	private DodavanjePredmetaForm dodavanjePredmetaForm;
	
	public DodavanjePredmetaFormController(DodavanjePredmetaForm dodavanjePredmetaForm) {
		this.dodavanjePredmetaForm = dodavanjePredmetaForm;
	}
	
	public void posaljiZahtjev() {
		PredmetDTO predmetDTO = dodavanjePredmetaForm.getSelectedPredmet();
		
		if(predmetDTO == null) {
			
		} else {
			NalogDTO nalogDTO = dodavanjePredmetaForm.getNalogDTO();
		
			ZahtjevDTO zahtjevDTO = new ZahtjevDTO(predmetDTO.getPredmetId(), nalogDTO.getNalogId());
		
			MySQLDAOFactory zahtjevFactory = new MySQLDAOFactory();
			ZahtjevDAO zahtjevDAO = zahtjevFactory.getZahtjevDAO();
		
			if(zahtjevDAO.addZahtjev(zahtjevDTO)) {
				JOptionPane.showMessageDialog(dodavanjePredmetaForm, "Zahtjev je uspjesno poslan.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				dodavanjePredmetaForm.dispose();
				AccountFormController.resetDodavanjePredmetaFormOpened();
			} else {
				JOptionPane.showMessageDialog(dodavanjePredmetaForm, "Zahtjev nije uspjesno poslan.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	public synchronized void postaviStudijskePrograme(JComboBox<String> studijskiProgramiCB, short ciklus) {
		studijskiProgramiCB.removeAllItems();
		ArrayList<String> studijskiProgramiList = new ArrayList<>();
		MySQLDAOFactory factory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		ArrayList<PredmetDTO> predmeti = predmetDAO.getAllPredmet();
		for(int i = 0; i < predmeti.size(); i++) {
			if(predmeti.get(i).getCiklus() == ciklus && !studijskiProgramiList.contains(predmeti.get(i).getNazivSP())) {
				studijskiProgramiList.add(predmeti.get(i).getNazivSP());
			}
		}
		for(int i = 0; i < studijskiProgramiList.size(); i++) {
			studijskiProgramiCB.addItem(studijskiProgramiList.get(i));
		}
		dodavanjePredmetaForm.setStudijskiProgramiList(studijskiProgramiList);
	}
	
	public void postaviPredmete(JComboBox<String> predmetiCB, ArrayList<PredmetDTO> predmetiNaNaloguList, short ciklus, String studijskiProgram, String skolskaGodina) {
		predmetiCB.removeAllItems();
		MySQLDAOFactory factory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		ArrayList<PredmetDTO> predmeti = predmetDAO.getAllPredmet();
		int k = 0;
		while(k < predmeti.size()) {
			if(predmeti.get(k).getCiklus() != ciklus) {
				predmeti.remove(k);
			} else {
				k++;
			}
		}
		k = 0;
		while(k < predmeti.size()) {
			if(!predmeti.get(k).getNazivSP().equals(studijskiProgram)) {
				predmeti.remove(k);
			} else {
				k++;
			}
		}
		k = 0;
		while(k < predmeti.size()) {
			if(!predmeti.get(k).getSkolskaGodina().equals(skolskaGodina)) {
				predmeti.remove(k);
			} else {
				k++;
			}
		}
		for(int i = 0; i < predmetiNaNaloguList.size(); i++) {
			if(predmeti.contains(predmetiNaNaloguList.get(i))) {
				predmeti.remove(predmetiNaNaloguList.get(i));
			}
		}
		ZahtjevDAO zahtjevDAO = factory.getZahtjevDAO();
		ArrayList<PredmetDTO> zahtjevaniPredmeti = zahtjevDAO.getPredmeteSaZahtjevomZaDan(dodavanjePredmetaForm.getNalogDTO().getNalogId(), new Date(System.currentTimeMillis()));
		for(int i = 0; i < zahtjevaniPredmeti.size(); i++) {
			if(predmeti.contains(zahtjevaniPredmeti.get(i))) {
				predmeti.remove(zahtjevaniPredmeti.get(i));
			}
		}
		for(int i = 0; i < predmeti.size(); i++) {
			predmetiCB.addItem(predmeti.get(i).getNazivPredmeta());
		}
	}

}
