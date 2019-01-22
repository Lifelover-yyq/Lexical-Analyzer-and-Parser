package Analysis;

public class ForAction implements Factory{
	public ForParser getParserGenerator(Production[] productions,Symbol[] sym){
		return new GetActionTable(productions,sym);
	}
}