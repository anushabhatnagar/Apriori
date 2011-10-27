import java.util.HashSet;
import java.util.Set;


public class record {
	public Set<Set<String>> items_row=new HashSet<Set<String>>();
	
	public void add_set(Set<String> item)
	{
		items_row.add(item);
	}
	
	public int sizeOfset()
	{
		int i=0;
		for (Set<String> s:items_row){					
			System.out.println(s);
			i++;
		}
		return i;
	}
	
	public Set<Set<String>> get_set()
	{
		return items_row;
	}
	
	public boolean contain_set(Set<String> item)
	{
		if (items_row.contains(item))
		return true;
		else
			return false;
	}
	public void display_itemrow()
	{
		for (Set<String> s:items_row){	
			for (String str:s)
			{
			System.out.println(str);
			}
		}
	}

}
