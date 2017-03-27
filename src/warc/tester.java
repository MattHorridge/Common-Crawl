package warc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import mapreduceio.WARCRecordArrayWritable;
import warc.WARCRecordBuilder.streamType;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

public class tester {
	
public static void main(String[] args) throws FileNotFoundException, IOException{
		
	
		//01wave.com/
	
		String URL = "http://";
		String SegmentName = "common-crawl/crawl-data/CC-MAIN-2015-22/segments/1432207924799.9/warc/CC-MAIN-20150521113204-00040-ip-10-180-206-219.ec2.internal.warc.gz";
		
		AWSCredentials creds = new BasicAWSCredentials("AKIAJL45LAS6LGGHQESQ","xDsZNHswz+ZMap5Y3o2+acDiI8ZeP7aW3TNtToE6");
		
		WARCRecordBuilder RBuilder = new WARCRecordBuilder(creds);
		
		
		RBuilder.openStream(streamType.GZIP, RBuilder.getSegmentExtractor().extractSegment("aws-publicdatasets", SegmentName.toString()).getObjectContent());
		try {
			
			//List<WARCRecord> test;
			
			
			//List<WARCRecord> Record = RBuilder.buildRecords("response", RBuilder.getFilereader(), URL.toString());
			
			WARCRecord[] k = RBuilder.buildRecordsArray("response", RBuilder.getFilereader(), URL.toString());
			
			for (int i = 0; i < k.length; i++){
				System.out.println(k[i]);
			}
			
			
		//	for(int i = 0; i < test.size(); i++){
			//	System.out.println(test.get(i));
		//	}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
