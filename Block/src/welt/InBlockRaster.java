package welt;

import java.util.*;
import k4.*;

public class InBlockRaster
{
	//   90|-a
	//-c   |    c
	//-----------
	//180  |    0
	//  270|a

	//0123
	public static int drehIntD(double drehung)
	{
		drehung = Drehung.sichern(drehung);
		return (int)(drehung / Math.PI * 2);
	}

	//01230
	public static int drehIntH(double drehung)
	{
		drehung = Drehung.sichern(drehung);
		return (((int)(drehung / Math.PI * 4) + 1) / 2) % 4;
	}

	//01234567
	public static int drehIntH2(double drehung)
	{
		drehung = Drehung.sichern(drehung);
		return (int)(drehung / Math.PI * 4);
	}

	public static boolean drehArt(double drehung)
	{
		drehung = Drehung.sichern(drehung);
		return (((int)(drehung / Math.PI * 8) + 1) / 2) % 2 == 0;
	}

	public static DerBlock gib(WeltB w, K4 position, int dreh,
			double mvv, double mvr, double mvo, double mvg)
	{
		K4 wb = w.weltBlock;
		K4 sw = w.startWelt;
		boolean dg = dreh % 2 == 0;
		double[] mvac = new double[2];
		mvac[dg ? 0 : 1] = mvr * (dreh >= 2 ? -1 : 1);
		mvac[dg ? 1 : 0] = mvv * (dreh == 1 || dreh == 2 ? -1 : 1);
		return w.gib(new WBP(takeZone(position.a, mvac[0], wb.a, sw.a), takeZone(position.c, mvac[1], wb.c, sw.c),
				takeZone(position.b, mvo, wb.b, sw.b), takeZone(position.d, mvg, wb.d, sw.d)));
	}

	public int[][][][] raster;
	public int[] rlens;
	public boolean nochOk = true;

	public InBlockRaster(int[] rlens, int[][][][] raster)
	{
		this.rlens = rlens;
		this.raster = raster;
	}

	public InBlockRaster(InBlockRaster von)
	{
		rlens = new int[4];
		System.arraycopy(von.rlens, 0, rlens, 0, 4);
		raster = new int[rlens[3]][rlens[2]][rlens[1]][rlens[0]];
		for(int id = 0; id < rlens[3]; id++)
			for(int ic = 0; ic < rlens[2]; ic++)
				for(int ib = 0; ib < rlens[1]; ib++)
					System.arraycopy(von.raster[id][ic][ib], 0, raster[id][ic][ib], 0, rlens[0]);
		nochOk = von.nochOk;
	}

	//true = horizontal -> hinten, vorne, links, rechts, unten, oben, -4d, +4d
	//false = diagonal -> lhinten, rvorne, lvorne, rhinten, unten, oben, -4d, +4d
	public InBlockRaster(WeltB w, K4 position, double drehung, boolean typ, double... area)
	{
		K4 wb = w.weltBlock;
		K4 sw = w.startWelt;
		int dreh = typ ? drehIntH(drehung) : drehIntD(drehung);
		boolean dg = dreh % 2 == 0;
		double[] mvac = new double[4];
		mvac[dg ? 0 : 1] = area[2] * (dreh >= 2 ? -1 : 1);
		mvac[dg ? 1 : 0] = area[0] * (dreh == 1 || dreh == 2 ? -1 : 1);
		mvac[dg ? 2 : 3] = area[3] * (dreh >= 2 ? -1 : 1);
		mvac[dg ? 3 : 2] = area[1] * (dreh == 1 || dreh == 2 ? -1 : 1);
		rastern(w, dreh, takeZone(position.c, -mvac[1], wb.c, sw.c), takeZone(position.c, mvac[3], wb.c, sw.c),
				takeZone(position.a, -mvac[0], wb.a, sw.a), takeZone(position.a, mvac[2], wb.a, sw.a),
				takeZone(position.b, -area[4], wb.b, sw.b), takeZone(position.b, area[5], wb.b, sw.b),
				takeZone(position.d, -area[6], wb.d, sw.d), takeZone(position.d, area[7], wb.d, sw.d));
	}

	private static int takeZone(double n1, double n2, double wb, double sw)
	{
		return WeltB.intiize((n1 + n2 - sw) / wb);
	}

