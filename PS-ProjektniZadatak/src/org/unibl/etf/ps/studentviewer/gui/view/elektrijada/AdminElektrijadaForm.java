package org.unibl.etf.ps.studentviewer.gui.view.elektrijada;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.unibl.etf.ps.studentviewer.gui.view.nalog.AdministratorForm;
import org.unibl.etf.ps.studentviewer.logic.controller.elektrijada.AdministratorElektrijadaFormController;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ZahtjevDisciplinaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDisciplinaDTO;

public class AdminElektrijadaForm extends JFrame {

	private JPanel contentPane;
	private JTable adminZahtjeviJt;
	private JPanel componentsPane;
	private JButton odobriBtn;
	private JButton odbijBtn;
	private JButton obrisiDisciplinuBtn;
	private JButton nazadBtn;
	private JButton dodajDisciplinuBtn;
	private JButton dodajElektrijaduBtn;
	private JButton obrisiElektrijaduBtn;
	private JButton azurirajElektrijaduBtn;
	private JScrollPane	scrollPane;
	private ArrayList<ZahtjevDisciplinaDTO> list;
	
	private NalogDTO nalogDTO;
	private AdministratorElektrijadaFormController administratorElektrijadaFormController;
	private AdministratorForm administratorForm;
	
	private DefaultTableModel dtm;
	
	public AdminElektrijadaForm(AdministratorForm administratorForm) {
		administratorElektrijadaFormController = new AdministratorElektrijadaFormController(this);
		this.administratorForm = administratorForm;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						administratorElektrijadaFormController.zatvoriProzor();
					}
				});
			}
		});
		setBounds(100, 10, 650, 640);
		setTitle("Elektrijada");
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
		componentsPane.setBounds(433, 150, 201, 441);
		contentPane.add(componentsPane);
		
		initComponents();
		initButtonsListeners();
		initTableListener();
	}

	private void initComponents() {
		
		MySQLDAOFactory factory = new MySQLDAOFactory();
		NalogDAO nalogDAO = factory.getNalogDAO();
		ElektrijadaDAO elektrijadaDAO = factory.getElektrijadaDAO();
		ZahtjevDisciplinaDAO zahtjevDisciplinaDAO = factory.getZahtjevDiciplinaDAO();
		list = zahtjevDisciplinaDAO.getAllZahtjev();
		
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
		scrollPane.setBounds(20, 150, 400, 243);
		contentPane.add(scrollPane);
		  
		odobriBtn = new JButton("Odobri");
		odobriBtn.setBounds(15, 29, 180, 40);
		odobriBtn.setEnabled(false);
		componentsPane.add(odobriBtn);
		  
		odbijBtn = new JButton("Odbij");
		odbijBtn.setBounds(15, 84, 180, 40);
		odbijBtn.setEnabled(false);
		componentsPane.add(odbijBtn);
		
		nazadBtn = new JButton("Nazad");
		nazadBtn.setBounds(15, 299, 180, 40);
		componentsPane.add(nazadBtn);
		
		    
		dtm = new DefaultTableModel();
		dtm.addColumn("Ime");
		dtm.addColumn("Prezime");
		dtm.addColumn("Naziv discipline");
		dtm.addColumn("Elektrijada");
		dtm.addColumn("Datum zahtjeva");
		for(int i = 0; i < list.size(); i++) {
			int id1 = (list.get(i)).getNalogId();
			NalogDTO nalogDTO = nalogDAO.getNalog(id1);
			int id2 = list.get(i).getElektrijadaId();
			ElektrijadaDTO elektrijadaDTO = elektrijadaDAO.getElektrijadaPoId(id2);
	
			Object[] rowData = { nalogDTO.getIme(), nalogDTO.getPrezime(), list.get(i).getNaziv(), elektrijadaDTO.getLokacija()+ ", " + elektrijadaDTO.getDatum(),list.get(i).getDatumZahtjeva() };
			dtm.addRow(rowData);
		}
		adminZahtjeviJt.setModel(dtm);
		adminZahtjeviJt.setFont(new Font("Century Gothic", Font.BOLD, 12));
		adminZahtjeviJt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		adminZahtjeviJt.setForeground(new Color(0, 0, 139));
		adminZahtjeviJt.setBackground(new Color(173, 216, 230));
		
		dodajElektrijaduBtn = new JButton("Dodaj Elektrijadu");
		dodajElektrijaduBtn.setBounds(20, 449, 180, 40);
		contentPane.add(dodajElektrijaduBtn);
		
		obrisiElektrijaduBtn = new JButton("Obri\u0161i Elektrijadu");
		obrisiElektrijaduBtn.setBounds(20, 500, 180, 40);
		contentPane.add(obrisiElektrijaduBtn);
		
		azurirajElektrijaduBtn = new JButton("A\u017Euriraj Elektrijadu");
		azurirajElektrijaduBtn.setBounds(20, 551, 180, 40);
		contentPane.add(azurirajElektrijaduBtn);
		
		dodajDisciplinuBtn = new JButton("Dodaj disciplinu");
		dodajDisciplinuBtn.setBounds(240, 449, 180, 40);
		contentPane.add(dodajDisciplinuBtn);
		
		obrisiDisciplinuBtn = new JButton("Obriši disciplinu");
		obrisiDisciplinuBtn.setBounds(240, 500, 180, 40);
		contentPane.add(obrisiDisciplinuBtn);
		  
	}
	
	private void initButtonsListeners() {
		
		odobriBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorElektrijadaFormController.odobriZahtjev();
			}
		});
		
		dodajDisciplinuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				administratorElektrijadaFormController.dodajDisciplinu();
			}
		});
		
		odbijBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorElektrijadaFormController.odbijZahtjev();
			}
		});
		
		nazadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorElektrijadaFormController.nazadNaAdminFormu();
			}
		});
		
		obrisiDisciplinuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorElektrijadaFormController.obrisiDisciplinu();
			}
		});
		
		dodajElektrijaduBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorElektrijadaFormController.dodajElektrijadu();
			}
		});
		
		obrisiElektrijaduBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorElektrijadaFormController.obrisiElektrijadu();
			}
		});
	
		azurirajElektrijaduBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administratorElektrijadaFormController.azurirajElektrijadu();
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
	
	public ZahtjevDisciplinaDTO getSelectedZahtjev() {
		ZahtjevDisciplinaDTO zahtjevDTO =null;
		if (!list.isEmpty()){
			zahtjevDTO = list.get(adminZahtjeviJt.getSelectedRow());
			zahtjevDTO.setAdminId(nalogDTO.getNalogId());
		}
		return zahtjevDTO;
	}
	
	public void removeSelectedRow() {
		list.remove(adminZahtjeviJt.getSelectedRow());
		dtm.removeRow(adminZahtjeviJt.getSelectedRow());
		adminZahtjeviJt.setModel(dtm);
	}
	
	public AdministratorForm getAdminForma(){
		return administratorForm;
	}
}
