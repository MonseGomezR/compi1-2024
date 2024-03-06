package org.example;

%%

%{
%}

%class SQLLexer
%unicode
%public
%cup
%{
  private java_cup.runtime.Scanner scanner;
  public SQLLexer(java_cup.runtime.Scanner scanner) {
    this.scanner = scanner;
  }
%}

%state IN_STRING

STRING_LITERAL = '"' [^"\n]* '"'
NUMBER_LITERAL = [0-9]+
IDENTIFIER = [a-zA-Z_][a-zA-Z0-9_]*
WS = [ \t\r\n]+

%%

<YYINITIAL> {WS}    { /* ignore whitespace */ }
<YYINITIAL> {STRING_LITERAL}  { return new java_cup.runtime.Symbol(SQLParserSym.STRING_LITERAL, yytext()); }
<YYINITIAL> {NUMBER_LITERAL}  { return new java_cup.runtime.Symbol(SQLParserSym.NUMBER_LITERAL, yytext()); }
<YYINITIAL> {IDENTIFIER}      { return new java_cup.runtime.Symbol(SQLParserSym.IDENTIFIER, yytext()); }
<YYINITIAL> {";"}             { return new java_cup.runtime.Symbol(SQLParserSym.SEMICOLON); }
<YYINITIAL> {"*"}             { return new java_cup.runtime.Symbol(SQLParserSym.STAR); }
<YYINITIAL> {","}             { return new java_cup.runtime.Symbol(SQLParserSym.COMMA); }
<YYINITIAL> {"="}             { return new java_cup.runtime.Symbol(SQLParserSym.EQUALS); }
<YYINITIAL> {"<"}             { return new java_cup.runtime.Symbol(SQLParserSym.LESS_THAN); }
<YYINITIAL> {">"}             { return new java_cup.runtime.Symbol(SQLParserSym.GREATER_THAN); }
<YYINITIAL> {"<="}            { return new java_cup.runtime.Symbol(SQLParserSym.LESS_THAN_OR_EQUALS); }
<YYINITIAL> {">="}            { return new java_cup.runtime.Symbol(SQLParserSym.GREATER_THAN_OR_EQUALS); }
<YYINITIAL> {"<>"}            { return new java_cup.runtime.Symbol(SQLParserSym.NOT_EQUALS); }
<YYINITIAL> {"SELECT"}        { return new java_cup.runtime.Symbol(SQLParserSym.SELECT); }
<YYINITIAL> {"FROM"}          { return new java_cup.runtime.Symbol(SQLParserSym.FROM); }
<YYINITIAL> {"FILTER"}        { return new java_cup.runtime.Symbol(SQLParserSym.FILTER); }
<YYINITIAL> {":"}             { yybegin(IN_STRING); }
<IN_STRING> {"\""}           { yybegin(YYINITIAL); return new java_cup.runtime.Symbol(SQLParserSym.STRING_LITERAL, yytext()); }

