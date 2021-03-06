package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;

public interface DisciplinaDAO {
	public List<DisciplinaDTO> getDiscipline(int idElektrijade, int idNaloga);
	public List<DisciplinaDTO> getDisciplinePoElektrijadi(int idElektrijade);
	public boolean addDisciplinu(DisciplinaDTO disciplina);
	public boolean deleteDisciplinu(DisciplinaDTO disciplina);
	public List<DisciplinaDTO> getDisciplinePoZahtjevima(int id, int nalogId);
}
