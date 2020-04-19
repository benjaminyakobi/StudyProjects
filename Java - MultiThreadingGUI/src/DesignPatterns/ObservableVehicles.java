package DesignPatterns;

public interface ObservableVehicles {
	
	public void registerObserver(Observer ob);
	public void unregisterObserver(Observer ob);
	public void notifyObservers(String msg);
}
