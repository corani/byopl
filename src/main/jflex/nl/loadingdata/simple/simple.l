package nl.loadingdata.simple;
%%
%int
%%
[a-zA-Z]+   { return simple.scan(Parser.NAME); }
[0-9]+      { return simple.scan(Parser.NUMBER); }
[ \t]+      { }
\r?\n       { simple.increment_lineno(); }
.           { simple.lexErr("unrecognized character"); }
