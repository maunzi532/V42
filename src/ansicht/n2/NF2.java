package ansicht.n2;

import ansicht.*;
import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.util.*;

public class NF2 extends F2
{
	public static void atl(ArrayList al, NF2 f2, boolean gmVision)
	{
		if(f2.vAnzeigen(false, f2.ecken, gmVision))
		{
			f2.mid();
			if(f2.mid != null)
				al.add(f2);
		}
	}

	public NF2(K4[] ecken, K4[] eckenNK, K4[] spken, XFarbe farbe,
			Boolean seite, LichtW lw, int spld, int seed, long tn)
	{
		block = false;
		this.ecken = ecken;
		this.eckenNK = eckenNK;
		this.spken = spken;
		this.farbe = farbe;
		this.seite = seite;
		this.lw = lw;
		this.spld = spld;
		this.seed = seed;
		this.tn = tn;
	}

	public NF2(K4[] ecken, K4[] eckenNK, XFarbe farbe, Boolean seite, LichtW lw, int seed, int splseed, long tn)
	{
		block = false;
		this.ecken = ecken;
		this.eckenNK = eckenNK;
		this.farbe = farbe;
		spken = null;
		this.seite = seite;
		this.lw = lw;
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
		if(eckenNK == null)
			return null;
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
}