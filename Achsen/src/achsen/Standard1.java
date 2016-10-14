package achsen;

import indexLader.*;
import java.util.*;

public class Standard1 implements LC1
{
	ArrayList<Achse1> achsen;

	public Standard1(String build)
	{
		achsen = new ArrayList<>();
		if(build.length() == 0)
			return;
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