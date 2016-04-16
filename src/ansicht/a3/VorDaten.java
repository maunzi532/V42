package ansicht.a3;

import java.util.*;

class VorDaten
{
	ArrayList<Double> abstands;
	ArrayList<Integer> splits;

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
}