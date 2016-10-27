package achsen;

import indexLader.*;
import java.util.*;

public class Standard1
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

	public void argh(String build)
	{
		ErrorVial errors = new ErrorVial();
		build = errors.prep(build);
		achsen = new ArrayList<>();
		ArrayList<Integer> ends2_1 = new ArrayList<>();
		ArrayList<String> buildSpl_1 = LC2.klaSplit2(build, false, 0, ends2_1);
		int lnum_1 = -1;
		for(int i_1 = 0; i_1 < buildSpl_1.size(); i_1++)
		{
			Object[] returned_1 = LC2.extractKey(buildSpl_1.get(i_1), ends2_1.get(i_1), ends2_1.get(i_1 + 1),
					true, true, false, lnum_1, errors);
			lnum_1 = LC2.fillthis(returned_1, achsen, ends2_1, i_1, errors);
			Achse1 ac_2 = new Achse1();
			achsen.add(ac_2);
			ArrayList<Punkt1> punkte_2 = new ArrayList<>();
			ArrayList<Integer> ends2_2 = new ArrayList<>();
			ArrayList<String> buildSpl_2 = LC2.klaSplit2((String) returned_1[2], true, ends2_1.get(i_1), ends2_2);
			int lnum_2 = -1;
			for(int i_2 = 0; i_2 < buildSpl_2.size(); i_2++)
			{
				Object[] returned_2 = LC2.extractKey(buildSpl_2.get(i_2), ends2_2.get(i_2), ends2_2.get(i_2 + 1),
						false, true, true, lnum_2, errors);
				if(returned_2[0] != null)
				{
					lnum_2 = LC2.fillthis(returned_2, punkte_2, ends2_2, i_2, errors);
					//punkte_2.add(new Punkt1());
					//argh
				}
				else
				{
					//argh
				}
			}
		}
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