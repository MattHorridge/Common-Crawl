package test.mapreducetest;

import static org.junit.Assert.*;

import java.io.IOException;

import main.mapreduceMain.SingleURLRecordMapper;
import main.mapreduceio.WARCRecordArrayWritable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.amazonaws.AmazonClientException;

public class SingleURLRecordMapperTest {

	MapDriver<LongWritable, Text, Text, WARCRecordArrayWritable> mapDriver;
	
	
	@Before
	public void setup(){
		SingleURLRecordMapper map = new SingleURLRecordMapper();
		mapDriver = mapDriver.newMapDriver(map);
		
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
	
	
	
	
}
