/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * HsqlDAO.java is part of 'archimedes'.
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
package net.iubris.archimedes.dao.support.hsql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.iubris.archimedes.dao.EGDAO;
import net.iubris.archimedes.dao.support.hsql.utils.ORMReflectionsMapper;
import net.iubris.archimedes.dao.support.hsql.utils.SQLStrings;
import net.iubris.archimedes.dao.support.hsql.utils.SqlUtilsDiscover;
import net.iubris.archimedes.db.hsql.HsqlDBHandler;
import net.iubris.archimedes.db.hsql.HsqlDBHandlerSimplerThanSimple;

public class HsqlDAO<T> implements EGDAO<T> {
		
	private HsqlDBHandler hsqlh;
	//private Class<T> clazz;
	//private Field[] tFields;
	/*
	private Map<String,Field> fieldsMap;
	private Map<Integer, String> fieldPositionMap;
	private Map<Field,Map<String,Method>> fieldMethodsMap;
	private String tableName;
	*/
	//private SQLStrings sqls;
	private Class<T> clazz;
	
	public HsqlDAO() {}
	
	public HsqlDAO(Class<T> clazz) {
		this.clazz = clazz;
		ORMReflectionsMapper.INSTANCE.initialize(clazz);
	}
	
	public HsqlDAO(Class<T> clazz,HsqlDBHandler hsqlh) {
		this(clazz);
		this.hsqlh = hsqlh;
	}
	
	public HsqlDAO(Class<T> clazz,int a) { // a dummy internal factory method
		this(clazz);
		this.hsqlh = HsqlDBHandlerSimplerThanSimple.INSTANCE;	
	}
	
	
	
	//final String lastInsert = "CALL IDENTITY() ;";
	/*
	 * (non-Javadoc)
	 * @see net.iubris.archimedes.dao.EGDAO#create
	 * 
	 * C
	 */
	@Override
	public boolean create(T data) {
		//"INSERT " +
        //"INTO " + post_table + " ( " + 
        //        post_id + ", " + post_giorno +", " + post_ora + ", " + post_testo + 
        //") " +
        //"VALUES (?,?,?,?) ;"
		/*
		 String insert = "INSERT INTO " + ORMReflectionsMapper.INSTANCE.getTableName() + " ";
		String columns = "";
		for (String attribute : ORMReflectionsMapper.INSTANCE.getFieldsPositionMap().values() ) {
       		columns += ", " + attribute; 
       	}
		columns = columns.replaceFirst(",", "(");
		columns +=  " ) ";
		String tail = "VALUES ";
		for (String attribute : ORMReflectionsMapper.INSTANCE.getFieldsPositionMap().values() ) {
			Field f = data.getClass().getField(attribute);
			f.setAccessible(true);
			f.get(data);
		}
		}*/
		
		boolean result = false;        
        if ( data == null )  {
                System.err.println(this.getClass().getName()+".create(): failed to insert a null");
                return result;
        }		
		Connection conn = hsqlh.createConnection();
		/*
		try {
            Statement stmt = conn.createStatement();
            stmt.execute("");         
            result = true;
            stmt.close();
        } catch (SQLException e) {
	        System.err.println(this.getClass().getName()+".createUser(): failed to insert user\n"+e);
	        e.printStackTrace();
	        result = false;
		}		
		finally {
		        hsqlh.closeConnection(conn);
		}
		*/
		
		
		try {
            PreparedStatement prep_stmt = conn.prepareStatement(SQLStrings.INSTANCE.getInsert());
            prep_stmt.clearParameters();
            
            for (Integer i: ORMReflectionsMapper.INSTANCE.getFieldPositionMap().keySet()) {            
            	Field f = data.getClass().getDeclaredField(ORMReflectionsMapper.INSTANCE.getFieldPositionMap().get(i));
            	f.setAccessible(true);
            
            	Method m = ORMReflectionsMapper.INSTANCE.getFieldAccessorMethodMap().get(f).get("set");
            	
            	m.invoke(prep_stmt, i, f.get(data));
            	
            	/* all string based
            	String value = f.get(t).toString();
            
            	prep_stmt.setString(i, value);
            	*/
            }           
            
            /*
            prep_stmt.setString(1, t.getName());
            prep_stmt.setString(2, t.getSurname());
            prep_stmt.setString(3, t.getAddress());
            prep_stmt.setLong(4, t.getMatriculation());
            prep_stmt.setString(5, t.getNick());
            prep_stmt.setBytes(6, t.getPassword());
                        //prep_stmt.setInt(parameterIndex, x)
            */

            prep_stmt.executeUpdate();
            prep_stmt.close();
            result = true;
		}
		catch (SQLException e) {
		        System.err.println(this.getClass().getName()+".createUser(): failed to insert user\n"+e);
		        e.printStackTrace();
		        result = false;
		} catch (SecurityException e) {
			e.printStackTrace();
			result = false;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			result = false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result = false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			result = false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			result = false;
		}		
		finally {
			System.out.println("HsqlDAO:165 closing connection");	 
			hsqlh.closeConnection(conn);
		}
		return result;
		
		
		/*
		try {
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(HsqldbUserDAO.lastInsert);
		        if ( rs.next() ) {
		                result = rs.getStr
		        }               
		        rs.close();
		        stmt.close();
		}
		catch (Exception e) {
		        System.err.println(HsqldbUserDAO.class.getName()+".createUser(): failed to retrieve id of last inserted user\n"+e);
		        e.printStackTrace();
		}
		finally {
		        HsqldbDAOFactory.closeConnection(conn);
		}
		return result;
		*/
	}

