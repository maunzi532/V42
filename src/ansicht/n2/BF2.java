package ansicht.n2;

import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.util.*;

public class BF2 extends F2
{
	public final int seitenwand;
	public double rend;
	public double gend;
	public K4 midsp;

	public static void atl(ArrayList al, BF2 f2, Drehung kDreh, K4 relativ)
	{
		for(int j = 0; j < f2.spken.length; j++)
		{
			f2.eckenNK[j] = new K4(f2.spken[j]);
			f2.spken[j] = TK4F.transformSet2(f2.spken[j], kDreh, relativ);
		}
		if(f2.anz())
		{
			al.add(f2);
		}
	}

	public static void atls(ArrayList al, BF2 f2)
	{
		if(f2.anzeigen())
		{
			al.add(f2);
		}
	}

	public BF2(K4[] spken, K4[] eckenNK, XFarbe farbe, Boolean seite,
			int seitenwand, double rend, double gend, long tn)
	{
		block = true;
		this.spken = spken;
		this.eckenNK = eckenNK;
		this.farbe = farbe;
		this.seite = seite;
		this.seitenwand = seitenwand;
		this.rend = rend;
		this.gend = gend;
		this.tn = tn;
		midsp();
	}

	public BF2(BF2 main, int spld)
	{
		block = true;
		spken = main.spken;
		eckenNK = main.eckenNK;
		farbe = main.farbe;
		seite = main.seite;
		seitenwand = main.seitenwand;
		rend = main.rend;
		gend = main.gend;
		this.spld = spld;
		tn = main.tn;
		midsp = main.midsp;
	}

	public void mid()
	{
		double a = 0;
		double b = 0;
		double c = 0;
		double d = 0;
		for(int i = 0; i < ecken.length; i++)
		{
			a += ecken[i].a;
			b += ecken[i].b;
			c += ecken[i].c;
			d += ecken[i].d;
		}
		mid = new K4(a / ecken.length, b / ecken.length, c / ecken.length, d / ecken.length);
	}

	public void midsp()
	{
		midsp = new K4((spken[0].a + spken[2].a) / 2, (spken[0].b + spken[2].b) / 2,
				(spken[0].c + spken[2].c) / 2, (spken[0].d + spken[2].d) / 2);
	}

	public K4 mid1()
	{
		return new K4((eckenNK[0].a + eckenNK[2].a) / 2, (eckenNK[0].b + eckenNK[2].b) / 2,
				(eckenNK[0].c + eckenNK[2].c) / 2, (eckenNK[0].d + eckenNK[2].d) / 2);
	}

	public K4 splInn(int a1, int a2, int m1, int m2)
	{
		return new K4((spken[0].a * a1 * a2 + spken[1].a * (m1 - a1) * a2 +
				spken[2].a * (m1 - a1) * (m2 - a2) + spken[3].a * a1 * (m2 - a2)) / m1 / m2,
				(spken[0].b * a1 * a2 + spken[1].b * (m1 - a1) * a2 +
						spken[2].b * (m1 - a1) * (m2 - a2) + spken[3].b * a1 * (m2 - a2)) / m1 / m2,
				(spken[0].c * a1 * a2 + spken[1].c * (m1 - a1) * a2 +
						spken[2].c * (m1 - a1) * (m2 - a2) + spken[3].c * a1 * (m2 - a2)) / m1 / m2,
				(spken[0].d * a1 * a2 + spken[1].d * (m1 - a1) * a2 +
						spken[2].d * (m1 - a1) * (m2 - a2) + spken[3].d * a1 * (m2 - a2)) / m1 / m2);
	}

	public K4 splInn1(int a1, int a2, int m1, int m2)
	{
		if(eckenNK == null)
			return null;
		return new K4((eckenNK[0].a * a1 * a2 + eckenNK[1].a * (m1 - a1) * a2 +
				eckenNK[2].a * (m1 - a1) * (m2 - a2) + eckenNK[3].a * a1 * (m2 - a2)) / m1 / m2,
				(eckenNK[0].b * a1 * a2 + eckenNK[1].b * (m1 - a1) * a2 +
						eckenNK[2].b * (m1 - a1) * (m2 - a2) + eckenNK[3].b * a1 * (m2 - a2)) / m1 / m2,
				(eckenNK[0].c * a1 * a2 + eckenNK[1].c * (m1 - a1) * a2 +
						eckenNK[2].c * (m1 - a1) * (m2 - a2) + eckenNK[3].c * a1 * (m2 - a2)) / m1 / m2,
				(eckenNK[0].d * a1 * a2 + eckenNK[1].d * (m1 - a1) * a2 +
						eckenNK[2].d * (m1 - a1) * (m2 - a2) + eckenNK[3].d * a1 * (m2 - a2)) / m1 / m2);
	}

