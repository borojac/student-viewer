package org.unibl.etf.ps.studentviewer.exec;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.gui.controler.MainFormControler;

public class SortExec extends Exec {
	private Date thirdParam;
	
	
	public SortExec(MainFormControler mainFormControler, ArrayList<Object> params) {
		this.mainFormControler = mainFormControler;
		//firstParam = params.get(0);
		secondParam = (String)params.get(1);
		thirdParam = (Date)params.get(2);
	}
	
	public void run() {
		
	}
}
