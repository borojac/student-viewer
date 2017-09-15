package org.unibl.etf.ps.studentviewer.model.dao;

import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;

public interface ElektrijadaDAO {

	public abstract ElektrijadaDTO getElektrijadaDTO(int idNaloga, String disciplina);
}
