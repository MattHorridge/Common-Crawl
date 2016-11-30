package WARC;

import java.util.regex.*;

public interface WARCFormatDetails {
	
	
	//Interface to help handle HTTP responses in WARC file
	
	
	
	String regexblank = "^\\s";
	
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
