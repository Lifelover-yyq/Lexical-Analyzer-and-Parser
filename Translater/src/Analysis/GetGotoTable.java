package Analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GetGotoTable extends GetFirstFollow implements ForParser{
	/*goto表*/
	protected Map<String,Integer> goto_table;
	/*action表*/
	protected Map<String,Action> action_table;
	/*项目集*/
	protected List<List<Production>> setofitems;
	protected int lookIndex(Production p){
		int i=0;
		for(Production pp:productions){
			if(pp.equals(p))
				return i;
			i++;
		}
		return -1;
	}
	protected boolean find(List<List<Production>> sois,List<Production> sops,
	                     int init,Symbol symbol){
		for(int i=0;i<sois.size();i++){
			if(sois.get(i).equals(sops)){
				goto_table.put(init+symbol.toString(),i);
				return true;
			}
		}
		return false;
	}
	protected List<Production> CLOSURE(List<Production> I){
		if(I==null)return I;
		for(int i=0;i<I.size();i++){
			Production p=I.get(i);
			if(p.isend==false&&p.lookahead!=null){
				List<Production> ps=table.get(p.body[p.dotpos]);
				if(ps!=null){
						Production pc=new Production(p.head,p.body,p.dotpos+1,p.lookahead);
						for(Production p1:ps){
							if(pc.isend&&pc.lookahead.equals(dollar)){
								Production np=new Production(p1,dollar);
								if(!I.contains(np))
									I.add(np);
							}else{
								List<Symbol> ls=
								new ArrayList<Symbol>(Arrays.asList(Symbol.subArray(pc.body,
																					pc.dotpos,pc.body.length-1)));
								ls.add(pc.lookahead);
								for(Symbol s:FIRST(ls)){
									Production np=new Production(p1,s);
									if(!I.contains(np))
										I.add(np);
								}
								
							}
						}
				}
			}
		}
		return I;
	}
	protected List<Production> GOTO(List<Production> ps,Symbol symbol){
		List<Production> lp = null;
		for(Production p:ps){
			if(p.isend==false)
				if(p.body[p.dotpos].equals(symbol)){
					if(lp==null)
						lp=new ArrayList<Production>(Arrays.asList(new Production(p.head,p.body,p.dotpos+1,p.lookahead)));
					else
						lp.add(new Production(p.head,p.body,p.dotpos+1,p.lookahead));
				}
		}
		
		return CLOSURE(lp);
	}
	protected void constructItems(){
		
		goto_table=new TreeMap<String,Integer>();
		setofitems=new ArrayList<List<Production>>();
		setofitems.add(CLOSURE(new ArrayList<Production>(Arrays.asList(new Production(productions[0],dollar)))));
		for(int i=0;i<setofitems.size();i++){
			List<Production> lp=setofitems.get(i);
			int j=setofitems.size();
			for(Production p:lp){
				if(p.isend==false){
					List<Production> result=GOTO(lp,p.body[p.dotpos]);
					if(result!=null&&!find(setofitems,result,i,p.body[p.dotpos])){
						setofitems.add(result);
						goto_table.put(i+p.body[p.dotpos].toString(),j++);
					}
				}
			}
		}
	}
	protected void constructActions()throws ConflictException{
		if(goto_table==null)constructItems();
		action_table=new TreeMap<String,Action>();
		for(int i=0;i<setofitems.size();i++)
			for(Production p:setofitems.get(i)){
				if(p.isend==false)
					if(p.body[p.dotpos].isterminal){
						Integer Ij=goto_table.get(i+p.body[p.dotpos].toString());
						if(Ij!=null)
							action_table.put(i+p.body[p.dotpos].toString(),new Action(true,Ij.intValue()));
					}
				if(p.isend==true)
					if(!p.head.equals(symbols[0])){
						int j=lookIndex(new Production(p.head,p.body,0));
						action_table.put(i+p.lookahead.toString(),new Action(false,j));
					}else
						action_table.put(i+(new Symbol("$",true)).toString(),new Action(false,-1));
			}
	}
	public GetGotoTable(Production[] productions,Symbol[] sym){
		super(productions,sym);
		goto_table=null;
		action_table=null;
		setofitems=null;
	}
	public Map<String,Integer> getGotoTable(){
		if(goto_table==null){
			constructItems();
			return goto_table;
		}
		return goto_table;
	}
	public Map<String,Action> getActionTable()throws ConflictException{
		if(action_table==null){
			constructActions();
			return action_table;
		}
		return action_table;
	}
	public Production getProduction(int i){
		return productions[i];
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
//    	Symbol FLOAT = new Symbol("float",true);
    	Symbol EQ = new Symbol("=",true);
    	Symbol SLP = new Symbol("(",true);
    	Symbol SRP = new Symbol(")",true);
    	Symbol IF = new Symbol("if",true);
    	Symbol ELSE = new Symbol("else",true);
    	Symbol GT = new Symbol(">",true);
    	Symbol LT = new Symbol("<",true);
    	Symbol ADD = new Symbol("+",true);
    	Symbol SUB = new Symbol("-",true);
    	Symbol INT10 = new Symbol("int10",true);
    	Symbol e = new Symbol("ε",true);	//ε
    	Production[] productions= new Production[]{new Production(_P,new Symbol[]{P}),
    			                                           
    	                                           new Production(P ,new Symbol[]{D,S}),
    											           
    	                                           new Production(D ,new Symbol[]{L,ID,SEMI,D}),
    											    
    	                                           new Production(D ,new Symbol[]{e}),
    											           
    	                                           new Production(L ,new Symbol[]{INT}),
    											           
    	                                           new Production(S ,new Symbol[]{ID,EQ,E,SEMI}),
    											          
    	                                           new Production(S ,new Symbol[]{IF,SLP,C,SRP,S,ELSE,S}),
    											           
    	                                           new Production(C ,new Symbol[]{E,GT,E}),
    	                                           
    	                                           new Production(C ,new Symbol[]{E,LT,E}),
    	                                           
    	                                           new Production(E ,new Symbol[]{E,ADD,T}),
    	                                           
    	                                           new Production(E ,new Symbol[]{E,SUB,T}),
    	                                           
    	                                           new Production(E ,new Symbol[]{T}),
    	                                           
    	                                           new Production(T ,new Symbol[]{F}),
    	                                           
    	                                           new Production(F ,new Symbol[]{SLP,E,SRP}),
    	                                           
    	                                           new Production(F ,new Symbol[]{ID}),
    	                                           
    	                                           new Production(F ,new Symbol[]{INT10})

    	                                          };
    	Symbol[] symbols=new Symbol[]{_P,P,D,S,L,E,C,T,F,ID,SEMI,INT,EQ,SLP,SRP,IF,ELSE,GT,LT,ADD,SUB,INT10};
		GetGotoTable m = new GetGotoTable(productions,symbols);
		
		System.out.println(m.getActionTable());
		System.out.println(m.getGotoTable());
	}
}