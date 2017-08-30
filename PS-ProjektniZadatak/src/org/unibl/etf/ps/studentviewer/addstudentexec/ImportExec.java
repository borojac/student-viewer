/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.addstudentexec;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.unibl.etf.ps.studentviewer.exec.Exec;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormController;
public class ImportExec extends Exec {
	public ImportExec(MainFormController mainFormController) {
		// TODO Auto-generated constructor stub
		this.mainFormControler = mainFormController;

		FileDialog fileDialog = new FileDialog(new JFrame());
		fileDialog.setVisible(true);
		File file;
		try {
			file = new File(fileDialog.getFile());
			if (file != null) {
				Vector<Vector<String>> vector = new Vector<Vector<String>>();
				if (file.getName().endsWith(".pdf")) {
					
				} else if (file.getName().endsWith(".xlsx")) {
					 File myFile = file;
					    FileInputStream fis = new FileInputStream(myFile);

					    // Finds the workbook instance for XLSX file
					    XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

					    // Return first sheet from the XLSX workbook
					    XSSFSheet mySheet = myWorkBook.getSheetAt(0);

					    // Get iterator to all the rows in current sheet
					    Iterator<Row> rowIterator = mySheet.iterator();

					    // Traversing over each row of XLSX file
					    while (rowIterator.hasNext()) {
					        Row row = rowIterator.next();

					        // For each row, iterate through each columns
					        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();
					        while (cellIterator.hasNext()) {

					            Cell cell = (Cell) cellIterator.next();


					            
					        }
					        System.out.println("fd");
					    }
				}
			}
		}catch(Exception e) {}
	}
}
