package WARC;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public interface WARCFormatDetails {
	
	
	//Interface to help handle HTTP responses in WARC file
	
	
	
	String REGEX_BLANK = "^\\s";
	String FULL_LINE = "(.|\n)+)";
	String Look_Behind_One = "(?<=";
	String LOOK_BEHIND_TWO = "\\s)";
	
	
	/*
	String WARC_Type = "WARC-Type";
	String WARC_Date = "WARC-Date";
	String WARC_ID = "WARC-Record-ID";
	String WARC_Content_Length = "Content-Length";
	String WARC_Content_Type
	String WARC_Concurrent-To
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
