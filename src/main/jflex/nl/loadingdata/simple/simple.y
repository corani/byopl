%token NAME NUMBER

%%

sequence : pair sequence 
         | 
         ;

pair : NAME NUMBER ;

%%

int yylex() {
    return simple.yylex();
}

void yyerror(String s) {
    simple.lexErr(s);
}
