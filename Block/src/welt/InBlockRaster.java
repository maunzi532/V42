package welt;

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
		//System.out.println((int)(drehung / Math.PI * 8));
		return (((int)(drehung / Math.PI * 8) + 1) / 2) % 2 == 0;
	}

	public int[][][][] raster;
	public int[] rlens;
	public boolean nochOk = true;

	//true = horizontal -> hinten, vorne, links, rechts, unten, oben, -4d, +4d
	//false = diagonal -> lhinten, rvorne, lvorne, rhinten, unten, oben, -4d, +4d
	public InBlockRaster(WeltB w, K4 position, double drehung, boolean typ, double... area)
	{

		K4 wb = w.weltBlock;
		K4 sw = w.startWelt;
		int dreh = typ ? drehIntH(drehung) : drehIntD(drehung);
		int hvmul = (dreh + 1) / 2 == 1 ? -1 : 1;
		int lrmul = dreh / 2 == 1 ? -1 : 1;
		double hvp = dreh % 2 == 0 ? position.c : position.a;
		double lrp = dreh % 2 == 0 ? position.a : position.c;
		double wbhv = dreh % 2 == 0 ? wb.c : wb.a;
		double wblr = dreh % 2 == 0 ? wb.a : wb.c;
		double swhv = dreh % 2 == 0 ? sw.c : sw.a;
		double swlr = dreh % 2 == 0 ? sw.a : sw.c;
		rastern(w, dreh, takeZone(hvp, area[0] * -hvmul, wbhv, swhv), takeZone(hvp, area[1] * hvmul, wbhv, swhv),
				takeZone(lrp, area[2] * -lrmul, wblr, swlr), takeZone(lrp, area[3] * lrmul, wblr, swlr),
				takeZone(position.b, -area[4], wb.b, sw.b), takeZone(position.b, area[5], wb.b, sw.b),
				takeZone(position.d, -area[6], wb.d, sw.d), takeZone(position.d, area[7], wb.d, sw.d));
	}

	private int takeZone(double n1, double n2, double wb, double sw)
	{
		return WeltB.intiize((n1 + n2 - sw) / wb);
	}

	private void rastern(WeltB w, int dreh, int hintenEnde, int vorneEnde,
			int linksEnde, int rechtsEnde, int untenEnde, int obenEnde, int m4dEnde, int p4dEnde)
	{
		boolean hvVerk = hintenEnde > vorneEnde;
		boolean lrVerk = linksEnde > rechtsEnde;
		rlens = new int[4];
		rlens[0] = (vorneEnde - hintenEnde) * (hvVerk ? -1 : 1) + 1;
		rlens[1] = (rechtsEnde - linksEnde) * (lrVerk ? -1 : 1) + 1;
		rlens[2] = obenEnde + 1 - untenEnde;
		rlens[3] = p4dEnde + 1 - m4dEnde;
		raster = new int[rlens[3]][rlens[2]][rlens[1]][rlens[0]];
		for(int id = 0; id < rlens[3]; id++)
			for(int ic = 0; ic < rlens[2]; ic++)
				for(int ib = 0; ib < rlens[1]; ib++)
					for(int ia = 0; ia < rlens[0]; ia++)
					{
						WBP wbp;/* = new WBP(0, untenEnde + ic, 0, m4dEnde + id);
						if(dreh < 2)
							wbp.k[dreh * 2] = rechtsEnde - ib;
						else
							wbp.k[(dreh - 2) * 2] = linksEnde + ib;
						if((dreh + 2) / 2 == 1)
							wbp.k[dreh / 2 * 2] = vorneEnde - ia;
						else
							wbp.k[dreh / 2 * -2 + 2] = hintenEnde + ib;*///Geht nich
						/*switch(dreh)
						{
							case 0:
								wbp = new WBP(rechtsEnde - ib, untenEnde + ic,
										hintenEnde + ia, m4dEnde + id);
								break;
							case 1:
								wbp = new WBP(vorneEnde - ia, untenEnde + ic,
										rechtsEnde - ib, m4dEnde + id);
								break;
							case 2:
								wbp = new WBP(linksEnde + ib, untenEnde + ic,
										vorneEnde - ia, m4dEnde + id);
								break;
							case 3:
								wbp = new WBP(hintenEnde + ia, untenEnde + ic,
										linksEnde + ib, m4dEnde + id);
								break;
							default:
								throw new RuntimeException();
						}*/
						if(dreh % 2 == 0)
							wbp = new WBP(lrVerk ? linksEnde - ib : linksEnde + ib, untenEnde + ic,
									hvVerk ? hintenEnde - ia : hintenEnde + ia, m4dEnde + id);
						else
							wbp = new WBP(hvVerk ? hintenEnde - ia : hintenEnde + ia, untenEnde + ic,
									lrVerk ? linksEnde - ib : linksEnde + ib, m4dEnde + id);
						//System.out.println(Arrays.toString(wbp.k));
						//System.out.println(w.gib(wbp));
						raster[id][ic][ib][ia] = w.gib(wbp).collideOpaque() ? 2 : 1;
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

	public void len(int richtung, int len)
	{
		if(rlens[richtung] != len)
			nochOk = false;
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