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
public final static short ArgListOp=287;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    3,    3,    4,    7,
    7,    7,    7,    7,    9,    9,   10,    8,    8,   11,
   11,    5,    6,   12,   14,   14,   15,   16,   16,   17,
   17,   18,   13,   19,   19,   20,   20,   21,   21,   22,
   24,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   25,   33,   33,   33,   28,   29,   30,   30,   38,
   38,   39,   31,   32,   40,   40,   40,   41,   41,   42,
   42,   43,   43,   26,   27,   44,   44,   44,   44,   45,
   45,   45,   45,   45,   36,   47,   47,   48,   48,   46,
   35,   35,   35,   49,   49,   50,   50,   50,   51,   51,
   51,   51,   52,   52,   52,   53,   53,   53,   53,   54,
   54,   55,   55,   55,   56,   56,   57,   57,   37,   37,
   34,   58,   58,   59,   59,   59,
};
final static short yylen[] = {                            2,
    4,    3,    0,    1,    2,    1,    1,    1,    3,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    3,    1,
    3,    2,    2,    4,    1,    1,    4,    1,    0,    1,
    3,    2,    3,    1,    0,    1,    2,    1,    1,    2,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    1,    1,    1,    5,    7,    6,    8,    1,
    2,    2,    5,    9,    1,    1,    0,    1,    0,    1,
    0,    1,    3,    2,    3,    1,    3,    1,    1,    1,
    1,    1,    1,    1,    4,    1,    0,    1,    3,    3,
    4,    6,    6,    1,    1,    2,    2,    1,    1,    3,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
    3,    1,    3,    3,    1,    3,    1,    3,    1,    1,
    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    1,   11,   10,   15,   12,
   13,    0,    0,    0,    4,    6,    7,    8,    0,    0,
   16,    0,    0,    0,   23,    2,    5,   20,    0,    0,
    0,   22,   26,   25,    0,    0,    0,    0,    0,    0,
   80,   81,   82,   83,   84,   43,    0,    0,    0,   42,
    0,    0,   36,   38,   39,    0,   44,   45,   46,   47,
   48,   49,   50,   51,    0,   53,    0,   55,    0,   76,
    0,    0,    9,    0,    0,   17,    0,   24,   74,    0,
    0,    0,    0,    0,  120,   79,   68,    0,    0,   98,
   99,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   33,   37,   40,   52,    0,  125,  126,  124,    0,
    0,   21,    0,   66,   72,    0,    0,    0,    0,   78,
   96,   97,    0,   75,    0,    0,    0,    0,    0,  106,
  107,  108,  109,    0,    0,    0,    0,    0,    0,   77,
    0,   88,    0,    0,    0,  121,    0,    0,    0,   30,
    0,    0,    0,  100,  101,  102,    0,    0,    0,    0,
    0,    0,    0,    0,   91,   85,    0,    0,    0,    0,
   27,    0,    0,    0,   73,    0,   63,   89,    0,    0,
   31,    0,    0,    0,   60,   93,   92,    0,    0,    0,
   57,   62,    0,   61,    0,    0,   59,   64,    0,    0,
   56,
};
final static short yydgoto[] = {                          2,
    6,   14,   15,   16,   17,   18,   48,   29,  119,   21,
   30,   22,   50,   35,   78,  148,  149,  150,   51,   52,
   53,   54,   55,   56,   57,   58,   59,   60,   61,   62,
   63,   64,   65,   85,   86,   68,  142,  184,  185,  116,
   88,  188,  117,   89,   70,   71,  143,  144,   90,   91,
   92,   93,  134,   94,   95,   96,   97,   72,  110,
};
final static short yysindex[] = {                      -241,
 -222,    0, -211,  -17,  314,    0,    0,    0,    0,    0,
    0, -172,    5,  153,    0,    0,    0,    0, -140,   90,
    0,    5,   50,  273,    0,    0,    0,    0,  -31,   47,
 -117,    0,    0,    0, -110,  100,  135,  136,   65,  137,
    0,    0,    0,    0,    0,    0,   65, -140,    2,    0,
   35,  273,    0,    0,    0,  107,    0,    0,    0,    0,
    0,    0,    0,    0,  121,    0,    0,    0,  143,    0,
    0,  -57,    0, -140,   89,    0,  156,    0,    0,  204,
   65,   65,   65,   34,    0,    0,    0,  138,  143,    0,
    0,  141,   38,  -45, -276,  -76,  -74,   65,  171,  169,
  -33,    0,    0,    0,    0,  -52,    0,    0,    0,   65,
   47,    0,  144,    0,    0,  157,  173,  174,   34,    0,
    0,    0,  -68,    0,   65,   65,   65,   65,   65,    0,
    0,    0,    0,   65,   65,   65,   65,   65,  179,    0,
  180,    0,  186,  190,  -34,    0, -140,  195,  193,    0,
   65,   14,    5,    0,    0,    0,  141,  141,   38,  -45,
  -45, -276,  -76,    5,    0,    0,   65,   65,   65,   47,
    0,  144,  188,    2,    0,   -6,    0,    0,  139,  236,
    0,   14, -118,   20,    0,    0,    0,  240,  173,  243,
    0,    0, -118,    0,    5,   65,    0,    0,  249,    5,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  284,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   44,
    0,    0,    0,  166,    0,    0,    0,    0,    0,   12,
    0,    0,    0,    0,    0,    0,    0,    0,  241,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -60,    0,
    0,  192,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  126,    0,    0,    0,
  -21,    0,    0,    0,    0,    0,    0,    0,    0,  252,
    0,    0,    0,   16,    0,    0,    0,    0,   25,    0,
    0,   73,   93,  -30,  127,  149,  151,    0,    0,  256,
  277,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   63,    0,  280,    0,    0,    0,  263,    0,   49,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -23,  -10,    0,    0,    0,  282,    0,
  241,    0,    0,    0,    0,    0,   80,   86,  103,  110,
  120,  140,  150,    0,    0,    0,    0,  199,  277,    3,
    0,    0,    0,  -53,    0,  184,    0,    0,    0,    0,
    0,  292,    0,  223,    0,    0,    0,    0,  293,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,  321,    0,    0,    0,   74,  294,  477,    0,
  -44,    0,  373,    0,    0,    0,    0,  172,    0,    0,
  289,    0,    0,  265,    0,    0,    0, -174,    0,    0,
    0,    0,  -70,   21,  380,    0,  471,    0,  159,    0,
  196,    0,  194,  388,    0,  467, -104,    0,    0,   75,
  -29,  221,    0,  -16,  231,  237,    0,    0,    0,
};
final static int YYTABLESIZE=673;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         83,
  122,  135,  136,  109,   24,  169,   47,  122,  192,  115,
  112,   82,   74,  112,  132,   78,  133,   86,  192,   78,
   78,   78,   78,   78,   78,   78,   90,   73,  112,  111,
   90,   90,   90,   90,   90,   90,   90,   78,   78,  123,
   78,  101,    1,   32,   66,    3,   32,   31,   90,   90,
   90,   90,   95,   47,    4,   18,   95,   95,   95,   95,
   95,   94,   95,  179,  180,   94,   94,   94,   94,   94,
   18,   94,   66,  123,   95,   95,  122,   95,   19,   31,
  129,  175,  128,   94,   94,   95,   94,   19,  168,   95,
   95,   95,   95,   95,  112,   95,   34,   83,  157,  158,
   66,   86,  170,   78,   47,    5,   19,   95,   95,   82,
   95,  115,   23,  103,   90,  103,  103,  103,  160,  161,
  105,   19,  105,  105,  105,   28,  104,   24,  104,  104,
  104,  103,  103,  110,  103,   31,  110,   75,  105,  105,
   95,  105,  190,  111,  104,  104,  111,  104,   76,   94,
  113,  110,  110,  113,  110,   77,  121,  122,   79,  102,
  114,  111,  111,  114,  111,  104,   54,  115,  113,   54,
  115,   79,   66,   95,   80,   81,   98,  127,  114,  105,
  116,  112,  125,  116,   54,  115,  147,  126,  106,  117,
  118,  119,  117,  118,  119,  113,  124,  103,  116,  154,
  155,  156,   66,  137,  105,   14,  138,  117,  118,  119,
  104,  140,   74,  145,  153,  151,  152,  110,  141,  164,
  165,  122,  122,   56,  107,  108,  166,  111,  122,  122,
  130,  131,    9,  167,  113,  171,  172,   41,   42,   43,
   44,   45,   56,   47,  114,  147,  182,  112,  112,  112,
  112,  115,  183,  141,   78,   78,   78,   78,   78,   78,
  123,  123,   58,  186,  116,   90,   90,   90,   90,   90,
   90,   90,   90,  117,  118,  119,  187,   26,  193,    9,
  195,   58,  196,    3,   41,   42,   43,   44,   45,  200,
   35,   95,   95,   95,   95,   95,   95,  122,  122,   69,
   94,   94,   94,   94,   94,   94,   56,    7,   56,   14,
   67,    8,   47,   33,   41,    9,   34,   87,   10,   11,
   29,   65,   28,   87,   95,   95,   95,   95,   95,   95,
    9,   46,   71,   70,   27,   41,   42,   43,   44,   45,
  103,  100,  194,  181,  114,   58,  173,   58,  103,  103,
  103,  103,  103,  103,  159,  105,  105,  105,  105,  105,
  105,  104,  104,  104,  104,  104,  104,  162,  110,  110,
  110,  110,  110,  110,  163,  189,    0,    0,  111,  111,
  111,  111,  111,  111,    0,   25,    0,  113,  113,  113,
  113,    0,    0,    0,   32,   24,    0,  114,  114,  114,
  114,    7,    0,   67,    0,    8,  115,  115,    0,    9,
    7,   69,   10,   11,    8,    0,    0,    0,    9,  116,
  116,   10,   11,    0,    0,    0,    0,    0,    0,  117,
  118,   67,    0,    0,    0,    0,   12,    0,   13,   69,
   56,   56,    0,   56,   56,   56,   56,    0,   56,   56,
    0,    0,   56,   56,   56,   56,   56,   56,   56,   67,
    0,    7,    0,    0,    0,    8,    0,   69,    0,    9,
    0,    0,   10,   11,   41,   42,   43,   44,   45,   58,
   58,   20,   58,   58,   58,   58,    0,   58,   58,    0,
   20,   58,   58,   58,   58,   58,   58,   58,    0,   20,
   49,    0,    0,    0,    0,    0,    0,    0,    0,   87,
    0,    0,    0,    0,    0,   84,    0,   99,    0,    0,
    0,    0,    0,   84,    0,  176,    0,    0,   49,   36,
    7,   67,   37,   38,    8,   39,  177,   40,    9,   69,
    0,   10,   11,   41,   42,   43,   44,   45,  120,  120,
    0,  118,    0,    0,    0,  191,   49,   84,    0,    0,
    0,   67,    0,    0,    0,  197,    0,  198,  139,   69,
    0,    7,  201,    0,   84,    8,    0,   84,    0,    9,
  146,    0,   10,   11,    0,    0,   84,    0,    0,   20,
    0,  120,  120,  120,  120,  120,    0,   12,    0,   13,
  120,  120,  120,  120,  120,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   87,    0,    0,    0,    0,    0,   84,  174,    0,
    0,    0,    0,    0,    0,    0,    0,  178,    0,    0,
    0,    0,    0,   84,   84,   84,    0,    0,   20,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  174,    0,
    0,    0,    0,    0,    0,    0,  199,    0,    0,    0,
    0,    0,   84,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   61,  278,  279,   61,  123,   40,   40,   61,  183,   80,
   41,   45,   44,   44,   60,   37,   62,   41,  193,   41,
   42,   43,   44,   45,   46,   47,   37,   59,   59,   74,
   41,   42,   43,   44,   45,   46,   47,   59,   60,   61,
   62,   40,  284,   41,   24,  268,   44,   46,   59,   60,
   61,   62,   37,   40,  266,   44,   41,   42,   43,   44,
   45,   37,   47,  168,  169,   41,   42,   43,   44,   45,
   59,   47,   52,   40,   59,   60,   61,   62,    5,   46,
   43,  152,   45,   59,   60,   37,   62,   14,  123,   41,
   42,   43,   44,   45,  125,   47,   23,   33,  128,  129,
   80,  125,  147,  125,   40,  123,   44,   59,   60,   45,
   62,  182,  285,   41,  125,   43,   44,   45,  135,  136,
   41,   59,   43,   44,   45,  266,   41,  123,   43,   44,
   45,   59,   60,   41,   62,   46,   44,   91,   59,   60,
  125,   62,  261,   41,   59,   60,   44,   62,  266,  125,
   41,   59,   60,   44,   62,  266,   82,   83,   59,  125,
   41,   59,   60,   44,   62,   59,   41,   41,   59,   44,
   44,   46,  152,  125,   40,   40,   40,   37,   59,   59,
   41,   93,   42,   44,   59,   59,  113,   47,   46,   41,
   41,   41,   44,   44,   44,   40,   59,  125,   59,  125,
  126,  127,  182,  280,  125,  266,  281,   59,   59,   59,
  125,   41,   44,  266,   41,   59,   44,  125,  287,   41,
   41,  282,  283,   40,  282,  283,   41,  125,  282,  283,
  276,  277,  266,   44,  125,   41,   44,  271,  272,  273,
  274,  275,   59,   40,  125,  172,   59,  278,  279,  280,
  281,  125,  259,  287,  276,  277,  278,  279,  280,  281,
  282,  283,   40,  125,  125,  276,  277,  278,  279,  280,
  281,  282,  283,  125,  125,  125,   41,  125,  259,  266,
   41,   59,   40,    0,  271,  272,  273,  274,  275,   41,
  125,  276,  277,  278,  279,  280,  281,  282,  283,   59,
  276,  277,  278,  279,  280,  281,  123,  258,  125,  266,
   59,  262,   40,  264,   59,  266,  125,   41,  269,  270,
   41,   59,   41,  125,  276,  277,  278,  279,  280,  281,
  266,   59,   41,   41,   14,  271,  272,  273,  274,  275,
   52,   48,  184,  172,   80,  123,  151,  125,  276,  277,
  278,  279,  280,  281,  134,  276,  277,  278,  279,  280,
  281,  276,  277,  278,  279,  280,  281,  137,  276,  277,
  278,  279,  280,  281,  138,  182,   -1,   -1,  276,  277,
  278,  279,  280,  281,   -1,   13,   -1,  278,  279,  280,
  281,   -1,   -1,   -1,   22,  123,   -1,  278,  279,  280,
  281,  258,   -1,   24,   -1,  262,  280,  281,   -1,  266,
  258,   24,  269,  270,  262,   -1,   -1,   -1,  266,  280,
  281,  269,  270,   -1,   -1,   -1,   -1,   -1,   -1,  281,
  281,   52,   -1,   -1,   -1,   -1,  284,   -1,  286,   52,
  257,  258,   -1,  260,  261,  262,  263,   -1,  265,  266,
   -1,   -1,  269,  270,  271,  272,  273,  274,  275,   80,
   -1,  258,   -1,   -1,   -1,  262,   -1,   80,   -1,  266,
   -1,   -1,  269,  270,  271,  272,  273,  274,  275,  257,
  258,    5,  260,  261,  262,  263,   -1,  265,  266,   -1,
   14,  269,  270,  271,  272,  273,  274,  275,   -1,   23,
   24,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   39,
   -1,   -1,   -1,   -1,   -1,   39,   -1,   47,   -1,   -1,
   -1,   -1,   -1,   47,   -1,  153,   -1,   -1,   52,  257,
  258,  152,  260,  261,  262,  263,  164,  265,  266,  152,
   -1,  269,  270,  271,  272,  273,  274,  275,   82,   83,
   -1,   81,   -1,   -1,   -1,  183,   80,   81,   -1,   -1,
   -1,  182,   -1,   -1,   -1,  193,   -1,  195,   98,  182,
   -1,  258,  200,   -1,   98,  262,   -1,  101,   -1,  266,
  110,   -1,  269,  270,   -1,   -1,  110,   -1,   -1,  113,
   -1,  125,  126,  127,  128,  129,   -1,  284,   -1,  286,
  134,  135,  136,  137,  138,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  151,   -1,   -1,   -1,   -1,   -1,  151,  152,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  167,   -1,   -1,
   -1,   -1,   -1,  167,  168,  169,   -1,   -1,  172,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  182,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  196,   -1,   -1,   -1,
   -1,   -1,  196,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=287;
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
"INCREMENT","DECREMENT","PUBLIC","STATIC","ConstructorDeclarator","ArgListOp",
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
"StmtExpr : InstantiationExpr",
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
"InstantiationExpr : Name '(' ArgListOpt ')'",
"ArgListOpt : ArgList",
"ArgListOpt :",
"ArgList : Expr",
"ArgList : ArgList ',' Expr",
"FieldAccess : Primary '.' IDENTIFIER",
"MethodCall : Name '(' ArgListOp ')'",
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

