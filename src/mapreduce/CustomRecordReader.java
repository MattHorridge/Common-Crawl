package mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;

public class CustomRecordReader extends KeyValueLineRecordReader {

	public CustomRecordReader(Configuration conf) throws IOException {
		super(conf);
		// TODO Auto-generated constructor stub
	}
	
	
	


}
