package edit;

import achsen.*;
import java.util.*;
import k4.*;

public class AView
{
	AchsenK1 ak1;
	String name;
	String drehfile;
	List<String> sichtbar;

	Drehung kamDreh;
	K4 kamMoved;
	double kamDistance;

	public AView(String name, String build)
	{
		this.name = name;
		String[] werte = build.split(",");
		kamDistance = Double.parseDouble(werte[0]);
		kamDreh = new Drehung(Double.parseDouble(werte[1]), Double.parseDouble(werte[2]));
		kamMoved = new K4(Double.parseDouble(werte[3]), Double.parseDouble(werte[4]),
				Double.parseDouble(werte[5]), Double.parseDouble(werte[6]));
		if(!"keines".equals(werte[7]))
			drehfile = werte[7];
		sichtbar = new ArrayList<>();
		sichtbar.addAll(Arrays.asList(werte).subList(8, werte.length));
		createak1();
	}

	public void createak1()
	{
		ak1 = new AchsenK1(new K4(), new Drehung(), name);
		if(drehfile != null)
		{
			try
			{
				ak1.reload(false, name, drehfile, sichtbar.toArray(new String[sichtbar.size()]));
			}catch(Exception e)
			{
				//Fehler
			}
		}
	}
}