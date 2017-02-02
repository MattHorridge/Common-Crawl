package functionality;


import org.apache.commons.*;

import warc.WARCFormatDetails;
import warc.WARCRecord;
import warc.WARCRecordBuilder;
import ziptools.SegmentExtractor;

import java.io.InputStream;
import java.io.File;
import java.io.*;
import java.util.regex.*;
import java.util.*;

public class CommonCrawlReaderTest {
	
	public static void main(String[] args) throws Exception{
		
		
		//TestCode tester = new TestCode();
		
		//tester.GetDataTest();
		
		//File f = new File("/Volumes/MATT WORK/Work/Common Crawl Knows it All/MainProject/test.warc");
		
		
		//File g = new File("/Volumes/MATT WORK/Work/Common Crawl Knows it All/MainProject/bbc.warc");
		
		//WARCRecordBuilder rest = new WARCRecordBuilder(f);
		
	//	List<WARCRecord> test = rest.buildRecords("response");
		
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
		
		SegmentExtractor testExtraction = new SegmentExtractor("/Users/matthorridge/Documents/WIDE-20110225210142891-04382-13730-crawl301.us.archive.org-9443.warc.gz", "UTF-8");
		
		
		
		testExtraction.printReader();
	}

	

}
