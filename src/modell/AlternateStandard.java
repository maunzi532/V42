package modell;

import falsch.*;
import k.*;
import nonblockbox.aktion.*;
import nonblockbox.attk.*;

import java.util.*;

public class AlternateStandard
{
	int la;
	public Drehung[] drehs;
	public Double[] lens;
	public Double[] spins;
	public Double[] dShifts;
	K4[] teles;
	ArrayList<double[]> blockbox;

	AlternateStandard(){}

	public AlternateStandard(String code)
	{
		AlternateStandard main = null;
		String[] zeilen = code.split("\n");
		ArrayList<Integer> numm = new ArrayList<>();
		ArrayList<Drehung> dreh = new ArrayList<>();
		ArrayList<Double> len = new ArrayList<>();
		ArrayList<Double> spin = new ArrayList<>();
		ArrayList<Double> dShift = new ArrayList<>();
		ArrayList<K4> tele = new ArrayList<>();
		blockbox = new ArrayList<>();
		for(int i = 0; i < zeilen.length; i++)
		{
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				if(zeilen[i].startsWith("Main="))
				{
					main = Index.gibStandardAussehen(zeilen[i].substring(5));
				}
				else if(zeilen[i].startsWith("B"))
				{
					String[] y1 = zeilen[i].split(" ");
					double[] da = new double[9];
					for(int j = 0; j < 9; j++)
						da[j] = Double.parseDouble(y1[j + 1]);
					blockbox.add(da);
				}
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

	public void changeToThis(NBD n)
	{
		n.standard = this;
		AltTrans command;
		if(n instanceof NBB)
			command = new AltTrans((NBB)n, 30, 10, blockbox);
		else
			command = new AltTrans(n, 30, 10);
		System.out.println(n.resLink[67]);
		for(int i = 0; i < n.resLink.length; i++)
			if(n.resLink[i] == null)
			{
				if(n.linkAchsen[i] != null)
					ATR.summonATRandCheck(n, i, command);
			}
			else if(n.resLink[i].power < 0)
			{
				n.resLink[i].needCancel = true;
				ATR.summonATRandCheck(n, i, command);
			}
		n.aktionen.add(command);
	}
}