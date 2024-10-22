package nl.loadingdata.simple;
%%
%int
%%
[a-zA-Z]+   { return simple.scan(parser.NAME); }
[0-9]+      { return simple.scan(parser.NUMBER); }
[ \t]+      { }
\r?\n       { simple.increment_lineno(); }
.           { simple.lexErr("unrecognized character"); }
