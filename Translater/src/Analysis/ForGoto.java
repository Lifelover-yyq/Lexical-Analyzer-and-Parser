package Analysis;


public class ForGoto implements Factory{
	public ForParser getParserGenerator(Production[] productions,Symbol[] sym){
		return new GetGotoTable(productions,sym);
	}
}
