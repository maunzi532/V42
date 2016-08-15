package achsen;

import java.util.*;

public class Achse1
{
	Punkt1[] punkte;
	int linkedAchse = -1;
	int linkedPunkt;
	boolean neueDrehung;

	/*
	{2:2.3,4.5,6.7;...;l:10,1}
	 */
	public Achse1(String build)
	{
		ArrayList<Punkt1> punkte1 = new ArrayList<>();
		String[] b2 = build.substring(1, build.length() - 1).split(";");
		punkte1.add(new Punkt1(0, 0, 0));
		punkte1.add(new Punkt1(1, 0, 0));
		for(String line : b2)
		{
			String[] lr = line.split("=");
			if(lr[0].equals("l"))
			{
				String[] r = lr[1].split(",");
				linkedAchse = Integer.parseInt(r[0]);
				linkedPunkt = Integer.parseInt(r[1]);
				if(r.length > 2 && r[2].charAt(0) == 'n')
					neueDrehung = true;
			}
			else
			{
				String[] r;
				if(lr.length > 1)
				{
					int ort = Integer.parseInt(lr[0]);
					while(punkte1.size() < ort)
						punkte1.add(null);
					r = lr[0].split(",");
				}
				else
					r = lr[1].split(",");
				punkte(r, punkte1);
			}
		}
		punkte = punkte1.toArray(new Punkt1[punkte1.size()]);
	}

	private void punkte(String[] r, ArrayList<Punkt1> p1)
	{
		switch(r[0])
		{
			case "r":
			{
				int ecken = Integer.parseInt(r[1]);
				double vor = Double.parseDouble(r[2]);
				double abstand = Double.parseDouble(r[3]);
				double baseSpin = Double.parseDouble(r[4]);
				for(int i = 0; i < ecken; i++)
					p1.add(new Punkt1(vor, abstand, (baseSpin + 360d / ecken * i) % 360));
				break;
			}
			default:
				p1.add(new Punkt1(Double.parseDouble(r[0]),
						Double.parseDouble(r[1]), Double.parseDouble(r[2])));
		}
	}
}