package mapreduceio;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;

import warc.WARCRecord;

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
