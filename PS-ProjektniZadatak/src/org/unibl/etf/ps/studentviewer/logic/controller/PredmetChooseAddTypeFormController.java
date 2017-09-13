package org.unibl.etf.ps.studentviewer.logic.controller;

import org.unibl.etf.ps.studentviewer.gui.view.PredmetChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PredmetChooseAddTypeFormController {
	AdministratorFormController administratorFormController = null;
	
	public PredmetChooseAddTypeFormController(AdministratorFormController administratorFormController, boolean one, boolean more, 
			PredmetChooseAddTypeForm predmetChoseAddTypeForm)
	{
		this.administratorFormController = administratorFormController;
		if (!(one || more)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(null, message);
			predmetChoseAddTypeForm.setVisible(true);
		} else if (one) {
			this.administratorFormController.createAddForm();
		} else {
			try {
				ImporterExcel importerExcel = new ImporterExcel();
				ArrayList<String[]> predmeti = importerExcel.getData(3);

				ArrayList<StudentMainTableDTO> listaZaTabelu = new ArrayList<>();
				AddChangeStudentsHelpController help = new AddChangeStudentsHelpController();
				for (String[] data : predmeti) {
					String indeks = data[0];
					String ime = data[1];
					String prezime = data[2];
					StudentMainTableDTO newStudent = new StudentMainTableDTO(indeks, ime, prezime);
					listaZaTabelu.add(newStudent);

				}
				// TODO poziv metode koja cuva listu u bazi
				int i = 1;
				for (StudentMainTableDTO student : listaZaTabelu) {
					if (help.checkParams(student) == 0) { //ispravni parametri za ovog studenta
						if (!administratorFormController.getMainTable().addStudent(student)) {
							final String message = "Greska pri unosu studenta!";
							JOptionPane.showMessageDialog(null, message);
						}
					}else {
						StringBuilder sb = new StringBuilder();
						sb.append("Pogresni parametri u ");
						sb.append(i);
						sb.append(". vrsti dokumenta!");
						String message = sb.toString();
						JOptionPane.showMessageDialog(null, message);
					}
					i++;
				}
				// TODO poziv metode koja azurira tabelu
				administratorFormController.resetChooseAddTypeFormOpened();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
