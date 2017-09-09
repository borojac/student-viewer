/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.gui.addstudentcontroller;


import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.addstudentview.AddForm;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.exec.addstudentexec.AddExec;

public class AddFormController {
	MainFormController mainFormControler = null;
	AddForm addForm = null;

	public AddFormController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddFormController(MainFormController mainFormControler, ArrayList<String> params) {
		this.mainFormControler = mainFormControler;
		this.addForm = addForm;
		ArrayList<String> paramList = params;
		AddExec addExec = new AddExec(this.mainFormControler, paramList);
	}


}
