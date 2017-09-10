/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.exec.addstudentexec;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.controler.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.exec.Exec;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;


public class ChooseAddTypeExec extends Exec {

	public ChooseAddTypeExec(MainFormController mainFormController, boolean jedan, boolean vise) {
		if(!(jedan || vise)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(null, message);
			MainFormController.resetChooseAddTypeFormOpened();
			mainFormController.createChooseAddTypeForm();
		}else if(jedan) {
			mainFormController.createAddForm();
		}else {
			try {
				ImporterExcel importerExcel = new ImporterExcel();
				ArrayList<String[]> studenti = importerExcel.getData(3);
				
				ArrayList<StudentMainTableDTO> listaZaTabelu = new ArrayList<>();
				for(String[] data : studenti) {
					StudentMainTableDTO newStudent = new StudentMainTableDTO(data[0], data[1], data[2], null);
					listaZaTabelu.add(newStudent);
				}
				//TODO poziv metode koja cuva listu u bazi
				//TODO poziv metode koja azurira tabelu
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
