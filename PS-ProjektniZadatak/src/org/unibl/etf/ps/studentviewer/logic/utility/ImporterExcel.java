/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImporterExcel {
	File chosenFile;
	public ImporterExcel() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		fileChooser.showOpenDialog(null);
		chosenFile = fileChooser.getSelectedFile();
	}

	/**
	 * 
	 * @param numOfColumns Broj kolona koje treba da se iscitaju iz selektovanog fajla
	 * @return 
	 * @throws IOException
	 */
	public ArrayList<String[]> getData(int numOfColumns) throws IOException {
		ArrayList<String[]> data = new ArrayList<String[]>();

		if (chosenFile != null && chosenFile.exists() && chosenFile.getAbsolutePath().endsWith(".xls")) {
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(chosenFile));
			HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); ++i) {
				HSSFRow row = sheet.getRow(i);
				String[] tmp = new String[numOfColumns];
				for(int j = 0; j < numOfColumns; j++) {
					HSSFCell cell = row.getCell(j);
					if(cell != null) {
						switch(cell.getCellTypeEnum()) { 
						/**
						 * @author Dejan Mijic
						 */
						case NUMERIC:
							Double d = Double.parseDouble(String.valueOf(cell.getNumericCellValue()));
							int k = d.intValue();
							tmp[j] = String.valueOf(k).trim();
							break;
						/**
						 * 
						 */
						case STRING:
							tmp[j] = cell.getStringCellValue().trim();
							break;
						default:
							break;
						}
					}
					else{
						tmp[j] = "";
					}
				}
				data.add(tmp);
			}
	
			workbook.close();
			fileSystem.close();
		
		} else if (chosenFile != null && chosenFile.exists() && chosenFile.getAbsolutePath().endsWith(".xlsx")) {
			InputStream chosenFileInputStream = new BufferedInputStream(new FileInputStream(chosenFile));
			XSSFWorkbook workbook = new XSSFWorkbook(chosenFileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); ++i) {
				Row row = sheet.getRow(i);
				String[] tmp = new String[numOfColumns];
				for(int j = 0; j < numOfColumns; j++) {
					Cell cell = row.getCell(j);
					if(cell != null) {
						switch(cell.getCellTypeEnum()) { 
						/**
						 * @author Dejan Mijic
						 */
						case NUMERIC:
							Double d = Double.parseDouble(String.valueOf(cell.getNumericCellValue()));
							int k = d.intValue();
							tmp[j] = String.valueOf(k).trim();
							break;
						/**
						 * 
						 */
						case STRING:
							tmp[j] = cell.getStringCellValue().trim();
							break;
						default:
							break;
						}
					}
					else{
						tmp[j] = "";
					}
				}
				data.add(tmp);
			}

			workbook.close();
			chosenFileInputStream.close();
		}
		return data;
	}
}
