package net.iubris.archimedes.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface EGDAO<T> {
	

	/*
	 * DAO interface for accessing the DATA(generic bean) datatype
	 * 
	 * just simple CRUD methods: create, read, update, delete
	 */
	
	/*
	 * C
	 */
	public boolean create(T data);

	/*
	 * R
	 */
	/*
	public abstract T read(String field, String value);
	public abstract T read(int id);
	public abstract T read(String id);
	public abstract Collection<T> readMany(String field, String value);		
	*/
	//public Collection<T> read(String fieldNameAsFilter, String value);
	public Collection<T> read(T data);
	public Collection<T> read(Map<String,String> filters);

	public Collection<T> read(String fieldNameAsFilter, Object value);
	public Collection<T> read(HashMap<String,Object> filters);
	
	public Collection<T> readAll();
	
	/*
	 * U
	 */
	public boolean update(T data);

	/*
	 * D
	 */
	boolean delete(T data);
	public boolean delete(T data, String fieldNameAsFilter);
	public boolean delete(String fieldNameAsFilter, Object someValue);

	
	public Collection<T> getAll();


}
