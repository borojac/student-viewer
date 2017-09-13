package org.unibl.etf.ps.studentviewer.logic.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class FilterUtil {

	public static ArrayList<StudentMainTableDTO> filter(MainFormController mainFormController,
			HashMap<String, HashMap<String, String>> testoviMap, ArrayList<String> controlParams) {
		ArrayList<StudentMainTableDTO> filteredStudents = new ArrayList<StudentMainTableDTO>();
		ArrayList<StudentMainTableDTO> currentStudents = mainFormController.getMainTable().getStudents();

		boolean ideNaElektrijadu = controlParams.contains(Filter.IDE_NA_ELEKTRIJADU);
		boolean neIdeNaElektrijadu = controlParams.contains(Filter.NE_IDE_NA_ELEKTRIJADU);
		boolean imaKomentar = controlParams.contains(Filter.IMA_KOMENTAR);
		boolean nemaKomentar = controlParams.contains(Filter.NEMA_KOMENTAR);

		Set<String> testovi = testoviMap.keySet();

		for (StudentMainTableDTO s : currentStudents) {
			if (ideNaElektrijadu)
				if ("NE".equals(s.getElektrijada()))
					continue;

			if (neIdeNaElektrijadu)
				if ("DA".equals(s.getElektrijada()))
					continue;

			if (imaKomentar)
				if ("".equals(s.getKomentar()))
					continue;

			if (nemaKomentar)
				if (!"".equals(s.getKomentar()))
					continue;

			boolean breakControl = false;
			for (String test : testovi) {
				boolean polozen = "true".equals(testoviMap.get(test).get(Filter.POLOZEN));
				boolean nijePolozen = "true".equals(testoviMap.get(test).get(Filter.NIJE_POLOZEN));
				boolean izasao = "true".equals(testoviMap.get(test).get(Filter.IZASAO));
				boolean nijeIzasao = "true".equals(testoviMap.get(test).get(Filter.NIJE_IZASAO));
				Integer viseOd = new Integer(testoviMap.get(test).get(Filter.VISE_OD));
				Integer manjeOd = new Integer(testoviMap.get(test).get(Filter.MANJE_OD));

				if (izasao)
					if (s.getTest(test) == null) {
						breakControl = true;
						break;
					}

				if (nijeIzasao)
					if (s.getTest(test) != null) {
						breakControl = true;
						break;
					}

				if (polozen)
					if (s.getTest(test) != null && new Integer(s.getTest(test)) <= 50) {
						breakControl = true;
						break;
					}

				if (nijePolozen)
					if (s.getTest(test) != null && new Integer(s.getTest(test)) > 50) {
						breakControl = true;
						break;
					}

				Integer poeni = new Integer(s.getTest(test));
				if (viseOd > poeni) {
					breakControl = true;
					break;
				}

				if (manjeOd < poeni) {
					breakControl = true;
					break;
				}

			}
			if (breakControl)
				continue;

			filteredStudents.add(s);
		}

		if (filteredStudents.size() == 0) {
			JOptionPane.showMessageDialog(null, "Nema odgovarajucih rezultata! Nijedna promjena na tabeli nije izvrsena.");
			filteredStudents = currentStudents;
		}
		return filteredStudents;
	}

}
