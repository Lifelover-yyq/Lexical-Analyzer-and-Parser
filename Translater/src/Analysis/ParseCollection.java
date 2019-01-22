package Analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseCollection{
	/*语法推导规则表*/
	protected final Production[] productions;
	/*符号表*/
	protected final Symbol[] symbols;
	/*每个非终结符映射它的推导规则*/
	protected Map<Symbol,List<Production>> table;
	/*构造函数*/
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
		
		