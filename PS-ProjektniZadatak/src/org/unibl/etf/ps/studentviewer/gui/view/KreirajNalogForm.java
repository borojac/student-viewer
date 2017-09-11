package org.unibl.etf.ps.studentviewer.gui.view;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.imgscalr.Scalr;
import javax.swing.JPasswordField;

import org.unibl.etf.ps.studentviewer.logic.controller.KreirajNalogFormController;;

@SuppressWarnings("serial")

public class KreirajNalogForm extends JFrame{   
	
	private JPanel contentPane;
	private JPanel componentsPane;
	private JLabel korImeLbl, imeLbl, prezimeLbl;
	private JTextField korImeTf, imeTf, prezimeTf;
	private JLabel lozinkaLbl, lozinkaPotLbl;
	private JPasswordField lozinkaTf, lozinkaPotTf;
	private JButton kreirajBtn;
	private KreirajNalogFormController kreirajNalogFormController;
	public String getLozinkaTf(){ return String.valueOf(lozinkaTf.getPassword());}
	public String getLozinkaPotTf(){ return String.valueOf(lozinkaPotTf.getPassword());}
	public String getKorImeTf(){ return korImeTf.getText();}
	public String getImeTf(){ return imeTf.getText();}
	public String getPrezimeTf(){ return prezimeTf.getText();}
	
	
      
      public KreirajNalogForm() {
    	  kreirajNalogFormController = new KreirajNalogFormController(this);
    	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		setBounds(100, 10, 650, 550);
  		setTitle("Kreiranje naloga");
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
  		componentsPane.setBounds(100, 120, 600, 430);
  		contentPane.add(componentsPane);
    	  
    	  initComponent();
      }
      private void initComponent(){
    	  
            
            imeLbl = new JLabel("Ime");
    		imeLbl.setBounds(0, 40, 250, 35);
    		imeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		imeLbl.setForeground(Color.WHITE);
    		componentsPane.add(imeLbl);
    		
    		imeTf = new JTextField();
    		imeTf.setBounds(200, 40, 250, 35);
    		imeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		componentsPane.add(imeTf);
    		
    		prezimeLbl = new JLabel("Prezime");
    		prezimeLbl.setBounds(0, 100, 250, 35);
    		prezimeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		prezimeLbl.setForeground(Color.WHITE);
    		componentsPane.add(prezimeLbl);
    		
    		prezimeTf = new JTextField();
    		prezimeTf.setBounds(200, 100, 250, 35);
    		prezimeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		componentsPane.add(prezimeTf);
    		
    		korImeLbl = new JLabel("Korisnicko ime");
    		korImeLbl.setBounds(0, 160, 250, 35);
    		korImeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		korImeLbl.setForeground(Color.WHITE);
    		componentsPane.add(korImeLbl);
    		
    		korImeTf = new JTextField();
    		korImeTf.setBounds(200, 160, 250, 35);
    		korImeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		componentsPane.add(korImeTf);
    		
    		lozinkaLbl = new JLabel("Lozinka");
    		lozinkaLbl.setBounds(0, 220, 250, 35);
    		lozinkaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		lozinkaLbl.setForeground(Color.WHITE);
    		componentsPane.add(lozinkaLbl);
    		
    		lozinkaTf = new JPasswordField();
    		lozinkaTf.setBounds(200, 220, 250, 35);
    		lozinkaTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		lozinkaTf.setEchoChar('*');
    		componentsPane.add(lozinkaTf);
    		
    		lozinkaPotLbl = new JLabel("Potvrda lozinke");
    		lozinkaPotLbl.setBounds(0, 280, 250, 35);
    		lozinkaPotLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		lozinkaPotLbl.setForeground(Color.WHITE);
    		componentsPane.add(lozinkaPotLbl);
    		
    		lozinkaPotTf = new JPasswordField();
    		lozinkaPotTf.setBounds(200, 280, 250, 35);
    		lozinkaPotTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 16));
    		lozinkaPotTf.setEchoChar('*');
    		componentsPane.add(lozinkaPotTf);
            
            kreirajBtn = new JButton("Kreiraj");
    		kreirajBtn.setBounds(100, 350, 250, 35);
    		componentsPane.add(kreirajBtn);
    		
    		
    		kreirajBtn.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent arg0) {
    				kreirajNalogFormController.createKreirajNalog();
    			}
    		});

      }
      
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KreirajNalogForm frame = new KreirajNalogForm();
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
	

}
