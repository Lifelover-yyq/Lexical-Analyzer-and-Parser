package Analysis;

	public class Symbol implements Comparable<Symbol> {
	        /*
		     * isterminal字段
		     * 表示该符号是否为终结符
		     */
		public final boolean isterminal;
		    /*
		     * sym字段
		     * 表示该符号
		     */
		public final String sym;
		public Symbol(String s){
			this(s,false);
		}
		public Symbol(String s,boolean ist){
			this.sym=s;
			this.isterminal=ist;
		}
		public Symbol(Symbol s){
			this(s.sym,s.isterminal);
		}
		public boolean equals(Object o){
			return o instanceof Symbol &&
				   sym.equals(((Symbol)o).sym);
		}
		public String toString(){
			return sym;
		}
		public int compareTo(Symbol s){
			return this.sym.compareTo(s.sym);
		}
		public int hashCode(){
			return sym.hashCode();
		}
		public static Symbol[] 
		subArray(Symbol[] array,int fromIndex, int toIndex){
			if(fromIndex>toIndex)
				return new Symbol[0];
			Symbol[] t=new Symbol[toIndex-fromIndex+1];
			int index=0;
			for(int i=fromIndex;i<=toIndex;i++)
				t[index++]=array[i];
			return t;
		}
	}
