package awstools;


import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.model.S3Object;


//Class for the extraction of gzipped WARC files

/**
 * Class to extract a specified segment from s3
 * Replaces S3DataStream
 * @author matthorridge
 *
 */


public class SegmentExtractor {

	private String key; //s3 public segment key
	private String bucket;	
	private String User;
	private s3Access pipeLine;
	private String encoding;
	private AWSCredentials creds;
	
	
	/**
	 * Blank Constructor
	 */
	public SegmentExtractor(){
	}
	
	public SegmentExtractor(AWSCredentials creds){
		//User = UserName;
		pipeLine = new s3Access(creds);
		encoding = "UTF-8"; //Default format for project is UTF-8 
	
	}
	
	
	/**
	 * Pulls desired segment object from s3bucket
	 * @param Bucket
	 * @param Key
	 * @return
	 */
	public S3Object extractSegment(String Bucket, String Key){
		try{
			S3Object ccSegment = pipeLine.gets3Instance().getObject(Bucket,Key);
			return ccSegment;
		}
		catch (AmazonServiceException as){
			
			 	System.out.println("Error Message:    " + as.getMessage());
	            System.out.println("HTTP Status Code: " + as.getStatusCode());
	            System.out.println("AWS Error Code:   " + as.getErrorCode());
	            System.out.println("Error Type:       " + as.getErrorType());
	            System.out.println("Request ID:       " + as.getRequestId());
		}
		catch (AmazonClientException ac) {
        
				System.out.println("Error: " + ac.getMessage());
		}
		
		return null;
	}
	/**
	 * Extract input stream form s3Object to then be parsed.
	 * For CC the inputstream will need a GZIP extraction
	 * @param segment
	 * @return
	 */
	public InputStream extractSegmentStream(S3Object segment) {
		return segment.getObjectContent();	
	}
	
	
	public String getKey(){
		return key;
	}
	
	public String getBucket(){
		return bucket;
	}
	
	public void setKey(String inputKey){
		key = inputKey;
	}
	
	public void setBucket(String inputBucket){
		bucket = inputBucket;
	}
	
	public void setEncoding(String encodingType){
		encoding = encodingType;
	}
	
	public String getEncoding(){
		return encoding;
	}

}
