package awsAccess;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
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
	
	/**
	 * Constructor
	 */
	
	 private final AmazonS3 s3;
	
	public s3Access(String User){
		//Blank for now
		
		
		String AccessKey = "AKIAJL45LAS6LGGHQESQ";
		String SecretKey = "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";

		
		

		BasicAWSCredentials cred= new BasicAWSCredentials(AccessKey, SecretKey);
		//change this sooooncret
		s3 = new AmazonS3Client(cred);
		
	
		
		Region s3Region = Region.getRegion(Regions.US_EAST_1); //Default Region US_EAST_1
		s3.setRegion(s3Region);

	}
	
	//Method to access the s3 
	public AmazonS3 getS3Instance(){
		return s3;
	}
	
	//Setup up credentials for user
	private AWSCredentials credSetup(String User){
		
		AWSCredentials creds = null;
		
		try{
			//Grab credentials for specific user
			//In testing/practice user will almost always be "SDKTestUser"
			creds = new ProfileCredentialsProvider(User).getCredentials();
			
		}
		catch (Exception e){
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. " +
		                    "Please make sure that your credentials file is at the correct " +
		                    "location (/Users/matthorridge/.aws/credentials), and is in valid format.",
		                    e);
		}
		
		return creds;
		
	}

}
