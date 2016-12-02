package WARC;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



public class WARCRecord implements WARCFormatDetails {
	

	protected WARCType type;
	protected int ContentLength;
	protected String TargetURI;
	protected List ContentBlock; //To hold unordered
	protected Map<String, String> Headers;

	
	
	//Need to get Content block

	
	
	//blank constructor
	public WARCRecord(){
		
		//Not sure how to organise ContentBlock
		//No consistent pattern 
		ContentBlock = new ArrayList<String>();
		Headers = new HashMap<String, String>();
		
		
		
		
	}
	
	
	public WARCRecord(InputStream in){

		//Not sure how to organise ContentBlock
		//No consistent pattern 
		ContentBlock = new ArrayList<String>();
		
	}
	

	public int getContentLength() {
		return ContentLength;
	}


	public void setContentLength(int contentLength) {
		ContentLength = contentLength;
	}


	public String getTargetURI() {
		return TargetURI;
	}


	public void setTargetURI(String targetURI) {
		TargetURI = targetURI;
	}


	public List getContentBlock() {
		return ContentBlock;
	}



	

	
	
	
	
	

}
