package achsen;

import indexLader.*;
import java.io.*;
import java.util.*;

public class Standard1 implements LC1
{
	ArrayList<Achse1> achsen;

	public Standard1(String build)
	{
		achsen = new ArrayList<>();
		for(String line : klaSplit(build))
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

	public static Standard1 gibVonIndex(String name)
	{
		name = name + File.separator + "Achsen";
		if(Index.geladen.containsKey(name))
			return (Standard1) Index.geladen.get(name);
		Standard1 s = new Standard1(Index.bauName("Ladeteile1", name));
		Index.geladen.put(name, s);
		return s;
	}
}