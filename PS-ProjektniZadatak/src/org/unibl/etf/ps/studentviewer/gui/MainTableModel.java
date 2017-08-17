package org.unibl.etf.ps.studentviewer.gui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MainTableModel extends DefaultTableModel {

	private Vector<Vector<String>> data = new Vector<Vector<String>>();

	// private String[][] data = {{"Example1", "Example2"}, {"Data1", "Data2"}};

	public Vector<Vector<String>> convertData(String[][] data) {
		Vector<Vector<String>> helpData = new Vector<Vector<String>>();
		for (int i = 0; i < data.length; i++) {
			Vector<String> helpVector = new Vector<String>();

			for (int j = 0; j < data[i].length; j++)
				helpVector.add(data[i][j]);

			helpData.add(helpVector);
		}
		return helpData;
	}

	public MainTableModel(String[][] data) {
		this.data = convertData(data);
	}

	public MainTableModel() {
	}

	public void setData(String[][] data) {
		this.data = convertData(data);
		setColumnCount(data[0].length);
		setRowCount(data.length);
	}

	@Override
	public int getColumnCount() {
		if (data.size() < 1)
			return 0;
		return data.get(0).size();
	}

	@Override
	public int getRowCount() {
		if (data != null)
			return data.size();
		return 0;
	}

	@Override
	public String getValueAt(int arg0, int arg1) {
		if (data.size() == 0)
			return null;

		if (data.size() < arg0)
			return null;

		if (data.size() < 1)
			return null;

		if (data.get(0).size() < arg1)
			return null;

		return data.get(arg0).get(arg1);
	}
	
	public void removeColumn(int n) {
		if (data.size() < 1)
			return;
		
		if (data.get(0).size() < n)
			return;
		
		for (Vector<String> vec : data) {
			vec.remove(n);
		}
	}
	
	public void insertColumn(int n, Vector<String> values) {
		int i = 0;
		for (Vector<String> vec : data) {
			vec.insertElementAt(values.get(i), n);
			i++;
		}
	}
	

}
