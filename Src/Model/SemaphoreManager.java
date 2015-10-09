package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SemaphoreManager extends Observable implements Observer{
	private ArrayList<Semaphore> semaphores;
	
	public SemaphoreManager() {
		semaphores = new ArrayList<Semaphore>();
	}
	
	@Override
	public void update(Observable o, Object arg) { //Some semaphore has changed
		setChanged();
		notifyObservers(o); //Notify passing the semaphore that has changed
	}

	public Semaphore addSemaphore(SemaphoreState state, int width, int height, Point position) {
		Semaphore semaphore = new Semaphore(state,width,height,position);
		semaphore.addObserver(this);
		semaphores.add(semaphore);
		return semaphore;
	}
	
	public Semaphore addSemaphore(SemaphoreState state, int width, int height, int x, int y) {
		return addSemaphore(state,width,height,new Point(x,y));
	}
	
	public Semaphore addSemaphore(SemaphoreState state, Point position) {
		Semaphore semaphore = new Semaphore(state,position);
		semaphore.addObserver(this);
		semaphores.add(semaphore);
		return semaphore;
	}
	
	public Semaphore addSemaphore(SemaphoreState state, int x, int y) {
		return addSemaphore(state,new Point(x,y));
	}

	public void addSemaphore(Semaphore semaphore) {
		semaphores.add(semaphore);
	}

	public boolean removeSemaphore(Semaphore semaphore) {
		boolean removed = semaphores.remove(semaphore);
		if (removed) {
			semaphore.deleteObserver(this);
		}
		return removed;
	}

	public boolean removeSemaphore(int index) {
		Semaphore sem = semaphores.remove(index);
		if (sem != null) {
			sem.deleteObserver(this);
		}
		return (sem != null);
	}

	public void clearSemaphores() {
		for (int i = 0; i < getSemaphoreCount(); i++) {
			removeSemaphore(getSemaphore(0));
		}
	}

	public int getSemaphoreIndex(Semaphore semaphore) {
		return semaphores.indexOf(semaphore);
	}

	public int getSemaphoreCount() {
		return semaphores.size();
	}

	public Semaphore getSemaphore(int index) {
		return semaphores.get(index);
	}
}
