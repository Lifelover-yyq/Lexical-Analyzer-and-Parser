package Analysis;

import java.util.*;
public interface ForParser{
	public Map<String,Integer> getGotoTable();
	public Map<String,Action> getActionTable()throws ConflictException;
	public Production getProduction(int i);
}