package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorDodavanjeStudijskogProgramaFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class AdministratorDodavanjeStudijskogProgramaForm extends JFrame {

	private JPanel contentPane;
	private JLabel nazivLbl;
	private JTextField nazivTf;
	private JLabel ectsLbl;
	private JTextField ectsTf;
	private JLabel ciklusLbl;
	private JComboBox<Short> ciklusCB;
	private JLabel trajanjeLbl;
	private JTextField trajanjeTf;
	private JLabel zvanjeLbl;
	private JTextField zvanjeTf;
	private ArrayList<Short> ciklusiList;
	private ArrayList<PredmetDTO> predmetiList;
	private JButton button;
	private AdministratorDodavanjeStudijskogProgramaFormController administratorDodavanjeStudijskogProgramaFormController;
	
	public AdministratorDodavanjeStudijskogProgramaForm() {
		
	}

	/**
	 * Create the frame.
	 */
	public AdministratorDodavanjeStudijskogProgramaForm(AdministratorFormController administratorFormController) {
		administratorDodavanjeStudijskogProgramaFormController = new AdministratorDodavanjeStudijskogProgramaFormController(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				AdministratorFormController.resetDodajStudProgOpened();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 450, 450);
		contentPane =new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel label = new JLabel("");
		label.setBounds(140, 0, 170, 120);
		try {
			BufferedImage headerImage = ImageIO.read(new File("img\\BellTower-RGB(JPG).jpg"));
			headerImage = Scalr.resize(headerImage, Scalr.Mode.FIT_EXACT, label.getWidth(), label.getHeight(), null);
			label.setIcon(new ImageIcon(headerImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setBackground(Color.WHITE);
		label_1.setOpaque(true);
		label_1.setBounds(0, 0, 140, 120);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setBackground(Color.WHITE);
		label_2.setOpaque(true);
		label_2.setBounds(310, 0, 140, 120);
		contentPane.add(label_2);
		
		initComponents();
		initButtonsListeners();
	}
	
	public void initComponents() {
		nazivLbl = new JLabel("Naziv studijskog programa");
 		nazivLbl.setBounds(15, 130, 250, 45);
 		nazivLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		nazivLbl.setForeground(Color.WHITE);
 		contentPane.add(nazivLbl);
 		
 		nazivTf = new JTextField();
		nazivTf.setBounds(265, 140, 160, 25);
		nazivTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(nazivTf);
		
		ectsLbl = new JLabel("Ukupno ECTS bodova");
		ectsLbl.setBounds(15, 180, 250, 45);
		ectsLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		ectsLbl.setForeground(Color.WHITE);
		contentPane.add(ectsLbl);
		
		ectsTf = new JTextField();
		ectsTf.setBounds(265, 190, 160, 25);
		ectsTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(ectsTf);
		
		
		MySQLDAOFactory factory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		ciklusCB = new JComboBox<>();
		ciklusiList = new ArrayList<>();
		predmetiList = predmetDAO.getAllPredmet();
		
		for(int i = 0; i < predmetiList.size(); i++) {
			if(!ciklusiList.contains(predmetiList.get(i).getCiklus())) {
				ciklusiList.add(predmetiList.get(i).getCiklus());
			}
		}
		
		Collections.sort(ciklusiList);
		
		for(int i = 0; i < ciklusiList.size(); i++) {
			ciklusCB.addItem(ciklusiList.get(i));
		}
		
		ciklusCB.setSelectedIndex(0);
		
		ciklusLbl = new JLabel("Ciklus");
 		ciklusLbl.setBounds(15, 230, 250, 45);
 		ciklusLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		ciklusLbl.setForeground(Color.WHITE);
 		contentPane.add(ciklusLbl);
 		
 		ciklusCB.setBounds(265, 240, 160, 25);
		contentPane.add(ciklusCB);
		
		trajanjeLbl = new JLabel("Trajanje");
		trajanjeLbl.setBounds(15, 280, 250, 45);
		trajanjeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		trajanjeLbl.setForeground(Color.WHITE);
 		contentPane.add(trajanjeLbl);
 		
 		trajanjeTf = new JTextField();
		trajanjeTf.setBounds(265, 290, 160, 25);
		trajanjeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(trajanjeTf);
		
		zvanjeLbl = new JLabel("Zvanje");
		zvanjeLbl.setBounds(15, 330, 250, 45);
		zvanjeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		zvanjeLbl.setForeground(Color.WHITE);
 		contentPane.add(zvanjeLbl);
 		
 		zvanjeTf = new JTextField();
		zvanjeTf.setBounds(265, 340, 160, 25);
		zvanjeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(zvanjeTf);
		
		button = new JButton("Potvrdi");
		button.setBounds(145, 380, 160, 25);
		contentPane.add(button);
		
	}
	
	private void initButtonsListeners() {
		
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				administratorDodavanjeStudijskogProgramaFormController.dodajStudProg();
			}
		});
		
	}
	
	public String getNazivSP() {
		return nazivTf.getText();
	}
	
	public int getEcts() {
		if("".equals(ectsTf.getText())) {
			return -1;
		}
		try {
			return Integer.parseInt(ectsTf.getText());
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Nekorektan unos ECTS bodova.");
		}
		return 0;
	}
	
	public short getCiklus() {
		return ciklusiList.get(ciklusCB.getSelectedIndex());
	}
	
	public short getTrajanje() {
		if("".equals(trajanjeTf.getText())) {
			return -1;
		}
		try {
			return Short.parseShort(trajanjeTf.getText());
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Nekorektan unos trajanja.");
		}
		return 0;
	}
	
	public String getZvanje() {
		return zvanjeTf.getText();
	}

}
