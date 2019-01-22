package Analysis;

import java.io.IOException;

public class BeginMain {
	/*定义语法符号 与 产生式*/
	//非终结符
	static  Symbol _P = new Symbol("P'");
	static  Symbol P = new Symbol("P");   			
	static	Symbol D = new Symbol("D");  			
	static	Symbol S = new Symbol("S");
	static	Symbol L = new Symbol("L");
	static	Symbol E = new Symbol("E");
	static	Symbol C = new Symbol("C");
	static	Symbol T = new Symbol("T");
	static	Symbol F = new Symbol("F");
	//终结符
	static	Symbol ID = new Symbol("id",true);
	static	Symbol SEMI = new Symbol(";",true);
	static	Symbol INT = new Symbol("int",true);
	static	Symbol FLOAT = new Symbol("float",true);
	static	Symbol EQ = new Symbol("=",true);
	static	Symbol SLP = new Symbol("(",true);
	static	Symbol SRP = new Symbol(")",true);
	static	Symbol IF = new Symbol("if",true);
	static	Symbol ELSE = new Symbol("else",true);
	static	Symbol WHILE = new Symbol("while",true);
	static  Symbol GT = new Symbol(">",true);
	static	Symbol LT = new Symbol("<",true);
	static  Symbol COMP = new Symbol("==",true);
	static  Symbol ADD = new Symbol("+",true);
	static  Symbol SUB = new Symbol("-",true);
	static  Symbol MUL = new Symbol("*",true);
	static  Symbol DIV = new Symbol("/",true);
	static  Symbol INT10 = new Symbol("int10",true);
	//产生式
	static Production[] productions = new Production[]{new Production(_P,new Symbol[]{P}),
			                                           
	                                           new Production(P ,new Symbol[]{D,S}),
	                                           
	                                           new Production(P ,new Symbol[]{S}),
  
	                                           new Production(D ,new Symbol[]{L,ID,SEMI,D}),
											    
	                                           new Production(D ,new Symbol[]{L,ID,SEMI}),
											           
	                                           new Production(L ,new Symbol[]{INT}),
											     
	                                           new Production(L ,new Symbol[]{FLOAT}),

	                                           new Production(S ,new Symbol[]{ID,EQ,E,SEMI}),
											    
	                                           new Production(S ,new Symbol[]{IF,SLP,C,SRP,S}),
	                                           
	                                           new Production(S ,new Symbol[]{WHILE,SLP,C,SRP,S}),

	                                           new Production(S ,new Symbol[]{ELSE,S}),
											      
	                                           new Production(S ,new Symbol[]{S,S}),

	                                           new Production(C ,new Symbol[]{E,GT,E}),
	                                           
	                                           new Production(C ,new Symbol[]{E,LT,E}),
	                                           
	                                           new Production(C ,new Symbol[]{E,COMP,E}),
	                                           
	                                           new Production(E ,new Symbol[]{E,ADD,T}),
	                                           
	                                           new Production(E ,new Symbol[]{E,SUB,T}),
	                                           
	                                           new Production(E ,new Symbol[]{T}),
	                                           
	                                           new Production(T ,new Symbol[]{F}),
	                                           
	                                           new Production(T ,new Symbol[]{T,MUL,F}),

	                                           new Production(T ,new Symbol[]{T,DIV,F}),
	                                           
	                                           new Production(F ,new Symbol[]{SLP,E,SRP}),
	                                           
	                                           new Production(F ,new Symbol[]{ID}),
	                                           
	                                           new Production(F ,new Symbol[]{INT10})
	                                          };
	//文法符号集合
	static Symbol[] symbols = new Symbol[]{_P,P,D,S,L,E,C,T,F,ID,SEMI,INT,FLOAT,EQ,SLP,SRP,IF,ELSE,WHILE,GT,LT,COMP,ADD,SUB,MUL,DIV,INT10};
	public static void main(String[] args) throws IOException, ConflictException {
		LexicalScanner.result();	//词法分析，得到forSLR.txt文件供语法分析调用
		//得到Action表 Goto表
    	Parser parser = new Parser(productions,symbols,new ForAction());		
    	//进行语法分析	
    	parser.parse();

	}

}
