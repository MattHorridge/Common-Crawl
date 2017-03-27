package mapreduce;



import mapreduceio.WARCRecordArrayWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;

import warc.WARCRecord;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ListRecordReducer extends Reducer<Text, WARCRecordArrayWritable, Text, Text>  {

	private Text outputkey;
	private Text outputvalue;
	private StringBuffer buffer;
	private String AppendOutputKey;
	
	@Override
	public void reduce(Text key, Iterable<WARCRecordArrayWritable> values, 
			Context context) throws IOException, InterruptedException{
		
		System.out.println("Inside Reducer");
		
		buffer = new StringBuffer();
		AppendOutputKey = "TARGET URL: " + key.toString();
		
		
		
		try{
			for (WARCRecordArrayWritable val : values){
				
				
			
				
				for(int i = 0; i < val.get().length; i++){
					
				buffer.append("\n");
				//System.out.println(val.get()[i].toString());
				buffer.append(val.get()[i].toString());
				buffer.append("\n");
				}
			}
		
		buffer.append("\n");
		
		
		
		outputvalue = new Text(buffer.toString());
		
		
		
		outputkey = new Text(AppendOutputKey);
		context.write(outputkey, outputvalue);
		System.out.println("Reducer Complete");
		}
		
		catch (Exception e){
		
		
		}
	}

	
	
	
	
}
