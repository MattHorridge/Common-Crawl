package WARC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private Pattern WARCTypePattern;
	private String TypeString;
	private Matcher TypeMatch;
	private List HeaderList;

	
	/**
	 * Constructor
	 */
	
	
	public WARCRecordBuilder(){
		
		
	}
	
	
	
	public WARCRecordBuilder(File fileIn) throws FileNotFoundException{
		
		file = fileIn;
		in = new FileInputStream(fileIn);
		reader = new BufferedReader(new InputStreamReader(in));
		HeaderList = new ArrayList<String>(Arrays.asList(HeaderFields));
		
		
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
		System.out.println(TypeString);
		WARCTypePattern = Pattern.compile(TypeString); //This what the reader looks for
		WARCRecord Record = new WARCRecord();
		Map<String, String> RecordHeaders = new HashMap<String, String>();
		List ContentBlock = new ArrayList<String>();
		
		
		try{
			while((currentLine = reader.readLine())!= null){
				TypeMatch = WARCTypePattern.matcher(currentLine);
				
				//If there is a WARC Record of type required
				if(TypeMatch.find()){
					//TODO: CREATE NEW WARCRecord
				
					while(!(nextLine = reader.readLine()).trim().equals(REGEX_RECORD_END)){
						
						
						//STORE IN MAP IN WARC RECORD
								
							//REGEX_WARC
							//REGEX_WARC_PATTERN
							
							Matcher PatternMatcher = WARC_MATCH_PATTERN.matcher(nextLine);
							Matcher CTypeMatcher = WARC_CONTENT_TYPE_PATTERN.matcher(nextLine);
							Matcher CLengthMatcher = WARC_CONTENT_LENGTH_PATTERN.matcher(nextLine);
							
							
							
							
							if (PatternMatcher.find()){
								RecordHeaders.put(PatternMatcher.group(1), PatternMatcher.group(2));	
							}
							else if (CTypeMatcher.find()){
								RecordHeaders.put(CTypeMatcher.group(1), CTypeMatcher.group(2));;
							}
							else if (CLengthMatcher.find()){
								RecordHeaders.put(CLengthMatcher.group(1), CLengthMatcher.group(2));
							}
						
					}	
				}
			}
		}
		catch(Exception e)
		{			
		}
		
		
		Record.setHeaders(RecordHeaders);
		
		return Record;
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
