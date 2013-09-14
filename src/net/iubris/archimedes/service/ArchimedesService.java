/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * ArchimedesService.java is part of 'archimedes'.
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
package net.iubris.archimedes.service;


import net.iubris.archimedes.dao.EGDAO;
import net.iubris.archimedes.dao.support.hsql.HsqlDAO;
import net.iubris.archimedes.db.hsql.HsqlDBHandler;

public enum ArchimedesService {
	
	INSTANCE;
	
	private Class<?> clazz;	
	private HsqlDBHandler hsqldbh = null;
	private Object[] objs;
	
	public void init(Class<?> clazz, HsqlDBHandler hsqldbh) {
		this.clazz = clazz;
		this.hsqldbh = hsqldbh;
	}
	
	@SuppressWarnings("unchecked")
	public <T> EGDAO<T> getDAO() {
		T obj = null;
		Class<T> c = null;
		try {
			obj = (T) this.clazz.newInstance();
			c = (Class<T>) obj.getClass();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		if (hsqldbh!=null) {
			return new HsqlDAO<T>(c,hsqldbh); 
		}
		return null;
	}
	
	public void setArgumentForConcreteEGDAOClassConstructorInvoker(Object[] objs) {
		System.out.println(objs.length);
		this.objs = objs;
	}
	public int getArgsLength() {
		return this.objs.length;
	}
}
