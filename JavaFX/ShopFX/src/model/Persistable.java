package model;

public interface Persistable<T> {
	public void add(T object);
	public T search(Integer id);
	public boolean exists(Integer id);
	public boolean delete(Integer id);	
}
