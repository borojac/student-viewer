/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.gui.addstudentcontroller;


import org.unibl.etf.ps.studentviewer.gui.control.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.exec.addstudentexec.ChooseAddTypeExec;

public class ChooseAddTypeController {
	MainFormController mainFormControler = null;
	
	public ChooseAddTypeController(MainFormController mainFormControler, boolean jedan, boolean vise) {
		this.mainFormControler = mainFormControler;
		ChooseAddTypeExec chooseAddTypeExec = new ChooseAddTypeExec(mainFormControler, jedan, vise);
	}
}
