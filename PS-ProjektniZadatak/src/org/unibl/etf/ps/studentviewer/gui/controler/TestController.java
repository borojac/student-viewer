package org.unibl.etf.ps.studentviewer.gui.controler;

import java.awt.event.KeyEvent;
import java.util.Stack;

import org.unibl.etf.ps.studentviewer.command.Command;
import org.unibl.etf.ps.studentviewer.command.CommandStack;

public class TestController {

	private CommandStack undoStack = new CommandStack();
	private CommandStack redoStack = new CommandStack();
	
	private static TestController instance = null;
	
	public static TestController getInstance() {
		if (instance == null)
			instance = new TestController();
		return instance;
	}
	
	private TestController() {}
	
	public void focusLostAction(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_Z && ke.isControlDown()) {
			instance.undo();
		} else if (ke.getKeyCode() == KeyEvent.VK_Y && ke.isControlDown()) {
			instance.redo();
		}
	}
	
	public void undo() {
		if (!undoStack.isEmpty()) {
			Command command = undoStack.pop();
			command.unExecute();
			redoStack.push(command);
		}
	}
	
	public void executeCommand(Command command) {
		command.execute();
		undoStack.push(command);
	}
	
	public void redo() {
		if (!redoStack.isEmpty()) {
			Command command = redoStack.pop();
			command.reExecute();
			undoStack.push(command);
		}
	}
	
}
