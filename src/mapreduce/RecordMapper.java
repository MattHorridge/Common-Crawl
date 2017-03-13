package mapreduce;

import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper; //The interface




import warc.WARCRecord;
import warc.WARCRecordBuilder;
import warc.WARCRecordBuilder.streamType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * TODO: Refactor name etc
 * 
 * 
 */

/**
 * Mapper class for MR project
 * Input will be Segment Address & URL name
 * Output <URL, WARCRecord>
 * @author matthorridge
 * @param <V>
 * @param <K>
 *
 */


public class RecordMapper extends Mapper<Text, Text, Text, WARCRecord> {

	private static final Log LOG = LogFactory.getLog(RecordMapper.class);
	
	private DataOutput out;
	
	
	public void map(Text URL, Text SegmentName, 
			Context context)  {
		
		
		
		LOG.info("Try to map");
		// TODO Auto-generated method stub
		try{
			
			
			
			WARCRecordBuilder RBuilder = new WARCRecordBuilder();
			
			//String testString = "http://0.tqn.com/6/g/candleandsoap/b/rss2.xml";
			
			
			RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("aws-publicdatasets", SegmentName.toString()).getObjectContent());
			WARCRecord poo = RBuilder.testRecords3("response", RBuilder.getFilereader());
			//output
			Text OutputKey = new Text(URL);
			context.write(OutputKey, poo);
			LOG.info("mapper finished");
			
		}
		
		catch(Exception e){
			LOG.info(e);
		}
	}

	
	//Map function
	
	
	
	

}
