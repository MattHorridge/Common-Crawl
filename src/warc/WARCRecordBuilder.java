package warc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import ziptools.SegmentExtractor;

/* Class to Read a specific WARC File 
 * To Generate a series of WARC - Response Records
 */



/**
 * 
 * @author matthorridge
 * Main Class to build Response WARC Records
 * Implements WARC Details for parsing
 */


public class WARCRecordBuilder implements WARCFormatDetails{

	private File file;
	private InputStream inputStream;
	private BufferedReader filereader;
	private Reader decoder;
	private Pattern WARCTypePattern;
	private String TypeString;
	private URL target;
	private Matcher TypeMatch;
	private SegmentExtractor extractor;
	public static enum streamType {FILE, GZIP, BYTE};
	
	/*
	 * Constructor
	 */
	
	
	public WARCRecordBuilder(){
		//
		extractor = new SegmentExtractor("testUserLinux");
	}
	
	
	
	public WARCRecordBuilder(File fileIn) throws FileNotFoundException{
		file = fileIn;
		inputStream = new FileInputStream(fileIn);
		filereader = new BufferedReader(new InputStreamReader(inputStream));
	}
	
	
	public void openStream(streamType type, Object input) throws IOException, FileNotFoundException{
		
		switch(type)
		{
		case FILE: inputStream = new FileInputStream((String) input);
		break;
		case GZIP: inputStream = new GZIPInputStream( (InputStream) input);
		decoder = new InputStreamReader(inputStream, extractor.getEncoding());
		filereader = new BufferedReader(decoder);
		break;
		default:
			break;
		}
	
	}
	
	/**
	 * Method buildrecords
	 * @return WARCRecord Object
	 * @throws Exception
	 * 
	 * 
	 * Primary method to parse WARC input filestream
	 * And produce a WARC record Object
	 * 
	 */
	
	public List<WARCRecord> buildRecords(String type, BufferedReader reader) throws Exception{
		
		String currentLine;
		String nextLine;
		TypeString = WARC_TYPE + ": " + type;
		
		WARCTypePattern = Pattern.compile(TypeString); //This is the start of the desired record 
		
		List<WARCRecord> RecordList = new ArrayList<WARCRecord>();
		
		
		try{
			while((currentLine = reader.readLine())!= null){
				TypeMatch = WARCTypePattern.matcher(currentLine);
			
				
				//If there is a WARC Record of type required
				if(TypeMatch.find()){
					//TODO: CREATE NEW WARCRecord
					
					Map<String, String> RecordHeaders = new HashMap<String, String>();
					List<String> ContentBlock = new ArrayList<String>();
					WARCRecord Record = new WARCRecord();
					
					
					while(!(nextLine = reader.readLine()).trim().equals(REGEX_RECORD_END)){
							Matcher PatternMatcher = WARC_RECORD_START_PATTERN.matcher(nextLine);
							Matcher contentTypeMatcher = WARC_CONTENT_TYPE_PATTERN.matcher(nextLine);
							Matcher CLengthMatcher = WARC_CONTENT_LENGTH_PATTERN.matcher(nextLine);
							
							if (PatternMatcher.find()){
								RecordHeaders.put(PatternMatcher.group(1), PatternMatcher.group(2));	
							}
							else if (contentTypeMatcher.find()){
								RecordHeaders.put(contentTypeMatcher.group(1), contentTypeMatcher.group(2));;
							}
							else if (CLengthMatcher.find()){
								RecordHeaders.put(CLengthMatcher.group(1), CLengthMatcher.group(2));
							}
							else
							{
								ContentBlock.add(nextLine);
							}
							
					}
					
					//Set data and add record to list
					Record.setHeaders(RecordHeaders);
					Record.setContentBlock(ContentBlock);
					RecordList.add(Record);
					
				}
				
			}
		}
		catch(Exception e)
		{			
		}
		return RecordList;
		
	}
	
	
	
