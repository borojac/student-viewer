package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;

public interface ElektrijadaDAO {

	public abstract ElektrijadaDTO getElektrijadaDTO(int idNaloga, String disciplina);
	public abstract List<ElektrijadaDTO> getListuElektrijada(int idNaloga);
	public abstract List<ElektrijadaDTO> getSveElektrijade();
}
