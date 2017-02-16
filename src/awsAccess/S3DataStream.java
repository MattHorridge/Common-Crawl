package awsAccess;



import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;



public class S3DataStream {
	
	//TODO: remove this after pipeline gets working
	String key = "common-crawl/crawl-data/CC-MAIN-2013-20/segments/1368699755211/warc/CC-MAIN-20130516102235-00011-ip-10-60-113-184.ec2.internal.warc.gz";
	String key2 = "common-crawl/crawl-data/CC-MAIN-2013-48/segments/1386163035819/warc/CC-MAIN-20131204131715-00000-ip-10-33-133-15.ec2.internal.warc.gz";
	String bucket = "aws-publicdatasets";
	//grab the segment from the public dataset
	
	S3Object ccSegment; //Specific "segment" object described by key and bucket
	
	
	public void run() throws IOException{
		
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
			Region usWest2 = Region.getRegion(Regions.US_EAST_1);
			s3.setRegion(usWest2);
			
			// get object
			ccSegment = s3.getObject(new GetObjectRequest(bucket, key));
			InputStream objectData = ccSegment.getObjectContent();
			// Process the objectData stream.
			
			
			InputStream gzipStream = new GZIPInputStream(objectData);
			
			Reader decoder = new InputStreamReader(gzipStream, "UTF-8");
			
			BufferedReader segmentReader = new BufferedReader(decoder);
			
			
			String nextLine, line;
			String TypeString = "WARC-Type: response";
			
			Matcher TypeMatch;
			
			Pattern typePattern = Pattern.compile(TypeString);
			
			try {
				while ((line = segmentReader.readLine())!= null){
					TypeMatch = typePattern.matcher(line);
					
					//System.out.println(line);
					
					if(TypeMatch.find()){
						System.out.println(line);
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Success!");
			
			
			
			
			
		
				
				
	}
	
}





