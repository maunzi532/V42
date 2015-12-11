package ansicht;

import block.*;
import falsch.*;
import k.*;
import modell.*;
import nbc.*;
import nonBlock.*;
import nonblockbox.aktion.*;

import java.util.*;

public class Zeichner
{
	public ArrayList<N2> n2s;
	public Controllable kam;
	private ArrayList<Double> abstands;
	private ArrayList<Integer> splits;

	public Zeichner(Controllable kam, String ats)
	{
		this.kam = kam;
		abstands = new ArrayList<>();
		splits = new ArrayList<>();
		String[] zeilen = ats.split("\n");
		for(int i = 0; i < zeilen.length; i++)
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				String[] z1 = zeilen[i].split(" ");
				abstands.add(Double.parseDouble(z1[0]));
				splits.add(Integer.parseInt(z1[1]));
			}
	}

	public void nehmen()
	{
		K4 kp = kam.kamP();
		Drehung kd = kam.kamD();
		n2s = new ArrayList<>();
		if(Staticf.siehNonBlocks)
			for(NonBlock nb : WeltND.nonBlocks)
			{
				if(nb.punkte == null)
					continue;
				nb.punkteTransformKam(kp, kd);
				Staticf.sca("NEF " + nb.toString() + " 1 ");
				for(int j = 0; j < nb.aussehen.f2.size(); j++)
				{
					LadeF2 f2 = nb.aussehen.f2.get(j);
					K4[] ecken = new K4[f2.ecken1.size()];
					for(int k = 0; k < f2.ecken1.size(); k++)
					{
						ecken[k] = nb.punkteK[f2.ecken1.get(k)][f2.ecken2.get(k)];
						if(kam == nb && ecken[k].a * ecken[k].a + ecken[k].b * ecken[k].b +
								ecken[k].c * ecken[k].c < Staticf.sichtMin * Staticf.sichtMin)
						{
							ecken = null;
							break;
						}
					}
					K4[] eckenNK = new K4[f2.ecken1.size()];
					for(int k = 0; k < f2.ecken1.size(); k++)
						eckenNK[k] = nb.punkte[f2.ecken1.get(k)][f2.ecken2.get(k)];
					if(ecken != null)
					{
						K4[] spken = new K4[f2.spken1.size()];
						for(int k = 0; k < f2.spken1.size(); k++)
							spken[k] = nb.punkteK[f2.spken1.get(k)][f2.spken2.get(k)];
						n2s.add(new NF2(ecken, eckenNK, spken, f2.et1, f2.farbe, f2.seite, 0, f2.seed, nb.tn));
					}
				}
				Staticf.sca("NEF " + nb.toString() + " 2 ");
				for(int i = 0; i < nb.enhances.length; i++)
					n2s.addAll(nb.enhances[i].gibFl(nb.punkteK));
				for(int i = 0; i < nb.aussehen.ext.size(); i++)
					n2s.addAll(nb.aussehen.ext.get(i).gibFl(nb.punkteK));
				Staticf.sca("NEF " + nb.toString() + " 3 ");
			}
		Staticf.sca("NE1 ");
		if(Staticf.siehBlocks)
			n2s.addAll(WeltB.flaechen(kp, kd, new K4(100, 100, 100, 60)));
		Staticf.sca("NE2 ");
		for(int i = 0; i < n2s.size(); i++)
		{
			if(n2s.get(i) instanceof F2)
				((F2)n2s.get(i)).mid();
			if(!n2s.get(i).anzeigen())
			{
				n2s.remove(i);
				i--;
			}
		}
	}

	private int sqToSplit(double sq)
	{
		for(int i = 0; i < abstands.size(); i++)
			if(sq < abstands.get(i) * abstands.get(i))
				return splits.get(i);
		return 1;
	}

	public void splittern()
	{
		for(int i = 0; i < n2s.size(); i++)
		{
			if(n2s.get(i) instanceof F2)
			{
				if(n2s.get(i).block)
				{
					BF2 tsp = (BF2)n2s.get(i);
					//noinspection ConstantConditions,PointlessBooleanExpression
					if(tsp.spld == 0)
					{
						int splB = sqToSplit(tsp.mid.a * tsp.mid.a + tsp.mid.b * tsp.mid.b +
								tsp.mid.c * tsp.mid.c);
						if(splB > 1)
						{
							n2s.remove(i);
							i--;
							K4[][] sec = new K4[splB + 1][splB + 1];
							for(int j = 0; j <= splB; j++)
								for(int k = 0; k <= splB; k++)
									sec[j][k] = tsp.splInn(j, k, splB, splB);
							K4[][] sec1 = new K4[splB + 1][splB + 1];
							for(int j = 0; j <= splB; j++)
								for(int k = 0; k <= splB; k++)
									sec1[j][k] = tsp.splInn1(j, k, splB, splB);
							for(int j = 0; j < splB; j++)
								for(int k = 0; k < splB; k++)
								{
									F2 tspt = new BF2(new K4[]{sec[j][k], sec[j + 1][k],
											sec[j + 1][k + 1], sec[j][k + 1]},
											new K4[]{sec1[j][k], sec1[j + 1][k],
													sec1[j + 1][k + 1], sec1[j][k + 1]},
											tsp.farbe, tsp.seite, tsp.seitenwand, splB, tsp.tn);
									tspt.mid();
									tspt.splseed = j * splB + k;
									n2s.add(tspt);
								}
						}
					}
				}
				else
				{
					NF2 tsp = (NF2)n2s.get(i);
					//noinspection ConstantConditions,PointlessBooleanExpression
					if(Staticf.splThr > 0 && (tsp.et1 != null && !tsp.et1.contains("mid")) &&
							tsp.maxAbs() > Staticf.splThr)
					{

						n2s.remove(i);
						i--;
						K4[] sec = new K4[tsp.ecken.length];
						for(int j = 0; j < sec.length; j++)
						{
							K4 secA = K4.plus(tsp.ecken[j], tsp.ecken[(j + 1) % sec.length]);
							sec[j] = new K4(secA.a / 2, secA.b / 2, secA.c / 2, secA.d / 2);
						}
						K4[] sec1 = new K4[tsp.eckenNK.length];
						for(int j = 0; j < sec1.length; j++)
						{
							K4 secA = K4.plus(tsp.eckenNK[j], tsp.eckenNK[(j + 1) % sec1.length]);
							sec1[j] = new K4(secA.a / 2, secA.b / 2, secA.c / 2, secA.d / 2);
						}
						for(int j = 0; j < sec.length; j++)
						{
							F2 tspt =
									new NF2(new K4[]{tsp.ecken[j], sec[j], tsp.mid,
											sec[j > 0 ? j - 1 : sec.length - 1]},
											new K4[]{tsp.eckenNK[j], sec1[j], tsp.mid1(),
											sec1[j > 0 ? j - 1 : sec1.length - 1]},
											tsp.spken, tsp.et1, tsp.farbe, tsp.seite, tsp.spld + 1, tsp.seed, tsp.tn);
							tspt.mid();
							tspt.splseed = tsp.splseed * 4 + j + 1;
							n2s.add(tspt);
						}
					}
				}
			}
		}
	}

	public void sortieren()
	{
		Collections.sort(n2s, (n21, n22) -> -Double.compare(n21.mid.c, n22.mid.c));
	}

	public void eckenEntf()
	{
		for(int q = 0; q < n2s.size(); q++)
		{
			if(n2s.get(q) instanceof F2)
			{
				F2 f2 = (F2)n2s.get(q);
				K4[] eck = ((F2)n2s.get(q)).ecken;
				int vornIdx = -1;
				int hintIdx = -1;
				int falsch = 0;
				boolean vvor = eck[eck.length - 1].c > 0;
				for(int i = 0; i < eck.length; i++)
				{
					if(eck[i].c > 0)
					{
						if(!vvor)
							vornIdx = i;
						vvor = true;
					}
					else
					{
						if(vvor)
							hintIdx = i;
						falsch++;
						vvor = false;
					}
				}
				if(falsch >= eck.length)
					continue;
				if(falsch <= 0)
					f2.ec2 = eck;
				else
				{
					int len = (hintIdx - vornIdx + eck.length) % eck.length + 2;
					f2.ec2 = new K4[len];
					for(int i = 0; i < len; i++)
						f2.ec2[i] = new K4();
					for(int i = 0; i < len - 2; i++)
					{
						f2.ec2[i].a = eck[(i + vornIdx + eck.length) % eck.length].a;
						f2.ec2[i].b = eck[(i + vornIdx + eck.length) % eck.length].b;
						f2.ec2[i].c = eck[(i + vornIdx + eck.length) % eck.length].c;
						f2.ec2[i].d = eck[(i + vornIdx + eck.length) % eck.length].d;
					}
					f2.ec2[len - 2].a = rkc(eck[(hintIdx - 1 + eck.length) % eck.length].a,
							eck[hintIdx].a, eck[(hintIdx - 1 + eck.length) % eck.length].c, eck[hintIdx].c);
					f2.ec2[len - 1].a = rkc(eck[vornIdx].a, eck[(vornIdx - 1 + eck.length) % eck.length].a,
							eck[vornIdx].c, eck[(vornIdx - 1 + eck.length) % eck.length].c);
					f2.ec2[len - 2].b = rkc(eck[(hintIdx - 1 + eck.length) % eck.length].b,
							eck[hintIdx].b, eck[(hintIdx - 1 + eck.length) % eck.length].c, eck[hintIdx].c);
					f2.ec2[len - 1].b = rkc(eck[vornIdx].b, eck[(vornIdx - 1 + eck.length) % eck.length].b,
							eck[vornIdx].c, eck[(vornIdx - 1 + eck.length) % eck.length].c);
					f2.ec2[len - 2].d = rkc(eck[(hintIdx - 1 + eck.length) % eck.length].d,
							eck[hintIdx].d, eck[(hintIdx - 1 + eck.length) % eck.length].c, eck[hintIdx].c);
					f2.ec2[len - 1].d = rkc(eck[vornIdx].b, eck[(vornIdx - 1 + eck.length) % eck.length].b,
							eck[vornIdx].c, eck[(vornIdx - 1 + eck.length) % eck.length].c);
					f2.ec2[len - 2].c = 0;
					f2.ec2[len - 1].c = 0;
				}
			}
		}
	}

	private static double rkc(double k0, double k1, double kc0, double kc1)
	{
		return (k0 * kc1 - k1 * kc0) / (kc1 - kc0);
	}
}