package org.unibl.etf.ps.studentviewer.gui;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class MainTable extends JTable {
	
	private ArrayList<StudentMainTableDTO> students = null;
	
	public void setStudents(ArrayList<StudentMainTableDTO> students) {
		this.students = students;
		MainTableModel model = (MainTableModel)getModel();
		model.setData(getStudentsForModel());
		model.setColumnIdentifiers(new String[]{"Indeks", "Ime", "Prezime"});
		repaint();
	}
	
	public void changeView() {
		TableColumnModel tcm = getColumnModel();
	}
	
	
	public ArrayList<StudentMainTableDTO> getStudents() {
		return students;
	}



	private String[][] getStudentsForModel(){
		String[][] forRet = new String[students.size()][3];
		int i = 0;
		for(StudentMainTableDTO student : students) {

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
