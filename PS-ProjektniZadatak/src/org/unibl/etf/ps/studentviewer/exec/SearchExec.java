package org.unibl.etf.ps.studentviewer.exec;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.controler.MainFormControler;

public class SearchExec extends Exec {
	ArrayList<String> params = new ArrayList<String>();
	
	public SearchExec(MainFormControler mainFormControler, ArrayList<Object> params) {
		this.mainFormControler = mainFormControler;
		for (Object o : params)
			this.params.add((String)o);
		mainFormControler.getScheduler().add(this);
	}
	
	public void execute() {
		//TO-DO MY-SQL
		System.out.println(params);
	}
}
