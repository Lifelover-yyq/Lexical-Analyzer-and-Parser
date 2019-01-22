package Analysis;

import java.util.ArrayList;  
import java.util.List;  
  
public class Stack {  
    List<Integer> list;  
      
    public Stack()  
    {  
        list = new ArrayList<Integer>();  
    }  
      
    public void push(Integer obj)  
    {  
        list.add(obj);  
    }  
      
    public Integer pop()  
    {  
        if(!list.isEmpty())  
        {  
            return list.remove(list.size()-1);  
        }  
        return null;  
    }  
      
    public Integer peek()  
    {  
        if(!list.isEmpty())  
        {  
            return list.get(list.size()-1);  
        }  
        return null;  
    } 
    public boolean IsEmpty(){
    	if(list.isEmpty())
    		return true;
    	else return false;
    }
}  