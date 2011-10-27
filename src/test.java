import java.util.*;
import java.util.Map.Entry;


public class test {
	
	public static void main(String[] args){
		List<HashMap<Set<String>,Integer>> freq_items=new ArrayList<HashMap<Set<String>,Integer>>();
		HashMap<Set<String>, Integer> support_map = new HashMap<Set<String>,Integer>();
		Set set = new HashSet();

		// Add elements to the set
		set.add("a");
		set.add("b");
		set.add("c");
		support_map.put(set, 1);
		freq_items.add(support_map);
		Set set1 = new HashSet();

		// Add elements to the set
		set1.add("c");
		set1.add("e");
		set1.add("b");
		HashMap<Set<String>, Integer> support_map1 = new HashMap<Set<String>,Integer>();
		support_map1.put(set1, 5);
		freq_items.add(support_map1);
		freq_items.remove(support_map1);
		System.out.println(support_map1.size());
		System.out.println(freq_items.size());
		if( support_map.containsKey("a"))
		{
			System.out.println("found");
		}
		
		
		
	}
}
