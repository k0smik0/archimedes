package net.iubris.archimedes.db.hsql;

import java.sql.Connection;

public interface HsqlDBHandler {

	public Connection createConnection();
	
	public void closeConnection(Connection conn);
	
	public final String DRIVER = "org.hsqldb.jdbcDriver";
	//public final String DBURL = "jdbc:hsqldb:hsql://localhost/k";
	public final String DBURL = "jdbc:hsqldb:/home/k0smik0/workspace/db/hsql/k";
	
	
}