	private void rastern(WeltB w, int dreh, int c1Ende, int c2Ende,
			int a1Ende, int a2Ende, int untenEnde, int obenEnde, int m4dEnde, int p4dEnde)
	{
		boolean dg = dreh % 2 == 0;
		boolean cVerk = c1Ende > c2Ende;
		boolean aVerk = a1Ende > a2Ende;
		rlens = new int[4];
		rlens[dg ? 0 : 1] = (c2Ende - c1Ende) * (cVerk ? -1 : 1) + 1;
		rlens[dg ? 1 : 0] = (a2Ende - a1Ende) * (aVerk ? -1 : 1) + 1;
		rlens[2] = obenEnde + 1 - untenEnde;
		rlens[3] = p4dEnde + 1 - m4dEnde;
		raster = new int[rlens[3]][rlens[2]][rlens[1]][rlens[0]];
		for(int id = 0; id < rlens[3]; id++)
			for(int ic = 0; ic < rlens[2]; ic++)
				for(int ib = 0; ib < rlens[dg ? 1 : 0]; ib++)
					for(int ia = 0; ia < rlens[dg ? 0 : 1]; ia++)
					{
						WBP wbp = new WBP(aVerk ? a1Ende - ib : a1Ende + ib, untenEnde + ic,
									cVerk ? c1Ende - ia : c1Ende + ia, m4dEnde + id);
						raster[id][ic][dg ? ib : ia][dg ? ia : ib] =
								(w.gib(wbp).collideOpaque() ? 2 : 1) + (w.gib(wbp).vKanten() ? 4 : 0);
					}
	}

	//horizontal -> hv, lr, uo, 4
	//diagonal -> lh-rv, lv-rh, uo, 4
	public void zusammenfassen(int richtung)
	{
		zusammenfassen(richtung, 0, 0);
	}

	public void zusammenfassen(int richtung, int startA, int endA)
	{
		if(rlens[richtung] <= startA + endA)
		{
			nochOk = false;
			return;
		}
		if(nochOk)
		{
			if(rlens[richtung] > startA + endA + 1)
			{
				switch(richtung)
				{
					case 0:
						for(int id = 0; id < rlens[3]; id++)
							for(int ic = 0; ic < rlens[2]; ic++)
								for(int ib = 0; ib < rlens[1]; ib++)
								{
									int z = 0;
									for(int ia = startA; ia < rlens[0] - endA; ia++)
										z = z | raster[id][ic][ib][ia];
									raster[id][ic][ib][startA] = z;
									for(int i2 = 0; i2 < endA; i2++)
										raster[id][ic][ib][i2 + startA + 1] =
												raster[id][ic][ib][rlens[0] - endA + i2];
								}
						break;
					case 1:
						for(int id = 0; id < rlens[3]; id++)
							for(int ic = 0; ic < rlens[2]; ic++)
							{
								int[] z = new int[rlens[0]];
								for(int ib = startA; ib < rlens[1] - endA; ib++)
									for(int ia = 0; ia < rlens[0]; ia++)
										z[ia] = z[ia] | raster[id][ic][ib][ia];
								raster[id][ic][startA] = z;
								for(int i2 = 0; i2 < endA; i2++)
									raster[id][ic][i2 + startA + 1] = raster[id][ic][rlens[1] - endA + i2];
							}
						break;
					case 2:
						for(int id = 0; id < rlens[3]; id++)
						{
							int[][] z = new int[rlens[1]][rlens[0]];
							for(int ic = startA; ic < rlens[2] - endA; ic++)
								for(int ib = 0; ib < rlens[1]; ib++)
									for(int ia = 0; ia < rlens[0]; ia++)
										z[ib][ia] = z[ib][ia] | raster[id][ic][ib][ia];
							raster[id][startA] = z;
							for(int i2 = 0; i2 < endA; i2++)
								raster[id][i2 + startA + 1] = raster[id][rlens[2] - endA + i2];
						}
						break;
					case 3:
						int[][][] z = new int[rlens[2]][rlens[1]][rlens[0]];
						for(int id = startA; id < rlens[3] - endA; id++)
							for(int ic = 0; ic < rlens[2]; ic++)
								for(int ib = 0; ib < rlens[1]; ib++)
									for(int ia = 0; ia < rlens[0]; ia++)
										z[ic][ib][ia] = z[ic][ib][ia] | raster[id][ic][ib][ia];
						raster[startA] = z;
						for(int i2 = 0; i2 < endA; i2++)
							raster[i2 + startA + 1] = raster[rlens[2] - endA + i2];
						break;
				}
				rlens[richtung] = startA + endA + 1;
			}
		}
	}

	public void checkLen(int richtung, int len)
	{
		if(rlens[richtung] != len)
			nochOk = false;
	}

	public int getLen(int richtung)
	{
		return rlens[richtung];
	}

