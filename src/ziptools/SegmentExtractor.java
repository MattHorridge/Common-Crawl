package ziptools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import com.amazonaws.services.s3.model.S3Object;

import awsAccess.s3Access;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	
	/**
	 * Blank Constructor
	 */
	public SegmentExtractor(){
	}
	
	public SegmentExtractor(String UserName){
		
		User = UserName;
		pipeLine = new s3Access(User);
		encoding = "UTF-8"; //Default format for project is UTF-8 
		//TODO: refactor this a bit - there may be other formats to consideer
	}
	
	
	/**
	 * Pulls desired segment object from s3bucket
	 * @param Bucket
	 * @param Key
	 * @return
	 */
	public S3Object extractSegment(String Bucket, String Key){
		S3Object ccSegment = pipeLine.getS3Instance().getObject(Bucket,Key);
		return ccSegment;	
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
	
	public void setKey(String k){
		key = k;
	}
	
	public void setBucket(String b){
		bucket = b;
	}
	
	public void setEncoding(String encodingType){
		encoding = encodingType;
	}
	
	public String getEncoding(){
		return encoding;
	}

}
