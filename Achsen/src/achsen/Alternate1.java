package achsen;

import indexLader.*;
import java.io.*;

public class Alternate1
{
	ADreh1[] drehungen;

	public Alternate1(String build, int len)
	{
		build = build.replace("\t", "").replace("\n", "").replace(" ", "");
		drehungen = new ADreh1[len];
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

	public static Alternate1 gibVonIndex(String name, int len)
	{
		name = name + File.separator + "Drehs";
		if(Index.geladen.containsKey(name))
			return (Alternate1) Index.geladen.get(name);
		Alternate1 s = new Alternate1(Index.bauName("Ladeteile1", name), len);
		Index.geladen.put(name, s);
		return s;
	}
}