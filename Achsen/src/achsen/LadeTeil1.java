package achsen;

import indexLader.*;
import java.util.*;

public class LadeTeil1 implements LC1
{
	public static IFarbe factory;

	public ArrayList<Ply1> plys;

	public LadeTeil1(String build)
	{
		plys = new ArrayList<>();
		if(build.length() == 0)
			return;
		for(String decl : klaSplit(build))
		{
			String[] b2 = decl.substring(1, decl.length() - 1).split(";");
			IFarbe farbe = null;
			Boolean seite = null;
			Ply1 oben = null;
			Ply1 unten = null;
			for(String line : b2)
			{
				if(line.length() == 0)
					continue;
				String[] lr;
				if(line.contains("="))
					lr = line.split("=");
				else
					lr = new String[]{line};
				switch(lr[0])
				{
					case "a":
						seite = true;
						break;
					case "v":
						seite = false;
						break;
					case "f":
						farbe = factory.gibNeu(klaSplit(lr[1]));
						break;
					case "o":
						oben = new Ply1(lr[1]);
						break;
					case "u":
						unten = new Ply1(lr[1]);
						break;
					case "p":
						assert farbe != null;
						plys.add(new Ply1(lr[1], farbe, seite));
						break;
					case "PRISMA":
					{
						assert farbe != null;
						assert oben != null;
						assert unten != null;
						int len = unten.punkte.size();
						for(int j = 0; j < len; j++)
						{
							ArrayList<AP1> punkte = new ArrayList<>();
							punkte.add(unten.punkte.get(j));
							punkte.add(unten.punkte.get((j + 1) % len));
							punkte.add(oben.punkte.get((j + 1) % len));
							punkte.add(oben.punkte.get(j));
							plys.add(new Ply1(punkte, farbe, seite));
						}
						break;
					}
					case "PYRA":
					{
						assert farbe != null;
						assert oben != null;
						assert unten != null;
						int len = unten.punkte.size();
						for(int j = 0; j < len; j++)
						{
							ArrayList<AP1> punkte = new ArrayList<>();
							punkte.add(unten.punkte.get(j));
							punkte.add(unten.punkte.get((j + 1) % len));
							punkte.add(oben.punkte.get(0));
							plys.add(new Ply1(punkte, farbe, seite));
						}
						break;
					}
					case "ZPRISMA":
					{
						assert farbe != null;
						assert oben != null;
						assert unten != null;
						int len = unten.punkte.size();
						for(int j = 0; j < len; j++)
						{
							ArrayList<AP1> punkte = new ArrayList<>();
							punkte.add(unten.punkte.get(j));
							punkte.add(unten.punkte.get((j + 1) % len));
							punkte.add(oben.punkte.get(j));
							plys.add(new Ply1(punkte, farbe, seite));
							ArrayList<AP1> punkte1 = new ArrayList<>();
							punkte1.add(unten.punkte.get((j + 1) % len));
							punkte1.add(oben.punkte.get((j + 1) % len));
							punkte1.add(oben.punkte.get(j));
							plys.add(new Ply1(punkte1, farbe, seite));
						}
						break;
					}
				}
			}
		}
	}

	public static LadeTeil1 gibVonL4(String name1, String name2, boolean save)
	{
		String s = Lader4.bauName("Ladeteile1", name1, name2);
		LadeTeil1 toR;
		if(save)
		{
			String s2 = "LadeTeil1 " + s;
			if(Lader4.map.containsKey(s2))
				toR = (LadeTeil1) Lader4.map.get(s2);
			else
			{
				toR = new LadeTeil1(Lader4.readR(s));
				Lader4.map.put(s2, toR);
			}
		}
		else
			toR = new LadeTeil1(Lader4.readR(s));
		return toR;
	}
}