//#line 257 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"

int yylex() {
    return j0.yylex();
}

void yyerror(String s) {
    j0.lexErr(s);
}
//#line 545 "Parser.java"
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
{ yyval=j0.node("ClassDecl", 1000, val_peek(1), val_peek(0)); }
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
case 56:
//#line 119 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("IfThenStmt", 1150, val_peek(2), val_peek(0)); }
break;
case 57:
//#line 122 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("IfThenElseStmt", 1160, val_peek(4), val_peek(2), val_peek(0)); }
break;
case 58:
//#line 125 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("IfThenElseIfStmt", 1170, val_peek(3), val_peek(1), val_peek(0)); }
break;
case 59:
//#line 126 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("IfThenElseIfStmt", 1171, val_peek(5), val_peek(3), val_peek(2), val_peek(0)); }
break;
case 61:
//#line 130 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ElseIfSequence", 1180, val_peek(1), val_peek(0)); }
break;
case 62:
//#line 133 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ElseIfStmt", 1190, val_peek(0)); }
break;
case 63:
//#line 136 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("WhileStmt", 1210, val_peek(2), val_peek(0)); }
break;
case 64:
//#line 139 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ForStmt", 1220, val_peek(6), val_peek(4), val_peek(2), val_peek(0)); }
break;
case 73:
//#line 156 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("StmtExprList", 1230, val_peek(2), val_peek(0)); }
break;
case 75:
//#line 162 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ReturnStmt", 1250, val_peek(1)); }
break;
case 77:
//#line 166 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=val_peek(1); }
break;
case 85:
//#line 178 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("InstantiationExpr", 1260, val_peek(3), val_peek(1)); }
break;
case 89:
//#line 186 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("ArgList", 1270, val_peek(2), val_peek(0)); }
break;
case 90:
//#line 189 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("FieldAccess", 1280, val_peek(2), val_peek(0)); }
break;
case 91:
//#line 192 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodCall", 1290, val_peek(3), val_peek(1)); }
break;
case 92:
//#line 193 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodCall", 1291, val_peek(5), val_peek(3), val_peek(1)); }
break;
case 93:
//#line 194 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MethodCall", 1292, val_peek(5), val_peek(3), val_peek(1)); }
break;
case 96:
//#line 201 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("UnaryExpr", 1300, val_peek(0)); }
break;
case 97:
//#line 202 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("UnaryExpr", 1301, val_peek(0)); }
break;
case 100:
//#line 207 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MulExpr", 1310, val_peek(2), val_peek(0)); }
break;
case 101:
//#line 208 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MulExpr", 1311, val_peek(2), val_peek(0)); }
break;
case 102:
//#line 209 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("MulExpr", 1312, val_peek(2), val_peek(0)); }
break;
case 104:
//#line 213 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("AddExpr", 1320, val_peek(2), val_peek(0)); }
break;
case 105:
//#line 214 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("AddExpr", 1321, val_peek(2), val_peek(0)); }
break;
case 111:
//#line 224 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("RelExpr", 1330, val_peek(2), val_peek(1), val_peek(0)); }
break;
case 113:
//#line 228 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("EqExpr", 1340, val_peek(2), val_peek(0)); }
break;
case 114:
//#line 229 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("EqExpr", 1341, val_peek(2), val_peek(0)); }
break;
case 116:
//#line 233 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("CondAndExpr", 1350, val_peek(2), val_peek(0)); }
break;
case 118:
//#line 237 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("CondOrExpr", 1360, val_peek(2), val_peek(0)); }
break;
case 121:
//#line 244 "/home/I331555/code/java/buildyourownprogramminglanguage/byopl-app/src/main/jflex/nl/loadingdata/j0/j0.y"
{ yyval=j0.node("Assignment", 1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 882 "Parser.java"
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
