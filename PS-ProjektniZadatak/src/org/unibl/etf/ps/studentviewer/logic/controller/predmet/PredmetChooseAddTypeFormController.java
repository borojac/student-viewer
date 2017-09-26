package org.unibl.etf.ps.studentviewer.logic.controller.predmet;

import org.unibl.etf.ps.studentviewer.gui.view.predmet.PredmetChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.logic.utility.ImporterExcel;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PredmetChooseAddTypeFormController {

	public PredmetChooseAddTypeFormController(AdminPredmetiFormController adminPredmetiFormController, boolean one, boolean more, 
			PredmetChooseAddTypeForm predmetChoseAddTypeForm) {
		
		if (!(one || more)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(predmetChoseAddTypeForm, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			predmetChoseAddTypeForm.setVisible(true);
		} else if (one) {
			adminPredmetiFormController.createAddPredmetForm();
		} else {
			try {
				ImporterExcel importerExcel = new ImporterExcel();
				ArrayList<String[]> predmeti = importerExcel.getData(8);

				ArrayList<PredmetDTO> lista = new ArrayList<>();
				AddChangePredmetHelpController help = new AddChangePredmetHelpController();
				int paramValid = 0;
				int redSaGreskom = 0;
				int k = 1;
				for (String[] data : predmeti) {
					ArrayList<String> params = new ArrayList<>();
					for(int i = 0; i < data.length; i++) {
						params.add(data[i]);
					}
					paramValid = help.checkParams(params);
					if(paramValid == 0) {
						String sifra = params.get(0);
						String naziv = params.get(1);
						short ects = Short.parseShort(params.get(2));
						short semestar = Short.parseShort(params.get(3));
						char tipPredmeta = params.get(4).charAt(0);
						String nazivSP = params.get(5);
						String skolskaGodina = params.get(6);
						short ciklus = Short.parseShort(params.get(7));
						PredmetDTO newPredmet = new PredmetDTO(sifra, naziv, ects, semestar, tipPredmeta, nazivSP, skolskaGodina, ciklus);
						lista.add(newPredmet);
					} else {
						redSaGreskom = k;
						break;
					}
					k++;
				}
				if(predmeti.size() == 0) {
					
				} else if(redSaGreskom == 0) {
					MySQLDAOFactory factory = new MySQLDAOFactory();
					PredmetDAO predmetDAO = factory.getPredmetDAO();
					for(int i = 0; i < lista.size(); i++) {
						if(predmetDAO.checkPredmetNaFakultetu(lista.get(i)) && predmetDAO.checkPNaSP(lista.get(i)) && predmetDAO.checkPredmet(lista.get(i))) {
							JOptionPane.showMessageDialog(predmetChoseAddTypeForm, lista.get(i).getNazivPredmeta() + " vec postoji.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
							lista.remove(i);
							i--;
						}
					}
					if(lista.size() == 0) {
						JOptionPane.showMessageDialog(predmetChoseAddTypeForm, "Neuspjesno dodavanje predmeta!", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
					} else if(predmetDAO.addPredmete(lista)) {
						adminPredmetiFormController.getAdminPredmetiForm().initTable();
						JOptionPane.showMessageDialog(predmetChoseAddTypeForm, "Uspjesno dodati predmeti!", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
						adminPredmetiFormController.getAdminPredmetiForm().getPredmetiTbl().getSelectionModel().setSelectionInterval(-1, -1);
						adminPredmetiFormController.getAdminPredmetiForm().getIzmjeniBtn().setEnabled(false);
						adminPredmetiFormController.getAdminPredmetiForm().getObrisiBtn().setEnabled(false);
					}
					else {
						JOptionPane.showMessageDialog(predmetChoseAddTypeForm, "Neuspjesno dodavanje predmeta!", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					StringBuilder message = new StringBuilder("Podaci nisu uneseni! Greska u ");
					message.append(redSaGreskom).append(". vrsti dokumenta, kod podatka o ");
					if(paramValid == 1) {
						message.append("sifri predmeta.");
					} else if(paramValid == 2) {
						message.append("nazivu predmeta.");
					} else if(paramValid == 3) {
						message.append("ECTS bodovima.");
					} else if(paramValid == 4) {
						message.append("semestru.");
					} else if(paramValid == 5) {
						message.append("tipu predmeta.");
					} else if(paramValid == 6) {
						message.append("nazivu studijskog programa.");
					} else if(paramValid == 7) {
						message.append("skolskoj godini.");
					} else {
						message.append("ciklusu.");
					}
					JOptionPane.showMessageDialog(predmetChoseAddTypeForm, message.toString(), "Upozorenje!", JOptionPane.WARNING_MESSAGE);
				}
				AdminPredmetiFormController.resetChooseAddTypeFormOpened();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
