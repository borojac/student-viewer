package org.unibl.etf.ps.studentviewer.model.dao;




import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDisciplinaDTO;

public interface ZahtjevDisciplinaDAO {
	public boolean addZahtjev(ZahtjevDisciplinaDTO zahtjevDTO);

	public ArrayList<ZahtjevDisciplinaDTO> getAllZahtjev();

	public boolean updateZahtjev(ZahtjevDisciplinaDTO zahtjevDTO);

	public boolean deleteZahtjev(ZahtjevDisciplinaDTO zahtjevDTO);

	public boolean deleteZahtjevPoElektrijadi(int id);

	public boolean deleteZahtjevPoDisciplini(DisciplinaDTO disciplinaDTO);

	public boolean deleteZahtjevPoNaloguIDisciplini(NalogDTO nalogDTO, DisciplinaDTO disciplinaDTO);
}

