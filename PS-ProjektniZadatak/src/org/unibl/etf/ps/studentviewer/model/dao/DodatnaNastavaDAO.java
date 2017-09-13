package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public interface DodatnaNastavaDAO {

	public List<DodatnaNastavaDTO> getDodatneNastave(int elektrijadId, int nalogId, String naziv);

	public boolean azurirajDodatnuNastavu(DodatnaNastavaDTO dodatnaNastava);

	public boolean dodajDodatnuNastavu(DodatnaNastavaDTO dodatnaNastava);

	public boolean obrisiDodatnuNastavu(int idDodatneNastave);
}
