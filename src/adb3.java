import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class adb3 {
 
	// Main Class
	public static void main(String[] args)throws Exception{
		String filename=args[0];
		String supp=args[1];
		String conf=args[2];
		float support= Float.valueOf(supp).floatValue();
		float confidence= Float.valueOf(conf).floatValue();
		int end=0,size=0;
		Process p =new Process();
		p.set_support(support);
		size=p.get_file_input(filename);
		end=p.calculate_support();
		while(end==0)
		{
		end=p.generate_set();
		}
		p.display_support();
		Process_Confidence pc =new Process_Confidence();
		pc.set_conf(confidence);
		pc.set(p.get_list());
		pc.generate_set(size);
	}
		



}
