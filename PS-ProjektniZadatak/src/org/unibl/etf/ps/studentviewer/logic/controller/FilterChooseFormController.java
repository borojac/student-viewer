package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.FilterChooseForm;
import org.unibl.etf.ps.studentviewer.logic.utility.Filter;

public class FilterChooseFormController {

	FilterChooseForm fcf;
	
	public FilterChooseFormController(FilterChooseForm filterChooseForm) {
		fcf = filterChooseForm;
	}
	
	public boolean setAndCheckParameters() {
		String test = fcf.getTest();
		boolean polozen = fcf.isPolozen();
		boolean nijePolozen = fcf.isNepolozen();
		boolean izasao = fcf.isIzasao();
		boolean nijeIzasao = fcf.isNijeIzasao();
		Integer donjaGranica = new Integer(fcf.getDonjaGranica());
		Integer gornjaGranica = new Integer(fcf.getGornjaGranica());
		
		if ("".equals(test)) {
			JOptionPane.showMessageDialog(null, "Nije izabran termin testa!");
			return false;
		}
		
		if (polozen && nijePolozen) {
			JOptionPane.showMessageDialog(null, "Neispravno selektovani \"polozen\" i \"nepolozen\"!");
			return false;
		}
		
		if (izasao && nijeIzasao) {
			JOptionPane.showMessageDialog(null, "Neispravno selektovani \"izasao\" i \"nije izasao\"!");
			return false;
		}
		
		if (donjaGranica > gornjaGranica) {
			JOptionPane.showMessageDialog(null, "Donja granica ne moze da bude veca od gornje!");
			return false;
		}
		
		if (polozen && nijeIzasao) {
			JOptionPane.showMessageDialog(null, "Neispravno selektovani \"polozen\" i \"nije izasao\"!");
			return false;
		}
		
		fcf.putToMap(Filter.POLOZEN, new Boolean(polozen).toString());
		fcf.putToMap(Filter.NIJE_POLOZEN, new Boolean(nijePolozen).toString());
		fcf.putToMap(Filter.IZASAO, new Boolean(izasao).toString());
		fcf.putToMap(Filter.NIJE_IZASAO, new Boolean(nijeIzasao).toString());
		fcf.putToMap(Filter.VISE_OD, donjaGranica.toString().toString());
		fcf.putToMap(Filter.MANJE_OD, gornjaGranica.toString());
		fcf.putToMap(Filter.TEST, test);
		
		return true;
	}
	
}
