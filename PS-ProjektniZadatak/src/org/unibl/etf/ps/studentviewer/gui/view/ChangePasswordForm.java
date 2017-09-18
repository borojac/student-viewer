package org.unibl.etf.ps.studentviewer.gui.view;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.border.EmptyBorder;
import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AccountFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.ChangePasswordFormController;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class ChangePasswordForm extends JFrame {
	
	private ChangePasswordFormController changePasswordFormController;
	private NalogDTO nalogDTO;

	private JPanel contentPane;
	private JPanel componentsPane;
	private JLabel staraLozinkaLbl;
	private JLabel novaLozinkaLbl;
	private JLabel novaLozinkaPotvrdaLbl;
	private JPasswordField staraLozinkaPf;
	private JPasswordField novaLozinkaPf;
	private JPasswordField novaLozinkaPotvrdaPf;
	private JButton potvrdiBtn;
	
	/**
	 * Create the frame.
	 */
	public ChangePasswordForm(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
		changePasswordFormController = new ChangePasswordFormController(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AccountFormController.resetChangePasswordFormOpened();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 10, 350, 432);
		setTitle("Promjena lozinke");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(90, 0, 170, 120);
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
		whiteCorrectionLabel.setBounds(0, 0, 90, 120);
		contentPane.add(whiteCorrectionLabel);

		JLabel whiteCorrectionLabel2 = new JLabel("");
		whiteCorrectionLabel2.setBackground(Color.WHITE);
		whiteCorrectionLabel2.setOpaque(true);
		whiteCorrectionLabel2.setBounds(260, 0, 90, 120);
		contentPane.add(whiteCorrectionLabel2);
		
		componentsPane = new JPanel();
		componentsPane.setBackground(new Color(0, 0, 139));
		componentsPane.setLayout(null);
		componentsPane.setBounds(50, 120, 250, 430);
		contentPane.add(componentsPane);
		
		initComponents();
		initButtonsListeners();
		
		getRootPane().setDefaultButton(potvrdiBtn);
	}
	private void initComponents() {
		staraLozinkaLbl = new JLabel("Stara lozinka");
		staraLozinkaLbl.setBounds(0, 10, 250, 25);
		staraLozinkaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		staraLozinkaLbl.setForeground(Color.WHITE);
		componentsPane.add(staraLozinkaLbl);
		
		staraLozinkaPf = new JPasswordField();
		staraLozinkaPf.setEchoChar('*');
		staraLozinkaPf.setBounds(0, 35, 250, 35);
		staraLozinkaPf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		componentsPane.add(staraLozinkaPf);
		
		novaLozinkaLbl = new JLabel("Nova lozinka");
		novaLozinkaLbl.setBounds(0, 80, 250, 25);
		novaLozinkaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		novaLozinkaLbl.setForeground(Color.WHITE);
		componentsPane.add(novaLozinkaLbl);
		
		novaLozinkaPf = new JPasswordField();
		novaLozinkaPf.setEchoChar('*');
		novaLozinkaPf.setBounds(0, 105, 250, 35);
		novaLozinkaPf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		componentsPane.add(novaLozinkaPf);
		
		novaLozinkaPotvrdaLbl = new JLabel("Potvrda nove lozinke");
		novaLozinkaPotvrdaLbl.setBounds(0, 150, 250, 25);
		novaLozinkaPotvrdaLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		novaLozinkaPotvrdaLbl.setForeground(Color.WHITE);
		componentsPane.add(novaLozinkaPotvrdaLbl);
		
		novaLozinkaPotvrdaPf = new JPasswordField();
		novaLozinkaPotvrdaPf.setEchoChar('*');
		novaLozinkaPotvrdaPf.setBounds(0, 175, 250, 35);
		novaLozinkaPotvrdaPf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		componentsPane.add(novaLozinkaPotvrdaPf);
		
		potvrdiBtn = new JButton("Potvrdi");
		potvrdiBtn.setBounds(50, 225, 150, 35);
		componentsPane.add(potvrdiBtn);
	}
	
	private void initButtonsListeners() {
		
		potvrdiBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePasswordFormController.promjenaLozinke();
			}
		});
		
	}
	
	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}
	public void setNalogDTO(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
	}
	
	public String getStaraLozinka() {
		return String.valueOf(staraLozinkaPf.getPassword());
	}
	
	public String getNovaLozinka() {
		return String.valueOf(novaLozinkaPf.getPassword());
	}
	
	public String getNovaLozinkaPotvrda() {
		return String.valueOf(novaLozinkaPotvrdaPf.getPassword());
	}
	
	public void setEmptyStaraLozinka() {
		staraLozinkaPf.setText("");
	}
	
	public void setEmptyNovaLozinka() {
		novaLozinkaPf.setText("");
	}
	
	public void setEmptyNovaLozinkaPotvrda() {
		novaLozinkaPotvrdaPf.setText("");
	}

}
