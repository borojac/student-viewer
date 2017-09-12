package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.gui.ShowViewData;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.SortChooseFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.SortFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.Sort;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;

public class SortChooseForm extends JFrame {

	private JPanel contentPane;
	SortForm sf;
	SortFormController sfc;
	JList<String> chooseList;
	JList<String> choosenList;

	/*
	 * Create the frame.
	 */
	public SortChooseForm(SortForm sf, MainFormController mainFormController, SortFormController sfc) {
		this.sfc = sfc;
		this.sf = sf;
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 338, 413);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBackground(new Color(255, 255, 255));
		label.setOpaque(true);
		label.setBounds(0, 0, 36, 120);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(283, 0, 45, 120);
		contentPane.add(lblNewLabel);
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(73, 0, 170, 120);
		contentPane.add(headerPictureLabel);
		
		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 141, 113, 179);
			contentPane.add(scrollPane);

			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(209, 143, 113, 179);
			contentPane.add(scrollPane_1);
			
			choosenList = new JList<String>();
			choosenList.setFont(new Font("Century", Font.BOLD, 13));
			DefaultListModel<String> model2 = new DefaultListModel<String>();
			for (String s : sf.getExamsToSort())
				model2.addElement(s);
			choosenList.setModel(model2);
			scrollPane_1.setViewportView(choosenList);
			
			chooseList = new JList<String>();
			DefaultListModel<String> model1 = new DefaultListModel<String>();
			for (String s : StudentsForMainTable.getAllIspiti())
				if (!sf.getExamsToSort().contains(s))
				model1.addElement(s);
			chooseList.setModel(model1);
			chooseList.setFont(new Font("Century", Font.BOLD, 13));
			scrollPane.setViewportView(chooseList);
			
			
			
			JButton btnNewButton = new JButton(">");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new SortChooseFormController(sf, SortChooseForm.this).rightFlow();
				}
			});
			btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
			btnNewButton.setBounds(133, 156, 66, 38);
			contentPane.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("<");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new SortChooseFormController(sf, SortChooseForm.this).leftFlow();
				}
			});
			btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
			btnNewButton_1.setBounds(133, 267, 66, 38);
			contentPane.add(btnNewButton_1);
			
			JButton btnNewButton_2 = new JButton("Sacuvaj");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new SortChooseFormController(sf, SortChooseForm.this).save(sfc);
					SortChooseForm.this.dispose();
					sf.setVisible(true);
					if (sf.getSortParams().contains(Sort.TEST))
						sf.setTestCheckBox();
					else
						sf.resetTestCheckBox();
				}
			});
			btnNewButton_2.setBounds(121, 345, 89, 29);
			contentPane.add(btnNewButton_2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public JList getChooseList() {
		return chooseList;
	}
	
	public JList getChoosenList() {
		return choosenList;
	}
	
	public String[] getLeftSelected() {
		List<String> selectedList = chooseList.getSelectedValuesList();
		String[] forRet = selectedList.toArray(new String[selectedList.size()]);
		return forRet;
	}
	
	public String[] getRightSelected() {
		List<String> selectedList = choosenList.getSelectedValuesList();
		String[] forRet = selectedList.toArray(new String[selectedList.size()]);
		return forRet;
	}
	
	public void deleteLeftSelected() {
			
	}
	
}
