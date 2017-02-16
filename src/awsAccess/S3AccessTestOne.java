package awsAccess;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import warc.WARCRecordBuilder;
import warc.WARCRecordBuilder.streamType;

import com.amazonaws.services.s3.AmazonS3Client;

import java.io.IOException;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;



public class S3AccessTestOne {
/**
 * Class to test basic access to s3 - will be basis to server side control of project including creating/destroying buckets,
 * moving data around and emailing the end user.
 */
	
	/**
	 * This sample demonstrates how to make basic requests to Amazon S3 using the
	 * AWS SDK for Java.
	 * <p>
	 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
	 * account, and be signed up to use Amazon S3. For more information on Amazon
	 * S3, see http://aws.amazon.com/s3.
	 * <p>
	 * Fill in your AWS access credentials in the provided credentials file
	 * template, and be sure to move the file to the default location
	 * (/Users/matthorridge/.aws/credentials) where the sample code will load the credentials from.
	 * <p>
	 * <b>WARNING:</b> To avoid accidental leakage of your credentials, DO NOT keep
	 * the credentials file in your source directory.
	 *
	 * http://aws.amazon.com/security-credentials
	 * @throws IOException 
	 */
	
	
	public static void main(String[] args) throws IOException {
		/*
		//credentials
		
		AWSCredentials creds = null;
		
		//try to get credentials from SDKTestUser locally
		try{
			creds = new ProfileCredentialsProvider("SDKTestUser").getCredentials();
		}
		catch (Exception e){
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. " +
		                    "Please make sure that your credentials file is at the correct " +
		                    "location (/Users/matthorridge/.aws/credentials), and is in valid format.",
		                    e);
		}
		
		

		final AmazonS3 s3 = new AmazonS3Client(creds);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		s3.setRegion(usWest2);
		
		
		String bucketName = "sdktestbucketmh" + UUID.randomUUID();
		String key = "MyObjectKey";
		
		
		try{
			Bucket b = s3.createBucket(bucketName);
		} catch (AmazonServiceException e){
			System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
		System.out.println("created bucket: "+ bucketName );
				
		
	*/	
		
		
		//S3DataStream testy = new S3DataStream();
		//testy.run();
		
		WARCRecordBuilder j = new WARCRecordBuilder();
		
		j.Stream(streamType.GZIP, j.getSegmentExtractor().extractSegment("aws-publicdatasets", "common-crawl/crawl-data/CC-MAIN-2013-20/segments/1368699755211/warc/CC-MAIN-20130516102235-00011-ip-10-60-113-184.ec2.internal.warc.gz").getObjectContent());
		
		
		
		
		//System.out.println(test.getHeaders());
		//System.out.println(test.get(15).getHeaders());
		//System.out.println(test.get(15).getContentBlock());
		
		
		
		/*
		for(int i = 0; i < test.size(); i++){
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(test.get(i).getHeaders());
			System.out.println(test.get(i).getContentBlock());
		}
		
		
		
		System.out.println(test.size());
		
		
		
*/		
		
	}
	
	
	
//Future iterations will require use of EMR client builder - this file will use basic s3 access.	
	
	
	
	
}
