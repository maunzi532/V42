package nonBlock.formwandler;

import java.util.*;

public class LadeFWATeil
{
	ArrayList<FWZustand> zustands;
	ArrayList<ArrayList<String>> usedInputs;
	ArrayList<ArrayList<LadeControlledMove>> availMoves;

	public LadeFWATeil(String code)
	{
		String cde1 = code.replace("\n	", "");
		String[] cde = cde1.split("\n");
		int n = -1;
		for(int i = 0; i < cde.length; i++)
		{
			if(!cde[i].isEmpty() && !cde[i].startsWith("/"))
			{
				if(cde[i].startsWith("Z "))
				{
					zustands.add(FWZustand.valueOf(cde[i].substring(2)));
					usedInputs.add(new ArrayList<>());
					availMoves.add(new ArrayList<>());
					n++;
				}
				else
				{
					String[] cde2 = cde[i].split(" ");
					usedInputs.get(n).add(cde2[0]);
					//TODO availMoves.get(n).add()
				}
			}
		}
	}
}