package nonBlock.aktion.lesen;

import indexLader.*;
import java.util.*;

public class LadeMove
{
	final ArrayList<LadeAktion> aktionen;
	int teilE;
	final ArrayList<Integer> zeitE;
	final String name;
	public int resist = 0;

	private LadeMove(String name, String code)
	{
		this.name = name;
		zeitE = new ArrayList<>();
		aktionen = new ArrayList<>();
		String cde1 = code.replace("\n	", "|");
		String[] cde = cde1.split("\n");
		for(int i = 0; i < cde.length; i++)
		{
			if(!cde[i].isEmpty() && !cde[i].startsWith("/"))
			{
				if(cde[i].startsWith("Z "))
				{
					zeitE.add(Integer.parseInt(cde[i].substring(2)));
					teilE++;
				}
				else if(cde[i].startsWith("R "))
					resist = Integer.parseInt(cde[i].substring(2));
				else
				{
					String[] cd2 = cde[i].split(" ");
					aktionen.add(new LadeAktion(teilE, cd2));
				}
			}
		}
	}

	public static LadeMove gibVonIndex(boolean seq, String name)
	{
		if(Index.geladen.containsKey(name))
			return (LadeMove) Index.geladen.get(name);
		LadeMove s = new LadeMove(name, Index.bauName(seq ? "Sequenzen" : "Moves", name));
		Index.geladen.put(name, s);
		return s;
	}
}