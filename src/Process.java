import java.io.*;
import java.util.*;
import java.util.Map.Entry;


public class Process {	
public ArrayList<record> entries=new ArrayList<record>();
public int sizeOfentries, iteration=0;
public HashMap<Set<String>, Integer> dup_map = new HashMap<Set<String>,Integer>();
public List<HashMap<Set<String>,Integer>> freq_items=new ArrayList<HashMap<Set<String>,Integer>>();
public float support;
public HashMap<Set<String>,Integer> large_set=new HashMap<Set<String>,Integer>();


	public void set_support(float supp)
	{
		support=supp;	//set support value given in argument
	}
	
	//Reading csv file and generating items for each row present in the file

	public int get_file_input(String filename) throws Exception
	{
		File file=new File(filename);
		FileReader fr;
		sizeOfentries=0;
		String[] itemset={};
		
		try {
			fr = new FileReader(file);
			String read;
			BufferedReader br=new BufferedReader(fr);
			
			while((read=br.readLine())!=null){
				itemset  = read.split(",");
				record r=new record();
				List<String> items=new ArrayList<String>();
				items= Arrays.asList(itemset);
				//System.out.println(items);
				for (String str: items)
				{
					Set<String> set = new HashSet<String>();
					set.add(str);
					r.add_set(set);
				}
				//r.display_itemrow();
				entries.add(r);
				sizeOfentries++;
			}
			/*for (Set s:trans_items){					
				System.out.println(str);
			}
			}*/
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(sizeOfentries);
			return sizeOfentries;
	}
	
	
	//Calculate support of the present items computed while reading file
	
	public int calculate_support()
	{
		//for (record item:entries)
		int end=0;
		HashMap<Set<String>, Integer> support_map = new HashMap<Set<String>,Integer>();
		for (int i=0;i<entries.size();i++)
		{
			Set<Set<String>> row=entries.get(i).get_set();
			for (Set<String> r:row)
			{
					if( support_map.containsKey(r) ){
								support_map.put(r, support_map.get(r) + 1);
							}
							else
							{
								support_map.put(r,1);
							} 
						}			
		}
		//System.out.println(support_map);
		freq_items.add(support_map);
		//System.out.println(freq_items);
		iteration++;
		end=prune_support();
		//System.out.println(freq_items);
		return end;
	}
	
	//display support values associated with every itemset 
	
	public void display_support() throws IOException
	{
		Set<String> key;
		int value;
		//System.out.println(freq_items);
		for (int i=0;i<freq_items.size();i++)
		{
			//System.out.println(freq_items.get(i));
		for(Entry<Set<String>, Integer> entry:freq_items.get(i).entrySet()) {
			key = (Set<String>)entry.getKey();
			value=(Integer)entry.getValue();
			large_set.put(key, value);
			//System.out.println(key);
			//System.out.println(value);
		}
		}
		sort_map();
	
	}
	
	//sorting values in accordance with support to write in the output.txt file in descending value
	
	public void sort_map() throws IOException
	{
		HashMap sortmap = new HashMap();
		List MapKeys = new ArrayList(large_set.keySet());
		List MapValues = new ArrayList(large_set.values());
		TreeSet sortedSet = new TreeSet(MapValues);
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;
		Writer output = null;
		File file = new File("output.txt");
	    output = new BufferedWriter(new FileWriter(file));

		for (int i=size-1; i>0; i--) {
		   sortmap.put(MapKeys.get(MapValues.indexOf(sortedArray[i])),sortedArray[i]);
		   int c=(Integer)sortedArray[i]*100;
		   float c1=1f*c/sizeOfentries;
		   //System.out.println(c+" "+sizeOfentries);
		   System.out.println(MapKeys.get(MapValues.indexOf(sortedArray[i])) +" , "+ (1f*c/sizeOfentries)+"%");
		   output.write(MapKeys.get(MapValues.indexOf(sortedArray[i])) +" , "+ (1f*c/sizeOfentries)+"%");
		   output.write("\n");
		}
		  output.close();
		
	}
	
	//check whether to generate L1, L2 etc i.e. new itemsets possible
	
	public int check()
	{
		if (freq_items.get(iteration-1).size()==1)
			return 1;
		if (freq_items.get(iteration-1).size()==0)
		{
			freq_items.remove(iteration-1);
			return 1;
		}
		else
			return 0;	
	}
		

	//remove those values from set which have support less than minimum support value argument provided
	
	public int prune_support()
	{
		int value;
		float item_support;
		for (Iterator<Map.Entry<Set<String>,Integer>> i = freq_items.get(iteration-1).entrySet().iterator(); i.hasNext(); )  
		{  
		    Map.Entry<Set<String>,Integer> entry = i.next();  
		    value=(Integer)entry.getValue();
		    //support=value;
			item_support=(float)value/sizeOfentries;
			if (item_support<=support)  
		    {  
		        Set<String> key =(Set<String>) entry.getKey();  
		        i.remove();  
		    }  
		} 
		int end=0;
		end=check();
		return end;
	}
	
	//generate itemset with the preceding items by combining them
	
	public int generate_set()
	{
		Set<String> key,key1=new HashSet<String>();
		int add=1;
		Set<Set<String>> newrow=new HashSet<Set<String>>();
		for (Iterator<Map.Entry<Set<String>,Integer>> i = freq_items.get(iteration-1).entrySet().iterator(); i.hasNext(); )  
		{ 
		    Map.Entry<Set<String>,Integer> entry = i.next();  
			key = (Set<String>)entry.getKey();
			for (Iterator<Map.Entry<Set<String>,Integer>> i1 = freq_items.get(iteration-1).entrySet().iterator(); i1.hasNext(); )  
			{   
			    Map.Entry<Set<String>,Integer> entry1 = i1.next();  
				key1 = (Set<String>)entry1.getKey();
				if (key.equals(key1))
				continue;
				else
				{
					Set<String> newitem=new HashSet<String>();
						for (String str:key)
						{ 
						newitem.add(str);
						}
						for (String str:key1)
						{
						newitem.add(str);
						}
					//System.out.println(newitem);
					//System.out.println(newitem.size()+"v"+iteration);
					if (newitem.size()==iteration+1)
						newrow.add(newitem);	
				}
			}
		}
		int end=0;
		//System.out.println(newrow);
		if (newrow!=null)
			end=calculate_support_itemset(newrow);
		else
			end=1;
		return end;
	}	
			
	//calculate support of itemset everytime a new itemsets table is generated
	
	public int calculate_support_itemset(Set<Set<String>> newitem)
	{
		int add=-1;
		HashMap<Set<String>, Integer> support_map = new HashMap<Set<String>,Integer>();
		for (int i=0;i<entries.size();i++)
		{
			Set<Set<String>> row=entries.get(i).get_set();

				for (Set<String> r:newitem)
					{
						add=-1;
						for (String str:r)
							{
								Set <String> check=new HashSet<String>();
								check.add(str);
								if (row.contains(check) && add!=0)
									add=1;
								else
									add=0;
							}

						if (add==1)
						{
							if( support_map.containsKey(r))
								support_map.put(r, support_map.get(r) + 1);
							else
								support_map.put(r,1);
						}	
					}
		}
		freq_items.add(support_map);
		iteration++;
		int end=0;
		end=prune_support();
		return end;
	}
	
	//get the whole map for every itemset table generated
	
	public List<HashMap<Set<String>,Integer>> get_list()
	{
		return freq_items;
	}
	
}
