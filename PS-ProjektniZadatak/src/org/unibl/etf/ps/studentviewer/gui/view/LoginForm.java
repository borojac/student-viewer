package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.LoginFormController;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JPanel componentsPane;
	
	private JLabel korImeLbl;
	private JTextField korImeTf;
	private JLabel lozinkaLbl;
	private JPasswordField lozinkaTf;
	private JButton prijavaBtn;
	private JButton kreirajNalogBtn;
	
	private LoginFormController loginFormController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", "StudentViewer.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "studentviewer");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception ex) {}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm frame = new LoginForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginForm() {
		loginFormController = new LoginFormController(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 10, 650, 550);
		setTitle("Prijava");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(240, 0, 170, 120);
		contentPane.add(headerPictureLabel);

		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel correct1Label = new JLabel("STUDENT");
		correct1Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct1Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct1Label.setOpaque(true);
		correct1Label.setForeground(new Color(0, 0, 139));
		correct1Label.setBackground(new Color(255, 255, 255));
		correct1Label.setBounds(0, 0, 240, 120);
		contentPane.add(correct1Label);

		JLabel correct2Label = new JLabel("VIEWER");
		correct2Label.setBackground(SystemColor.text);
		correct2Label.setForeground(new Color(0, 0, 139));
		correct2Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct2Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct2Label.setOpaque(true);
		correct2Label.setBounds(410, 0, 240, 120);
		contentPane.add(correct2Label);
		
		componentsPane = new JPanel();
		componentsPane.setBackground(new Color(0, 0, 139));
		componentsPane.setLayout(null);
		componentsPane.setBounds(200, 120, 250, 430);
		contentPane.add(componentsPane);
		
		initComponents();
		initButtonsListeners();
	}
	
	private void initComponents() {
		korImeLbl = new JLabel("Korisnicko ime");
		korImeLbl.setBounds(0, 10, 250, 35);
		korImeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		korImeLbl.setForeground(Color.WHITE);
		componentsPane.add(korImeLbl);
		
		korImeTf = new JTextField();
		korImeTf.setBounds(0, 45, 250, 35);
		korImeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		componentsPane.add(korImeTf);
		
		lozinkaLbl = new JLabel("Lozinka");
		lozinkaLbl.setBounds(0, 80, 250, 35);
		lozinkaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		lozinkaLbl.setForeground(Color.WHITE);
		componentsPane.add(lozinkaLbl);
		
		lozinkaTf = new JPasswordField();
		lozinkaTf.setEchoChar('*');
		lozinkaTf.setBounds(0, 115, 250, 35);
		lozinkaTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		componentsPane.add(lozinkaTf);
		
		prijavaBtn = new JButton("Prijavi se");
		prijavaBtn.setBounds(50, 180, 150, 35);
		componentsPane.add(prijavaBtn);
		
		JLabel porukaLbl = new JLabel("Nemate nalog? Ovde mozete da ga napravite.");
		porukaLbl.setBounds(0, 315, 250, 35);
		porukaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 11));
		porukaLbl.setForeground(Color.WHITE);
		componentsPane.add(porukaLbl);
		
		kreirajNalogBtn = new JButton("Kreiraj nalog");
		kreirajNalogBtn.setBounds(50, 345, 150, 35);
		componentsPane.add(kreirajNalogBtn);
	}
	
	private void initButtonsListeners() {
		
		kreirajNalogBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loginFormController.createKreirajNalogForm();
			}
		});
		
		prijavaBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					loginFormController.prijava();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	public String getKorisnickoIme() {
		return korImeTf.getText();
	}
	
	public String getLozinka() {
		return String.valueOf(lozinkaTf.getPassword());
	}

}
