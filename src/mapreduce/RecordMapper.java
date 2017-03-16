package mapreduce;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.hadoop.io.Text;
//The interface

import org.apache.hadoop.mapreduce.Mapper;

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
	

	
	@Override
	public void map(Text URL, Text SegmentName, 
			Context context)  {
		
		
		
		LOG.info("Try to map");
		try{
			
			WARCRecordBuilder RBuilder = new WARCRecordBuilder();
			//String testString = "http://0.tqn.com/6/g/candleandsoap/b/rss2.xml";
			
			RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("aws-publicdatasets", SegmentName.toString()).getObjectContent());
			WARCRecord R = RBuilder.testRecords3("response", RBuilder.getFilereader(), URL.toString());
			//output
		
			context.write(URL, R);
			LOG.info("mapper finished");
			
			
		}
		
		catch(Exception e){
			LOG.info(e);
			
			//LOG.info(e.printStackTrace());
			System.out.println(e.getStackTrace().toString());
			
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			System.out.println(errors.toString());
			
		}
	}

	
	//Map function
	
	
	
	

}
