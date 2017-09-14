package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorDodavanjeStudijskogCiklusaFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class AdministratorDodavanjeStudijskogProgramaForm extends JFrame {

	private JPanel contentPane;
	private AdministratorFormController administratorFormController;
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
	private AdministratorDodavanjeStudijskogCiklusaFormController administratorDodavanjeStudijskogCiklusaFormController;



	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministratorDodavanjeStudijskogProgramaForm frame = new AdministratorDodavanjeStudijskogProgramaForm(administratorFormController);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	public AdministratorDodavanjeStudijskogProgramaForm()
	{
		
	}

	/**
	 * Create the frame.
	 */
	public AdministratorDodavanjeStudijskogProgramaForm(AdministratorFormController administratorFormController) {
setResizable(false);
		
		this.administratorFormController = administratorFormController;
		addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
				   administratorFormController.resetDodajStudProgOpened();
			   }
			  });
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 440);
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
			// TODO Auto-generated catch block
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
	}
	public void initComponents()
	{
		nazivLbl = new JLabel("Naziv predmeta");
 		nazivLbl.setBounds(15, 130, 250, 45);
 		nazivLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		nazivLbl.setForeground(Color.WHITE);
 		contentPane.add(nazivLbl);
 		
 		nazivTf = new JTextField();
		nazivTf.setBounds(175, 140, 160, 25);
		nazivTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(nazivTf);
		
		ectsLbl = new JLabel("ECTS bodovi");
		ectsLbl.setBounds(15, 180, 250, 45);
		ectsLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		ectsLbl.setForeground(Color.WHITE);
		contentPane.add(ectsLbl);
		
		ectsTf = new JTextField();
		ectsTf.setBounds(175, 190, 160, 25);
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
		
		for(int i = 0; i < ciklusiList.size(); i++) {
			ciklusCB.addItem(ciklusiList.get(i));
		}
		
		ciklusCB.setSelectedIndex(0);
		
		ciklusLbl = new JLabel("Studijski ciklus");
 		ciklusLbl.setBounds(15, 230, 250, 45);
 		ciklusLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		ciklusLbl.setForeground(Color.WHITE);
 		contentPane.add(ciklusLbl);
 		
 		ciklusCB.setBounds(175, 240, 160, 25);
		contentPane.add(ciklusCB);
		
		trajanjeLbl = new JLabel("Trajanje");
		trajanjeLbl.setBounds(15, 280, 250, 45);
		trajanjeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		trajanjeLbl.setForeground(Color.WHITE);
 		contentPane.add(trajanjeLbl);
 		
 		trajanjeTf = new JTextField();
		trajanjeTf.setBounds(175, 290, 160, 25);
		trajanjeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(trajanjeTf);
		
		zvanjeLbl = new JLabel("Zvanje");
		zvanjeLbl.setBounds(15, 330, 250, 45);
		zvanjeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		zvanjeLbl.setForeground(Color.WHITE);
 		contentPane.add(zvanjeLbl);
 		
 		zvanjeTf = new JTextField();
		zvanjeTf.setBounds(175, 340, 160, 25);
		zvanjeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(zvanjeTf);
		
		button = new JButton("Potvrdi");
		button.setBounds(100,380,160,25);
		contentPane.add(button);
		
	}
private void initButtonsListeners() {
		
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				administratorDodavanjeStudijskogCiklusaFormController.dodajStudProg();
			}
		});
		
	}

}
