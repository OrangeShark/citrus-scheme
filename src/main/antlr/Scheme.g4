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
grammar Scheme;

@header {
package scheme.antlr;
}

// Parser rules

datum : simpleDatum
      | compoundDatum
      ;

simpleDatum : bool
            | number
            | character
            | string
            | identifier
            ;

compoundDatum : list
              | vector
              | byteVector
              | quote
              | abbreviation
              ;

list : '(' datum* ')'
     | '(' datum+ '.' datum ')'
     ;

bool : TRUE              # True
     | FALSE             # False
     ;

number : NUM_10             # Num10
       ;

character : CHARACTER;

string : STRING;

identifier : IDENTIFIER;

vector : '#(' datum* ')';

byteVector : '#u8(' number* ')';

quote : '\'' datum;

abbreviation : abbrevPrefix datum;

abbrevPrefix : '`' | ',' | ',@';

// Lexer rules

fragment
INTRALINE_WHITESPACE : [ \t];
fragment
LINE_ENDING : '\n' | '\r\n' | '\r';
LPAREN : '(';
RPAREN : ')';
VECTORPAREN : '#(';
BYTEPAREN : '#u8(';
QUOTE : '\'';
QUASIQUOTE : '`';
UNQUOTE : ',';
SPLICING_UNQUOTE : ',@';
DOT : '.';

IDENTIFIER : INITIAL SUBSEQUENT*
           | '|' SYMBOL_ELEMENT* '|'
           | PECULIAR_IDENTIFIER
           ;

fragment
INITIAL : LETTER
        | SPECIAL_INITIAL
        ;
fragment
LETTER : [a-zA-Z];
fragment
SPECIAL_INITIAL : '!' | '$' | '%' | '*' | '/' | ':' | '<'
                | '=' | '>' | '?' | '^' | '_' | '~';
fragment
SUBSEQUENT : INITIAL | DIGIT | SPECIAL_SUBSEQUENT;
fragment
DIGIT : [0-9];
fragment
HEX_DIGIT : DIGIT | [a-f];
fragment
EXPLICIT_SIGN : '+' | '-';
fragment
SPECIAL_SUBSEQUENT : EXPLICIT_SIGN | '.' | '@';
fragment
INLINE_HEX_ESCAPE : '\\x' HEX_SCALAR_VALUE ';';
fragment
HEX_SCALAR_VALUE : HEX_DIGIT+;
fragment
MNEMONIC_ESCAPE : '\\a' | '\\b' | '\\t' | '\\n' | '\\r';
fragment
PECULIAR_IDENTIFIER : EXPLICIT_SIGN
                    | EXPLICIT_SIGN SIGN_SUBSEQUENT SUBSEQUENT*
                    | EXPLICIT_SIGN '.' DOT_SUBSEQUENT SUBSEQUENT*
                    | '.' DOT_SUBSEQUENT SUBSEQUENT*
                    ;
fragment
DOT_SUBSEQUENT : SIGN_SUBSEQUENT
               | '.'
               ;
fragment
SIGN_SUBSEQUENT : INITIAL | EXPLICIT_SIGN | '@';
fragment
SYMBOL_ELEMENT : ~[|\\] | INLINE_HEX_ESCAPE | MNEMONIC_ESCAPE | '\\|';

TRUE : '#t'
     | '#true'
     ;

FALSE : '#f'
      | '#false'
      ;

CHARACTER : '#\\' . | '#\\' CHARACTER_NAME | '#\\x' HEX_SCALAR_VALUE;
fragment
CHARACTER_NAME : 'alarm' | 'backspace' | 'delete' | 'escape'
               | 'newline' | 'null' | 'return' | 'space' | 'tab';

