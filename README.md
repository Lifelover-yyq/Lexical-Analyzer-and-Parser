# Lexical-Analyzer-and-Parser
**编译原理实验内容，包含一个词法分析器、一个语法分析器，并把运行结果保存到本地文件。**
## 实验内容
1. 设计一个简单的类C语言的词法扫描器
2. 使用下文中的文法，可以对类似下面的程序语句进行语法分析：
```C
int a;
int b;
int c;
a = 2;
b = 1;
if （a>b）
c = a+b;
else
c = a-b;
```
### 简单类C语言文法
产生式：（注：P为文法的开始符号）
* 说明语句部分文法：
```
        P’-> P
        P → D S
        P → S
        D →L id ; D
        D →L id ; 
        L → int
        L → float
```
* 程序语句部分文法：
```
        S → id = E;	     
        S → if （C）  S1   
        S → if （C）  S1   else   S2
        S →  else   S2
        S → while （C）  S1  
        S → S  S
        C → E1 > E2
        C → E1 < E2                  
        C → E1 = = E2                 
        E → E1 + T           
        E → E1 – T 	
        E → T 				
        T → F 				
        T → T1 * F 			
        T → T1 / F 			
        F → ( E )			  
	F → id 
        F → int10
```	
说明：删去了产生式 D → ε ，对原文档所给产生式进行了微调使得能够运行。
## 使用手册
导入`java`项目`Translater`至`eclipse`等IDE，运行程序`BeginMain.java`。
### 文件解释
1. `code.txt`:待分析的C语言代码段
2. `单词种别码.txt`:人为规定的单词对应的种别码
3. `SymbolTable.txt`:词法分析过程中产生的中间文件，运行时有提示
4. `token.txt`:同3
5. `forSLR.txt`:将`code.txt`中的代码按**每行一单词**的形式保存，便于语法分析时读取
6. `SLRresult.txt`:语法分析输出结果
