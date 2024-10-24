//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package nl.loadingdata.j0;







public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short BREAK=257;
public final static short DOUBLE=258;
public final static short ELSE=259;
public final static short FOR=260;
public final static short IF=261;
public final static short INT=262;
public final static short RETURN=263;
public final static short VOID=264;
public final static short WHILE=265;
public final static short IDENTIFIER=266;
public final static short CLASSNAME=267;
public final static short CLASS=268;
public final static short STRING=269;
public final static short BOOL=270;
public final static short INTLIT=271;
public final static short DOUBLELIT=272;
public final static short STRINGLIT=273;
public final static short BOOLLIT=274;
public final static short NULLVAL=275;
public final static short LESSTHANOREQUAL=276;
public final static short GREATERTHANOREQUAL=277;
public final static short ISEQUALTO=278;
public final static short NOTEQUALTO=279;
public final static short LOGICALAND=280;
public final static short LOGICALOR=281;
public final static short INCREMENT=282;
public final static short DECREMENT=283;
public final static short PUBLIC=284;
public final static short STATIC=285;
public final static short ConstructorDeclarator=286;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    3,    3,    4,    7,
    7,    7,    7,    7,    9,    9,   10,    8,    8,   11,
   11,    5,    6,   12,   14,   14,   15,   16,   16,   17,
   17,   18,   13,   19,   19,   20,   20,   21,   21,   22,
   24,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   25,   33,   33,   28,   29,   30,   30,   37,   37,
   38,   31,   32,   39,   39,   39,   40,   40,   41,   41,
   42,   42,   26,   27,   43,   43,   43,   43,   44,   44,
   44,   44,   44,   46,   46,   47,   47,   45,   35,   35,
   35,   48,   48,   49,   49,   49,   50,   50,   50,   50,
   51,   51,   51,   52,   52,   52,   52,   53,   53,   54,
   54,   54,   55,   55,   56,   56,   36,   36,   34,   57,
   57,   58,   58,   58,
};
final static short yylen[] = {                            2,
    4,    3,    0,    1,    2,    1,    1,    1,    3,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    3,    1,
    3,    2,    2,    4,    1,    1,    4,    1,    0,    1,
    3,    2,    3,    1,    0,    1,    2,    1,    1,    2,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    1,    1,    5,    7,    6,    8,    1,    2,
    2,    5,    9,    1,    1,    0,    1,    0,    1,    0,
    1,    3,    2,    3,    1,    3,    1,    1,    1,    1,
    1,    1,    1,    1,    0,    1,    3,    3,    4,    6,
    6,    1,    1,    2,    2,    1,    1,    3,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    1,    3,    1,
    3,    3,    1,    3,    1,    3,    1,    1,    3,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    1,   11,   10,   15,   12,
   13,    0,    0,    0,    4,    6,    7,    8,    0,    0,
   16,    0,    0,    0,   23,    2,    5,   20,    0,    0,
    0,   22,   26,   25,    0,    0,    0,    0,    0,    0,
   79,   80,   81,   82,   83,   43,    0,    0,    0,   42,
    0,    0,   36,   38,   39,    0,   44,   45,   46,   47,
   48,   49,   50,   51,    0,   53,    0,    0,   75,    0,
    0,    9,    0,    0,   17,    0,   24,   73,    0,    0,
    0,    0,    0,  118,   78,   67,    0,    0,   96,   97,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   33,   37,   40,   52,    0,  123,  124,  122,    0,    0,
   21,    0,   65,   71,    0,    0,    0,    0,   77,   94,
   95,   74,    0,    0,    0,    0,    0,  104,  105,  106,
  107,    0,    0,    0,    0,    0,    0,   76,   86,    0,
    0,    0,  119,    0,    0,    0,   30,    0,    0,    0,
   98,   99,  100,    0,    0,    0,    0,    0,    0,    0,
    0,   89,    0,    0,    0,    0,   27,    0,    0,    0,
   72,    0,   62,   87,    0,    0,   31,    0,    0,    0,
   59,   91,   90,    0,    0,    0,   56,   61,    0,   60,
    0,    0,   58,   63,    0,    0,   55,
};
final static short yydgoto[] = {                          2,
    6,   14,   15,   16,   17,   18,   48,   29,  118,   21,
   30,   22,   50,   35,   77,  145,  146,  147,   51,   52,
   53,   54,   55,   56,   57,   58,   59,   60,   61,   62,
   63,   64,   65,   84,   85,  139,  180,  181,  115,   87,
  184,  116,   88,   69,   70,  140,  141,   89,   90,   91,
   92,  132,   93,   94,   95,   96,   71,  109,
};
final static short yysindex[] = {                      -272,
 -237,    0, -200,  -55,  253,    0,    0,    0,    0,    0,
    0, -212,  -14,  243,    0,    0,    0,    0, -155,   74,
    0,  -14,  -53,  207,    0,    0,    0,    0,  -31,   64,
 -132,    0,    0,    0,  -99,  120,  140,  141,   50,  143,
    0,    0,    0,    0,    0,    0,   50, -155,   40,    0,
   60,  207,    0,    0,    0,  128,    0,    0,    0,    0,
    0,    0,    0,    0,  130,    0,    0,  145,    0,    0,
  -34,    0, -155,  111,    0,  166,    0,    0,  225,   50,
   50,   50,   40,    0,    0,    0,  137,  145,    0,    0,
  156,  -13,   43, -238,  -70,  -69,   50,  177,  175,   50,
    0,    0,    0,    0,  -46,    0,    0,    0,   50,   64,
    0,  266,    0,    0,  162,  182,  188,   40,    0,    0,
    0,    0,   50,   50,   50,   50,   50,    0,    0,    0,
    0,   50,   50,   50,   50,   50,  190,    0,    0,  195,
  193,  -39,    0, -155,  197,  216,    0,   50,   -3,  -14,
    0,    0,    0,  156,  156,  -13,   43,   43, -238,  -70,
  -14,    0,   50,   50,   50,   64,    0,  266,  203,   40,
    0,    5,    0,    0,  142,  233,    0,   -3, -109,   16,
    0,    0,    0,  235,  182,  245,    0,    0, -109,    0,
  -14,   50,    0,    0,  251,  -14,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  298,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   42,
    0,    0,    0,  180,    0,    0,    0,    0,    0,    6,
    0,    0,    0,    0,    0,    0,    0,    0,  241,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -59,    0,
    0,  184,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  119,    0,    0,  -37,
    0,    0,    0,    0,    0,    0,    0,    0,  258,    0,
    0,    0,    1,    0,    0,    0,    0,   10,    0,    0,
   57,   77,   15,  103,  109,  133,    0,    0,  259,  285,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   71,
    0,  286,    0,    0,    0,  269,    0,   34,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -38,  -26,    0,    0,    0,  290,    0,  241,    0,    0,
    0,    0,    0,   63,   69,   83,   23,   97,  105,  110,
    0,    0,    0,  226,  285,   48,    0,    0,    0,   24,
    0,  136,    0,    0,    0,    0,    0,  311,    0,  174,
    0,    0,    0,    0,  324,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  318,    0,    0,    0,  161,  321,  479,    0,
  -47,    0,   36,    0,    0,    0,    0,  198,    0,    0,
  319,    0,    0,  295,    0,    0,    0, -150,    0,    0,
    0,    0,   12,  343,  376,  341,    0,  199,    0,  222,
    0,  204,  406,    0,  291,  -32,    0,    0,   76,   44,
  249,    0,   39,  252,  256,    0,    0,    0,
};
final static int YYTABLESIZE=671;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         77,
  165,  120,   84,   77,   77,   77,   77,   77,   77,   77,
   88,    1,   73,   24,   88,   88,   88,   88,   88,   88,
   88,   77,   77,  121,   77,  110,  108,   72,  188,  127,
    3,  126,   88,   88,   88,   88,   47,   93,  188,  133,
  134,   93,   93,   93,   93,   93,   92,   93,   25,   18,
   92,   92,   92,   92,   92,  110,   92,   32,  110,   93,
   93,  120,   93,  111,   18,    4,  111,    5,   92,   92,
   93,   92,   23,  110,   93,   93,   93,   93,   93,  100,
   93,  111,   82,  164,  120,   31,   84,   77,   32,   47,
  114,   32,   93,   93,   81,   93,  166,  101,   88,  101,
  101,  101,  130,  103,  131,  103,  103,  103,   24,  102,
   28,  102,  102,  102,   19,  101,  101,  108,  101,   31,
  108,  103,  103,  109,  103,   93,  109,  102,  102,   19,
  102,  175,  176,   75,   92,  108,  108,  112,  108,  110,
  112,  109,  109,  113,  109,  114,  113,  111,  114,  115,
  116,  186,  115,  116,   74,  112,  120,  121,   93,   54,
  171,  113,   54,  114,   78,   19,   76,  115,  116,  154,
  155,  157,  158,  117,   19,   55,  117,   54,   78,   79,
   80,  101,   97,   34,  101,  172,  103,  103,  104,  114,
  105,  117,  125,  102,   55,  122,  173,  123,  151,  152,
  153,  108,  124,  111,    7,  112,   14,  109,    8,  135,
   33,  136,    9,   57,  187,   10,   11,  138,   73,  142,
  148,  112,  120,  120,  193,  149,  194,  113,  150,  114,
  161,  197,   57,  115,  116,  162,  163,  167,   77,   77,
   77,   77,   77,   77,  121,  121,   47,  106,  107,   88,
   88,   88,   88,   88,   88,   88,   88,  117,   55,  168,
   55,  178,    9,  179,   47,   46,  182,   41,   42,   43,
   44,   45,  144,  183,  189,  191,   93,   93,   93,   93,
   93,   93,  120,  120,  192,   92,   92,   92,   92,   92,
   92,  196,  110,  110,  110,  110,   57,    3,   57,   68,
  111,  111,  111,  111,   35,  120,  120,   14,   34,   93,
   93,   93,   93,   93,   93,    9,   66,   41,  128,  129,
   41,   42,   43,   44,   45,   85,   29,   64,  144,   24,
   28,   27,  101,  101,  101,  101,  101,  101,  103,  103,
  103,  103,  103,  103,  102,  102,  102,  102,  102,  102,
   85,   70,  108,  108,  108,  108,  108,  108,  109,  109,
  109,  109,  109,  109,   69,  177,   66,   26,   99,  169,
  102,  119,  119,  113,  112,  112,  112,  112,  190,   86,
  156,  185,  113,  113,  114,  114,  159,   98,    0,  115,
  116,  160,   55,   55,   66,   55,   55,   55,   55,   67,
   55,   55,    0,    0,   55,   55,   55,   55,   55,   55,
   55,    0,    0,  119,  119,  119,  119,  119,    0,    0,
  117,   66,  119,  119,  119,  119,  119,   67,    0,   68,
   57,   57,    0,   57,   57,   57,   57,  137,   57,   57,
    0,    0,   57,   57,   57,   57,   57,   57,   57,  143,
    0,    0,    0,    0,   67,    0,    0,   68,    0,    0,
    0,    0,    0,   36,    7,    0,   37,   38,    8,   39,
    0,   40,    9,    0,    0,   10,   11,   41,   42,   43,
   44,   45,    7,   20,   68,    0,    8,    0,   86,    0,
    9,   66,   20,   10,   11,   41,   42,   43,   44,   45,
    7,   20,   49,  174,    8,    0,    0,    0,    9,    0,
    7,   10,   11,    0,    8,    0,    0,   83,    9,    0,
   66,   10,   11,    7,   67,   83,   12,    8,   13,    0,
   49,    9,  195,    0,   10,   11,   12,    0,   13,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   67,   68,    0,    0,   49,   83,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   83,    0,    0,   83,    0,
    0,    0,    0,   68,    0,    0,    0,   83,    0,    0,
   20,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   83,  170,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   83,   83,   83,    0,    0,   20,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  170,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   83,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
   40,   61,   41,   41,   42,   43,   44,   45,   46,   47,
   37,  284,   44,  123,   41,   42,   43,   44,   45,   46,
   47,   59,   60,   61,   62,   73,   61,   59,  179,   43,
  268,   45,   59,   60,   61,   62,   40,   37,  189,  278,
  279,   41,   42,   43,   44,   45,   37,   47,   13,   44,
   41,   42,   43,   44,   45,   41,   47,   22,   44,   59,
   60,   61,   62,   41,   59,  266,   44,  123,   59,   60,
   37,   62,  285,   59,   41,   42,   43,   44,   45,   40,
   47,   59,   33,  123,   61,   46,  125,  125,   41,   40,
   79,   44,   59,   60,   45,   62,  144,   41,  125,   43,
   44,   45,   60,   41,   62,   43,   44,   45,  123,   41,
  266,   43,   44,   45,   44,   59,   60,   41,   62,   46,
   44,   59,   60,   41,   62,  125,   44,   59,   60,   59,
   62,  164,  165,  266,  125,   59,   60,   41,   62,  125,
   44,   59,   60,   41,   62,   41,   44,  125,   44,   41,
   41,  261,   44,   44,   91,   59,   81,   82,  125,   41,
  149,   59,   44,   59,   46,    5,  266,   59,   59,  126,
  127,  133,  134,   41,   14,   40,   44,   59,   59,   40,
   40,  125,   40,   23,  125,  150,   59,  125,   59,  178,
   46,   59,   37,  125,   59,   59,  161,   42,  123,  124,
  125,  125,   47,   93,  258,   40,  266,  125,  262,  280,
  264,  281,  266,   40,  179,  269,  270,   41,   44,  266,
   59,  125,  282,  283,  189,   44,  191,  125,   41,  125,
   41,  196,   59,  125,  125,   41,   44,   41,  276,  277,
  278,  279,  280,  281,  282,  283,   40,  282,  283,  276,
  277,  278,  279,  280,  281,  282,  283,  125,  123,   44,
  125,   59,  266,  259,   40,   59,  125,  271,  272,  273,
  274,  275,  112,   41,  259,   41,  276,  277,  278,  279,
  280,  281,  282,  283,   40,  276,  277,  278,  279,  280,
  281,   41,  278,  279,  280,  281,  123,    0,  125,   59,
  278,  279,  280,  281,  125,  282,  283,  266,  125,  276,
  277,  278,  279,  280,  281,  266,   59,   59,  276,  277,
  271,  272,  273,  274,  275,   41,   41,   59,  168,  123,
   41,   14,  276,  277,  278,  279,  280,  281,  276,  277,
  278,  279,  280,  281,  276,  277,  278,  279,  280,  281,
  125,   41,  276,  277,  278,  279,  280,  281,  276,  277,
  278,  279,  280,  281,   41,  168,   24,  125,   48,  148,
   52,   81,   82,   79,  278,  279,  280,  281,  180,   39,
  132,  178,  280,  281,  280,  281,  135,   47,   -1,  281,
  281,  136,  257,  258,   52,  260,  261,  262,  263,   24,
  265,  266,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,  123,  124,  125,  126,  127,   -1,   -1,
   80,   79,  132,  133,  134,  135,  136,   52,   -1,   24,
  257,  258,   -1,  260,  261,  262,  263,   97,  265,  266,
   -1,   -1,  269,  270,  271,  272,  273,  274,  275,  109,
   -1,   -1,   -1,   -1,   79,   -1,   -1,   52,   -1,   -1,
   -1,   -1,   -1,  257,  258,   -1,  260,  261,  262,  263,
   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,  273,
  274,  275,  258,    5,   79,   -1,  262,   -1,  148,   -1,
  266,  149,   14,  269,  270,  271,  272,  273,  274,  275,
  258,   23,   24,  163,  262,   -1,   -1,   -1,  266,   -1,
  258,  269,  270,   -1,  262,   -1,   -1,   39,  266,   -1,
  178,  269,  270,  258,  149,   47,  284,  262,  286,   -1,
   52,  266,  192,   -1,  269,  270,  284,   -1,  286,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  178,  149,   -1,   -1,   79,   80,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   97,   -1,   -1,  100,   -1,
   -1,   -1,   -1,  178,   -1,   -1,   -1,  109,   -1,   -1,
  112,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  148,  149,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  163,  164,  165,   -1,   -1,  168,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  178,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  192,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=286;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"BREAK","DOUBLE","ELSE","FOR","IF",
