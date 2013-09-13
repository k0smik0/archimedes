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
