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
					int result = o1.getProperty(param).compareTo(o2.getProperty(param));
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
