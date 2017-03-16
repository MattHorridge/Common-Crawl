package awsAccess;



import mapreduce.RecordMapper;

import mapreduce.ReducerB;
import mapreduce.WARCArrayWritable;
import warc.WARCRecord;
import warc.WARCRecordBuilder;
import warc.WARCRecordBuilder.streamType;
import ziptools.SegmentExtractor;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

import com.amazonaws.services.s3.AmazonS3Client;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.*;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsResult;
import com.amazonaws.services.elasticmapreduce.model.HadoopJarStepConfig;
import com.amazonaws.services.elasticmapreduce.model.StepConfig;
import com.amazonaws.services.elasticmapreduce.util.StepFactory;

import org.apache.hadoop.util.ToolRunner;



public class S3AccessTestOne {
/**
 * Class to test basic access to s3 - will be basis to server side control of project including creating/destroying buckets,
 * moving data around and emailing the end user.
 */
	
	/**
	 * This sample demonstrates how to make basic requests to Amazon S3 using the
	 * AWS SDK for Java.
	 * <p>
	 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
	 * account, and be signed up to use Amazon S3. For more information on Amazon
	 * S3, see http://aws.amazon.com/s3.
	 * <p>
	 * Fill in your AWS access credentials in the provided credentials file
	 * template, and be sure to move the file to the default location
	 * (/Users/matthorridge/.aws/credentials) where the sample code will load the credentials from.
	 * <p>
	 * <b>WARNING:</b> To avoid accidental leakage of your credentials, DO NOT keep
	 * the credentials file in your source directory.
	 *
	 * http://aws.amazon.com/security-credentials
	 * @throws IOException 
	 */
	
	
	public static void main(String[] args) throws Exception {
		
		
		
		
		
		AWSCredentials creds = null;
		
		//try to get credentials from SDKTestUser locally
		//try{
			//creds = new ProfileCredentialsProvider("SDKTestUser").getCredentials();
		//}
		//catch (Exception e){
			//throw new AmazonClientException(
				//	"Cannot load the credentials from the credential profiles file. " +
		          //          "Please make sure that your credentials file is at the correct " +
		            //        "location (/Users/matthorridge/.aws/credentials), and is in valid format.",
		              //      e);
		//}
		
		String inputpath = "s3n://mhs3accesstest/testin/MRtestread.txt";

		String outputPath = "s3n://mhs3accesstest/testout";
		
		
		
		Configuration conf = new Configuration();
		conf.set("mapreduce.jobtracker.address", "local");
		
		
		Job job = Job.getInstance(conf, "testjar");
		
		
	 
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		//job.setOutputFormatClass(TextOutputFormat.class);
	    job.setMapperClass(RecordMapper.class);
	   
	    
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(WARCRecord.class);
	   
	    
	    
	    
	    job.setReducerClass(ReducerB.class);
	    
	    System.out.println(job.getReducerClass().toString());
	    
	    
	    //job.setNumReduceTasks(0);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WARCRecord.class);
		
	
		FileInputFormat.addInputPath(job,  new Path(args[1]));
		FileOutputFormat.setOutputPath(job,  new Path(args[2]));
		
		job.setJarByClass(S3AccessTestOne.class);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);	      
	        
	        
		//final AmazonS3 s3 = new AmazonS3Client(creds);
		//Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		//s3.setRegion(usWest2);
		
///////////////////////////////////////////
		
		
		
		/*
		//credentials
		
		AWSCredentials creds = null;
		
		//try to get credentials from SDKTestUser locally
		try{
			creds = new ProfileCredentialsProvider("SDKTestUser").getCredentials();
		}
		catch (Exception e){
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. " +
		                    "Please make sure that your credentials file is at the correct " +
		                    "location (/Users/matthorridge/.aws/credentials), and is in valid format.",
		                    e);
		}
		
		

		final AmazonS3 s3 = new AmazonS3Client(creds);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		s3.setRegion(usWest2);
		
		
		String bucketName = "sdktestbucketmh" + UUID.randomUUID();
		String key = "MyObjectKey";
		
		
		try{
			Bucket b = s3.createBucket(bucketName);
		} catch (AmazonServiceException e){
			System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
		System.out.println("created bucket: "+ bucketName );
				
		
	*/	
		
		
		//S3DataStream testy = new S3DataStream();
		//testy.run();
		
		//WARCRecordBuilder j = new WARCRecordBuilder();
		
		//crawl-data/CC-MAIN-2015-18/segments/1430461119624.60/warc/CC-MAIN-20150501061839-00023-ip-10-235-10-82.ec2.internal.warc.gz
		
		
		
		
	//	j.openStream(streamType.GZIP, j.getSegmentExtractor().extractSegment("aws-publicdatasets", "common-crawl/crawl-data/CC-MAIN-2013-20/segments/1368699755211/warc/CC-MAIN-20130516102235-00011-ip-10-60-113-184.ec2.internal.warc.gz").getObjectContent());
		
		
		//doesnt work
		//j.openStream(streamType.GZIP, j.getSegmentExtractor().extractSegment("aws-publicdatasets", "crawl-data/CC-MAIN-2017-04/").getObjectContent());

		/*
		
		try {
			//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WARCRecord poo = j.testRecords3("response", j.getFilereader());
		System.out.println("Finished");
		
		Map<String, String> t = poo.getHeaders();
		List<String> s = poo.getContentBlock();
		
		
		
		
		
		//System.out.println(t.keySet());
		
		//System.out.println(t.values());
		
		for (Map.Entry entry : t.entrySet()) {
		    System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		
		
		for (String item: s){
			System.out.println(item);
		}
		*/
		
		
		
		//System.out.println(test.getHeaders());
		//System.out.println(test.get(15).getHeaders());
		//System.out.println(test.get(15).getContentBlock());
		
		
		
		/*
		for(int i = 0; i < test.size(); i++){
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(test.get(i).getHeaders());
			System.out.println(test.get(i).getContentBlock());
		}
		
		
		
		System.out.println(test.size());
		
		
		
*/		
		
	}
//
	//public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		//String inputpath = "common-crawl/crawl-data/CC-MAIN-2013-20/segments/1368699755211/warc/CC-MAIN-20130516102235-00011-ip-10-60-113-184.ec2.internal.warc.gz";
		//not sure
		//;
	//}
	
	
//Future iterations will require use of EMR client builder - this file will use basic s3 access.	
	
	
	
	
}
