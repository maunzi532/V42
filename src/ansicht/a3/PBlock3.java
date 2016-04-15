package ansicht.a3;

import ansicht.*;
import wahr.zugriff.*;

import java.util.*;

public class PBlock3 extends Polygon3
{
	public K4[] unSpldEckenK;
	public K4[] unSpldEckenR;
	private double rEnd;
	private double gEnd;

	public PBlock3(long tn, LichtW lw, Boolean seite,
			double rEnd, double gEnd, PolyFarbe farbe, K4[] unSpldEckenR, K4[] unSpldEckenK)
	{
		super(tn, lw, seite);
		this.unSpldEckenR = unSpldEckenR;
		this.unSpldEckenK = unSpldEckenK;
		this.rEnd = rEnd;
		this.gEnd = gEnd;
		this.farbe = farbe;
	}

	public PBlock3(PBlock3 main, int xs, int ys, int max)
	{
		super(main.tn, main.lw, main.seite);
		unSpldEckenR = main.unSpldEckenR;
		unSpldEckenK = main.unSpldEckenK;
		rEnd = main.rEnd;
		gEnd = main.gEnd;
		farbe = main.farbe;
		nachSplitID = xs * max + ys;
		ecken(xs, ys, max);
		berechneMids();
	}

	private int sqToSplit(double sq, Vor daten)
	{
		for(int i = 0; i < daten.abstands.size(); i++)
			if(sq < daten.abstands.get(i) * daten.abstands.get(i))
				return daten.splits.get(i);
		return 1;
	}

	public void splittern(ArrayList<Anzeige3> dieListe, boolean gmVision, Vor daten)
	{
		if(!anzeigen || eckenR != null)
			return;
		anzeigen = false;
		K4 midsp = new K4((unSpldEckenK[0].a + unSpldEckenK[2].a) / 2,
				(unSpldEckenK[0].b + unSpldEckenK[2].b) / 2,
				(unSpldEckenK[0].c + unSpldEckenK[2].c) / 2,
				(unSpldEckenK[0].d + unSpldEckenK[2].d) / 2);
		int spl = sqToSplit(midsp.a * midsp.a + midsp.b * midsp.b + midsp.c * midsp.c, daten);
		K4[][] neueEckenK = new K4[spl + 1][spl + 1];
		for(int j = 0; j <= spl; j++)
			for(int k = 0; k <= spl; k++)
				neueEckenK[j][k] = splInn(unSpldEckenK, j, k, spl);
		K4[][] neueEckenR = new K4[spl + 1][spl + 1];
		for(int j = 0; j <= spl; j++)
			for(int k = 0; k <= spl; k++)
				neueEckenR[j][k] = splInn(unSpldEckenR, j, k, spl);
		for(int j = 0; j < spl; j++)
			for(int k = 0; k < spl; k++)
				dieListe.add(new PBlock3(this, j, k, spl));
	}

	public K4 splInn(K4[] ecken, int ax, int ay, int size)
	{
		return new K4((ecken[0].a * ax * ay + ecken[1].a * (size - ax) * ay +
				ecken[2].a * (size - ax) * (size - ay) + ecken[3].a * ax * (size - ay)) / size / size,
				(ecken[0].b * ax * ay + ecken[1].b * (size - ax) * ay +
						ecken[2].b * (size - ax) * (size - ay) + ecken[3].b * ax * (size - ay)) / size / size,
				(ecken[0].c * ax * ay + ecken[1].c * (size - ax) * ay +
						ecken[2].c * (size - ax) * (size - ay) + ecken[3].c * ax * (size - ay)) / size / size,
				(ecken[0].d * ax * ay + ecken[1].d * (size - ax) * ay +
						ecken[2].d * (size - ax) * (size - ay) + ecken[3].d * ax * (size - ay)) / size / size);
	}

