package nonBlock.aussehen;

import java.util.*;

public class LadeModell
{
	public ArrayList<LadePunkt>[] punkte;
	public ArrayList<LadeF2> f2;

	public LadeModell reload(LadeTeil... teile)
	{
		ArrayList<Integer> max = new ArrayList<>();
		for(int i = 0; i < teile.length; i++)
			max.add(teile[i].end);
		if(max.size() == 0)
			//noinspection unchecked
			punkte = new ArrayList[0];
		else
			//noinspection unchecked
			punkte = new ArrayList[Collections.max(max) + 1];
		f2 = new ArrayList<>();
		for(int j = 0; j < punkte.length; j++)
			punkte[j] = new ArrayList<>();
		for(int i = 0; i < teile.length; i++)
		{
			for(int j = 0; j < punkte.length; j++)
				if(teile[i].punkte.get(j) != null)
					for(int k = 0; k < teile[i].punkte.get(j).size(); k++)
						punkte[j].add(teile[i].punkte.get(j).get(k));
			f2.addAll(teile[i].f2);
		}
		return this;
	}
}