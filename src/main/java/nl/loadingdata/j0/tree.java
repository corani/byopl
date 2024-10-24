package nl.loadingdata.j0;

class tree {
    int id;
    String sym;
    int rule;
    int nkids;
    token tok;
    tree kids[];

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

}
