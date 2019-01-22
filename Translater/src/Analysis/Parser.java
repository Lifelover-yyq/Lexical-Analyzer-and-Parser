package Analysis;


import java.util.*;
import java.io.*;

public class Parser {
	private ForParser parser;
	private Stack stack;
	public Parser(Production[] productions,Symbol[] sym,Factory factory){
		parser=factory.getParserGenerator(productions,sym);
		stack=new Stack();
		stack.push(0);
	}
	public void parse()throws IOException,ConflictException{
		FileInputStream filein = new FileInputStream("forSLR.txt");
		InputStreamReader fileinp = new InputStreamReader(filein);
		BufferedReader reader = new BufferedReader(fileinp);	//读所需文件
		File fileout = new File("SLRresult.txt");
		FileWriter write = new FileWriter(fileout,true);
		BufferedWriter writer = new BufferedWriter(write);	//分析结果写入
		String a;
		Map<String,Integer> GOTO = parser.getGotoTable();
		Map<String,Action> ACTION = parser.getActionTable();
		a=reader.readLine();
		Integer s;
		while(true){
			s=(Integer) stack.peek();
			Action act=ACTION.get(s+a);
			if(act==null){
				System.out.println(s+a);
				System.out.println("parse error at "+a);
				break;
			}
			if(act.isshift){
				stack.push(act.state);
				a=reader.readLine();
			}else if(act.state==-1){
				writer.write("accept");
				writer.newLine();
				writer.flush();
				System.out.println("accept");
				break;
			}else{
				Production p=parser.getProduction(act.state);
				for(int i=0;i<p.body.length;i++)
					stack.pop();
				s=(Integer) stack.peek();
				Integer it=GOTO.get(s+p.head.toString());
				if(it==null){
					System.out.println("parse error at "+a);
					break;
				}
				stack.push(it);
				writer.write(p+"");
				writer.newLine();
				writer.flush();
				System.out.println(p);
			}
		}
		reader.close();
		writer.close();
		System.out.println("语法分析结果已保存为：SLRresult.txt");
	}
	
}