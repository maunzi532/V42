package nonBlock.formwandler;

import java.util.*;

public class LadeControlledMove
{
	ArrayList<FWVerwendet> braucht;
	ArrayList<Integer> brauchtLevel;
	boolean isChainOnly;
	String theMove;
	int cooldown;
	int power;

	public LadeControlledMove(String code)
	{
		braucht = new ArrayList<>();
		String cde1 = code.replace("\n	", "|");
		String[] cde = cde1.split("\n");
		for(int i = 0; i < cde.length; i++)
		{
			if(!cde[i].isEmpty() && !cde[i].startsWith("/"))
			{
				if(cde[i].startsWith("V "))
				{
					String[] cde2 = cde[i].split(" ");
					braucht.add(FWVerwendet.valueOf(cde2[0]));
					brauchtLevel.add(Integer.parseInt(cde2[1]));
				}
				else if(cde[i].equalsIgnoreCase("ChainOnly"))
					isChainOnly = true;
				else if(cde[i].startsWith("cld "))
					cooldown = Integer.parseInt(cde[i].substring(4));
				else if(cde[i].startsWith("power "))
					power = Integer.parseInt(cde[i].substring(6));
				else if(cde[i].startsWith("move "))
					theMove = cde[i].substring(5);
			}
		}
	}
}