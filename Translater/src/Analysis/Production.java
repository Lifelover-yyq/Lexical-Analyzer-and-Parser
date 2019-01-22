package Analysis;

public class Production{
		/*
	     * head字段
	     * 表示语法推导规则的首部
	     * 例如E->T+F中的E
	     */
 public final Symbol head;
		/*
	     * body字段
	     * 表示语法推导规则的规则体
	     * 例如E->T+F中的T+F
	     */
 public final Symbol[] body;
	   /*
	    *dotpos字段
	    *表示用于SLR,LR,LALR项目集构造时推导规则中的点的位置
	    * 例如E->·T+F,dotpos=0
	    */
 public final int dotpos;
	   /*
	    *isend字段
	    *表示点是否在规则的末尾
	    */
	public final boolean isend;
	   /*
	    *lookahead字段
	    *表示用于LR,LALR项目集构造时推导规则中的向前搜索符
	    *例如:E->T+F,$中的$
	    */
	public final Symbol lookahead;
	   /*
	    *identify字段
	    *表示每个推导规则的id
	    */
 private String identify;
	   /*
	    *head表示文法的首部
	    *body表示文法的规则体
	    */
	public Production(Symbol head,Symbol[] body){
		this(head,body,0);
	}
	   /*
	    *pos表示表示用于LR项目集构造时推导规则中的点的位置
	    */
 public Production(Symbol head,Symbol[] body,int pos){
		this(head,body,pos,null);
	}
	   /*
	    *t表示表示用于LR项目集构造时推导规则中的向前搜索符
	    */
	public Production(Production p,Symbol t){
		this(p.head,p.body,p.dotpos,t);
	}
	   /*
	    *head表示文法的首部
	    *body表示文法的规则体
	    *pos表示表示用于LR项目集构造时推导规则中的点的位置
	    *t表示表示用于LR项目集构造时推导规则中的向前搜索符
	    */
	public Production(Symbol head,Symbol[] body,int pos,Symbol t){
		this.head=head;
		this.body=body;
		lookahead=t;
		dotpos=pos;
		identify=head+"->";
		for(Symbol s:body)
			identify+=s;
		if(dotpos!=0)
			identify+=dotpos;
		if(lookahead!=null)
			identify+=","+lookahead;
		if(dotpos==body.length)
			isend=true;
		else
			isend=false;
	}
	public boolean equals(Object o){
		return o instanceof Production &&
			   identify.equals(((Production)o).identify);
	}
	public String toString(){
		return identify;
	}
}
		
		