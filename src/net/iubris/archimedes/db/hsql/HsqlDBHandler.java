/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * HsqlDBHandler.java is part of 'archimedes'.
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

public interface HsqlDBHandler {

	public Connection createConnection();
	
	public void closeConnection(Connection conn);
	
	public final String DRIVER = "org.hsqldb.jdbcDriver";
	//public final String DBURL = "jdbc:hsqldb:hsql://localhost/k";
	public final String DBURL = "jdbc:hsqldb:/home/k0smik0/workspace/db/hsql/k";
	
	
}
