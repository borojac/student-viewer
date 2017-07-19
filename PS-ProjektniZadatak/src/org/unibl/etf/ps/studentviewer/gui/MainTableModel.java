package org.unibl.etf.ps.studentviewer.gui;

import javax.swing.table.AbstractTableModel;

public class MainTableModel extends AbstractTableModel {

	private String[][] data = {{"Example1", "Example2"}, {"Data1", "Data2"}};// null;
	
	
	
	@Override
	public int getColumnCount() {
		if (data == null || data[0] == null)
			return 0;
		
		return data[0].length;
	}

	@Override
	public int getRowCount() {
		if (data == null)
			return 0;
		
		return data.length;
	}

	@Override
	public String getValueAt(int arg0, int arg1) {
		if (data == null)
			return null;
		if (data[arg0][arg1] == null)
			return null;
		
		return data[arg0][arg1];
	}

}
