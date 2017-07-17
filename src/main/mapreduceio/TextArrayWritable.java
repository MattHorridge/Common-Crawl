package main.mapreduceio;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class TextArrayWritable extends ArrayWritable {

	public TextArrayWritable(Text[] values){
		super(Text.class, values);
	}
	
	public TextArrayWritable(){
		super(Text.class);
	}
	
	@Override
	public Writable[] get(){
		return super.get();
	}
	
}
