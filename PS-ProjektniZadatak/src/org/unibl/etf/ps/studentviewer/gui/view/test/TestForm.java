package org.unibl.etf.ps.studentviewer.gui.view.test;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.logic.controller.test.TestController;
import org.unibl.etf.ps.studentviewer.logic.utility.command.Command;
import org.unibl.etf.ps.studentviewer.logic.utility.command.DodajNapomenuTestCommand;
import org.unibl.etf.ps.studentviewer.logic.utility.command.DodajStudenteTestCommand;
import org.unibl.etf.ps.studentviewer.logic.utility.command.IzmjenaDatumaTestCommand;
import org.unibl.etf.ps.studentviewer.logic.utility.command.IzmjenaNazivaTestaCommand;
import org.unibl.etf.ps.studentviewer.logic.utility.command.IzmjenaProcentaTestCommand;
import org.unibl.etf.ps.studentviewer.logic.utility.command.UkloniStudenteTestCommand;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

import com.itextpdf.text.DocumentException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import datechooser.events.CommitEvent;
import datechooser.events.CommitListener;
import datechooser.model.multiple.MultyModelBehavior;
import datechooser.beans.DateChooserCombo;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.ScrollPaneConstants;

public class TestForm extends JFrame {

	private static final long serialVersionUID = -8038774276444482778L;
	
	private JPanel contentPane;
	private JTextField nazivTextField;
	private JTextArea napomenaTextArea;
	private JTable studentiTable;
	private JScrollPane studentiScrollPane;
	private JButton btnSacuvaj;
	private JTextField searchTextField;
	private JButton btnPrint;
	private JButton btnPretrazi;
	private JButton btnEksport;
	private JButton btnUkloni;
	private JButton btnDodaj;

	private TestDTO test;
	private TestController testController;
	private DateChooserCombo dateChooserCombo;
	private boolean update = false;

	private TestForm testForm;
	private MainForm parentForm;
	private JButton btnImport;

	private Logger logger = Logger.getLogger(TestForm.class);
	private JTextArea statistikaTextArea;
	private JComboBox procenatComboBox;
	private JScrollPane napomenaScrollPane;


