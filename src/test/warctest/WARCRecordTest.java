package test.warctest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.junit.Assert;
import org.junit.Test;

import main.warc.WARCFormatDetails;
import main.warc.WARCRecord;


public class WARCRecordTest implements WARCFormatDetails{


	private Map<String, String> testHeaders;
	private List<String> testCBlock;
	private String testtype;

	
	
	@Test
	public void constructorTest(){
		
		WARCRecord testRecord = new WARCRecord();
		
		Assert.assertNull(testRecord.getContentBlock());
		Assert.assertNull(testRecord.getHeaders());
	}
	
	@Test
	public void outputTest(){
		testtype = "type:Test";
		testHeaders = new HashMap<String,String>();
		testCBlock = new ArrayList<String>();
		
		testHeaders.put("TestKeyA", "TestValueA");
		testCBlock.add("TestCvalue1");
	
		StringBuffer testbuffer = new StringBuffer();
		
		WARCRecord testRecord = new WARCRecord();
		testRecord.setContentBlock(testCBlock);
		testRecord.setHeaders(testHeaders);
		testRecord.setType(testtype);
		
		
		testbuffer.append("\n");
		testbuffer.append(testtype);
		testbuffer.append("\n");
		testbuffer.append("TestKeyA");
		testbuffer.append("TestValueA\n");
		testbuffer.append("\n");
		testbuffer.append("TestCvalue1");
		
		Assert.assertEquals(testbuffer.toString(),testRecord.toString());
	}
	
	
	@Test
	public void WritableSerializationForMRTest() throws Exception{

		WARCRecord testRecordWritableOut = new WARCRecord();
		WARCRecord testRecordWritableIn = new WARCRecord();
		ByteArrayOutputStream byteStreamOut = new ByteArrayOutputStream();
	    DataOutput dataOut = new DataOutputStream(byteStreamOut);
	   
	    String type = "type:Test";
		Map<String,String> testHeaders = new HashMap<String,String>();
		List<String> testCBlock = new ArrayList<String>();
		testHeaders.put("TestKeyA", "TestValueA");
		testCBlock.add("TestCvalue1");
		testRecordWritableOut.setContentBlock(testCBlock);
		testRecordWritableOut.setHeaders(testHeaders);
		testRecordWritableOut.setType(type);
		testRecordWritableOut.write(dataOut);
		
		ByteArrayInputStream byteStreamIn = new ByteArrayInputStream(byteStreamOut.toByteArray());
		DataInput dataIn = new DataInputStream(byteStreamIn);
		testRecordWritableIn.readFields(dataIn);
		
		
		Assert.assertThat(testRecordWritableOut, new ReflectionEquals(testRecordWritableIn));
		Assert.assertEquals(testRecordWritableOut.toString(), testRecordWritableIn.toString());
		Assert.assertEquals(testRecordWritableOut.getType(), testRecordWritableIn.getType());
		Assert.assertEquals(testRecordWritableOut.getContentBlock(), testRecordWritableIn.getContentBlock());
		Assert.assertEquals(testRecordWritableOut.getHeaders(), testRecordWritableIn.getHeaders());
		
	}
	

	
	

}
