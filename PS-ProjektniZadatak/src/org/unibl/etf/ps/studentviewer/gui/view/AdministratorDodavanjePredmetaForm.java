package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorDodavanjePredmetaFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class AdministratorDodavanjePredmetaForm extends JFrame {

	private JPanel contentPane;
	private JLabel sifraLbl, ectsLbl, tipPredmetaLbl, nazivSPLbl;
	private JLabel nazivLbl, semestarLbl, skolskaGodinaLbl, ciklusLbl;
	private JTextField sifraTf;
	private JTextField nazivTf;
	private JTextField ectsTf;
	private JTextField semestarTf;
	private JRadioButton obavezan, izborni;
	private ButtonGroup bg;
	AdministratorFormController administratorFormController;
	private JButton potvrdiBtn;
	AdministratorDodavanjePredmetaFormController administratorDodavanjePredmetaFormController;
	
	private JComboBox<Short> ciklusiCB;
	private JComboBox<String> studijskiProgramiCB;
	private JComboBox<String> skolskeGodineCB;
	private ArrayList<PredmetDTO> predmetiList;
	private ArrayList<Short> ciklusiList;
	private ArrayList<String> studijskiProgramiList;
	private ArrayList<String> skolskeGodineList;

	
	public AdministratorDodavanjePredmetaForm(AdministratorFormController administratorFormController) {
		
		administratorDodavanjePredmetaFormController = new AdministratorDodavanjePredmetaFormController(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				administratorFormController.resetAddFormOpened();
				administratorFormController.resetChooseAddTypeFormOpened();
			}
		});
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 650);
		contentPane =new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel label = new JLabel("");
		label.setBounds(90, 0, 171, 120);
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
		label_1.setBounds(0, 0, 95, 120);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setBackground(Color.WHITE);
		label_2.setOpaque(true);
		label_2.setBounds(241, 0, 117, 120);
		contentPane.add(label_2);
		
		initComponents();
		initButtonsListeners();
		initComboBoxListener();
		administratorDodavanjePredmetaFormController.postaviStudijskePrograme(studijskiProgramiCB, getCiklus());
	}
	
	private void initComponents() {
		sifraLbl = new JLabel("Šifra predmeta");
 		sifraLbl.setBounds(15, 130, 250, 45);
 		sifraLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		sifraLbl.setForeground(Color.WHITE);
 		contentPane.add(sifraLbl);
 		
 		sifraTf = new JTextField();
		sifraTf.setBounds(175, 140, 160, 25);
		sifraTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(sifraTf);
		
		nazivLbl = new JLabel("Naziv predmeta");
 		nazivLbl.setBounds(15, 185, 250, 45);
 		nazivLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		nazivLbl.setForeground(Color.WHITE);
 		contentPane.add(nazivLbl);
 		
 		nazivTf = new JTextField();
		nazivTf.setBounds(175, 195, 160, 25);
		nazivTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(nazivTf);
		
		ectsLbl = new JLabel("ECTS bodovi");
 		ectsLbl.setBounds(15, 240, 250, 45);
 		ectsLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		ectsLbl.setForeground(Color.WHITE);
 		contentPane.add(ectsLbl);
 		
 		ectsTf = new JTextField();
		ectsTf.setBounds(175, 250, 160, 25);
		ectsTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(ectsTf);
		
		semestarLbl = new JLabel("Semestar");
 		semestarLbl.setBounds(15, 295, 250, 45);
 		semestarLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		semestarLbl.setForeground(Color.WHITE);
 		contentPane.add(semestarLbl);
 		
 		semestarTf = new JTextField();
		semestarTf.setBounds(175, 305, 160, 25);
		semestarTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(semestarTf);
		
		tipPredmetaLbl = new JLabel("Tip predmeta");
 		tipPredmetaLbl.setBounds(15, 350, 250, 45);
 		tipPredmetaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		tipPredmetaLbl.setForeground(Color.WHITE);
 		contentPane.add(tipPredmetaLbl);
 		
 		obavezan = new JRadioButton("Obavezan");
        obavezan.setBounds(175, 355, 160, 15);
        izborni = new JRadioButton("Izborni");
        izborni.setBounds(175, 375, 160, 15);  
 		bg = new ButtonGroup(); 
        bg.add(obavezan); 
        bg.add(izborni);
        contentPane.add(obavezan);
        contentPane.add(izborni);
        obavezan.setSelected(true);
 		
 		skolskaGodinaLbl = new JLabel("Školska godina");
 		skolskaGodinaLbl.setBounds(15, 405, 250, 45);
 		skolskaGodinaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		skolskaGodinaLbl.setForeground(Color.WHITE);
 		contentPane.add(skolskaGodinaLbl);
 		
 		MySQLDAOFactory factory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
 		
		ciklusiCB = new JComboBox<>();
		studijskiProgramiCB = new JComboBox<>();
		skolskeGodineCB = new JComboBox<>();
 		predmetiList = predmetDAO.getAllPredmet();
		
		ciklusiList = new ArrayList<>();
		studijskiProgramiList = new ArrayList<>();
		skolskeGodineList = new ArrayList<>();
		
		for(int i = 0; i < predmetiList.size(); i++) {
			if(!ciklusiList.contains(predmetiList.get(i).getCiklus())) {
				ciklusiList.add(predmetiList.get(i).getCiklus());
			}
		}
		
		for(int i = 0; i < ciklusiList.size(); i++) {
			ciklusiCB.addItem(ciklusiList.get(i));
		}
		
		ciklusiCB.setSelectedIndex(0);
		
		for(int i = 0; i < predmetiList.size(); i++) {
			if(!studijskiProgramiList.contains(predmetiList.get(i).getNazivSP())) {
				studijskiProgramiList.add(predmetiList.get(i).getNazivSP());
			}
			if(!skolskeGodineList.contains(predmetiList.get(i).getSkolskaGodina())) {
				skolskeGodineList.add(predmetiList.get(i).getSkolskaGodina());
			}
		}
		
		for(int i = 0; i < skolskeGodineList.size(); i++) {
			skolskeGodineCB.addItem(skolskeGodineList.get(i));
		}
		
		skolskeGodineCB.setBounds(175, 415, 160, 25);
		contentPane.add(skolskeGodineCB);
		
		nazivSPLbl = new JLabel("Studijski program");
 		nazivSPLbl.setBounds(15, 460, 250, 45);
 		nazivSPLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		nazivSPLbl.setForeground(Color.WHITE);
 		contentPane.add(nazivSPLbl);
 		
 		studijskiProgramiCB.setBounds(175, 470, 160, 25);
		contentPane.add(studijskiProgramiCB);
		
		ciklusLbl = new JLabel("Studijski ciklus");
 		ciklusLbl.setBounds(15, 515, 250, 45);
 		ciklusLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		ciklusLbl.setForeground(Color.WHITE);
 		contentPane.add(ciklusLbl);
 		
 		ciklusiCB.setBounds(175, 525, 160, 25);
		contentPane.add(ciklusiCB);
		
		potvrdiBtn = new JButton("Potvrdi");
		potvrdiBtn.setBounds(100,580,160,25);
		contentPane.add(potvrdiBtn);
 		
	}
	
	private void initButtonsListeners() {
		
		potvrdiBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				administratorDodavanjePredmetaFormController.dodajPredmet();
			}
		});
		
	}
	
	private void initComboBoxListener() {
		
		ciklusiCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorDodavanjePredmetaFormController.postaviStudijskePrograme(studijskiProgramiCB, getCiklus());
			}
		});
		
	}

	public void setStudijskiProgramiList(ArrayList<String> studijskiProgramiList) {
		this.studijskiProgramiList = studijskiProgramiList;
	}
	
	public String getSifra() {
		return sifraTf.getText();
	}
	
	public String getNazivPredmeta() {
		return nazivTf.getText();
	}
	
	public short getEcts() {
		if("".equals(ectsTf.getText())) {
			return -1;
		}
		try {
			return Short.parseShort(ectsTf.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Nekorektan unos ECTS bodova.");
		}
		return 0;
	}
	
	public short getSemestar() {
		if("".equals(semestarTf.getText())) {
			return -1;
		}
		try {
			return Short.parseShort(semestarTf.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Nekorektan unos semestra.");
		}
		return 0;
	}
	
	public String getSkolskaGodina() {
		return skolskeGodineList.get(skolskeGodineCB.getSelectedIndex());
	}
	
	public short getCiklus() {
		return ciklusiList.get(ciklusiCB.getSelectedIndex());
	}
	
	public String getStudijskiProgram() {
		return (String)studijskiProgramiCB.getSelectedItem();
	}
	
	public char getTipPredmeta() {
		if(obavezan.isSelected()) {
			return 'O';
		}
		return 'I';
	}

}
