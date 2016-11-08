package achsen;

import indexLader.*;
import java.util.*;

public class Alternate1 extends LC2
{
	ADreh1[] drehungen;

	public Alternate1(String build, int len)
	{
		drehungen = new ADreh1[len];
		if(build.length() == 0)
			return;
		build = build.replace("\t", "").replace("\n", "").replace(" ", "");
		int next = 0;
		for(String line : build.split(";"))
		{
			if(line.length() == 0)
				continue;
			if(line.contains("="))
			{
				String[] lr = line.split("=");
				next = Integer.parseInt(lr[0]);
				line = lr[1];
			}
			drehungen[next] = new ADreh1(line);
			next++;
		}
	}

	public static String auto(String build, ArrayList<Integer> achsen)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(build);
		build = build.replace("\t", "").replace("\n", "").replace(" ", "");
		int next = 0;
		for(String line : build.split(";"))
		{
			if(line.length() == 0)
				continue;
			if(line.contains("="))
				next = Integer.parseInt(line.split("=")[0]);
			achsen.remove((Integer) next);
			next++;
		}
		for(Integer fehlt : achsen)
			sb.append(fehlt).append(" = 0, 0, 1;\n");
		return sb.toString();
	}

	private static final KXS forArgh = new KXS(true, true, true, false, false, false);

	public ErrorVial argh(String build)
	{
		ErrorVial vial = new ErrorVial();
		ArrayList<ADreh1> dreh1 = new ArrayList<>();
		superwaguh(vial.prep(build), 0, vial, forArgh, dreh1, this, "arghS", dreh1);
		drehungen = dreh1.toArray(new ADreh1[dreh1.size()]);
		return vial;
	}

	public void arghS(Integer numKey, String textKey, String value,
			Integer errStart, Integer errEnd, ErrorVial vial, ArrayList<ADreh1> dreh1)
	{
		dreh1.add(new ADreh1(value, errStart, errEnd, vial));
	}

	public static Alternate1 gibVonL4(String name1, String name2, int len, boolean save)
	{
		String s = Lader4.bauName("Ladeteile1", name1, name2);
		Alternate1 toR;
		if(save)
		{
			String s2 = "Alternate1 " + s;
			if(Lader4.map.containsKey(s2))
				toR = (Alternate1) Lader4.map.get(s2);
			else
			{
				toR = new Alternate1(Lader4.readR(s), len);
				Lader4.map.put(s2, toR);
			}
		}
		else
			toR = new Alternate1(Lader4.readR(s), len);
		return toR;
	}
}