package main.warc;

import java.util.regex.*;

public interface WARCFormatDetails {
	

	//Regex strings
	final String REGEX_BLANK = "^\\s";
	final String REGEX_RECORD_END = "Connection: close";
	
	//Regex for matching a line begining with WARC.. and then
	//Splitting it into two components, the WARC prefix and data presented after
	final String REGEX_RECORD_START = "(WARC.{0,20}:\\s)((.|\n)+)";
	//Used to split the WARC-TARGET-URI and grab right URL
	final String REGEX_URI_SPLIT = "(WARC-Target-URI:\\s)((.|\n)+)";
	final String REGEX_CONTENT_TYPE = "(Content-Type.{0,20}:\\s)((.|\n)+)";
	final String REGEX_CONTENT_LENGTH = "(Content-Length.{0,20}:\\s)((.|\n)+)";
	
	
	//Compiled Patterns
	final Pattern WARC_RECORD_START_PATTERN = Pattern.compile(REGEX_RECORD_START);
	final Pattern WARC_CONTENT_TYPE_PATTERN = Pattern.compile(REGEX_CONTENT_TYPE);
	final Pattern WARC_CONTENT_LENGTH_PATTERN = Pattern.compile(REGEX_CONTENT_LENGTH);
	final Pattern REGEX_BLANK_PATTERN = Pattern.compile(REGEX_BLANK);
	final Pattern WARC_URI_SPLIT_PATTERN = Pattern.compile(REGEX_URI_SPLIT);

	//Header Fields
	final String WARC_TYPE = "WARC-Type";
	final String WARC_DATE = "WARC-Date";
	final String WARC_ID = "WARC-Record-ID";
	final String WARC_CONTENT_LENGTH = "Content-Length";
	final String WARC_CONTENT_TYPE = "Content-Type";
	final String WARC_CONCURRENT_TO = "WARC-Concurrent-To";
	final String WARC_BLOCK_DIGEST = "WARC-Payload-Digest" ;
	final String WARC_PAYLOAD = "WARC-IP-Address";
	final String WARC_IP = "WARC-IP-Address";
	final String WARC_REFERS_TO = "WARC-Refers-To";
	final String WARC_TARGET_URI = "WARC-Target-URI";
	final String WARC_TRUNCATED = "WARC-Truncated";
	final String WARC_WARCINFO_ID = "WARC-Warcinfo-ID";
	final String WARC_FILENAME ="WARC-Filename";
	final String WARC_IDENTIFIED_PAYLOAD_TYPE = "WARC-Identified-Payload-Type";
	
	
	//Responses are only relevant to this project
	enum WARCType{
		warcinfo,
		request,
		response,
		metadata
	}
	
	public static final String[] StringWARCType = {
		"warcinfo",
		"request",
		"response",
		"metadata"
	};
	
		
	//Array of all Header Fields in a WARC Record
	public static final String[] HeaderFields = {
			"WARC-Type",
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
