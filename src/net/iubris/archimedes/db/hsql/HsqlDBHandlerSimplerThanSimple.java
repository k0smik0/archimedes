/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * HsqlDBHandlerSimplerThanSimple.java is part of 'archimedes'.
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
package net.iubris.archimedes.db.hsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum HsqlDBHandlerSimplerThanSimple implements HsqlDBHandler {

	INSTANCE;
	
	/*
	private final String DRIVER = "org.hsqldb.jdbcDriver";
	private final String DBURL = "jdbc:hsqldb:hsql://localhost/kdb";
	*/
	
	private HsqlDBHandlerSimplerThanSimple() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println(this.getClass().getName()+": failed to load HSQLDB JDBC driver" + "\n" + e.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public Connection createConnection() {
		try {
			return DriverManager.getConnection(DBURL);
		} 
		catch (SQLException e) {
			System.err.println(this.getClass().getName() + ".createConnection(): failed creating connection" + "\n" + e.toString());
			e.printStackTrace();
			//System.err.println("Was the database started? Is the database URL right?");
			return null;
		}
	}
	
	@Override
	public void closeConnection(Connection conn) {
		try {
			if (conn!=null) {
				conn.close();
			}
		}
		catch (SQLException e) {
			System.err.println(this.getClass().getName() + ".closeConnection(): failed closing connection" + "\n" + e.toString());
			e.printStackTrace();
		}
	}

/*		
		@Override
		public UserDAO getUserDAO() {
			return new HsqldbUserDAO();
		}

*/
		
}
