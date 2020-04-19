package DesignPatterns;

import java.util.ArrayList;

/**
 * Memento Design pattern Class
 * 
 * @author benja
 *
 */
public class MementoCareTaker {

	private ArrayList<Memento> mementoList = new ArrayList<Memento>();

	public void add(Memento state) {
		this.mementoList.add(state);
	}

	public Memento get(int index) {
		return this.mementoList.get(index);
	}

	public ArrayList<Memento> getMementoList() {
		return this.mementoList;
	}
}
