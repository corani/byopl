package nl.loadingdata.simple;

import java.io.FileReader;
import java.io.IOException;

public class simple {
    static Yylex lex;
    public static int yylineno;
    public static token yylval;

    public static void main(String argv[]) throws Exception {
        lex = new Yylex(new FileReader(argv[0]));
        yylineno = 1;

        Parser par = new Parser();

        if (par.yyparse() != 0) {
            System.err.println("parse failed");
        } else {
            System.out.println("parse succeeded");
        }
    }

    public static int yylex() {
        try {
            return lex.yylex();
        } catch (IOException e) {
            return -1;
        }
    }
    public static String yytext() {
        return lex.yytext();
    }

    public static void lexErr(String s) {
        System.err.println(s + 
                ": line " + yylineno + 
                ": " + yytext());
        System.exit(1);
    }

    public static int scan(int cat) {
        yylval = new token(cat, yytext(), yylineno);

        return cat;
    }

    public static void increment_lineno() {
        yylineno++;
    }
}
