package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LexicalScanner {

    static String[] gjz = new String[]{"auto","break","case","char","const","continue","default",
    									"do","double","else","enum","extern","float","for",
    									"goto","if","int","long","register","return","short",
    									"signed","sizeof","static","struct","switch","typedef",
    									"union","unsigned","void","volatile","while"};//C语言32个关键字
    
    static String inputstr = "";   //存储源程序字符串
    static StringBuilder word = new StringBuilder("");     //存储单词自身组成的字符串

    static char ch;		//当前读取字符
    static int pointer;	//指针
    static int sum = 0;	//常数处理时辅助变量
    static String syn;		//种别码
    static String sign;		//单词类别
     //扫描器
    static void Scanner() {
    	if(pointer == inputstr.length())	//所有输入程序扫描完毕
    		syn = "over";	//置syn为over作为标记
    	else {   		
        word.delete(0, word.length());	//置空token对象，清除
        sign = "null";
        ch = inputstr.charAt(pointer++);
        while(ch == ' ') {
            ch = inputstr.charAt(pointer++);      //消除空格
        }

        if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){         //可能是关键字或者标识符
            while((ch >= '0'&& ch <= '9') || (ch >= 'a'&&ch <= 'z') || (ch >= 'A'&& ch <= 'Z')){
                word.append(ch);
                ch = inputstr.charAt(pointer++);
            }
            pointer--;      //此次识别的最后一个字符未识别入，需要将指针回退
            syn = "ID";       //默认为识别出的字符串为自定义的标识符ID
            sign = "标识符";
            String s = word.toString();
            for(int i=0; i<gjz.length; i++){
                if(s.equals(gjz[i])){    
                    syn = s.toUpperCase();	//关键字大写即为种别码
                    sign = "关键字";
                    break;        //识别出是关键字
                }
            }
        }
        
        else if((ch>='0'&&ch<='9')){	//处理常数
            sum = 0;
            while((ch>='0'&&ch<='9')){
                sum = sum*10 + ch-'0';
                ch = inputstr.charAt(pointer++);
            }
            pointer--;
            syn = "INT10";
            sign = "整数";
        }
        else 	//处理符号
        	switch(ch){

        case '<':
            word.append(ch);
            ch = inputstr.charAt(pointer++);
            if(ch == '='){
                word.append(ch);
                syn = "LTE";
            }
            else if(ch == '>'){
                word.append(ch);
                syn = "NEQ";
            }
            else{
                syn = "LT";
                pointer--;
            }
            sign = "运算符";
            break;
        case '>':
            word.append(ch);
            ch = inputstr.charAt(pointer++);
            if(ch == '='){
                word.append(ch);
                syn = "GTE";
            }
            else{
                syn = "GT";
                pointer--;
            }
            sign = "运算符";
            break;
        case '*':
            word.append(ch);
            ch = inputstr.charAt(pointer++);
            if(ch == '*'){
                word.append(ch);
                syn = "POW";
            }
            else{
                syn = "MUL";
                pointer--;
            }
            sign = "运算符";
            break;
        case '=':
            word.append(ch);
            ch = inputstr.charAt(pointer++);
            if(ch == '='){
                syn = "COMP";
                word.append(ch);
            }
            else{
                syn = "EQ";
                pointer--;
            }
            sign = "运算符";
            break;
        case '!':
            word.append(ch);
            ch = inputstr.charAt(pointer++);
            if(ch == '='){
                syn = "NEQ";
                sign = "运算符";
                word.append(ch);
            }
            else{
                syn = "-1";
                pointer--;
            }
            break;
        case '/':
            word.append(ch);
            ch = inputstr.charAt(pointer++);
            if(ch == '/'){
                while(ch != ' '){
                    ch = inputstr.charAt(pointer++);  //忽略掉注释，以空格为界定
                }
                syn = "-2";
                break;
            }
            else{
                syn = "DIV";
                pointer--;
            }
            sign = "运算符";
            break;
        case '+':
            word.append(ch);
            ch = inputstr.charAt(pointer++);
            if(ch == '+'){
                syn = "INC";
                word.append(ch);
            }
            else{
                syn = "ADD";
                pointer--;
            }
            sign = "运算符";
            break;
        case '-':
            word.append(ch);
            ch = inputstr.charAt(pointer++);
            if(ch == '-'){
                syn = "DEC";
                word.append(ch);
            }
            else{
                syn = "SUB";
                pointer--;
            }
            sign = "运算符";
            break;
        case ';':
            syn = "SEMI";
            sign = "分隔符";
            word.append(ch);
            break;
        case ',':
            syn = "COMM";
            sign = "分隔符";
            word.append(ch);
            break;
        case '(':
            syn = "SLP";
            sign = "分隔符";
            word.append(ch);
            break;
        case ')':
            syn = "SRP";
            sign = "分隔符";
            word.append(ch);
            break;
        case '{':
            syn = "LP";
            sign = "分隔符";
            word.append(ch);
            break;
        case '}':
            syn = "RP";
            sign = "分隔符";
            word.append(ch);
            break;
        case '\n':
            syn = "\n";
            word.append(ch);
            break;
        default:
            syn = "error";break;
        }
    }
  }
    static public void result() {
    	//读取源程序文本文件
        System.out.println("请输入源程序文件所在完整路径名:\n(测试用例:code.txt)");
        @SuppressWarnings("resource")
		Scanner txtname = new Scanner(System.in);
        String s;
        try {
        	FileInputStream file = new FileInputStream(txtname.nextLine());
        	InputStreamReader file1 = new InputStreamReader(file);
        	@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(file1);
        	while((s = reader.readLine()) != null) {
        		inputstr = inputstr + s;
        	}
        }catch(IOException e) {
        	e.printStackTrace();
        }
        

        pointer = 0; 
        //保存文件与输出结果
        File fileout0 = new File("SymbolTable.txt");
    	File fileout1 = new File("token.txt");
    	File fileout2 = new File("forSLR.txt");
    	System.out.println("SymbolTable.txt 已生成\ntoken.txt 已生成");
        System.out.println("分析结果如下:\n单词\t种别码\t类别");
         	while(true) {
         		Scanner();
         		if(syn == "over")	//分析结束
         			break;
         		try {
         			FileWriter write0 = new FileWriter(fileout0,true);//按行依次写入文件SymbolTable.txt
         			BufferedWriter writer0 = new BufferedWriter(write0); 
         			FileWriter write1 = new FileWriter(fileout1,true);//按行依次写入文件token.txt
         			BufferedWriter writer1 = new BufferedWriter(write1);    		
         			FileWriter write2= new FileWriter(fileout2,true);//按行依次写入文件forSLR.txt
         			BufferedWriter writer2 = new BufferedWriter(write2);    		
            	switch(syn){
            case "INT10":
                System.out.println(sum+"\t"+syn+"\t"+sign);
                String s1 = syn + "," + sum;     
                writer0.write("NUM,"+syn+","+sum);
				writer0.newLine();
				writer0.flush(); 
				writer0.close();
                writer1.write(s1);
				writer1.newLine();
				writer1.flush(); 
				writer1.close();
				writer2.write(syn.toLowerCase());
				writer2.newLine();
				writer2.flush(); 
				writer2.close();					
                break;
            case "ID":
            	System.out.println(word+"\t"+syn+"\t"+sign);
                String s2 = syn + "," + word;
                writer0.write(word+","+syn+","+word);
				writer0.newLine();
				writer0.flush(); 
				writer0.close();
                writer1.write(s2);
				writer1.newLine();
				writer1.flush(); 
				writer1.close();
				writer2.write(syn.toLowerCase());
				writer2.newLine();
				writer2.flush(); 
				writer2.close();
                break;
            case "error":
                System.out.println("Analysis error!");
                break;
            case "\n":break;	//回车则跳过
            default:
                System.out.println(word+"\t"+syn+"\t"+sign);
                String s3 = syn + "," + "_";
                writer1.write(s3);
				writer1.newLine();
				writer1.flush(); 
				writer1.close();
				writer2.write(word+"");
				writer2.newLine();
				writer2.flush(); 
				writer2.close();
                break;
            	}
         	}catch(IOException e) {
         		e.printStackTrace();
         	}
           } 
         	try {
				Deduplication.run();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
         	/*最后写入一个字符‘$’*/
         	try{
         		FileWriter write2= new FileWriter(fileout2,true);//按行依次写入文件forSLR.txt
         		BufferedWriter writer2 = new BufferedWriter(write2);   
 			writer2.write('$');
 			write2.flush();
 			writer2.close();
         	}catch(IOException e) {
         		e.printStackTrace();
         	}
 			
    }
    public static void main(String[] args) throws IOException, ConflictException{
		result();	//词法分析，得到forSLR.txt文件供语法分析调用
    	Symbol _P = new Symbol("P'");
    	Symbol P = new Symbol("P");   			
    	Symbol D = new Symbol("D");  			
    	Symbol S = new Symbol("S");
    	Symbol L = new Symbol("L");
    	Symbol E = new Symbol("E");
    	Symbol C = new Symbol("C");
    	Symbol T = new Symbol("T");
    	Symbol F = new Symbol("F");

    	Symbol ID = new Symbol("id",true);
    	Symbol SEMI = new Symbol(";",true);
    	Symbol INT = new Symbol("int",true);
   	    Symbol FLOAT = new Symbol("float",true);
    	Symbol EQ = new Symbol("=",true);
    	Symbol SLP = new Symbol("(",true);
    	Symbol SRP = new Symbol(")",true);
    	Symbol IF = new Symbol("if",true);
    	Symbol ELSE = new Symbol("else",true);
    	Symbol WHILE = new Symbol("while",true);
    	Symbol GT = new Symbol(">",true);
    	Symbol LT = new Symbol("<",true);
    	Symbol COMP = new Symbol("==",true);
    	Symbol ADD = new Symbol("+",true);
    	Symbol SUB = new Symbol("-",true);
    	Symbol MUL = new Symbol("*",true);
    	Symbol DIV = new Symbol("/",true);
    	Symbol INT10 = new Symbol("int10",true);
    	Production[] productions= new Production[]{new Production(_P,new Symbol[]{P}),
    			                                           
    	                                           new Production(P ,new Symbol[]{D,S}),
    	                                           
    	                                           new Production(P ,new Symbol[]{S}),
      
    	                                           new Production(D ,new Symbol[]{L,ID,SEMI,D}),
    											    
    	                                           new Production(D ,new Symbol[]{L,ID,SEMI}),
    											           
    	                                           new Production(L ,new Symbol[]{INT}),
    											         
    	                                           new Production(L ,new Symbol[]{FLOAT}),
    	                                           
    	                                           new Production(S ,new Symbol[]{ID,EQ,E,SEMI}),
    											    
    	                                           new Production(S ,new Symbol[]{IF,SLP,C,SRP,S}),

    	                                           new Production(S ,new Symbol[]{ELSE,S}),
    											    
    	                                           new Production(S ,new Symbol[]{WHILE,SLP,C,SRP,S}),

    	                                           new Production(S ,new Symbol[]{S,S}),

    	                                           new Production(C ,new Symbol[]{E,GT,E}),
    	                                           
    	                                           new Production(C ,new Symbol[]{E,LT,E}),
    	                                           
    	                                           new Production(C ,new Symbol[]{E,COMP,E}),
    	                                           
    	                                           new Production(E ,new Symbol[]{E,ADD,T}),
    	                                           
    	                                           new Production(E ,new Symbol[]{E,SUB,T}),
    	                                           
    	                                           new Production(E ,new Symbol[]{T}),
    	                                           
    	                                           new Production(T ,new Symbol[]{F}),
    	                                           
    	                                           new Production(T ,new Symbol[]{T,MUL,F}),

    	                                           new Production(T ,new Symbol[]{T,DIV,F}),

    	                                           new Production(F ,new Symbol[]{SLP,E,SRP}),
    	                                           
    	                                           new Production(F ,new Symbol[]{ID}),
    	                                           
    	                                           new Production(F ,new Symbol[]{INT10})

    	                                          };
    	Symbol[] symbols=new Symbol[]{_P,P,D,S,L,E,C,T,F,ID,SEMI,INT,FLOAT,EQ,SLP,SRP,IF,ELSE,WHILE,GT,LT,COMP,ADD,SUB,MUL,DIV,INT10};
    	//得到Action Goto表
    	Parser parser=new Parser(productions,symbols,new ForAction());
    		
    	//进行语法分析	
    	parser.parse();
    	/**
    	 * ELSE 报错
    	 */
	}
}
