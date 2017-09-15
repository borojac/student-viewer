package org.unibl.etf.ps.studentviewer.logic.utility.exec;

import java.util.PriorityQueue;

public class ExecScheduler extends Thread {
	private PriorityQueue<Exec> execs = new PriorityQueue<Exec>();
	private Object lock = new Object();
	
	public ExecScheduler() {
		start();
	}
	
	public boolean add(Exec e) {
		execs.add(e);
		synchronized (lock) {
			lock.notify();
		}
		return true;
	}

	public void run() {
		while (true) {
			synchronized (lock) {
				while (execs.size() == 0)
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				execs.poll().execute();
			}
		}
	}

}
