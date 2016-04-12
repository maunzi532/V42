package ansicht.a3;

import ansicht.*;
import wahr.zugriff.*;

public class PBlock3 extends Polygon3
{
	public K4[] unSpldEckenK;
	public K4[] unSpldEckenR;
	private double rEnd;
	private double gEnd;

	public PBlock3(long tn, Boolean seite, LichtW lw,
			double rEnd, double gEnd, PolyFarbe farbe, K4[] unSpldEckenR, K4[] unSpldEckenK)
	{
		super(tn, seite, lw);
		this.unSpldEckenR = unSpldEckenR;
		this.unSpldEckenK = unSpldEckenK;
		this.rEnd = rEnd;
		this.gEnd = gEnd;
		this.farbe = farbe;
	}

	public PBlock3(PBlock3 main, int xs, int ys, int max)
	{
		super(main.tn, main.seite, main.lw);
		rEnd = main.rEnd;
		gEnd = main.gEnd;
		farbe = main.farbe;
		nachSplitID = xs * max + ys;
		ecken(xs, ys, max);
		//TODO mid berechnen
	}

	public boolean errechneKam(K4 kamP, Drehung kamD)
	{
		return false; //TODO
	}

	public void splittern(boolean gmVision)
	{
		if(!anzeigen)
			return;
		//midsp();
		int spl = 5;//sqToSplit(midsp.a * midsp.a + midsp.b * midsp.b + midsp.c * midsp.c);
		anzeigen = false;
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
			{
				PBlock3 tspt = new PBlock3(this, j, k, spl);
				//TODO hinzu
			}
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