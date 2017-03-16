package mapreduce;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.ArrayWritable;

import warc.WARCRecord;

public class WARCArrayWritable extends ArrayWritable {

	public WARCArrayWritable(WARCRecord[] records) {
		super(WARCRecord.class, records);
	}
	
	public WARCArrayWritable(){
		super(WARCRecord.class);
	}
	
	
	  @Override
	    public WARCRecord[] get() {
	        return (WARCRecord[]) super.get();
	    }
	
	  @Override
	  public String toString() {
	    return Arrays.toString(get());
	  }
	  
	
	  
	  
	
}
