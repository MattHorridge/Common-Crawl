package mapreduceDriver;



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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class CommonCrawlExplorer {
	private static final Log LOG = LogFactory.getLog(CommonCrawlExplorer.class);

	static Configuration conf;

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		jobSetup(args);

	}
	
	
	public static void jobSetup(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		String x = args[5];
		
		switch (x){
		case "-l": listInput(args);
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
		
		job.setMapperClass(ListRecordMapper.class);
	       
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(WARCRecordArrayWritable.class);
	   
	    job.setReducerClass(ListRecordReducer.class);
	    
	   
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WARCRecord.class);
		job.setJarByClass(CommonCrawlExplorer.class);
		
		FileInputFormat.addInputPath(job,  new Path(args[1]));
		FileOutputFormat.setOutputPath(job,  new Path(args[2]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);	      
	        	
		
	}

	private static void listInput(String[] args)throws IOException, ClassNotFoundException, InterruptedException{
		
		
		
		
		//String[] arr = readInTargetList(args[6]);
		
		String[] arr = new String[]{
				"nytimes.com",
				"imdb.com",
				"tumblr.com",
				"instagram.com",
				"imgur.com",
				"twitch.tv", //change this,
				"aliexpress.com",
				"ebay.com",
				"WordPress.com",
				"blogspot.com",
				"stackoverflow.com",
				"answers.yahoo.com",
				"http://1045theteam.com",
				"http://6dollarshirts.com",
		
				
		};
		
		
		conf = new Configuration();
		conf.set("mapreduce.jobtracker.address", "local");
		conf.set("inputKey", args[3]);
		conf.set("inputSecret", args[4]);
		conf.setStrings("URLlist", arr);
		conf.set("targettype", "response");

		
		Job job = Job.getInstance(conf, "CommonCrawlKnowsItAll");
	
	
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setMapperClass(MultipleURLInputRecordMapper.class);
	       
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(WARCRecord.class);
	   
	    job.setReducerClass(SingleIORecordReducer.class);
	    
	   
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WARCRecord.class);
		job.setJarByClass(CommonCrawlExplorer.class);
		
		FileInputFormat.addInputPath(job,  new Path(args[1]));
		FileOutputFormat.setOutputPath(job,  new Path(args[2]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);	
		
		
	}
	
	private static String[] readInTargetList(String path) throws IOException{
		
		List<String> list = new ArrayList<String>();
		BufferedReader read = new BufferedReader(new FileReader(path));
		String line;
		while((line = read.readLine()) != null){
			list.add(line);
		}
		read.close();
		
		
		return list.toArray(new String[]{});
	}
	
	
	
}
