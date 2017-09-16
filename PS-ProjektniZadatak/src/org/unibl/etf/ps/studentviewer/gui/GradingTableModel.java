package org.unibl.etf.ps.studentviewer.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.model.dto.GradingInfoDTO;


public class GradingTableModel extends AbstractTableModel {
	private String[] columns = {"Naziv", "Datum", "Bodovi", "Komentar"};
	private List<GradingInfoDTO> data = new ArrayList<>();
	public GradingTableModel() {}
	public GradingTableModel(List<GradingInfoDTO> data) {
		super();
		setData(data);
	}
	public void setData(List<GradingInfoDTO> data) {
		this.data = data;
		fireTableDataChanged();
	}
	
	public GradingInfoDTO getDataAt(int row) {
		if (row >= 0 && row < data.size())
			return data.get(row);
		return null;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		GradingInfoDTO row = data.get(rowIndex);
		if (columnIndex == 0)
			return row.getTest().getNaziv();
		else if (columnIndex == 1)
			return new SimpleDateFormat("dd.MM.yyyy").format(row.getTest().getDatum());
		else if (columnIndex == 2)
			return row.getBrojBodova();
		else if (columnIndex == 3)
			return row.getKomentar();
		return null;
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}
	
}
