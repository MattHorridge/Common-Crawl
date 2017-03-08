package mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;

import org.apache.hadoop.mapreduce.Mapper; //The interface
import org.apache.hadoop.mapred.OutputCollector;


import warc.WARCRecord;
import warc.WARCRecordBuilder;
import warc.WARCRecordBuilder.streamType;

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

	public void map(Text URL, Text SegmentName, 
			OutputCollector<Text, WARCRecord> output) throws IOException {
		// TODO Auto-generated method stub
		try{
			
			WARCRecordBuilder RBuilder = new WARCRecordBuilder();
			
			String testString = "http://0.tqn.com/6/g/candleandsoap/b/rss2.xml";
			Text segment = SegmentName;
			
			RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("aws-publicdatasets", "common-crawl/crawl-data/CC-MAIN-2013-20/segments/1368699755211/warc/CC-MAIN-20130516102235-00011-ip-10-60-113-184.ec2.internal.warc.gz").getObjectContent());
			WARCRecord poo = RBuilder.testRecords3("response", RBuilder.getFilereader());
			
			
			//output
			Text OutputKey = new Text(segment);
			
			output.collect(OutputKey, poo);
			
		}
		
		catch(Exception e){}
	}

	
	//Map function
	
	
	
	

}
