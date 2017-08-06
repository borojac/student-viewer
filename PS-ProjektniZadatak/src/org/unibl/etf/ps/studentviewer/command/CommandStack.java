package org.unibl.etf.ps.studentviewer.command;

import java.util.ArrayDeque;

public class CommandStack extends ArrayDeque<Command> {

	private static final long serialVersionUID = 3285534697551635507L;
	private static final int MAX_SIZE = 25;
	
	public void push(Command command) {
		if (super.size() == MAX_SIZE)
			super.removeLast();
		super.push(command);
	}
	 
	public Command pop() {
		if (!super.isEmpty())
			return super.pop();
		return null;
	}

}
