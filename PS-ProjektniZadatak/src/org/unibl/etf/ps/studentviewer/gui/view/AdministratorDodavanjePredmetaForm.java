package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;

public class AdministratorDodavanjePredmetaForm extends JFrame {

	private JPanel contentPane;
	private JPanel componentPane;
	private MainFormController mainFormController = null;
	private JLabel sifraLbl, ectsLbl, tipPredmetaLbl, nazivSPLbl;
	private JLabel nazivLbl, semestarLbl, skolskaGodinaLbl, ciklusLbl;
	private JPanel panel = null;
	private JTextField sifraTf;
	private JTextField nazivTf;
	private JTextField ectsTf;
	private JTextField semestarTf;
	private JTextField tipPredmetaTf;
	private JTextField skolskaGodinaTf;
	private JTextField nazivSPTf;
	private JTextField ciklusTf;
	private JButton addButton;
	private JRadioButton obavezan, izborni;
	private ButtonGroup bg;
	AdministratorFormController administratorFormController;
	private JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministratorDodavanjePredmetaForm frame = new AdministratorDodavanjePredmetaForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	public AdministratorDodavanjePredmetaForm(AdministratorFormController administratorFormController)
	{
setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
				   administratorFormController.resetAddFormOpened();
				   administratorFormController.resetChooseAddTypeFormOpened();
			   }
			  });
		this.administratorFormController = administratorFormController;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 640);
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
		sifraLbl = new JLabel("Šifra predmeta");
 		sifraLbl.setBounds(15, 130, 250, 45);
 		sifraLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		sifraLbl.setForeground(Color.WHITE);
 		contentPane.add(sifraLbl);
 		
 		sifraTf = new JTextField();
		sifraTf.setBounds(175, 130, 160, 45);
		sifraTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(sifraTf);
		
		nazivLbl = new JLabel("Naziv predmeta");
 		nazivLbl.setBounds(15, 185, 250, 45);
 		nazivLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		nazivLbl.setForeground(Color.WHITE);
 		contentPane.add(nazivLbl);
 		
 		nazivTf = new JTextField();
		nazivTf.setBounds(175, 185, 160, 45);
		nazivTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(nazivTf);
		
		ectsLbl = new JLabel("ECTS bodovi");
 		ectsLbl.setBounds(15, 240, 250, 45);
 		ectsLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		ectsLbl.setForeground(Color.WHITE);
 		contentPane.add(ectsLbl);
 		
 		ectsTf = new JTextField();
		ectsTf.setBounds(175, 240, 160, 45);
		ectsTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(ectsTf);
		
		semestarLbl = new JLabel("Semestar");
 		semestarLbl.setBounds(15, 295, 250, 45);
 		semestarLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		semestarLbl.setForeground(Color.WHITE);
 		contentPane.add(semestarLbl);
 		
 		semestarTf = new JTextField();
		semestarTf.setBounds(175, 295, 160, 45);
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
            add(obavezan);
            add(izborni);
 		
 		skolskaGodinaLbl = new JLabel("Školska godina");
 		skolskaGodinaLbl.setBounds(15, 405, 250, 45);
 		skolskaGodinaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		skolskaGodinaLbl.setForeground(Color.WHITE);
 		contentPane.add(skolskaGodinaLbl);
 		
 		skolskaGodinaTf = new JTextField();
		skolskaGodinaTf.setBounds(175, 405, 160, 45);
		skolskaGodinaTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(skolskaGodinaTf);
		
		nazivSPLbl = new JLabel("Studijski program");
 		nazivSPLbl.setBounds(15, 460, 250, 45);
 		nazivSPLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		nazivSPLbl.setForeground(Color.WHITE);
 		contentPane.add(nazivSPLbl);
 		
 		nazivSPTf = new JTextField();
		nazivSPTf.setBounds(175, 460, 160, 45);
		nazivSPTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(nazivSPTf);
		
		ciklusLbl = new JLabel("Studijski ciklus");
 		ciklusLbl.setBounds(15, 515, 250, 45);
 		ciklusLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
 		ciklusLbl.setForeground(Color.WHITE);
 		contentPane.add(ciklusLbl);
 		
 		ciklusTf = new JTextField();
		ciklusTf.setBounds(175, 515, 160, 45);
		ciklusTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
		contentPane.add(ciklusTf);
		
		button = new JButton("Potvrdi");
		button.setBounds(100,580,160,25);
		contentPane.add(button);
 		
	}

	/**
	 * Create the frame.
	 */
	public AdministratorDodavanjePredmetaForm() {
	}

}
