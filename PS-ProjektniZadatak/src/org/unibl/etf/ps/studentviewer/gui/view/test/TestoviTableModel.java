package org.unibl.etf.ps.studentviewer.gui.view.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class TestoviTableModel extends AbstractTableModel {

	private List<TestDTO> data = new ArrayList<>();
	private String[] columns = {"Naziv", "Datum", "Napomena"};
	
	public TestoviTableModel() {
	}
	
	public TestoviTableModel(List<TestDTO> data) {
		this.setData(data);
		fireTableDataChanged();
	}
	
	public void setData(List<TestDTO> data) {
		this.data = data;
		fireTableDataChanged();
	}
	
	public List<TestDTO> getData() {
		return data;
	}
	
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
	
	public TestDTO getRowAt(int row) {
		if (row >= 0 && row < data.size())
			return data.get(row);
		return null;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TestDTO row = data.get(rowIndex);
		if (columnIndex == 0) 
			return row.getNaziv();
		else if (columnIndex == 1)
			return new SimpleDateFormat("dd.MM.yyyy").format(row.getDatum());
		else if (columnIndex == 2)
			return row.getNapomena();
		return null;
	}

}
