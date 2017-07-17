package main.mapreduceMain;
import main.mapreduceio.WARCRecordArrayWritable;
import main.warc.WARCRecord;
import main.warc.WARCRecordBuilder;
import main.warc.WARCRecordBuilder.streamType;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;



public class SingleURLRecordMapper extends Mapper<LongWritable, Text, Text, WARCRecordArrayWritable> {

	private static final Log LOG = LogFactory.getLog(SingleURLRecordMapper.class);
	private static AWSCredentials creds;
	private WARCRecord[] RecordList;
	private static String targetURL;
	
	 
	protected void setup(Context context)throws IOException, InterruptedException, AmazonClientException{
		
		Configuration config = context.getConfiguration();
		
		targetURL = config.get("URL");
		creds = null;
		
		try {
			creds = new BasicAWSCredentials(config.get("inputKey"), config.get("inputSecret"));
		}
		catch(AmazonClientException e){ 
			LOG.info("Amazon Client Exception - Correct Credentials not correctly entered or were wrong");
			LOG.info(e.getCause());
			LOG.info(e.getMessage());
			return;
		}
		
	}
	
	
	
	
	@Override
	public void map(LongWritable line, Text SegmentName, Context context){
	
		LOG.info("Trying to List Map");
	
		try{
			
			WARCRecordBuilder RBuilder = new WARCRecordBuilder(creds);	
			RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("commoncrawl", SegmentName.toString()).getObjectContent());
			RecordList =  RBuilder.buildRecordsArray("response", RBuilder.getFilereader(), targetURL);
			RBuilder.closeStream();
			//output
			
			if (RecordList == null){
				LOG.info("mapper finished");
				LOG.info("Desired URL Not Found");
				return;
			}
		
	
			context.write(new Text(targetURL), new WARCRecordArrayWritable(RecordList));
			LOG.info("mapper finished");
		}
		
		
		catch(FileNotFoundException f){
			StringWriter errors = new StringWriter();
			f.printStackTrace(new PrintWriter(errors));
			LOG.info("IO Exception");
			LOG.info(errors);
		}
		catch(IOException e){
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LOG.info("IO Exception");
			LOG.info(errors);	
		}
		catch(InterruptedException i){
			StringWriter errors = new StringWriter();
			i.printStackTrace(new PrintWriter(errors));
			LOG.info("Interrupted Exception");
			LOG.info(errors);	
		}
		
		
		
	}
	
	
	
	
}
