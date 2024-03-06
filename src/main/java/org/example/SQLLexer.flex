package org.example;
import java.io.IOException;

%%

%class SQLLexer

%type Token

%scanerror  LexicalException

%unicode

%{
    // Aquí puedes importar clases necesarias y definir variables globales si es necesario
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

Identifier = [a-zA-Z_][a-zA-Z0-9_]*
DecIntegerLiteral = [0-9]+

%state STRING

%%

/* Now we define tokens for keywords, identifiers, literals, and operators in our grammar. */
/* Cuando la entrada coincide con la expresión regular a la izquierda, se realiza la acción a la derecha. */

<YYINITIAL> "SELECT"            { return new Token(TokenType.SELECT, yytext()); }
<YYINITIAL> "FROM"              { return new Token(TokenType.FROM, yytext()); }
<YYINITIAL> "FILTER"            { return new Token(TokenType.FILTER, yytext()); }
<YYINITIAL> "WHERE"             { return new Token(TokenType.WHERE, yytext()); }
<YYINITIAL> "*"                 { return new Token(TokenType.STAR, yytext()); }
<YYINITIAL> ","                 { return new Token(TokenType.COMMA, yytext()); }
<YYINITIAL> ";"                 { return new Token(TokenType.SEMICOLON, yytext()); }
<YYINITIAL> "="                 { return new Token(TokenType.EQUALS, yytext()); }

<YYINITIAL> {Identifier}        { return new Token(TokenType.IDENTIFIER, yytext()); }
<YYINITIAL> {DecIntegerLiteral} { return new Token(TokenType.INTEGER_LITERAL, yytext()); }

/* whitespace */
<YYINITIAL> {WhiteSpace}        { /* ignore */ }

/* error fallback */
<YYINITIAL> .                   { throw new LexicalException("Illegal character <" + yytext() + ">"); }

