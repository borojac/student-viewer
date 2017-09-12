package org.unibl.etf.ps.studentviewer.logic.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class SortUtil {
	

	public static Comparator<StudentMainTableDTO> getComparator(ArrayList<StudentMainTableDTO> students, ArrayList<String> params) {
		Comparator<StudentMainTableDTO> comparator = new Comparator<StudentMainTableDTO>() {
		
			@Override
			public int compare(StudentMainTableDTO o1, StudentMainTableDTO o2) {
				for (String param : params) {
					int result = 0;
					
					if (Sort.BROJ_INDEKSA.equals(param)) {
						Integer godina1 = new Integer(o1.getProperty(param).split("/")[1]);
						Integer godina2 = new Integer(o2.getProperty(param).split("/")[1]);
						result = godina1.compareTo(godina2);
						if (result == 0) {
							Integer broj1 = new Integer(o1.getProperty(param).split("/")[0]);
							Integer broj2 = new Integer(o2.getProperty(param).split("/")[0]);
							result = broj1.compareTo(broj2);
						}
					}else if (param.startsWith(Sort.TEST)) {
						Integer broj1 = new Integer(o1.getProperty(param));
						Integer broj2 = new Integer(o2.getProperty(param));
						result = broj2.compareTo(broj1);
					}else
						result = o1.getProperty(param).compareTo(o2.getProperty(param));
					if (result > 0)
						return 1;
					else if (result < 0)
						return -1;
				}
				return 0;
			}
			
		};
		return comparator;
	}
	
}
