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




import mapreduceio.WARCRecordArrayWritable;

import com.amazonaws.auth.AWSCredentials;

import awstools.SegmentExtractor;

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
	public static enum streamType {FILE, GZIP, BYTE};
	
	/*
	 * Constructor
	 */
	
	public WARCRecordBuilder(){
		
	}
	
	
	public WARCRecordBuilder(AWSCredentials Creds){
		//
		extractor = new SegmentExtractor(creds);
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
		
		WARCTypePattern = Pattern.compile(TypeString); //This is the start of the desired record 		
		
		while((currentLine = reader.readLine())!= null){
			TypeMatch = WARCTypePattern.matcher(currentLine);
		
		
			if(TypeMatch.find()){
				
				reader.mark(2000);
				
				WARCRecord r = this.extractRecord(reader, target);
				
				if (r != null){
					
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
	
	
	public List<WARCRecord> buildRecords(String type, BufferedReader reader, String target) throws IOException{
		
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
		reader.close();
		return wList.toArray(new WARCRecord[wList.size()]);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private WARCRecord extractRecord(BufferedReader reader, String target) throws IOException{
		

		String nextLine;
		int breaks = 0;

		Map<String, String> Headers = new HashMap<String,String>();
		List<String> contentBlock = new ArrayList<String>();
		WARCRecord record = new WARCRecord();
		
		while((nextLine = reader.readLine())!= "End Of Records"){
			
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
				//System.out.println(nextLine);
				if(URLsplitMatcher.group(2).toLowerCase().contains(target)){
				System.out.println("FOUND IT");
				System.out.println(nextLine);
				Headers.put(URLsplitMatcher.group(1), URLsplitMatcher.group(2));

				}
				else
					{
						//System.out.println("readOn returning null");
						return null;
					}	
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
				//System.out.println(nextLine);
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
	
	private AWSCredentials getCreds(){
		return creds;
	}

}
