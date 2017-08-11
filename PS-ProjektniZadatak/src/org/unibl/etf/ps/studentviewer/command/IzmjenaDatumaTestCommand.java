package org.unibl.etf.ps.studentviewer.command;

import java.util.Calendar;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

import datechooser.beans.DateChooserCombo;

public class IzmjenaDatumaTestCommand extends TestCommand {
	
	private DateChooserCombo dateChooser;
	private Date prethodniDatum, sljedeciDatum;
	
	public IzmjenaDatumaTestCommand(TestDTO test, DateChooserCombo dateChooser) {
		super(test);
		this.dateChooser = dateChooser;
		prethodniDatum = test.getDatum();
		sljedeciDatum = dateChooser.getSelectedDate().getTime();
	}
	
	@Override
	public void execute() {
		this.test.setDatum(sljedeciDatum);
	}

	@Override
	public void unExecute() {

		this.test.setDatum(prethodniDatum);
		Calendar cal = Calendar.getInstance();
		cal.setTime(prethodniDatum);
		dateChooser.setSelectedDate(cal);

	}

	@Override
	public void reExecute() {

		this.test.setDatum(sljedeciDatum);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sljedeciDatum);
		dateChooser.setSelectedDate(cal);
		
	}

}
