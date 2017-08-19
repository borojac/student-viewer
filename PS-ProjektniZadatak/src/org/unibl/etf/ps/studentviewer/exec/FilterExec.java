package org.unibl.etf.ps.studentviewer.exec;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.gui.controler.MainFormController;

public class FilterExec extends Exec {
	private String thirdParam;
	private Date fourthParam;
	private double fifthParam;
	private double sixthParam;
	
	public FilterExec(MainFormController mainFormControler, ArrayList<Object> params) {
		this.mainFormControler = mainFormControler;
		//firstParam = params.get(0);
		secondParam = (String)params.get(1);
		thirdParam = (String)params.get(2);
		fourthParam = (Date)params.get(3);
		fifthParam = (double)params.get(4);
		sixthParam = (double)params.get(5);
	}
	
	public void run() {
		
	}
}
