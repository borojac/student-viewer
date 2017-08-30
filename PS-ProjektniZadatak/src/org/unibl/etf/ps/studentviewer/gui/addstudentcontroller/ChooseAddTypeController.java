/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.gui.addstudentcontroller;


import org.unibl.etf.ps.studentviewer.addstudentexec.ChooseAddTypeExec;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormController;

public class ChooseAddTypeController {
	MainFormController mainFormControler = null;
	
	public ChooseAddTypeController(MainFormController mainFormControler, boolean jedan, boolean vise) {
		this.mainFormControler = mainFormControler;
		ChooseAddTypeExec chooseAddTypeExec = new ChooseAddTypeExec(mainFormControler, jedan, vise);
	}
}
