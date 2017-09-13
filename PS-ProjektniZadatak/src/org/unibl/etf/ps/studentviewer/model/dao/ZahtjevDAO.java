package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;

public interface ZahtjevDAO {
	
	public boolean addZahtjev(ZahtjevDTO zahtjevDTO);
	public boolean updateZahtjev(ZahtjevDTO zahtjevDTO);
	public boolean deleteZahtjev(ZahtjevDTO zahtjevDTO);
	public ArrayList<ZahtjevDTO> getAllZahtjev();
	public ArrayList<PredmetDTO> getPredmeteSaZahtjevomZaDan(int nalogId, Date date);

}
