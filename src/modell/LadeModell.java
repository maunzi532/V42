package modell;

import modell.ext.*;

import java.util.*;

public class LadeModell
{
	public ArrayList<LadePunkt>[] punkte;
	public ArrayList<LadeF2> f2;
	public ArrayList<External> ext;

	public LadeModell reload(LadeTeil... teile)
	{
		return reload(null, teile);
	}

	public LadeModell reload(External[] exts, LadeTeil... teile)
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
		ext = new ArrayList<>();
		if(exts != null)
			Collections.addAll(ext, exts);
		return this;
	}

	public int allext()
	{
		int toR = 0;
		for(int i = 0; i < ext.size(); i++)
			toR += ext.get(i).platz();
		return toR;
	}
}