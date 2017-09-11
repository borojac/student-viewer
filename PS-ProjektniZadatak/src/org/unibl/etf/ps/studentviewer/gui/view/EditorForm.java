package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import org.unibl.etf.ps.studentviewer.logic.controller.EditorController;
import org.unibl.etf.ps.studentviewer.logic.controller.ElektrijadaController;



public class EditorForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static String izmjena;
	public static boolean kraj;
	private JTextArea textArea;
	private ElektrijadaController elektrijadaController;
	private EditorController editorController;
	private JTable targer;
	private ElektrijadaForm forma;
	private AbstractTableModel dataModel;
	private String sadrzajEditora;
	private boolean izbor;
	private JFrame unosTeksta;
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 * @param target 
	 * @param forma 
	 * @param dataModel 
	 * @param undoRedo 
	 * @param sadrzaj 
	 * @param izbor 
	 * @param izmjena 
	 */
	//true studenti false nastava
	public EditorForm(JTable target, ElektrijadaForm forma, AbstractTableModel dataModel, ElektrijadaController elektrijadaController, String sadrzajEditora, boolean izbor) {
		setTitle("Izmjena");
		this.elektrijadaController = elektrijadaController;
		this.dataModel = dataModel;
		this.forma = forma;
		this.sadrzajEditora = sadrzajEditora;
		this.izbor = izbor;
		editorController = new EditorController();
		unosTeksta = this;
		
		setResizable(false);
		setBounds(100, 100, 332, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(0, 0, 139));
		setContentPane(contentPane);
		
		JLabel lblNoviUnos = new JLabel("Sadrzaj celije:");
		lblNoviUnos.setForeground(Color.WHITE);
		lblNoviUnos.setFont(new Font("Century Gothic", Font.BOLD, 15));
		textArea = new JTextArea();
		textArea.setText(sadrzajEditora);
		 this.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	            	editorController.zatvoriProzor(e,forma);	            	
	            }
	        });
		JButton btnIzmjena = new JButton("Izmjena");
		btnIzmjena.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnIzmjena.setToolTipText("Izmjena");
		btnIzmjena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorController.izvrsiIzmjene(izbor,elektrijadaController,target,textArea,unosTeksta,dataModel,forma);
			}
		});
		
		
		textArea.setLineWrap(true);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnIzmjena)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNoviUnos)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textArea)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNoviUnos, Alignment.LEADING)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnIzmjena))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
}
