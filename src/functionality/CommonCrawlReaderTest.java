package functionality;


import org.apache.commons.*;

import WARC.WARCFormatDetails;
import WARC.WARCRecord;
import WARC.WARCRecordBuilder;

import java.io.InputStream;
import java.io.File;
import java.io.*;
import java.util.regex.*;
import java.util.*;

public class CommonCrawlReaderTest {
	
	public static void main(String[] args) throws Exception{
		
		
		//TestCode tester = new TestCode();
		
		//tester.GetDataTest();
		
		File f = new File("/Volumes/MATT WORK/Work/Common Crawl Knows it All/MainProject/test.warc");
		
		WARCRecordBuilder rest = new WARCRecordBuilder(f);
		
		WARCRecord test = rest.buildRecord("response");
		
		System.out.println(test.getHeaders());
		
	}
	
	

}
