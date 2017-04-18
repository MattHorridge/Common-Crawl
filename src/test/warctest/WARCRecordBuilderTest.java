package test.warctest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.warc.WARCRecord;
import main.warc.WARCRecordBuilder;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import test.awstoolstest.RecordTestDetails;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AnonymousAWSCredentials;

public class WARCRecordBuilderTest implements RecordTestDetails {

	private static WARCRecordBuilder testBuilder;
	private static WARCRecord testRecord;
	private static List<String> contentBlock;
	private static Map<String, String> headers;
	private static File testfile;
	
	
	@Rule
	 public TemporaryFolder myfolder = new TemporaryFolder();

	
	@Test
	public void blankConstructorTest(){
		
		testBuilder = new WARCRecordBuilder();
		Assert.assertNull(testBuilder.getSegmentExtractor());
	}
	
	@Test
	public void credConstructorTest(){
		AWSCredentials creds = new AnonymousAWSCredentials();
		testBuilder = new WARCRecordBuilder(creds);
		Assert.assertNotNull(testBuilder.getSegmentExtractor());
	}
	


	
	@Test
	public void buildSingleRecordTest() throws IOException{
		
		
		testfile =  myfolder.newFile("test.txt");
		testRecord = new WARCRecord();
		contentBlock = new ArrayList<String>();
		headers = new HashMap<String,String>();
		
		
		try {	
			BufferedWriter writer = new BufferedWriter(new FileWriter(testfile));
			writer.write(RESPONSE + "\n");
			writer.write(WARCTARGETURI+ "\n");
			writer.write(WARC_WARCINFO_ID + ": " + WARCINFO + "\n");
			writer.write(HEADER_LINE_ONE + "\n");
			writer.write(HEADER_LINE_TWO + "\n");
			writer.write(HEADER_LINE_THREE + "\n");
			
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		 	contentBlock.add(HEADER_LINE_ONE);
			contentBlock.add(HEADER_LINE_TWO);
			contentBlock.add(HEADER_LINE_THREE);
			headers.put(WARC_TARGET_URI + ": ", TARGET);
			headers.put(WARC_WARCINFO_ID + ": ", WARCINFO);
			
			testRecord.setContentBlock(contentBlock);
			testRecord.setHeaders(headers);
			testRecord.setURL(TARGET);
			testRecord.setType("response");
			testBuilder = new WARCRecordBuilder(testfile);

			WARCRecord builtRecord = testBuilder.buildSingleRecord("response", testBuilder.getFilereader(), TARGET);
			
			Assert.assertNotNull(builtRecord);
			Assert.assertEquals(builtRecord.toString(), testRecord.toString());
		
		
	}
	
	
	
	@Test
	public void buildRecordArrayTest() throws IOException{
		testfile =  myfolder.newFile("test.txt");
		testBuilder = new WARCRecordBuilder(testfile);
		
		try {	
			BufferedWriter writer = new BufferedWriter(new FileWriter(testfile));
			writer.write(RESPONSE + "\n");
			writer.write(WARCTARGETURI+ "\n");
			writer.write(WARC_WARCINFO_ID + ": " + WARCINFO + "\n");
			writer.write(HEADER_LINE_ONE + "\n");
			writer.write(HEADER_LINE_TWO + "\n");
			writer.write(HEADER_LINE_THREE + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		WARCRecord[] testRecordArray = testBuilder.buildRecordsArray("response", testBuilder.getFilereader(), TARGET);
		
		Assert.assertNotNull(testRecordArray);
		Assert.assertEquals(1, testRecordArray.length);
		Assert.assertEquals(WARCRecord.class, testRecordArray[0].getClass());
	}
	

	
	
}
