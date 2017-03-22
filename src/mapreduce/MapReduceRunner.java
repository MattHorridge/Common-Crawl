package mapreduce;



import warc.WARCRecord;
import warc.WARCRecordBuilder;
import warc.WARCRecordBuilder.streamType;

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

import awstools.SegmentExtractor;

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



public class MapReduceRunner {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		
		
		Configuration conf = new Configuration();
		conf.set("mapreduce.jobtracker.address", "local");
		conf.set("inputKey", args[3]);
		conf.set("inputSecret", args[4]);

		
		Job job = Job.getInstance(conf, "CommonCrawlKnowsItAll");
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
	    job.setMapperClass(RecordMapper.class);
	   
	    
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(WARCRecord.class);
	   
	
	    job.setReducerClass(RecordReducer.class);
	    
	    //job.setNumReduceTasks(0);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WARCRecord.class);
		job.setJarByClass(MapReduceRunner.class);
		
		FileInputFormat.addInputPath(job,  new Path(args[1]));
		FileOutputFormat.setOutputPath(job,  new Path(args[2]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);	      
	        	
	}

	
	
	
}
