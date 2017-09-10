/**
 *@author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.exec.addstudentexec;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.exec.Exec;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class ChangeExec extends Exec {
	ArrayList<String> params = new ArrayList<String>();
	
	public ChangeExec(MainFormController mainFormController, ArrayList<String> paramList, StudentMainTableDTO student, int numInList) {

			this.mainFormControler = mainFormController;
			for (String ob : paramList) {
				this.params.add(ob.trim());
			}
			int valid = checkParams();
			if(valid == 0) {
				
				student.setIme(params.get(0));
				student.setPrezime(params.get(1));
				student.setBrojIndeksa(params.get(2)); //setovanje izmjena podataka u studentu
			//TODO	poziv metode koja ce to sacuvati u bazu
			//TODO	poziv metode koja ce azurirati prikaz u tabeli
				
				final String message = "Uspjesno cuvanje!";
				JOptionPane.showMessageDialog(null, message);
//				MainFormController.resetChangeFormOpened();
				}
			else if(valid == 1) {
				final String message = "Pogresan unos za ime studenta!";
				JOptionPane.showMessageDialog(null, message);
//				MainFormController.resetChangeFormOpened();
//				this.mainFormControler.createChangeForm(numInList);
			}else if(valid == 2) {
				final String message = "Pogresan unos za prezime studenta!";
				JOptionPane.showMessageDialog(null, message);
//				this.mainFormControler.createChangeForm(numInList);
			}else if(valid == 3) {
				final String message = "Pogresan unos za broj indeksa! "
						+ "Morate unijeti tacno jedan karakter '/'";
				JOptionPane.showMessageDialog(null, message);
//				this.mainFormControler.createChangeForm(numInList);
			}
		}

		private int checkParams() {
			String param1 = params.get(0);
			if (param1.length() < 2 || param1.length() > 20) return 1;
			char[] ime = param1.toCharArray();
			for (char c : ime) {
				if (!Character.isLetter(c))
					return 1; //pogresan unos imena
			}
			param1 = param1.substring(0, 1).toUpperCase() + param1.substring(1).toLowerCase();
			params.set(0, param1);
			

			String param2 = params.get(1);
			if (param2.length() < 2 || param1.length() > 30) return 2;
			char[] prezime = param2.toCharArray();
			for (char c : prezime) {
				if (!Character.isLetter(c))
					if(c != ' ' || c != '-')
					return 2; //pogresan unos prezimena
			}
			param2 = param2.substring(0, 1).toUpperCase() + param2.substring(1).toLowerCase();
			params.set(1, param2);

			String param3 = params.get(2);
			if(!param3.contains("/"))return 3; //mora biti '/' u broju indeksa
			
			char[] index = param3.toCharArray();
			int numOfSlashes = 0;
			for (char c : index) {
				if (!Character.isDigit(c))
					if (c == '/') {
						numOfSlashes++;
						if (numOfSlashes > 1)
							return 3; //vise od jedan '/' karakter u indeksu
					} else {
						return 3; //pogresan unos indeksu
					}
			}
			return 0; //ispravan unos
		}
		
	
}