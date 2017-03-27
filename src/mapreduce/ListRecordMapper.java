package mapreduce;




import mapreduceio.WARCRecordArrayWritable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import warc.WARCRecord;
import warc.WARCRecordBuilder;
import warc.WARCRecordBuilder.streamType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;



public class ListRecordMapper extends Mapper<LongWritable, Text, Text, WARCRecordArrayWritable> {

	private static final Log LOG = LogFactory.getLog(ListRecordMapper.class);
	private static AWSCredentials creds;
	//private List<WARCRecord> RecordList;
	private WARCRecord[] RecordList;
	
	@Override
	public void map(LongWritable line, Text SegmentName, Context context){
		
		Configuration config = context.getConfiguration();
		
		String targetURL = config.get("URL");
		creds = null;
		
		
		try {
			creds = new BasicAWSCredentials(config.get("inputKey"), config.get("inputSecret"));
		}
		catch(Exception e){ 
			throw new AmazonClientException("Credentials were not correctly entered - provide your correct credentails in the "
					+ "specfied format",
		                    e);
		}
		
		
		LOG.info("Trying to List Map");
		
		try{
			
			WARCRecordBuilder RBuilder = new WARCRecordBuilder(creds);
			RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("aws-publicdatasets", "common-crawl/" + SegmentName.toString()).getObjectContent());
			RecordList =  RBuilder.buildRecordsArray("response", RBuilder.getFilereader(), targetURL);
			//output
			
			if (RecordList == null){
				LOG.info("mapper finished");
				LOG.info("Desired URL Not Found");
				return;
			}
		
			
			
			
			
			context.write(new Text(targetURL), new WARCRecordArrayWritable(RecordList));
			
			//RecordList.clear();
			LOG.info("mapper finished");
		}
		
		catch(Exception e){
			
			//Some debugging
			LOG.info(e);
			
			System.out.println(e.getStackTrace().toString());
			
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			System.out.println(errors.toString());
			
		}
		
		
	}
	
	
	
	
}
