package ansicht.a3;

import ansicht.*;
import ansicht.n2.*;
import block.*;
import wahr.zugriff.*;

import java.util.*;

public class BlockA3
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

	public static final List<Integer> drehseiten = Arrays.asList(4, 1, 5, 0);

	private Vor vor;
	private WeltB von;
	private LichtW lw;

	public BlockA3(Vor vor, WeltB von, LichtW lw)
	{
		this.vor = vor;
		this.von = von;
		this.lw = lw;
	}

	private boolean sichtOpaque(DerBlock block)
	{
		return block.typ > 0;
	}

	public ArrayList<Anzeige3> flaechen(ArrayList<Anzeige3> dieListe, K4 kam, Drehung kDreh, K4 radius, boolean gmVision)
	{
		WBP kaw0 = new WBP(von.tw(new K4(kam.a - radius.a, kam.b - radius.b,
				kam.c - radius.c, kam.d - radius.d)));
		WBP kawEnd = new WBP(von.tw(new K4(kam.a + radius.a + 1, kam.b + radius.b + 1,
				kam.c + radius.c + 1, kam.d + radius.d + 1)));
		double dd = (kam.d - von.startWelt.d) / von.weltBlock.d;
		int di = von.intiize((kam.d - von.startWelt.d) / von.weltBlock.d);
		double dddi = dd - di;
		K4 relativ = TK4F.transformSet2(new K4(kam), kDreh, null);
		Staticf.sca("WE1 ");
		if(vor.visionRange4D == 0)
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						WBP p = new WBP(a, b, c, di);
						DerBlock block = von.gib(p);
						if(sichtOpaque(block))
						{
							for(int i = 0; i < WeltB.seiten.length; i++)
								if(!sichtOpaque(von.gib(new WBP(a + WeltB.seiten[i][0],
										b + WeltB.seiten[i][1], c + WeltB.seiten[i][2], di))))
									dieListe.add(flaeche(new WBP(a, b, c, di), block, i, -1, 1));
						}
						else if(d2Vis(p))
						{
							long tn = von.tn(p);
							dieListe.add(new BlockDInfo3(tn, von.wt2(p), true, null, null));
						}
					}
		else if(dddi > 0.25 && dddi < 0.75)
		{
			for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
				for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
					for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
					{
						WBP p = new WBP(a, b, c, di);
						DerBlock block = von.gib(p);
						if(sichtOpaque(block))
						{
							DerBlock blockR = von.gib(new WBP(a, b, c, di + 1));
							DerBlock blockG = von.gib(new WBP(a, b, c, di - 1));
							for(int i = 0; i < WeltB.seiten.length; i++)
								if(!sichtOpaque(von.gib(new WBP(a + WeltB.seiten[i][0],
										b + WeltB.seiten[i][1], c + WeltB.seiten[i][2], di))))
								{
									if(sichtOpaque(blockR))
										dieListe.add(flaeche(new WBP(a, b, c, di + 1), blockR, i, dddi, 1));
									if(sichtOpaque(blockG))
										dieListe.add(flaeche(new WBP(a, b, c, di - 1), blockG, i, -1, dddi - 1));
									dieListe.add(flaeche(new WBP(a, b, c, di), block, i,
											(!sichtOpaque(blockG)) ? -1 : dddi - 1,
											(!sichtOpaque(blockR)) ? 1 : dddi));
								}
						}
						else
						{
							DerBlock blockR = von.gib(new WBP(a, b, c, di + 1));
							DerBlock blockG = von.gib(new WBP(a, b, c, di - 1));
							if(!sichtOpaque(von.gib(p)))
							{
								long tn = von.tn(p);
								BlockDInfo3 der = descr(p, blockR, false, tn);
								if(der != null)
									dieListe.add(der);
								BlockDInfo3 deg = descr(p, blockG, true, tn);
								if(deg != null)
									dieListe.add(deg);
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
						DerBlock blockG = von.gib(pG);
						DerBlock blockR = von.gib(pR);
						if(sichtOpaque(blockR) || sichtOpaque(blockG))
						{
							for(int i = 0; i < WeltB.seiten.length; i++)
							{
								if(!sichtOpaque(von.gib(new WBP(a + WeltB.seiten[i][0],
										b + WeltB.seiten[i][1], c + WeltB.seiten[i][2], di))) && sichtOpaque(blockG))
									dieListe.add(flaeche(new WBP(pG), blockG, i, -1, dddi));
								if(!sichtOpaque(von.gib(new WBP(a + WeltB.seiten[i][0],
										b + WeltB.seiten[i][1], c + WeltB.seiten[i][2], di + 1))) && von.opaque(blockR))
									dieListe.add(flaeche(new WBP(pR), blockR, i, dddi, 1));
							}
						}
						if(!sichtOpaque(blockR) && !sichtOpaque(blockG))
						{
							DerBlock blockR2 = von.gib(new WBP(a, b, c, di + 2));
							DerBlock blockG2 = von.gib(new WBP(a, b, c, di - 1));
							BlockDInfo3 der = descr(pR, blockR2, false, von.tn(pR));
							if(der != null)
								dieListe.add(der);
							BlockDInfo3 deg = descr(pG, blockG2, true, von.tn(pG));
							if(deg != null)
								dieListe.add(deg);
						}
						else if(!sichtOpaque(blockR))
						{
							DerBlock blockR2 = von.gib(new WBP(a, b, c, di + 2));
							BlockDInfo3 der = descr(pR, blockR2, false, von.tn(pR));
							if(der != null)
								dieListe.add(der);
						}
						else if(!sichtOpaque(blockG))
						{
							DerBlock blockG2 = von.gib(new WBP(a, b, c, di - 1));
							BlockDInfo3 deg = descr(pG, blockG2, true, von.tn(pG));
							if(deg != null)
								dieListe.add(deg);
						}
					}
		}
		return dieListe;
	}

	private PBlock3 flaeche(WBP p, DerBlock block, int nof, double rend, double gend)
	{
		long tn = von.tn(p);
		K4[] ke = new K4[4];
		for(int i = 0; i < 4; i++)
		{
			int[] pl = punkte[seiten[nof][i]];
			ke[i] = von.wt(new WBP(p.k[0] + pl[0], p.k[1] + pl[1], p.k[2] + pl[2], p.k[3]));
		}
		if(drehseiten.contains(nof))
		{
			int ind = drehseiten.indexOf(nof);
			ind = (ind + block.dreh4) % 4;
			nof = drehseiten.get(ind);
		}
		else
		{
			K4[] ke2 = new K4[4];
			for(int i  = 0; i < 4; i++)
				ke2[(i + block.dreh4) % 4] = ke[i];
			ke = ke2;
		}
		return new PBlock3(tn, true, lw, rend, gend,
				null/*XFBT2 mit nof*/, ke, /*transformed ke*/ke);
	}

	private BlockDInfo3 descr(WBP p, DerBlock b, boolean g, long tn)
	{
		boolean quad = true;//TODO d2Vis(p);
		if(!sichtOpaque(b))
		{
			if(quad)
				return new BlockDInfo3(/*true, g, new Color(0, 0, 100),
						null, von.wt2(p), tn*/tn, von.wt2(p), true, null, g);
			return null;
		}
		String text = null;
		if(sichtOpaque(b))
			text = (g ? "Gn: " : "Rot: ") + b; //TODO argh
		return new BlockDInfo3(/*quad, g, new Color(!g ? 255 : 0, g ? 255 : 0, 0),
				text, von.wt2(p), tn*/tn, von.wt2(p), true, text, g);
	}

	private boolean d2Vis(WBP p)
	{
		/*if(!von.innen(p))
			return false;
		if(vor.d2tangibility == 1)
		{
			for(int i = 0; i < 8; i++)
			{
				if(sichtOpaque(von.gib(new WBP(p.k[0] + allD[i][0], p.k[1] + allD[i][1],
						p.k[2] + allD[i][2], p.k[3] + allD[i][3]))))
					return true;
			}
			return false;
		}
		return vor.d2tangibility > 0;*/
		return true; //TODO
	}
}