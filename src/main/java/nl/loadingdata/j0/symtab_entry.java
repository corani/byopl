package nl.loadingdata.j0;

public class symtab_entry {
    String sym;
    symtab parent_st, st;
    boolean isConst;

    symtab_entry(String s, symtab p, boolean c) {
        sym = s;
        parent_st = p;
        st = null;
        isConst = c;
    }

    symtab_entry(String s, symtab p, boolean c, symtab t) {
        sym = s;
        parent_st = p;
        st = t;
        isConst = c;
    }

    void indent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
    }

    void print(int level) {
        indent(level);
        System.out.print(sym);

        if (isConst) {
            System.out.print(" (const)");
        }

        System.out.println();

        if (st != null) {
            st.print(level + 1);
        }
    }
}
