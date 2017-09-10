package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JButton;
import org.unibl.etf.ps.studentviewer.gui.view.KreirajNalogForm;

public class KreirajNalogFormController {
	
	
	
	public KreirajNalogFormController(KreirajNalogForm kreirajNalogForm)
	{
		this.kreirajNalogForm = kreirajNalogForm;
	}
	
	private KreirajNalogForm kreirajNalogForm;
	String s1,s2,s3;
	int x=0;
	
	public synchronized boolean createKreirajNalog() {
		
		s1 = kreirajNalogForm.getLozinkaTf();
		s2 = kreirajNalogForm.getLozinkaPotTf();
		s3 = kreirajNalogForm.getKorImeTf();
		
		return true;
	}
}
