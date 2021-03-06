package ext;

import a3.*;
import achsen.*;
import java.util.*;
import k4.*;

public class H extends External
{
	public static boolean calculateH = true;
	private final double w;
	private final double h;
	private final int wt;
	private final int ht;
	public final LinkAchse[][][] h2;
	public final Achse[][][] h2a;
	private final K4[][] punkte;
	private final K4[][] punkteK;
	public final int nlen;
	private final int[][] seeds;
	private PolyFarbe polyFarbe;

	public H(double w, double h, int wt, int ht, int nlen,
			double wwl, double hwl, double len)
	{
		this.w = w;
		this.h = h;
		this.wt = wt;
		this.ht = ht;
		this.nlen = nlen;
		h2 = new LinkAchse[ht][wt][nlen];
		h2a = new Achse[ht][wt][nlen];
		punkte = new K4[ht * wt * nlen][];
		punkteK = new K4[ht * wt * nlen][];
		Random r = new Random();
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
			{
				for(int k = 0; k < nlen; k++)
				{
					switch(k)
					{
						case 0:
							h2[i][j][k] = new LinkAchse(new Drehung(
									(i - (ht - 1d) / 2) * hwl + (j - (wt - 1d) / 2) * wwl +
											(r.nextDouble() - 0.5), (r.nextDouble() - 0.5) / 5),
									len * (r.nextDouble() / 5 + 0.25), 0, 0);
							break;
						default:
							h2[i][j][k] = new LinkAchse(new Drehung(
									(r.nextDouble() - 0.5) / 10, (r.nextDouble() - 0.5) / 10),
									len * (r.nextDouble() + 0.5), 0, 0);
							break;
					}
				}
			}
		seeds = new int[ht][wt];
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				seeds[i][j] = r.nextInt();
		polyFarbe = new PolyFarbe("150,H");
	}

	public void tick()
	{
		for(int i = 0; i < h2.length; i++)
			for(int j = 0; j < h2[i].length; j++)
				for(int k = 0; k < h2[i][j].length; k++)
					if(k >= 1)
					{
						Achse ac = h2a[i][j][k];
						double a1 = ac.start.a - inmid.a;
						double c1 = ac.start.c - inmid.c;
						double ab = Math.sqrt(a1 * a1 + c1 * c1);
						double de1 = ab > 1 ? 1 / ab : 1;
						double depth = Math.PI * (1.5 + de1 * 0.5);
						if(ac.dreh.wb > depth + 0.05 || ac.dreh.wb < depth - Math.PI - 0.05)
							h2[i][j][k].dreh.wb -= 0.05;
						else if(ac.dreh.wb < depth - 0.05 && ac.dreh.wb > depth - Math.PI + 0.05)
							h2[i][j][k].dreh.wb += 0.05;
					}
	}

	public void entLink(Drehung mDreh, K4 mPos)
	{
		inmid = NonBlock.zuPunkt(main2.achsen[axn], 0, 0, 1, 0, mDreh, mPos);
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				for(int k = 0; k < h2[i][j].length; k++)
				{
					if(k == 0)
						h2a[i][j][k] = h2[i][j][k].entlinken(
								NonBlock.zuPunkt(main2.achsen[axn], j * w * 2 / wt - w + w / wt,
										i * h * 2 / ht - h + h / ht, 1, 0, mDreh, mPos),
								main2.achsen[axn], mDreh);
					else
						h2a[i][j][k] = h2[i][j][k].entlinken(h2a[i][j][k - 1]);
				}
	}

	public void punkte(K4[][] into)
	{
		if(!calculateH)
			return;
		int cy = 0;
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				for(int k = 0; k < nlen; k++)
				{
					punkte[cy] = punktA(h2a[i][j][k], k, nlen);
					cy++;
				}
	}

	public void transformK(K4 kam, Drehung kDreh)
	{
		K4 relativ = NonBlock.transformSet2(new K4(kam), kDreh, null);
		for(int i = 0; i < punkteK.length; i++)
			if(punkte[i] != null)
			{
				punkteK[i] = new K4[punkte[i].length];
				for(int j = 0; j < punkte[i].length; j++)
					punkteK[i][j] = new K4(punkte[i][j]);
				for(int j = 0; j < punkteK[i].length; j++)
					if(punkteK[i][j] != null)
						punkteK[i][j] = NonBlock.transformSet2(punkteK[i][j], kDreh, relativ);
			}
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

	public void gibPl(ArrayList<Anzeige3> dieListe, K4[][] into,
			LichtW lw, boolean isMasterVision)
	{
		if(!calculateH || isMasterVision)
			return;
		int cy = 0;
		int splSeed = 0;
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				for(int k = 0; k < nlen; k++)
				{
					K4[] p6 = punkte[cy];
					K4[] p61 = punkteK[cy];
					if(k == 0)
					{
						K4[] p7 = punkte[cy + 1];
						K4[] p71 = punkteK[cy + 1];
						dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
								new K4[]{p6[0], p6[1], p7[1], p7[0]},
								new K4[]{p61[0], p61[1], p71[1], p71[0]}, splSeed));
						splSeed++;
						dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
								new K4[]{p6[1], p6[2], p7[1]},
								new K4[]{p61[1], p61[2], p71[1]}, splSeed));
						splSeed++;
						dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
								new K4[]{p6[2], p6[3], p7[2], p7[1]},
								new K4[]{p61[2], p61[3], p71[2], p71[1]}, splSeed));
						splSeed++;
						dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
								new K4[]{p6[3], p6[0], p7[0], p7[2]},
								new K4[]{p61[3], p61[0], p71[0], p71[2]}, splSeed));
						splSeed++;
					}
					else if(k + 1 < nlen)
					{
						if(k + 2 >= nlen)
						{
							K4[] p7 = punkte[cy + 1];
							K4[] p71 = punkteK[cy + 1];
							dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
									new K4[]{p6[0], p6[1], p7[0]},
									new K4[]{p61[0], p61[1], p71[0]}, splSeed));
							splSeed++;
							dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
									new K4[]{p6[1], p6[2], p7[0]},
									new K4[]{p61[1], p61[2], p71[0]}, splSeed));
							splSeed++;
							dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
									new K4[]{p6[2], p6[0], p7[0]},
									new K4[]{p61[2], p61[0], p71[0]}, splSeed));
							splSeed++;
						}
						else
						{
							K4[] p7 = punkte[cy + 1];
							K4[] p71 = punkteK[cy + 1];
							dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
									new K4[]{p6[0], p6[1], p7[1], p7[0]},
									new K4[]{p61[0], p61[1], p71[1], p71[0]}, splSeed));
							splSeed++;
							dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
									new K4[]{p6[1], p6[2], p7[2], p7[1]},
									new K4[]{p61[1], p61[2], p71[2], p71[1]}, splSeed));
							splSeed++;
							dieListe.add(new PNonBlock3(main2.tn, lw, true, polyFarbe, seeds[i][j],
									new K4[]{p6[2], p6[0], p7[0], p7[2]},
									new K4[]{p61[2], p61[0], p71[0], p71[2]}, splSeed));
							splSeed++;
						}
					}
					cy++;
				}
	}
}