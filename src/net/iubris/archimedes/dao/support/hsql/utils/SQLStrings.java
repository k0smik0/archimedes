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