	/*
	 * (non-Javadoc)
	 * @see net.iubris.archimedes.dao.EGDAO#read(java.lang.String, java.lang.String)
	 * 
	 * R
	 */
	@Override
	public Collection<T> read(String fieldNameAsFilter, Object value) {
		// String sample = "SELECT * FROM " + user_table + " WHERE " + user_id + " = ? ;" ;

		String read = "SELECT * FROM " + 
						ORMReflectionsMapper.INSTANCE.getTableName() + 
						" WHERE " + fieldNameAsFilter + " = ? ;" ;
		
        Collection<T> result = null;

        if ( fieldNameAsFilter == null || value == null  )  {
                System.err.println(this.getClass().getName()+".read(): failed - parameters must be not null");
                return result;
        }

        Connection conn = hsqlh.createConnection();

        try {
                PreparedStatement prep_stmt = conn.prepareStatement(read);
                prep_stmt.clearParameters();
                
            	String setterMethodName = SqlUtilsDiscover.INSTANCE.setterDiscover(value.getClass());            
    		    Method m = PreparedStatement.class.getDeclaredMethod(setterMethodName, int.class, SqlUtilsDiscover.INSTANCE.preparedStatementTypeDiscover( value.getClass() ) );
    			m.invoke(prep_stmt, 1, value);                
                //prep_stmt.setInt(1, id); //if value is int - sample

                ResultSet rs = prep_stmt.executeQuery();
                //if (rs!=null) {
 
             	Collection<T> lResult = new ArrayList<T>();
                //}
                Collection<String> fieldsOrdered = ORMReflectionsMapper.INSTANCE.getFieldPositionMap().values();
                while (rs.next()) {                	
                	T data = clazz.newInstance();
                	for (String fieldName: fieldsOrdered  ) {                	
	                	Field f = ORMReflectionsMapper.INSTANCE.getFieldMap().get(fieldName);
	                	Method method = ORMReflectionsMapper.INSTANCE.getFieldAccessorMethodMap().get(f).get("get");	                
	            		f.setAccessible(true);
	            		Object toSet = method.invoke(rs, f.getName());
                		f.set(data, toSet );
                	}
                	lResult.add(data);
                }
                if (!lResult.isEmpty()) result = lResult;
                rs.close();
                prep_stmt.close();
        }
        catch (SQLException e) {
           System.err.println(this.getClass().getName()+".read(): failed to retrieve element with " +fieldNameAsFilter + " = " + value+"\n"+e);
           e.printStackTrace();
        } catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
        finally {
        	hsqlh.closeConnection(conn);
        }    
        return result;
	}
	public Collection<T> read(T data) {
		// String sample = "SELECT * FROM " + user_table + " WHERE " + user_id + " = ? ;" ;
		/*
		String read = "SELECT * FROM " + 
						ORMReflectionsMapper.INSTANCE.getTableName() + 
						" WHERE " + fieldNameAsFilter + " = ? ;" ;
		*/        

        if ( data == null )  {
		    System.err.println(this.getClass().getName()+".read(): failed to read a null element");
		    return null;
		}
        Collection<T> result = null;
		Connection conn = hsqlh.createConnection();
		
		try {
		    PreparedStatement prep_stmt = conn.prepareStatement(SQLStrings.INSTANCE.getReadExactElement());
		    prep_stmt.clearParameters();
		    
		    for (Integer position : ORMReflectionsMapper.INSTANCE.getFieldPositionMap().keySet()) {
		    	String fieldName = ORMReflectionsMapper.INSTANCE.getFieldPositionMap().get(position);
		    	Field f = data.getClass().getDeclaredField( fieldName );		    	
		    	Method prep_stmt_setter = ORMReflectionsMapper.INSTANCE.getFieldAccessorMethodMap().get( f ).get("set");		    	
		    	f.setAccessible(true);
		    	Object toSet = f.get(data);		    	
		    	prep_stmt_setter.invoke(prep_stmt, position, toSet);		    		
		    }
		    
		    //System.out.println(prep_stmt.toString());
            ResultSet rs = prep_stmt.executeQuery();
            if (rs!=null) {
//            	System.out.println("HsqlDAO:297 "+rs.toString());
            	result = new ArrayList<T>();
            }
            Collection<String> fieldsOrdered = ORMReflectionsMapper.INSTANCE.getFieldPositionMap().values();
            while (rs.next()) {                	
            	T data_new = clazz.newInstance();
            	for (String fieldName: fieldsOrdered  ) {                	
                	Field f = ORMReflectionsMapper.INSTANCE.getFieldMap().get(fieldName);
                	Method method = ORMReflectionsMapper.INSTANCE.getFieldAccessorMethodMap().get(f).get("get");	                
            		f.setAccessible(true);
            		Object toSet = method.invoke(rs, f.getName());
            		f.set(data_new, toSet );
            	}
            	result.add(data_new);
            }                       
            rs.close();
            prep_stmt.close();
        }
        catch (SQLException e) {
           System.err.println(this.getClass().getName()+".read(): failed to retrieve any element with \n"+e);
           e.printStackTrace();
        } catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
        finally {
        	hsqlh.closeConnection(conn);
        }

        return result;
	};
	public Collection<T> read(Map<String,String> filters) { return null; };	
	//public Collection<T> read(String fieldNameAsFilter, Object value){ return null; };
	public Collection<T> read(HashMap<String,Object> filters){ return null; };
		
