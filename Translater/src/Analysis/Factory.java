package Analysis;

public interface Factory{
	ForParser getParserGenerator(Production[] productions,Symbol[] sym);
}

