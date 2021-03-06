package org.unibl.etf.ps.studentviewer.gui.view.nalog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDTO;

public class AdministratorForm extends JFrame {

	private JPanel contentPane;
	private JTable adminZahtjeviJt;
	private JPanel componentsPane;
	private JButton odobriBtn;
	private JButton odbijBtn;
	private JButton upravljanjePredmetimaBtn;
	private JButton odjavaBtn;
	private JButton elektrijadaBtn;
	private JScrollPane	scrollPane;
	private ArrayList<ZahtjevDTO> list;
	
	private NalogDTO nalogDTO;
	private AdministratorFormController administratorFormController;
	
	private DefaultTableModel dtm;
	
	public AdministratorForm() {
		administratorFormController = new AdministratorFormController(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 10, 650, 550);
		setTitle("Zahtjevi");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(240, 0, 170, 120);
		contentPane.add(headerPictureLabel);

		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel correct1Label = new JLabel("STUDENT");
		correct1Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct1Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct1Label.setOpaque(true);
		correct1Label.setForeground(new Color(0, 0, 139));
		correct1Label.setBackground(new Color(255, 255, 255));
		correct1Label.setBounds(0, 0, 240, 120);
		contentPane.add(correct1Label);

		JLabel correct2Label = new JLabel("VIEWER");
		correct2Label.setBackground(SystemColor.text);
		correct2Label.setForeground(new Color(0, 0, 139));
		correct2Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct2Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct2Label.setOpaque(true);
		correct2Label.setBounds(410, 0, 240, 120);
		contentPane.add(correct2Label);
		
		componentsPane = new JPanel();
		componentsPane.setBackground(new Color(0, 0, 139));
		componentsPane.setLayout(null);
		componentsPane.setBounds(432, 138, 600, 430);
		contentPane.add(componentsPane);
		
		initComponents();
		initButtonsListeners();
		initTableListener();
	}

	private void initComponents() {
		
		MySQLDAOFactory factory = new MySQLDAOFactory();
		NalogDAO nalogDAO = factory.getNalogDAO();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		ZahtjevDAO zahtjevDAO = factory.getZahtjevDAO();
		list = zahtjevDAO.getAllZahtjev();
		
		adminZahtjeviJt = new JTable() {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent) {
					if(column == 0) {
						JComponent jc = (JComponent) c;
						jc.setToolTipText(getValueAt(row, column).toString());
					} else if(column == 1) {
						JComponent jc = (JComponent) c;
						jc.setToolTipText(getValueAt(row, column).toString());
					} else if(column == 2) {
						JComponent jc = (JComponent) c;
						jc.setToolTipText(getValueAt(row, column).toString());
					} else {
						JComponent jc = (JComponent) c;
						jc.setToolTipText(getValueAt(row, column).toString());
					}
				}
				return c;
			}
		};
		scrollPane = new JScrollPane(adminZahtjeviJt);
		scrollPane.setBounds(20, 150, 400, 350);
		contentPane.add(scrollPane);
		  
		odobriBtn = new JButton("Odobri");
		odobriBtn.setBounds(15, 29, 180, 40);
		odobriBtn.setEnabled(false);
		componentsPane.add(odobriBtn);
		  
		odbijBtn = new JButton("Odbij");
		odbijBtn.setBounds(15, 84, 180, 40);
		odbijBtn.setEnabled(false);
		componentsPane.add(odbijBtn);
		
		upravljanjePredmetimaBtn = new JButton("Upravljanje predmetima");
		upravljanjePredmetimaBtn.setBounds(15, 192, 180, 40);
		componentsPane.add(upravljanjePredmetimaBtn);
		
		elektrijadaBtn = new JButton("Elektrijada");
		elektrijadaBtn.setBounds(15, 246, 180, 40);
		componentsPane.add(elektrijadaBtn);
		
		odjavaBtn = new JButton("Odjava");
		odjavaBtn.setBounds(15, 300, 180, 40);
		componentsPane.add(odjavaBtn);
		/*Stankovic*/
		JButton upravljanjeStudentimaBtn = new JButton("Upravljanje studentima");
		upravljanjeStudentimaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorFormController.createAdminStudentForm(AdministratorForm.this);
			}
		});
		upravljanjeStudentimaBtn.setBounds(15, 138, 180, 40);
		componentsPane.add(upravljanjeStudentimaBtn);
		/*Stankovic end*/
		    
		dtm = new DefaultTableModel();
		dtm.addColumn("Ime");
		dtm.addColumn("Prezime");
		dtm.addColumn("Naziv predmeta");
		dtm.addColumn("Datum zahtjeva");
		for(int i = 0; i < list.size(); i++) {
			int id1 = (list.get(i)).getNalogId();
			NalogDTO nalogDTO = nalogDAO.getNalog(id1);
			int id2 = (list.get(i)).getPredmetId();
			PredmetDTO predmetDTO = predmetDAO.getPredmet(id2);
			Object[] rowData = { nalogDTO.getIme(), nalogDTO.getPrezime(), predmetDTO.getNazivPredmeta(), list.get(i).getDatumZahtjeva() };
			dtm.addRow(rowData);
		}
		adminZahtjeviJt.setModel(dtm);
		adminZahtjeviJt.setFont(new Font("Century Gothic", Font.BOLD, 12));
		adminZahtjeviJt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		adminZahtjeviJt.setForeground(new Color(0, 0, 139));
		adminZahtjeviJt.setBackground(new Color(173, 216, 230));
		  
	}
	
	private void initButtonsListeners() {
		
		odobriBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorFormController.odobriZahtjev();
				adminZahtjeviJt.getSelectionModel().setSelectionInterval(-1, -1);
				odbijBtn.setEnabled(false);
				odobriBtn.setEnabled(false);
			}
		});
		
		upravljanjePredmetimaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				administratorFormController.createAdminPredmetiForm();
			}
		});
		
		odbijBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorFormController.odbijZahtjev();
				adminZahtjeviJt.getSelectionModel().setSelectionInterval(-1, -1);
				odbijBtn.setEnabled(false);
				odobriBtn.setEnabled(false);
			}
		});
		
		odjavaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorFormController.odjava();
			}
		});
		
		elektrijadaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorFormController.elektrijadaOtvori();
			}
		});
		
	}
	
	private void initTableListener() {
		
		adminZahtjeviJt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(adminZahtjeviJt.getSelectedRow() != -1) {
					odbijBtn.setEnabled(true);
					odobriBtn.setEnabled(true);
				} else {
					odbijBtn.setEnabled(false);
					odobriBtn.setEnabled(false);
				}
			}
		});
		
	}

	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}

	public void setNalogDTO(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
	}
	
	public ZahtjevDTO getSelectedZahtjev() {
		ZahtjevDTO zahtjevDTO = list.get(adminZahtjeviJt.getSelectedRow());
		zahtjevDTO.setAdminId(nalogDTO.getNalogId());
		return zahtjevDTO;
	}
	
	public void removeSelectedRow() {
		list.remove(adminZahtjeviJt.getSelectedRow());
		dtm.removeRow(adminZahtjeviJt.getSelectedRow());
		adminZahtjeviJt.setModel(dtm);
	}
}
