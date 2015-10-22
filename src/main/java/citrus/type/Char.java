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
package citrus.type;

public class Char extends SchemeObject {
    private final char value;

    public Char(char c) {
        this.value = c;
    }

    public String toString() {
        String str = "";
        switch(value) {
            case '\u0007':
                str = "alarm";
                break;
            case '\u0008':
                str = "backspace";
                break;
            case '\u007F':
                str = "delete";
                break;
            case '\u001B':
                str = "escape";
                break;
            case '\n':
                str = "newline";
                break;
            case '\0':
                str = "null";
                break;
            case '\r':
                str = "return";
                break;
            case ' ':
                str = "space";
                break;
            case '\t':
                str = "tab";
            default:
                str = String.valueOf(value);
        }
        return "#\\" + str;
    }
}
