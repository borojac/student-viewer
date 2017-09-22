package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.BrisanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class BrisanjePredmetaFormController {
	
	private BrisanjePredmetaForm brisanjePredmetaForm;
	
	public BrisanjePredmetaFormController(BrisanjePredmetaForm brisanjePredmetaForm) {
		this.brisanjePredmetaForm = brisanjePredmetaForm;
	}
	
	public void ukloniPredmet() {
		NalogDTO nalogDTO = brisanjePredmetaForm.getNalogDTO();
		PredmetDTO predmetDTO = brisanjePredmetaForm.getSelectedPredmet();
		
		if(predmetDTO == null) {
			
		} else {
			MySQLDAOFactory factory = new MySQLDAOFactory();
			NalogDAO nalogDAO = factory.getNalogDAO();
			ZahtjevDAO zahtjevDAO = factory.getZahtjevDAO();
		
			if(zahtjevDAO.deleteZahtjeve(predmetDTO, nalogDTO) && nalogDAO.removePredmet(predmetDTO, nalogDTO)) {
				JOptionPane.showMessageDialog(brisanjePredmetaForm, "Predmet uspjesno uklonjen.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				brisanjePredmetaForm.getMainForm().resetPredmetiComboBox();
				brisanjePredmetaForm.dispose();
				AccountFormController.resetBrisanjePredmetaFormOpened();
			} else {
				JOptionPane.showMessageDialog(brisanjePredmetaForm, "Predmet nije uspjesno uklonjen.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public synchronized void postaviStudijskePrograme(JComboBox<String> studijskiProgramiCB, short ciklus) {
		studijskiProgramiCB.removeAllItems();
		ArrayList<String> studijskiProgramiList = new ArrayList<>();
		MySQLDAOFactory factory = new MySQLDAOFactory();
		NalogDAO nalogDAO = factory.getNalogDAO();
		ArrayList<PredmetDTO> predmeti = nalogDAO.getPredmeteNaNalogu(brisanjePredmetaForm.getNalogDTO().getNalogId());
		for(int i = 0; i < predmeti.size(); i++) {
			if(predmeti.get(i).getCiklus() == ciklus && !studijskiProgramiList.contains(predmeti.get(i).getNazivSP())) {
				studijskiProgramiList.add(predmeti.get(i).getNazivSP());
			}
		}
		for(int i = 0; i < studijskiProgramiList.size(); i++) {
			studijskiProgramiCB.addItem(studijskiProgramiList.get(i));
		}
		brisanjePredmetaForm.setStudijskiProgramiList(studijskiProgramiList);
	}
	
	public void postaviPredmete(JComboBox<String> predmetiCB, short ciklus, String studijskiProgram, String skolskaGodina) {
		predmetiCB.removeAllItems();
		MySQLDAOFactory factory = new MySQLDAOFactory();
		NalogDAO nalogDAO = factory.getNalogDAO();
		ArrayList<PredmetDTO> predmeti = nalogDAO.getPredmeteNaNalogu(brisanjePredmetaForm.getNalogDTO().getNalogId());
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
		for(int i = 0; i < predmeti.size(); i++) {
			predmetiCB.addItem(predmeti.get(i).getNazivPredmeta());
		}
	}

}
