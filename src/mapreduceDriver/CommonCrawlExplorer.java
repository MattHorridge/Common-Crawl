package mapreduceDriver;



import main.awstools.SegmentExtractor;
import main.mapreduceMain.*;
import main.mapreduceio.WARCRecordArrayWritable;
import main.warc.WARCRecord;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.S3Object;



public class CommonCrawlExplorer {
	private static final Log LOG = LogFactory.getLog(CommonCrawlExplorer.class);

	static Configuration conf;

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		jobSetup(args);

	}
	
	
	public static void jobSetup(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		String x = args[5];
		
		switch (x){
		case "-l": localListInput(args);
		case "-s": s3ListInput(args);
		default: singleInput(args);
		}
			
		

		
	}
	
	private static void singleInput(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		conf = new Configuration();
		conf.set("inputKey", args[3]);
		conf.set("inputSecret", args[4]);
		conf.set("URL", args[5]);
		conf.set("targettype", "response");
		
		Job job = Job.getInstance(conf, "CommonCrawlKnowsItAll");
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
	

		job.setMapperClass(SingleURLRecordMapper.class);
	    job.setMapOutputKeyClass(Text.class);
	    
	    job.setMapOutputValueClass(WARCRecordArrayWritable.class);
	   
	    job.setReducerClass(SingleURLRecordReducer.class);
	    
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WARCRecord.class);
		job.setJarByClass(CommonCrawlExplorer.class);
		
		FileInputFormat.addInputPath(job,  new Path(args[1]));
		FileOutputFormat.setOutputPath(job,  new Path(args[2]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);	      
	        	
		
	}

	private static void localListInput(String[] args)throws IOException, ClassNotFoundException, InterruptedException{
		
		
		
		
		String[] arr = localTargetList(args[6]);
		
	
		
		
		conf = new Configuration();
		conf.set("mapreduce.jobtracker.address", "local");
		conf.set("inputKey", args[3]);
		conf.set("inputSecret", args[4]);
		conf.setStrings("URLlist", arr);
		conf.set("targettype", "response");

		
		Job job = Job.getInstance(conf, "CommonCrawlKnowsItAll");
	
	
		job.setInputFormatClass(TextInputFormat.class);
		//job.setInputFormatClass(NLineInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setMapperClass(MultipleURLInputRecordMapper.class);
	       
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(WARCRecord.class);
	   
	    job.setReducerClass(MultipleURLRecordReducer.class);
	    
	   
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WARCRecord.class);
		job.setJarByClass(CommonCrawlExplorer.class);
		
		FileInputFormat.addInputPath(job,  new Path(args[1]));
		FileOutputFormat.setOutputPath(job,  new Path(args[2]));
		
		job.waitForCompletion(true);
		
		System.exit(0);
		
	
	}
	
	
	private static void s3ListInput(String[] args)throws IOException, ClassNotFoundException, InterruptedException{
		
		
		AWSCredentials creds = new BasicAWSCredentials(args[3], args[4]);
		
		String[] arr = s3TargetList(args[6], args[7], creds);
		

		conf = new Configuration();
		conf.set("mapreduce.jobtracker.address", "local");
		conf.set("inputKey", args[3]);
		conf.set("inputSecret", args[4]);
		conf.setStrings("URLlist", arr);
		conf.set("targettype", "response");

		
		Job job = Job.getInstance(conf, "CommonCrawlKnowsItAll");
	
	
		job.setInputFormatClass(TextInputFormat.class);
		//job.setInputFormatClass(NLineInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setMapperClass(MultipleURLInputRecordMapper.class);
	       
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(WARCRecord.class);
	   
	    job.setReducerClass(MultipleURLRecordReducer.class);
	    
	   
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WARCRecord.class);
		job.setJarByClass(CommonCrawlExplorer.class);
		
		FileInputFormat.addInputPath(job,  new Path(args[1]));
		FileOutputFormat.setOutputPath(job,  new Path(args[2]));
		
		job.waitForCompletion(true);
		
		System.exit(0);
		
	
	}
	
	
	
	
	
	
	
	
	
	
	
	private static String[] localTargetList(String path) throws IOException{
		
		List<String> list = new ArrayList<String>();
		BufferedReader read = new BufferedReader(new FileReader(path));
		String line;
		while((line = read.readLine()) != null){
			list.add(line);
		}
		read.close();
		
		
		return list.toArray(new String[]{});
	}
	
	private static String[] s3TargetList(String Bucket, String Key, AWSCredentials creds) throws IOException{
		

		SegmentExtractor extractor = new SegmentExtractor(creds);
		S3Object TargetList = extractor.extractSegment(Bucket, Key);
		InputStream stream = extractor.extractSegmentStream(TargetList);
		BufferedReader read = new BufferedReader(new InputStreamReader(stream));
		List<String> list = new ArrayList<String>();	
		String line;
		
		while((line = read.readLine()) != null){
			list.add(line);
		}
		read.close();
		
		return list.toArray(new String[]{});
		
	
	}

	
	
	
}
