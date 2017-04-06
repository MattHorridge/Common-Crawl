package main.warc;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.hadoop.io.Writable;


public class WARCRecord implements WARCFormatDetails, Writable {
	
	protected List<String> ContentBlock; //To hold unordered HTTP header information
	protected Map<String, String> Headers;
	protected String recordType;
	protected String URL;
	/*
	 * Blank Constructor
	 */
	public WARCRecord(){
	
	}

	public Map<String, String> getHeaders() {
		return Headers;
	}
	
	public void setURL(String url){
		URL = url;
	}

	public String getURL(){
		return URL;
	}

	public void setHeaders(Map<String, String> headers) {
		Headers = headers;
	}


	public void setContentBlock(List<String> contentBlock) {
		ContentBlock = contentBlock;
	}

	public List<String> getContentBlock() {
		return ContentBlock;
	}

	public String getType(){
		return recordType;
	}
	
	public void setType(String type){
		recordType = type;
	}


	public void readFields(DataInput input) throws IOException {
			
		recordType = input.readUTF();
		int numofdata = input.readInt();
		URL = input.readUTF();
		
		Map<String, String> newHeader = new HashMap<String,String>();
		ArrayList<String> newCBlock = new ArrayList<String>();
		
		//simple loop that sets key value of Header map correctly
		for (int i = 0; i < numofdata; i++){
			String key = input.readUTF().toString();
			String value = input.readUTF().toString();
			newHeader.put(key,value);
		}
		this.setHeaders(newHeader);
		
		int numofcontent = input.readInt();
		
		for (int i = 0; i < numofcontent; i++){
			newCBlock.add(input.readUTF().toString());
		}
		
		this.setContentBlock(newCBlock);
	}


	//Method to serialize the data for MR
	
	public void write(DataOutput output) throws IOException {
				
		//List Iterator
		Iterator<String> ListIterator = ContentBlock.listIterator();
		Iterator<Entry<String, String>> HeaderIterator = Headers.entrySet().iterator();
		
		
		output.writeUTF(recordType);//type\
		output.writeInt(Headers.size());
		output.writeUTF(URL);
		
		while (HeaderIterator.hasNext()) {
	        Entry<String, String> thisEntry = HeaderIterator.next();
	        output.writeUTF(thisEntry.getKey());
	        output.writeUTF(thisEntry.getValue());
	      }
		
		//size of content block
		output.writeInt(ContentBlock.size());
		
		while(ListIterator.hasNext()){
			Object e = ListIterator.next();
			output.writeUTF(e.toString());
		}

	}
	
	
	@Override
	public String toString() {
	  
		Iterator<Entry<String, String>> HeaderIterator = this.getHeaders().entrySet().iterator();
		Iterator<String> listIterator = this.getContentBlock().listIterator();
		StringBuffer b = new StringBuffer();
		
		b.append("\n");
		b.append(this.getType());
		
		while (HeaderIterator.hasNext()) {
	        Entry<String, String> thisEntry = HeaderIterator.next();
	        b.append("\n");
	        b.append(thisEntry.getKey());
	        b.append(thisEntry.getValue());
	      }
		b.append("\n");
		
		
		while(listIterator.hasNext()){
			b.append("\n");
			b.append(listIterator.next().toString());
		}
		
		return b.toString();
	}
}
