package nonBlock.formwandler;

import wahr.zugriff.*;

import java.util.*;

public class LadeControlledMove
{
	ArrayList<FWVerwendet> braucht;
	ArrayList<Integer> brauchtLevel;
	boolean isChainOnly;
	String theMove;
	String theFall;
	int sharedcooldown;
	double cooldown;
	protected int power;

	private LadeControlledMove(String code, String find)
	{
		braucht = new ArrayList<>();
		String cde1 = code.replace("\n	", "");
		String[] cde = cde1.split("\n");
		int i = Arrays.asList(cde).indexOf("F " + find);
		for(i++; i < cde.length; i++)
		{
			if(!cde[i].isEmpty() && !cde[i].startsWith("/"))
			{
				if(cde[i].startsWith("F "))
					return;
				if(cde[i].startsWith("V "))
				{
					String[] cde2 = cde[i].split(" ");
					braucht.add(FWVerwendet.valueOf(cde2[1]));
					brauchtLevel.add(Integer.parseInt(cde2[2]));
				}
				else if(cde[i].equalsIgnoreCase("ChainOnly"))
					isChainOnly = true;
				else if(cde[i].startsWith("cld "))
					cooldown = Integer.parseInt(cde[i].substring(4));
				else if(cde[i].startsWith("shcld "))
					sharedcooldown = Integer.parseInt(cde[i].substring(6));
				else if(cde[i].startsWith("power "))
					power = Integer.parseInt(cde[i].substring(6));
				else if(cde[i].startsWith("move "))
					theMove = cde[i].substring(5);
				else if(cde[i].startsWith("fall "))
					theFall = cde[i].substring(5);
			}
		}
	}

	public static LadeControlledMove gibVonIndex(String name)
	{
		int xe = name.indexOf('-');
		if(xe == -1)
			throw new RuntimeException("LadeControlledMove name muss - enthalten");
		if(Index.geladen.containsKey(name))
			return (LadeControlledMove) Index.geladen.get(name);
		LadeControlledMove s = new LadeControlledMove(Index.bauName("FWA",
				name.substring(0, xe)), name.substring(xe + 1));
		Index.geladen.put(name, s);
		return s;
	}
}