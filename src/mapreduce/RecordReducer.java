package mapreduce;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


import warc.WARCRecord;

public class RecordReducer extends Reducer<Text, WARCRecord, Text, Text>{
	
	private static final Log LOG = LogFactory.getLog(RecordReducer.class);

	private Text outputkey;
	private Text outputvalue;
	private StringBuffer buffer;
	private String AppendOutputKey; 
	
	@Override
	public void reduce(Text key, Iterable<WARCRecord> values, 
			Context context) throws IOException, InterruptedException{
		
		System.out.println("Inside Reducer");
		
		buffer = new StringBuffer();
		AppendOutputKey = "TARGET URL: " + key.toString();
		
		try{
			for (WARCRecord val : values){
				
				buffer.append("\n");
				buffer.append(val.toString());
				buffer.append("\n");
			
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
