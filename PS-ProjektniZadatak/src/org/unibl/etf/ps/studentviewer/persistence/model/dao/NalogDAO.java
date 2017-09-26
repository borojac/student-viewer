package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;

public interface NalogDAO {
	
	public NalogDTO getNalog(int nalogId);
	public NalogDTO getNalog(String korisnickoIme, String lozinka);
	public boolean checkNalog(String korisnickoIme);
	public boolean addNalog(NalogDTO nalog);
	public boolean updateNalog(NalogDTO nalog);
	public boolean addPredmet(PredmetDTO predmet, NalogDTO nalog);
	public boolean addDisciplinuNaNalog(DisciplinaDTO disciplinaDTO, NalogDTO nalog);
	public boolean removePredmet(PredmetDTO predmet, NalogDTO nalog);
	public boolean removePredmete(PredmetDTO predmetDTO);
	public ArrayList<PredmetDTO> getPredmeteNaNalogu(int nalogId);
	public boolean removeDisciplina(DisciplinaDTO disciplina, NalogDTO nalog);
	public boolean ukloniZaduzenja(int id);
	public boolean ukloniZaduzenjaPoDisciplini(DisciplinaDTO disciplinaDTO);
}
