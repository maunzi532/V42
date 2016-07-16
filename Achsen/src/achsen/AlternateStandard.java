package achsen;

import indexLader.*;
import java.util.*;
import k4.*;

public class AlternateStandard
{
	protected int la;
	public Drehung[] drehs;
	public Double[] lens;
	public Double[] spins;
	public Double[] dShifts;
	protected K4[] teles;

	AlternateStandard(){}

	private AlternateStandard(String code)
	{
		AlternateStandard main = null;
		String[] zeilen = code.split("\n");
		ArrayList<Integer> numm = new ArrayList<>();
		ArrayList<Drehung> dreh = new ArrayList<>();
		ArrayList<Double> len = new ArrayList<>();
		ArrayList<Double> spin = new ArrayList<>();
		ArrayList<Double> dShift = new ArrayList<>();
		ArrayList<K4> tele = new ArrayList<>();
		for(int i = 0; i < zeilen.length; i++)
		{
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				if(zeilen[i].startsWith("Main="))
					main = StandardAussehen.gibVonIndexS(zeilen[i].substring(5));
				else
				{
					String[] y1 = zeilen[i].split(" ");
					numm.add(Integer.parseInt(y1[0]));
					dreh.add(Drehung.nDrehung(Double.parseDouble(y1[1]) * Math.PI / 180,
							Double.parseDouble(y1[2]) * Math.PI / 180));
					len.add(Double.parseDouble(y1[3]));
					if(y1.length > 4)
						spin.add(Double.parseDouble(y1[4]) * Math.PI / 180);
					else
						spin.add(0d);
					if(y1.length > 5)
						dShift.add(Double.parseDouble(y1[5]));
					else
						dShift.add(0d);
					if(y1.length > 6)
						tele.add(new K4(Double.parseDouble(y1[6]), Double.parseDouble(y1[7]),
								Double.parseDouble(y1[8]), Double.parseDouble(y1[9])));
					else
						tele.add(null);
				}
			}
		}
		assert main != null;
		la = main.la;
		drehs = new Drehung[la];
		lens = new Double[la];
		spins = new Double[la];
		dShifts = new Double[la];
		teles = new K4[la];
		for(int i = 0; i < la; i++)
		{
			int p = numm.indexOf(i);
			if(p < 0)
			{
				drehs[i] = main.drehs[i];
				lens[i] = main.lens[i];
				spins[i] = main.spins[i];
				dShifts[i] = main.dShifts[i];
				teles[i] = main.teles[i];
			}
			else
			{
				drehs[i] = dreh.get(p);
				lens[i] = len.get(p);
				spins[i] = spin.get(p);
				dShifts[i] = dShift.get(p);
				teles[i] = tele.get(p);
			}
		}
	}

	public static AlternateStandard gibVonIndex(String name)
	{
		if(Index.geladen.containsKey("STA" + name))
			return (AlternateStandard) Index.geladen.get("STA" + name);
		AlternateStandard s = new AlternateStandard(Index.bauName("Ladeteile", name));
		Index.geladen.put("STA" + name, s);
		return s;
	}
}