	public Collection<T> readAll() {
		SQLStrings.INSTANCE.getReadAllElementsString();
		
		
        Collection<T> result = null;
		Connection conn = hsqlh.createConnection();
		
		try {
			/*
		    PreparedStatement prep_stmt = conn.prepareStatement(SQLStrings.INSTANCE.getReadAllElementsString());
		    prep_stmt.clearParameters();
		    
		    for (Integer position : ORMReflectionsMapper.INSTANCE.getFieldPositionMap().keySet()) {
		    	String fieldName = ORMReflectionsMapper.INSTANCE.getFieldPositionMap().get(position);
		    	Field f = clazz.getDeclaredField( fieldName );		    	
		    	Method prep_stmt_setter = ORMReflectionsMapper.INSTANCE.getFieldAccessorMethodMap().get( f ).get("set");		    	
		    	f.setAccessible(true);
		    	Object toSet = f.get(data);		    	
		    	prep_stmt_setter.invoke(prep_stmt, position, toSet);		    		
		    }*/
		    
		    Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(SQLStrings.INSTANCE.getReadAllElementsString());         
            		    		    
		    //if (rs!=null) {
//		    System.out.println("HsqlDAO:366 "+rs.toString());
		    Collection<T> lResult = new ArrayList<T>();
		    //}
            Collection<String> fieldsOrdered = ORMReflectionsMapper.INSTANCE.getFieldPositionMap().values();
            while (rs.next()) {                	
            	T data_new = clazz.newInstance();
            	for (String fieldName: fieldsOrdered  ) {                	
                	Field f = ORMReflectionsMapper.INSTANCE.getFieldMap().get(fieldName);
                	Method method = ORMReflectionsMapper.INSTANCE.getFieldAccessorMethodMap().get(f).get("get");	                
            		f.setAccessible(true);
            		Object toSet = method.invoke(rs, f.getName());
            		f.set(data_new, toSet );
            	}
            	lResult.add(data_new);
            }
            if (!lResult.isEmpty()) result = lResult;	
            rs.close();
            stmt.close();
        }
        catch (SQLException e) {
           System.err.println(this.getClass().getName()+".readAll(): failed to retrieve any element with \n"+e);
           e.printStackTrace();
        } catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
        finally {
        	hsqlh.closeConnection(conn);
        }
        return result;		
	}

	@Override
	public boolean update(T data) {
		return false;
	}
	