	public void ecken(int xs, int ys, int max)
	{
		eckenR = new K4[4];
		eckenR[0] = splInn(unSpldEckenR, xs, ys, max);
		eckenR[1] = splInn(unSpldEckenR, xs + 1, ys, max);
		eckenR[2] = splInn(unSpldEckenR, xs + 1, ys + 1, max);
		eckenR[3] = splInn(unSpldEckenR, xs, ys + 1, max);
		K4[] spldEckenK = new K4[4];
		spldEckenK[0] = splInn(unSpldEckenK, xs, ys, max);
		spldEckenK[1] = splInn(unSpldEckenK, xs + 1, ys, max);
		spldEckenK[2] = splInn(unSpldEckenK, xs + 1, ys + 1, max);
		spldEckenK[3] = splInn(unSpldEckenK, xs, ys + 1, max);
		double decth = (xs + ys + 1) / (double) max - 1;
		double rdcd = (rEnd - decth) * max;
		double gdcd = (gEnd - decth) * max;
		assert gdcd >= rdcd;
		if((rdcd <= -1 && gdcd <= -1) || (rdcd >= 1 && gdcd >= 1))
		{
			anzeigen = false;
			return;
		}
		if(rdcd <= -1 && gdcd >= 1)
		{
			eckenK = spldEckenK;
			return;
		}
		if(rdcd >= 0)
		{
			if(gdcd >= 1)
			{
				eckenK = new K4[3];
				eckenK[0] = spldEckenK[2];
				eckenK[1] = scseite(spldEckenK[3], spldEckenK[2], rdcd);
				eckenK[2] = scseite(spldEckenK[1], spldEckenK[2], rdcd);
				return;
			}
			eckenK = new K4[4];
			eckenK[0] = scseite(spldEckenK[3], spldEckenK[2], rdcd);
			eckenK[1] = scseite(spldEckenK[1], spldEckenK[2], rdcd);
			eckenK[2] = scseite(spldEckenK[1], spldEckenK[2], gdcd);
			eckenK[3] = scseite(spldEckenK[3], spldEckenK[2], gdcd);
			return;
		}
		if(gdcd <= 0)
		{
			if(rdcd <= -1)
			{
				eckenK = new K4[3];
				eckenK[0] = spldEckenK[0];
				eckenK[1] = scseite(spldEckenK[1], spldEckenK[0], gdcd);
				eckenK[2] = scseite(spldEckenK[3], spldEckenK[0], gdcd);
				return;
			}
			eckenK = new K4[4];
			eckenK[0] = scseite(spldEckenK[1], spldEckenK[0], gdcd);
			eckenK[1] = scseite(spldEckenK[3], spldEckenK[0], gdcd);
			eckenK[2] = scseite(spldEckenK[3], spldEckenK[0], rdcd);
			eckenK[3] = scseite(spldEckenK[1], spldEckenK[0], rdcd);
			return;
		}
		if(rdcd <= -1)
		{
			eckenK = new K4[5];
			eckenK[0] = spldEckenK[0];
			eckenK[1] = spldEckenK[1];
			eckenK[2] = scseite(spldEckenK[1], spldEckenK[2], gdcd);
			eckenK[3] = scseite(spldEckenK[3], spldEckenK[2], gdcd);
			eckenK[4] = spldEckenK[3];
			return;
		}
		if(gdcd >= 1)
		{
			eckenK = new K4[5];
			eckenK[0] = spldEckenK[2];
			eckenK[1] = spldEckenK[3];
			eckenK[2] = scseite(spldEckenK[3], spldEckenK[0], rdcd);
			eckenK[3] = scseite(spldEckenK[1], spldEckenK[0], rdcd);
			eckenK[4] = spldEckenK[1];
			return;
		}
		eckenK = new K4[6];
		eckenK[0] = spldEckenK[3];
		eckenK[1] = scseite(spldEckenK[3], spldEckenK[0], rdcd);
		eckenK[2] = scseite(spldEckenK[1], spldEckenK[0], rdcd);
		eckenK[3] = spldEckenK[1];
		eckenK[4] = scseite(spldEckenK[1], spldEckenK[2], gdcd);
		eckenK[5] = scseite(spldEckenK[3], spldEckenK[2], gdcd);
	}

	private K4 scseite(K4 sm, K4 se, double faktor)
	{
		if(faktor < 0)
			faktor = -faktor;
		return new K4(sm.a * (1 - faktor) + se.a * faktor, sm.b * (1 - faktor) + se.b * faktor,
				sm.c * (1 - faktor) + se.c * faktor, sm.d * (1 - faktor) + se.d * faktor);
	}
}