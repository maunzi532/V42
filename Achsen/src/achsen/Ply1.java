package achsen;

import indexLader.*;
import java.util.*;

public class Ply1 extends LC2
{
	public static IFarbe factory;

	public ArrayList<AP1> punkte;
	public IFarbe farbe;
	public Boolean seite;

	public Ply1(){}

	public Ply1(ArrayList<AP1> punkte, IFarbe farbe, Boolean seite)
	{
		this.punkte = punkte;
		this.farbe = farbe;
		this.seite = seite;
	}

	public Ply1(String build, IFarbe farbe, Boolean seite)
	{
		this(build);
		this.farbe = farbe;
		this.seite = seite;
	}

	public Ply1(String build)
	{
		punkte = new ArrayList<>();
		int achse = -1;
		ArrayList<String> teils = LC2.klaSplit(build);
		for(int i = 0; i < teils.size(); i++)
			if(i % 2 == 0)
				achse = Integer.parseInt(teils.get(i));
			else
			{
				String ti = teils.get(i);
				if(ti.charAt(0) == '{')
				{
					String[] nums = ti.substring(1, ti.length() - 1).split(",");
					for(int j = 0; j < nums.length; j++)
						punkte.add(new AP1(achse, Integer.parseInt(nums[j])));
				}
				else if(ti.contains("-"))
				{
					String[] lr = ti.split("-");
					int n = Integer.parseInt(lr[0]);
					int max = Integer.parseInt(lr[1]);
					for(; n <= max; n++)
						punkte.add(new AP1(achse, n));
				}
				else
					punkte.add(new AP1(achse, Integer.parseInt(ti)));
			}
	}

	public boolean argh(String build, Standard1 standard1, boolean mitF, IFarbe farbe1, Boolean seite1,
			int errStart, int errEnd, ErrorVial vial)
	{
		if(mitF)
		{
			if(farbe1 != null)
				farbe = farbe1;
			else
			{
				vial.add(new CError("Farbe noch nicht definiert", errStart, errEnd));
				return false;
			}
			seite = seite1;
		}
		punkte = new ArrayList<>();
		ArrayList<Integer> ends = new ArrayList<>();
		ArrayList<String> teils = LC2.klaSplit2(build, false, errStart, ends);
		if(teils.size() % 2 != 0)
		{
			vial.add(new CError("Achsennummer ohne Punkte", errEnd - 1, errEnd));
			teils.remove(teils.size() - 1);
		}
		for(int i = 0; i < teils.size(); i += 2)
		{
			int achse = -1;
			try
			{
				achse = Integer.parseUnsignedInt(teils.get(i));
			}
			catch(NumberFormatException e)
			{
				vial.add(new CError("Achsennummer " + teils.get(i) + " ist kein positiver int",
						ends.get(i), ends.get(i + 1)));
			}
			ArrayList<Integer> xaec = xaec(teils.get(i + 1), ends.get(i + 1), ends.get(i + 2), vial);
			if(achse >= 0 && achse < standard1.achsen.length)
			{
				if(standard1.achsen[achse] != null)
				{
					for(Integer j : xaec)
						if(j < standard1.achsen[achse].punkte.size() &&
								standard1.achsen[achse].punkte.get(j) != null)
							punkte.add(new AP1(achse, j));
						else
							vial.add(new CError("Punkt Nummer " + j + "nicht in Achse " + achse + " enthalten",
									ends.get(i), ends.get(i + 2)));
				}
				else
					vial.add(new CError("Achsennummer " + achse + " nicht im .v42s enthalten",
							ends.get(i), ends.get(i + 1)));
			}
		}
		if(punkte.size() >= 3)
			return true;
		else
		{
			vial.add(new CError("Mindestens 3 Punkte", errStart, errEnd));
			return false;
		}
	}

	public ArrayList<Integer> xaec(String build, int errStart, int errEnd, ErrorVial vial)
	{
		ArrayList<Integer> toR = new ArrayList<>();
		if(build.charAt(0) == '{' && build.charAt(build.length() - 1) == '}')
		{
			ArrayList<Integer> ends = new ArrayList<>();
			ArrayList<String> buildSpl = klaSplit2(build.substring(1, build.length() - 1),
					false, errStart, ends);
			for(int i = 0; i < buildSpl.size(); i++)
				toR.addAll(xaec(buildSpl.get(i), ends.get(i), ends.get(i + 1), vial));
		}
		else if(build.contains("-"))
		{
			String[] b2 = build.split("-");
			if(b2.length != 2)
				vial.add(new CError("Nur 1 \"-\" erlaubt", errStart, errEnd));
			int[] nums = new int[2];
			boolean ok = true;
			for(int i = 0; i < 2; i++)
				try
				{
					nums[i] = Integer.parseUnsignedInt(b2[i]);
				}
				catch(NumberFormatException e)
				{
					vial.add(new CError("Punkt Nummer ist kein positiver int",
							errStart, errEnd));
					ok = false;
				}
			if(ok)
				if(nums[1] > nums[0])
					for(int j = nums[0]; j <= nums[1]; j++)
						toR.add(j);
				else
					for(int j = nums[0]; j >= nums[1]; j--)
						toR.add(j);
		}
		else
			try
			{
				toR.add(Integer.parseUnsignedInt(build));
			}
			catch(NumberFormatException e)
			{
				vial.add(new CError("Punkt Nummer ist kein positiver int",
						errStart, errEnd));
			}
		return toR;
	}

	public static ArrayList<Ply1> prisma(IFarbe farbe1, Boolean seite1,
			Ply1 unten, Ply1 oben, int errStart, int errEnd, ErrorVial vial)
	{
		if(farbe1 == null)
		{
			vial.add(new CError("Farbe noch nicht definiert", errStart, errEnd));
			return new ArrayList<>();
		}
		if(unten == null)
		{
			vial.add(new CError("U noch nicht definiert", errStart, errEnd));
			return new ArrayList<>();
		}
		if(oben == null)
		{
			vial.add(new CError("O noch nicht definiert", errStart, errEnd));
			return new ArrayList<>();
		}
		int len = unten.punkte.size();
		if(len != oben.punkte.size())
		{
			vial.add(new CError("Anzahl Punkte unterschiedlich, U = " + len +
					", O = " + oben.punkte.size(), errStart, errEnd));
			return new ArrayList<>();
		}
		ArrayList<Ply1> plys = new ArrayList<>();
		for(int j = 0; j < len; j++)
		{
			ArrayList<AP1> punkte = new ArrayList<>();
			punkte.add(unten.punkte.get(j));
			punkte.add(unten.punkte.get((j + 1) % len));
			punkte.add(oben.punkte.get((j + 1) % len));
			punkte.add(oben.punkte.get(j));
			plys.add(new Ply1(punkte, farbe1, seite1));
		}
		return plys;
	}
}