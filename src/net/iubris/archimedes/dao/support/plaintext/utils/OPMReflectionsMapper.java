/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * OPMReflectionsMapper.java is part of 'archimedes'.
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
package net.iubris.archimedes.dao.support.plaintext.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.iubris.archimedes.db.plaintextfile.PTFDB;

public enum OPMReflectionsMapper {
	INSTANCE;
	
	private boolean initialized = false;
	
	private Map<String,Field> fieldMap = null;
	private Map<String,Integer> fieldPositionMap = null;
	
	public <T> void initialize(Class<T> clazz) {
		if (this.initialized == false) {
			
			prepareFieldMap(clazz);
			prepareFieldPositionMap();
			//prepareAccesorMethodMap();
			/*
			obtainTableName(clazz);		
			
			
			SQLStrings.INSTANCE.setbuildTable( buildCreateTableString() );
			SQLStrings.INSTANCE.setDropTable( buildDropTableString() );
			SQLStrings.INSTANCE.setInsert( buildInsertString() );
			SQLStrings.INSTANCE.setReadExactElement( buildReadExactElementString() );			
			SQLStrings.INSTANCE.setDeleteExactElement( buildDeleteExactElementString() );
			*/
			this.initialized = true;
		}
	}
	
	public Map<String,Field> getFieldMap() {		
		return this.fieldMap;
	}
	
	public Map<String,Integer> getFieldPositionMap() {		
		return this.fieldPositionMap;
	}
	
	private <T> void prepareFieldMap(Class<T> clazz) {
		this.fieldMap = new HashMap<String,Field>();
		for (Field field: clazz.getDeclaredFields()) {
			this.fieldMap.put(field.getName(), field);
		}
		
	}
	private void prepareFieldPositionMap() {
		this.fieldPositionMap = new TreeMap<String,Integer>();
		for (Field field: this.fieldMap.values()) {
			PTFDB annotation = field.getAnnotation(PTFDB.class);
			if (annotation!=null) {				
				fieldPositionMap.put(field.getName(), annotation.position());
			}
		}
	}	

}
