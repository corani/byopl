package nl.loadingdata.j0;

public class token {
    public int cat;
    public String text;
    public int lineno, colno, ival;
    public double dval;
    public String sval;

    public token(int cat, String text, int lineno, int colno) {
        this.cat = cat;
        this.text = text;
        this.lineno = lineno;
        this.colno = colno;

        switch (cat) {
        case parser.INTLIT: 
            this.ival = Integer.parseInt(text);
            break;
        case parser.DOUBLELIT: 
            this.dval = Double.parseDouble(text);
            break;
        case parser.STRINGLIT: 
            this.sval = unescape(text);
            break;
        }
    }

    private String unescape(String sin) {
        String sout = "";

        sin = sin.substring(1, sin.length()-1);

        while (sin.length() > 0) {
            char c = sin.charAt(0);
            if (c == '\\') {
                sin = sin.substring(1);
                if (sin.length() < 1) {
                    j0.lexErr("malformed string literal");
                } else {
                    switch (sin.charAt(0)) {
                    case 't':
                        sout += '\t';
                        break;
                    case 'n': 
                        sout += '\n';
                        break;
                    default: 
                        j0.lexErr("unknown escape sequence");
                    }
                }
            } else {
                sout += c;
            }

            sin = sin.substring(1);
        }
        
        return sout;
    }
}
