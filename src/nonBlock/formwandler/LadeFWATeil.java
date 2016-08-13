package nonBlock.formwandler;

import indexLader.*;
import java.util.*;

public class LadeFWATeil
{
	ArrayList<String> zustands;
	ArrayList<ArrayList<String>> usedInputs;
	ArrayList<ArrayList<LadeControlledMove>> availMoves;

	private LadeFWATeil(String code)
	{
		zustands = new ArrayList<>();
		usedInputs = new ArrayList<>();
		availMoves = new ArrayList<>();
		String cde1 = code.replace("\n	", "");
		String[] cde = cde1.split("\n");
		int n = -1;
		for(int i = 0; i < cde.length; i++)
		{
			if(!cde[i].isEmpty() && !cde[i].startsWith("/"))
			{
				if(cde[i].startsWith("Z "))
				{
					zustands.add(cde[i].substring(2));
					usedInputs.add(new ArrayList<>());
					availMoves.add(new ArrayList<>());
					n++;
				}
				else
				{
					String[] cde2 = cde[i].split(" ");
					usedInputs.get(n).add(cde2[0]);
					availMoves.get(n).add(LadeControlledMove.gibVonIndex(cde2[1]));
				}
			}
		}
	}

	public static LadeFWATeil gibVonIndex(String name)
	{
		if(Index.geladen.containsKey(name))
			return (LadeFWATeil) Index.geladen.get(name);
		LadeFWATeil s = new LadeFWATeil(Index.bauName("FWA", name));
		Index.geladen.put(name, s);
		return s;
	}
}