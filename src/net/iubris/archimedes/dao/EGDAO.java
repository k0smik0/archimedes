/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * EGDAO.java is part of 'archimedes'.
 * 
 * 'archimedes' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'archimedes' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'archimedes'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
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
