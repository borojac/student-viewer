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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AccountFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.BrisanjePredmetaFormController;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class BrisanjePredmetaForm extends JFrame {

	private JPanel contentPane;
	
	private JComboBox<Short> ciklusiCB;
	private JComboBox<String> studijskiProgramiCB;
	private JComboBox<String> skolskeGodineCB;
	private JComboBox<String> predmetiCB;
	private JButton ukloniBtn;
	
	private JLabel ciklusLbl;
	private JLabel studijskiProgramLbl;
	private JLabel skolskaGodinaLbl;
	private JLabel predmetLbl;
	
	private ArrayList<PredmetDTO> predmetiList;
	private ArrayList<Short> ciklusiList;
	private ArrayList<String> studijskiProgramiList;
	private ArrayList<String> skolskeGodineList;
	
	private NalogDTO nalogDTO;
	private BrisanjePredmetaFormController brisanjePredmetaFormController;
	private MainForm mainForm;

	/**
	 * Create the frame.
	 */
	public BrisanjePredmetaForm(NalogDTO nalogDTO, MainForm mainForm) {
		this.nalogDTO = nalogDTO;
		this.mainForm = mainForm;
		brisanjePredmetaFormController = new BrisanjePredmetaFormController(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AccountFormController.resetBrisanjePredmetaFormOpened();
			}
		});
		
		setTitle("Brisanje predmeta");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 430, 520);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(130, 0, 170, 120);
		contentPane.add(headerPictureLabel);

		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel whiteCorrectionLabel = new JLabel("");
		whiteCorrectionLabel.setOpaque(true);
		whiteCorrectionLabel.setBackground(Color.WHITE);
		whiteCorrectionLabel.setBounds(0, 0, 130, 120);
		contentPane.add(whiteCorrectionLabel);

		JLabel whiteCorrectionLabel2 = new JLabel("");
		whiteCorrectionLabel2.setBackground(Color.WHITE);
		whiteCorrectionLabel2.setOpaque(true);
		whiteCorrectionLabel2.setBounds(300, 0, 130, 120);
		contentPane.add(whiteCorrectionLabel2);
		
		initComponents();
		initButtonsListeners();
		initComboBoxListeners();
		brisanjePredmetaFormController.postaviStudijskePrograme(studijskiProgramiCB, getSelectedCiklus());
	}
	
	private void initComponents() {
		ciklusiCB = new JComboBox<>();
		studijskiProgramiCB = new JComboBox<>();
		skolskeGodineCB = new JComboBox<>();
		predmetiCB = new JComboBox<>();
		
		MySQLDAOFactory factory = new MySQLDAOFactory();
		NalogDAO nalogDAO = factory.getNalogDAO();
		
		predmetiList = nalogDAO.getPredmeteNaNalogu(nalogDTO.getNalogId());
		
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
			if(predmetiList.get(i).getCiklus() == getSelectedCiklus() && !studijskiProgramiList.contains(predmetiList.get(i).getNazivSP())) {
				studijskiProgramiList.add(predmetiList.get(i).getNazivSP());
			}
			if(!skolskeGodineList.contains(predmetiList.get(i).getSkolskaGodina())) {
				skolskeGodineList.add(predmetiList.get(i).getSkolskaGodina());
			}
		}
		
		for(int i = 0; i < skolskeGodineList.size(); i++) {
			skolskeGodineCB.addItem(skolskeGodineList.get(i));
		}
		
		ciklusLbl = new JLabel("Ciklus:");
		ciklusLbl.setBounds(20, 140, 100, 25);
		ciklusLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 15));
		ciklusLbl.setForeground(Color.WHITE);
		contentPane.add(ciklusLbl);
		
		ciklusiCB.setBounds(20, 165, 380, 35);
		contentPane.add(ciklusiCB);
		
		studijskiProgramLbl = new JLabel("Studijski program:");
		studijskiProgramLbl.setBounds(20, 210, 200, 25);
		studijskiProgramLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 15));
		studijskiProgramLbl.setForeground(Color.WHITE);
		contentPane.add(studijskiProgramLbl);
		
		studijskiProgramiCB.setBounds(20, 235, 380, 35);
		contentPane.add(studijskiProgramiCB);
		
		skolskaGodinaLbl = new JLabel("Skolska godina:");
		skolskaGodinaLbl.setBounds(20, 280, 200, 25);
		skolskaGodinaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 15));
		skolskaGodinaLbl.setForeground(Color.WHITE);
		contentPane.add(skolskaGodinaLbl);
		
		skolskeGodineCB.setBounds(20, 305, 380, 35);
		contentPane.add(skolskeGodineCB);
		
		predmetLbl = new JLabel("Predmet:");
		predmetLbl.setBounds(20, 350, 100, 25);
		predmetLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 15));
		predmetLbl.setForeground(Color.WHITE);
		contentPane.add(predmetLbl);
		
		predmetiCB.setBounds(20, 375, 380, 35);
		contentPane.add(predmetiCB);
		
		ukloniBtn = new JButton("Ukloni iz mojih predmeta");
		ukloniBtn.setBounds(125, 430, 180, 35);
		contentPane.add(ukloniBtn);
	}
	
	private void initButtonsListeners() {
		
		ukloniBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				brisanjePredmetaFormController.ukloni();
			}
		});
		
	}
	
	private void initComboBoxListeners() {
		
		ciklusiCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brisanjePredmetaFormController.postaviStudijskePrograme(studijskiProgramiCB, getSelectedCiklus());
				brisanjePredmetaFormController.postaviPredmete(predmetiCB, getSelectedCiklus(), getSelectedStudijskiProgram(), getSelectedSkolskaGodina());
			}
		});
		
		studijskiProgramiCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brisanjePredmetaFormController.postaviPredmete(predmetiCB, getSelectedCiklus(), getSelectedStudijskiProgram(), getSelectedSkolskaGodina());
			}
		});
		
		skolskeGodineCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brisanjePredmetaFormController.postaviPredmete(predmetiCB, getSelectedCiklus(), getSelectedStudijskiProgram(), getSelectedSkolskaGodina());
			}
		});
		
	}

	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}

	public void setNalogDTO(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
	}

	public MainForm getMainForm() {
		return mainForm;
	}

	public void setMainForm(MainForm mainForm) {
		this.mainForm = mainForm;
	}
	
	public void setStudijskiProgramiList(ArrayList<String> studijskiProgramiList) {
		this.studijskiProgramiList = studijskiProgramiList;
	}
	
	public PredmetDTO getSelectedPredmet() {
		String s = (String)predmetiCB.getSelectedItem();
		PredmetDTO predmetDTO = null;
		for(int i = 0; i < predmetiList.size(); i++) {
			if(s.equals(predmetiList.get(i).getNazivPredmeta())) {
				predmetDTO = predmetiList.get(i);
				break;
			}
		}
		return predmetDTO;
	}
	
	public short getSelectedCiklus() {
		int i = ciklusiCB.getSelectedIndex();
		return (i == -1) ? null : ciklusiList.get(i);
	}
	
	public String getSelectedStudijskiProgram() {
		int i = studijskiProgramiCB.getSelectedIndex();
		return (i == -1) ? null : studijskiProgramiList.get(i);
	}
	
	public String getSelectedSkolskaGodina() {
		int i = skolskeGodineCB.getSelectedIndex();
		return (i == -1) ? null : skolskeGodineList.get(i);
	}

}
