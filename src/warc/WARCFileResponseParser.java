package warc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Class to Read a specific WARC File 
 * To Generate a series of WARC - Response Records
 */


public class WARCFileResponseParser implements WARCFormatDetails{
	
	String WARCResponseString = "WARC-Type: response";
	String RegexRecordEnd = "Connection: close"; //Defines end of "Content Block" before HTML data
	String regexblank = "^\\s";
	
	//TODO: This method of parsing *seems* a little unsophisticated
	
	Pattern WARCResponsePattern;
	BufferedReader reader;
	
	
	//Constructor
	public WARCFileResponseParser(){
		
		
	}
	
	
	public void ParseWARCResponses(FileInputStream in){
		
		WARCResponsePattern = Pattern.compile(WARCResponseString);
		reader = new BufferedReader(new InputStreamReader(in));
		
		String currentLine;
		String nextLine;
		
		try {
			while((currentLine = reader.readLine())!= null){
				Matcher ResponseMatch = WARCResponsePattern.matcher(currentLine);
				
				if(ResponseMatch.find()){
					//TODO: BUILD WARC Record File of type Response
				}
				
			}
			
		}
		catch(Exception e)
		{
			
			
		}
		
		
		
	}
	

}
