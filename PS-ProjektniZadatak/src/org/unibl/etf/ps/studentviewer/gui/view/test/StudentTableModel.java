package org.unibl.etf.ps.studentviewer.gui.view.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.logic.controller.test.TestController;
import org.unibl.etf.ps.studentviewer.logic.utility.command.IzmjenaBrojaBodovaTestCommand;
import org.unibl.etf.ps.studentviewer.logic.utility.command.IzmjenaKomentaraTestCommand;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.TestDTO;

public class StudentTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8429148563151885068L;

	private List<StudentNaTestuDTO> data = new ArrayList<>();
	
	private TestController testController = null;
	
	public TestController getTestController() {
		return testController;
	}

	public void setTestController(TestController testController) {
		this.testController = testController;
	}

	private String[] columns = { "Broj indeksa", "Ime", "Prezime", "Broj bodova", "Komentar" };

	public StudentTableModel(List<StudentNaTestuDTO> data) {
		super();
		setData(data);
	}
	
	public void setData(List<StudentNaTestuDTO> data) {
		this.data = data;
		fireTableDataChanged();
	}
	
	public List<StudentNaTestuDTO> getData() {
		return data;
	}

	public StudentNaTestuDTO getRowAt(int index) {
		if (index >= 0 && index < data.size())
			return data.get(index);
		return null;
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
	public String getColumnName(int index) {
		if (index >= 0 && index < columns.length)
			return columns[index];
		return null;
	}
	
	

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 3 || column == 4)
			return true;
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		StudentNaTestuDTO row = data.get(rowIndex);
		if (columnIndex == 0)
			return row.getBrojIndeksa();
		else if (columnIndex == 1)
			return row.getIme();
		else if (columnIndex == 2)
			return row.getPrezime();
		else if (columnIndex == 3)
			return new Integer(row.getBrojBodova());
		else if (columnIndex == 4)
			return row.getKomentar();
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		StudentNaTestuDTO student = data.get(rowIndex);
		if (columnIndex == 3 && aValue instanceof String) {
			try {
				int brBodova = Integer.parseInt((String) aValue);
				if (brBodova <=100 && brBodova >= 0 && brBodova != (int) getValueAt(rowIndex, columnIndex)) {
					testController.executeCommand(
							new IzmjenaBrojaBodovaTestCommand(student, brBodova));
					this.fireTableCellUpdated(rowIndex, columnIndex);
				}
			} catch (NumberFormatException ex) {}
		} else if (columnIndex == 4 && aValue instanceof String) {
			String komentar = (String) aValue;
			if (!komentar.equals((String) getValueAt(rowIndex, columnIndex))) {
				testController.executeCommand(
						new IzmjenaKomentaraTestCommand(student, komentar));
				this.fireTableCellUpdated(rowIndex, columnIndex);
			}
		}
	}
	
	

}
