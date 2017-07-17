package test.awstoolstest;



import java.io.BufferedReader;
import java.io.IOException;

import main.awstools.SegmentExtractor;
import main.awstools.s3Access;
import main.warc.WARCRecord;
import main.warc.WARCRecordBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;

public class SegmentExtractorTest {


	private static AnonymousAWSCredentials anoncreds;
	
	
	@Before
	public void setup(){
		 anoncreds = new AnonymousAWSCredentials();
	}
	
	@Test
	public void SegmentExtractorBlankConstructorTest(){
		SegmentExtractor testExtract = new SegmentExtractor();
		
		Assert.assertNull(testExtract.getBucket());
		Assert.assertNull(testExtract.getEncoding());
		Assert.assertNull(testExtract.getKey());	
	}
	
	@Test
	public void SegmentExtractorCredConstructor(){
		SegmentExtractor testExtract = new SegmentExtractor(anoncreds);
		
		Assert.assertEquals("UTF-8", testExtract.getEncoding());
		Assert.assertNull(testExtract.getBucket());
		Assert.assertNull(testExtract.getKey());
	}
	
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test 
	public void TestExtractSegmentMethodInvalidInputException(){
		SegmentExtractor testExtract = new SegmentExtractor();
		s3Access access = new s3Access(anoncreds);
		
		testExtract.setPipeline(access);
		//exception.expect(IndexOutOfBoundsException.class);
		Assert.assertNull(testExtract.extractSegment("testincorrect", "testincorrect"));
		
	}
	
	@Test
	public void TestExtractSegmentMethod404Error(){
		SegmentExtractor testExtract = new SegmentExtractor();
		s3Access access = new s3Access(anoncreds);
		
		testExtract.setPipeline(access);
		try{
		testExtract.extractSegment("commoncrawl", "crawl-data/CC-MAIN-2014-35/segments/14085");
		}
		catch (AmazonServiceException e){
			Assert.assertEquals(e.getStatusCode(), "404");
			Assert.assertEquals(e.getErrorCode(), "NoSuchKey");
		}
	}
	
	@Test
	public void TestExtractSegmentWorks() throws IOException{
		
		String bucket = "testBucket";
		String key = "testKey";
		
		//Create SegmentExtractor to be tested
		SegmentExtractor testExtract = new SegmentExtractor();
	
		AmazonS3Client mockedClient = Mockito.mock(AmazonS3Client.class);
		S3Object mockedS3Obj = Mockito.mock(S3Object.class);
		s3Access mockAccess = Mockito.mock(s3Access.class);
		BufferedReader mockedReader = Mockito.mock(BufferedReader.class);	
		WARCRecordBuilder mockedBuilder = Mockito.mock(WARCRecordBuilder.class);
		WARCRecord mockedRecord = Mockito.mock(WARCRecord.class);
		
		testExtract.setPipeline(mockAccess);
		testExtract.setBucket(bucket);
		testExtract.setKey(key);
		
		
		Mockito.when(mockAccess.gets3Instance()).thenReturn(mockedClient);
		Mockito.when(mockedClient.getObject(bucket,key)).thenReturn(mockedS3Obj);
		Mockito.when(mockedBuilder.buildSingleRecord("TEST", mockedReader, "TEST")).thenReturn(mockedRecord);
		Mockito.when(mockedRecord.toString()).thenReturn("TEST RECORD RETURN");
	
		Assert.assertEquals(mockAccess.gets3Instance(), mockedClient);
		Assert.assertEquals(testExtract.extractSegment(testExtract.getBucket(), testExtract.getKey()), mockedS3Obj); //maintest
	}
	
	

}
