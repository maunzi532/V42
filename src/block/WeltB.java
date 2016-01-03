package block;

import ansicht.n2.*;
import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.awt.*;
import java.io.*;
import java.util.*;

public class WeltB
{
	private static final int[][] allD = new int[][]
			{
					{-1, 0, 0, 0},
					{1, 0, 0, 0},
					{0, -1, 0, 0},
					{0, 1, 0, 0},
					{0, 0, -1, 0},
					{0, 0, 1, 0},
					{0, 0, 0, -1},
					{0, 0, 0, 1},
			};

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
		double dd = (kam.d - Koord.startWelt.d) / Koord.weltBlock.d;
		int di = Koord.intiize((kam.d - Koord.startWelt.d) / Koord.weltBlock.d);
		double dddi = dd - di;
		K4 relativ = TK4F.transformSet2(new K4(kam), kDreh, null);
		Staticf.sca("WE1 ");
		if(UIVerbunden.x4dization == 0)
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						WBP p = new WBP(a, b, c, di);
						int block = gib(p);
						if(opaque(block))
						{
							for(int i = 0; i < Koord.seiten.length; i++)
								if(!opaque(gib(new WBP(a + Koord.seiten[i][0],
										b + Koord.seiten[i][1], c + Koord.seiten[i][2], di))))
									BF2.atl(toR, flaeche(new WBP(a, b, c, di), block, i, -1, 1), kDreh, relativ);
						}
						else if(d2Vis(p))
						{
							long tn = tn(p);
							D2.atl(toR, new D2(true, null, new XFN(new Color(0, 0, 100)),
									null, Koord.wt2(p), tn), kDreh, relativ);
						}
					}
		else if(dddi > 0.25 && dddi < 0.75)
		{
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						WBP p = new WBP(a, b, c, di);
						int block = gib(p);
						if(opaque(block))
						{
							int blockR = gib(new WBP(a, b, c, di + 1));
							int blockG = gib(new WBP(a, b, c, di - 1));
							for(int i = 0; i < Koord.seiten.length; i++)
								if(!opaque(gib(new WBP(a + Koord.seiten[i][0],
										b + Koord.seiten[i][1], c + Koord.seiten[i][2], di))))
								{
									if(opaque(blockR))
										BF2.atl(toR, flaeche(new WBP(a, b, c, di + 1), blockR, i, dddi, 1),
												kDreh, relativ);
									if(opaque(blockG))
										BF2.atl(toR, flaeche(new WBP(a, b, c, di - 1), blockG, i, -1, dddi - 1),
												kDreh, relativ);
									BF2.atl(toR, flaeche(new WBP(a, b, c, di), block, i,
											(!opaque(blockG)) ? -1 : dddi - 1,
											(!opaque(blockR)) ? 1 : dddi), kDreh, relativ);
								}
						}
						else
						{
							int blockR = gib(new WBP(a, b, c, di + 1));
							int blockG = gib(new WBP(a, b, c, di - 1));
							if(!opaque(gib(p)))
							{
								long tn = tn(p);
								D2 der = descr(p, blockR, false, tn);
								if(der != null)
									D2.atl(toR, der, kDreh, relativ);
								D2 deg = descr(p, blockG, true, tn);
								if(deg != null)
									D2.atl(toR, deg, kDreh, relativ);
							}
						}
					}
		}
		else
		{
			if(dddi < 0.5)
				di--;
			else
				dddi--;
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						WBP pG = new WBP(a, b, c, di);
						WBP pR = new WBP(a, b, c, di + 1);
						int blockG = gib(pG);
						int blockR = gib(pR);
						if(opaque(blockR) || opaque(blockG))
						{
							for(int i = 0; i < Koord.seiten.length; i++)
							{
								if(!opaque(gib(new WBP(a + Koord.seiten[i][0],
										b + Koord.seiten[i][1], c + Koord.seiten[i][2], di))) && opaque(blockG))
									BF2.atl(toR, flaeche(new WBP(pG), blockG, i, -1, dddi), kDreh, relativ);
								if(!opaque(gib(new WBP(a + Koord.seiten[i][0],
										b + Koord.seiten[i][1], c + Koord.seiten[i][2], di + 1))) && opaque(blockR))
									BF2.atl(toR, flaeche(new WBP(pR), blockR, i, dddi, 1), kDreh, relativ);
							}
						}
						if(!opaque(blockR) && !opaque(blockG))
						{
							int blockR2 = gib(new WBP(a, b, c, di + 2));
							int blockG2 = gib(new WBP(a, b, c, di - 1));
							D2 der = descr(pR, blockR2, true, tn(pR));
							if(der != null)
								D2.atl(toR, der, kDreh, relativ);
							D2 deg = descr(pG, blockG2, false, tn(pG));
							if(deg != null)
								D2.atl(toR, deg, kDreh, relativ);
						}
						else if(!opaque(blockR))
						{
							int blockR2 = gib(new WBP(a, b, c, di + 2));
							D2 deg = descr(pR, blockR2, true, tn(pR));
							if(deg != null)
								D2.atl(toR, deg, kDreh, relativ);
						}
						else if(!opaque(blockG))
						{
							int blockG2 = gib(new WBP(a, b, c, di - 1));
							D2 der = descr(pG, blockG2, false, tn(pG));
							if(der != null)
								D2.atl(toR, der, kDreh, relativ);
						}
					}
		}
		return toR;
	}

	private static BF2 flaeche(WBP p, int block, int nof, double rend, double gend)
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
		return new BF2(ke, new K4[ke.length], Index.gibXFBT("XFBT_" + block + "_" + nof),
				nof % 2 == 0, nof, rend, gend, tn);
	}

	private static long tn(WBP p)
	{
		if(innen(p))
			return p.k[3] + Koord.end[3] * (p.k[2] + Koord.end[2] * (p.k[1] + Koord.end[1] * p.k[0]));
		return -1;
	}

	private static D2 descr(WBP p, int b, boolean g, long tn)
	{
		boolean quad = d2Vis(p);
		if(!opaque(b))
		{
			if(quad)
				return new D2(true, g, new XFN(new Color(0, 0, 100)),
						null, Koord.wt2(p), tn);
			return null;
		}
		String text = null;
		if(opaque(b))
			text = (g ? "Gn: " : "Rot: ") + b;
		return new D2(quad, g, new XFN(new Color(!g ? 255 : 0, g ? 255 : 0, 0)),
				text, Koord.wt2(p), tn);
	}

	private static boolean d2Vis(WBP p)
	{
		if(UIVerbunden.d2tangibility == 1)
		{
			for(int i = 0; i < 8; i++)
			{
				if(opaque(gib(new WBP(p.k[0] + allD[i][0], p.k[1] + allD[i][1],
						p.k[2] + allD[i][2], p.k[3] + allD[i][3]))))
					return true;
			}
			return false;
		}
		return UIVerbunden.d2tangibility > 0;
	}

	public static boolean opaque(int block)
	{
		return block > 0;
	}

	private static boolean vKanten(int block)
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