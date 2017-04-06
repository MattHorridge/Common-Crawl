package main.mapreduceMain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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

public class SingleIORecordMapper extends Mapper<LongWritable, Text, Text, WARCRecord> {

	private static final Log LOG = LogFactory.getLog(SingleIORecordMapper.class);
	private static AWSCredentials creds;

	
	@Override
	public void map(LongWritable line, Text SegmentName, Context context)  {
	
		Configuration config = context.getConfiguration();
		String targetURL = config.get("URL");
		creds = null;
		
		try {
			creds = new BasicAWSCredentials(config.get("inputKey"), config.get("inputSecret"));
		}
		catch(AmazonClientException e){ 
			
			throw new AmazonClientException("Credentials were not correctly entered or were wrong - provide your correct credentails in the "
					+ "specfied format",
		                    e);
		}
		
		
	
		LOG.info("Try to map");
		try{
			
			WARCRecordBuilder RBuilder = new WARCRecordBuilder(creds);

			RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("commoncrawl", SegmentName.toString()).getObjectContent());
			WARCRecord Record = RBuilder.buildSingleRecord("response", RBuilder.getFilereader(), targetURL);
			RBuilder.closeStream();
			//output
			
			if (Record == null){
				LOG.info("mapper finished");
				LOG.info("Desired URL Not Found");
				return;
			}
		
			context.write(new Text(targetURL), Record);
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
