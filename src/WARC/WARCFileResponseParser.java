package WARC;

import java.io.BufferedReader;
import java.io.FileReader;
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
	
	

}
