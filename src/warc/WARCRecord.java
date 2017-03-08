package warc;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.hadoop.io.Writable;


public class WARCRecord implements WARCFormatDetails, Writable {
	

	protected WARCType type;
	protected List ContentBlock; //To hold unordered HTTP header information
	protected Map<String, String> Headers;
	
	
	/*
	 * Blank Constructor
	 */
	public WARCRecord(){
		
	}
	

	public WARCType getType() {
		return type;
	}


	public void setType(WARCType type) {
		this.type = type;
	}


	public Map<String, String> getHeaders() {
		return Headers;
	}


	public void setHeaders(Map<String, String> headers) {
		Headers = headers;
	}


	public void setContentBlock(List contentBlock) {
		ContentBlock = contentBlock;
	}

	public List getContentBlock() {
		return ContentBlock;
	}


	/**
	 * TODO: FIX THESE
	 */
	
	
	
	public void readFields(DataInput arg0) throws IOException {
		// TODO FINISH THIS
		
		
		
	}


	//Method to serialize the data for MR
	
	public void write(DataOutput output) throws IOException {
		// TODO Auto-generated method stub
		
		output.writeUTF(type.toString());//type\
		
		//List Iterator
		Iterator ListIterator = ContentBlock.listIterator();
		Iterator<Entry<String, String>> HeaderIterator = Headers.entrySet().iterator();
		
		while (HeaderIterator.hasNext()) {
	        Entry<String, String> thisEntry = HeaderIterator.next();
	        output.writeUTF(thisEntry.getKey());
	        output.writeUTF(thisEntry.getValue());
	      }
		
		while(ListIterator.hasNext()){
			Object e = ListIterator.next();
			output.writeUTF(e.toString());
		}
		
		
	}



	

	
	
	
	
	

}
