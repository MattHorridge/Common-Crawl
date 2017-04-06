package main.warc;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import main.awstools.GZIPWrapper.GZIPWrapperInputStream;
import main.awstools.SegmentExtractor;
import main.awstools.s3Access;
import main.mapreduceio.WARCRecordArrayWritable;
import main.warc.WARCRecordBuilder.streamType;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class tester {
	
public static void main(String[] args) throws FileNotFoundException, IOException{
		
	//CC-MAIN-20140416005201-00000-ip-10-147-4-33.ec2.internal.warc
		//01wave.com/
	
		String URL = "http://";
		String SegmentName = "crawl-data/CC-MAIN-2015-22/segments/1432207924799.9/warc/CC-MAIN-20150521113204-00000-ip-10-180-206-219.ec2.internal.warc.gz";
		
		AWSCredentials creds = new BasicAWSCredentials("AKIAJL45LAS6LGGHQESQ","xDsZNHswz+ZMap5Y3o2+acDiI8ZeP7aW3TNtToE6");
		//AWSCredentials creds = new AnonymousAWSCredentials();
		
		//WARCRecordBuilder RBuilder = new WARCRecordBuilder(creds);
		
	//	File file = new File("/home/matty/Desktop/CC-MAIN-20140416005201-00000-ip-10-147-4-33.ec2.internal.warc");
		//WARCRecordBuilder RBuilder = new WARCRecordBuilder(file);
		
		//RBuilder.openStream(streamType.FILE, file);
		
	//	WARCRecord[] k = RBuilder.buildRecordsArray("response", RBuilder.getFilereader(), URL.toString());
		//System.out.println(k.length);
		
	//	for (int i = 0; i < k.length; i++){
		//	System.out.println(k[i]);
	//	}
		
		
		//RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("commoncrawl", SegmentName.toString()).getObjectContent());
		try {
			
			s3Access pipeLine = new s3Access(creds);
			
			//SegmentExtractor k = new SegmentExtractor(creds);
			
			S3Object ccSegment = pipeLine.gets3Instance().getObject(new GetObjectRequest("commoncrawl","crawl-data/CC-MAIN-2015-22/segments/1432207924799.9/warc/CC-MAIN-20150521113204-00000-ip-10-180-206-219.ec2.internal.warc.gz"));
			
			//S3Object j = k.extractSegment("commoncrawl", "crawl-data/CC-MAIN-2015-22/segments/1432207924799.9/warc/CC-MAIN-20150521113204-00000-ip-10-180-206-219.ec2.internal.warc.gz");
			
			
			//URL a = new URL("https://commoncrawl.s3.amazonaws.com/crawl-data/CC-MAIN-2015-22/segments/1432207924799.9/warc/CC-MAIN-20150521113204-00000-ip-10-180-206-219.ec2.internal.warc.gz");
			
			//URLConnection urlConnection = a.openConnection();
			
			//BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream((ccSegment.getObjectContent()))));		
			
			//BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlConnection.getInputStream())));	
			
			//AvailableInputStream ais = new AvailableInputStream(urlConnection.getInputStream());
			//GZIPInputStream gzipishttp = new GZIPInputStream(ais);
			
			GZIPWrapperInputStream ais = new GZIPWrapperInputStream(ccSegment.getObjectContent());
			
			GZIPInputStream gg = new GZIPInputStream(ais);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(gg));
			
		
		//	while(reader.readLine() != null){
			//	System.out.println(reader.readLine());
		//	}
			
			//gzipishttp.close();
			
			WARCRecordBuilder RBuilder = new WARCRecordBuilder(creds);
			
			//WARCRecord[] k = RBuilder.buildRecordsArray("response", reader, URL.toString());
			
			String[] targets = {
					
					"stackoverflow.com",
					"nytimes.com"
			};
			
			
		//	
			WARCRecord[] p = RBuilder.buildRecordsArray("response", reader, targets);
			
			gg.close();
			
			

			//List<WARCRecord> test;
			
			
			//List<WARCRecord> Record = RBuilder.buildRecords("response", RBuilder.getFilereader(), URL.toString());
			
			//WARCRecord[] k = RBuilder.buildRecordsArray("response", RBuilder.getFilereader(), URL.toString());
			//System.out.println(k.length);
			
			//for (int i = 0; i < k.length; i++){
			//	System.out.println(k[i]);
			//}
			
			
		//	for(int i = 0; i < test.size(); i++){
			//	System.out.println(test.get(i));
		//	}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
