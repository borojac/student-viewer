package org.unibl.etf.ps.studentviewer.logic.command;

public interface Command {

	public abstract void execute();
	public abstract void unExecute();
	public abstract void reExecute();
}
