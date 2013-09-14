/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * PlainTextFileDB.java is part of 'archimedes'.
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
package net.iubris.archimedes.db.plaintextfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PlainTextFileDB {
			
	private File fileDB = null;

	public PlainTextFileDB() {}
	
	public PlainTextFileDB(File fileDB) {
		this.fileDB = fileDB;
		createFile();
	}
	
	public PlainTextFileDB(String fileDBName) {		
		setFileDB(fileDBName);
	}	
	
	public void setFileDB(String fileDBName) {
		fileDB = new File(fileDBName);
		createFile();
	}
	
	public File getFileDB() {
		return this.fileDB;
	}
	
	public boolean appendToFile(String s) {		
		FileOutputStream fos = null;
		PrintWriter out = null;
		try {			
			fos = new FileOutputStream(this.fileDB);
			out = new PrintWriter(fos);
			out.println(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		finally {
		    out.close();
		    try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}

	public List<String> readAllFile() {
		BufferedReader is = null;
		List<String> list = new ArrayList<String>();
		String l;
		try {
			is = new BufferedReader(new FileReader(this.fileDB));
			while ((l = is.readLine()) != null) {
				list.add(l);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private void createFile() {
		if ( !fileDB.exists() ) {
			try {
				fileDB.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
