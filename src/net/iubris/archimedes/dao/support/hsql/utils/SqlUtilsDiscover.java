/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * SqlUtilsDiscover.java is part of 'archimedes'.
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
/* 
 * Massimiliano Leone - k0smik0@logorroici.org - 2009, GPL license
 * 
 * A simple method discover for PreparedStatement, based on value type 
 * For now, just int/Integer, float/Float, double/Double 
 * are supported.
 *  
 */
package net.iubris.archimedes.dao.support.hsql.utils;

import java.lang.reflect.Type;

public enum SqlUtilsDiscover {
	
	INSTANCE;

	/*
	 * @param type 		type (Type) which for we would discover setter
	 * @return 			method name which will be invoked in query	
	 */
	public String setterDiscover(Type type) {
		
		if ( type.equals(String.class)) return "setString";
		
		if ( type.equals(int.class)) return "setInt";
		if ( type.equals(Integer.class)) return "setInt";
		
		if ( type.equals(float.class)) return "setFloat";
		if ( type.equals(Float.class)) return "setFloat";
		
		if ( type.equals(double.class)) return "setDouble";
		if ( type.equals(Double.class)) return "setDouble";
				
		return null;
	}
	
	/*
	 * @param type 		type (Type) which for we would discover getter
	 * @return 			method name which will be invoked in query	
	 */
	public String getterDiscover(Type type) {
		
		if ( type.equals(String.class)) return "getString";
		
		if ( type.equals(int.class)) return "getInt";
		if ( type.equals(Integer.class)) return "getInt";
		
		if ( type.equals(float.class)) return "getFloat";
		if ( type.equals(Float.class)) return "getFloat";
		
		if ( type.equals(double.class)) return "getDouble";
		if ( type.equals(Double.class)) return "getDouble";
				
		return null;
	}
	
	/*
	 * @param type 		java type (Type) which for we would discover SQL type
	 * @return 			SQL type
	 */	
	public String sqlTypeDiscover(Type type) {		
		if ( type.equals(String.class)) return "VARCHAR";
		
		if ( type.equals(int.class)) return "INT";
		if ( type.equals(Integer.class)) return "INTEGER";
		
		if ( type.equals(float.class)) return "DOUBLE";
		if ( type.equals(Float.class)) return "DOUBLE";
		
		if ( type.equals(double.class)) return "DOUBLE";
		if ( type.equals(Double.class)) return "DOUBLE";
				
		return null;
	}
	
	/*
	 * @param type 		java type (Type) which for we would PreparedStatement setter compliant type
	 * @return 			SQL type
	 */	
	public Class<?> preparedStatementTypeDiscover(Type type) {		
		if ( type.equals(String.class)) return String.class;
		
		if ( type.equals(int.class)) return int.class;
		if ( type.equals(Integer.class)) return int.class;
		
		if ( type.equals(float.class)) return float.class;
		if ( type.equals(Float.class)) return float.class;
		
		if ( type.equals(double.class)) return double.class;
		if ( type.equals(Double.class)) return double.class;
				
		return null;
	}
}

