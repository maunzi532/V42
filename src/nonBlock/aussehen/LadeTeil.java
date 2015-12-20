package nonBlock.aussehen;

import ansicht.n2.xF.*;

import java.util.*;

public class LadeTeil
{
	final HashMap<Integer, ArrayList<LadePunkt>> punkte;
	final ArrayList<LadeF2> f2;
	final int end;

	public LadeTeil(String code)
	{
		String[] zeilen = code.split("\n");
		int achseAktuell = -1;
		punkte = new HashMap<>();
		f2 = new ArrayList<>();
		for(int i = 0; i < zeilen.length; i++)
		{
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				if(zeilen[i].startsWith("Achse "))
				{
					achseAktuell = Integer.parseInt(zeilen[i].substring(6, zeilen[i].length()));
					punkte.put(achseAktuell, new ArrayList<>());
				}
				else if(zeilen[i].startsWith("F") && zeilen[i].charAt(2) == ' ')
				{
					XFarbe farbe;
					Boolean seite = null;
					String[] x2 = zeilen[i].split(" ");
					if(x2[0].equals("FA"))
						seite = true;
					else if(x2[0].equals("FV"))
						seite = false;
					farbe = XFarbe.t2xf(x2[1]);
					f2.add(new LadeF2(farbe, seite));
					for(int j = 0; j + 2 < x2.length; j++)
					{
						String[] cx2 = x2[j + 2].split("-");
						if(cx2.length <= 2)
						{
							if(cx2[0].startsWith("+"))
							{
								f2.get(f2.size() - 1).ecken1.add(Integer.parseInt(cx2[0].substring(1)));
								f2.get(f2.size() - 1).ecken3.add(true);
							}
							else
							{
								f2.get(f2.size() - 1).ecken1.add(Integer.parseInt(cx2[0]));
								f2.get(f2.size() - 1).ecken3.add(false);
							}
							f2.get(f2.size() - 1).ecken2.add(Integer.parseInt(cx2[1]));
						}
						else
						{
							if(cx2[0].startsWith("+"))
							{
								f2.get(f2.size() - 1).spken1.add(Integer.parseInt(cx2[0].substring(1)));
								f2.get(f2.size() - 1).spken3.add(true);
							}
							else
							{
								f2.get(f2.size() - 1).spken1.add(Integer.parseInt(cx2[0]));
								f2.get(f2.size() - 1).spken3.add(false);
							}
							f2.get(f2.size() - 1).spken2.add(Integer.parseInt(cx2[1]));
						}
					}
				}
				else
				{
					String[] x3 = zeilen[i].split(" ");
					punkte.get(achseAktuell).add(new LadePunkt(achseAktuell,
							Double.parseDouble(x3[0]), Double.parseDouble(x3[1]), Double.parseDouble(x3[2])));
				}
			}
		}
		end = Collections.max(punkte.keySet());
	}

}