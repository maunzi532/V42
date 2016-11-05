package achsen;

import indexLader.*;
import java.util.*;

public class Ply1
{
	public ArrayList<AP1> punkte;
	public IFarbe farbe;
	public Boolean seite;

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

	public void argh(String build, Standard1 standard1, int errStart, int errEnd, ErrorVial vial)
	{

	}
}