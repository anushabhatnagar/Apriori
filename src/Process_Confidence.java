import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.Map.Entry;


public class Process_Confidence {
	public List<HashMap<Set<String>,Integer>> conf_list=new ArrayList<HashMap<Set<String>,Integer>>();
	public float conf;
	public HashMap asso_list=new HashMap();
	
	//set confidence value
	
	public void set_conf(float conf)
	{
		conf=conf;
	}
	
	//set list computed in the process function to this class
	public void set(List<HashMap<Set<String>,Integer>> list)
	{
		conf_list=list;
		//System.out.println(conf_list);
	}
	
	//generate association rules and write those values which are more than minimum confidence value provided in the argument in the output.txt file
	
	public void generate_set(int size) throws IOException
	{
		Set<String> key;
		Set rule=new HashSet();
		int den,num;
		float confidence;
		List <Float> MapValues= new ArrayList<Float>();
		for (int i=1;i<conf_list.size();i++)
		{
			//System.out.println(conf_list.get(i));
			for(Entry<Set<String>, Integer> entry:conf_list.get(i).entrySet()) 
			{
			key = (Set<String>)entry.getKey();
			//System.out.println(key);
			for (String str:key)
			{
				Set temp_rule=new HashSet();
				Set<String> temp_set=new HashSet<String>();
				Set<String> temp=new HashSet<String>();
				for (String str1:key)
				{
					if (!str1.equals(str))
						temp_set.add(str1);
						temp.add(str1);
				}
				den=get_support(temp_set);
				temp.add(str);
				num=get_support(temp);
				temp_rule.add(temp_set);
				temp_rule.add(str);
				confidence=(float)num/den;
				rule.add(temp_rule);
				
				if (confidence>=conf)
				{
					//out.write(temp_set+"=>"+str+" (Confidence="+confidence*100+"% ,Support="+(float)num*100/size+"%)");
				//System.out.println(temp_set+"=>"+str+" (Confidence="+confidence*100+"% ,Support="+(float)num*100/size+"%)");
				//out.write("\n");
				MapValues.add(confidence);
				}
			}
			}
		}
		FileWriter fstream = new FileWriter("output.txt",true);
        BufferedWriter out = new BufferedWriter(fstream);
		//System.out.println(rule);
		Collections.sort(MapValues);
		int size1 = MapValues.size();

		for (int i=size1-1; i>0; i--) {
			for (int i1=1;i1<conf_list.size();i1++)
			{
				//System.out.println(conf_list.get(i));
				for(Entry<Set<String>, Integer> entry:conf_list.get(i1).entrySet()) 
				{
				key = (Set<String>)entry.getKey();
				//System.out.println(key);
				for (String str:key)
				{
					Set temp_rule=new HashSet();
					Set<String> temp_set=new HashSet<String>();
					Set<String> temp=new HashSet<String>();
					for (String str1:key)
					{
						if (!str1.equals(str))
							temp_set.add(str1);
							temp.add(str1);
					}
					den=get_support(temp_set);
					temp.add(str);
					num=get_support(temp);
					confidence=(float)num/den;
					if (confidence==MapValues.get(i))
					{
						out.write(temp_set+"=>"+str+" (Confidence="+confidence*100+"% ,Support="+(float)num*100/size+"%)");
					System.out.println(temp_set+"=>"+str+" (Confidence="+confidence*100+"% ,Support="+(float)num*100/size+"%)");
					out.write("\n");
					MapValues.add(confidence);
					}
					
				}
				}
			}
		  
		}
		out.close();
		
	}
	
	//get support for the lhs and rhs value of the association rule to calculate confidence for the rule
	
	public int get_support(Set temp)
	{
		int value=0;
		Set<String> key=new HashSet<String>();
		int size=conf_list.size();
		for (int j=0;j<conf_list.size();j++)
		{
		for (Iterator<Map.Entry<Set<String>,Integer>> i = conf_list.get(j).entrySet().iterator(); i.hasNext(); )  
		{  
		    Map.Entry<Set<String>,Integer> entry = i.next();  
		    key = (Set<String>)entry.getKey();
		    //System.out.println(key);
		    if(key.equals(temp))
		    {
		    	value=(Integer)entry.getValue();
		    	//System.out.println(value);
		    } 
		}  
		}
		 return value;   
	}
}
