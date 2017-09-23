package org.unibl.etf.ps.studentviewer;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.unibl.etf.ps.studentviewer.gui.view.nalog.LoginForm;

public final class StudentViewer {


	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", "StudentViewer.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "studentviewer");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception ex) {}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm frame = new LoginForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
