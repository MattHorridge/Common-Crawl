package main.warc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import main.awstools.GZIPWrapper.GZIPWrapperInputStream;
import main.awstools.SegmentExtractor;
import main.awstools.s3Access;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.model.S3Object;

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
	private Matcher TypeMatch;
	private SegmentExtractor extractor;
	private AWSCredentials creds;
	private s3Access pipeLine;
	public static enum streamType {FILE, GZIP, BYTE};
	private String encoding;
	private GZIPWrapperInputStream avInputStream;
	/*
	 * Constructor
	 */
	
	public WARCRecordBuilder(){
		
	}
	
	
	public WARCRecordBuilder(AWSCredentials Creds){
		//
		extractor = new SegmentExtractor(creds);
		pipeLine = new s3Access(creds);
		encoding = "UTF-8"; //Default format for project is UTF-8 
	}
	

	public WARCRecordBuilder(File fileIn) throws FileNotFoundException{
		file = fileIn;
		inputStream = new FileInputStream(fileIn);
		filereader = new BufferedReader(new InputStreamReader(inputStream));
	}

	
	public void openStream(streamType type, Object input) throws IOException, FileNotFoundException{
		
		switch(type)
		{
		case FILE: inputStream = new FileInputStream((File) input);
		break;
		case GZIP: avInputStream = new GZIPWrapperInputStream((InputStream) input);
		inputStream = new GZIPInputStream(avInputStream);
		decoder = new InputStreamReader(inputStream, extractor.getEncoding());
		filereader = new BufferedReader(decoder);
		break;
		default:
			break;
		}
	
	}
	
	public void closeStream() throws IOException{
		this.getavInputStream().close();
		this.getFilereader().close();
	}
	
	/**
	 * Method buildRecord
	 * @return WARCRecord Object
	 * @throws Exception
	 * 
	 * 
	 * Primary method to parse WARC input filestream
	 * And produce a WARC record Object
	 * 
	 */

	
	public WARCRecord buildSingleRecord(String type, BufferedReader reader, String target) throws IOException{
	
		String currentLine;
		String TypeString = WARC_TYPE + ": " + type;
		target = target.toLowerCase();
		
		WARCTypePattern = Pattern.compile(TypeString); //This is the start of the desired record 		
		
		while((currentLine = reader.readLine())!= null){
			TypeMatch = WARCTypePattern.matcher(currentLine);
		
			if(TypeMatch.find()){
				
				reader.mark(2000);
				
				WARCRecord r = this.extractRecord(reader, target);
				
				if (r != null){
					r.setType(type);
					reader.reset();
					return r;
					
				}
				else 
					reader.reset();
					continue;
			
			}
			else
				continue;
			
		}
	
		return null;
	}
	
	//No Longer Needed
	/*
	public List<WARCRecord> buildRecordsList(String type, BufferedReader reader, String target) throws IOException{
		
		String currentLine;
		String TypeString = WARC_TYPE + ": " + type;
		List<WARCRecord> wList = new ArrayList<WARCRecord>();
		
		WARCTypePattern = Pattern.compile(TypeString); //This is the start of the desired record 		
		
		while((currentLine = reader.readLine())!= null){
			TypeMatch = WARCTypePattern.matcher(currentLine);
		
		
			if(TypeMatch.find()){
				
				//System.out.println(currentLine);
				reader.mark(2000);
				
				WARCRecord r = this.extractRecord(reader, target);
				
				if (r != null){
					r.setType(type);
					reader.reset();
					wList.add(r);
					
				}
				else 
					reader.reset();
					continue;
			
			}
			else
				continue;
			
		}
	
		return wList;
	}
	*/
	
	public WARCRecord[] buildRecordsArray(String type, BufferedReader reader, String target) throws IOException{
		
		String currentLine;
		String TypeString = WARC_TYPE + ": " + type;
		List<WARCRecord> wList = new ArrayList<WARCRecord>();
		WARCTypePattern = Pattern.compile(TypeString); //This is the start of the desired record 		
		
		
		while((currentLine = reader.readLine())!= null){
			TypeMatch = WARCTypePattern.matcher(currentLine);
			
		
			if(TypeMatch.find()){
				
				//System.out.println(currentLine);
				reader.mark(2000);
				WARCRecord r = this.extractRecord(reader, target);
				
				
				if (r != null){
					r.setType(type);
					r.setURL(target);
					wList.add(r);			
				}
				else 
					reader.reset();
					continue;
			}
			continue;
		}
		
		
		return wList.toArray(new WARCRecord[wList.size()]);
	}
	
	
	public WARCRecord[] buildRecordsArray(String type, BufferedReader reader, String target[]) throws IOException{
		
		String currentLine;
		String TypeString = WARC_TYPE + ": " + type;
		List<WARCRecord> wList = new ArrayList<WARCRecord>();
		WARCTypePattern = Pattern.compile(TypeString); //This is the start of the desired record 		
		
		while((currentLine = reader.readLine())!= null){
			TypeMatch = WARCTypePattern.matcher(currentLine);
			
			if(TypeMatch.find()){
				
			
				reader.mark(2000);
				WARCRecord r = this.extractRecord(reader, target);
				
				
				if (r != null){
					r.setType(type);
					wList.add(r);			
				}
				else 
					reader.reset();
					continue;
			}
			continue;
		}
		
		
		return wList.toArray(new WARCRecord[wList.size()]);
	}
	

	private WARCRecord extractRecord(BufferedReader reader, String[] target) throws IOException{
		
		String nextLine;
		int breaks = 0;
		Map<String, String> Headers = new HashMap<String,String>();
		List<String> contentBlock = new ArrayList<String>();
		WARCRecord record = new WARCRecord();
		String URL = null;
		
		while((nextLine = reader.readLine())!= null){
			
			Matcher PatternMatcher = WARC_RECORD_START_PATTERN.matcher(nextLine);
			Matcher contentTypeMatcher = WARC_CONTENT_TYPE_PATTERN.matcher(nextLine);
			Matcher CLengthMatcher = WARC_CONTENT_LENGTH_PATTERN.matcher(nextLine);
			Matcher URLsplitMatcher = WARC_URI_SPLIT_PATTERN.matcher(nextLine);
			
			if(breaks > 1)
			{
				breaks = 0;
				break;
				
			}
			
			if (nextLine.trim().equals("")){
				breaks++;
			}
			

			if(URLsplitMatcher.find()){
				
				boolean inlist = false;
				for (int i = 0; i< target.length; i++)
					{
						if(URLsplitMatcher.group(2).toLowerCase().contains(target[i])){
							System.out.println("FOUND IT");
							System.out.println(nextLine);
							Headers.put(URLsplitMatcher.group(1), URLsplitMatcher.group(2));
							URL = target[i];
							inlist = true;
							break;
						}
						else 
							continue;
				
					}
				
				if (!inlist){
					return null; //none of the urls in the list match
				}
			
					

			}
			
			else if (PatternMatcher.find()){
				Headers.put(PatternMatcher.group(1), PatternMatcher.group(2));	
			}
			else if (contentTypeMatcher.find()){
				Headers.put(contentTypeMatcher.group(1), contentTypeMatcher.group(2));;
			}
			else if (CLengthMatcher.find()){
				Headers.put(CLengthMatcher.group(1), CLengthMatcher.group(2));
			}
			
			else
			{
				contentBlock.add(nextLine);
			}
			
		}
		
		record.setHeaders(Headers);
		record.setContentBlock(contentBlock);
		record.setURL(URL);
		
		return record;
	}
	
	
	private WARCRecord extractRecord(BufferedReader reader, String target) throws IOException{
	
		String nextLine;
		int breaks = 0;
		Map<String, String> Headers = new HashMap<String,String>();
		List<String> contentBlock = new ArrayList<String>();
		WARCRecord record = new WARCRecord();
		
		while((nextLine = reader.readLine())!= null){
			
			Matcher PatternMatcher = WARC_RECORD_START_PATTERN.matcher(nextLine);
			Matcher contentTypeMatcher = WARC_CONTENT_TYPE_PATTERN.matcher(nextLine);
			Matcher CLengthMatcher = WARC_CONTENT_LENGTH_PATTERN.matcher(nextLine);
			Matcher URLsplitMatcher = WARC_URI_SPLIT_PATTERN.matcher(nextLine);
			
			if(breaks > 1)
			{
				breaks = 0;
				break;
				
			}
			
			if (nextLine.trim().equals("")){
				breaks++;
			}
			

			if(URLsplitMatcher.find()){
				if(URLsplitMatcher.group(2).toLowerCase().contains(target)){
				System.out.println("FOUND IT");
				System.out.println(nextLine);
				Headers.put(URLsplitMatcher.group(1), URLsplitMatcher.group(2));

				}
				else
					{
						return null;
					}	
			}
			
			else if (PatternMatcher.find()){
				Headers.put(PatternMatcher.group(1), PatternMatcher.group(2));	
			}
			else if (contentTypeMatcher.find()){
				Headers.put(contentTypeMatcher.group(1), contentTypeMatcher.group(2));;
			}
			else if (CLengthMatcher.find()){
				Headers.put(CLengthMatcher.group(1), CLengthMatcher.group(2));
			}
			
			else
			{
				contentBlock.add(nextLine);
			}
			
		}
		
		record.setHeaders(Headers);
		record.setContentBlock(contentBlock);

		
		return record;
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

	public void setCreds(AWSCredentials credentials){
		creds = credentials;
	}
	
	private InputStream getavInputStream(){
		return avInputStream;
	}

}
