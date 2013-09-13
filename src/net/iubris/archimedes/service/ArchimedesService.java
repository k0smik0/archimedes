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
