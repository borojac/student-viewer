package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;

public interface DisciplinaDAO {
	public List<DisciplinaDTO> getDiscipline(int idElektrijade, int idNaloga);
	public List<DisciplinaDTO> getDisciplinePoElektrijadi(int idElektrijade);
	public boolean addDisciplinu(DisciplinaDTO disciplina);
}