STRING : '"' STRING_ELEMENT* '"';
fragment
STRING_ELEMENT : ~["\\]
               | MNEMONIC_ESCAPE
               | '\\"'
               | '\\\\'
               | '\\' INTRALINE_WHITESPACE* LINE_ENDING INTRALINE_WHITESPACE*
               | INLINE_HEX_ESCAPE
               ;

// Numbers

// Binary
NUM_2 : PREFIX_2 COMPLEX_2;
fragment
COMPLEX_2 : REAL_2 | REAL_2 '@' REAL_2 | REAL_2 '+' UREAL_2 'i'
          | REAL_2 '-' UREAL_2 'i' | REAL_2 '+' 'i' | REAL_2 '-' 'i'
          | REAL_2 INFNAN 'i' | '+' UREAL_2 'i' | '-' UREAL_2 'i'
          | INFNAN 'i' | '+' 'i' | '-' 'i'
          ;
fragment
REAL_2 : SIGN? UREAL_2 | INFNAN;
fragment
UREAL_2 : UINTEGER_2
        | UINTEGER_2 '/' UINTEGER_2
        ;
fragment
UINTEGER_2 : DIGIT_2+;
fragment
PREFIX_2 : RADIX_2 EXACTNESS?
         | EXACTNESS? RADIX_2
         ;
// Octal
NUM_8 : PREFIX_8 COMPLEX_8;
fragment
COMPLEX_8 : REAL_8 | REAL_8 '@' REAL_8 | REAL_8 '+' UREAL_8 'i'
          | REAL_8 '-' UREAL_8 'i' | REAL_8 '+' 'i' | REAL_8 '-' 'i'
          | REAL_8 INFNAN 'i' | '+' UREAL_8 'i' | '-' UREAL_8 'i'
          | INFNAN 'i' | '+' 'i' | '-' 'i'
          ;
fragment
REAL_8 : SIGN? UREAL_8 | INFNAN;
fragment
UREAL_8 : UINTEGER_8
        | UINTEGER_8 '/' UINTEGER_8
        ;
fragment
UINTEGER_8 : DIGIT_8+;
fragment
PREFIX_8 : RADIX_8 EXACTNESS?
         | EXACTNESS? RADIX_8
         ;
// Decimal
NUM_10 : PREFIX_10 COMPLEX_10;
fragment
COMPLEX_10 : REAL_10 | REAL_10 '@' REAL_10 | REAL_10 '+' UREAL_10 'i'
           | REAL_10 '-' UREAL_10 'i' | REAL_10 '+' 'i' | REAL_10 '-' 'i'
           |    REAL_10 INFNAN 'i' | '+' UREAL_10 'i' | '-' UREAL_10 'i'
           | INFNAN 'i' | '+' 'i' | '-' 'i'
           ;
fragment
REAL_10 : SIGN? UREAL_10 | INFNAN;
fragment
UREAL_10 : UINTEGER_10
         | UINTEGER_10 '/' UINTEGER_10
         | DECIMAL_10
         ;
fragment
DECIMAL_10 : UINTEGER_10 SUFFIX?
           | '.' DIGIT_10+ SUFFIX?
           | DIGIT_10+ '.' DIGIT_10* SUFFIX?
           ;
fragment
UINTEGER_10 : DIGIT_10+;
fragment
PREFIX_10 : RADIX_10? EXACTNESS?
          | EXACTNESS? RADIX_10?
          ;
// Hexadecimal
NUM_16 : PREFIX_16 COMPLEX_16;
fragment
COMPLEX_16 : REAL_16 | REAL_16 '@' REAL_16 | REAL_16 '+' UREAL_16 'i'
           | REAL_16 '-' UREAL_16 'i' | REAL_16 '+' 'i' | REAL_16 '-' 'i'
           | REAL_16 INFNAN 'i' | '+' UREAL_16 'i' | '-' UREAL_16 'i'
           | INFNAN 'i' | '+' 'i' | '-' 'i'
           ;
fragment
REAL_16 : SIGN? UREAL_16 | INFNAN;
fragment
UREAL_16 : UINTEGER_16
         | UINTEGER_16 '/' UINTEGER_16
         ;
fragment
UINTEGER_16 : DIGIT_16+;
fragment
PREFIX_16 : RADIX_16 EXACTNESS?
         | EXACTNESS? RADIX_16
         ;
fragment
INFNAN : '+inf.0' | '-inf.0' | '+nan.0' | '-nan.0';

fragment
SUFFIX : EXPONENT_MARKER SIGN? DIGIT_10+;

fragment
SIGN : '+' | '-';

fragment
EXPONENT_MARKER : 'e';

fragment
EXACTNESS : '#i' | '#e';

fragment
RADIX_2 : '#b';

fragment
RADIX_8 : '#o';

fragment
RADIX_10 : '#d';

fragment
RADIX_16 : '#x';

fragment
DIGIT_2 : '0' | '1';

fragment
DIGIT_8 : [0-7];

fragment
DIGIT_10 : DIGIT;

fragment
DIGIT_16 : DIGIT_10 | [a-f];

TRASH : (INTRALINE_WHITESPACE | LINE_ENDING ) -> channel(HIDDEN);
