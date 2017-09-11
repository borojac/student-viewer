package org.unibl.etf.ps.studentviewer.logic.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.ChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.logic.exec.studentdatamanipulation.ImporterExcel;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class ChooseAddTypeController {
	MainFormController mainFormController = null;

	public ChooseAddTypeController(MainFormController mainFormController, boolean one, boolean more, ChooseAddTypeForm form) {
		this.mainFormController = mainFormController;
		if(!(one || more)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(null, message);
			form.setVisible(true);
		}
		else if(one) {
			this.mainFormController.createAddForm();
		}
		else {
			try {
				ImporterExcel importerExcel = new ImporterExcel();
				ArrayList<String[]> studenti = importerExcel.getData(3);
				
				ArrayList<StudentMainTableDTO> listaZaTabelu = new ArrayList<>();
				for(String[] data : studenti) {
					StudentMainTableDTO newStudent = new StudentMainTableDTO(data[0], data[1], data[2]);
					listaZaTabelu.add(newStudent);
				}
				//TODO poziv metode koja cuva listu u bazi
				for(StudentMainTableDTO student : listaZaTabelu) {
					mainFormController.getMainTable().addStudent(student);
					/*if(!added){
					 * 	show eror for that student...
					 * }
					 * 
					 */
				}
				//TODO poziv metode koja azurira tabelu
				mainFormController.resetChooseAddTypeFormOpened();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
