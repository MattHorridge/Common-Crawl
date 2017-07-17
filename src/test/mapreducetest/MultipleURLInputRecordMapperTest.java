package test.mapreducetest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;

import main.awstools.SegmentExtractor;
import main.mapreduceMain.MultipleURLInputRecordMapper;
import main.mapreduceMain.SingleURLRecordMapper;
import main.mapreduceio.WARCRecordArrayWritable;
import main.warc.WARCRecord;
import main.warc.WARCRecordBuilder;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class MultipleURLInputRecordMapperTest {

	MapDriver<LongWritable, Text, Text, WARCRecord> mapDriver;
	WARCRecordBuilder wBuildtest;
	WARCRecord wRecordtest, wRecordtesttwo;
	SegmentExtractor extest;
	BufferedReader bufftest;
	
	@Before
	public void setup(){
		MultipleURLInputRecordMapper map = new MultipleURLInputRecordMapper();
		mapDriver = MapDriver.newMapDriver(map);
		
	}
	
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void setUpTest() throws IOException{
		
			
			
			mapDriver.getConfiguration().set("inputKey", "fakekey");
			mapDriver.getConfiguration().set("inputSecret", "fakeSecret");
			
		 exception.expect(NullPointerException.class);
		 mapDriver.withInput(new LongWritable(1), new Text("Test"));
		 mapDriver.runTest();
	}
	
	@Test
	public void mapTest() throws IOException{
		
		wBuildtest = Mockito.mock(WARCRecordBuilder.class);
		wRecordtest = Mockito.mock(WARCRecord.class);
		wRecordtesttwo = Mockito.mock(WARCRecord.class);
		extest = Mockito.mock(SegmentExtractor.class);
		bufftest = Mockito.mock(BufferedReader.class);
		
		String[] targets = {"test.com", "test2.com"};
		
		WARCRecord[] warray = {wRecordtest, 
				wRecordtesttwo};
		
		Mockito.when(wRecordtest.getURL()).thenReturn("test");
		Mockito.when(wRecordtest.getURL()).thenReturn("test");
		Mockito.when(wBuildtest.getSegmentExtractor()).thenReturn(extest);
		Mockito.when(wBuildtest.getFilereader()).thenReturn(bufftest);
		Mockito.when(wBuildtest.buildRecordsArray("response", wBuildtest.getFilereader(), targets)).thenReturn(warray);
		
		exception.expect(IllegalArgumentException.class);
		mapDriver.withInput(new LongWritable(1), new Text("Test"));
		mapDriver.runTest();
		
		
	}
	
	
	
	
}
