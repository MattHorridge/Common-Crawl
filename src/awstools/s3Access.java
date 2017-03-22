package awstools;

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
	
	
	
	public s3Access(AWSCredentials creds){
		//credSetup("AKIAJL45LAS6LGGHQESQ", "xDsZNHswz+ZMap5Y3o2+acDiI8ZeP7aW3TNtToE6")
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
		    //creds = new PropertiesCredentials(new File("resources/creds/credentials"));
		    creds = new BasicAWSCredentials(key, value);
		}
		catch (Exception e){
			throw new AmazonClientException(
					"Credentials were not correctly entered - provide your correct credentails in the "
					+ "specfied format",
		                    e);
		}
		
		return creds;
		
	}

}
