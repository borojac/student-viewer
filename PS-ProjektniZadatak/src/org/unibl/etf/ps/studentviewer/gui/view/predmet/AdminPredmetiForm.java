package org.unibl.etf.ps.studentviewer.gui.view.predmet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.imgscalr.Scalr;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.predmet.AdminPredmetiFormController;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class AdminPredmetiForm extends JFrame {

	private JPanel contentPane;
	
	private JTable predmetiTbl;
	private JButton dodajPredmeteBtn;
	private JButton izmjeniPredmetBtn;
	private JButton obrisiPredmetBtn;
	
	private JScrollPane scroll;
	
	private DefaultTableModel dtm;
	
	private ArrayList<PredmetDTO> predmetiList;
	
	private AdminPredmetiFormController adminPredmetiFormController;

	/**
	 * Create the frame.
	 */
	public AdminPredmetiForm() {
		adminPredmetiFormController = new AdminPredmetiFormController(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AdministratorFormController.resetAdminPredmetiFormOpened();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 624, 500);
		setResizable(false);
		setTitle("Predmeti");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel headerPictureLabel = new JLabel("");
		headerPictureLabel.setBounds(227, 0, 170, 120);
		contentPane.add(headerPictureLabel);

		try {
			BufferedImage headerImg = ImageIO.read(new File("img" + File.separator + "BellTower-RGB(JPG).jpg"));
			headerImg = Scalr.resize(headerImg, Scalr.Mode.FIT_EXACT, headerPictureLabel.getWidth(),
					headerPictureLabel.getHeight(), null);
			headerPictureLabel.setIcon(new ImageIcon(headerImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel whiteCorrectionLabel = new JLabel("");
		whiteCorrectionLabel.setOpaque(true);
		whiteCorrectionLabel.setBackground(Color.WHITE);
		whiteCorrectionLabel.setBounds(0, 0, 227, 120);
		contentPane.add(whiteCorrectionLabel);

		JLabel whiteCorrectionLabel2 = new JLabel("");
		whiteCorrectionLabel2.setBackground(Color.WHITE);
		whiteCorrectionLabel2.setOpaque(true);
		whiteCorrectionLabel2.setBounds(397, 0, 227, 120);
		contentPane.add(whiteCorrectionLabel2);
		
		initTable();
		initButtons();
		initButtonsListeners();
	}
	
	public void initTable() {
		try {
			contentPane.remove(scroll);
		} catch(Exception e) {
			
		}
		
		MySQLDAOFactory factory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		predmetiList = predmetDAO.getAllPredmet();
		dtm = new DefaultTableModel();
		dtm.addColumn("Sifra");
		dtm.addColumn("Naziv");
		dtm.addColumn("ECTS");
		dtm.addColumn("Semestar");
		dtm.addColumn("Tip");
		dtm.addColumn("Studijski program");
		dtm.addColumn("Ciklus");
		dtm.addColumn("Skolska godina");
		
		for(int i = 0; i < predmetiList.size(); i++) {
			PredmetDTO p = predmetiList.get(i);
			Object[] rowData = { p.getSifraPredmeta(), p.getNazivPredmeta(), p.getEcts(), p.getSemestar(), p.getTipPredmeta(), p.getNazivSP(), p.getCiklus(), p.getSkolskaGodina() };
			dtm.addRow(rowData);
		}
		
		String[] columnToolTips = { null, null, null, null, null, "Studijski program", null, "Skolska godina" };
		predmetiTbl = new JTable(dtm) {
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					public String getToolTipText(MouseEvent e) {
						java.awt.Point p = e.getPoint();
			            int index = columnModel.getColumnIndexAtX(p.x);
			            int realIndex = columnModel.getColumn(index).getModelIndex();
			            return columnToolTips[realIndex];
					}
				};
			}
			 
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
					if (c instanceof JComponent) {
						if(column == 1) {
							JComponent jc = (JComponent) c;
							jc.setToolTipText(getValueAt(row, column).toString());
						} else if(column == 5) {
							JComponent jc = (JComponent) c;
							jc.setToolTipText(getValueAt(row, column).toString());
						} else {
							JComponent jc = (JComponent) c;
							jc.setToolTipText(null);
						}
					}
				return c;
			}
		};
		predmetiTbl.setAutoCreateRowSorter(true);
		predmetiTbl.setFont(new Font("Century Gothic", Font.BOLD, 12));
		predmetiTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		predmetiTbl.setForeground(new Color(0, 0, 139));
		predmetiTbl.setBackground(new Color(173, 216, 230));
		
		scroll = new JScrollPane(predmetiTbl);
		scroll.setBounds(10, 130, 600, 252);
		contentPane.add(scroll);
		
		initTableListener();
	}
	
	private void initButtons() {
		dodajPredmeteBtn = new JButton("Dodaj predmete");
		dodajPredmeteBtn.setBounds(60, 400, 135, 35);
		contentPane.add(dodajPredmeteBtn);
		
		izmjeniPredmetBtn = new JButton("Izmjeni predmet");
		izmjeniPredmetBtn.setBounds(245, 400, 135, 35);
		izmjeniPredmetBtn.setEnabled(false);
		contentPane.add(izmjeniPredmetBtn);
		
		obrisiPredmetBtn = new JButton("Obrisi predmet");
		obrisiPredmetBtn.setBounds(430, 400, 135, 34);
		obrisiPredmetBtn.setEnabled(false);
		contentPane.add(obrisiPredmetBtn);
	}
	
	private void initButtonsListeners() {
		
		dodajPredmeteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adminPredmetiFormController.createPredmetChooseAddTypeForm();
			}
		});
		
		izmjeniPredmetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adminPredmetiFormController.izmjeniPredmet();
			}
		});
		
		obrisiPredmetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adminPredmetiFormController.obrisiPredmet();
			}
		});
		
	}
	
	private void initTableListener() {
		
		predmetiTbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(predmetiTbl.getSelectedRow() != -1) {
					izmjeniPredmetBtn.setEnabled(true);
					obrisiPredmetBtn.setEnabled(true);
				} else {
					izmjeniPredmetBtn.setEnabled(false);
					obrisiPredmetBtn.setEnabled(false);
				}
			}
		});
		
	}
	
	public PredmetDTO getSelectedPredmet() {
		return predmetiList.get(predmetiTbl.getSelectedRow());
	}
	
	public void removeSelectedRow() {
		predmetiList.remove(predmetiTbl.getSelectedRow());
		dtm.removeRow(predmetiTbl.getSelectedRow());
		predmetiTbl.setModel(dtm);
	}
	
	public JTable getPredmetiTbl() {
		return predmetiTbl;
	}
	
	public JButton getIzmjeniBtn() {
		return izmjeniPredmetBtn;
	}
	
	public JButton getObrisiBtn() {
		return obrisiPredmetBtn;
	}

}
