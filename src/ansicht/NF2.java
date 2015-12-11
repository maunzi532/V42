package ansicht;

import k.*;
import modell.xF.*;

public class NF2 extends F2
{
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

	public NF2(K4[] ecken, K4[] eckenNK, XFarbe farbe, int seed, int splseed, long tn)
	{
		block = false;
		this.ecken = ecken;
		this.eckenNK = eckenNK;
		this.farbe = farbe;
		spken = null;
		seite = null;
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
}