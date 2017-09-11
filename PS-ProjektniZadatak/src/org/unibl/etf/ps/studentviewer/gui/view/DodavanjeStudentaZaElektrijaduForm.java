package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import org.unibl.etf.ps.studentviewer.gui.StudentiZaElektrijaduTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.DodavanjeStudentaZaElektrijaduController;
import org.unibl.etf.ps.studentviewer.logic.controller.ElektrijadaController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class DodavanjeStudentaZaElektrijaduForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -810389623048410641L;
	private JPanel contentPane;
	private static ArrayList<StudentZaElektrijaduDTO> studentiPredmeti = new ArrayList<>();
	private JTable tableStudentiPredmeti;
	private StudentiZaElektrijaduTableModel modelStudentiPredmeti;
	private ElektrijadaForm forma;
	private JTable tableStudenti;
	private ElektrijadaController elektrijadaController;
	private StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel;
	private DodavanjeStudentaZaElektrijaduController studentController;
	private DodavanjeStudentaZaElektrijaduForm dodavanjeForma;

	public static void popuniListu() {
		studentiPredmeti.add(new StudentZaElektrijaduDTO(1,"1122/13", "Janko", "Raspopovic", "Napomena"));
		studentiPredmeti.add(new StudentZaElektrijaduDTO(2,"1245/13", "Marko", "Krusic", "Napomena1"));
		studentiPredmeti.add(new StudentZaElektrijaduDTO(3,"1236/13", "Sinisa", "Mutic", "Napomena2"));
		studentiPredmeti.add(new StudentZaElektrijaduDTO(4,"1258/13", "Jovica", "Cubic", "Napomena3"));
		studentiPredmeti.add(new StudentZaElektrijaduDTO(5,"1126/13", "Dragica", "Takic", "Napomena4"));
	}

	public DodavanjeStudentaZaElektrijaduForm(ElektrijadaForm forma, JTable tableStudenti,
			ElektrijadaController kontroler, StudentiZaElektrijaduTableModel studentiZaElektrijaduDataModel) {
		this.forma = forma;
		dodavanjeForma = this;
		this.tableStudenti = tableStudenti;
		this.elektrijadaController = kontroler;
		this.studentiZaElektrijaduDataModel = studentiZaElektrijaduDataModel;
		studentController = new DodavanjeStudentaZaElektrijaduController(dodavanjeForma);
		setResizable(false);
		setTitle("Dodavanje studenata");
		setBounds(100, 100, 383, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(0, 0, 139));
		setContentPane(contentPane);
		popuniListu();

		modelStudentiPredmeti = new StudentiZaElektrijaduTableModel(studentiPredmeti);
		tableStudentiPredmeti = new JTable(modelStudentiPredmeti) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2824565547549108090L;

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent) {
					if (column == 3) {
						JComponent jc = (JComponent) c;
						jc.setToolTipText(getValueAt(row, column).toString());
					}
				}
				return c;
			}
		};
		tableStudentiPredmeti.setFont(new Font("Century Gothic", Font.BOLD, 12));
		tableStudentiPredmeti.setForeground(new Color(0, 0, 139));
		tableStudentiPredmeti.setBackground(new Color(173, 216, 230));
		tableStudentiPredmeti.setRowHeight(25);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tableStudentiPredmeti);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(UIManager.getBorder("Button.border"));
		scrollPane.setBounds(10, 219, 558, 382);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				studentController.zatvoriProzor(forma, e);
			}
		});
		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDodaj.setToolTipText("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentController.dodajStudentaControl( tableStudenti, tableStudentiPredmeti,
						studentiZaElektrijaduDataModel, kontroler);
			}
		});

		JButton btnNazad = new JButton("Nazad");
		btnNazad.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNazad.setToolTipText("Nazad");
		btnNazad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentController.opcijaNazad(forma, dodavanjeForma);
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(btnNazad, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
								.addComponent(btnDodaj, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(
										gl_contentPane.createSequentialGroup().addContainerGap()
												.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 388,
														Short.MAX_VALUE)
												.addGap(18)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
														.addComponent(btnDodaj).addComponent(btnNazad))
												.addContainerGap()));

		contentPane.setLayout(gl_contentPane);
	}

}
