package net.iubris.archimedes.config;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import net.iubris.archimedes.dao.support.hsql.utils.ORMReflectionsMapper;
import net.iubris.archimedes.db.hsql.HsqlDBHandler;
import net.iubris.archimedes.db.hsql.HsqlDBHandlerSimplerThanSimple;
import net.iubris.archimedes.service.ArchimedesService;

public class ArchimedesInit extends HttpServlet {
	
	private static final long serialVersionUID = -9113954565040455077L;
	public void init(ServletConfig config) {
				
		String bc = config.getInitParameter("BaseClass");
		Class<?> clazz = null; 
		try {
			clazz = Class.forName(bc);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String daoType = config.getInitParameter("DAOType");
		initService(daoType,clazz);	
	}
	
	/*
	 * custom service to initialize hsql or plaintext or other dao,
	 * and so on 
	 */	
	private void initService(String daoType,Class<?> clazz) { // custom
		
		if (daoType.toUpperCase().equals(EGDAOType.HSQL.toString())) {
					
			ORMReflectionsMapper.INSTANCE.initialize(clazz);		
			HsqlDBHandler hsqldbh = HsqlDBHandlerSimplerThanSimple.INSTANCE;
			
			ArchimedesService.INSTANCE.init(clazz,hsqldbh);			
		}
		
	}
	
	private enum EGDAOType {
		HSQL,PLAINTEXT;
	}
	
}
