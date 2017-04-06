package main.mapreduceMain;



import main.mapreduceio.WARCRecordArrayWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class ListRecordReducer extends Reducer<Text, WARCRecordArrayWritable, Text, Text>  {

	private Text outputkey, outputvalue;
	private StringBuffer buffer;
	private String AppendOutputKey;
	private static final Log LOG = LogFactory.getLog(ListRecordReducer.class);
	
	@Override
	public void reduce(Text key, Iterable<WARCRecordArrayWritable> values, 
			Context context) throws IOException, InterruptedException{
		
		LOG.info("Trying to List Reduce");;
		
		buffer = new StringBuffer();
		AppendOutputKey = "TARGET URL: " + key.toString();
	
		try{
			for (WARCRecordArrayWritable val : values){
				for(int i = 0; i < val.get().length; i++){
				buffer.append("\n");
				buffer.append(val.get()[i].toString());
				buffer.append("\n");
				}
			}
		
			buffer.append("\n");
			outputvalue = new Text(buffer.toString());
			outputkey = new Text(AppendOutputKey);
			context.write(outputkey, outputvalue);
			LOG.info("Reducer Complete");
		}
		
		catch (InterruptedException e){
			StringWriter errors = new StringWriter();
			errors.append(e.getMessage());
			errors.append(e.getCause().toString());
			errors.append(e.getStackTrace().toString());
			LOG.info("Interrupted Exception");
			LOG.info(errors);
		}
		
		catch (Exception e){
			StringWriter errors = new StringWriter();
			errors.append(e.getMessage());
			errors.append(e.getCause().toString());
			errors.append(e.getStackTrace().toString());
			
			LOG.info("Interrupted Exception");
			LOG.info(errors);
		}
		
	}

	
	
	
	
}
