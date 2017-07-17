package main.awstools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;


/**
 * Class to handle S3 Access
 * Handles Credentials and pipeline setup
 * 
 * To be created once to open and access s3 pipeline
 * @author matthorridge
 *
 */

public class s3Access {

	private final AmazonS3 s3;
	private static final Log LOG = LogFactory.getLog(s3Access.class);
	
	
	public s3Access(AWSCredentials creds){
		s3 = new AmazonS3Client(creds);
		Region s3Region = Region.getRegion(Regions.US_EAST_1); //Default Region US_EAST_1
		s3.setRegion(s3Region);

	}
	
	public s3Access(String key, String value){
		s3 = new AmazonS3Client(credSetup(key,value));
		Region s3Region = Region.getRegion(Regions.US_EAST_1); //Default Region US_EAST_1
		s3.setRegion(s3Region);
	}
	

	public AmazonS3 gets3Instance(){
		return s3;
	}
	
	
	private AWSCredentials credSetup(String key, String value){
		
		AWSCredentials creds = null;
		
		try {
		    creds = new BasicAWSCredentials(key, value);
		}
		catch (AmazonClientException ac){
			
			
			LOG.info("Amazon Client Exception - Correct Credentials not correctly entered or were wrong");
			LOG.info(ac.getCause());
			LOG.info(ac.getMessage());
		}
		
		return creds;
		
	}

}
