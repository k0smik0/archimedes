/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * PlainTextFileEGDAO.java is part of 'archimedes'.
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
package net.iubris.archimedes.dao.support.plaintext;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.iubris.archimedes.dao.EGDAO;
import net.iubris.archimedes.dao.support.plaintext.utils.OPMReflectionsMapper;
import net.iubris.archimedes.db.plaintextfile.PTFDB;
import net.iubris.archimedes.db.plaintextfile.PlainTextFileDB;
import net.iubris.archimedes.utils.Converter;

public class PlainTextFileEGDAO<T> implements EGDAO<T> {

	private PlainTextFileDB fileDB = null;
	
	private Class<T> clazz;	
	
	final private String COLUMN_SEPARATOR = "#";
	
	public PlainTextFileEGDAO(Class<T> clazz) {
		this.clazz = clazz;
		OPMReflectionsMapper.INSTANCE.initialize(clazz);		
	}
	public PlainTextFileEGDAO(Class<T> clazz,File fileDB) {
		this(clazz);
		this.fileDB = new PlainTextFileDB(fileDB);		
	}	
	public PlainTextFileEGDAO(Class<T> clazz,String fileDBName) {
		this(clazz);
		this.fileDB = new PlainTextFileDB(fileDBName);		
	}		
		
	/*
	 * C
	 */
	public boolean create(T dto) {		
		
		List<String> valuesList = new ArrayList<String>(OPMReflectionsMapper.INSTANCE.getFieldMap().size());
		for (Field field: OPMReflectionsMapper.INSTANCE.getFieldMap().values()) {
			Integer column = OPMReflectionsMapper.INSTANCE.getFieldPositionMap().get(field.getName());
			String value = "";
			try {
				value = field.get(dto).toString();
			} catch (IllegalArgumentException e) {		
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}			
			valuesList = new ArrayList<String>(OPMReflectionsMapper.INSTANCE.getFieldMap().size());
			valuesList.add(column, value);
		}
		
		String line = "";
		
		Iterator<String> i = valuesList.iterator();
		while ( i.hasNext() ) {			
			line += "#"+i.next();
		}
		line.replaceFirst("#", "");
		
		return this.fileDB.appendToFile(line);		
	}
	
	/*
	 * R
	 */
	@Override
	public Collection<T> read(String fieldNameAsFilter, Object valueAsObject) {
		String value = valueAsObject.toString();		
		Collection<T> c = new ArrayList<T>();
		List<String> l = this.fileDB.readAllFile();
		
		for (Iterator<String> i = l.iterator(); i.hasNext(); ) {
			String row = i.next();
			List<String> valuesRow = Arrays.asList(row.split(COLUMN_SEPARATOR));
			if ( valuesRow.get(OPMReflectionsMapper.INSTANCE.getFieldPositionMap().get(fieldNameAsFilter)).equals(value) ) { //found!
				T o = null;
				try {
					o = rowAsPojo(valuesRow);
				} catch (InstantiationException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
				c.add(o);
			}			
		}		
		return c;
	}	
	public Collection<T> read(T data) {
		//String value = valueAsObject.toString();		
		Collection<T> c = new ArrayList<T>();
		List<String> allRows = this.fileDB.readAllFile();
		
		for (Iterator<String> i = allRows.iterator(); i.hasNext(); ) {
			String row = i.next();
			List<String> valuesRow = Arrays.asList(row.split(COLUMN_SEPARATOR));
			
			List<Boolean> notValid = new ArrayList<Boolean>();
			
			for (String s: OPMReflectionsMapper.INSTANCE.getFieldPositionMap().keySet()) {
				int columnNumber = OPMReflectionsMapper.INSTANCE.getFieldPositionMap().get(s);
				String columnValue = valuesRow.get(columnNumber);
				Field f = null;
				try {
					f = data.getClass().getDeclaredField(s);
					f.setAccessible(true);
					if (!(f.get(data)).equals(columnValue)) {
						notValid.add(false);	
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {					
					e.printStackTrace();
				}				
			}
			
			if ( notValid.size() == 0 ) { //found!
				T o = null;
				try {
					o = rowAsPojo(valuesRow);
				} catch (InstantiationException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
				c.add(o);
			}			
		}		
		return c;		
	};
	public Collection<T> read(Map<String,String> filters) { return null; };
	//public Collection<T> read(String fieldNameAsFilter, Object value){ return null; };
	public Collection<T> read(HashMap<String,Object> filters){ return null; };
	public Collection<T> readAll() {
		return null;
	}	
	
	/*
	 * U
	 */
	@Override
	public boolean update(T data) {
		return false;
	}
	
	/*
	 * D
	 */
	@Override
	public boolean delete(T data) {
		return false;
	};
	@Override
	public boolean delete(T data, String fieldNameAsFilter) {
 		return false;
	}
	@Override
	public boolean delete(String fieldNameAsFilter, Object someValue) {
		return false;
	}

	/*
	 * Find by id
	 */
	/*
	public Data findData(String id) {
		
		if ( contains(id)) {
		
		try {
			Data result = null;
	                String app;
			FileReader f1 = new FileReader(this.fileDataDB.getFileDB());
			BufferedReader b1= new BufferedReader(f1);
	
			app=b1.readLine();
			
			while (app!=null){
				result = risultato(id,app);
	            if (result != null) return result; 
	            app=b1.readLine();
			}
		}catch (IOException e){
			return null;
		}
		}
		return null;
	}// findData
	*/
	/*
	private Data risultato(String id, String linea) {
		Data result=null;
		StringTokenizer tokens;
		String loc;
		tokens = new StringTokenizer(linea, ", \t\n\r\f");
		loc = (tokens.nextToken()).trim();
		if (!loc.equals(id)) return null; // altrimenti abbiamo trovato prodotto
		
		//result = new Data();
		
		//result.setId(loc); // setta ID
		//result.setNome((tokens.nextToken()).trim());
		//result.setMarca((tokens.nextToken()).trim());
		//result.setPrezzo((tokens.nextToken()).trim());
		
		return result;
	}
	 */
	
	


	/*
	private boolean contains(String id) {
		return Grep.process(id, this.fileDataDB.getFileDB());		
	}	
	
	
	
	private boolean contains(int id) {
		return contains(""+id);
	}
	*/

	@Override
	public Collection<T> getAll() {
		return null;
	}
	
	
	/*
	private void insert(String linesToBeInserted) {
		FileOutputStream fos = null;
		try {			
			fos = new FileOutputStream(this.fileDB);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	
		PrintWriter out = new PrintWriter(fos);
		
		try {			
				out.println(linesToBeInserted);
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			out.flush();
		    out.close();
		}		
	}
	*/
	
	public void setFileDataDB(PlainTextFileDB fileDB) {
		this.fileDB = fileDB;		
	}	
	
	
	private T rowAsPojo(List<String> valuesRow) throws InstantiationException, IllegalAccessException {
		
		T pojo = this.clazz.newInstance();
				
		for (Field field: OPMReflectionsMapper.INSTANCE.getFieldMap().values()) {
			PTFDB annotation =  field.getAnnotation(PTFDB.class);			
			String value = valuesRow.get( annotation.position() );			
			Object toSet = Converter.INSTANCE.convert(value,field.getType());
			
			field.setAccessible(true);
			field.set(pojo, toSet );
			field.setAccessible(false);
		}
		
		return pojo;
	}

}

