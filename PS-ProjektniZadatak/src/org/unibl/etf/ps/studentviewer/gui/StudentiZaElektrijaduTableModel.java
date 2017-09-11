package org.unibl.etf.ps.studentviewer.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class StudentiZaElektrijaduTableModel extends AbstractTableModel {
	/**
		 * 
		 */
	private static final long serialVersionUID = -170305053481477210L;
	private ArrayList<StudentZaElektrijaduDTO> listaStudenata;

	public StudentiZaElektrijaduTableModel(ArrayList<StudentZaElektrijaduDTO> listaStudenata) {
		// TODO Auto-generated constructor stub
		super();
		this.listaStudenata = listaStudenata;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listaStudenata.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public String getColumnName(int column) {
		String name = "??";
		switch (column) {
		case 0:
			name = "Indeks";
			break;
		case 1:
			name = "Ime";
			break;
		case 2:
			name = "Prezime";
			break;
		case 3:
			name = "Napomena";
			break;
		}
		return name;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		StudentZaElektrijaduDTO st = listaStudenata.get(rowIndex);
		Object value = null;
		switch (columnIndex) {
		case 0:
			value = st.getIndeks();
			break;
		case 1:
			value = st.getIme();
			break;
		case 2:
			value = st.getPrezime();
			break;
		case 3:
			value = st.getNapomena();
			break;
		}
		return value;
	}
}
