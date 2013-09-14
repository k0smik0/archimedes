/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ORMReflectionsMapper.java is part of 'archimedes'.
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
package net.iubris.archimedes.dao.support.hsql.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.iubris.archimedes.dao.support.hsql.utils.SQLStrings;
import net.iubris.archimedes.db.hsql.HSQLDB;

public enum ORMReflectionsMapper {
	INSTANCE;
	
	private boolean initialized = false;
	
	//private Class<?> clazz;
	private Map<String,Field> fieldMap = null;
	private Map<Integer, String> fieldPositionMap = null;
	private Map<Field,Map<String,Method>> fieldAccessorMethodMap = null;
	
	private String tableName;
	
	/*
	public void setClassType(Class<T> clazz) {
		
	}*/
	
	public <T> void initialize(Class<T> clazz) {
		if (this.initialized == false) {
			
			prepareFieldMap(clazz);
			prepareFieldPositionMap();
			prepareAccesorMethodMap();
			
			obtainTableName(clazz);		
			
			SQLStrings.INSTANCE.setbuildTable( buildCreateTableString() );
			SQLStrings.INSTANCE.setDropTable( buildDropTableString() );
			SQLStrings.INSTANCE.setInsert( buildInsertString() );
			SQLStrings.INSTANCE.setReadExactElement( buildReadExactElementString() );
			SQLStrings.INSTANCE.setReadAllElementsString( buildReadAllElementsString() );
			SQLStrings.INSTANCE.setDeleteExactElement( buildDeleteExactElementString() );
			
			this.initialized = true;
		}
	}	
	
	public Map<String, Field> getFieldMap() {
		return fieldMap;
	}
	public Map<Integer, String> getFieldPositionMap() {
		return fieldPositionMap;
	}
	public Map<Field, Map<String, Method>> getFieldAccessorMethodMap() {
		return fieldAccessorMethodMap;
	}
	public String getTableName() {
		return tableName; 
	}
	
	private <T> void prepareFieldMap(Class<T> clazz) {
		this.fieldMap = new HashMap<String,Field>();
		for (Field f: clazz.getDeclaredFields()) {
			this.fieldMap.put(f.getName(), f);
		}
	}
	private void prepareFieldPositionMap() {
		this.fieldPositionMap = new TreeMap<Integer,String>();
		for (Field field: this.fieldMap.values()) {	
			HSQLDB annotation = field.getAnnotation(HSQLDB.class);
			if (annotation!=null) {
				this.fieldPositionMap.put(annotation.position(), field.getName() );
			}
		}
	}	
	private void prepareAccesorMethodMap() {
		this.fieldAccessorMethodMap = new HashMap<Field,Map<String,Method>>();
		for (Field f: fieldMap.values()) {
			String setterMethodName = SqlUtilsDiscover.INSTANCE.setterDiscover(f.getType());
			String getterMethodName = SqlUtilsDiscover.INSTANCE.getterDiscover(f.getType());
			try {
				//System.out.println("prepareAccesorMethodMap - f: "+f.getName() +" "+ f.getType());
				Method sm = PreparedStatement.class.getDeclaredMethod(setterMethodName, int.class, f.getType());
				Method gm = ResultSet.class.getDeclaredMethod(getterMethodName, String.class);
				Map<String,Method> m = new HashMap<String,Method>();
				m.put("set", sm);
				m.put("get", gm);
				this.fieldAccessorMethodMap.put(f, m);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	private <T> void obtainTableName(Class<T> clazz) {	
		String[] a = clazz.getName().split("\\.");		
		String tableName = a[a.length-1];
		this.tableName = tableName;
	}
	private String buildCreateTableString() {
		/*
		      "CREATE " +
                "TABLE " + nome_tabella +" ( " +
                        post_id + " INT NOT NULL, " +
                        post_giorno + " VARCHAR(20), " +
                        post_ora + " VARCHAR(10), " +
                        post_testo + " VARCHAR(250) " +
                        ...
                        nome_field + " TIPO " +
                ") ;"
		 */		
		String s = "CREATE TABLE "+ this.tableName + " ";		
		for (String name : this.fieldPositionMap.values()) {
       		s += ", " + name + " "+ SqlUtilsDiscover.INSTANCE.sqlTypeDiscover(this.fieldMap.get(name).getType());
       	}
		s = s.replaceFirst(",", "(");
		s +=  ") ;";
		return s;
	}
	private String buildDropTableString() {
		/*
		static String drop = 
	        "DROP TABLE " + post_table + " ;"
	        ;
		*/		
		String s = "DROP TABLE " + this.tableName + " ;";
		return s;
	}
	private <T> String buildInsertString() {		
		/*
		String sample =  
	        "INSERT " +
            "INTO " + user_table + " ( " + 
                    user_name +", "+user_surname+", "+user_address+ " " +
            ") " +
            "VALUES (?,?,?,?,?,?) ;"
            ;
		*/
		String r = "INSERT INTO " + this.tableName + " ";		
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
	private String buildReadExactElementString() {
		String read = "SELECT * FROM " + 
			ORMReflectionsMapper.INSTANCE.getTableName() + " " +
		"WHERE "; 
		for (String fieldName : ORMReflectionsMapper.INSTANCE.getFieldPositionMap().values()) {
			read += "AND " + fieldName + " = ? " ;
		}
		read = read.replaceFirst("AND", "");
		read += " ; ";
		return read;
	}
	private String buildReadAllElementsString() {
		String read = "SELECT * FROM " +
			ORMReflectionsMapper.INSTANCE.getTableName() + " ;";
		return read;
	}
	private String buildDeleteExactElementString() {
		String delete = "DELETE FROM " + 
		ORMReflectionsMapper.INSTANCE.getTableName() + " " +
		"WHERE "; 
		for (String fieldName : ORMReflectionsMapper.INSTANCE.getFieldPositionMap().values()) {
		delete += "AND " + fieldName + " = ? " ;
		}
		delete = delete.replaceFirst("AND", "");
		delete += " ; ";
		return delete;
	}
	
}
