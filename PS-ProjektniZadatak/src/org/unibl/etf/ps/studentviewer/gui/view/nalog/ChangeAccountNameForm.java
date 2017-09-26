package org.unibl.etf.ps.studentviewer.gui.view.nalog;

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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.AccountFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.ChangeAccountNameFormController;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;

public class ChangeAccountNameForm extends JFrame {

	private JPanel contentPane;
	private JPanel componentsPane;
	private JLabel korisnickoImeLbl;
	private JTextField korisnickoImeTf;
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
		setBounds(100, 10, 530, 260);
		setTitle("Promjena korisnickog imena");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(180, 0, 170, 120);
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
		whiteCorrectionLabel.setBounds(0, 0, 180, 120);
		contentPane.add(whiteCorrectionLabel);

		JLabel whiteCorrectionLabel2 = new JLabel("");
		whiteCorrectionLabel2.setBackground(Color.WHITE);
		whiteCorrectionLabel2.setOpaque(true);
		whiteCorrectionLabel2.setBounds(350, 0, 180, 120);
		contentPane.add(whiteCorrectionLabel2);
		
		componentsPane = new JPanel();
		componentsPane.setBackground(new Color(0, 0, 139));
		componentsPane.setLayout(null);
		componentsPane.setBounds(50, 120, 430, 430);
		contentPane.add(componentsPane);
		
		initComponents();
		initButtonsListeners();
		
		getRootPane().setDefaultButton(potvrdiBtn);
	}
	private void initComponents() {
		korisnickoImeLbl = new JLabel("Korisnicko ime");
		korisnickoImeLbl.setBounds(0, 10, 250, 25);
		korisnickoImeLbl.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		korisnickoImeLbl.setForeground(Color.WHITE);
		componentsPane.add(korisnickoImeLbl);
		
		korisnickoImeTf = new JTextField();
		korisnickoImeTf.setBounds(0, 35, 260, 35);
		korisnickoImeTf.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 18));
		korisnickoImeTf.setText(nalogDTO.getKorisnickoIme());
		componentsPane.add(korisnickoImeTf);
		
		potvrdiBtn = new JButton("Potvrdi");
		potvrdiBtn.setBounds(275, 35, 150, 35);
		componentsPane.add(potvrdiBtn);
	}
	
	private void initButtonsListeners() {
		
		potvrdiBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
	
	public String getKorisnickoIme() {
		return korisnickoImeTf.getText();
	}

}
