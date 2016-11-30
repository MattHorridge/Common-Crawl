package functionality;

import org.apache.commons.*;

import java.io.*;
import java.util.regex.*;
import java.util.*;



public class TestCode {
	
	File file; 
	InputStream in;
	
	
	
	
	public TestCode() throws FileNotFoundException{
		
		file = new File("/Volumes/MATT WORK/Work/Common Crawl Knows it All/MainProject/test.warc");
		in = new FileInputStream("/Volumes/MATT WORK/Work/Common Crawl Knows it All/MainProject/test.warc");
		
	}

	
	
	
	public void GetDataTest() throws Exception{
		
		String WARCResponseString = "WARC-Type: response";
		String RegexRecordEnd = "Connection: close";
		String regexblank = "^\\s";
		
		Pattern WARCResponsePattern = Pattern.compile(WARCResponseString);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String thisLine;
		String nextLine;
	try{
		while((thisLine = reader.readLine())!= null){
			Matcher ResponseMatch = WARCResponsePattern.matcher(thisLine);
			
			if(ResponseMatch.find()){
				System.out.println("===============================================================");
				System.out.println(thisLine);
				while(!(nextLine = reader.readLine()).trim().equals(RegexRecordEnd)){
					
					if(nextLine.trim().equals(""))
					{
					
					}
					else{
						System.out.println(nextLine);
					}
					
				}
			}
			else
			{
				
			}
			
		}
		
		
		
		
	}
	catch(Exception e){}
	}
	
	
	
	
}
