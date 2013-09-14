/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * ArchimedesService_OLD.java is part of 'archimedes'.
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

public enum ArchimedesService_OLD {
	
	INSTANCE;
	
	private Class<?> clazz;	
	private HsqlDBHandler hsqldbh = null;
	
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
	
	
	
	
	

	//static private ArchimedesService instance;
	//private T t;
	
	//private Class<EGDAO<T>> clazz;
	/*	
	private ArchimedesService() {}	
	static public  ArchimedesService getInstance(){
		if (instance==null) {
			System.out.println("ArchimedesService:21 newInstance" );
			instance = new ArchimedesService();			
		}
		else {
			System.out.println("ArchimedesService:24 instance" );
		}
		return (ArchimedesService) instance;
	}
	*/
		
	/*
	private Constructor<HsqlDAO> hsqldao;
	private Constructor<HsqlDAO<T>> hsqldaoT;
	private Constructor<HsqlDAO<Concerto>> hsqldaoC;
	private Object[] objs;
	private Constructor<HsqlDAO<T>> hsqldaoP;
	*/
	private Object[] objs;
	//private Class[] classes;
	
	public void setClassName(String name) {
	//	Class<HsqlDAO<T>> clazz = (Class<HsqlDAO<T>>) Class.forName("net.iubris.archimedes.dao.support.hsql.HsqlDAO");
		//this.clazz = clazz;
	}
	
	/*
	public void setParameterizedConstructorForConcreteHsqlDAOClass(Constructor<HsqlDAO<T>> t) {
		this.hsqldaoT = t;
	}*/
	/*
	public void setArgumentForConcreteEGDAOClassConstructorRetriever(Class[] cs) {
		this.classes = cs;
	}*/
	public void setArgumentForConcreteEGDAOClassConstructorInvoker(Object[] objs) {
		System.out.println(objs.length);
		this.objs = objs;
	}
	public int getArgsLength() {
		return this.objs.length;
	}
	/*
	public <T> EGDAO<T> getDAO(EGDAOType et,Class<T> clazz) {
		switch (et){
			case HSQL:
				HsqlDAO<T> h = new HsqlDAO<T>();
				Constructor<HsqlDAO<T>> c;
				HsqlDAO<T> hsqldao = null;
				try {
					c = (Constructor<HsqlDAO<T>>) h.getClass().getConstructor(classes);
					hsqldao = c.newInstance(objs);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
				return hsqldao;				
				//break;
			case PLAINTEXT:
				break;
		}
				
				
				try {
			if (this.hsqldaoT!=null)  return (EGDAO<T>) this.hsqldaoC.newInstance(objs);
			if (this.hsqldaoC!=null)  return (EGDAO<T>) this.hsqldaoC.newInstance(objs);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null; 
	}
*/
	/*
	public void setConstructorForConcreteHsqlDAOClass(Constructor<HsqlDAO<Concerto>> c) {
		this.hsqldaoC = c;		
	}
	public void setParameterizedConstructorForConcreteHsqlDAOClass(Constructor<HsqlDAO<T>> p) {
		this.hsqldaoP = p;
	}
	public void setNotParameterizedConstructorForConcreteHsqlDAOClass(Constructor<HsqlDAO> h) {
		this.hsqldao = h;
	}
	*/
	/*
	public enum EGDAOType {
		HSQL,PLAINTEXT;
	}
	*/
}