	public boolean entspricht(int[][][][] ent, boolean[][][][] genau)
	{
		if(!nochOk)
			return false;
		if(ent.length != rlens[3] || genau.length != rlens[3])
			return false;
		if(ent[0].length != rlens[2] || genau[0].length != rlens[2])
			return false;
		if(ent[0][0].length != rlens[1] || genau[0][0].length != rlens[1])
			return false;
		if(ent[0][0][0].length != rlens[0] || genau[0][0][0].length != rlens[0])
			return false;
		for(int id = 0; id < rlens[3]; id++)
			for(int ic = 0; ic < rlens[2]; ic++)
				for(int ib = 0; ib < rlens[1]; ib++)
					for(int ia = 0; ia < rlens[0]; ia++)
						if(genau[id][ic][ib][ia] ? raster[id][ic][ib][ia] != ent[id][ic][ib][ia] :
								(raster[id][ic][ib][ia] | ent[id][ic][ib][ia]) != raster[id][ic][ib][ia])
							return false;
		return true;
	}

	public ArrayList<InBlockRaster> zerteilen(int richtung)
	{
		ArrayList<InBlockRaster> teile = new ArrayList<>();
		if(!nochOk)
		{
			teile.add(this);
			return teile;
		}
		switch(richtung)
		{
			case 0:
				for(int i = 0; i < rlens[0]; i++)
				{
					int[] rlens0 = new int[]{1, rlens[1], rlens[2], rlens[3]};
					int[][][][] raster0 = new int[rlens0[3]][rlens0[2]][rlens0[1]][rlens0[0]];
					for(int id = 0; id < rlens[3]; id++)
						for(int ic = 0; ic < rlens[2]; ic++)
							for(int ib = 0; ib < rlens[1]; ib++)
								raster0[id][ic][ib][0] = raster[id][ic][ib][i];
					teile.add(new InBlockRaster(rlens0, raster0));
				}
				break;
			case 1:
				for(int i = 0; i < rlens[1]; i++)
				{
					int[] rlens0 = new int[]{rlens[0], 1, rlens[2], rlens[3]};
					int[][][][] raster0 = new int[rlens0[3]][rlens0[2]][rlens0[1]][rlens0[0]];
					for(int id = 0; id < rlens[3]; id++)
						for(int ic = 0; ic < rlens[2]; ic++)
							for(int ia = 0; ia < rlens[0]; ia++)
								raster0[id][ic][0][ia] = raster[id][ic][i][ia];
					teile.add(new InBlockRaster(rlens0, raster0));
				}
				break;
			case 2:
				for(int i = 0; i < rlens[2]; i++)
				{
					int[] rlens0 = new int[]{rlens[0], rlens[1], 1, rlens[3]};
					int[][][][] raster0 = new int[rlens0[3]][rlens0[2]][rlens0[1]][rlens0[0]];
					for(int id = 0; id < rlens[3]; id++)
						for(int ib = 0; ib < rlens[1]; ib++)
							for(int ia = 0; ia < rlens[0]; ia++)
								raster0[id][0][ib][ia] = raster[id][i][ib][ia];
					teile.add(new InBlockRaster(rlens0, raster0));
				}
				break;
			case 3:
				for(int i = 0; i < rlens[3]; i++)
				{
					int[] rlens0 = new int[]{rlens[0], rlens[1], rlens[2], 1};
					int[][][][] raster0 = new int[rlens0[3]][rlens0[2]][rlens0[1]][rlens0[0]];
					for(int ic = 0; ic < rlens[2]; ic++)
						for(int ib = 0; ib < rlens[1]; ib++)
							for(int ia = 0; ia < rlens[0]; ia++)
								raster0[0][ic][ib][ia] = raster[i][ic][ib][ia];
					teile.add(new InBlockRaster(rlens0, raster0));
				}
				break;
			default:
				teile.add(this);
		}
		return teile;
	}

	public String toString()
	{
		if(!nochOk)
			return "X";
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for(int id = 0; id < rlens[3]; id++)
		{
			sb.append('[');
			for(int ic = 0; ic < rlens[2]; ic++)
			{
				sb.append('[');
				for(int ib = 0; ib < rlens[1]; ib++)
				{
					sb.append('[');
					for(int ia = 0; ia < rlens[0]; ia++)
					{
						sb.append(raster[id][ic][ib][ia]);
						if(ia < rlens[0] - 1)
							sb.append(", ");
					}
					sb.append(']');
					if(ib < rlens[1] - 1)
						sb.append(", ");
				}
				sb.append(']');
				if(ic < rlens[2] - 1)
					sb.append(", ");
			}
			sb.append(']');
			if(id < rlens[3] - 1)
				sb.append(", ");
		}
		sb.append(']');
		return sb.toString();
	}
}