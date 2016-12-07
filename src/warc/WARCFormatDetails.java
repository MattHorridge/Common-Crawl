package WARC;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public interface WARCFormatDetails {
	
	
	//Interface to help handle HTTP responses in WARC file
	
	
	
	
	
	
	//Regex strings
	final String REGEX_BLANK = "^\\s";
	final String REGEX_RECORD_END = "Connection: close";
	final String REGEX_WARC = "(WARC.{0,20}:\\s)((.|\n)+)";
	final String REGEX_CONTENT_TYPE = "(Content-Type.{0,20}:\\s)((.|\n)+)";
	final String REGEX_CONTENT_LENGTH = "(Content-Length.{0,20}:\\s)((.|\n)+)";
	
	
	//Compiled Regex Patterns
	final Pattern WARC_MATCH_PATTERN = Pattern.compile(REGEX_WARC);
	final Pattern WARC_CONTENT_TYPE_PATTERN = Pattern.compile(REGEX_CONTENT_TYPE);
	final Pattern WARC_CONTENT_LENGTH_PATTERN = Pattern.compile(REGEX_CONTENT_LENGTH);
	final Pattern REGEX_BLANK_PATTERN = Pattern.compile(REGEX_BLANK);

	
	
	//Header Fields
	final String WARC_TYPE = "WARC-Type";
	final String WARC_DATE = "WARC-Date";
	String WARC_ID = "WARC-Record-ID";
	String WARC_CONTENT_LENGTH = "Content-Length";
	String WARC_CONTENT_TYPE = "Content-Type";
	String WARC_CONCURRENT_TO = "WARC-Concurrent-To";
	String WARC_BLOCK_DIGEST = "WARC-Payload-Digest" ;
	String WARC_PAYLOAD = "WARC-IP-Address";
	String WARC_IP = "WARC-IP-Address";
	String WARC_REFERS_TO = "WARC-Refers-To";
	String WARC_TARGET_URI = "WARC-Target-URI";
	String WARC_TRUNCATED = "WARC-Truncated";
	String WARC_WARCINFO_ID = "WARC-Warcinfo-ID";
	String WARC_FILENAME ="WARC-Filename";
	String WARC_IDENTIFIED_PAYLOAD_TYPE = "WARC-Identified-Payload-Type";
	
	
	
	
		
	
	

	//Responses are only relevant to this project
	enum WARCType{
		warcinfo,
		request,
		response,
		metadata
	
	}
	
	
	
	
	
	//Array of all Header Fields in a WARC Record
	public static final String[] HeaderFields = {
			//"WARC-Type",
			"WARC-Date",
			"WARC-Record-ID",
			"Content-Length",
			"Content-Type",
			"WARC-Concurrent-To",
			"WARC-Block-Digest",
			"WARC-Payload-Digest",
			"WARC-IP-Address",
			"WARC-Refers-To",
			"WARC-Target-URI",
			"WARC-Truncated",
			"WARC-Warcinfo-ID",
			"WARC-Filename", //warcinfo only
			"WARC-Identified-Payload-Type"
			
	};
	
	
	

	
	
	
	
	
	
	
	
	
	
		
	

}
