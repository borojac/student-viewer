package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public interface NalogDAO {
	
	public NalogDTO getNalog(String korisnickoIme, String lozinka);
	public boolean addNalog(NalogDTO nalog);
	public boolean updateNalog(NalogDTO nalog);
	public boolean addPredmet(PredmetDTO predmet, NalogDTO nalog);
	public boolean removePredmet(PredmetDTO predmet, NalogDTO nalog);
	public ArrayList<PredmetDTO> getPredmeteNaNalogu(int nalogId);

}
