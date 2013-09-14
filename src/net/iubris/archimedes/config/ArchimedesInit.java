/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * ArchimedesInit.java is part of 'archimedes'.
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