	public WARCRecord testRecords3(String type, BufferedReader reader){
		
		
		String currentLine;
		String nextLine;
		TypeString = WARC_TYPE + ": " + type;
		
		WARCTypePattern = Pattern.compile(TypeString); //This is the start of the desired record 
		
		List<WARCRecord> RecordList = new ArrayList<WARCRecord>();
		
		
		//"http://0.tqn.com/6/g/candleandsoap/b/rss2.xml"
		//"http://www.poo.com"
		
		String URLtest = "http://0.tqn.com/6/g/candleandsoap/b/rss2.xml";
		
		Pattern URLpattern = Pattern.compile(URLtest);
		
		
		//create Record Object
		WARCRecord Record = new WARCRecord();
		
		//create map
		Map<String, String> Headers = new HashMap<String,String>();
		List<String> contentBlock = new ArrayList<String>();
	
		
		try{
			while((currentLine = reader.readLine())!= null){
				TypeMatch = WARCTypePattern.matcher(currentLine);
		
				//If there is a WARC Record of type required
				if(TypeMatch.find()){
					//TODO: Found Bug - need exit loop
					//System.out.println(" ");
					//System.out.println(currentLine);
					
					//Map<String, String> RecordHeaders = new HashMap<String, String>();
					//List<String> ContentBlock = new ArrayList<String>();
					//WARCRecord Record = new WARCRecord();
					
					//TODO: THis check doesnt work - FIX BUG
					//while(!(nextLine = reader.readLine()).trim().equals(REGEX_RECORD_END)){
					int count = 0;
					while(!(nextLine = reader.readLine()).trim().equals("kkktestkkkk")){
							
							//System.out.println(nextLine);
							Matcher PatternMatcher = WARC_RECORD_START_PATTERN.matcher(nextLine);
							Matcher contentTypeMatcher = WARC_CONTENT_TYPE_PATTERN.matcher(nextLine);
							Matcher CLengthMatcher = WARC_CONTENT_LENGTH_PATTERN.matcher(nextLine);
							Matcher URLsplitMatcher = WARC_URI_SPLIT_PATTERN.matcher(nextLine);
							
							if(count == 2){
								count = 0;
								break;
							}
							
							else if(URLsplitMatcher.find()){
								if(URLsplitMatcher.group(2).toLowerCase().equals(URLtest)){
								System.out.println("FOUND IT");
								System.out.println(nextLine);
								Headers.put(URLsplitMatcher.group(1), URLsplitMatcher.group(2));
								}
								else
									break;	
							}
							else if(nextLine.trim().isEmpty()){
								count++;
							}
							else if (PatternMatcher.find()){
								Headers.put(PatternMatcher.group(1), PatternMatcher.group(2));	
								//System.out.println(PatternMatcher.group(1).toString() + " " + PatternMatcher.group(2).toString());
							}
							else if (contentTypeMatcher.find()){
								Headers.put(contentTypeMatcher.group(1), contentTypeMatcher.group(2));;
								//System.out.println(contentTypeMatcher.group(1).toString() + " " + contentTypeMatcher.group(2).toString());
							}
							else if (CLengthMatcher.find()){
								Headers.put(CLengthMatcher.group(1), CLengthMatcher.group(2));
								//System.out.println(CLengthMatcher.group(1).toString() + " " + CLengthMatcher.group(2).toString());
							}
							else 
							{
								contentBlock.add(nextLine);
								//System.out.println(nextLine);
							}	
					}	
				}
			}
		}
		catch(Exception e)
		{			
		}
		Record.setHeaders(Headers);
		Record.setContentBlock(contentBlock);
	
		return Record;
	}
	
	
	public BufferedReader getFilereader() {
		return filereader;
	}

	public void setFilereader(BufferedReader filereader) {
		this.filereader = filereader;
	}

	public void setFile(File f){
		file = f;
	}
	
	public File getFile() {
		return file;
	}
	
	public InputStream getIn() {
		return inputStream;
	}

	public void setIn(InputStream in) {
		this.inputStream = in;
	}
	
	public SegmentExtractor getSegmentExtractor(){
		return extractor;
	}
}
