package org.unibl.etf.ps.studentviewer.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;

import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class MainTable extends JTable {

	private ArrayList<StudentMainTableDTO> students = null;
	private String[] columnIdentifiers = { "Indeks", "Ime", "Prezime" };
	private HashMap<String, String> map = new HashMap<String, String>();

	public void setStudents(ArrayList<StudentMainTableDTO> students) {
		this.students = students;
		MainTableModel model = (MainTableModel) getModel();
		model.setData(getStudentsForModel());
		model.setColumnIdentifiers(columnIdentifiers);
		map.put("Indeks", ShowViewData.D_BROJINDEKSA);
		map.put("Ime", ShowViewData.D_IME);
		map.put("Prezime", ShowViewData.D_PREZIME);
		repaint();
	}

	public void changeView() {
		
		MainTableModel model = (MainTableModel) getModel();
		ArrayList<String> columnNames = new ArrayList<String>();
		for (int i = 0; i < getColumnCount(); i ++)
			columnNames.add(getColumnName(i));
		int ii = 0;
		for (String s : columnIdentifiers) {

			if (columnNames.contains(s))
				ii++;
			
			if (columnNames.contains(s) && !ShowViewData.getValue(map.get(s))) {
				model.removeColumn(columnNames.indexOf(s));
				columnNames.remove(s);
				model.setColumnIdentifiers(columnNames.toArray());
			}else if (!columnNames.contains(s) && ShowViewData.getValue(map.get(s))) {
				Vector<String> values = new Vector<String>();
				for (StudentMainTableDTO student : students) {
					String property = student.getProperty(map.get(s));
					values.add(property);
				}
				model.insertColumn(ii, values);
				columnNames.add(ii, s);
				model.setColumnIdentifiers(columnNames.toArray());
			}
		}
		
		model.fireTableStructureChanged();
	}

	

	public ArrayList<StudentMainTableDTO> getStudents() {
		return students;
	}

	private String[][] getStudentsForModel() {
		String[][] forRet = new String[students.size()][3];
		int i = 0;
		for (StudentMainTableDTO student : students) {

			forRet[i][0] = student.getBrojIndeksa();
			forRet[i][1] = student.getIme();
			forRet[i][2] = student.getPrezime();
			i++;
		}
		return forRet;
	}

	public MainTable() {
		super();
		initView();
	}

	// TO-DO sa studentima

	private void initView() {
		// Dovuci podatke i prikazati
	}
}
