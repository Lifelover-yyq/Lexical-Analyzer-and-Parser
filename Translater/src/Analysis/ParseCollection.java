package Analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseCollection{
	/*�﷨�Ƶ������*/
	protected final Production[] productions;
	/*���ű�*/
	protected final Symbol[] symbols;
	/*ÿ�����ս��ӳ�������Ƶ�����*/
	protected Map<Symbol,List<Production>> table;
	/*���캯��*/
	public ParseCollection(Production[] productions,
					  Symbol[] sym){
		this.productions=productions;
		this.symbols=sym;
		table=new HashMap<Symbol,List<Production>>();
		for(Production p :productions){
		    List<Production> ls=table.get(p.head);
			if(ls==null){
				ls=new ArrayList<Production>(Arrays.asList(p));
			}else{
				ls.add(p);
			}
			table.put(p.head,ls);
		}
	}
	
						
}
		
		