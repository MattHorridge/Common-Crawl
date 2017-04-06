package main.mapreduceio;

import main.warc.WARCRecord;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;

public class WARCRecordArrayWritable extends ArrayWritable{

	public WARCRecordArrayWritable(WARCRecord[] values) {
		super(WARCRecord.class, values);
		// TODO Auto-generated constructor stub
	}
	
	public WARCRecordArrayWritable(){
		super(WARCRecord.class);
	}
	
	
	@Override
    public Writable[] get() {
        return super.get();
    }


}
