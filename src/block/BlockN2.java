package block;

import ansicht.*;
import ansicht.n2.*;
import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class BlockN2
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
					{0, 0, 0, 1}
			};

	private static final int[][] seiten = new int[][]
			{
					{0, 2, 6, 4},
					{5, 7, 3, 1},
					{0, 4, 5, 1},
					{7, 6, 2, 3},
					{1, 3, 2, 0},
					{4, 6, 7, 5}
			};

	private static final int[][] punkte = new int[][]
			{
					{0, 0, 0},
					{1, 0, 0},
					{0, 1, 0},
					{1, 1, 0},
					{0, 0, 1},
					{1, 0, 1},
					{0, 1, 1},
					{1, 1, 1}
			};

	private Zeichner z;
	private WeltB von;
	private LichtW licht;

	public BlockN2(Zeichner z, WeltB von, LichtW licht)
	{
		this.z = z;
		this.von = von;
		this.licht = licht;
	}

	private boolean sichtOpaque(int block)
	{
		return block > 0;
	}

	public ArrayList<N2> flaechen(K4 kam, Drehung kDreh, K4 radius)
	{
		ArrayList<N2> toR = new ArrayList<>();
		WBP kaw0 = new WBP(von.tw(new K4(kam.a - radius.a, kam.b - radius.b,
				kam.c - radius.c, kam.d - radius.d)));
		WBP kawEnd = new WBP(von.tw(new K4(kam.a + radius.a + 1, kam.b + radius.b + 1,
				kam.c + radius.c + 1, kam.d + radius.d + 1)));
		double dd = (kam.d - von.startWelt.d) / von.weltBlock.d;
		int di = von.intiize((kam.d - von.startWelt.d) / von.weltBlock.d);
		double dddi = dd - di;
		K4 relativ = TK4F.transformSet2(new K4(kam), kDreh, null);
		Staticf.sca("WE1 ");
		if(z.x4dization == 0)
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						WBP p = new WBP(a, b, c, di);
						int block = von.gib(p);
						if(sichtOpaque(block))
						{
							for(int i = 0; i < WeltB.seiten.length; i++)
								if(!sichtOpaque(von.gib(new WBP(a + WeltB.seiten[i][0],
										b + WeltB.seiten[i][1], c + WeltB.seiten[i][2], di))))
									BF2.atl(toR, flaeche(new WBP(a, b, c, di), block, i, -1, 1), kDreh, relativ);
						}
						else if(d2Vis(p))
						{
							long tn = von.tn(p);
							D2.atl(toR, new D2(true, null, new XFD(new Color(0, 0, 100)),
									null, von.wt2(p), tn), kDreh, relativ);
						}
					}
		else if(dddi > 0.25 && dddi < 0.75)
		{
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						WBP p = new WBP(a, b, c, di);
						int block = von.gib(p);
						if(sichtOpaque(block))
						{
							int blockR = von.gib(new WBP(a, b, c, di + 1));
							int blockG = von.gib(new WBP(a, b, c, di - 1));
							for(int i = 0; i < WeltB.seiten.length; i++)
								if(!sichtOpaque(von.gib(new WBP(a + WeltB.seiten[i][0],
										b + WeltB.seiten[i][1], c + WeltB.seiten[i][2], di))))
								{
									if(sichtOpaque(blockR))
										BF2.atl(toR, flaeche(new WBP(a, b, c, di + 1), blockR, i, dddi, 1),
												kDreh, relativ);
									if(sichtOpaque(blockG))
										BF2.atl(toR, flaeche(new WBP(a, b, c, di - 1), blockG, i, -1, dddi - 1),
												kDreh, relativ);
									BF2.atl(toR, flaeche(new WBP(a, b, c, di), block, i,
											(!sichtOpaque(blockG)) ? -1 : dddi - 1,
											(!sichtOpaque(blockR)) ? 1 : dddi), kDreh, relativ);
								}
						}
						else
						{
							int blockR = von.gib(new WBP(a, b, c, di + 1));
							int blockG = von.gib(new WBP(a, b, c, di - 1));
							if(!sichtOpaque(von.gib(p)))
							{
								long tn = von.tn(p);
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
						int blockG = von.gib(pG);
						int blockR = von.gib(pR);
						if(sichtOpaque(blockR) || sichtOpaque(blockG))
						{
							for(int i = 0; i < WeltB.seiten.length; i++)
							{
								if(!sichtOpaque(von.gib(new WBP(a + WeltB.seiten[i][0],
										b + WeltB.seiten[i][1], c + WeltB.seiten[i][2], di))) && sichtOpaque(blockG))
									BF2.atl(toR, flaeche(new WBP(pG), blockG, i, -1, dddi), kDreh, relativ);
								if(!sichtOpaque(von.gib(new WBP(a + WeltB.seiten[i][0],
										b + WeltB.seiten[i][1], c + WeltB.seiten[i][2], di + 1))) && von.opaque(blockR))
									BF2.atl(toR, flaeche(new WBP(pR), blockR, i, dddi, 1), kDreh, relativ);
							}
						}
						if(!sichtOpaque(blockR) && !sichtOpaque(blockG))
						{
							int blockR2 = von.gib(new WBP(a, b, c, di + 2));
							int blockG2 = von.gib(new WBP(a, b, c, di - 1));
							D2 der = descr(pR, blockR2, false, von.tn(pR));
							if(der != null)
								D2.atl(toR, der, kDreh, relativ);
							D2 deg = descr(pG, blockG2, true, von.tn(pG));
							if(deg != null)
								D2.atl(toR, deg, kDreh, relativ);
						}
						else if(!sichtOpaque(blockR))
						{
							int blockR2 = von.gib(new WBP(a, b, c, di + 2));
							D2 der = descr(pR, blockR2, false, von.tn(pR));
							if(der != null)
								D2.atl(toR, der, kDreh, relativ);
						}
						else if(!sichtOpaque(blockG))
						{
							int blockG2 = von.gib(new WBP(a, b, c, di - 1));
							D2 deg = descr(pG, blockG2, true, von.tn(pG));
							if(deg != null)
								D2.atl(toR, deg, kDreh, relativ);
						}
					}
		}
		return toR;
	}

	private BF2 flaeche(WBP p, int block, int nof, double rend, double gend)
	{
		long tn = von.tn(p);
		K4[] ke = new K4[4];
		for(int i = 0; i < 4; i++)
		{
			int[] pl = punkte[seiten[nof][i]];
			ke[i] = von.wt(new WBP(p.k[0] + pl[0], p.k[1] + pl[1], p.k[2] + pl[2], p.k[3]));
		}
		return new BF2(ke, new K4[ke.length], Index.gibXFBT(String.valueOf(block), nof, 10),
				true, licht, nof, rend, gend, tn);
	}

	private D2 descr(WBP p, int b, boolean g, long tn)
	{
		boolean quad = d2Vis(p);
		if(!sichtOpaque(b))
		{
			if(quad)
				return new D2(true, g, new XFD(new Color(0, 0, 100)),
						null, von.wt2(p), tn);
			return null;
		}
		String text = null;
		if(sichtOpaque(b))
			text = (g ? "Gn: " : "Rot: ") + b;
		return new D2(quad, g, new XFD(new Color(!g ? 255 : 0, g ? 255 : 0, 0)),
				text, von.wt2(p), tn);
	}

	private boolean d2Vis(WBP p)
	{
		if(!von.innen(p))
			return false;
		if(z.d2tangibility == 1)
		{
			for(int i = 0; i < 8; i++)
			{
				if(sichtOpaque(von.gib(new WBP(p.k[0] + allD[i][0], p.k[1] + allD[i][1],
						p.k[2] + allD[i][2], p.k[3] + allD[i][3]))))
					return true;
			}
			return false;
		}
		return z.d2tangibility > 0;
	}
}