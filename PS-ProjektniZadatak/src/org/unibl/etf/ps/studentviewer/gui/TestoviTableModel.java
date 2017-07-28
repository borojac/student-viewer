package org.unibl.etf.ps.studentviewer.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class TestoviTableModel extends AbstractTableModel {

	private List<TestDTO> data = new ArrayList<>();
	private String[] columns = {"Naziv", "Datum", "Napomena"};
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex >= 0 && columnIndex < columns.length)
			return columns[columnIndex];
		return null;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TestDTO row = data.get(rowIndex);
		if (columnIndex == 0) 
			return row.getNaziv();
		else if (columnIndex == 1)
			return row.getDatum();
		else if (columnIndex == 2)
			return row.getNapomena();
		return null;
	}

}
