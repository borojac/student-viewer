package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.ShowViewData;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormControler;
import org.unibl.etf.ps.studentviewer.gui.controler.ShowFormControler;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowForm extends JFrame {

	private JPanel contentPane;
	private MainFormControler mainFormControler;
	
	ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();
	
	/**
	 * Launch the application.
	 *

	/**
	 * Create the frame.
	 * @param mainFormControler 
	 */
	public ShowForm(MainFormControler mainFormControler) {
		this.mainFormControler = mainFormControler;
		setTitle("Prikaz");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 267, 367);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(45, 0, 170, 120);
		contentPane.add(headerPictureLabel);

		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		whiteCorrectionLabel2.setBounds(215, 0, 49, 120);
		contentPane.add(whiteCorrectionLabel2);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 205));
		panel.setBounds(45, 131, 170, 143);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JCheckBox indeksCheckBox = new JCheckBox("Indeks");
		indeksCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		indeksCheckBox.setForeground(new Color(255, 255, 255));
		indeksCheckBox.setBackground(new Color(0, 0, 205));
		panel.add(indeksCheckBox);
		boxes.add(indeksCheckBox);
		if (ShowViewData.getValue(ShowViewData.D_BROJINDEKSA))
			indeksCheckBox.setSelected(true);
		
		JCheckBox imeCheckBox = new JCheckBox("Ime");
		imeCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		imeCheckBox.setForeground(new Color(255, 255, 255));
		imeCheckBox.setBackground(new Color(0, 0, 205));
		panel.add(imeCheckBox);
		boxes.add(imeCheckBox);
		if(ShowViewData.getValue(ShowViewData.D_IME))
			imeCheckBox.setSelected(true);
		
		JCheckBox prezimeCheckBox = new JCheckBox("Prezime");
		prezimeCheckBox.setFont(new Font("Century Gothic", Font.BOLD, 13));
		prezimeCheckBox.setForeground(new Color(255, 255, 255));
		prezimeCheckBox.setBackground(new Color(0, 0, 205));
		panel.add(prezimeCheckBox);
		boxes.add(prezimeCheckBox);
		if (ShowViewData.getValue(ShowViewData.D_PREZIME))
			prezimeCheckBox.setSelected(true);
		
		
		JButton btnSacuvaj = new JButton("Sacuvaj");
		btnSacuvaj.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ArrayList<Boolean> list = new ArrayList<Boolean>();
				list.add(indeksCheckBox.isSelected());
				list.add(imeCheckBox.isSelected());
				list.add(prezimeCheckBox.isSelected());
				new ShowFormControler(mainFormControler).updateShowView(list);
				setVisible(false);
			}
		});
		btnSacuvaj.setMaximumSize(new Dimension(100, 28));
		btnSacuvaj.setBounds(87, 285, 90, 25);
		btnSacuvaj.setPreferredSize(new Dimension(90,25));
		contentPane.add(btnSacuvaj);
		
		
		
		initCheckBoxSize();
	}

	private void initCheckBoxSize() {
		for (JCheckBox c : boxes) 
			c.setPreferredSize(new Dimension(150, 20));
	}
}
