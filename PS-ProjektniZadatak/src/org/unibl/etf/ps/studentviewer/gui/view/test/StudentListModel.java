package org.unibl.etf.ps.studentviewer.gui.view.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

public class StudentListModel extends AbstractListModel<StudentNaTestuDTO> {

	private static final long serialVersionUID = 6353517380860715L;
	private List<StudentNaTestuDTO> data = new ArrayList<>();
	
	public void setData(List<StudentNaTestuDTO> data) {
		this.data = data;
		fireContentsChanged(this, 0, data.size() - 1);
	}
	
	public List<StudentNaTestuDTO> getData() {
		return data;
	}
	
	@Override
	public StudentNaTestuDTO getElementAt(int index) {
		if (index >= 0 && index < data.size())
			return data.get(index);
		return null;
	}

	@Override
	public int getSize() {
		return data.size();
	}
	
	@Override
	public void fireContentsChanged(Object obj, int index1, int index2) {
		super.fireContentsChanged(obj, index1, index2);
	}

}
