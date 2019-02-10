package ext;

import achsen.*;
import indexLader.*;
import java.util.*;
import k4.*;

public class XSta extends StandardAussehen
{
	private final ArrayList<Integer> enhStart;
	private final ArrayList<Integer> enhLink;

	private XSta(String code)
	{
		String[] zeilen = code.split("\n");
		ArrayList<Integer> numm = new ArrayList<>();
		ArrayList<Integer> linked = new ArrayList<>();
		ArrayList<Drehung> dreh = new ArrayList<>();
		ArrayList<Double> len = new ArrayList<>();
		ArrayList<Double> spin = new ArrayList<>();
		ArrayList<Double> dShift = new ArrayList<>();
		ArrayList<K4> tele = new ArrayList<>();
		enhStart = new ArrayList<>();
		enhLink = new ArrayList<>();
		int lastEnhShift = 0;
		for(int i = 0; i < zeilen.length; i++)
		{
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				if(zeilen[i].startsWith("+"))
				{
					String[] y1 = zeilen[i].split(" ");
					if(y1[0].equals("+"))
						enhStart.add(Integer.parseInt(y1[1]));
					else
					{
						enhLink.add(Integer.parseInt(y1[0].substring(1)));
						lastEnhShift = Integer.parseInt(y1[1]);
						enhStart.add(lastEnhShift);
					}
				}
				else
				{
					String[] y1 = zeilen[i].split(" ");
					numm.add(Integer.parseInt(y1[0]) + lastEnhShift);
					if(y1[1].equals("N"))
						linked.add(null);
					else
						linked.add(Integer.parseInt(y1[1]) + lastEnhShift);
					dreh.add(Drehung.nDrehung(Double.parseDouble(y1[2]) * Math.PI / 180,
							Double.parseDouble(y1[3]) * Math.PI / 180));
					len.add(Double.parseDouble(y1[4]));
					if(y1.length > 5)
						spin.add(Double.parseDouble(y1[5]) * Math.PI / 180);
					else
						spin.add(0d);
					if(y1.length > 6)
						dShift.add(Double.parseDouble(y1[6]));
					else
						dShift.add(0d);
					if(y1.length > 7)
						tele.add(new K4(Double.parseDouble(y1[7]), Double.parseDouble(y1[8]),
								Double.parseDouble(y1[9]), Double.parseDouble(y1[10])));
					else
						tele.add(null);
				}
			}
		}
		la = enhStart.get(enhStart.size() - 1);
		linkeds = new Integer[la];
		drehs = new Drehung[la];
		lens = new Double[la];
		spins = new Double[la];
		dShifts = new Double[la];
		teles = new K4[la];
		for(int i = 0; i < numm.size(); i++)
		{
			int p = numm.get(i);
			linkeds[p] = linked.get(i);
			drehs[p] = dreh.get(i);
			lens[p] = len.get(i);
			spins[p] = spin.get(i);
			dShifts[p] = dShift.get(i);
			teles[p] = tele.get(i);
		}
	}

	public void assignStandard(ENB n, External... ext)
	{
		n.elimit = enhStart.get(0);
		n.externals = new External[ext.length];
		for(int i = 0; i < ext.length; i++)
		{
			n.externals[i] = ext[i];
			n.externals[i].axn = enhLink.get(i);
			n.externals[i].anfang = enhStart.get(i);
			n.externals[i].laenge = enhStart.get(i + 1) - enhStart.get(i);
			n.externals[i].main2 = n;
		}
		n.linkAchsen = new LinkAchse[enhStart.get(enhStart.size() - 1)];
		for(int i = 0; i < la; i++)
			if(drehs[i] != null)
			{
				if(linkeds[i] == null)
					n.linkAchsen[i] = new LinkAchse(null, new Drehung(drehs[i]),
							lens[i], spins[i], dShifts[i], teles[i]);
				else
					n.linkAchsen[i] = new LinkAchse(n.linkAchsen[linkeds[i]],
							new Drehung(drehs[i]), lens[i], spins[i], dShifts[i], teles[i]);
			}
		n.standard = this;
	}

	public static XSta gibVonIndexS2(String name)
	{
		if(Index.geladen.containsKey("STA" + name))
			return (XSta) Index.geladen.get("STA" + name);
		XSta s = new XSta(Index.bauName("Ladeteile", name));
		Index.geladen.put("STA" + name, s);
		return s;
	}
}