	public TestForm(TestDTO testParam, MainForm mainForm) {
		setType(Type.UTILITY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		if (testParam != null) {
			test = new TestDTO(testParam.getTestId(), testParam.getNaziv(), testParam.getDatum(),
					testParam.getNapomena(), testParam.getProcenat(), testParam.getPredmetId());
			test.setStudenti(testParam.getStudenti());
			update = true;
		} else {
			PredmetDTO activePredmet = mainForm.getSelectedPredmet();
			test = new TestDTO();
			if (activePredmet != null)
				test.setPredmetId(activePredmet.getPredmetId());
		}
		parentForm = mainForm;
		
		init();
	}
	
	private void init() {
		testForm = this;
		
		testForm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						testController.windowClosingAction();
					}
				});
			}
		});
		testForm.setResizable(false);
		testForm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});
		testForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		testForm.setBounds(200, 10, 540, 686);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});

		testForm.setContentPane(contentPane);
		contentPane.setLayout(null);

		

		testController = new TestController(test, this);

		initLogger();
		initMainArea();
		initBottomButtons();
		initProcenatComboBox();
		

		if (update)
			setFields();
	}

	private void initLogger() {

		try {
			File logFolder = new File("./log");
			if (!logFolder.exists())
				logFolder.mkdirs();
			logger.addAppender(new FileAppender(new SimpleLayout(), "./log/" + TestForm.class.getSimpleName() + ".log"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void initMainArea() {
		JLabel lblNaziv = new JLabel("Naziv:");
		lblNaziv.setForeground(Color.WHITE);
		lblNaziv.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNaziv.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNaziv.setBounds(41, 11, 70, 20);
		contentPane.add(lblNaziv);

		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setForeground(Color.WHITE);
		lblDatum.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDatum.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDatum.setBounds(41, 55, 70, 20);
		contentPane.add(lblDatum);

		JLabel lblNapomena = new JLabel("Napomena:");
		lblNapomena.setForeground(Color.WHITE);
		lblNapomena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNapomena.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNapomena.setBounds(41, 104, 70, 20);
		contentPane.add(lblNapomena);
		
		nazivTextField = new JTextField();
		nazivTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_Y))
					testController.undoRedoAction((TestForm) testForm, e);
				
			}
			@Override
			public void keyTyped(KeyEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String text = nazivTextField.getText();
						try {
							TimeUnit.MILLISECONDS.sleep(250);
						} catch (InterruptedException e) {}
						
						if (nazivTextField.getText().equals(text) 
								&& !nazivTextField.getText().equals(test.getNaziv())) {
							
							IzmjenaNazivaTestaCommand command = new IzmjenaNazivaTestaCommand(test, nazivTextField);
							testController.executeCommand(command);
							
						}
					}
				}).start();
			}
		});
		nazivTextField.setBounds(121, 12, 280, 20);
		contentPane.add(nazivTextField);
		nazivTextField.setColumns(10);
		
		studentiScrollPane = new JScrollPane();
		studentiScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		studentiScrollPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.isControlDown() && 
						(e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_Y))
					testController.undoRedoAction((TestForm) testForm, e);
			}
		});
		studentiScrollPane.setBackground(new Color(0, 0, 139));
		studentiScrollPane.setBounds(10, 420, 514, 196);
		studentiScrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				studentiTable.clearSelection();
				btnUkloni.setEnabled(false);
				refreshStatistics();
			}
		});
		contentPane.add(studentiScrollPane);


		StudentTableModel tableModel = new StudentTableModel(test.getStudenti());
		tableModel.setTestController(testController);


		studentiTable = new JTable(tableModel);
		studentiTable.setForeground(new Color(0, 0, 139));
		studentiTable.setBackground(new Color(173, 216, 230));
		studentiTable.setFont(new Font("Century Gothic", Font.BOLD, 11));
		studentiTable.setFillsViewportHeight(true);
		studentiTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (studentiTable.getSelectedRowCount() > 0)
					btnUkloni.setEnabled(true);
				else
					btnUkloni.setEnabled(false);
			}
		});
		studentiTable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				refreshStatistics();
			}
		});

		studentiTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_Y))
					testController.undoRedoAction((TestForm) testForm, e);
				else if (!e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DELETE) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							if (studentiTable.getSelectedRowCount() > 0) {
								List<StudentNaTestuDTO> forDelete = new ArrayList<>();
								StudentTableModel model = (StudentTableModel) studentiTable.getModel();
								for (int index : studentiTable.getSelectedRows())
									forDelete.add(model.getRowAt(index));
								testController.removeStudents(model, forDelete);
							}
						}
					}).start();
				}
			}
		});

		studentiScrollPane.setViewportView(studentiTable);
		
		searchTextField = new JTextField();
		searchTextField.setForeground(new Color(0, 0, 139));
		searchTextField.setBackground(new Color(173, 216, 230));
		searchTextField.setFont(new Font("Century Gothic", Font.BOLD, 11));
		searchTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String searchText = searchTextField.getText();
				testController.initiateStudentSearch(
						(StudentTableModel)studentiTable.getModel(),
						searchText);
			}
		});
		searchTextField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		searchTextField.setBounds(90, 389, 311, 23);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);

		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.isControlDown() && 
						(e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_Y))
					testController.undoRedoAction((TestForm) testForm, e);
				else if (!e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					EventQueue.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							searchTextField.setText("");
						}
					});
					
					((StudentTableModel) studentiTable.getModel()).setData(test.getStudenti());
				}
			}
		});

		btnPretrazi = new JButton("");
		btnPretrazi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});
		btnPretrazi.setIcon(new ImageIcon("img/Search_14.png"));
		btnPretrazi.setBackground(new Color(0, 0, 139));
		btnPretrazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String searchText = searchTextField.getText();
				testController.initiateStudentSearch(
						(StudentTableModel)studentiTable.getModel(),
						searchText);
			}
		});
		btnPretrazi.setBounds(411, 389, 113, 23);
		contentPane.add(btnPretrazi);

		dateChooserCombo = new DateChooserCombo();
		dateChooserCombo.setBehavior(MultyModelBehavior.SELECT_SINGLE);
		dateChooserCombo.setBounds(121, 55, 280, 20);
		dateChooserCombo.setCalendarBackground(Color.WHITE);
		dateChooserCombo.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));

		dateChooserCombo.addCommitListener(new CommitListener() {

			@Override
			public void onCommit(CommitEvent arg0) {
				if (dateChooserCombo.getSelectedDate().getTime().getTime() != test.getDatum().getTime()) {
					Command command = new IzmjenaDatumaTestCommand(test, dateChooserCombo);
					testController.executeCommand(command);
				}

			}
		});

		dateChooserCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction((TestForm) testForm, e);
			}
		});



		contentPane.add(dateChooserCombo);

		

		JLabel lblStatistika = new JLabel("Statistika:");
		lblStatistika.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatistika.setForeground(Color.WHITE);
		lblStatistika.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStatistika.setBounds(41, 233, 70, 20);
		contentPane.add(lblStatistika);

		statistikaTextArea = new JTextArea();
		statistikaTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction(testForm, e);
			}
		});
		statistikaTextArea.setEditable(false);
		statistikaTextArea.setBounds(121, 231, 280, 147);
		statistikaTextArea.setText(testController.getTestStatistics());
		contentPane.add(statistikaTextArea);

		JLabel lblProcenat = new JLabel("Procenat:");
		lblProcenat.setVerticalAlignment(SwingConstants.BOTTOM);
		lblProcenat.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblProcenat.setForeground(Color.WHITE);
		lblProcenat.setBounds(410, 11, 55, 20);
		contentPane.add(lblProcenat);

		

	}

	private void initBottomButtons() {
		btnSacuvaj = new JButton("");
		btnSacuvaj.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction(testForm, e);
			}
		});
		btnSacuvaj.setIcon(new ImageIcon("img/Save_14.png"));
		btnSacuvaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (update)
					testController.updateTestAction();
				else
					testController.addTestAction();
				parentForm.refreshTestoviTable();
			}
		});
		btnSacuvaj.setBackground(new Color(0, 0, 139));
		btnSacuvaj.setBounds(454, 627, 70, 23);
		contentPane.add(btnSacuvaj);

		
		btnPrint = new JButton("");
		btnPrint.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction(testForm, e);
			}
		});
		btnPrint.setIcon(new ImageIcon("img/Print_14.png"));
		btnPrint.setBackground(new Color(0, 0, 139));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							testController.print();
						} catch (Exception e) {
							EventQueue.invokeLater(new Runnable() {

								@Override
								public void run() {
									JOptionPane.showMessageDialog(testForm,
											"Štampanje nije uspjelo. Pogledajte log za detalje:\n" + new File("log" + "/" + TestForm.class.getSimpleName() + ".log").getAbsolutePath(), 
											"Greška", 
											JOptionPane.ERROR_MESSAGE);
								}
							});

							logger.error("Štampanje nije uspjelo", e);

						}
					}
				}).start();

			}
		});
		btnPrint.setBounds(10, 627, 70, 23);
		contentPane.add(btnPrint);

		btnEksport = new JButton("");
		btnEksport.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction(testForm, e);
			}
		});
		btnEksport.setIcon(new ImageIcon("img/PDF_14.png"));
		btnEksport.setBackground(new Color(0, 0, 139));
		btnEksport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							testController.export();
						} catch (Exception e) {
							EventQueue.invokeLater(new Runnable() {

								@Override
								public void run() {
									JOptionPane.showMessageDialog(testForm,
											"Eksportovanje nije uspjelo. Pogledajte log za detalje:\n" + new File("log" + "/" + TestForm.class.getSimpleName() + ".log").getAbsolutePath(), 
											"Greška", 
											JOptionPane.ERROR_MESSAGE);
								}
							});

							logger.error("Eksportovanje nije uspjelo", e);
						}
					}
				}).start();
			}
		});
		btnEksport.setBounds(90, 627, 70, 23);
		contentPane.add(btnEksport);

		btnDodaj = new JButton("");
		btnDodaj.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction(testForm, e);
			}
		});
		btnDodaj.setIcon(new ImageIcon("img/Add_14.png"));
		btnDodaj.setBackground(new Color(0, 0, 139));
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				testController.addStudents();
			}
		});
		btnDodaj.setBounds(170, 627, 70, 23);
		contentPane.add(btnDodaj);

		btnUkloni = new JButton("");
		btnUkloni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction(testForm, e);
			}
		});
		btnUkloni.setIcon(new ImageIcon("img/Delete_14.png"));
		btnUkloni.setBackground(new Color(0, 0, 139));
		btnUkloni.setEnabled(false);
		btnUkloni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						if (studentiTable.getSelectedRowCount() > 0) {
							List<StudentNaTestuDTO> forRemoving = new ArrayList<>();
							StudentTableModel model = (StudentTableModel) studentiTable.getModel();
							for (int index : studentiTable.getSelectedRows()) {
								forRemoving.add(model.getRowAt(index));
							}
							testController.removeStudents(model, forRemoving);
						}
					}
				}).start();
			}
		});
		btnUkloni.setBounds(330, 627, 70, 23);
		contentPane.add(btnUkloni);
		
		btnImport = new JButton("");
		btnImport.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				testController.undoRedoAction(testForm, e);
			}
		});
		btnImport.setIcon(new ImageIcon("img/Import_14.png"));
		btnImport.setBackground(new Color(0, 0, 139));
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					List<StudentNaTestuDTO> data = testController.importFromExcel();
					if (data != null) {
						testController.executeCommand(
								new DodajStudenteTestCommand(
										test, 
										(StudentTableModel) studentiTable.getModel(), 
										data));
						refreshStatistics();
						refreshStudentiTable();
						searchTextField.setText("");
					}

				} catch (FileNotFoundException e) {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(testForm,
									"Fajl ne postoji na disku.",
									"Greška", 
									JOptionPane.ERROR_MESSAGE);
						}
					});
					logger.error("Greška kod importa testa iz excela.", e);
				} catch (IOException e) {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							JOptionPane.showMessageDialog(testForm,
									"Import iz datog fajla nije uspio. Pokušajte ponovo. Pogledajte log za detalje:\n" + new File("./log/" + TestForm.class.getSimpleName() + ".log").getAbsolutePath(),
									"Greška", 
									JOptionPane.ERROR_MESSAGE);
						}
					});
					logger.error("Greška kod importa testa iz excela.", e);
				}
			}
		});
		btnImport.setBounds(250, 627, 70, 23);
		contentPane.add(btnImport);
	}
	
	public JTable getStudentiTable() {
		return studentiTable;
	}

	public void refreshStatistics() {
		final String statistics = testController.getTestStatistics();
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				statistikaTextArea.setText(statistics);
			}
		});
	}

	public void resetStudentiTable() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				((StudentTableModel) studentiTable.getModel()).setData(test.getStudenti());
			}
		});
	}

	public void refreshStudentiTable() {
		testController.initiateStudentSearch((StudentTableModel) studentiTable.getModel(),
				searchTextField.getText());
		List<StudentNaTestuDTO> data = ((StudentTableModel) studentiTable.getModel()).getData();
		((StudentTableModel) studentiTable.getModel()).setData(new ArrayList<>(data));
	}

	private void setFields() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				procenatComboBox.setSelectedItem("" + test.getProcenat());
				nazivTextField.setText(test.getNaziv());
				Calendar cal = Calendar.getInstance();
				cal.setTime(test.getDatum());
				dateChooserCombo.setSelectedDate(cal);
				napomenaTextArea.setText(test.getNapomena());
				StudentTableModel model = (StudentTableModel) studentiTable.getModel();
				if (model == null) 
					model = new StudentTableModel(test.getStudenti());
				else
					model.setData(test.getStudenti());
			}
		});


	}

	private void initProcenatComboBox() {
		
		procenatComboBox = new JComboBox();
		procenatComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				if (event.isControlDown() && (event.getKeyCode() == KeyEvent.VK_Z || event.getKeyCode() == KeyEvent.VK_Y))
					testController.undoRedoAction(testForm, event);
			}
		});
		
		int tmpProcenat = 0;
		while (tmpProcenat <= 100) {
			procenatComboBox.addItem("" + tmpProcenat);
			tmpProcenat += 5;
		}

		procenatComboBox.setSelectedItem("" + 50);
		
		procenatComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (Integer.parseInt((String) procenatComboBox.getSelectedItem())
						!= test.getProcenat()) {

					IzmjenaProcentaTestCommand command = 
							new IzmjenaProcentaTestCommand(test, procenatComboBox);
					testController.executeCommand(command);
				}
			}
		});
		procenatComboBox.setBounds(475, 12, 49, 20);
		contentPane.add(procenatComboBox);
				
				napomenaScrollPane = new JScrollPane();
				napomenaScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				napomenaScrollPane.setBounds(121, 103, 280, 98);
				contentPane.add(napomenaScrollPane);
		
				napomenaTextArea = new JTextArea();
				napomenaTextArea.setColumns(10);
				napomenaTextArea.setLineWrap(true);
				napomenaTextArea.setWrapStyleWord(true);
				napomenaScrollPane.setViewportView(napomenaTextArea);
				napomenaTextArea.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_Y))
							testController.undoRedoAction((TestForm) testForm, e);
					}
					@Override
					public void keyTyped(KeyEvent e) {
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								String text = napomenaTextArea.getText();
								try {
									TimeUnit.MILLISECONDS.sleep(500);
								} catch (InterruptedException e) {}
								if (napomenaTextArea.getText().equals(text) 
										&& !napomenaTextArea.getText().equals(test.getNapomena())) {
									
									Command command = new DodajNapomenuTestCommand(test, napomenaTextArea);
									testController.executeCommand(command);
									
								}
							}
						}).start();
						
					}
				});
				
				JLabel lblPretraga = new JLabel("Pretraga:");
				lblPretraga.setHorizontalAlignment(SwingConstants.RIGHT);
				lblPretraga.setForeground(Color.WHITE);
				lblPretraga.setFont(new Font("Tahoma", Font.PLAIN, 13));
				lblPretraga.setBounds(10, 389, 70, 20);
				contentPane.add(lblPretraga);
	}
	
	public MainForm getMainForm() {
		return parentForm;
	}
	
	public List<StudentNaTestuDTO> getStudentsOnTest() {
		return test.getStudenti();
	}
}
