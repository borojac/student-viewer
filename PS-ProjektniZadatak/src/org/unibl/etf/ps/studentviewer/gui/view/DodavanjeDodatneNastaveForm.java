package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.gui.DodatnaNastavaDataTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.DodavanjeDodatneNastaveController;
import org.unibl.etf.ps.studentviewer.logic.controller.ElektrijadaController;

public class DodavanjeDodatneNastaveForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2691244454059099527L;
	private JPanel contentPane;
	private JTextField textFieldNaziv;
	private JTextField textFieldDatum;
	private JTable tableNastavneTeme;
	private ElektrijadaController elektrijadaController;
	private DodatnaNastavaDataTableModel dodatnaNastavaDataModel;
	private DodavanjeDodatneNastaveController dodatnaNastavaController;
	private DodavanjeDodatneNastaveForm nastavaForm;

	/**
	 * Create the frame.
	 * 
	 * @param tableNastavneTeme
	 * @param forma
	 * @param undoRedo
	 * @param dataModel
	 */
	public DodavanjeDodatneNastaveForm(JTable tableNastavneTeme, ElektrijadaController kontroler,
			DodatnaNastavaDataTableModel dodatnaNastavaDataModel) {
		this.tableNastavneTeme = tableNastavneTeme;
		this.elektrijadaController = kontroler;
		this.dodatnaNastavaDataModel = dodatnaNastavaDataModel;
		nastavaForm = this;
		dodatnaNastavaController = new DodavanjeDodatneNastaveController(nastavaForm);
		setResizable(false);
		setTitle("Dodavanje dodatne nastave");
		setBounds(100, 100, 270, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(0, 0, 139));
		setContentPane(contentPane);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dodatnaNastavaController.zatvoriProzor(elektrijadaController.getForma(), e);
			}
		});
		JLabel lblNaziv = new JLabel("Naziv: ");
		lblNaziv.setForeground(Color.WHITE);
		lblNaziv.setFont(new Font("Century Gothic", Font.BOLD, 15));
		textFieldNaziv = new JTextField();
		textFieldNaziv.setColumns(10);

		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setForeground(Color.WHITE);
		lblDatum.setFont(new Font("Century Gothic", Font.BOLD, 15));

		textFieldDatum = new JTextField();
		textFieldDatum.setColumns(10);
		textFieldDatum.setEditable(false);
		JLabel lblNapomena = new JLabel("Napomena:");
		lblNapomena.setForeground(Color.WHITE);
		lblNapomena.setFont(new Font("Century Gothic", Font.BOLD, 15));

		JTextArea textAreaNapomena = new JTextArea();
		textAreaNapomena.setLineWrap(true);

		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDodaj.setToolTipText("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodatnaNastavaController.dodajDodatnuNastavuControl(textFieldNaziv, textFieldDatum, textAreaNapomena,
						tableNastavneTeme, kontroler, dodatnaNastavaDataModel);

			}
		});

		JButton btnNewButton = new JButton("Nazad");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setToolTipText("Nazad");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				kontroler.getForma().setEnabled(true);
			}
		});

		JButton btnNewButton_1 = new JButton("Datum");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setToolTipText("Izbor datuma");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodatnaNastavaController.izborDatuma(nastavaForm, textFieldDatum);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addComponent(lblDatum).addGap(18)
										.addComponent(textFieldDatum, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
						.addComponent(btnNewButton_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 85,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addComponent(lblNapomena)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(textAreaNapomena))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
								.addComponent(btnDodaj, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addComponent(lblNaziv).addGap(18)
										.addComponent(textFieldNaziv, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(28)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldNaziv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNaziv))
				.addGap(28)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblDatum).addComponent(
						textFieldDatum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addGap(11).addComponent(btnNewButton_1).addGap(19)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNapomena)
						.addComponent(textAreaNapomena, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGap(26).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnDodaj)
						.addComponent(btnNewButton))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

}
