package org.unibl.etf.ps.studentviewer.logic.controller;

import org.unibl.etf.ps.studentviewer.gui.view.PredmetChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PredmetChooseAddTypeFormController {
	AdministratorFormController administratorFormController = null;
	private PredmetChooseAddTypeForm pred;
	PredmetDTO predmetDTO;
	PredmetDAO predmetDAO;
	short pom1, pom2, pom3;
	char pom4;

	
	public PredmetChooseAddTypeFormController(AdministratorFormController administratorFormController, boolean one, boolean more, 
			PredmetChooseAddTypeForm predmetChoseAddTypeForm)
	{
		this.administratorFormController = administratorFormController;
		if (!(one || more)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(null, message);
			predmetChoseAddTypeForm.setVisible(true);
		} else if (one) {
			this.administratorFormController.createAddPredmetForm();
		} else {
			try {
				ImporterExcel importerExcel = new ImporterExcel();
				ArrayList<String[]> predmeti = importerExcel.getData(8);

				ArrayList<PredmetDTO> lista = new ArrayList<>();
				AddChangeStudentsHelpController help = new AddChangeStudentsHelpController();
				for (String[] data : predmeti) {
					String sifra = data[0];
					String naziv = data[1];
					String ects = data[2];
					pom1 = Short.parseShort(ects);
					String semestar = data[3];
					pom2 = Short.parseShort(semestar);
					String tipPredmeta = data[4];
					pom4 = tipPredmeta.charAt(0);
					String nazivSP = data[5];
					String skolskaGodina = data[6];
					String ciklus = data[7];
					pom3 = Short.parseShort(ciklus);
					PredmetDTO newPredmet = new PredmetDTO(sifra, naziv, pom1, pom2, pom4, nazivSP, skolskaGodina, pom3);
					lista.add(newPredmet);
				}
				if (predmetDAO.addPredmete(lista))
				{
					JOptionPane.showMessageDialog(pred, "Uspjesno dodati predmeti!");
				}
				else
				{
					JOptionPane.showMessageDialog(pred, "Neuspjesno dodavanje predmeta!");
				}
				// TODO poziv metode koja cuva listu u bazi
				
				// TODO poziv metode koja azurira tabelu
				administratorFormController.resetChooseAddTypeFormOpened();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
