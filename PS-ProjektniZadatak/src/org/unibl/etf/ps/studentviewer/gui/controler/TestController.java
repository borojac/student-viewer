package org.unibl.etf.ps.studentviewer.gui.controler;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JFileChooser;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.unibl.etf.ps.studentviewer.command.Command;
import org.unibl.etf.ps.studentviewer.command.CommandStack;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.TestDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

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
	
	public List<StudentNaTestuDTO> importFromExcel() throws FileNotFoundException, IOException {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		fileChooser.showOpenDialog(null);
		File chosenFile = fileChooser.getSelectedFile();
		List<StudentNaTestuDTO> data = new ArrayList<>();
		
		
		POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(chosenFile));
		HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rowIndex = 0;
		for (int i = 0; i < sheet.getPhysicalNumberOfRows(); ++i) {
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell = row.getCell(1);
			if ("Prezime".equalsIgnoreCase(cell.getStringCellValue().trim())) {
				rowIndex = i + 1;
				break;
			}
		}
		
		for (int i = rowIndex; i < sheet.getPhysicalNumberOfRows(); ++i) {
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell = row.getCell(0);
			String brojIndeksa = cell.getStringCellValue().trim();

			// TODO - potreban StudentDAO
			
		}
		
		return data;
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
