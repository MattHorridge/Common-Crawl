package WARC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * 
 * @author matthorridge
 * Main Class to build Response WARC Records
 * Implements WARC Details for parsing
 */


public class WARCRecordBuilder implements WARCFormatDetails{

	
	
	private File file;
	private InputStream in;
	private BufferedReader reader;
	private Pattern WARCPattern;
	private String TypeString;
	private Matcher TypeMatch;
	
	
	
	/**
	 * Constructor
	 */
	
	
	public WARCRecordBuilder(){
		
		
	}
	
	
	
	public WARCRecordBuilder(File fileIn) throws FileNotFoundException{
		
		file = fileIn;
		
		in = new FileInputStream(fileIn);
		reader = new BufferedReader(new InputStreamReader(in));
	}
	
	
	
	
	/**
	 * Method buildResponseRecord
	 * @return WARCRecord Object
	 * @throws Exception
	 * 
	 * 
	 * Primary method to parse WARC input filestream
	 * And produce a WARC record Object
	 * 
	 */
	
	
	public WARCRecord buildRecord(String type) throws Exception{
		
		String currentLine;
		String nextLine;
		TypeString = WARC_TYPE + ": " + type;
		WARCPattern = Pattern.compile(TypeString); //This what the reader looks for
		
		
		try{
			while((currentLine = reader.readLine())!= null){
				TypeMatch = WARCPattern.matcher(currentLine);
				
				if(TypeMatch.find()){
					//TODO: CREATE NEW WARCRecord
				
					while(!(nextLine = reader.readLine()).trim().equals(REGEX_RECORD_END)){
						
						
						String lookbehindtest = "(?<=WARC-Date:\\s)((.|\n)+)";
						Pattern lookb = Pattern.compile(lookbehindtest);
						Matcher matchytest = lookb.matcher(nextLine);
						
						
						//for (int i = 0; i <= 15; i++){
							if(matchytest.find()){
								System.out.println(matchytest.group());
							//}
							
						}
						
					
					}	
					
					
					
				}
				
			}
			
			
		}
		
		catch(Exception e){}
		
		
		
		
		
		
		return null;
	}



	public File getFile() {
		return file;
	}



	public void setFile(File file) {
		this.file = file;
	}



	public InputStream getIn() {
		return in;
	}



	public void setIn(InputStream in) {
		this.in = in;
	}
	
	
	
	
	
	
	
	

}
