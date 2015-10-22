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

import citrus.Environment;

public class Closure extends SchemeObject implements Applicable {
    private Environment env;
    private SchemeList params;
    private SchemeObject body;

    public Closure(Environment env, SchemeList params, SchemeObject body) {
        this.env = env;
        this.params = params;
        this.body = new Pair(new Syntax(Syntax.Special.BEGIN), body);
    }

    public SchemeObject apply(SchemeList args) {
        Environment newEnv = new Environment(env, params, args);
        return new Pair(body, newEnv);
    }
}
