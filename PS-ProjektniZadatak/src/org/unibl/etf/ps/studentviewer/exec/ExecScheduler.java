package org.unibl.etf.ps.studentviewer.exec;

import java.util.PriorityQueue;

public class ExecScheduler {
	PriorityQueue<Exec> execs = null;
	Object lock = new Object();
	boolean running;
	
	public void signal() {
		/* Signalizacija za oslobadjanje sledeceg
		 * exec-a
		 *  u izvrsavanje */
	}
	

	public boolean add(Exec e) {
		/* TO-DO
		 * mozda moze odmah da se izvrsi
		 */
		synchronized(lock) {
			while(running == true)
				try {
					lock.wait();
				} catch (InterruptedException e1) {
					return false;
				}
			execs.add(e);
		}
		return true;
	}
	
}