	@Override
	public boolean delete(T data) {
		//String delete = "DELETE FROM " + ORMReflectionsMapper.INSTANCE.getTableName() + " " +
		//	"WHERE " + fieldNameAsFilter + " = ? ;" ;
		
		boolean result = false;
		
		if ( data == null )  {
		    System.err.println(this.getClass().getName()+".delete(): failed to delete a null element");
		    return result;
		}
		
		Connection conn = hsqlh.createConnection();
		
		try {
		    PreparedStatement prep_stmt = conn.prepareStatement(SQLStrings.INSTANCE.getDeleteExactElement());
		    prep_stmt.clearParameters();
		    
		    for (String fieldName : ORMReflectionsMapper.INSTANCE.getFieldPositionMap().values()) {
		    	Field f = data.getClass().getDeclaredField( fieldName );
		    	f.setAccessible(true);
		    	Method prep_stmt_setter = ORMReflectionsMapper.INSTANCE.getFieldAccessorMethodMap().get( f ).get("set");
		    	prep_stmt_setter.invoke(prep_stmt, 1, f.get(data));
		    }
		    prep_stmt.executeUpdate();		
		    result = true;		    
		    prep_stmt.close();
		}
		catch (SQLException e) {
		    System.err.println(this.getClass().getName()+".delete(): failed to delete with null parameters\n"+e);
		    e.printStackTrace();
		    result = false;
		} catch (SecurityException e) {
			e.printStackTrace();
			result = false;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			result = false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result = false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			result = false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			result = false;
		}
		finally {
		    hsqlh.closeConnection(conn);
		}
		return result;
	}
	
	@Override
	public boolean delete(T data, String fieldNameAsFilter) {
		String delete = "DELETE FROM " + ORMReflectionsMapper.INSTANCE.getTableName() + " " +
                    "WHERE " + fieldNameAsFilter + " = ? ;" ;

        boolean result = false;

        if ( data == null )  {
                System.err.println(this.getClass().getName()+".delete(): failed to delete a null element");
                return result;
        }

        Connection conn = hsqlh.createConnection();

        try {
            PreparedStatement prep_stmt = conn.prepareStatement(delete);
            prep_stmt.clearParameters();
            
            Field f = data.getClass().getDeclaredField( fieldNameAsFilter );
            f.setAccessible(true);
            Method m = ORMReflectionsMapper.INSTANCE.getFieldAccessorMethodMap().get( f ).get("set");            	
        	m.invoke(prep_stmt, 1, f.get(data));
            //prep_stmt.setInt(1, t.getId()); //if value is int

            prep_stmt.executeUpdate();

            result = true;
            
            prep_stmt.close();
        }
        catch (Exception e) {
            System.err.println(this.getClass().getName()+".delete(): failed to delete with null parameters\n"+e);
            e.printStackTrace();
        }
        finally {
        	hsqlh.closeConnection(conn);
        }
        return result;
	}
	public boolean delete(String fieldNameAsFilter, Object someValue) {
		String delete = "DELETE FROM " + ORMReflectionsMapper.INSTANCE.getTableName() + " " +
        "WHERE " + fieldNameAsFilter + " = ? ;" ;

		boolean result = false;
		
		if ( fieldNameAsFilter == null || someValue == null  )  {
		    System.err.println(this.getClass().getName()+".delete(): failed - parameters must be not null");
		    return result;
		}
		
		Connection conn = hsqlh.createConnection();
		
		try {
		    PreparedStatement prep_stmt = conn.prepareStatement(delete);
		    prep_stmt.clearParameters();

		    String setterMethodName = SqlUtilsDiscover.INSTANCE.setterDiscover(someValue.getClass());
		    Method m = PreparedStatement.class.getMethod(setterMethodName, someValue.getClass());
			m.invoke(prep_stmt, 1, someValue);
		    //prep_stmt.setInt(1, t.getId()); //example
		
		    prep_stmt.executeUpdate();
		
		    result = true;
		    
		    prep_stmt.close();
		}
		catch (Exception e) {
		    System.err.println(this.getClass().getName()+".delete(): failed to delete element with "+fieldNameAsFilter+" = "+someValue+"\n"+e);
		    e.printStackTrace();
		}
		finally {
		    hsqlh.closeConnection(conn);
		}
		return result;
	}

	@Override
	public Collection<T> getAll() {
		return null;
	}
	
	public void setHsqlDBHandler(HsqlDBHandler hsqlh) {
		this.hsqlh = hsqlh;
	}
	
