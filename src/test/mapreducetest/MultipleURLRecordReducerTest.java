package test.mapreducetest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.mapreduceMain.MultipleURLRecordReducer;
import main.mapreduceMain.SingleURLRecordReducer;
import main.mapreduceio.WARCRecordArrayWritable;
import main.warc.WARCRecord;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

public class MultipleURLRecordReducerTest {
	
	
	 ReduceDriver<Text, WARCRecord, Text, Text> reduceDriver;
	 List<String> TestContentBlockOne = new ArrayList<String>();
		List<String> TestContentBlockTwo = new ArrayList<String>();
		 
		Map<String,String> TestMapOne = new HashMap<String,String>();
		Map<String, String> TestMapTwo = new HashMap<String,String>();

	@Before
	public void setUp(){
		
	    MultipleURLRecordReducer testReducer = new MultipleURLRecordReducer();    
	    reduceDriver = ReduceDriver.newReduceDriver(testReducer);
	    
	    TestContentBlockOne.add("TestHeaderOne");
		TestContentBlockOne.add("TestHeaderTwo");
		
		TestContentBlockTwo.add("OneTestHeader");
		TestContentBlockTwo.add("TwoTestHeader");
		
		TestMapOne.put("TestKeyOne", "TestValueOne");
		
		TestMapTwo.put("TestKeyTwo", "TestValueTwo");
		
	    
	}
	

	@Test
	public void testRemoveDoubles() throws IOException {
		
		//TWO Equal Inputs only one output
		
		WARCRecord TestWARCRecordA = new WARCRecord();
		WARCRecord TestWARCRecordADouble = new WARCRecord();
		
		
		
		TestWARCRecordA.setURL("URLA");
		TestWARCRecordADouble.setURL("URLA");
		
		TestWARCRecordA.setType("testType");
		TestWARCRecordADouble.setType("testType");
		
		
		TestWARCRecordA.setContentBlock(TestContentBlockOne);
		TestWARCRecordADouble.setContentBlock(TestContentBlockOne);
		
		TestWARCRecordA.setHeaders(TestMapOne);
		TestWARCRecordADouble.setHeaders(TestMapOne);
		
		List<WARCRecord> values = new ArrayList<WARCRecord>();
		values.add(TestWARCRecordA);
		values.add(TestWARCRecordADouble);
		
		reduceDriver.withInput(new Text("URLA"), values);

		
		StringBuffer testBuffer = new StringBuffer();
		

		testBuffer.append(TestWARCRecordA.toString());
		testBuffer.append("\n");

	
		
		reduceDriver.withOutput(new Text("TARGET URL: URLA"), new Text(testBuffer.toString()));
	    reduceDriver.runTest();
	
	}

}
