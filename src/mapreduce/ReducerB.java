package mapreduce;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


import warc.WARCRecord;

public class ReducerB extends Reducer<Text, WARCRecord, Text, Text>{
	
	private static Log LOG = LogFactory.getLog(ReducerB.class);

	
	
	Text outputkey;
	Text outputvalue;
	StringBuffer buffer;
	String AppendOutputKey = "TARGET URL: ";
	
	@Override
	public void reduce(Text key, Iterable<WARCRecord> values, 
			Context context) throws IOException, InterruptedException{
		
		System.out.println("Inside Reducer");
		
		buffer = new StringBuffer();
		
		AppendOutputKey = AppendOutputKey + key.toString();
		
		
		try{
			for (WARCRecord val : values){
				
				buffer.append("\n");
				buffer.append(val.toString());
			
			}
			
		outputvalue = new Text(buffer.toString());
		outputkey = new Text(AppendOutputKey);
		context.write(outputkey, outputvalue);
		
		System.out.println("Reducer Complete");
		}
		catch (Exception e){
			
		}
	}

	
}
