package org.unibl.etf.ps.studentviewer.exec;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.gui.controler.MainFormControler;

public class SortExec extends Exec {
	
	private Date thirdParam;
	ArrayList<String> params = new ArrayList<String>();
	
	
	public SortExec(MainFormControler mainFormControler, ArrayList<Object> params) {
		this.mainFormControler = mainFormControler;
		for(Object o : params) {
			this.params.add((String)o);
		}
//		secondParam = (String)params.get(1);
//		thirdParam = (Date)params.get(2);
		mainFormControler.getScheduler().add(this);
	}
	
	public void execute() {
		System.out.println("SortExecute");
	}
}
