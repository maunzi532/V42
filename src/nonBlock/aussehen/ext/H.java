package nonBlock.aussehen.ext;

import ansicht.n2.*;
import ansicht.n2.xF.*;
import nonBlock.aussehen.*;
import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class H extends External
{
	private final double w;
	private final double h;
	protected final int wt;
	private final int ht;
	final ArrayList<LinkAchse>[][] h2;
	final int nlen;
	private final int[][] seeds;

	public H(double w, double h, int wt, int ht, int nlen,
			double wwl, double hwl, double wwb, double hwb)
	{
		this.w = w;
		this.h = h;
		this.wt = wt;
		this.ht = ht;
		this.nlen = nlen;
		//noinspection unchecked
		h2 = new ArrayList[ht][wt];
		Random r = new Random();
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
			{
				h2[i][j] = new ArrayList<>();
				for(int k = 0; k < nlen; k++)
				{
					switch(k)
					{
						case 0:
							h2[i][j].add(new LinkAchse(new Drehung(
									(i - (ht - 1d) / 2) * hwl + (j - (wt - 1d) / 2) * wwl +
											(r.nextDouble() - 0.5) / 10,
									(i - (ht - 1d) / 2) * hwb + (j - (wt - 1d) / 2) * wwb +
											(r.nextDouble() - 0.5) / 10), 0.3 + r.nextDouble() / 10, 0, 0));
							break;
						default:
							h2[i][j].add(new LinkAchse(new Drehung(0, 0),
									1.2 - r.nextDouble() * 0.2 * k, 0, 0));
							break;
					}
				}
			}
		seeds = new int[ht][wt];
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				seeds[i][j] = r.nextInt();
	}

	public void tick()
	{
		for(int i = 0; i < h2.length; i++)
			for(int j = 0; j < h2[i].length; j++)
				for(int k = 0; k < h2[i][j].size(); k++)
					if(k >= 1)
					{
						Achse ac = main2.achsen[anfang + i * wt * nlen + j * nlen + k];
						double a1 = ac.start.a - inmid.a;
						double c1 = ac.start.c - inmid.c;
						double ab = Math.sqrt(a1 * a1 + c1 * c1);
						double de1 = ab > 1 ? 1 / ab : 1;
						double depth = Math.PI * (1.5 + de1 * 0.5);
						if(ac.dreh.wb > depth + 0.05 || ac.dreh.wb < depth - Math.PI - 0.05)
							h2[i][j].get(k).dreh.wb -= 0.05;
						else if(ac.dreh.wb < depth - 0.05 && ac.dreh.wb > depth - Math.PI + 0.05)
							h2[i][j].get(k).dreh.wb += 0.05;
					}
	}

	/*if(k >= 1) //Diese Formel ist falsch, aber gut. Version 2
	{
		double a1 = h2a[i][j][k].start.a - inmid.a;
		double c1 = h2a[i][j][k].start.c - inmid.c;
		double ab = Math.sqrt(a1 * a1 + c1 * c1);
		double de1 = ab > 1 ? 1 / ab : 1;
		double depth = Math.PI * (1 + de1);
		if(h2a[i][j][k].dreh.wb > depth + 0.05 || h2a[i][j][k].dreh.wb < depth - Math.PI - 0.05)
			h2[i][j].get(k).dreh.wb -= 0.05;
		else if(h2a[i][j][k].dreh.wb < depth - 0.05 && h2a[i][j][k].dreh.wb > depth - Math.PI + 0.05)
			h2[i][j].get(k).dreh.wb += 0.05;
	}*/

	public void entLink(Drehung mDreh, K4 mPos)
	{
		inmid = TK4F.zuPunkt(main2.achsen[axn], 0, 0, 1, 0, mDreh, mPos);
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				for(int k = 0; k < h2[i][j].size(); k++)
				{
					int a = anfang + i * wt * nlen + j * nlen + k;
					if(k == 0)
					{
						main2.achsen[a] = h2[i][j].get(k).entlinken(
								TK4F.zuPunkt(main2.achsen[axn], j * w * 2 / wt - w + w / wt,
										i * h * 2 / ht - h + h / ht, 1, 0, mDreh, mPos), main2.achsen[axn]);
						main2.achsen[a].dreh = Drehung.plus(main2.achsen[a].dreh, mDreh);
					}
					else
						main2.achsen[a] = h2[i][j].get(k).entlinken(main2.achsen[a - 1]);
				}
	}

	public void punkte(K4[][] into)
	{
		if(!UIVerbunden.calculateH)
			return;
		int cy = this.anfang;
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				for(int k = 0; k < nlen; k++)
				{
					into[cy] = punktA(main2.achsen[anfang + i * wt * nlen + j * nlen + k], k, nlen);
					cy++;
				}
		into1 = into;
	}

	private K4[] punktA(Achse ac, int k, int max)
	{
		if(k == 0)
		{
			K4[] e = new K4[4];
			e[0] = TK4F.zuPunktXH(ac, w / wt, h / ht);
			e[1] = TK4F.zuPunktXH(ac, -w / wt, h / ht);
			e[2] = TK4F.zuPunktXH(ac, -w / wt, -h / ht);
			e[3] = TK4F.zuPunktXH(ac, w / wt, -h / ht);
			return e;
		}
		else if(k + 2 >= max)
		{
			if(k + 1 >= max)
			{
				K4[] e = new K4[1];
				e[0] = TK4F.zuPunktXH(ac, 0, 0);
				return e;
			}
			K4[] e = new K4[3];
			e[0] = TK4F.zuPunktXH(ac, w / wt / 2, h / ht);
			e[1] = TK4F.zuPunktXH(ac, -w / wt, 0);
			e[2] = TK4F.zuPunktXH(ac, w / wt / 2, -h / ht);
			return e;
		}
		else
		{
			K4[] e = new K4[3];
			e[0] = TK4F.zuPunktXH(ac, w / wt / 2, h / ht);
			e[1] = TK4F.zuPunktXH(ac, -w / wt, 0);
			e[2] = TK4F.zuPunktXH(ac, w / wt / 2, -h / ht);
			return e;
		}
	}

	public ArrayList<F2> gibFl(K4[][] p5)
	{
		ArrayList<F2> al = new ArrayList<>();
		if(!UIVerbunden.calculateH || main2 == UIVerbunden.kamA)
			return al;
		int cy = anfang;
		int grau = 100;
		XFarbe fn = new XFN(new Color(grau, grau, grau));
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				for(int k = 0; k < nlen; k++)
				{
					K4[] p6 = p5[cy];
					K4[] p61 = into1[cy];
					if(k == 0)
					{
						K4[] p7 = p5[cy + 1];
						K4[] p71 = into1[cy + 1];
						NF2.atl(al, new NF2(new K4[]{p6[0], p6[1], p7[1], p7[0]},
								new K4[]{p61[0], p61[1], p71[1], p71[0]}, fn,
								true, seeds[i][j], 0, main2.tn));
						NF2.atl(al, new NF2(new K4[]{p6[1], p6[2], p7[1]},
								new K4[]{p61[1], p61[2], p71[1]}, fn,
								true, seeds[i][j], 1, main2.tn));
						NF2.atl(al, new NF2(new K4[]{p6[2], p6[3], p7[2], p7[1]},
								new K4[]{p61[2], p61[3], p71[2], p71[1]}, fn,
								true, seeds[i][j], 2, main2.tn));
						NF2.atl(al, new NF2(new K4[]{p6[3], p6[0], p7[0], p7[2]},
								new K4[]{p61[3], p61[0], p71[0], p71[2]}, fn,
								true, seeds[i][j], 3, main2.tn));
					}
					else if(k + 1 < nlen)
					{
						if(k + 2 >= nlen)
						{
							K4[] p7 = p5[cy + 1];
							K4[] p71 = into1[cy + 1];
							NF2.atl(al, new NF2(new K4[]{p6[0], p6[1], p7[0]},
									new K4[]{p61[0], p61[1], p71[0]}, fn,
									true, seeds[i][j], k * 3 + 1, main2.tn));
							NF2.atl(al, new NF2(new K4[]{p6[1], p6[2], p7[0]},
									new K4[]{p61[1], p61[2], p71[0]}, fn,
									true, seeds[i][j], k * 3 + 1, main2.tn));
							NF2.atl(al, new NF2(new K4[]{p6[2], p6[0], p7[0]},
									new K4[]{p61[2], p61[0], p71[0]},fn,
									true, seeds[i][j], k * 3 + 1, main2.tn));
						}
						else
						{
							K4[] p7 = p5[cy + 1];
							K4[] p71 = into1[cy + 1];
							NF2.atl(al, new NF2(new K4[]{p6[0], p6[1], p7[1], p7[0]},
									new K4[]{p61[0], p61[1], p71[1], p71[0]}, fn,
									true, seeds[i][j], k * 3 + 1, main2.tn));
							NF2.atl(al, new NF2(new K4[]{p6[1], p6[2], p7[2], p7[1]},
									new K4[]{p61[1], p61[2], p71[2], p71[1]}, fn,
									true, seeds[i][j], k * 3 + 1, main2.tn));
							NF2.atl(al, new NF2(new K4[]{p6[2], p6[0], p7[0], p7[2]},
									new K4[]{p61[2], p61[0], p71[0], p71[2]}, fn,
									true, seeds[i][j], k * 3 + 1, main2.tn));
						}
					}
					cy++;
				}
		return al;
	}
}