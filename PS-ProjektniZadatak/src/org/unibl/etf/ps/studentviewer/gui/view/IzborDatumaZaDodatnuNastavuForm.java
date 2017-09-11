package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.calendar.SingleDaySelectionModel;
import org.unibl.etf.ps.studentviewer.logic.controller.IzborDatumaZaDodatnuNastavuController;

public class IzborDatumaZaDodatnuNastavuForm extends JXDatePicker {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3387289067356056652L;
	private JSpinner timeSpinner;
	private JPanel timePanel;
	private DateFormat timeFormat = new SimpleDateFormat("M/d/yy h:mm a");
	private JFormattedTextField tf;
	private JTextField textFieldDatum;
	private DodavanjeDodatneNastaveForm nastavaForm;
	private IzborDatumaZaDodatnuNastavuController dateController;
	private IzborDatumaZaDodatnuNastavuForm izborDatumaForma;

	public IzborDatumaZaDodatnuNastavuForm(JTextField textFieldDatum, DodavanjeDodatneNastaveForm nastavaForm) {
		super();
		izborDatumaForma = this;
		this.textFieldDatum = textFieldDatum;
		this.nastavaForm = nastavaForm;
		dateController = new IzborDatumaZaDodatnuNastavuController(izborDatumaForma);
		getMonthView().setSelectionModel(new SingleDaySelectionModel());
		Date date = new Date();
		JFrame frame = new JFrame();
		frame.setTitle("Date Time Picker");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dateController.zatvoriProzor(textFieldDatum, nastavaForm, frame);

			}
		});
		this.setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		this.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));

		this.setDate(date);

		frame.getContentPane().add(this);
		frame.pack();

		frame.setLocationRelativeTo(nastavaForm);
		frame.setVisible(true);

	}

	public void commitEdit() throws ParseException {
		commitTime();
		super.commitEdit();
	}

	public void cancelEdit() {
		super.cancelEdit();
		setTimeSpinners();
	}

	@Override
	public JPanel getLinkPanel() {
		super.getLinkPanel();
		if (timePanel == null) {
			timePanel = createTimePanel();
		}
		setTimeSpinners();
		return timePanel;
	}

	private JPanel createTimePanel() {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new FlowLayout());
		// newPanel.add(panelOriginal);

		SpinnerDateModel dateModel = new SpinnerDateModel();
		timeSpinner = new JSpinner(dateModel);
		if (timeFormat == null)
			timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
		updateTextFieldFormat();
		newPanel.add(new JLabel("Time:"));
		newPanel.add(timeSpinner);
		newPanel.setBackground(Color.WHITE);
		return newPanel;
	}

	private void updateTextFieldFormat() {
		if (timeSpinner == null)
			return;
		tf = ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField();
		DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
		DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();
		// Change the date format to only show the hours
		formatter.setFormat(timeFormat);
	}

	private void commitTime() {
		Date date = getDate();
		if (date != null) {
			Date time = (Date) timeSpinner.getValue();
			GregorianCalendar timeCalendar = new GregorianCalendar();
			timeCalendar.setTime(time);

			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			Date newDate = calendar.getTime();
			setDate(newDate);
		}

	}

	private void setTimeSpinners() {
		Date date = getDate();
		if (date != null) {
			timeSpinner.setValue(date);
		}
	}

	public DateFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(DateFormat timeFormat) {
		this.timeFormat = timeFormat;
		updateTextFieldFormat();
	}
}