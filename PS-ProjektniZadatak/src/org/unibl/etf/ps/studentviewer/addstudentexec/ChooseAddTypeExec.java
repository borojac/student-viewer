/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.addstudentexec;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.exec.Exec;
import org.unibl.etf.ps.studentviewer.gui.addstudentcontroller.ImportController;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormController;


public class ChooseAddTypeExec extends Exec {

	public ChooseAddTypeExec(MainFormController mainFormController, boolean jedan, boolean vise) {
		// TODO Auto-generated constructor stub
		if(!(jedan || vise)) {
			final String message = "Morate izabrati jednu opciju!";
			JOptionPane.showMessageDialog(null, message);
			MainFormController.resetChooseAddTypeFormOpened();
			while(!mainFormController.createChooseAddTypeForm());
		}else if(jedan) {
			while(!mainFormController.createAddForm());
		}else {
			try {
				ImportController importController = new ImportController(mainFormController);
				MainFormController.resetChooseAddTypeFormOpened();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