"INT","RETURN","VOID","WHILE","IDENTIFIER","CLASSNAME","CLASS","STRING","BOOL",
"INTLIT","DOUBLELIT","STRINGLIT","BOOLLIT","NULLVAL","LESSTHANOREQUAL",
"GREATERTHANOREQUAL","ISEQUALTO","NOTEQUALTO","LOGICALAND","LOGICALOR",
"INCREMENT","DECREMENT","PUBLIC","STATIC","ConstructorDeclarator",
};
final static String yyrule[] = {
"$accept : ClassDecl",
"ClassDecl : PUBLIC CLASS IDENTIFIER ClassBody",
"ClassBody : '{' ClassBodyDecls '}'",
"ClassBody :",
"ClassBodyDecls : ClassBodyDecl",
"ClassBodyDecls : ClassBodyDecls ClassBodyDecl",
"ClassBodyDecl : FieldDecl",
"ClassBodyDecl : MethodDecl",
"ClassBodyDecl : ConstructorDecl",
"FieldDecl : Type VarDecls ';'",
"Type : INT",
"Type : DOUBLE",
"Type : STRING",
"Type : BOOL",
"Type : Name",
"Name : IDENTIFIER",
"Name : QualifiedName",
"QualifiedName : Name '.' IDENTIFIER",
"VarDecls : VarDeclarator",
"VarDecls : VarDecls ',' VarDeclarator",
"VarDeclarator : IDENTIFIER",
"VarDeclarator : VarDeclarator '[' ']'",
"MethodDecl : MethodHeader Block",
"ConstructorDecl : ConstructorDeclarator Block",
"MethodHeader : PUBLIC STATIC MethodReturnVal MethodDeclarator",
"MethodReturnVal : Type",
"MethodReturnVal : VOID",
"MethodDeclarator : IDENTIFIER '(' FormalParamListOpt ')'",
"FormalParamListOpt : FormalParamList",
"FormalParamListOpt :",
"FormalParamList : FormalParam",
"FormalParamList : FormalParamList ',' FormalParam",
"FormalParam : Type VarDeclarator",
"Block : '{' BlockStmtsOpt '}'",
"BlockStmtsOpt : BlockStmts",
"BlockStmtsOpt :",
"BlockStmts : BlockStmt",
"BlockStmts : BlockStmts BlockStmt",
"BlockStmt : LocalVarDeclStmt",
"BlockStmt : Stmt",
"LocalVarDeclStmt : LocalVarDecl ';'",
"LocalVarDecl : Type VarDecls",
"Stmt : Block",
"Stmt : ';'",
"Stmt : ExprStmt",
"Stmt : BreakStmt",
"Stmt : ReturnStmt",
"Stmt : IfThenStmt",
"Stmt : IfThenElseStmt",
"Stmt : IfThenElseIfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"ExprStmt : StmtExpr ';'",
"StmtExpr : Assignment",
"StmtExpr : MethodCall",
"IfThenStmt : IF '(' Expr ')' Block",
"IfThenElseStmt : IF '(' Expr ')' Block ELSE Block",
"IfThenElseIfStmt : IF '(' Expr ')' Block ElseIfSequence",
"IfThenElseIfStmt : IF '(' Expr ')' Block ElseIfSequence ELSE Block",
"ElseIfSequence : ElseIfStmt",
"ElseIfSequence : ElseIfSequence ElseIfStmt",
"ElseIfStmt : ELSE IfThenStmt",
"WhileStmt : WHILE '(' Expr ')' Block",
"ForStmt : FOR '(' ForInit ';' ExprOpt ';' ForUpdate ')' Block",
"ForInit : StmtExprList",
"ForInit : LocalVarDecl",
"ForInit :",
"ExprOpt : Expr",
"ExprOpt :",
"ForUpdate : StmtExprList",
"ForUpdate :",
"StmtExprList : StmtExpr",
"StmtExprList : StmtExprList ',' StmtExpr",
"BreakStmt : BREAK ';'",
"ReturnStmt : RETURN ExprOpt ';'",
"Primary : Literal",
"Primary : '(' Expr ')'",
"Primary : FieldAccess",
"Primary : MethodCall",
"Literal : INTLIT",
"Literal : DOUBLELIT",
"Literal : STRINGLIT",
"Literal : BOOLLIT",
"Literal : NULLVAL",
"ArgListOpt : ArgList",
"ArgListOpt :",
"ArgList : Expr",
"ArgList : ArgList ',' Expr",
"FieldAccess : Primary '.' IDENTIFIER",
"MethodCall : Name '(' ArgListOpt ')'",
"MethodCall : Primary '.' IDENTIFIER '(' ArgListOpt ')'",
"MethodCall : Primary '.' IDENTIFIER '{' ArgListOpt '}'",
"PostFixExpr : Primary",
"PostFixExpr : Name",
"UnaryExpr : '-' UnaryExpr",
"UnaryExpr : '!' UnaryExpr",
"UnaryExpr : PostFixExpr",
"MulExpr : UnaryExpr",
"MulExpr : MulExpr '*' UnaryExpr",
"MulExpr : MulExpr '/' UnaryExpr",
"MulExpr : MulExpr '%' UnaryExpr",
"AddExpr : MulExpr",
"AddExpr : AddExpr '+' MulExpr",
"AddExpr : AddExpr '-' MulExpr",
"RelOp : LESSTHANOREQUAL",
"RelOp : GREATERTHANOREQUAL",
"RelOp : '<'",
"RelOp : '>'",
"RelExpr : AddExpr",
"RelExpr : RelExpr RelOp AddExpr",
"EqExpr : RelExpr",
"EqExpr : EqExpr ISEQUALTO RelExpr",
"EqExpr : EqExpr NOTEQUALTO RelExpr",
"CondAndExpr : EqExpr",
"CondAndExpr : CondAndExpr LOGICALAND EqExpr",
"CondOrExpr : CondAndExpr",
"CondOrExpr : CondOrExpr LOGICALOR CondAndExpr",
"Expr : CondOrExpr",
"Expr : Assignment",
"Assignment : LeftHandSide AssignOp Expr",
"LeftHandSide : Name",
"LeftHandSide : FieldAccess",
"AssignOp : '='",
"AssignOp : INCREMENT",
"AssignOp : DECREMENT",
};

