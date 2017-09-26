package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDTO;

public interface ZahtjevDAO {
	
	public boolean addZahtjev(ZahtjevDTO zahtjevDTO);
	public boolean updateZahtjev(ZahtjevDTO zahtjevDTO);
	public boolean deleteZahtjev(ZahtjevDTO zahtjevDTO);
	public boolean deleteZahtjeve(PredmetDTO predmetDTO);
	public boolean deleteZahtjeve(PredmetDTO predmetDTO, NalogDTO nalogDTO);
	public ArrayList<ZahtjevDTO> getAllZahtjev();
	public ArrayList<PredmetDTO> getPredmeteSaZahtjevomZaDan(int nalogId, Date date);

}
