package org.unibl.etf.ps.studentviewer.gui.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.unibl.etf.ps.studentviewer.logic.controller.EksportStudentiZaElektrijaduController;
import org.unibl.etf.ps.studentviewer.logic.controller.ElektrijadaController;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class EksportStudentiZaElektrijaduForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private EksportStudentiZaElektrijaduForm eksportForma;
	private EksportStudentiZaElektrijaduController eksportKontroler;
	
	public EksportStudentiZaElektrijaduForm(ElektrijadaController elektrijadaKontroler, Logger logger) {
		setResizable(false);
		setTitle("Kreiraj");
		eksportForma = this;
		eksportKontroler = new EksportStudentiZaElektrijaduController(eksportForma);
		setBounds(100, 100, 273, 149);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(0, 0, 139));
		setContentPane(contentPane);
		
		JLabel lblNazivFajla = new JLabel("Naziv fajla:");
		lblNazivFajla.setForeground(Color.WHITE);
		lblNazivFajla.setFont(new Font("Century Gothic", Font.BOLD, 15));
		textField = new JTextField();
		textField.setColumns(10);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				eksportKontroler.zatvoriProzor(elektrijadaKontroler.getForma(),e);
			}
		});
		JButton btnKreiraj = new JButton("Kreiraj");
		btnKreiraj.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnKreiraj.setToolTipText("Kreiraj");
		btnKreiraj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					eksportKontroler.kreirajFajl(textField.getText(),elektrijadaKontroler);
					
				} catch (Exception e1) {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(eksportForma,
									"Eksportovanje nije uspjelo. Pogledajte log za detalje:\n" + new File("log" + "/" + ElektrijadaForm.class.getSimpleName() + ".log").getAbsolutePath(), 
									"Greška", 
									JOptionPane.ERROR_MESSAGE);
						}
					});

					logger.error("Eksportovanje nije uspjelo", e1);
					
				}
			}
		});
		
		JButton btnNazad = new JButton("Nazad");
		btnNazad.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNazad.setToolTipText("Nazad");
		btnNazad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eksportKontroler.opcijaNazad(elektrijadaKontroler);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNazivFajla)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(btnNazad, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
							.addComponent(btnKreiraj, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNazivFajla)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnKreiraj)
						.addComponent(btnNazad)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
