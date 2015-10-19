/*
 * Copyright 2015 Erik Edrosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
