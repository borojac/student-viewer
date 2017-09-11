package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;

public class AdministratorForm extends JFrame {

	private JPanel contentPane;
	private JTable adminZahtjeviJt;
	private JPanel componentsPane;
	private JButton odobriBtn;
	private JButton odbijBtn;
	private JScrollPane	scrollPane;
	private ArrayList<ZahtjevDTO> list;
	int id1,id2;
	
	private NalogDTO nalogDTO;
	
	MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
	NalogDAO nalogDAO = nalogFactory.getNalogDAO();
	
	PredmetDAO predmetDAO = nalogFactory.getPredmetDAO();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministratorForm frame = new AdministratorForm();
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
	public AdministratorForm() {
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
		componentsPane.setBounds(0, 400, 600, 430);
		contentPane.add(componentsPane);
		
		initComponents();
	}

	private void initComponents()
	{
		
		MySQLDAOFactory zahtjevFactory = new MySQLDAOFactory();
		ZahtjevDAO zahtjevDAO = zahtjevFactory.getZahtjevDAO();
		list = zahtjevDAO.getAllZahtjev();
		
		adminZahtjeviJt = new JTable();
		  scrollPane = new JScrollPane(adminZahtjeviJt);
		  scrollPane.setBounds(70, 150, 500, 250);
		  contentPane.add(scrollPane);
		  
		  odobriBtn = new JButton("Odobri");
		  odobriBtn.setBounds(140, 40, 120, 40);
		  componentsPane.add(odobriBtn);
		  
		  odbijBtn = new JButton("Odbij");
		  odbijBtn.setBounds(380, 40, 120, 40);
		  componentsPane.add(odbijBtn);
		  
		  
		  DefaultTableModel dtm = new DefaultTableModel();
		  dtm.addColumn("Ime");
		  dtm.addColumn("Prezime");
		  dtm.addColumn("Naziv predmeta");
		  dtm.addColumn("Datum zahtjeva");
		  for(int i = 0; i < list.size(); i++) {
			id1 = (list.get(i)).getNalogId();
			NalogDTO nalogDTO = nalogDAO.getNalog(id1);
			id2 = (list.get(i)).getPredmetId();
			PredmetDTO predmetDTO = predmetDAO.getPredmet(id2);
		  Object[] rowData = { nalogDTO.getIme(), nalogDTO.getPrezime(), predmetDTO.getNazivPredmeta(), list.get(i).getDatumZahtjeva() };
		   dtm.addRow(rowData);
		  }
		  adminZahtjeviJt.setModel(dtm);
		  
		  
	}

	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}

	public void setNalogDTO(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
	}
	
}
