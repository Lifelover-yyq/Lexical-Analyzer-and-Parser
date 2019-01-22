package Analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class GetFirstFollow extends ParseCollection{
	/*每个符号的first集*/
	private Map<Symbol,Set<Symbol>> firstset;
	/*每个符号的follow集*/
	private Map<Symbol,Set<Symbol>> followset;
	/*ε符号*/
	protected final Symbol epsilon;
	/*$符号*/
	protected final Symbol dollar;
	  /*判断一个符号是否能推出ε*/
	private boolean deriveEmpty(Symbol s){
		if(s.isterminal)
			if(s.equals(epsilon)||s.equals(dollar))
				return true;
			else
				return false;
		boolean de=false;
		List<Production> lp=table.get(s);
		for(Production p:lp)
			de|=deriveEmpty(s,p.body);
		return de;
	}
	/*判断一个规则体是否能推出ε*/
	private boolean deriveEmpty(Symbol head,Symbol[] body){
		boolean samerecur=true;
		boolean de=true;
		for(Symbol s:body){
			if(s.isterminal){
				return false;
			}
			if(!s.equals(head)){
				samerecur=false;
				de&=deriveEmpty(s);
			}
		}
		return samerecur ? false : de;
	}

	/*构造函数*/
	public GetFirstFollow(Production[] productions,Symbol[] sym){
		super(productions,sym);
		firstset=new HashMap<Symbol,Set<Symbol>>();
		followset=new HashMap<Symbol,Set<Symbol>>();
		epsilon=new Symbol("ε",true);
		dollar=new Symbol("$",true);
	}
	
	/*得到某个符号组的first集*/
	public Set<Symbol> FIRST(Symbol[] ss){
		List<Symbol> arg=new ArrayList<Symbol>(Arrays.asList(ss));
		return FIRST(arg);
	}
	
	/*到某个符号组的first集*/
	public Set<Symbol> FIRST(List<Symbol> ss){
		Set<Symbol> ls=new TreeSet<Symbol>();
		if(ss.size()==0){
			ls.add(epsilon);
			return ls;
		}
		for(Symbol s:ss){
			if(!deriveEmpty(s)){
				ls.addAll(FIRST(s));;
				break;
			}
			ls.addAll(FIRST(s));
		}
		return ls;
	}
	/*得到某个符号的first集*/
	public Set<Symbol> FIRST(Symbol s){
		Set<Symbol> ls=firstset.get(s);
		
		if(s.isterminal){
			if(ls==null){
				ls=new TreeSet<Symbol>();
				if(s.equals(epsilon)||s.equals(dollar))
					ls.add(epsilon);
				else
					ls.add(s);
				firstset.put(s,ls);
			}
			return ls;
		}
		if(!s.isterminal){
			if(ls!=null)return ls;
			ls=new TreeSet<Symbol>();
			List<Production> lp=table.get(s);
			for(Production p:lp)
				if(!p.head.equals(p.body[0]))
					ls.addAll(FIRST(p.body));
			firstset.put(s,ls);
			return ls;
		}
		if(deriveEmpty(s)){
			if(ls==null){
				ls=new TreeSet<Symbol>(Arrays.asList(epsilon));
				firstset.put(s,ls);
			}
			ls.add(epsilon);
			return ls;
		}
		return null;
	}
	/*得到某个符号的Follow集*/
	public Set<Symbol> FOLLOW(Symbol s){
		Set<Symbol> ls=followset.get(s);
		if(ls!=null)return ls;
		ls=new TreeSet<Symbol>();
		if(s.equals(symbols[0])){
			ls.add(dollar);
			followset.put(s,ls);
			return ls;
		}
		for(Production p:productions){
			for(int i=0;i<p.body.length;i++){
				if(s.equals(p.body[i])){
						Set<Symbol> as=FIRST(Symbol.subArray(p.body,i+1,p.body.length-1));
						if(as.contains(epsilon)){
							if(!s.equals(p.head))
								ls.addAll(FOLLOW(p.head));
						}else{
							ls.addAll(as);
						}
				}
			}
		}
		followset.put(s,ls);
		return ls;
	}
	public static void main(String[] args)throws ConflictException{
		Symbol _P = new Symbol("P'");
    	Symbol P = new Symbol("P");   			
    	Symbol D = new Symbol("D");  			
    	Symbol S = new Symbol("S");
    	Symbol L = new Symbol("L");
    	Symbol E = new Symbol("E");
    	Symbol C = new Symbol("C");
    	Symbol T = new Symbol("T");
    	Symbol F = new Symbol("F");

    	Symbol ID = new Symbol("id",true);
    	Symbol SEMI = new Symbol(";",true);
    	Symbol INT = new Symbol("int",true);
   	    Symbol FLOAT = new Symbol("float",true);
    	Symbol EQ = new Symbol("=",true);
    	Symbol SLP = new Symbol("(",true);
    	Symbol SRP = new Symbol(")",true);
    	Symbol IF = new Symbol("if",true);
    	Symbol ELSE = new Symbol("else",true);
    	Symbol WHILE = new Symbol("while",true);
    	Symbol GT = new Symbol(">",true);
    	Symbol LT = new Symbol("<",true);
    	Symbol COMP = new Symbol("==",true);
    	Symbol ADD = new Symbol("+",true);
    	Symbol SUB = new Symbol("-",true);
    	Symbol MUL = new Symbol("*",true);
    	Symbol DIV = new Symbol("/",true);
    	Symbol INT10 = new Symbol("int10",true);
    	Production[] productions= new Production[]{new Production(_P,new Symbol[]{P}),
    			                                           
    	                                           new Production(P ,new Symbol[]{D,S}),
    	                                           
    	                                           new Production(P ,new Symbol[]{S}),
      
    	                                           new Production(D ,new Symbol[]{L,ID,SEMI,D}),
    											    
    	                                           new Production(D ,new Symbol[]{L,ID,SEMI}),
    											           
    	                                           new Production(L ,new Symbol[]{INT}),
    											         
    	                                           new Production(L ,new Symbol[]{FLOAT}),
    	                                           
    	                                           new Production(S ,new Symbol[]{ID,EQ,E,SEMI}),
    											    
    	                                           new Production(S ,new Symbol[]{IF,SLP,C,SRP,S}),

    	                                           new Production(S ,new Symbol[]{ELSE,S}),
    											    
    	                                           new Production(S ,new Symbol[]{WHILE,SLP,C,SRP,S}),

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
    	Symbol[] symbols=new Symbol[]{_P,P,D,S,L,E,C,T,F,ID,SEMI,INT,FLOAT,EQ,SLP,SRP,IF,ELSE,WHILE,GT,LT,COMP,ADD,SUB,MUL,DIV,INT10};

							
		GetFirstFollow m=new GetFirstFollow(productions,symbols);
		System.out.println(m.FIRST(_P));
		System.out.println(m.FIRST(P));
		System.out.println(m.FIRST(D));
		System.out.println(m.FIRST(L));
		System.out.println(m.FIRST(S));
		System.out.println(m.FIRST(E));
		System.out.println(m.FIRST(C));
		System.out.println(m.FIRST(T));
		System.out.println(m.FIRST(F));

		System.out.println(m.FOLLOW(_P));
		System.out.println(m.FOLLOW(P));
		System.out.println(m.FOLLOW(D));
		System.out.println(m.FOLLOW(L));
		System.out.println(m.FOLLOW(S));
		System.out.println(m.FOLLOW(E));
		System.out.println(m.FOLLOW(C));
		System.out.println(m.FOLLOW(T));
		System.out.println(m.FOLLOW(F));
	}
									   
}