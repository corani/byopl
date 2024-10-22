package nl.loadingdata.j0;

import java.io.FileReader;

public class j0 {
    static Yylex lex;
    public static int yylineno, yycolno;
    public static token yylval;

    public static void main(String argv[]) throws Exception {
        lex = new Yylex(new FileReader(argv[0]));
        yylineno = 1;
        yycolno = 1;

        int i;

        while ((i = lex.yylex()) != Yylex.YYEOF) {
            System.out.println("token " + i + 
                    ": line " + yylval.lineno + 
                    ": " + yylval.text);
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

    public static short ord(String s) {
        return (short) s.charAt(0);
    }

    public static int scan(int cat) {
        yylval = new token(cat, yytext(), yylineno, yycolno);
        yycolno += yytext().length();

        return cat;
    }

    public static void whitespace() {
        yycolno += yytext().length();
    }

    public static void newline() {
        yylineno++;
        yycolno = 1;
    }

    public static void comment() {
        String s = yytext();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\n') {
                yylineno++;
                yycolno = 1;
            } else {
                yycolno++;
            }
        }
    }
}
