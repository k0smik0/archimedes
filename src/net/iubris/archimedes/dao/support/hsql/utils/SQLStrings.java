/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * SQLStrings.java is part of 'archimedes'.
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

public enum SQLStrings {
	
	INSTANCE;
	
	private String buildTable;
	private String dropTable;
	
	private String insert;	
	//private String delete;
	private String readExactElementString;
	private String readAllElementsString;
	private String deleteExactElementString;
	
	
	public String getBuildTable() {
		return buildTable;
	}

	public void setbuildTable (String buildTable) {
		this.buildTable = buildTable;
	}

	public String getDropTable() {
		return dropTable;
	}

	public void setDropTable(String dropTable) {
		this.dropTable = dropTable;
	}
	

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}
	
	public String getReadExactElement() {
		return this.readExactElementString ;		
	}	
	public void setReadExactElement(String readExactElementString) {
		this.readExactElementString = readExactElementString;		
	}
	
	public String getReadAllElementsString() {
		return readAllElementsString;
	}
	public void setReadAllElementsString(String readAllElementsString) {
		this.readAllElementsString = readAllElementsString;
	}
	
	public String getDeleteExactElement() {		
		return this.deleteExactElementString;
	}
	public void setDeleteExactElement(String deleteExactElementString) {		
		this.deleteExactElementString = deleteExactElementString;
	}
	/*
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	*/
		
}
