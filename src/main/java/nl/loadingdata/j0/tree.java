package nl.loadingdata.j0;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class tree {
    int id, rule, nkids;
    String sym;
    token tok;
    tree kids[];
    boolean isConst;
    symtab stab;

    public tree(String s, int r, token t) {
        id = serial.getid();
        sym = s;
        rule = r;
        tok = t;
    }

    public tree(String s, int r, tree[] t) {
        id = serial.getid();
        sym = s;
        rule = r;
        nkids = t.length;
        kids = t;
    }

    private void indent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
    }

    public void print_tree(int level) {
        indent(level);

        if (tok != null) {
            System.out.printf("%s (id=%02d, cat=%02d, loc=%s:%d:%d)\n", 
                tok.text, id, tok.cat, j0.yyfilename, tok.lineno, tok.colno);
        } else {
            System.out.printf("%s (id=%02d, rule=%04d)\n", 
                    sym, id, rule);
        }

        for (int i = 0; i < nkids; i++) {
            if (kids[i] != null) {
                kids[i].print_tree(level + 1);
            } else {
                indent(level + 1);
                System.out.println("null");
            }
        }
    }

    private int j;

    public void print_leaf(PrintWriter pw) {
        String s = Parser.yyname[tok.cat];
        pw.printf("N%d [shape=box style=dotted label=\" %s \\n", 
                id, s);
        pw.printf("text = %s \\l loc = %s:%d:%d \\l\"];\n",
                escape(tok.text), j0.yyfilename, tok.lineno, tok.colno);
    }

    public void print_branch(PrintWriter pw) {
        pw.printf("N%d [shape=box label=\"%s\"];\n", 
                id, pretty_print_name());
    }

    public void print_graph(PrintWriter pw) {
        if (tok != null) {
            print_leaf(pw);
            return;
        }

        print_branch(pw);

        for (int i = 0; i < nkids; i++) {
            if (kids[i] != null) {
                pw.printf("N%d -> N%d;\n", id, kids[i].id);
                kids[i].print_graph(pw);
            } else {
                pw.printf("N%d -> N%d%d;\n", id, id, j);
                pw.printf("N%d%d [label=\"%s\"];\n", id, j, "Empty rule");
                j++;
            }
        }
    }

    public void print_graph(String filename) {
        try {
            PrintWriter pw = new PrintWriter(
                    new BufferedWriter(
                        new FileWriter(filename)));
            pw.printf("digraph {\n");
            j = 0;
            print_graph(pw);
            pw.printf("}\n");
            pw.close();
        } catch (IOException e) {
            System.err.println("error opening file " + filename);
            System.exit(1);
        }
    }

    public void print(String filename) {
        print_tree(0);
        print_graph(filename);
    }

    public String pretty_print_name() {
        if (tok == null) {
            return sym+"#"+(rule%10);
        } else {
            return escape(tok.text)+":"+tok.cat;
        }
        
    }

    public String escape(String s) {
        if (s.charAt(0) == '\"') {
            return "\\"+s.substring(0, s.length() - 1) + "\\\"";
        } else {
            return s;
        }
    }

    void mkSymTables(symtab curr) {
        stab = curr;

        switch (sym) {
            case "ClassDecl": {
                curr = new symtab("class", curr);
            } break;
            case "MethodDecl": {
                curr = new symtab("method", curr);
            } break;
        }

        for (int i = 0; i < nkids; i++) {
            if (kids[i] != null) {
                kids[i].mkSymTables(curr);
            }
        }
    }

    void populateSymTables() {
        switch (sym) {
            case "ClassDecl": {
                stab.insert(kids[0].tok.text, false, kids[0].stab);
            } break;
            case "FieldDecl": case "LocalVarDecl": {
                tree k = kids[1];

                while ((k != null) && k.sym.equals("VarDecls")) {
                    insert_vardeclarator(k.kids[1]);
                    k = k.kids[0];
                }

                insert_vardeclarator(k);
            } return;
            case "MethodDecl": {
                stab.insert(kids[0].kids[1].kids[0].tok.text, false, kids[0].stab);
            } // fallthrough 
            case "FormalParam": {
                insert_vardeclarator(kids[1]);
            } return;
        }

        for (int i = 0; i < nkids; i++) {
            if (kids[i] != null) {
                kids[i].populateSymTables();
            }
        }
    }

    void insert_vardeclarator(tree vd) {
        if (vd.tok != null) {
            stab.insert(vd.tok.text, false);
        } else {
            insert_vardeclarator(vd.kids[0]);
        }
    }

    void checkSymTables() {

    }

    void calc_isConst() {
        for (int i = 0; i < nkids; i++) {
            if (kids[i] != null) {
                kids[i].calc_isConst();
            }
        }

        switch (sym) {
            case "INTLIT": case "DOUBLELIT": case "STRINGLIT":
            case "BOOLLIT": {
                // NOTE(daniel): the book uses `BOOLTRUE` and `BOOLFALSE`, which seems incorrect.
                isConst = true;
            } break;
            case "UnaryExpr": {
                isConst = kids[1].isConst;
            } break;
            case "RelExpr": {
                isConst = kids[0].isConst && kids[2].isConst;
            } break;
            case "CondOrExpr": case "CondAndExpr": case "EqExpr": 
            case "MulExpr": case "AddExpr": {
                // NOTE(daniel): the book uses `MULEXPR` and `ADDEXPR`, which seems incorrect.
                isConst = kids[0].isConst && kids[1].isConst;
            } break;
        }
    }
}
