/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Component;
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
import org.unibl.etf.ps.studentviewer.logic.controller.AddStudentsController;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;

public class AddForm extends JFrame {

	private JPanel contentPane;
	private MainFormController mainFormController = null;
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

	public AddForm(MainFormController mainFormController) {
		setTitle("Dodavanje");
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				mainFormController.resetAddFormOpened();
				mainFormController.resetChooseAddTypeFormOpened();
			}
		});

		this.mainFormController = mainFormController;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 354);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 139));
		panel.setBounds(10, 131, 93, 132);
		contentPane.add(panel);

		JLabel lblImeStudenta = new JLabel("Ime:");
		JLabel lblPrezime = new JLabel("Prezime:");
		JLabel lblBrojIndeksa = new JLabel("Broj indeksa:");
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
		panel.add(lblImeStudenta);
		panel.add(lblPrezime);
		panel.add(lblBrojIndeksa);

		JLabel lblKomentar = new JLabel("Komentar:");
		panel.add(lblKomentar);

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
				paramList.add(AddForm.this.textFieldIme.getText());
				paramList.add(AddForm.this.textFieldPrezime.getText());
				paramList.add(AddForm.this.textFieldBrIndeksa.getText());
				paramList.add(AddForm.this.textArea.getText());

				new AddStudentsController(AddForm.this.mainFormController, paramList, AddForm.this);
			}
		});
		addButton.setBounds(80, 285, 89, 29);
		contentPane.add(addButton);

		panel2 = new JPanel();
		panel2.setBackground(new Color(0, 0, 139));
		panel2.setBounds(114, 131, 120, 153);
		contentPane.add(panel2);
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));

		textFieldIme = new JTextField();
		panel2.add(textFieldIme);

		textFieldPrezime = new JTextField();
		panel2.add(textFieldPrezime);

		textFieldBrIndeksa = new JTextField();
		panel2.add(textFieldBrIndeksa);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 50));
		panel2.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		

		initLabels();
		initTextFields();
	}

	private void initLabels() {
		for (Component labela : panel.getComponents()) {
			labela.setForeground(Color.WHITE);
			labela.setFont(labela.getFont().deriveFont(1, 12));
			labela.setPreferredSize(new Dimension(90, 15));;
			labela.setVisible(true);
		}
	}

	private void initTextFields() {
		JTextField tmp = null;
		for (Component box : panel2.getComponents()) {
			if (box instanceof JTextField) {
				JTextField field = (JTextField) box;
				field.setColumns(12);
				if (tmp != null) {
					tmp.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							addButton.doClick();
						}
					});
				}
				tmp = field;
			}
		}
		tmp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addButton.doClick();
			}
		});
	}
}
