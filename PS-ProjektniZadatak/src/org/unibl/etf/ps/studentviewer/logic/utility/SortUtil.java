package org.unibl.etf.ps.studentviewer.logic.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;

public class SortUtil {

	public static Comparator<StudentMainTableDTO> getComparator(ArrayList<String> params) {
		Comparator<StudentMainTableDTO> comparator = new Comparator<StudentMainTableDTO>() {
		
			@Override
			public int compare(StudentMainTableDTO o1, StudentMainTableDTO o2) {
				for (String param : params) {
					int result = 0;
					
					if (Sort.OCJENA.equals(param)) {
						Integer ocjena1;//new Integer(o1.getProperty(param));
						Integer ocjena2;// = new Integer(o2.getProperty(param));
						if ("/".equals(o1.getProperty(param)))
							ocjena1 = 0;
						else
							ocjena1 = new Integer(o1.getProperty(param));

						if ("/".equals(o2.getProperty(param)))
							ocjena2 = 0;
						else
							ocjena2 = new Integer(o2.getProperty(param));
						result = ocjena2.compareTo(ocjena1);
					}else if (Sort.BROJ_INDEKSA.equals(param)) {
						Integer godina1 = new Integer(o1.getProperty(param).split("/")[1]);
						Integer godina2 = new Integer(o2.getProperty(param).split("/")[1]);
						result = godina2.compareTo(godina1);
						if (result == 0) {
							Integer broj1 = new Integer(o1.getProperty(param).split("/")[0]);
							Integer broj2 = new Integer(o2.getProperty(param).split("/")[0]);
							result = broj1.compareTo(broj2);
						}
					}else if (param.startsWith(Sort.TEST)) {
						String help1 = null;
						String help2 = null;
						if ("/".equals(o1.getProperty(param)))
							help1 = "-1";
						else
							help1 = o1.getProperty(param);
						
						if ("/".equals(o2.getProperty(param)))
								help2 = "-1";
						else 
							help2 = o2.getProperty(param);
						
						Integer broj1 = new Integer(help1);
						Integer broj2 = new Integer(help2);
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
