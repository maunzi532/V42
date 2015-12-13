package block;

import ansicht.*;
import falsch.*;
import k.*;
import modell.xF.*;

import java.awt.*;
import java.io.*;
import java.util.*;

public class WeltB
{
	public static int[][][][] blocks;
	public static int[] enden;
	public static int[] endOrder;
	public static K4[] starts;

	public static int gib(WBP p)
	{
		for(int i = 0; i < 4; i++)
		{
			if(p.k[endOrder[i]] < 0)
				return enden[endOrder[i] * 2];
			if(p.k[endOrder[i]] >= Koord.end[endOrder[i]])
				return enden[endOrder[i] * 2 + 1];
		}
		return blocks[p.k[0]][p.k[1]][p.k[2]][p.k[3]];
	}

	private static boolean innen(WBP p)
	{
		for(int i = 0; i < 4; i++)
		{
			if(p.k[endOrder[i]] < 0)
				return false;
			if(p.k[endOrder[i]] >= Koord.end[endOrder[i]])
				return false;
		}
		return true;
	}

	public static boolean set(WBP p, int block)
	{
		if(p.k[0] < 0 || p.k[0] >= Koord.end[0] || p.k[1] < 0 || p.k[1] >= Koord.end[1] ||
				p.k[2] < 0 || p.k[2] >= Koord.end[2] || p.k[3] < 0 || p.k[3] >= Koord.end[3])
			return false;
		blocks[p.k[0]][p.k[1]][p.k[2]][p.k[3]] = block;
		return true;
	}

