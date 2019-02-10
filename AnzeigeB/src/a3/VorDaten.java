package a3;

import indexLader.*;
import java.util.*;

public class VorDaten
{
	public ArrayList<Double> abstands;
	public ArrayList<Integer> splits;

	public VorDaten(String abstandSplitInput)
	{
		abstands = new ArrayList<>();
		splits = new ArrayList<>();
		String[] zeilen = abstandSplitInput.split("\n");
		for(int i = 0; i < zeilen.length; i++)
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				String[] z1 = zeilen[i].split(" ");
				abstands.add(Double.parseDouble(z1[0]));
				splits.add(Integer.parseInt(z1[1]));
			}
	}

	public VorDaten()
	{
		abstands = new ArrayList<>();
		splits = new ArrayList<>();
	}

	public static VorDaten gibVonIndex(String name)
	{
		if(Index.geladen.containsKey("VD" + name))
			return (VorDaten) Index.geladen.get("VD" + name);
		VorDaten s;
		try
		{
			s = new VorDaten(Index.bauName("Blocks", name));
		}
		catch(Exception e)
		{
			s = new VorDaten();
		}
		Index.geladen.put("VD" + name, s);
		return s;
	}
}