package Analysis;

import java.util.*;


public class GetActionTable extends ProjectGroup{
	private List<List<Production>> setofkernelitems;
	private List<Production> closure(List<Production> I){
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
	private void filterNonKernel(){
		setofkernelitems=new ArrayList<List<Production>>();
		for(List<Production> lp:setofitems){
			List<Production> ps=new ArrayList<Production>();
			for(Production p:lp){
				if(p.lookahead==null&&(p.head.equals(symbols[0])||p.dotpos!=0))
					ps.add(p);
			}
			setofkernelitems.add(ps);
		}
		setofitems=null;
	}
	private void spontaneousGenerator(Production p,int i){
		if(p.lookahead==null){
					Production t=new Production(p,dollar);
					if(!setofkernelitems.get(i).contains(t))
						setofkernelitems.get(i).add(t);
					//List<Production> items=new ArrayList<Production>(Arrays.asList(t));
					List<Production> items=closure(setofkernelitems.get(i));
		
					for(int m=0;m<items.size();m++){
						Production pi=items.get(m);
						if(!pi.isend){
							List<Production> gitems=setofkernelitems.get(goto_table.get(i+pi.body[pi.dotpos].toString()));
							for(int j=0;j<gitems.size();j++){
								Production pj=gitems.get(j);
								if((pj.head.toString()+pj.body).equals(pi.head.toString()+pi.body)&&
									pj.dotpos>0&&pj.body[pj.dotpos-1].equals(pi.body[pi.dotpos])){
									Production np=new Production(pj,pi.lookahead);
									if(!gitems.contains(np))
										gitems.add(np);
								}		
							}
						}
					}
		}
	}
	private void propagation(int i){
		for(int a=i;a<setofkernelitems.size();a++){
					for(int b=0;b<setofkernelitems.get(a).size();b++){
						Production pi=setofkernelitems.get(a).get(b);
						if(pi.lookahead!=null&&!pi.isend){
							List<Production> gitems=setofkernelitems.get(goto_table.get(a+pi.body[pi.dotpos].toString()));
							for(int j=0;j<gitems.size();j++){
								Production pj=gitems.get(j);
								if((pj.head.toString()+pj.body).equals(pi.head.toString()+pi.body)&&
									pj.dotpos>0&&pj.body[pj.dotpos-1].equals(pi.body[pi.dotpos])){
									Production np=new Production(pj,pi.lookahead);
									if(!gitems.contains(np))
										gitems.add(np);
								}		
							}
						}
					}
		}
	}
	private void determinLookaheads(){
		if(goto_table==null)constructItems();
		filterNonKernel();
		for(int i=0;i<setofkernelitems.size();i++){
			for(int k=0;k<setofkernelitems.get(i).size();k++){
				/*spontaneous generator*/
				Production p=setofkernelitems.get(i).get(k);
				spontaneousGenerator(p,i);
				/*propagation*/
				propagation(i+1);
			}
		}
	}
	protected void constructActions()throws ConflictException{
		if(goto_table==null)constructItems();
		determinLookaheads();
		action_table=new TreeMap<String,Action>();
		for(int i=0;i<setofkernelitems.size();i++)
			for(Production p:setofkernelitems.get(i)){
				if(p.isend==false)
					if(p.body[p.dotpos].isterminal){
						Integer Ij=goto_table.get(i+p.body[p.dotpos].toString());
						if(Ij!=null)
							action_table.put(i+p.body[p.dotpos].toString(),new Action(true,Ij.intValue()));
					}
				if(p.isend==true)
					if(!p.head.equals(symbols[0])){
						if(p.lookahead!=null){
							int j=lookIndex(new Production(p.head,p.body,0));
							action_table.put(i+p.lookahead.toString(),new Action(false,j));
						}
					}else
						action_table.put(i+dollar.toString(),new Action(false,-1));
			}
	}
	public GetActionTable(Production[] productions,Symbol[] sym){
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
    	Symbol[] symbols = new Symbol[]{_P,P,D,S,L,E,C,T,F,ID,SEMI,INT,FLOAT,EQ,SLP,SRP,IF,ELSE,WHILE,GT,LT,COMP,ADD,SUB,MUL,DIV,INT10};
		GetActionTable m = new GetActionTable(productions,symbols);
	
		m.constructActions();
		System.out.println(m.action_table);
	}
}	