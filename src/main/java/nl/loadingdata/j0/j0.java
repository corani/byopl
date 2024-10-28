package nl.loadingdata.j0;

import java.io.FileReader;
import java.io.IOException;

public class j0 {
    static Yylex lex;
    public static int yylineno, yycolno, count;
    public static String yyfilename;
    public static Parser par;
    public static symtab global_st;

    public static void main(String argv[]) throws Exception {
        yyfilename = argv[0];
        yylineno = 1;
        yycolno = 1;
        count = 0;

        lex = new Yylex(new FileReader(yyfilename));
        par = new Parser();

        if (par.yyparse() != 0) {
            System.err.println("parse failed");
        } else {
            System.out.println("parse succeeded, " + count + " tokens processed");
        }
    }

    public static int yylex() {
        try {
            int token;

            if ((token = lex.yylex()) >= 0) {
                count++;
                System.out.println(count + ": " + token + " " + yytext());
            }

            return token;
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

    public static void semErr(String s) {
        System.err.println(s + 
                ": line " + yylineno + 
                ": " + yytext());
        System.exit(1);
    }

    public static short ord(String s) {
        return (short) s.charAt(0);
    }

    public static int scan(int cat) {
        par.yylval = new ParserVal(
                new tree("token", 0, 
                    new token(cat, yytext(), yylineno, yycolno)));
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

    public static void semantic(ParserVal node) {
        tree root = (tree) node.obj;

        root.print(yyfilename + ".dot");

        global_st = new symtab("global");
        symtab System_st = new symtab("class");
        symtab out_st = new symtab("class");
        out_st.insert("println", false);
        System_st.insert("out", false, out_st);
        global_st.insert("System", false, System_st);

        root.mkSymTables(global_st);
        root.populateSymTables();
        root.checkSymTables();

        global_st.print();
    }

    public static ParserVal node(String s, int r, ParserVal...p) {
        tree[] t = new tree[p.length];

        for (int i = 0; i < t.length; i++) {
            t[i] = (tree) p[i].obj;
        }

        return new ParserVal(new tree(s, r, t));
    }
}
