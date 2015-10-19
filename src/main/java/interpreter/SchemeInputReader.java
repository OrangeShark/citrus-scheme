package interpreter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class SchemeInputReader extends InputStreamReader {
    private int parenCount;
    private boolean end;
    private final static int EOF = -1;

    public SchemeInputReader(InputStream in) {
        super(in);
        parenCount = 0;
        end = false;
    }

    public int read() throws IOException {
        int ch = super.read();
        switch(ch) {
            case '(':
                parenCount++;
                break;
            case ')':
                parenCount--;
                break;
            case '\n':
            case '\r':
                ch = parenCount == 0 ? EOF : ch;
                break;
        }
        return ch;
    }

    public int read(char[] cbuf, int offset, int length) throws IOException {
        if(end) return -1;
        int ch = 0;
        int chRead = 0;
        while(offset <= length && (ch = read()) != EOF) {
            cbuf[offset] = (char)ch;

            offset++;
            chRead++;
        }
        end = ch == EOF;
        return chRead;
    }

    public void reset() {
        parenCount = 0;
        end = false;
    }

}
