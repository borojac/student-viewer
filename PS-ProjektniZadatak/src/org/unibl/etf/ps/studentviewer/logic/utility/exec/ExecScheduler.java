package org.unibl.etf.ps.studentviewer.logic.utility.exec;

import java.util.PriorityQueue;

public class ExecScheduler extends Thread {
	PriorityQueue<Exec> execs = new PriorityQueue<Exec>();
	Object lock = new Object();
	boolean running;
	
	public ExecScheduler() {
		start();
	}
	
	
	public void signal() {
		/*
		 * Signalizacija za oslobadjanje sledeceg exec-a u izvrsavanje
		 */
	}

	public boolean add(Exec e) {
		/*
		 * TO-DO mozda moze odmah da se izvrsi
		 */
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
