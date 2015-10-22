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
package citrus;

import citrus.scheme.Base;
import citrus.type.*;

import java.lang.StringBuilder;
import java.io.IOException;
import java.io.Reader;

public class Main {
    private static int BUFFER_SIZE = 1024;

    private static String readInput(Reader reader) throws IOException {
        char[] buffer = new char[BUFFER_SIZE];
        StringBuilder strBuilder = new StringBuilder();
        int numRead = 0;
        do {
            numRead = reader.read(buffer, 0, BUFFER_SIZE);
            strBuilder.append(buffer, 0, numRead);
        } while(numRead == BUFFER_SIZE);

        return strBuilder.toString();
    }

    public static void main(String[] args) throws Exception {
        Interpreter interpreter = new Interpreter();

        Environment env = new Environment();
        Library lib = new Base();
        SchemeInputReader reader = new SchemeInputReader(System.in);
        lib.importLib(env);
        int resultCount = 1;
        while(true) {
            System.out.print("> ");
            String input = readInput(reader);
            SchemeObject obj = interpreter.read(input);
            SchemeObject result = interpreter.eval(obj, env);
            if(!(result instanceof Unspecified)) {
                env.define(new Symbol("$" + resultCount), result);
                System.out.println("$" + resultCount + " = " + result);
                resultCount++;
            }
            reader.reset();
        }
    }
}
