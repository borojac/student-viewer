package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AccountFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class AccountForm extends JFrame {

	private JPanel contentPane;
	private JPanel buttonPane;
	
	private MainFormController mainFormController;
	private AccountFormController accountFormController;
	private NalogDTO nalogDTO;
	
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	
	private JButton promjenaLozinkeBtn;
	private JButton promjenaKorImenaBtn;
	private JButton dodavanjePredmetaBtn;
	private JButton brisanjePredmetaBtn;
	private JButton dodavanjeDisciplineBtn;
	private JButton brisanjeDisciplineBtn;
	private JButton odjavaBtn;

	/**
	 * Create the frame.
	 */
	public AccountForm(MainFormController mainFormController, NalogDTO nalogDTO) {
		this.mainFormController = mainFormController;
		this.nalogDTO = nalogDTO;
		accountFormController = new AccountFormController(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MainFormController.resetAccountFormOpened();
			}
		});
		
		setTitle("Nalog");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 260, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(45, 0, 170, 120);
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
		whiteCorrectionLabel.setBounds(0, 0, 45, 120);
		contentPane.add(whiteCorrectionLabel);

		JLabel whiteCorrectionLabel2 = new JLabel("");
		whiteCorrectionLabel2.setBackground(Color.WHITE);
		whiteCorrectionLabel2.setOpaque(true);
		whiteCorrectionLabel2.setBounds(215, 0, 45, 120);
		contentPane.add(whiteCorrectionLabel2);
		
		buttonPane = new JPanel();
		buttonPane.setBackground(new Color(0, 0, 139));
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		buttonPane.setBounds(0, 120, 260, 400);
		contentPane.add(buttonPane);
		
		initButtons();
		initButtonsListeners();
		setButtonsSize();
	}
	
	private void setButtonsSize() {
		for (JButton btn : buttons) {
			btn.setPreferredSize(new Dimension(185, 35));
		}
	}
	
	private void initButtons() {
		
		promjenaLozinkeBtn = new JButton("Promjeni lozinku");
		buttons.add(promjenaLozinkeBtn);
		buttonPane.add(promjenaLozinkeBtn);
		
		promjenaKorImenaBtn = new JButton("Promjeni korisnicko ime");
		buttons.add(promjenaKorImenaBtn);
		buttonPane.add(promjenaKorImenaBtn);
		
		dodavanjePredmetaBtn = new JButton("Dodaj predmet");
		buttons.add(dodavanjePredmetaBtn);
		buttonPane.add(dodavanjePredmetaBtn);
		
		brisanjePredmetaBtn = new JButton("Ukloni predmet");
		buttons.add(brisanjePredmetaBtn);
		buttonPane.add(brisanjePredmetaBtn);
		
		dodavanjeDisciplineBtn = new JButton("Dodaj disciplinu");
		buttons.add(dodavanjeDisciplineBtn);
		buttonPane.add(dodavanjeDisciplineBtn);
		
		brisanjeDisciplineBtn = new JButton("Ukloni disciplinu");
		buttons.add(brisanjeDisciplineBtn);
		buttonPane.add(brisanjeDisciplineBtn);
		
		odjavaBtn = new JButton("Odjavi se");
		buttons.add(odjavaBtn);
		buttonPane.add(odjavaBtn);
		
	}
	
	private void initButtonsListeners() {
		
		promjenaLozinkeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accountFormController.createChangePasswordForm();
			}
		});
		
		promjenaKorImenaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accountFormController.createChangeAccountNameForm();
			}
		});
		
		dodavanjePredmetaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accountFormController.createDodavanjePredmetaForm();
			}
		});
		
		brisanjePredmetaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accountFormController.createBrisanjePredmetaForm();
			}
		});
		
		odjavaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accountFormController.odjava();
			}
		});
		
		dodavanjeDisciplineBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accountFormController.createDodavanjeDisciplineForm();
			}
		});
		
		brisanjeDisciplineBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accountFormController.createBrisanjeDisciplineForm();
			}
		});
		
	}

	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}

	public void setNalogDTO(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
	}

	public MainFormController getMainFormController() {
		return mainFormController;
	}

	public void setMainFormController(MainFormController mainFormController) {
		this.mainFormController = mainFormController;
	}

}
