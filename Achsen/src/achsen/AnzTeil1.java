package achsen;

import indexLader.*;
import java.util.*;

public class AnzTeil1 extends LC2
{
	public ArrayList<Ply1> plys;

	public AnzTeil1(){}

	public AnzTeil1(String build)
	{
		plys = new ArrayList<>();
		if(build.length() == 0)
			return;
		for(String decl : LC2.klaSplit(build))
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
						farbe = Ply1.factory.gibNeu(LC2.klaSplit(lr[1]));
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

	private static final KXS forArgh = new KXS(false, false, false, false, false, true);

	public ErrorVial argh(String build, Standard1 standard1)
	{
		ErrorVial vial = new ErrorVial();
		plys = new ArrayList<>();
		superwaguh(vial.prep(build), 0, vial, forArgh, null, this, "arghS", standard1);
		return vial;
	}

	private static final KXS forArghS = new KXS(true, true, false, true, true, false);

	public void arghS(Integer numKey, String textKey, String value,
			Integer errStart, Integer errEnd, ErrorVial vial, Standard1 standard1)
	{
		Object[] keeper = new Object[4]; //Farbe, Seite, Unten, Oben
		superwaguh(value, errStart, vial, forArghS, null, this, "arghS2", standard1, keeper);
	}

	public void arghS2(Integer numKey, String textKey, String value,
			Integer errStart, Integer errEnd, ErrorVial vial, Standard1 standard1, Object[] keeper)
	{
		switch(textKey.toUpperCase())
		{
			case "M":
				keeper[1] = null;
				break;
			case "A":
				keeper[1] = true;
				break;
			case "V":
				keeper[1] = false;
				break;
			case "F":
				if(requireValue(value, errStart, vial))
				{
					keeper[0] = Ply1.factory.gibNeu(value, errStart, errEnd, vial);
				}
				break;
			case "U":
				if(requireValue(value, errStart, vial))
				{
					Ply1 ply = new Ply1();
					if(ply.argh(value, standard1, false, null, null, errStart, errEnd, vial))
						keeper[2] = ply;
				}
				break;
			case "O":
				if(requireValue(value, errStart, vial))
				{
					Ply1 ply = new Ply1();
					if(ply.argh(value, standard1, false, null, null, errStart, errEnd, vial))
						keeper[3] = ply;
				}
				break;
			case "P":
				if(requireValue(value, errStart, vial))
				{
					Ply1 ply = new Ply1();
					if(ply.argh(value, standard1, true, (IFarbe) keeper[0],
						(Boolean) keeper[1], errStart, errEnd, vial))
						plys.add(ply);
				}
				break;
			case "PRISMA":
				plys.addAll(Ply1.prisma((IFarbe) keeper[0], (Boolean) keeper[1],
						(Ply1) keeper[2], (Ply1) keeper[3], errStart, errEnd, vial));
				break;
			case "PYRA":
				break;
			case "ZPRISMA":
				break;
			default:
				vial.add(new CError("Wos is des?", errStart - 1, errStart));
		}
	}

	public static AnzTeil1 gibVonL4(String name1, String name2, boolean save)
	{
		String s = Lader4.bauName("Ladeteile1", name1, name2);
		AnzTeil1 toR;
		if(save)
		{
			String s2 = "LadeTeil1 " + s;
			if(Lader4.map.containsKey(s2))
				toR = (AnzTeil1) Lader4.map.get(s2);
			else
			{
				toR = new AnzTeil1(Lader4.readR(s));
				Lader4.map.put(s2, toR);
			}
		}
		else
			toR = new AnzTeil1(Lader4.readR(s));
		return toR;
	}
}