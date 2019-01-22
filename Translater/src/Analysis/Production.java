package Analysis;

public class Production{
		/*
	     * head�ֶ�
	     * ��ʾ�﷨�Ƶ�������ײ�
	     * ����E->T+F�е�E
	     */
 public final Symbol head;
		/*
	     * body�ֶ�
	     * ��ʾ�﷨�Ƶ�����Ĺ�����
	     * ����E->T+F�е�T+F
	     */
 public final Symbol[] body;
	   /*
	    *dotpos�ֶ�
	    *��ʾ����SLR,LR,LALR��Ŀ������ʱ�Ƶ������еĵ��λ��
	    * ����E->��T+F,dotpos=0
	    */
 public final int dotpos;
	   /*
	    *isend�ֶ�
	    *��ʾ���Ƿ��ڹ����ĩβ
	    */
	public final boolean isend;
	   /*
	    *lookahead�ֶ�
	    *��ʾ����LR,LALR��Ŀ������ʱ�Ƶ������е���ǰ������
	    *����:E->T+F,$�е�$
	    */
	public final Symbol lookahead;
	   /*
	    *identify�ֶ�
	    *��ʾÿ���Ƶ������id
	    */
 private String identify;
	   /*
	    *head��ʾ�ķ����ײ�
	    *body��ʾ�ķ��Ĺ�����
	    */
	public Production(Symbol head,Symbol[] body){
		this(head,body,0);
	}
	   /*
	    *pos��ʾ��ʾ����LR��Ŀ������ʱ�Ƶ������еĵ��λ��
	    */
 public Production(Symbol head,Symbol[] body,int pos){
		this(head,body,pos,null);
	}
	   /*
	    *t��ʾ��ʾ����LR��Ŀ������ʱ�Ƶ������е���ǰ������
	    */
	public Production(Production p,Symbol t){
		this(p.head,p.body,p.dotpos,t);
	}
	   /*
	    *head��ʾ�ķ����ײ�
	    *body��ʾ�ķ��Ĺ�����
	    *pos��ʾ��ʾ����LR��Ŀ������ʱ�Ƶ������еĵ��λ��
	    *t��ʾ��ʾ����LR��Ŀ������ʱ�Ƶ������е���ǰ������
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
		
		