package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import org.unibl.etf.ps.studentviewer.gui.DodatnaNastavaDataTableModel;
import org.unibl.etf.ps.studentviewer.gui.StudentiZaElektrijaduTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.ElektrijadaController;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;



public class ElektrijadaForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableStudenti;
	private JTable tableDodatneNastave;
	private static ElektrijadaForm forma;
	private static DodatnaNastavaDataTableModel dodatnaNastavaDataModel;
	private static StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel;
	private static ElektrijadaController elektrijadaController;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ElektrijadaForm frame = new ElektrijadaForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ElektrijadaForm() throws Exception{
		forma = this;
		
		setTitle("Disciplina Naziv");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 810, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(0, 0, 139));
		setContentPane(contentPane);
		JLabel lblListaStudenata = new JLabel("Lista studenata");
		lblListaStudenata.setForeground(Color.WHITE);
		lblListaStudenata.setFont(new Font("Century Gothic", Font.BOLD, 15));
		
		JScrollPane scrollPaneStudenti = new JScrollPane();
		
		scrollPaneStudenti.setBackground(Color.WHITE);	
		scrollPaneStudenti.setBorder(UIManager.getBorder("Button.border"));
		scrollPaneStudenti.setBounds(10, 219, 558, 382);
		
		JLabel lblListaNastavnihTema = new JLabel("Dodatna nastava");
		lblListaNastavnihTema.setForeground(Color.WHITE);
		lblListaNastavnihTema.setFont(new Font("Century Gothic", Font.BOLD, 15));
		
		JScrollPane scrollPaneNastavneTeme = new JScrollPane();
		scrollPaneNastavneTeme.setBackground(Color.WHITE);	
		scrollPaneNastavneTeme.setBorder(UIManager.getBorder("Button.border"));
		scrollPaneNastavneTeme.setBounds(10, 219, 558, 382);
		
		
		elektrijadaController = new ElektrijadaController();
		
		
		String date = "23/10/2012 08:15 AM" ;
		
		Date datum = new SimpleDateFormat("dd/MM/yyyy hh:mm a").parse(date);
		String newstring = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(datum);
		ElektrijadaController.listaDodatnihNastava.add(new DodatnaNastavaDTO("Konstruktor kopije",newstring, "Napomena"));
		
		ElektrijadaController.listaStudenata.add(new StudentZaElektrijaduDTO("1111/11", "Marko", "Marković", "Prvo mjesto na Elektrijadi u Beogradu 2012. godine."));
		
		studentiZaElektrijaduDataModel = new StudentiZaElektrijaduTableModel(ElektrijadaController.listaStudenata);
		tableStudenti = new JTable(studentiZaElektrijaduDataModel){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		        Component c = super.prepareRenderer(renderer, row, column);
		        if (c instanceof JComponent) {
		           if(column == 3){
		            //X is your particlur column number

		            JComponent jc = (JComponent) c;
		            jc.setToolTipText(getValueAt(row, column).toString());
		           }
		        }
		        return c;
		    }
		};
		tableStudenti.setFont(new Font("Century Gothic", Font.BOLD, 12));
		tableStudenti.setForeground(new Color(0, 0, 139));
		tableStudenti.setBackground(new Color(173, 216, 230));
		scrollPaneStudenti.setViewportView(tableStudenti);
		dodatnaNastavaDataModel = new DodatnaNastavaDataTableModel(ElektrijadaController.listaDodatnihNastava);
		tableDodatneNastave = new JTable(dodatnaNastavaDataModel){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		        Component c = super.prepareRenderer(renderer, row, column);
		        if (c instanceof JComponent) {
		           if(column == 2){
		            JComponent jc = (JComponent) c;
		            jc.setToolTipText(getValueAt(row, column).toString());
		           }
		        }
		        return c;
		    }
		};
		
		tableDodatneNastave.setFont(new Font("Century Gothic", Font.BOLD, 12));
		tableDodatneNastave.setForeground(new Color(0, 0, 139));
		tableDodatneNastave.setBackground(new Color(173, 216, 230));
		scrollPaneNastavneTeme.setViewportView(tableDodatneNastave);
		
		tableDodatneNastave.setRowHeight(25);
		tableStudenti.setRowHeight(25);
		
		tableStudenti.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
				      if (e.getClickCount() == 2) 
				    	 elektrijadaController.otvaranjeEditora(e,forma,studentiZaElektrijaduDataModel,true);			         				        				         					      
				   }
				});
		
		tableDodatneNastave.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) 
			    	 elektrijadaController.otvaranjeEditora(e,forma,dodatnaNastavaDataModel,false);	
			   }
			});
		
		JButton btnBrisanjeListe = new JButton("Brisanje liste studenata");
		btnBrisanjeListe.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBrisanjeListe.setToolTipText("Brisanje liste studenata");		
		btnBrisanjeListe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elektrijadaController.brisanjeListeControl(tableStudenti,studentiZaElektrijaduDataModel);
			}
		});
		
		JButton btnBrisanjeNastavneTeme = new JButton("Brisanje dodatne nastave");
		btnBrisanjeNastavneTeme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 elektrijadaController.brisanjeNastaveControl(forma,tableDodatneNastave,dodatnaNastavaDataModel);	         
			}
		});		
		btnBrisanjeNastavneTeme.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBrisanjeNastavneTeme.setToolTipText("Brisanje dodatne nastave");	
		
		JButton btnBrisanjeStudenta = new JButton("Brisanje studenta");
		btnBrisanjeStudenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elektrijadaController.brisanjeStudentaControl(forma,tableStudenti,studentiZaElektrijaduDataModel);				
			}
		});
		btnBrisanjeStudenta.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBrisanjeStudenta.setToolTipText("Brisanje studenta");
		
		JButton btnDodavanjeNastavneTeme = new JButton("Dodavanje dodatne nastave");
		btnDodavanjeNastavneTeme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elektrijadaController.dodavanjeNastaveControl(forma,tableDodatneNastave,dodatnaNastavaDataModel);					
			}
		});
		btnDodavanjeNastavneTeme.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDodavanjeNastavneTeme.setToolTipText("Dodavanje dodatne nastave");
		
		JButton btnDodajStudenta = new JButton("Dodaj studenta");
		btnDodajStudenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elektrijadaController.dodavanjeStudentaControl(forma,tableStudenti,studentiZaElektrijaduDataModel);			
			}
			
		});
		btnDodajStudenta.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDodajStudenta.setToolTipText("Dodaj studenta");
		
		
		JButton btnExportPdff = new JButton("Export pdf");
		btnExportPdff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnExportPdff.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExportPdff.setToolTipText("Export pdf");
		
		JButton btnExporttampa = new JButton("Export \u0161tampa\u010D");
		btnExporttampa.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExporttampa.setToolTipText("Export \u0161tampa\u010D");
		
		JButton btnNazad = new JButton("Nazad");
		btnNazad.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNazad.setToolTipText("Nazad");
		btnNazad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUndo.setToolTipText("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elektrijadaController.undoOpcija(dodatnaNastavaDataModel,tableDodatneNastave,studentiZaElektrijaduDataModel,tableStudenti);				
			}
		});
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRedo.setToolTipText("Redo");
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elektrijadaController.redoOpcija(dodatnaNastavaDataModel,tableDodatneNastave,studentiZaElektrijaduDataModel,tableStudenti);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblListaStudenata, Alignment.LEADING)
						.addComponent(lblListaNastavnihTema, Alignment.LEADING)
						.addComponent(scrollPaneNastavneTeme, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneStudenti, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(112)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnExporttampa, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(btnExportPdff, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(btnDodajStudenta, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(btnDodavanjeNastavneTeme, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(btnBrisanjeStudenta, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(btnBrisanjeNastavneTeme, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(btnBrisanjeListe, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnNazad, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnUndo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnRedo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblListaStudenata)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPaneStudenti, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
							.addGap(35)
							.addComponent(lblListaNastavnihTema)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPaneNastavneTeme, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnExporttampa)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExportPdff)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDodajStudenta)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDodavanjeNastavneTeme)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBrisanjeStudenta)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBrisanjeNastavneTeme)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBrisanjeListe)
							.addGap(18)
							.addComponent(btnUndo)
							.addGap(18)
							.addComponent(btnRedo)
							.addGap(18)
							.addComponent(btnNazad)))
					.addGap(40))
		);
		
		
		contentPane.setLayout(gl_contentPane);
	}

}
