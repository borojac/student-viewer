package org.unibl.etf.ps.studentviewer.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public class DodatnaNastavaDataTableModel extends AbstractTableModel {

	/**
		 * 
		 */
	private static final long serialVersionUID = -190084636559831076L;
	private ArrayList<DodatnaNastavaDTO> listaDodatnihNastava;

	public DodatnaNastavaDataTableModel(ArrayList<DodatnaNastavaDTO> listaDodatnihNastava) {
		super();
		this.listaDodatnihNastava = listaDodatnihNastava;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listaDodatnihNastava.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public String getColumnName(int column) {
		String name = "??";
		switch (column) {
		case 0:
			name = "Naziv";
			break;
		case 1:
			name = "Datum";
			break;
		case 2:
			name = "Napomena";
			break;
		}
		return name;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		DodatnaNastavaDTO nastava = listaDodatnihNastava.get(rowIndex);
		Object value = null;
		switch (columnIndex) {
		case 0:
			value = nastava.getNaziv();
			break;
		case 1:
			value = nastava.getDatum();
			break;
		case 2:
			value = nastava.getNapomena();
			break;
		}
		return value;
	}

}
