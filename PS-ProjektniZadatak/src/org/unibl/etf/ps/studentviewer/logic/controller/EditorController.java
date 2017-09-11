package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.gui.view.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;



public class EditorController {
	public void zatvoriProzor(WindowEvent e, ElektrijadaForm forma) {
		forma.setEnabled(true);
        e.getWindow().dispose();
	}

	public void izvrsiIzmjene(boolean izbor, ElektrijadaController elektrijadaController, JTable target, JTextArea textArea, JFrame unosTeksta, AbstractTableModel dataModel, ElektrijadaForm forma) {
		if (!izbor){
			String izmjena = "";
			String procitano = textArea.getText();
			if (procitano.length() > 0){
				int row = target.getSelectedRow();
		        int column = target.getSelectedColumn();
		        DodatnaNastavaDTO dodatnaNastava = elektrijadaController.listaDodatnihNastava.get(row);
		        if (column == 0){
		        	
		        	elektrijadaController.izmjenaNazivaDodatneNastave(dodatnaNastava, procitano);
		        }
		        else if (column == 1){
		        	if (elektrijadaController.validnostDatuma(procitano)){
		        		
		        		elektrijadaController.izmjenaDatumaDodatneNastave(dodatnaNastava, procitano);
		        	}else
		        		JOptionPane.showMessageDialog(unosTeksta, "Greska u formi datuma.");
		        }
		        else if (column == 2){
		        	
		        	elektrijadaController.izmjenaNapomeneDodatneNastave(dodatnaNastava, procitano);
		        }
		       
				target.setModel(dataModel);
				unosTeksta.setVisible(false);
				unosTeksta.dispose();
				forma.setEnabled(true);		
			}
		}else {
			String izmjena = "";
			String procitano = textArea.getText();
			if (procitano.length() > 0){
				 int row = target.getSelectedRow();
		         int column = target.getSelectedColumn();
		         StudentZaElektrijaduDTO st = elektrijadaController.listaStudenata.get(row);
		        if (column == 3){				        
		        	elektrijadaController.izmjenaPodatkaNapomena(st, procitano);
		        }
		       
		        target.setModel(dataModel);
				unosTeksta.setVisible(false);
				unosTeksta.dispose();
				forma.setEnabled(true);		
			}
		}
	}
}
