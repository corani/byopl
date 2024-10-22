package nl.loadingdata.simple;

public class token {
    public int cat;
    public String text;
    public int lineno;

    public token(int cat, String text, int lineno) {
        this.cat = cat;
        this.text = text;
        this.lineno = lineno;
    }
}
