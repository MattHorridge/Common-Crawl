package WARC;

import java.io.InputStream;
import java.util.List;

public class WARCRecord implements WARCFormatDetails {
	

	protected String type;
	protected int ContentLength;
	protected String TargetURI;
	protected List ContentBlock;
	
	//Need to get Content block

	
	
	//blank constructor
	public WARCRecord(){
		
		
	}
	
	
	public WARCRecord(InputStream in){
		
	}
	

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
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
