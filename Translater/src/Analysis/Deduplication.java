package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
/*
 * TXT�ļ�ȥ�ظ���
 */
public class Deduplication {
	public static void run() throws Exception {
		// ��Ҫ�������ݵ��ļ�λ��
		File txt = new File("SymbolTable.txt");
        FileReader fileReader = new FileReader(txt);
        BufferedReader bufferedReader = new BufferedReader(fileReader);  
        Map<String, String> map = new HashMap<String, String>();
        String readLine = null;
        int i = 0;

        while ((readLine = bufferedReader.readLine()) != null) {
            // ÿ�ζ�ȡһ�����ݣ��� map ���бȽϣ������������ map ��û�У��ͱ��浽 map ������
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
