package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;

public interface DisciplinaDAO {
	public List<String> getDiscipline(int idNaloga);
	public DisciplinaDTO getDisciplina(int idNaloga, int idElektrijade);
}
