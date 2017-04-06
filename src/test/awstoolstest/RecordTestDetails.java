/**
 * Class to help test the builder + extractor classes/methods
 */

package test.awstoolstest;
import main.warc.WARCFormatDetails;

public interface RecordTestDetails extends WARCFormatDetails {

	static final String RESPONSE = WARC_TYPE + ": response";
	static final String REQUEST = WARC_TYPE + ": request";
	static final String METADATA = WARC_TYPE + ": metadata";
	
	static final String TARGET = "http://www.exampletest.com";
	
	static final String WARCINFO ="<urn:uuid:9c75dd42-a5c4-4dc5-b04a-75befff86d84>";
	static final String WARCTARGETURI = "WARC-Target-URI: http://www.exampletest.com";
	
	static final String HEADER_LINE_ONE = "HTTP/1.1 200 OK";
	static final String HEADER_LINE_TWO = "Date: Fri, 22 May 2015 13:42:09 GMT";
	static final String HEADER_LINE_THREE = "Server: Apache";

}
