package main.mapreduceMain;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import main.warc.WARCRecord;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MultipleURLRecordReducer extends Reducer<Text, WARCRecord, Text, Text>{
	
	private static final Log LOG = LogFactory.getLog(MultipleURLRecordReducer.class);

	private Text outputkey;
	private Text outputvalue;
	private StringBuffer buffer;
	private String AppendOutputKey; 
	
	@Override
	public void reduce(Text key, Iterable<WARCRecord> values, 
			Context context) throws IOException, InterruptedException{
		
		LOG.info("Inside Reducer");
		
		buffer = new StringBuffer();
		AppendOutputKey = "TARGET URL: " + key.toString();
		
		WARCRecord Comparator = values.iterator().next();
		buffer.append(Comparator.toString());
		try{
			for (WARCRecord val : values){
				if (Comparator.compareTo(val) == 1){//If its not the same header
					buffer.append("\n");
					buffer.append(val.toString());
					buffer.append("\n");
				}
			}
		
		buffer.append("\n");
		
		outputvalue = new Text(buffer.toString());
		outputkey = new Text(AppendOutputKey);
		context.write(outputkey, outputvalue);
		}
		
		catch (InterruptedException e){
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LOG.info("Interrupted Exception");
			LOG.info(errors);
		}
		
		catch (Exception e){
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LOG.info("Interrupted Exception");
			LOG.info(errors);
		}
	}

	
}
