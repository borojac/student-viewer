package org.unibl.etf.ps.studentviewer.gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class MainTable extends JTable {

	private ArrayList<StudentMainTableDTO> students = null;
	private String[] columnIdentifiers = { "Indeks", "Ime", "Prezime", "Elektrijada", "Komentar" };
	private HashMap<String, String> map = new HashMap<String, String>();

	public void setStudents(ArrayList<StudentMainTableDTO> students) {
		this.students = students;
		MainTableModel model = (MainTableModel) getModel();
		model.setData(getStudentsForModel());
		model.setColumnIdentifiers(columnIdentifiers);

		setSizeOfColumns();
		repaint();
	}

	public void changeView() {

		MainTableModel model = (MainTableModel) getModel();
		ArrayList<String> columnNames = new ArrayList<String>();
		for (int i = 0; i < getColumnCount(); i++)
			columnNames.add(getColumnName(i));
		int ii = 0;
		for (String s : columnIdentifiers) {

			if (columnNames.contains(s))
				ii++;

			if (columnNames.contains(s) && !ShowViewData.getValue(map.get(s))) {
				model.removeColumn(columnNames.indexOf(s));
				columnNames.remove(s);
				model.setColumnIdentifiers(columnNames.toArray());
			} else if (!columnNames.contains(s) && ShowViewData.getValue(map.get(s))) {
				Vector<String> values = new Vector<String>();
				for (StudentMainTableDTO student : students) {
					String property = student.getProperty(map.get(s));
					values.add(property);
				}
				model.insertColumn(ii, values);
				columnNames.add(ii, s);
				model.setColumnIdentifiers(columnNames.toArray());
				ii++;
			}
		}

		model.fireTableStructureChanged();
		setSizeOfColumns();
	}

	private void setSizeOfColumns() {
		int columnCount = columnModel.getColumnCount();
		Enumeration<TableColumn> tc = columnModel.getColumns();
		int divider = 0;
		if (columnCount < 4)
			divider = columnCount;
		else
			divider = 4;

		for (; tc.hasMoreElements();)
			tc.nextElement().setPreferredWidth(537 / divider);
	}

	public ArrayList<StudentMainTableDTO> getStudents() {
		return students;
	}

	private String[][] getStudentsForModel() {
		int columnCount = (getColumnCount() != 0) ? getColumnCount() : 3;
		String[][] forRet = new String[students.size()][columnCount];
		int i = 0;

		for (StudentMainTableDTO student : students) {
			int j = 0;
			if (ShowViewData.getValue(ShowViewData.D_BROJINDEKSA))
				forRet[i][j++] = student.getBrojIndeksa();

			if (ShowViewData.getValue(ShowViewData.D_IME))
				forRet[i][j++] = student.getIme();

			if (ShowViewData.getValue(ShowViewData.D_PREZIME))
				forRet[i][j++] = student.getPrezime();

			if (ShowViewData.getValue(ShowViewData.D_ELEKTRIJADA))
				forRet[i][j++] = student.getElektrijada();

			if (ShowViewData.getValue(ShowViewData.D_KOMENTAR))
				forRet[i][j++] = student.getElektrijada();

			i++;
		}
		return forRet;
	}

	public MainTable() {
		super();
		map.put("Indeks", ShowViewData.D_BROJINDEKSA);
		map.put("Ime", ShowViewData.D_IME);
		map.put("Prezime", ShowViewData.D_PREZIME);
		map.put("Elektrijada", ShowViewData.D_ELEKTRIJADA);
		map.put("Komentar", ShowViewData.D_KOMENTAR);
		initView();
	}

	// TO-DO sa studentima

	private void initView() {
		// Dovuci podatke i prikazati
	}

	public boolean addStudent(StudentMainTableDTO student) {
		if (!StudentsForMainTable.getAllStudents().contains(student)) {
			StudentsForMainTable.getAllStudents().add(student);
			return true;
		}
		return false;
	}
	
	public void deleteStudents(int[] selectedRows) {
		ArrayList<StudentMainTableDTO> helpList = new ArrayList<StudentMainTableDTO>();
		for (int i : selectedRows) {
			StudentsForMainTable.getAllStudents().remove(students.get(i));
			helpList.add(students.get(i));
		}
		for (StudentMainTableDTO s : helpList)
			students.remove(s);
	}

}
