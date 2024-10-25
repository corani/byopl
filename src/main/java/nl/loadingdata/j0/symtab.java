package nl.loadingdata.j0;

import java.util.HashMap;

public class symtab {
    String scope;
    symtab parent;
    HashMap<String, symtab_entry> t;

    symtab(String sc) {
        scope = sc;
        parent = null;
        t = new HashMap<String, symtab_entry>();
    }

    symtab(String sc, symtab p) {
        scope = sc;
        parent = p;
        t = new HashMap<String, symtab_entry>();
    }

    void insert(String s, boolean c) {
        t.put(s, new symtab_entry(s, this, c));
    }

    void insert(String s, boolean c, symtab st) {
        t.put(s, new symtab_entry(s, this, c, st));
    }

    symtab_entry lookup(String s) {
        symtab_entry e = t.get(s);

        if (e != null) {
            return e;
        } else if (parent != null) {
            return parent.lookup(s);
        } else {
            return null;
        }
    }

    void print() {
        // TODO(daniel): implementation
    }
}
