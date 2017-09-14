package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AccountFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.ChangeAccountNameFormController;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class ChangeAccountNameForm extends JFrame {

	private JPanel contentPane;
	private JPanel componentsPane;
	private JLabel novoKorImeLbl, staroKorImeLbl;
	private JTextField novoKorImeTf, staroKorImeTf;
	private JButton potvrdiBtn;
	private ChangeAccountNameFormController changeAccountNameFormController;
	private NalogDTO nalogDTO;

	/**
	 * Create the frame.
	 */
	public ChangeAccountNameForm(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
		changeAccountNameFormController = new ChangeAccountNameFormController(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AccountFormController.resetChangeAccountNameFormOpened();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 10, 650, 400);
		setTitle("Promjena korisnickog imena");
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
		staroKorImeLbl = new JLabel("Staro korisnicko ime");
		staroKorImeLbl.setBounds(30, 10, 250, 35);
		staroKorImeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		staroKorImeLbl.setForeground(Color.WHITE);
		componentsPane.add(staroKorImeLbl);
		
		staroKorImeTf = new JTextField();
		staroKorImeTf.setBounds(0, 45, 250, 35);
		staroKorImeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		componentsPane.add(staroKorImeTf);
		
		novoKorImeLbl = new JLabel("Novo korisnicko ime");
		novoKorImeLbl.setBounds(30, 80, 250, 35);
		novoKorImeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		novoKorImeLbl.setForeground(Color.WHITE);
		componentsPane.add(novoKorImeLbl);
		
		novoKorImeTf = new JTextField();
		novoKorImeTf.setBounds(0, 115, 250, 35);
		novoKorImeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		componentsPane.add(novoKorImeTf);
		
		potvrdiBtn = new JButton("Potvrdi");
		potvrdiBtn.setBounds(50, 180, 150, 35);
		componentsPane.add(potvrdiBtn);
	}
	
	private void initButtonsListeners() {
		
		potvrdiBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeAccountNameFormController.promjenaKorisnickogImena();
			}
		});
		
	}
	
	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}
	
	public void setNalogDTO(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
	}
	
	public String getStaroKorIme() {
		return staroKorImeTf.getText();
	}
	
	public String getNovoKorIme() {
		return novoKorImeTf.getText();
	}

}