	public void ecken(int splx, int sply, int splmx, int splmy)
	{
		K4[] nk2 = new K4[4];
		nk2[0] = splInn1(splx, sply, splmx, splmy);
		nk2[1] = splInn1(splx + 1, sply, splmx, splmy);
		nk2[2] = splInn1(splx + 1, sply + 1, splmx, splmy);
		nk2[3] = splInn1(splx, sply + 1, splmx, splmy);
		eckenNK = nk2;
		K4[] sple = new K4[4];
		sple[0] = splInn(splx, sply, splmx, splmy);
		sple[1] = splInn(splx + 1, sply, splmx, splmy);
		sple[2] = splInn(splx + 1, sply + 1, splmx, splmy);
		sple[3] = splInn(splx, sply + 1, splmx, splmy);
		double decth = (splx + sply + 1) * 2d / (splmx + splmy) - 1;
		double rdcd = (rend - decth) * (splmx + splmy) / 2;
		double gdcd = (gend - decth) * (splmx + splmy) / 2;
		assert gdcd >= rdcd;
		if((rdcd <= -1 && gdcd <= -1) || (rdcd >= 1 && gdcd >= 1))
		{
			ecken = new K4[0];
			return;
		}
		if(rdcd <= -1 && gdcd >= 1)
		{
			ecken = sple;
			return;
		}
		if(rdcd >= 0)
		{
			if(gdcd >= 1)
			{
				ecken = new K4[3];
				ecken[0] = sple[2];
				ecken[1] = scseite(sple[3], sple[2], rdcd);
				ecken[2] = scseite(sple[1], sple[2], rdcd);
				return;
			}
			ecken = new K4[4];
			ecken[0] = scseite(sple[3], sple[2], rdcd);
			ecken[1] = scseite(sple[1], sple[2], rdcd);
			ecken[2] = scseite(sple[1], sple[2], gdcd);
			ecken[3] = scseite(sple[3], sple[2], gdcd);
			return;
		}
		if(gdcd <= 0)
		{
			if(rdcd <= -1)
			{
				ecken = new K4[3];
				ecken[0] = sple[0];
				ecken[1] = scseite(sple[1], sple[0], gdcd);
				ecken[2] = scseite(sple[3], sple[0], gdcd);
				return;
			}
			ecken = new K4[4];
			ecken[0] = scseite(sple[1], sple[0], gdcd);
			ecken[1] = scseite(sple[3], sple[0], gdcd);
			ecken[2] = scseite(sple[3], sple[0], rdcd);
			ecken[3] = scseite(sple[1], sple[0], rdcd);
			return;
		}
		if(rdcd <= -1)
		{
			ecken = new K4[5];
			ecken[0] = sple[0];
			ecken[1] = sple[1];
			ecken[2] = scseite(sple[1], sple[2], gdcd);
			ecken[3] = scseite(sple[3], sple[2], gdcd);
			ecken[4] = sple[3];
			return;
		}
		if(gdcd >= 1)
		{
			ecken = new K4[5];
			ecken[0] = sple[2];
			ecken[1] = sple[3];
			ecken[2] = scseite(sple[3], sple[0], rdcd);
			ecken[3] = scseite(sple[1], sple[0], rdcd);
			ecken[4] = sple[1];
			return;
		}
		ecken = new K4[6];
		ecken[0] = sple[3];
		ecken[1] = scseite(sple[3], sple[0], rdcd);
		ecken[2] = scseite(sple[1], sple[0], rdcd);
		ecken[3] = sple[1];
		ecken[4] = scseite(sple[1], sple[2], gdcd);
		ecken[5] = scseite(sple[3], sple[2], gdcd);
	}

	public static K4 scseite(K4 sm, K4 se, double faktor)
	{
		if(faktor < 0)
			faktor = -faktor;
		return new K4(sm.a * (1 - faktor) + se.a * faktor, sm.b * (1 - faktor) + se.b * faktor,
				sm.c * (1 - faktor) + se.c * faktor, sm.d * (1 - faktor) + se.d * faktor);
	}

	public boolean anz()
	{
		boolean a = false;
		for(int i = 0; i < spken.length; i++)
		{
			if(spken[i] == null)
				return false;
			if(spken[i].c >= 0 && spken[i].a * spken[i].a + spken[i].b * spken[i].b +
					spken[i].c * spken[i].c < Staticf.sicht * Staticf.sicht &&
					spken[i].d > -Staticf.sichtd && spken[i].d < Staticf.sichtd)
				a = true;
		}
		if(!a)
			return false;
		if(seite == null)
			return true;
		if(spken[0].c <= 0 || spken[1].c <= 0 || spken[2].c <= 0)
			return !UIVerbunden.godMode;
		double a1 = spken[0].a / spken[0].c;
		double a2 = spken[1].a / spken[1].c;
		double a3 = spken[2].a / spken[2].c;
		double b1 = spken[0].b / spken[0].c;
		double b2 = spken[1].b / spken[1].c;
		double b3 = spken[2].b / spken[2].c;
		if(a2 - a1 == 0)
			return a3 != a1 && (a3 > a1) == seite;
		double bv = (b2 - b1) / (a2 - a1);
		double bs = b1 - bv * a1;
		double bx = b3 - bv * a3;
		return bs != bx && (bx > bs) == (seite == (a1 < a2));
	}
}