	public static ArrayList<N2> flaechen(K4 kam, Drehung kDreh, K4 radius)
	{
		ArrayList<N2> toR = new ArrayList<>();
		WBP kaw0 = new WBP(Koord.tw(new K4(kam.a - radius.a, kam.b - radius.b,
				kam.c - radius.c, kam.d - radius.d)));
		WBP kawEnd = new WBP(Koord.tw(new K4(kam.a + radius.a + 1, kam.b + radius.b + 1,
				kam.c + radius.c + 1, kam.d + radius.d + 1)));
		Staticf.sca("WE1 ");
		double dd = (kam.d - Koord.startWelt.d) / Koord.weltBlock.d;
		int di = Koord.intiize((kam.d - Koord.startWelt.d) / Koord.weltBlock.d);
		if(Staticf.x4dization == 0)
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						int block = gib(new WBP(a, b, c, di));
						if(opaque(block))
						{
							for(int i = 0; i < Koord.seiten.length; i++)
								if(!opaque(gib(new WBP(a + Koord.seiten[i][0],
										b + Koord.seiten[i][1], c + Koord.seiten[i][2], di))))
									toR.add(flaeche(new WBP(a, b, c, di), block, i));
						}
						else if(Staticf.d2tangibility)
						{
							WBP p = new WBP(a, b, c, di);
							long tn = tn(p);
							toR.add(new D2(true, new XFN(new Color(100, 100, block > 1 ? 255 : 100)),
									null, Koord.wt2(p), tn));
						}
					}
		else
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						int block = gib(new WBP(a, b, c, di));
						if(opaque(block))
						{
							int blockG = gib(new WBP(a, b, c, di + 1));
							int blockR = gib(new WBP(a, b, c, di - 1));
							for(int i = 0; i < Koord.seiten.length; i++)
								if(!opaque(gib(new WBP(a + Koord.seiten[i][0],
										b + Koord.seiten[i][1], c + Koord.seiten[i][2], di))))
								{
									if(opaque(blockG))
										toR.add(flaechex4d(new WBP(a, b, c, di + 1),
												-1 + dd - di, 1, blockG, i));
									if(opaque(blockR))
										toR.add(flaechex4d(new WBP(a, b, c, di - 1),
												1, di - dd, blockR, i));
									toR.add(flaechex4d(new WBP(a, b, c, di),
											(!opaque(blockR)) ? 1 : dd - di,
											(!opaque(blockG)) ? 1 : 1 + di - dd, block, i));
								}
						}
						else
						{
							WBP ba = new WBP(a, b, c, di);
							int blockA = gib(ba);
							int blockR = gib(new WBP(a, b, c, di - 1));
							int blockG = gib(new WBP(a, b, c, di + 1));
							if(!opaque(blockA))
							{
								long tn = tn(ba);
								D2 de = descr(new WBP(a, b, c, di), blockA, blockR, blockG, tn);
								if(de != null)
									toR.add(de);
							}
						}
					}
		Staticf.sca("WE2 ");
		K4 relativ = TK4F.transformSet2(new K4(kam), kDreh, null);
		for(int i = 0; i < toR.size(); i++)
			if(toR.get(i) instanceof F2)
			{
				F2 f2 = (F2) toR.get(i);
				for(int j = 0; j < f2.ecken.length; j++)
				{
					f2.eckenNK[j] = new K4(f2.ecken[j]);
					f2.ecken[j] = TK4F.transformSet2(f2.ecken[j], kDreh, relativ);
				}
			}
			else
				toR.get(i).mid = TK4F.transformSet2(toR.get(i).mid, kDreh, relativ);
		return toR;
	}

	private static F2 flaeche(WBP p, int block, int nof)
	{
		long tn = tn(p);
		K4[] ke = new K4[4];
		switch(nof)
		{
			case 1:
				p.k[0]++;
			case 0:
				ke[0] = Koord.wt(p);
				p.k[1]++;
				ke[1] = Koord.wt(p);
				p.k[2]++;
				ke[2] = Koord.wt(p);
				p.k[1]--;
				ke[3] = Koord.wt(p);
				break;
			case 3:
				p.k[1]++;
			case 2:
				ke[0] = Koord.wt(p);
				p.k[2]++;
				ke[1] = Koord.wt(p);
				p.k[0]++;
				ke[2] = Koord.wt(p);
				p.k[2]--;
				ke[3] = Koord.wt(p);
				break;
			case 5:
				p.k[2]++;
			case 4:
				ke[0] = Koord.wt(p);
				p.k[0]++;
				ke[1] = Koord.wt(p);
				p.k[1]++;
				ke[2] = Koord.wt(p);
				p.k[0]--;
				ke[3] = Koord.wt(p);
				break;
		}
		return new BF2(ke, new K4[ke.length], Index.gibXFBT("XFBT_" + block + "_" + nof), nof % 2 == 0, nof, 0, tn);
	}

	private static F2 flaechex4d(WBP p, double rstart, double gstart, int block, int nof)
	{
		F2 f2 = flaeche(p, block, nof);
		if(rstart == 1 && gstart == 1)
			return f2;
		int nf = nof % 4;
		f2.spken = f2.ecken;
		f2.spld = -1;
		K4[] ecken;
		if(rstart == 1)
		{
			if(gstart > 0)
				ecken = new K4[]{new K4(f2.spken[nf % 4]), new K4(f2.spken[(1 + nf) % 4]),
						t1e(f2.spken, gstart, 1, nf),
						t1e(f2.spken, gstart, 2, nf), new K4(f2.spken[(3 + nf) % 4])};
			else
				ecken = new K4[]{new K4(f2.spken[nf % 4]), t1e(f2.spken, -gstart, 0, nf),
						t1e(f2.spken, -gstart, 3, nf)};
		}
		else if(gstart == 1)
		{
			if(rstart > 0)
				ecken = new K4[]{new K4(f2.spken[(2 + nf) % 4]), new K4(f2.spken[(3 + nf) % 4]),
						t1e(f2.spken, rstart, 3, nf),
						t1e(f2.spken, rstart, 0, nf), new K4(f2.spken[(1 + nf) % 4])};
			else
				ecken = new K4[]{new K4(f2.spken[(2 + nf) % 4]), t1e(f2.spken, -rstart, 2, nf),
						t1e(f2.spken, -rstart, 1, nf)};
		}
		else
				ecken = new K4[]{new K4(f2.spken[(1 + nf) % 4]), t1e(f2.spken, gstart, 1, nf),
						t1e(f2.spken, gstart, 2, nf),
						new K4(f2.spken[(3 + nf) % 4]), t1e(f2.spken, rstart, 3, nf),
						t1e(f2.spken, rstart, 0, nf)};
		f2.ecken = ecken;
		f2.eckenNK = new K4[f2.ecken.length];
		return f2;
	}

	private static long tn(WBP p)
	{
		if(innen(p))
			return p.k[3] + Koord.end[3] * (p.k[2] + Koord.end[2] * (p.k[1] + Koord.end[1] * p.k[0]));
		return -1;
	}

	private static D2 descr(WBP p, int block, int blockR, int blockG, long tn)
	{
		if(!opaque(blockR) && !opaque(blockG))
		{
			if(Staticf.d2tangibility)
				return new D2(true, new XFN(new Color(100, 100, block > 1 ? 255 : 100)),
						null, Koord.wt2(p), tn);
			return null;
		}
		String text = (opaque(blockR) ? "Rot: " + blockR : "") +
				(opaque(blockR) && opaque(blockG) ? " " : "") +
				(opaque(blockG) ? "Gn: " + blockG : "");
		if(Staticf.d2tangibility || Staticf.x4dization > 1)
			return new D2(Staticf.d2tangibility, new XFN(new Color(100, 100, block > 1 ? 255 : 100)),
					text, Koord.wt2(p), tn);
		else
			return null;
	}

	private static K4 t1e(K4[] spken, double modifier, int kante, int nf)
	{
		K4 k1 = spken[kante > 1 ? (3 + nf) % 4 : (1 + nf) % 4];
		K4 k2 = spken[kante % 3 == 0 ? nf % 4 : (2 + nf) % 4];
		double mod2 = modifier > 1 ? 1 : (modifier < 0 ? 0 : modifier);
		return new K4(k1.a * (1 - mod2) + k2.a * mod2, k1.b * (1 - mod2) + k2.b * mod2,
				k1.c * (1 - mod2) + k2.c * mod2, k1.d * (1 - mod2) + k2.d * mod2);
	}

	public static boolean opaque(int block)
	{
		return block > 0;
	}

	public static boolean vKanten(int block)
	{
		return block == 2;
	}

	public static boolean tk1(WBP p, int richtung)
	{
		if(opaque(gib(p)))
			return false;
		p.k[richtung % 2 == 0 ? 2 : 0] += richtung < 2 ? 1 : -1;
		if(!opaque(gib(p)))
			return false;
		boolean v = vKanten(gib(p));
			p.k[1]++;
		if(!v && opaque(gib(p)))
			return false;
		p.k[richtung % 2 == 0 ? 2 : 0] -= richtung < 2 ? 1 : -1;
		if(opaque(gib(p)))
			return false;
		p.k[1]--;
		return true;
	}

	public static Boolean tk2(WBP p, int richtung)
	{
		if(opaque(gib(p)))
			return null;
		p.k[richtung % 2 == 0 ? 2 : 0] += richtung < 2 ? 1 : -1;
		if(!opaque(gib(p)))
			return null;
		p.k[1]++;
		boolean rf = !opaque(gib(p));
		p.k[richtung % 2 == 0 ? 2 : 0] -= richtung < 2 ? 1 : -1;
		if(opaque(gib(p)))
			return null;
		p.k[1]--;
		return rf;
	}

	public static void speichern(String name, int[] size)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("S = ").append(size[0]).append(",")
				.append(size[1]).append(",")
				.append(size[2]).append(",")
				.append(size[3]).append(",").append("\n");
		sb.append("E = ").append(BlockToText(new WBP(-1, 0, 0, 0))).append(",")
				.append(BlockToText(new WBP(Koord.end[0], 0, 0, 0))).append(",")
				.append(BlockToText(new WBP(0, -1, 0, 0))).append(",")
				.append(BlockToText(new WBP(0, Koord.end[1], 0, 0))).append(",")
				.append(BlockToText(new WBP(0, 0, -1, 0))).append(",")
				.append(BlockToText(new WBP(0, 0, Koord.end[2], 0))).append(",")
				.append(BlockToText(new WBP(0, 0, 0, -1))).append(",")
				.append(BlockToText(new WBP(0, 0, 0, Koord.end[3]))).append(",").append("\n");
		sb.append("O = ").append(endOrder[0]).append(",")
				.append(endOrder[1]).append(",")
				.append(endOrder[2]).append(",")
				.append(endOrder[3]).append(",").append("\n");
		sb.append("B = ");
		int cta = 0;
		for(int i0 = 0; i0 < size[0]; i0++)
			for(int i1 = 0; i1 < size[1]; i1++)
				for(int i2 = 0; i2 < size[2]; i2++)
					for(int i3 = 0; i3 < size[3]; i3++)
					{
						String b = BlockToText(new WBP(i0, i1, i2, i3));
						sb.append(b);
						cta += b.length() + 1;
						if(cta > 120)
						{
							cta = 0;
							sb.append(",\n	");
						}
						else
							sb.append(",");
					}
		try
		{
			FileWriter fw = new FileWriter(new File(name.replace('/', File.separatorChar)));
			fw.write(sb.toString());
			fw.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static String BlockToText(WBP p)
	{
		return String.valueOf(gib(p));
	}
}