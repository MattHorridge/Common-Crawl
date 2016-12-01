package WARC;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author matthorridge
 * Main Class to build Response WARC Records
 * Implements WARC Details for parsing
 */


public class WARCRecordBuilder implements WARCFormatDetails{

	
	
	File file;
	InputStream in;
	
	
	/**
	 * Constructor
	 */
	
	public WARCRecordBuilder(InputStream streamIn, File fileIn){
		
		in = streamIn;
		file = fileIn;
		
	}
	
	
	
	public WARCRecord buildRecord(){
		
		
		return null;
	}
	
	
	
	
	
	
	
	

}
