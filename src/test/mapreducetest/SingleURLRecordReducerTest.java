package test.mapreducetest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import main.mapreduceMain.SingleURLRecordReducer;
import main.mapreduceio.WARCRecordArrayWritable;
import main.warc.WARCRecord;

import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;


public class SingleURLRecordReducerTest {
	
	 ReduceDriver<Text, WARCRecordArrayWritable, Text, Text> reduceDriver;
	 List<String> TestContentBlockOne = new ArrayList<String>();
	 List<String> TestContentBlockTwo = new ArrayList<String>();
	 
	 Map<String,String> TestMapOne = new HashMap<String,String>();
	 Map<String, String> TestMapTwo = new HashMap<String,String>();
	 
	  @Before
	  public void setUp() {
	   
	    SingleURLRecordReducer testReducer = new SingleURLRecordReducer();    
	    reduceDriver = ReduceDriver.newReduceDriver(testReducer);
	    TestContentBlockOne.add("TestHeaderOne");
		TestContentBlockOne.add("TestHeaderTwo");
		
		TestContentBlockTwo.add("OneTestHeader");
		TestContentBlockTwo.add("TwoTestHeader");
		
		TestMapOne.put("TestKeyOne", "TestValueOne");
		
		TestMapTwo.put("TestKeyTwo", "TestValueTwo");
		
	  }
	
	@Test
	public void testSingleURlReducer() throws IOException {
		
		WARCRecordArrayWritable TestArray = new WARCRecordArrayWritable();
		
		WARCRecord TestWARCRecordA = new WARCRecord();
		WARCRecord TestWARCRecordB = new WARCRecord();
		
		TestWARCRecordA.setURL("URLA");
		TestWARCRecordB.setURL("URLA");
		
		TestWARCRecordA.setType("testType");
		TestWARCRecordB.setType("testType");
		
		
		TestWARCRecordA.setContentBlock(TestContentBlockOne);
		TestWARCRecordB.setContentBlock(TestContentBlockTwo);
		
		TestWARCRecordA.setHeaders(TestMapOne);
		TestWARCRecordB.setHeaders(TestMapTwo);
		
		Writable[] testWritableArray = {TestWARCRecordA, TestWARCRecordB};
		
		
		List<WARCRecordArrayWritable> values = new ArrayList<WARCRecordArrayWritable>();
		
		TestArray.set(testWritableArray);
		
		
		values.add(TestArray);
		reduceDriver.withInput(new Text("URLA"), values);
		
		StringBuffer testBuffer = new StringBuffer();
		
	
		
		testBuffer.append("\n");
		testBuffer.append(TestWARCRecordA.toString());
		testBuffer.append("\n");
		testBuffer.append("\n");
		testBuffer.append( TestWARCRecordB.toString());
		testBuffer.append("\n");
		testBuffer.append("\n");
		
	    reduceDriver.withOutput(new Text("URLA"), new Text(testBuffer.toString()));
	    reduceDriver.runTest();
		
	}

}
