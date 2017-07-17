package main.awstools;

import java.io.IOException;
import java.io.InputStream;

public class GZIPWrapper {
	
	public static class GZIPWrapperInputStream extends InputStream {
        private InputStream is;

        public GZIPWrapperInputStream(InputStream inputstream) {
            is = inputstream;
        }

        public int read() throws IOException {
            return(is.read());
        }

        public int read(byte[] b) throws IOException {
            return(is.read(b));
        }

        public int read(byte[] b, int off, int len) throws IOException {
            return(is.read(b, off, len));
        }

        public void close() throws IOException {
            is.close();
        }

        public int available() throws IOException {
         
            int a = is.available();
            if (a == 0) {
                return(1);
            } else {
                return(a);
            }
        }
    }

}
