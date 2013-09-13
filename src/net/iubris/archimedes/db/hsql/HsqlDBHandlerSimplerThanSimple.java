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
