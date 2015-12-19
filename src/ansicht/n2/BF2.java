package ansicht.n2;

import ansicht.n2.xF.*;
import wahr.zugriff.*;

public class BF2 extends F2
{
	public final int seitenwand;

	public BF2(K4[] ecken, K4[] eckenNK, XFarbe farbe, Boolean seite, int seitenwand, int spld, long tn)
	{
		block = true;
		this.ecken = ecken;
		this.eckenNK = eckenNK;
		this.farbe = farbe;
		this.seite = seite;
		this.seitenwand = seitenwand;
		this.spld = spld;
		this.tn = tn;
	}

	public void mid()
	{
		mid = new K4((ecken[0].a + ecken[2].a) / 2, (ecken[0].b + ecken[2].b) / 2,
				(ecken[0].c + ecken[2].c) / 2, (ecken[0].d + ecken[2].d) / 2);
	}

	public K4 mid1()
	{
		return new K4((eckenNK[0].a + eckenNK[2].a) / 2, (eckenNK[0].b + eckenNK[2].b) / 2,
				(eckenNK[0].c + eckenNK[2].c) / 2, (eckenNK[0].d + eckenNK[2].d) / 2);
	}

	public K4 splInn(int a1, int a2, int m1, int m2)
	{
		return new K4((ecken[0].a * a1 * a2 + ecken[1].a * (m1 - a1) * a2 +
				ecken[2].a * (m1 - a1) * (m2 - a2) + ecken[3].a * a1 * (m2 - a2)) / m1 / m2,
				(ecken[0].b * a1 * a2 + ecken[1].b * (m1 - a1) * a2 +
						ecken[2].b * (m1 - a1) * (m2 - a2) + ecken[3].b * a1 * (m2 - a2)) / m1 / m2,
				(ecken[0].c * a1 * a2 + ecken[1].c * (m1 - a1) * a2 +
						ecken[2].c * (m1 - a1) * (m2 - a2) + ecken[3].c * a1 * (m2 - a2)) / m1 / m2,
				(ecken[0].d * a1 * a2 + ecken[1].d * (m1 - a1) * a2 +
						ecken[2].d * (m1 - a1) * (m2 - a2) + ecken[3].d * a1 * (m2 - a2)) / m1 / m2);
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
}