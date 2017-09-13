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
			JOptionPane.showMessageDialog(null, message, "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			form.setVisible(true);
		} else if (one) {
			this.mainFormController.createAddForm();
		} else {
			try {
				ImporterExcel importerExcel = new ImporterExcel();
				ArrayList<String[]> studenti = importerExcel.getData(3);

				ArrayList<StudentMainTableDTO> listaZaTabelu = new ArrayList<>();
				AddChangeStudentsHelpController help = new AddChangeStudentsHelpController();
				ArrayList<Integer> vrsteSGreskama = new ArrayList<>();
				int i = 1;
				for (String[] data : studenti) {
					String indeks = data[0];
					String ime = data[1];
					String prezime = data[2];
					StudentMainTableDTO newStudent = new StudentMainTableDTO(indeks, ime, prezime);
					if (help.checkParams(newStudent) == 0)
						listaZaTabelu.add(newStudent);
					else
						vrsteSGreskama.add(i);
					i++;
				}
				// TODO poziv metode koja cuva listu u bazi
				if (vrsteSGreskama.size() == 0)
					for (StudentMainTableDTO student : listaZaTabelu) {
						if (!mainFormController.getMainTable().addStudent(student)) {
							final String message = "Greska pri unosu studenta!";
							JOptionPane.showMessageDialog(null, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
						}
					}
				else {
					StringBuilder message = new StringBuilder("Greske u ");
					for(Integer num : vrsteSGreskama) {
						message.append(num);
						message.append(", ");
					}
					String tmp = message.reverse().delete(0, 2).reverse().append(" vrsti").toString();
					JOptionPane.showMessageDialog(null, tmp, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
				}
				mainFormController.resetChooseAddTypeFormOpened();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
