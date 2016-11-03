package achsen;

import indexLader.*;
import java.util.*;

public class Achse1
{
	Punkt1[] punkte;
	int linkedAchse = -1;
	int linkedPunkt;

	public Achse1(){}

	public Achse1(String build)
	{
		ArrayList<Punkt1> punkte1 = new ArrayList<>();
		String[] b2 = build.substring(1, build.length() - 1).split(";");
		punkte1.add(new Punkt1(0, 0, 0));
		punkte1.add(new Punkt1(1, 0, 0));
		for(String line : b2)
		{
			if(line.length() == 0)
				continue;
			String[] lr = line.split("=");
			if(lr[0].equals("a"))
			{
				String[] r = lr[1].split(",");
				linkedAchse = Integer.parseInt(r[0]);
				linkedPunkt = Integer.parseInt(r[1]);
			}
			else
			{
				String[] r;
				if(lr.length > 1)
				{
					int ort = Integer.parseInt(lr[0]);
					while(punkte1.size() < ort)
						punkte1.add(null);
					r = lr[1].split(",");
				}
				else
					r = lr[0].split(",");
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
				double baseSpin = d2r(r[4]);
				for(int i = 0; i < ecken; i++)
					p1.add(new Punkt1(vor, abstand, (baseSpin + Math.PI * 2 / ecken * i) % (Math.PI * 2)));
				break;
			}
			case "q":
			{
				double vor = Double.parseDouble(r[1]);
				double abstand = Double.parseDouble(r[2]);
				double baseSpin = d2r(r[3]);
				for(int i = 0; i < 4; i++)
					p1.add(new Punkt1(vor, abstand, ((i + 1) / 2) * Math.PI + (i % 2 == 0 ? baseSpin : -baseSpin)));
				break;
			}
			default:
				p1.add(new Punkt1(Double.parseDouble(r[0]),
						Double.parseDouble(r[1]), d2r(r[2])));
		}
	}

	public void argh(String build, int errStart, ErrorVial vial)
	{
		ArrayList<Punkt1> punkteL = new ArrayList<>();
		punkteL.add(new Punkt1(0, 0, 0));
		punkteL.add(new Punkt1(1, 0, 0));
		ArrayList<Integer> ends = new ArrayList<>();
		ArrayList<String> buildSpl = LC2.klaSplit2(build, true, errStart, ends);
		int lnum = -1;
		for(int i = 0; i < buildSpl.size(); i++)
		{
			Object[] returned = LC2.extractKey(buildSpl.get(i), ends.get(i), ends.get(i + 1),
					false, true, true, lnum, vial);
			if(returned[0] != null)
			{
				lnum = LC2.fillthis(returned, punkteL, ends, i, vial);
				Punkt1.argh((String) returned[2], punkteL, ends.get(i), ends.get(i + 1), vial);
			}
			else if("a".equalsIgnoreCase((String) returned[1]))
				arghA((String) returned[2], ends.get(i), ends.get(i + 1), vial);
			else
				vial.add(new CError("Wos is des?", ends.get(i), ends.get(i + 1)));
		}
		punkte = punkteL.toArray(new Punkt1[punkteL.size()]);
	}

	private void arghA(String build, int errStart, int errEnd, ErrorVial vial)
	{
		ArrayList<Integer> ends = new ArrayList<>();
		ArrayList<String> buildSpl = LC2.klaSplit2(build, false, errStart, ends);
		if(buildSpl.size() == 2)
		{
			try
			{
				linkedAchse = Integer.parseInt(buildSpl.get(0));
			}catch(NumberFormatException e)
			{
				vial.add(new CError("Anschluss Achse ist kein int",
						ends.get(0), ends.get(1)));
			}
			try
			{
				linkedPunkt = Integer.parseInt(buildSpl.get(1));
			}catch(NumberFormatException e)
			{
				vial.add(new CError("Anschluss Punkt ist kein int",
						ends.get(1), ends.get(2)));
			}
		}
		else
		{
			vial.add(new CError("Anschluss Parameter anzahl: " + buildSpl.size() + ", muss 2 sein",
					errStart, errEnd));
		}
	}

	static double d2r(String tr)
	{
		return Double.parseDouble(tr) / 180d * Math.PI;
	}
}