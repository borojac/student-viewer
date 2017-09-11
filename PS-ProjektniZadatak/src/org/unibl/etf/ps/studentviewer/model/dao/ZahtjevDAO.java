package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;

public interface ZahtjevDAO {
	
	public boolean addZahtjev(ZahtjevDTO zahtjevDTO);
	public boolean updateZahtjev(ZahtjevDTO zahtjevDTO);
	public boolean deleteZahtjev(ZahtjevDTO zahtjevDTO);
	public ArrayList<ZahtjevDTO> getAllZahtjev();

}
