package Analysis;

public class ForGroup implements Factory{
	public ForParser getParserGenerator(Production[] productions,Symbol[] sym){
		return new ProjectGroup(productions,sym);
	}
}