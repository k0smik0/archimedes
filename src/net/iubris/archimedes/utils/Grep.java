package net.iubris.archimedes.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

	
public class Grep {
	
	public static boolean process(String patternString, File file) {
		boolean result = false;
		
		Pattern pattern = Pattern.compile(patternString);
		Matcher m = null; 
		
	    // Get a FileChannel from the given file.
	    FileChannel fc;
	    CharBuffer cbuf = null;
		try {
			fc = new FileInputStream(file).getChannel();
		    // Map the file's content
		    ByteBuffer buf = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

		    // Decode ByteBuffer into CharBuffer
		    cbuf = Charset.forName("UTF-8").newDecoder().decode(buf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	    m = pattern.matcher(cbuf);
	    while (m.find()) {
	    	result = true;
	      //System.out.println(m.group(0));
	    }
	    return result;		
	}
	
	public static boolean process(String patternName, String fileName) throws IOException {
		return process(patternName, new File(fileName));		
	  }	
	
}
