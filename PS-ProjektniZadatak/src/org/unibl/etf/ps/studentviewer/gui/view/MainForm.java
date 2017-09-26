package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.imgscalr.Scalr;
import org.jdesktop.swingx.search.TableSearchable;
import org.unibl.etf.ps.studentviewer.gui.view.student.GradeGenerationForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.MainTable;
import org.unibl.etf.ps.studentviewer.gui.view.student.MainTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.test.TestoviTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.maintableshow.UndoRedoData;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.DisciplinaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dao.TestDAO;
import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm extends JFrame {
	
	private JPanel contentPane;
	private MainFormController mainFormController = new MainFormController(this);
	private NalogDTO nalogDTO;
	private PredmetDTO lastPredmet = null;

	private ArrayList<PredmetDTO> predmetiList;
	
	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mainFormController.postaviMainForm(getSelectedPredmet(), lastPredmet);
			lastPredmet = getSelectedPredmet();
		}
	};

	// ------- Components!!! ------- //
	ArrayList<JButton> buttons = new ArrayList<JButton>();

	private JButton searchButton = null;
	private JButton showViewBtn = null;
	private JButton sortBtn = null;
	private JButton filterBtn = null;
	private JButton addBtn = null;
	private JButton deleteBtn = null;
	private JButton changeBtn = null;
	private JButton exportBtn = null;
	private JButton accountBtn = null;
	private JButton restoreButton = null;
	private JButton undoButton = null;
	private JButton redoButton = null;

	private JPanel panel = null;
	private JPanel panel_1 = null;
	private JPanel buttonPanel;

	private JScrollPane scrollPane = null;

	private static JScrollPane helpPane = null;
	public static JScrollPane getScrollPane() {
		return helpPane;
	}
	
	private MainTable mainTable = null;

	private JPanel testoviPanel;
	private JTable testoviTable;
	private JScrollPane testoviScrollPane;
	private JButton btnDodaj;
	private JButton btnIzmjeni;
	private JButton btnBrisi;
	private JLabel correct2Label;
	private JTextField textField;

	private JComboBox<String> predmetiCB;
	private JComboBox<String> elektrijadaCB;
	private JComboBox<String> disciplineCB;
	private JButton prikaziDisciplinuBtn;

	// ------- EndComponents!!! ------- //

	/**
	 * @throws IOException
	 *             Create the frame. @throws
	 */

	@Override
	public void dispose() {
		UndoRedoData.saveState(getNalogDTO(), getSelectedPredmet());
		super.dispose();
	}

	public MainForm(NalogDTO nalogDTO) throws IOException {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				UndoRedoData.saveState(getNalogDTO(), getSelectedPredmet());
			}
		});
		this.nalogDTO = nalogDTO;
		setResizable(false);
		setTitle("StudentViewer_v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 10, 1200, 640);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(0, 0, 139));
		buttonPanel.setBounds(578, 172, 147, 429);
		contentPane.add(buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 12));

		BufferedImage img = ImageIO.read(new File("img\\BellTower-RGB(JPG).jpg"));
		BufferedImage correctionImage = ImageIO.read(new File("img\\whiteCorrection.png"));

		JLabel label = new JLabel("");
		label.setBounds(515, 0, 170, 120);

		img = Scalr.resize(img, Scalr.Mode.FIT_EXACT, label.getWidth(), label.getHeight(), null);
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		contentPane.add(label);

		JLabel correct1Label = new JLabel("STUDENT");
		correct1Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct1Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct1Label.setOpaque(true);
		correct1Label.setForeground(new Color(0, 0, 139));
		correct1Label.setBackground(new Color(255, 255, 255));
		correct1Label.setBounds(0, 0, 515, 120);
		contentPane.add(correct1Label);

		correct2Label = new JLabel("VIEWER");
		correct2Label.setBackground(SystemColor.text);
		correct2Label.setForeground(new Color(0, 0, 139));
		correct2Label.setHorizontalAlignment(SwingConstants.CENTER);
		correct2Label.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 45));
		correct2Label.setOpaque(true);
		correct2Label.setBounds(685, 0, 509, 120);
		contentPane.add(correct2Label);

		JLabel lblPretraga = new JLabel("Pretraga:");
		lblPretraga.setForeground(Color.WHITE);
		lblPretraga.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblPretraga.setBounds(10, 172, 78, 25);
		contentPane.add(lblPretraga);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.toString().contains("keyText=Enter"))
					mainFormController.search(MainForm.this);
			}
		});
		textField.setForeground(new Color(0, 0, 139));
		textField.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 15));
		textField.setBounds(97, 172, 263, 25);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel predmetiLbl = new JLabel("Moji predmeti:");
		predmetiLbl.setForeground(Color.WHITE);
		predmetiLbl.setFont(new Font("Century Gothic", Font.BOLD, 15));
		predmetiLbl.setBounds(745, 145, 200, 25);
		contentPane.add(predmetiLbl);

		JLabel disciplineLbl = new JLabel("Moje discipline:");
		disciplineLbl.setForeground(Color.WHITE);
		disciplineLbl.setFont(new Font("Century Gothic", Font.BOLD, 15));
		disciplineLbl.setBounds(745, 288, 200, 25);
		contentPane.add(disciplineLbl);

		JLabel elektrijadaLbl = new JLabel("Elektrijada:");
		elektrijadaLbl.setForeground(Color.WHITE);
		elektrijadaLbl.setFont(new Font("Century Gothic", Font.BOLD, 15));
		elektrijadaLbl.setBounds(745, 225, 200, 25);
		contentPane.add(elektrijadaLbl);

		
		initButtons();
		initButtonsListeners();
		initPredmetiComboBox();
		initElektrijadaComboBox();
		initComboBoxListener();
		

		setButtonsSize();

		initTestoviPanel();
		lastPredmet = getSelectedPredmet();

		
		initTable();
		scrollPane = new JScrollPane(mainTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(UIManager.getBorder("Button.border"));
		scrollPane.setBounds(10, 219, 556, 382);
		contentPane.add(scrollPane);
		helpPane = scrollPane;
		

	}

	public String getSearchParams() {
		return textField.getText();
	}

	public void setSearchParams(String params) {
		textField.setText(params);
	}

	private void initButtonsListeners() {

		filterBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormController.createFilterForm();
			}
		});

		sortBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormController.createSortForm();
			}
		});

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFormController.search(MainForm.this);
			}
		});

		restoreButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormController.restoreTable();
			}
		});

		showViewBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormController.createShowForm();
			}
		});

		undoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ArrayList<StudentMainTableDTO> students = UndoRedoData.undo();
				if (students != null)
					mainTable.setStudents(students);
			}
		});

		accountBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormController.createAccountForm();
			}
		});

	}

	private void initTable() {
		mainTable = new MainTable();
		
		StudentsForMainTable.setMainTable(mainTable);
		mainTable.setFont(new Font("Century Gothic", Font.BOLD, 15));
		mainTable.setForeground(new Color(0, 0, 139));
		mainTable.setBackground(new Color(173, 216, 230));
		MainTableModel model = new MainTableModel();
		mainTable.setModel(model);

		if (getSelectedPredmet() != null) {
			ArrayList<StudentMainTableDTO> temp = StudentsForMainTable.initShowInMainTable(getSelectedPredmet(),
					getNalogDTO());

			model.setColumnIdentifiers(new Object[] { "Indeks", "Ime", "Prezime" });
			mainTable.setStudents(temp);
			mainTable.changeView();
		}
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	}

	private void setButtonsSize() {
		for (JButton btn : buttons) {
			btn.setPreferredSize(new Dimension(135, 35));
		}
	}

	private void initTestoviPanel() {

		testoviPanel = new JPanel();
		testoviPanel.setBounds(735, 401, 449, 200);
		testoviPanel.setBackground(new Color(0, 0, 139));
		contentPane.add(testoviPanel);
		testoviPanel.setLayout(null);

		testoviTable = new JTable();
		testoviTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_DELETE)
					new Thread(new Runnable() {

						@Override
						public void run() {

							mainFormController.deleteTestAction();
						}
					}).start();
			}
		});
		testoviTable.setFont(new Font("Century Gothic", Font.BOLD, 12));
		testoviTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testoviTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		testoviTable.setForeground(new Color(0, 0, 139));
		testoviTable.setBackground(new Color(173, 216, 230));

		new Thread(new Runnable() {

			@Override
			public void run() {
				mainFormController.initTestoviTable();
			}
		}).start();

		testoviTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (testoviTable.getSelectedRowCount() == 1) {
					btnIzmjeni.setEnabled(true);
					btnBrisi.setEnabled(true);
				} else {
					btnIzmjeni.setEnabled(false);
					btnBrisi.setEnabled(false);
				}
			}
		});
		testoviTable.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						mainFormController.initMouseHoverAction(e, testoviTable);						
					}
				}).start();
			}

		});
		testoviTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							mainFormController.editTestAction(testoviTable);							
						}
					}).start();
				}
			}


		});

		testoviScrollPane = new JScrollPane();
		testoviScrollPane.setBounds(10, 11, 429, 145);
		testoviScrollPane.setBackground(Color.WHITE);
		testoviScrollPane.setBorder(UIManager.getBorder("Button.border"));
		testoviPanel.add(testoviScrollPane);
		testoviScrollPane.setViewportView(testoviTable);
		/* Buttons by Stokuca */
		btnDodaj = new JButton("");
		btnDodaj.setIcon(new ImageIcon("img/Add_14.png"));
		btnDodaj.setBackground(new Color(0, 0, 139));
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFormController.addTestAction();
			}
		});
		btnDodaj.setBounds(10, 166, 89, 23);
		testoviPanel.add(btnDodaj);

		btnIzmjeni = new JButton("");
		btnIzmjeni.setIcon(new ImageIcon("img/Edit_14.png"));
		btnIzmjeni.setBackground(new Color(0, 0, 139));
		btnIzmjeni.setEnabled(false);
		btnIzmjeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFormController.editTestAction(testoviTable);
			}
		});
		btnIzmjeni.setBounds(109, 166, 89, 23);
		testoviPanel.add(btnIzmjeni);

		btnBrisi = new JButton("");
		btnBrisi.setIcon(new ImageIcon("img/Delete_14.png"));
		btnBrisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						mainFormController.deleteTestAction();
					}
				}).start();

			}
		});
		btnBrisi.setBackground(new Color(0, 0, 139));
		btnBrisi.setBounds(208, 166, 89, 23);
		btnBrisi.setEnabled(false);
		testoviPanel.add(btnBrisi);
	}

	private void initButtons() {

		searchButton = new JButton("");
		searchButton.setToolTipText("Pretrazi");

		searchButton.setBounds(370, 171, 42, 26);
		try {
			BufferedImage searchImg = ImageIO.read(new File("img" + File.separator + "searchIcon.png"));
			searchImg = Scalr.resize(searchImg, Scalr.Mode.FIT_EXACT, searchButton.getWidth(), searchButton.getHeight(),
					null);
			searchButton.setIcon(new ImageIcon(searchImg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		contentPane.add(searchButton);

		/* Buttons by Boroja */
		
				JButton konacnaOcjenaButton = new JButton("Ocjeni");
				konacnaOcjenaButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EventQueue.invokeLater(new Runnable() {

							@Override
							public void run() {
								new GradeGenerationForm(getSelectedPredmet(), mainTable.getStudents())
								.setVisible(true);
							}
						});

					}
				});
				konacnaOcjenaButton.setBounds(628, 131, 89, 23);
				buttons.add(konacnaOcjenaButton);
				buttonPanel.add(konacnaOcjenaButton);
		showViewBtn = new JButton("Prikaz");

		buttonPanel.add(showViewBtn);
		buttons.add(showViewBtn);

		sortBtn = new JButton("Sortiraj");
		buttonPanel.add(sortBtn);
		buttons.add(sortBtn);

		filterBtn = new JButton("Filtriraj");
		buttonPanel.add(filterBtn);
		buttons.add(filterBtn);

		addBtn = new JButton("Dodaj studente");
		addBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/* Stankovic */
				mainFormController.createAddForm();
			}
		});
		buttonPanel.add(addBtn);
		buttons.add(addBtn);

		deleteBtn = new JButton("Obri\u0161i studente");
		deleteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/* Stankovic */
				int[] selectedRows = mainTable.getSelectedRows();
				mainFormController.deleteStudentsControler(selectedRows);
			}
		});
		buttonPanel.add(deleteBtn);
		buttons.add(deleteBtn);

		changeBtn = new JButton("Izmijeni studenta");
		changeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/* Stankovic */
				int[] selectedRow = mainTable.getSelectedRows();
				mainFormController.createChangeForm(selectedRow);
			}
		});
		buttonPanel.add(changeBtn);
		buttons.add(changeBtn);

		exportBtn = new JButton("Eksportuj");
		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/* Stankovic */
				mainFormController.choseExportType();
			}
		});
		buttonPanel.add(exportBtn);
		buttons.add(exportBtn);

		accountBtn = new JButton("Nalog");
		buttonPanel.add(accountBtn);
		buttons.add(accountBtn);

		restoreButton = new JButton("");
		restoreButton.setToolTipText("Ucitaj podatke o svim studentima");
		restoreButton.setBounds(422, 171, 42, 26);
		try {
			BufferedImage restoreImg = ImageIO.read(new File("img" + File.separator + "restoreIcon.png"));
			restoreImg = Scalr.resize(restoreImg, Scalr.Mode.FIT_EXACT, restoreButton.getWidth(),
					restoreButton.getHeight(), null);
			restoreButton.setIcon(new ImageIcon(restoreImg));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		contentPane.add(restoreButton);

		undoButton = new JButton("<");
		undoButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		undoButton.setToolTipText("Undo");
		undoButton.setBounds(474, 171, 42, 26);
		contentPane.add(undoButton);

		redoButton = new JButton(">");
		redoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ArrayList<StudentMainTableDTO> students = UndoRedoData.redo();
				if (students != null)
					mainTable.setStudents(students);
			}
		});
		redoButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		redoButton.setToolTipText("Redo");
		redoButton.setBounds(526, 171, 42, 26);
		contentPane.add(redoButton);

		/* Buttons by Mijic */

		prikaziDisciplinuBtn = new JButton("Prikazi disciplinu");
		prikaziDisciplinuBtn.setBounds(1040, 355, 135, 35);
		contentPane.add(prikaziDisciplinuBtn);
		prikaziDisciplinuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFormController.prikaziDisciplinu(nalogDTO, disciplineCB);
			}
		});

	}

	private void initPredmetiComboBox() {
		predmetiCB = new JComboBox<>();
		predmetiCB.setBounds(745, 172, 430, 35);
		contentPane.add(predmetiCB);

		MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
		NalogDAO nalogDAO = nalogFactory.getNalogDAO();

		predmetiList = nalogDAO.getPredmeteNaNalogu(nalogDTO.getNalogId());

		for (int i = 0; i < predmetiList.size(); i++) {
			predmetiCB.addItem(
					(predmetiList.get(i)).getSifraPredmeta() + " - " + (predmetiList.get(i)).getNazivPredmeta());
		}
	}

	private void initElektrijadaComboBox() {
		elektrijadaCB = new JComboBox<>();
		elektrijadaCB.setBounds(745, 252, 430, 35);
		initDisciplineComboBox();
		MySQLDAOFactory dao = new MySQLDAOFactory();
		ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
		ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO
				.getListuElektrijada(nalogDTO.getNalogId()); // nalogDTO
		// umjesto
		// 2
		if (elektrijade.size() > 0){
			for (ElektrijadaDTO el : elektrijade) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat newDf = new SimpleDateFormat("dd.MM.yyyy");
				java.util.Date datum = null;
				try {
					datum = df.parse(el.getDatum().toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				elektrijadaCB.addItem(el.getLokacija() + ", " + newDf.format(datum));
			}
			
			int indeks = elektrijadaCB.getSelectedIndex();
			ElektrijadaDTO selektovanaElektrijada = elektrijade.get(indeks);
			DisciplinaDAO discDAO = dao.getDisciplinaDAO();
			ArrayList<DisciplinaDTO> discipline = (ArrayList<DisciplinaDTO>) discDAO
					.getDiscipline(selektovanaElektrijada.getId(), nalogDTO.getNalogId());// nalogDTO
			// umjesto 2
			for (DisciplinaDTO di : discipline) {
				disciplineCB.addItem(di.getNaziv());
			}

			elektrijadaCB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (disciplineCB.getItemCount() > 0)
						disciplineCB.removeAllItems();
					int indeks = elektrijadaCB.getSelectedIndex();
					if (indeks != -1) {
						ElektrijadaDTO selektovanaElektrijada = elektrijade.get(indeks);
						DisciplinaDAO discDAO = dao.getDisciplinaDAO();
						ArrayList<DisciplinaDTO> discipline = (ArrayList<DisciplinaDTO>) discDAO
								.getDiscipline(selektovanaElektrijada.getId(), nalogDTO.getNalogId());// nalogDTO
						// umjesto
						// 2
						for (DisciplinaDTO di : discipline) {
							disciplineCB.addItem(di.getNaziv());
						}
					}
				}
			});
		}
		contentPane.add(elektrijadaCB);
	}

	private void initDisciplineComboBox() {
		disciplineCB = new JComboBox<>();
		disciplineCB.setBounds(745, 315, 430, 35);
		contentPane.add(disciplineCB);
	}

	private void initComboBoxListener() {
		predmetiCB.addActionListener(actionListener);
	}

	public void resetPredmetiComboBox() {
		predmetiCB.removeActionListener(actionListener);
		predmetiCB.removeAllItems();
		ArrayList<PredmetDTO> predmetiList = new ArrayList<>();
		MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
		NalogDAO nalogDAO = nalogFactory.getNalogDAO();

		predmetiList = nalogDAO.getPredmeteNaNalogu(nalogDTO.getNalogId());

		for (int i = 0; i < predmetiList.size(); i++) {
			predmetiCB.addItem(
					(predmetiList.get(i)).getSifraPredmeta() + " - " + (predmetiList.get(i)).getNazivPredmeta());
		}
		initComboBoxListener();
	}

	public void resetElektrijadaComboBox() {
//		MySQLDAOFactory dao = new MySQLDAOFactory();
//		ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
//		ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO
//				.getListuElektrijada(nalogDTO.getNalogId()); 
//		if (elektrijadaCB.getItemCount() > 0)
//			elektrijadaCB.removeAllItems();
//		if (elektrijade.size() > 0 ){
//			int indeks = elektrijadaCB.getSelectedIndex();
//			if (disciplineCB.getItemCount() > 0)
//				disciplineCB.removeAllItems();
//			if (indeks != -1) {			
//				ElektrijadaDTO selektovanaElektrijada = elektrijade.get(indeks);
//				DisciplinaDAO discDAO = dao.getDisciplinaDAO();
//				ArrayList<DisciplinaDTO> discipline = (ArrayList<DisciplinaDTO>) discDAO
//						.getDiscipline(selektovanaElektrijada.getId(), nalogDTO.getNalogId());// nalogDTO
//				// umjesto 2
//				for (DisciplinaDTO di : discipline) {
//					disciplineCB.addItem(di.getNaziv());
//				}
//			}
//		}
//		elektrijadaCB.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (disciplineCB.getItemCount() > 0)
//					disciplineCB.removeAllItems();
//				int indeks = elektrijadaCB.getSelectedIndex();
//				if (indeks != -1) {
//					ElektrijadaDTO selektovanaElektrijada = elektrijade.get(indeks);
//					DisciplinaDAO discDAO = dao.getDisciplinaDAO();
//					ArrayList<DisciplinaDTO> discipline = (ArrayList<DisciplinaDTO>) discDAO
//							.getDiscipline(selektovanaElektrijada.getId(), nalogDTO.getNalogId());// nalogDTO
//					// umjesto
//					// 2
//					for (DisciplinaDTO di : discipline) {
//						disciplineCB.addItem(di.getNaziv());
//					}
//				}
//			}
//		});
		if (elektrijadaCB.getItemCount() > 0 ) elektrijadaCB.removeAllItems();
		if (disciplineCB.getItemCount() > 0 ) disciplineCB.removeAllItems();
		MySQLDAOFactory dao = new MySQLDAOFactory();
		ElektrijadaDAO eleDAO = dao.getElektrijadaDAO();
		ArrayList<ElektrijadaDTO> elektrijade = (ArrayList<ElektrijadaDTO>) eleDAO
				.getListuElektrijada(nalogDTO.getNalogId()); // nalogDTO
		// umjesto
		// 2
		if (elektrijade.size() > 0){
			for (ElektrijadaDTO el : elektrijade) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat newDf = new SimpleDateFormat("dd.MM.yyyy");
				java.util.Date datum = null;
				try {
					datum = df.parse(el.getDatum().toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				elektrijadaCB.addItem(el.getLokacija() + ", " + newDf.format(datum));
			}
			
			int indeks = elektrijadaCB.getSelectedIndex();
			ElektrijadaDTO selektovanaElektrijada = elektrijade.get(indeks);
			DisciplinaDAO discDAO = dao.getDisciplinaDAO();
			ArrayList<DisciplinaDTO> discipline = (ArrayList<DisciplinaDTO>) discDAO
					.getDiscipline(selektovanaElektrijada.getId(), nalogDTO.getNalogId());// nalogDTO
			// umjesto 2
			for (DisciplinaDTO di : discipline) {
				disciplineCB.addItem(di.getNaziv());
			}

			elektrijadaCB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (disciplineCB.getItemCount() > 0)
						disciplineCB.removeAllItems();
					int indeks = elektrijadaCB.getSelectedIndex();
					if (indeks != -1) {
						ElektrijadaDTO selektovanaElektrijada = elektrijade.get(indeks);
						DisciplinaDAO discDAO = dao.getDisciplinaDAO();
						ArrayList<DisciplinaDTO> discipline = (ArrayList<DisciplinaDTO>) discDAO
								.getDiscipline(selektovanaElektrijada.getId(), nalogDTO.getNalogId());// nalogDTO
						// umjesto
						// 2
						for (DisciplinaDTO di : discipline) {
							disciplineCB.addItem(di.getNaziv());
						}
					}
				}
			});
		}
	}

	public void refreshTestoviTable() {
		PredmetDTO activePredmet = getSelectedPredmet();
		if (activePredmet != null) {
			TestoviTableModel model = (TestoviTableModel) testoviTable.getModel();
			DAOFactory factory = new MySQLDAOFactory();
			TestDAO testDAO = factory.getTestDAO();

			List<TestDTO> data = testDAO.getAllTests(activePredmet.getPredmetId());
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					model.setData(data);
				}
			});
		}
	}

	public JTable getTestoviTable() {
		return testoviTable;
	}

	public MainTable getMainTable() {
		return mainTable;
	}

	public MainFormController getMainFormController() {
		return mainFormController;
	}

	public double getTableWidth() {
		return scrollPane.getSize().getWidth();
	}

	public NalogDTO getNalogDTO() {
		return nalogDTO;
	}

	public void setNalogDTO(NalogDTO nalogDTO) {
		this.nalogDTO = nalogDTO;
	}

	public PredmetDTO getSelectedPredmet() {
		int i = predmetiCB.getSelectedIndex();
		return (i == -1) ? null : predmetiList.get(i);
	}

	public void testoviClearSelection() {
		testoviTable.clearSelection();
		btnBrisi.setEnabled(false);
		btnIzmjeni.setEnabled(false);
	}

}
