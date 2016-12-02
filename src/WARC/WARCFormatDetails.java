package WARC;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public interface WARCFormatDetails {
	
	
	//Interface to help handle HTTP responses in WARC file
	
	
	
	final String REGEX_BLANK = "^\\s";
	final String FULL_LINE = "(.|\n)+)";
	final String LOOK_BEHIND_ONE = "(?<=";
	final String LOOK_BEHIND_TWO = "\\s)";
	final String REGEX_RECORD_END = "Connection: close";
	
	
	
	
	
	final String WARC_TYPE = "WARC-Type";
	String WARC_DATE = "WARC-Date";
	String WARC_ID = "WARC-Record-ID";
	String WARC_CONTENT_LENGTH = "Content-Length";
	String WARC_CONTENT_TYPE = "Content-Type";
	//String WARC_Concurrent-To
	/*
	
	String WARC_Block_Digest
	String WARC_Payload
	String WARC_IP
	String WARC_Refers_to
	String WARC_Target_URI
	String WARC_Truncated
	String WARC_warcinfo_ID
	String WARC_Filename
	String
	*/
	
	final Pattern WARC_DATE_PATTERN = Pattern.compile(WARC_DATE);
	
	
	
	List Lookbehind = new ArrayList<String>();
	
	
	

	//Responses are only relevant to this project
	enum WARCType{
		warcinfo,
		request,
		response,
		metadata
	
	}
	
	
	
	
	
	//Array of all Header Fields in a WARC Record
	public static String[] HeaderFields = {
			"WARC-Type",
			"WARC-Date",
			"WARC-Record-ID",
			"Content-Length",
			"Content-Type",
			"WARC-Concurrent-To ",
			"WARC-Block-Digest ",
			"WARC-Payload-Digest",
			"WARC-IP-Address",
			"WARC-Refers-To",
			"WARC-Target-URI ",
			"WARC-Truncated",
			"WARC-Warcinfo-ID",
			"WARC-Filename", //warcinfo only
			"WARC-Identified-Payload-Type"
			
	};
	
	
	
	
	
	
	
	
	
	
		
	

}
