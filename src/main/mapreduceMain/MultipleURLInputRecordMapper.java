package main.mapreduceMain;

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




public class MultipleURLInputRecordMapper extends Mapper<LongWritable, Text, Text, WARCRecord> {
	private static final Log LOG = LogFactory.getLog(MultipleURLInputRecordMapper.class);
	private static AWSCredentials creds;
	private static String[] targets;
	
	protected void setup(Context context)throws IOException, InterruptedException, AmazonClientException{
		
		Configuration config = context.getConfiguration();
		targets = config.getStrings("URLlist");
		creds = null;
	
		try {
			creds = new BasicAWSCredentials(config.get("inputKey"), config.get("inputSecret"));
		}
		catch(AmazonClientException e){ 
			LOG.info("Amazon Client Exception - Correct Credentials not correctly entered or were wrong");
			LOG.info(e.getCause());
			LOG.info(e.getMessage());
		}
		
	}
	
	
	
	@Override
	public void map(LongWritable line, Text SegmentName, Context context){
		
	
		LOG.info("Trying to Map");
		WARCRecordBuilder RBuilder = new WARCRecordBuilder(creds);
		
		try{
				RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("commoncrawl", SegmentName.toString()).getObjectContent());
				WARCRecord Records[] = RBuilder.buildRecordsArray("response", RBuilder.getFilereader(), targets);
				RBuilder.closeStream();
				
				if(Records == null){
					return;
				}
				else{
					for (int i = 0; i < Records.length; i++){
							context.write(new Text(Records[i].getURL()), Records[i]);
					}
				}
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
			catch (InterruptedException e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LOG.info("IO Exception");
				LOG.info(errors);
		}
		
		}
	
	}
	
	
	

