package org.unibl.etf.ps.studentviewer.gui.view.elektrijada;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.logic.controller.elektrijada.AdminDodavanjeElektrijadeController;

import javax.swing.JTextField;

public class AdminDodavanjeElektrijadeForm extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8334947459282519047L;
	private JPanel contentPane_1;
	
	private AdminDodavanjeElektrijadeController dodavanjeElektrijadeKontroler;
	private JTextField textFieldDatum;
	private JTextField textFieldLokacija;
	
	/**
	 * Create the frame.
	 */
	public AdminDodavanjeElektrijadeForm() {
		dodavanjeElektrijadeKontroler = new AdminDodavanjeElektrijadeController(this);
		setTitle("Dodavanje Elektrijade");
		
		setBounds(100, 100, 450, 270);
		new JPanel();
		contentPane_1 = new JPanel();
		contentPane_1.setBackground(new Color(0, 0, 139));
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane_1);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						dodavanjeElektrijadeKontroler.zatvoriProzor();
					}
				});
			}
		});

		JLabel lblNazivDiscipline = new JLabel("Lokacija:");
		lblNazivDiscipline.setBounds(20, 210, 200, 25);
		lblNazivDiscipline.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 15));
		lblNazivDiscipline.setForeground(Color.WHITE);

		JLabel lblIzborElektrijade = new JLabel("Datum Elektrijade:");
		lblIzborElektrijade.setBounds(20, 210, 200, 25);
		lblIzborElektrijade.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 15));
		lblIzborElektrijade.setForeground(Color.WHITE);

		
		JButton dodaj = new JButton("Dodaj");
		dodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodavanjeElektrijadeKontroler.dodajElektrijadu(textFieldDatum,textFieldLokacija);
			}
		});
		dodaj.setBounds(140, 430, 150, 35);
		
		textFieldDatum = new JTextField();
		textFieldDatum.setColumns(10);
		
		textFieldLokacija = new JTextField();
		textFieldLokacija.setColumns(10);
		
		JLabel lblFormaUnosaDatuma = new JLabel("Forma unosa datuma: \"yyyy-MM-dd\"");
		lblFormaUnosaDatuma.setFont(new Font("Century Gothic", Font.BOLD, 11));
		lblFormaUnosaDatuma.setForeground(Color.WHITE);

		GroupLayout gl_contentPane_1 = new GroupLayout(contentPane_1);
		gl_contentPane_1.setHorizontalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane_1.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, gl_contentPane_1.createSequentialGroup()
								.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
									.addComponent(lblIzborElektrijade)
									.addComponent(lblNazivDiscipline))
								.addGap(18)
								.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
									.addComponent(textFieldLokacija, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
									.addComponent(textFieldDatum, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
								.addContainerGap())
							.addGroup(gl_contentPane_1.createSequentialGroup()
								.addComponent(lblFormaUnosaDatuma)
								.addGap(39)))
						.addGroup(Alignment.TRAILING, gl_contentPane_1.createSequentialGroup()
							.addComponent(dodaj, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(156))))
		);
		gl_contentPane_1.setVerticalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIzborElektrijade)
						.addComponent(textFieldDatum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFormaUnosaDatuma)
					.addGap(19)
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNazivDiscipline)
						.addComponent(textFieldLokacija, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
					.addComponent(dodaj)
					.addGap(19))
		);
		contentPane_1.setLayout(gl_contentPane_1);
	}
}
