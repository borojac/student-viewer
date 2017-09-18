package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.PredmetChooseAddTypeFormController;

import java.awt.Font;


public class PredmetChooseAddTypeForm extends JFrame {

	private JPanel contentPane;
	JCheckBox chckbxNewCheckBoxJedan = new JCheckBox("");
	JCheckBox chckbxNewCheckBoxVise = new JCheckBox("");

	public PredmetChooseAddTypeForm(AdministratorFormController administratorFormController) {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AdministratorFormController.resetChooseAddTypeFormOpened();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 293, 287);
		contentPane =new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel label = new JLabel("");
		label.setBounds(56, 0, 171, 120);
		try {
			BufferedImage headerImage = ImageIO.read(new File("img\\BellTower-RGB(JPG).jpg"));
			headerImage = Scalr.resize(headerImage, Scalr.Mode.FIT_EXACT, label.getWidth(), label.getHeight(), null);
			label.setIcon(new ImageIcon(headerImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setBackground(Color.WHITE);
		label_1.setOpaque(true);
		label_1.setBounds(0, 0, 65, 120);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setBackground(Color.WHITE);
		label_2.setOpaque(true);
		label_2.setBounds(211, 0, 77, 120);
		contentPane.add(label_2);
		
		JButton chooseButton = new JButton("Nastavi");
		chooseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean jedan = chckbxNewCheckBoxJedan.isSelected();
				boolean vise = chckbxNewCheckBoxVise.isSelected();
				PredmetChooseAddTypeForm.this.dispose();
				new PredmetChooseAddTypeFormController(administratorFormController, jedan, vise, PredmetChooseAddTypeForm.this);	
			}
		});
		chooseButton.setBounds(96, 217, 89, 29);
		contentPane.add(chooseButton);
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(10, 131, 125, 75);
		panel1.setBackground(new Color(0,0,139));
		contentPane.add(panel1);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JTextArea txtrDodavanjeJednog = new JTextArea();
		txtrDodavanjeJednog.setEditable(false);
		txtrDodavanjeJednog.setForeground(Color.WHITE);
		txtrDodavanjeJednog.setBackground(new Color(0,0,139));
		txtrDodavanjeJednog.setText("     Dodavanje" + System.lineSeparator()+"jednog predmeta");
		txtrDodavanjeJednog.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel1.add(txtrDodavanjeJednog);
		
		
		chckbxNewCheckBoxJedan.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				chckbxNewCheckBoxVise.setSelected(false);
			}
		});
		chckbxNewCheckBoxJedan.setBackground(new Color(0,0,139));
		panel1.add(chckbxNewCheckBoxJedan);
		
		JPanel panel2 = new JPanel();
		panel2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel2.setBounds(145, 131, 111, 75);
		panel2.setBackground(new Color(0,0,139));
		contentPane.add(panel2);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JTextArea txtrDodavanjeVise = new JTextArea();
		txtrDodavanjeVise.setEditable(false);
		txtrDodavanjeVise.setForeground(Color.WHITE);
		txtrDodavanjeVise.setBackground(new Color(0,0,139));
		txtrDodavanjeVise.setText("    Dodavanje" + System.lineSeparator()+" vise predmeta");
		txtrDodavanjeVise.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel2.add(txtrDodavanjeVise);
		chckbxNewCheckBoxVise.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				chckbxNewCheckBoxJedan.setSelected(false);
			}
		});
		
		chckbxNewCheckBoxVise.setBackground(new Color(0,0,139));
		panel2.add(chckbxNewCheckBoxVise);
		
		getRootPane().setDefaultButton(chooseButton);
	}
	
}	
