/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.ChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class ChooseAddTypeController {
	MainFormController mainFormController = null;

	public ChooseAddTypeController(MainFormController mainFormController, boolean one, boolean more,
			ChooseAddTypeForm form) {
		this.mainFormController = mainFormController;
		if (!(one || more)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(null, message);
			form.setVisible(true);
		} else if (one) {
			this.mainFormController.createAddForm();
		} else {
			try {
				ImporterExcel importerExcel = new ImporterExcel();
				ArrayList<String[]> studenti = importerExcel.getData(3);

				ArrayList<StudentMainTableDTO> listaZaTabelu = new ArrayList<>();
				AddChangeStudentsHelpController help = new AddChangeStudentsHelpController();
				for (String[] data : studenti) {
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
						if (!mainFormController.getMainTable().addStudent(student)) {
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
				mainFormController.resetChooseAddTypeFormOpened();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
