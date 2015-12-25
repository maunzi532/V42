package ansicht.n2;

import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.util.*;

public class NF2 extends F2
{
	public static void atl(ArrayList al, NF2 f2)
	{
		if(f2.anz())
		{
			f2.mid();
			if(f2.mid != null)
				al.add(f2);
		}
	}

	public NF2(K4[] ecken, K4[] eckenNK, K4[] spken, XFarbe farbe,
			Boolean seite, int spld, int seed, long tn)
	{
		block = false;
		this.ecken = ecken;
		this.eckenNK = eckenNK;
		this.spken = spken;
		this.farbe = farbe;
		this.seite = seite;
		this.spld = spld;
		this.seed = seed;
		this.tn = tn;
	}

	public NF2(K4[] ecken, K4[] eckenNK, XFarbe farbe, Boolean seite, int seed, int splseed, long tn)
	{
		block = false;
		this.ecken = ecken;
		this.eckenNK = eckenNK;
		this.farbe = farbe;
		spken = null;
		this.seite = seite;
		spld = 0;
		this.seed = seed;
		this.splseed = splseed;
		this.tn = tn;
	}

	public void mid()
	{
		if(spld > 0)
		{
			K4 nmid = K4.plus(K4.plus(ecken[0], ecken[1]), K4.plus(ecken[2], ecken[3]));
			mid = new K4(nmid.a / 4, nmid.b / 4, nmid.c / 4, nmid.d / 4);
		}
		else
		{
			if(mid == null)
			{
				for(int i = 0; i < ecken.length; i++)
					if(ecken[i] == null)
						return;
				K4 nmid = new K4();
				int durch = 0;
				for(int i = 0; i < ecken.length; i++)
				{
					nmid = K4.plus(nmid, ecken[i]);
					durch++;
				}
				mid = new K4(nmid.a / durch, nmid.b / durch, nmid.c / durch, nmid.d / durch);
			}
		}
		avkh2 = mid.a * mid.a + mid.b * mid.b + mid.c * mid.c;
	}

	public K4 mid1()
	{
		if(spld > 0)
		{
			K4 nmid = K4.plus(K4.plus(eckenNK[0], eckenNK[1]), K4.plus(eckenNK[2], eckenNK[3]));
			return new K4(nmid.a / 4, nmid.b / 4, nmid.c / 4, nmid.d / 4);
		}
		for(int i = 0; i < eckenNK.length; i++)
			if(eckenNK[i] == null)
				return null;
		K4 nmid = new K4();
		int durch = 0;
		for(int i = 0; i < eckenNK.length; i++)
		{
			nmid = K4.plus(nmid, eckenNK[i]);
			durch++;
		}
		return new K4(nmid.a / durch, nmid.b / durch, nmid.c / durch, nmid.d / durch);
	}

	public boolean anz()
	{
		boolean a = false;
		for(int i = 0; i < ecken.length; i++)
		{
			if(ecken[i] == null)
				return false;
			if(ecken[i].c >= 0 && ecken[i].a * ecken[i].a + ecken[i].b * ecken[i].b +
					ecken[i].c * ecken[i].c < Staticf.sicht * Staticf.sicht &&
					ecken[i].d > -Staticf.sichtd && ecken[i].d < Staticf.sichtd)
				a = true;
		}
		if(!a)
			return false;
		if(seite == null)
			return true;
		if(ecken[0].c <= 0 || ecken[1].c <= 0 || ecken[2].c <= 0)
			return !UIVerbunden.godMode;
		double a1 = ecken[0].a / ecken[0].c;
		double a2 = ecken[1].a / ecken[1].c;
		double a3 = ecken[2].a / ecken[2].c;
		double b1 = ecken[0].b / ecken[0].c;
		double b2 = ecken[1].b / ecken[1].c;
		double b3 = ecken[2].b / ecken[2].c;
		if(a2 - a1 == 0)
			return a3 != a1 && (a3 > a1) == seite;
		double bv = (b2 - b1) / (a2 - a1);
		double bs = b1 - bv * a1;
		double bx = b3 - bv * a3;
		return bs != bx && (bx > bs) == (seite == (a1 < a2));
	}
}