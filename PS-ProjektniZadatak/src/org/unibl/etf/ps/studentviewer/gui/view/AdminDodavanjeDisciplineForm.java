package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.logic.controller.AdminDodavanjeDisciplineController;
import org.unibl.etf.ps.studentviewer.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import javax.swing.JTextField;

public class AdminDodavanjeDisciplineForm extends JFrame {

	private JPanel contentPane;
	private JPanel contentPane_1;
	
	private AdminDodavanjeDisciplineController adminDodavanjeDisciplineKontroler;
	private JComboBox elektrijadeCB;
	private NalogDTO nalogDTO;
	private JTextField textFieldNazivDiscipline;


	/**
	 * Create the frame.
	 */
	public AdminDodavanjeDisciplineForm() {
		adminDodavanjeDisciplineKontroler = new AdminDodavanjeDisciplineController(this);
		setTitle("Dodavanje discipline");
		this.nalogDTO = nalogDTO;
		
		setBounds(100, 100, 450, 270);
		contentPane = new JPanel();
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
						adminDodavanjeDisciplineKontroler.zatvoriProzor();
					}
				});
			}
		});

		JLabel lblNazivDiscipline = new JLabel("Naziv discipline:");
		lblNazivDiscipline.setBounds(20, 210, 200, 25);
		lblNazivDiscipline.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 15));
		lblNazivDiscipline.setForeground(Color.WHITE);

		JLabel lblIzborElektrijade = new JLabel("Izbor Elektrijade:");
		lblIzborElektrijade.setBounds(20, 210, 200, 25);
		lblIzborElektrijade.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 15));
		lblIzborElektrijade.setForeground(Color.WHITE);

		initElektrijadaComboBox();

		JButton posaljiZahtjev = new JButton("Dodaj");
		posaljiZahtjev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminDodavanjeDisciplineKontroler.dodajDisciplinu(elektrijadeCB, textFieldNazivDiscipline);
			}
		});
		posaljiZahtjev.setBounds(140, 430, 150, 35);
		
		textFieldNazivDiscipline = new JTextField();
		textFieldNazivDiscipline.setColumns(10);

		GroupLayout gl_contentPane_1 = new GroupLayout(contentPane_1);
		gl_contentPane_1.setHorizontalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane_1.createSequentialGroup()
							.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblIzborElektrijade)
								.addComponent(lblNazivDiscipline))
							.addGap(18)
							.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
								.addComponent(elektrijadeCB, 0, 264, Short.MAX_VALUE)
								.addComponent(textFieldNazivDiscipline, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPane_1.createSequentialGroup()
							.addComponent(posaljiZahtjev, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(156))))
		);
		gl_contentPane_1.setVerticalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIzborElektrijade)
						.addComponent(elektrijadeCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(39)
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNazivDiscipline)
						.addComponent(textFieldNazivDiscipline, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
					.addComponent(posaljiZahtjev)
					.addGap(19))
		);
		contentPane_1.setLayout(gl_contentPane_1);
	}

	private void initElektrijadaComboBox() {
		elektrijadeCB = new JComboBox<>();
		MySQLDAOFactory dao = new MySQLDAOFactory();
		ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
		ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO
				.getSveElektrijade();
		if (elektrijade.size() > 0) {
			for (ElektrijadaDTO el : elektrijade) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat newDf = new SimpleDateFormat("dd.MM.yyyy");
				java.util.Date datum = null;
				try {
					datum = df.parse(el.getDatum().toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				elektrijadeCB.addItem(el.getLokacija() + ", " + newDf.format(datum));
			}
			
		}
	}

	

	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}
}
