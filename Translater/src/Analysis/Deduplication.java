package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
/*
 * TXT文件去重复行
 */
public class Deduplication {
	public static void run() throws Exception {
		// 需要处理数据的文件位置
		File txt = new File("SymbolTable.txt");
        FileReader fileReader = new FileReader(txt);
        BufferedReader bufferedReader = new BufferedReader(fileReader);  
        Map<String, String> map = new HashMap<String, String>();
        String readLine = null;
        int i = 0;

        while ((readLine = bufferedReader.readLine()) != null) {
            // 每次读取一行数据，与 map 进行比较，如果该行数据 map 中没有，就保存到 map 集合中
            if (!map.containsValue(readLine)) {
                map.put("key" + i, readLine);
                i++;
            }
        }
        bufferedReader.close();
        txt.delete();
        FileWriter write = new FileWriter(new File("SymbolTable.txt"),false);
 		BufferedWriter writer = new BufferedWriter(write);   
        for (int j = 0; j < map.size(); j++) {
        	String ss = map.get("key" + j) + "";
        	writer.write(ss);
        	writer.newLine();
    		writer.flush();		
        }
        writer.close();
	}
}
