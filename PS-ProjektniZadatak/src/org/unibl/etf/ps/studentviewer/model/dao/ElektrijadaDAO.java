package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;

public interface ElektrijadaDAO {

	public abstract ElektrijadaDTO getElektrijadaZaNalogDTO(int idNaloga, String disciplina);
	public abstract ElektrijadaDTO getElektrijadaPoId(int idElektrijade);
	public abstract List<ElektrijadaDTO> getListuElektrijada(int idNaloga);
	public abstract List<ElektrijadaDTO> getSveElektrijade();
	public abstract boolean addElektrijada(ElektrijadaDTO elektrijadaDTO);
	public abstract boolean deleteElektrijada(ElektrijadaDTO elektrijadaDTO);
	public abstract boolean updateElektrijada(ElektrijadaDTO elektrijadaDTO);
}