//#line 253 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"

int yylex() {
    return j0.yylex();
}

void yyerror(String s) {
    j0.lexErr(s);
}
//#line 539 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 10 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ClassDecl", 1000, val_peek(1), val_peek(0)); j0.print(yyval); }
break;
case 2:
//#line 13 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ClassBody", 1010, val_peek(1)); }
break;
case 3:
//#line 14 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ClassBody", 1010); }
break;
case 5:
//#line 18 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ClassBodyDecls", 1020, val_peek(1), val_peek(0)); }
break;
case 9:
//#line 26 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("FieldDecl", 1030, val_peek(2), val_peek(1)); }
break;
case 17:
//#line 40 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("QualifiedName", 1040, val_peek(2), val_peek(0)); }
break;
case 19:
//#line 44 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("VarDecls", 1050, val_peek(2), val_peek(0)); }
break;
case 21:
//#line 48 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("VarDeclarator", 1060, val_peek(2)); }
break;
case 22:
//#line 51 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodDecl", 1380, val_peek(1), val_peek(0)); }
break;
case 23:
//#line 54 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ConstructorDecl", 1110, val_peek(1), val_peek(0)); }
break;
case 24:
//#line 57 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodHeader", 1070, val_peek(1), val_peek(0)); }
break;
case 27:
//#line 64 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodDeclarator", 1080, val_peek(3), val_peek(1)); }
break;
case 31:
//#line 72 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("FormalParamList", 1090, val_peek(2), val_peek(0)); }
break;
case 32:
//#line 75 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("FormalParam", 1100, val_peek(1), val_peek(0)); }
break;
case 33:
//#line 78 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("Block", 1200, val_peek(1)); }
break;
case 37:
//#line 86 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("BlockStmts", 1130, val_peek(1), val_peek(0)); }
break;
case 41:
//#line 96 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("LocalVarDecl", 1140, val_peek(1), val_peek(0)); }
break;
case 55:
//#line 118 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("IfThenStmt", 1150, val_peek(2), val_peek(0)); }
break;
case 56:
//#line 121 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("IfThenElseStmt", 1160, val_peek(4), val_peek(2), val_peek(0)); }
break;
case 57:
//#line 124 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("IfThenElseIfStmt", 1170, val_peek(3), val_peek(1), val_peek(0)); }
break;
case 58:
//#line 125 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("IfThenElseIfStmt", 1171, val_peek(5), val_peek(3), val_peek(2), val_peek(0)); }
break;
case 60:
//#line 129 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ElseIfSequence", 1180, val_peek(1), val_peek(0)); }
break;
case 61:
//#line 132 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ElseIfStmt", 1190, val_peek(0)); }
break;
case 62:
//#line 135 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("WhileStmt", 1210, val_peek(2), val_peek(0)); }
break;
case 63:
//#line 138 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ForStmt", 1220, val_peek(6), val_peek(4), val_peek(2), val_peek(0)); }
break;
case 72:
//#line 155 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("StmtExprList", 1230, val_peek(2), val_peek(0)); }
break;
case 74:
//#line 161 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ReturnStmt", 1250, val_peek(1)); }
break;
case 76:
//#line 165 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=val_peek(1); }
break;
case 87:
//#line 182 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ArgList", 1270, val_peek(2), val_peek(0)); }
break;
case 88:
//#line 185 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("FieldAccess", 1280, val_peek(2), val_peek(0)); }
break;
case 89:
//#line 188 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodCall", 1290, val_peek(3), val_peek(1)); }
break;
case 90:
//#line 189 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodCall", 1291, val_peek(5), val_peek(3), val_peek(1)); }
break;
case 91:
//#line 190 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodCall", 1292, val_peek(5), val_peek(3), val_peek(1)); }
break;
case 94:
//#line 197 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("UnaryExpr", 1300, val_peek(0)); }
break;
case 95:
//#line 198 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("UnaryExpr", 1301, val_peek(0)); }
break;
case 98:
//#line 203 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MulExpr", 1310, val_peek(2), val_peek(0)); }
break;
case 99:
//#line 204 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MulExpr", 1311, val_peek(2), val_peek(0)); }
break;
case 100:
//#line 205 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MulExpr", 1312, val_peek(2), val_peek(0)); }
break;
case 102:
//#line 209 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("AddExpr", 1320, val_peek(2), val_peek(0)); }
break;
case 103:
//#line 210 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("AddExpr", 1321, val_peek(2), val_peek(0)); }
break;
case 109:
//#line 220 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("RelExpr", 1330, val_peek(2), val_peek(1), val_peek(0)); }
break;
case 111:
//#line 224 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("EqExpr", 1340, val_peek(2), val_peek(0)); }
break;
case 112:
//#line 225 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("EqExpr", 1341, val_peek(2), val_peek(0)); }
break;
case 114:
//#line 229 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("CondAndExpr", 1350, val_peek(2), val_peek(0)); }
break;
case 116:
//#line 233 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("CondOrExpr", 1360, val_peek(2), val_peek(0)); }
break;
case 119:
//#line 240 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("Assignment", 1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 872 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
