/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.gui.view.student;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.student.AddStudentsController;
import org.unibl.etf.ps.studentviewer.logic.controller.student.ChangeStudentsController;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;

import java.awt.Font;

public class ChangeForm extends JFrame {

	private JPanel contentPane;
	private MainFormController mainFormController = null;
	private AdministratorFormController administratorFormController = null;
	private JPanel panel = null;
	private JPanel panel2 = null;
	private JTextField textFieldIme;
	private JTextField textFieldPrezime;
	private JTextField textFieldBrIndeksa;
	private JButton addButton;
	private JTextArea textArea;
	
	public void setIme(String ime) {
		textFieldIme.setText(ime);
	}

	public String getIme() {
		return textFieldIme.getText();
	}

	public void setPrezime(String prezime) {
		textFieldPrezime.setText(prezime);
	}

	public String getPrezime() {
		return textFieldPrezime.getText();
	}

	public void setBrojIndeksa(String brojIndeksa) {
		textFieldBrIndeksa.setText(brojIndeksa);
	}

	public String getBrojIndeksa() {
		return textFieldBrIndeksa.getText();
	}

	public void setFocusIme() {
		textFieldIme.requestFocusInWindow();
	}

	public void setFocusPrezime() {
		textFieldPrezime.requestFocusInWindow();
	}

	public void setFocusBrIndeksa() {
		textFieldBrIndeksa.requestFocusInWindow();
	}

	public ChangeForm(MainFormController mainFormController, AdministratorFormController administratorFormController,StudentMainTableDTO student, int numInList) {
		setTitle("Izmjena");
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				if(mainFormController != null)
					mainFormController.resetChangeFormOpened();
				else
					administratorFormController.resetChanging();
			}
		});

		this.mainFormController = mainFormController;
		this.administratorFormController = administratorFormController;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 293);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 139));
		panel.setBounds(10, 131, 93, 88);
		contentPane.add(panel);

		JLabel lblImeStudenta = new JLabel("Ime:");
		JLabel lblPrezime = new JLabel("Prezime:");
		JLabel lblBrojIndeksa = new JLabel("Broj indeksa:");
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
		panel.add(lblImeStudenta);
		panel.add(lblPrezime);
		panel.add(lblBrojIndeksa);
		
		if(mainFormController != null) {
		JLabel lblKomentar = new JLabel("Komentar:");
		panel.add(lblKomentar);
		}

		JLabel label = new JLabel("");
		label.setBounds(30, 0, 171, 120);
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
		label_1.setBounds(0, 0, 44, 120);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("");
		label_2.setBackground(Color.WHITE);
		label_2.setOpaque(true);
		label_2.setBounds(200, 0, 66, 120);
		contentPane.add(label_2);

		addButton = new JButton("Sacuvaj");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> paramList = new ArrayList();
				paramList.add(ChangeForm.this.textFieldIme.getText());
				paramList.add(ChangeForm.this.textFieldPrezime.getText());
				paramList.add(ChangeForm.this.textFieldBrIndeksa.getText());
				if(mainFormController != null)
					paramList.add(ChangeForm.this.textArea.getText());
				boolean isAdmin = true;
				if(mainFormController != null)
					isAdmin = false;
				new ChangeStudentsController(ChangeForm.this.mainFormController,ChangeForm.this.administratorFormController, paramList, student, numInList, ChangeForm.this, isAdmin);
				
			}
		});
		addButton.setBounds(69, 224, 89, 29);
		contentPane.add(addButton);

		panel2 = new JPanel();
		panel2.setBackground(new Color(0, 0, 139));
		panel2.setBounds(114, 131, 120, 88);
		contentPane.add(panel2);
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));

		textFieldIme = new JTextField(student.getIme());
		textFieldIme.setColumns(12);
		if(mainFormController != null)
			textFieldIme.setEditable(false);
		panel2.add(textFieldIme);

		textFieldPrezime = new JTextField(student.getPrezime());
		textFieldPrezime.setColumns(12);
		if(mainFormController != null)
			textFieldPrezime.setEditable(false);
		panel2.add(textFieldPrezime);

		textFieldBrIndeksa = new JTextField(student.getBrojIndeksa());
		textFieldBrIndeksa.setColumns(12);
		if(mainFormController != null)
			textFieldBrIndeksa.setEditable(false);
		panel2.add(textFieldBrIndeksa);
		
		if(mainFormController != null) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 50));
		panel2.add(scrollPane);

		textArea = new JTextArea(student.getKomentar());
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(textArea);
		
		setBounds(100, 100, 250, 354);
		panel.setBounds(10, 131, 93, 132);
		panel2.setBounds(114, 131, 120, 153);
		addButton.setBounds(80, 285, 89, 29);
		}

		initLabels();
	}

	private void initLabels() {
		for (Component labela : panel.getComponents()) {
			labela.setForeground(Color.WHITE);
			labela.setFont(labela.getFont().deriveFont(1, 12));
			labela.setPreferredSize(new Dimension(90, 15));;
			labela.setVisible(true);
		}
	}

}