	public void setClassType(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	
	/*private void init() {
		
		
		prepareFieldsMap();
		prepareFieldPositionMap();
		prepareTypeMethodMap();
				
		//getTableName();
		
		
		this.sqls = SQLStrings.INSTANCE;
		this.sqls.setCreate(buildCreateTableString());
		this.sqls.setDrop(buildDropTableString());
		this.sqls.setInsert(buildInsertString());
		
	}
	*/
	/*
	private void prepareFieldsMap() {
		this.fieldsMap = new HashMap<String,Field>();
		for (Field f: this.clazz.getDeclaredFields()) {
			this.fieldsMap.put(f.getName(), f);
		}
	}
	private void prepareFieldPositionMap() {
		this.fieldPositionMap = new TreeMap<Integer,String>();
		for (Field field: fieldsMap.values()) {	
			HSQLDB annotation = field.getAnnotation(HSQLDB.class);
			if (annotation!=null) {
				fieldPositionMap.put(annotation.position(), field.getName() );
			}
		}
	}	
	private void prepareTypeMethodMap() {
		this.fieldMethodsMap = new HashMap<Field,Map<String,Method>>();
		for (Field f: fieldsMap.values()) {
			String setterMethodName = SqlUtilsDiscover.INSTANCE.setterDiscover(f.getType());
			String getterMethodName = SqlUtilsDiscover.INSTANCE.getterDiscover(f.getType());
			try {
				Method sm = PreparedStatement.class.getMethod(setterMethodName, f.getType());
				Method gm = PreparedStatement.class.getMethod(getterMethodName, f.getType());
				Map<String,Method> m = new HashMap<String,Method>();
				m.put("set", sm);
				m.put("get", gm);
				this.fieldMethodsMap.put(f, m);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	*/
	/*
	private void getTableName() {
		String[] a = clazz.getName().split(".");
		String tableName = a[a.length-1];
		this.tableName = tableName;
	}
	
	private String buildCreateTableString() {
		
		//      "CREATE " +
        //        "TABLE " + nome_tabella +" ( " +
        //                post_id + " INT NOT NULL, " +
        //                post_giorno + " VARCHAR(20), " +
        //                post_ora + " VARCHAR(10), " +
        //                post_testo + " VARCHAR(250) " +
        //                ...
        //                nome_field + " TIPO " +
        //        ") ;"
		 		
		String s = "CREATE TABLE "+ this.tableName + " ";
		
		for (Integer i : this.fieldPositionMap.keySet()) {
			String name = this.fieldPositionMap.get(i);
       		s += ", " + name + " "+ SqlUtilsDiscover.INSTANCE.typeDiscover(this.fieldsMap.get(name).getType());
       	}
		s = s.replaceFirst(",", "(");
		s +=  ") ;";
		return s;
	}
	private String buildDropTableString() {
		
		//static String drop = 
	    //    "DROP TABLE " + post_table + " ;"
	    //    ;
				
		String s = "DROP TABLE " + this.tableName + " ;";
		return s;
	}
	private String buildInsertString() {		
		 
		//String sample =  
	    //  "INSERT " +
        //    "INTO " + user_table + " ( " + 
        //            user_name +", "+user_surname+", "+user_address+ " " +
        //    ") " +
        //    "VALUES (?,?,?,?,?,?) ;"
        //    ;
		
		String r = "INSERT INTO " + this.clazz.getName() + " ";
		
       	for (Integer i : this.fieldPositionMap.keySet()) {
       		r += ", " + this.fieldPositionMap.get(i); 
       	}
        r = r.replaceFirst(",", "(");
        r +=  " ) " + "VALUES "+"(?";
        for (int i=1; i< this.fieldPositionMap.size(); i++) {
        	r+= ",?";
        }
        r += ") ;";
        
        return r;
	}
	private String buildReadString() {
		return null;
	}
	*/
	/*
	private String buildDeleteString() {		
		//String sample = "DELETE FROM " + user_table + " " + "WHERE " + user_id + " = ? ;" ;
		return "";
	}
	*/
	
	public boolean buildTable() {
        boolean result = false;
        Connection conn = hsqlh.createConnection();
        try {
            Statement stmt = conn.createStatement();
            /*ResultSet rs =  stmt.executeQuery("SHOW TABLES in DATABASE K");
            while (rs.next()) {
            	String s = rs.getString(1);
            	System.out.println("HsqlDAO:622 "+s);
            }*/
            stmt.execute(SQLStrings.INSTANCE.getBuildTable());         
            result = true;
            stmt.close();
        }
        catch (SQLException e) {
        	System.err.println(this.getClass().getName()+".createTable(): failed to create table\n"+e);
        }
        finally {
        	hsqlh.closeConnection(conn);
        }
        return result;
	}
	public boolean dropTable() {
        boolean result = false;
        Connection conn = hsqlh.createConnection();
        try {
        	Statement stmt = conn.createStatement();
        	stmt.execute(SQLStrings.INSTANCE.getDropTable());
            result = true;
            stmt.close();
        }
        catch (SQLException e) {
            System.err.println(this.getClass().getName()+".dropTable(): failed to drop table\n"+e);
        }
        finally {
        	hsqlh.closeConnection(conn);
        }
        return result;
	}
	
}
