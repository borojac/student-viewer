/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdminStudentForm;
import org.unibl.etf.ps.studentviewer.gui.view.ChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class ChooseAddTypeController {
	MainFormController mainFormController = null;
	AdministratorFormController administratorFormController = null;
			
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
			addListOfStudents(false);

		}
	}
	
	public ChooseAddTypeController(AdministratorFormController adminFormController, boolean one, boolean more,
			ChooseAddTypeForm form) {
		this.administratorFormController = adminFormController;
		if (!(one || more)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(null, message, "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			form.setVisible(true);
		} else if (one) {
			adminFormController.createAddStudentsForm();
		} else {
			addListOfStudents(true);
		}
	}

	private void addListOfStudents(boolean isAdmin) {
		try {
			ImporterExcel importerExcel = new ImporterExcel();
			ArrayList<String[]> studenti = importerExcel.getData(3);

			ArrayList<StudentMainTableDTO> listaZaTabelu = new ArrayList<>();
			AddChangeStudentsHelpController help = new AddChangeStudentsHelpController();
			int vrsteSGreskama = 0;
			int i = 1;
			StudentMainTableDTO newStudent = null;
			int paramValid = 0;
			for (String[] data : studenti) {
				String indeks = data[0];
				String ime = data[1];
				String prezime = data[2];
				newStudent = new StudentMainTableDTO(indeks, ime, prezime);
				paramValid = help.checkParams(newStudent);
				if (paramValid == 0)
					listaZaTabelu.add(newStudent);
				else {
					vrsteSGreskama = i;
					break;
				}
				i++;
			}
			MySQLStudentDAO dao = new MySQLStudentDAO();
			boolean greska = false;
			if (vrsteSGreskama == 0) {
				for (StudentMainTableDTO student : listaZaTabelu) {

					if (isAdmin)
						dao.dodajStudentaUListu(student); // dodavanje u bazu podataka
					else {
						int studetnID = dao.getStudentBy(student.getBrojIndeksa()).getStudentId();
						student.setStudentId(studetnID);
						dao.dodajStudentaNaPredmet(student, mainFormController.getMainForm().getSelectedPredmet());
						if (!mainFormController.getMainTable().addStudent(student)) {
							final String message = "Greska pri unosu studenta!";
							JOptionPane.showMessageDialog(null, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
							greska = true;
							break;
						}
					}
				}
				// mainFormController.getMainTable().tableChanged();
				if (!greska) {
					final String message = "Uspjesno dodavanje!";
					JOptionPane.showMessageDialog(null, message, "Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				StringBuilder message = new StringBuilder("Podaci nisu uneseni! Greska u ");
				message.append(vrsteSGreskama).append(". vrsti dokumenta, kod podatka o ");
				if (paramValid == 1)
					message.append("imenu.");
				else if (paramValid == 2)
					message.append("prezimenu.");
				else
					message.append("broju indeksa.");
				JOptionPane.showMessageDialog(null, message.toString(), "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			}
			if(isAdmin)
				administratorFormController.resetChooseAddStudentsTypeFormOpened();
			else
				mainFormController.resetChooseAddTypeFormOpened();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
