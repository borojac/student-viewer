package org.unibl.etf.ps.studentviewer.command;

import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public abstract class TestCommand implements Command {
	
	protected TestDTO test;
	
	public TestCommand(TestDTO test) {
		this.test = test;
	}

}
