package org.unibl.etf.ps.studentviewer.logic.utility.command;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.TestDTO;

public abstract class TestCommand implements Command {
	
	protected TestDTO test;
	
	public TestCommand(TestDTO test) {
		this.test = test;
	}

}
