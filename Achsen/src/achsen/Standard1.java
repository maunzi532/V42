package achsen;

import indexLader.*;
import java.util.*;

public class Standard1 implements Decodable
{
	ArrayList<Achse1> achsen;

	public Standard1(){}

	public Standard1(String build)
	{
		achsen = new ArrayList<>();
		if(build.length() == 0)
			return;
		for(String line : LC2.klaSplit(build))
		{
			int div = line.indexOf("=");
			int klae = line.indexOf("{");
			if(klae < div)
				div = 0;
			else
			{
				int ort = Integer.parseInt(line.substring(0, div));
				while(ort > achsen.size())
					achsen.add(null);
			}
			achsen.add(new Achse1(line.substring(div == 0 ? 0 : div + 1)));
		}
	}

	public int achsenAnz()
	{
		return achsen.size();
	}

	public static ArrayList<Integer> achsennummern(String build)
	{
		if(build.length() == 0)
			return new ArrayList<>();
		ArrayList<Integer> toR = new ArrayList<>();
		for(String line : LC2.klaSplit(build))
		{
			int div = line.indexOf("=");
			int klae = line.indexOf("{");
			if(klae < div)
				div = 0;
			else
				toR.add(Integer.parseInt(line.substring(0, div)));
		}
		return toR;
	}

	public ErrorVial argh(String build)
	{
		ErrorVial vial = new ErrorVial();
		build = vial.prep(build);
		achsen = new ArrayList<>();
		ArrayList<Integer> ends = new ArrayList<>();
		ArrayList<String> buildSpl = LC2.klaSplit2(build, false, 0, ends);
		int lnum = -1;
		for(int i = 0; i < buildSpl.size(); i++)
		{
			Object[] returned = LC2.extractKey(buildSpl.get(i), ends.get(i), ends.get(i + 1),
					true, true, false, lnum, vial);
			lnum = LC2.fillthis(returned, achsen, ends, i, vial);
			Achse1 ac = new Achse1();
			achsen.add(ac);
			ac.argh((String) returned[2], ends.get(i), vial);
		}
		return vial;
	}

	public static Standard1 gibVonL4(String name, boolean save)
	{
		String s = Lader4.bauName("Ladeteile1", name, "Standard.v42s");
		Standard1 toR;
		if(save)
		{
			String s2 = "Standard1 " + s;
			if(Lader4.map.containsKey(s2))
				toR = (Standard1) Lader4.map.get(s2);
			else
			{
				toR = new Standard1(Lader4.readR(s));
				Lader4.map.put(s2, toR);
			}
		}
		else
			toR = new Standard1(Lader4.readR(s));
		return toR;
	}
}