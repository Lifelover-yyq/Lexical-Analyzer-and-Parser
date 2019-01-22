package Analysis;

import java.util.*;


public class ProjectGroup extends GetGotoTable{
	
	protected List<Production> GOTO(List<Production> ps,Symbol symbol){
		List<Production> lp=null;
		for(Production p:ps){
			if(p.isend==false)
				if(p.body[p.dotpos].equals(symbol)){
					if(lp==null)
						lp=new ArrayList<Production>(Arrays.asList(new Production(p.head,p.body,p.dotpos+1)));
					else
						lp.add(new Production(p.head,p.body,p.dotpos+1));
				}
		}
		return CLOSURE(lp);
	}
	protected List<Production> CLOSURE(List<Production> I){
		if(I==null)return I;
		for(int i=0;i<I.size();i++){
			Production p=I.get(i);
			if(p.isend==false){
				List<Production> ps=table.get(p.body[p.dotpos]);
				if(ps!=null){
						for(Production p1:ps)
							if(!I.contains(p1))
								I.add(p1);
				}
			}
		}
		return I;
	}
	protected void constructItems(){
		goto_table=new TreeMap<String,Integer>();
		setofitems=new ArrayList<List<Production>>();
		setofitems.add(CLOSURE(new ArrayList<Production>(Arrays.asList(productions[0]))));
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
		/*构造项目集setofitems={I0,I1,I2,...In}*/
		if(goto_table==null)constructItems();
		action_table=new TreeMap<String,Action>();
		for(int i=0;i<setofitems.size();i++)
			for(Production p:setofitems.get(i)){
				/*如果[A->α·aβ]在Ii中并接GOTO(Ii,a)=Ij,设置ACTION[i,a]为shift i。a必须为终结符*/
				if(p.isend==false)
					if(p.body[p.dotpos].isterminal){
						Integer Ij=goto_table.get(i+p.body[p.dotpos].toString());
						if(Ij!=null){
							Action t=action_table.get(i+p.body[p.dotpos].toString());
							Action act=new Action(true,Ij.intValue());
							if(t!=null&&!t.isshift)
								throw new ConflictException("不是SLR文法。a shift/reduce conflict:不确定是否用"+getProduction(t.state)+"进行规约");
							action_table.put(i+p.body[p.dotpos].toString(),act);
						}
					}
				if(p.isend==true)
					/*如果[A->α·]在Ii,对于所有在FOLLOW(A)中的a,设置ACTION[i,a]为reduce A->α。A不是S'。*/
					if(!p.head.equals(symbols[0])){
						Set<Symbol> follow=FOLLOW(p.head);
						Iterator<Symbol> it=follow.iterator();
						int j=lookIndex(new Production(p.head,p.body,0));
						while(it.hasNext()){
							Symbol s=it.next();
							Action t=action_table.get(i+s.toString());
							if(t!=null)
								if(t.isshift)
									throw new ConflictException("不是SLR文法。a shift/reduce conflict:不确定是否用"+getProduction(j)+"进行规约");
								else
									throw new ConflictException("不是SLR文法。a reduce/reduce conflict at:"+getProduction(t.state)+" and "+getProduction(j));
							action_table.put(i+s.toString(),new Action(false,j));
						}
					/*如果[S'->S]在Ii中，设置ACTION[i,$]为accept*/
					}else
						action_table.put(i+dollar.toString(),new Action(false,-1));
			}
	}
	
	public ProjectGroup(Production[] productions,Symbol[] sym){
		super(productions,sym);
		
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
		ProjectGroup m=new ProjectGroup(productions,symbols);
		m.constructActions();
		System.out.println(m.setofitems);
	
	}
}