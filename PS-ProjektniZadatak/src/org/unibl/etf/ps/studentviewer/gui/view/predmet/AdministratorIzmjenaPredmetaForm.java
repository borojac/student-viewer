package org.unibl.etf.ps.studentviewer.gui.view.predmet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.unibl.etf.ps.studentviewer.logic.controller.predmet.AdministratorIzmjenaPredmetaFormController;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class AdministratorIzmjenaPredmetaForm extends AdministratorDodavanjePredmetaForm {
	
	private AdministratorIzmjenaPredmetaFormController administratorIzmjenaPredmetaFormController;
	private PredmetDTO predmetDTO;
	
	private JButton izmjeniBtn;

	public AdministratorIzmjenaPredmetaForm(AdminPredmetiForm adminPredmetiForm) {
		super(adminPredmetiForm);
		
		setTitle("Izmjena predmeta");
		
		administratorIzmjenaPredmetaFormController = new AdministratorIzmjenaPredmetaFormController(this);
		predmetDTO = adminPredmetiForm.getSelectedPredmet();
		
		sifraTf.setText(predmetDTO.getSifraPredmeta());
		nazivTf.setText(predmetDTO.getNazivPredmeta());
		ectsTf.setText(String.valueOf(predmetDTO.getEcts()));
		semestarTf.setText(String.valueOf(predmetDTO.getSemestar()));
		if(predmetDTO.getTipPredmeta() == 'O') {
			obavezan.setSelected(true);
		} else {
			izborni.setSelected(true);
		}
		skolskeGodineCB.setSelectedItem(predmetDTO.getSkolskaGodina());
		studijskiProgramiCB.setSelectedItem(predmetDTO.getNazivSP());
		ciklusiCB.setSelectedItem(predmetDTO.getCiklus());
		
		contentPane.remove(potvrdiBtn);
		
		izmjeniBtn = new JButton("Potvrdi");
		izmjeniBtn.setBounds(95, 580, 170, 25);
		contentPane.add(izmjeniBtn);
		
		izmjeniBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				administratorIzmjenaPredmetaFormController.izmjeniPredmet();
			}
		});
	}
	
	public PredmetDTO getPredmetDTO() {
		return predmetDTO;
	}

}
