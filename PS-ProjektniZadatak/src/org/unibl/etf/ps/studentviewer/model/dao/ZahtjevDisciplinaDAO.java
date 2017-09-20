package org.unibl.etf.ps.studentviewer.model.dao;




import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDisciplinaDTO;

public interface ZahtjevDisciplinaDAO {
	public boolean addZahtjev(ZahtjevDisciplinaDTO zahtjevDTO);

	public ArrayList<ZahtjevDisciplinaDTO> getAllZahtjev();

	public boolean updateZahtjev(ZahtjevDisciplinaDTO zahtjevDTO);

	public boolean deleteZahtjev(ZahtjevDisciplinaDTO zahtjevDTO);
}

