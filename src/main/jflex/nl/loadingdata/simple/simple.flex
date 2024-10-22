package nl.loadingdata.simple;
%%
%int
%%
[a-zA-Z]+   { return simple.scan(1); }
[0-9]+      { return simple.scan(2); }
[ \t]+      { }
\r?\n       { simple.increment_lineno(); }
.           { simple.lexErr("unrecognized character"); }
