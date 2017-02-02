package ziptools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Class for the extraction of gzipped WARC files
//TODO: Text locally


public class SegmentExtractor {

	private String segmentAddress;
	private String encoding = "UTF-8"; //default encoding follows that of WARC encoding
	private InputStream fileStream, gzipStream;
	private BufferedReader segmentReader;
	private Reader reader;
	private String line;
	private Matcher TypeMatch;
	
	
	
	//Constructor
	public SegmentExtractor(String address){
		
		segmentAddress = address;
	}
	
	public SegmentExtractor(String address, String Encoding) throws IOException{
		
		segmentAddress = address;
		encoding = Encoding;
		fileStream = new FileInputStream(segmentAddress);
		gzipStream = new GZIPInputStream(fileStream);
		
		
		Reader decoder = new InputStreamReader(gzipStream, encoding);
		
		segmentReader = new BufferedReader(decoder);
	}
	
	
	public BufferedReader getsegmentReader(){
		
		//TODO:Set up Gzipstream, file stream, buffered reader
		
		
		return null;
		
	}
	
	public void printReader(){
		
		String nextLine;
		String TypeString = "WARC-Type: response";
		
		Pattern typePattern = Pattern.compile(TypeString);
		
		try {
			while ((line = segmentReader.readLine())!= null){
				TypeMatch = typePattern.matcher(line);
				
				//System.out.println(line);
				
				if(TypeMatch.find()){
					System.out.println(line);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
