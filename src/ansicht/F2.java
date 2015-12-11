package ansicht;

import falsch.*;
import k.*;

import java.awt.*;

public abstract class F2 extends N2
{
	public K4[] ecken;
	public K4[] eckenNK;
	public K4[] ec2;
	public Boolean seite;
	public int spld;
	public int seed;
	public int splseed;

	private int[] xe;
	private int[] ye;
	private double ddiff;
	private double avkh2;

	public abstract void mid();

	public abstract K4 mid1();

	public boolean anzeigen()
	{
		if(mid == null)
			return false;
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
		avkh2 = mid.a * mid.a + mid.b * mid.b + mid.c * mid.c;
		if(seite == null)
			return true;
		if(ecken[0].c <= 0 || ecken[1].c <= 0 || ecken[2].c <= 0)
			return !Staticf.godMode;
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

	public double maxAbs()
	{
		double k = 0;
		for(int i = 0; i < ecken.length; i++)
		{
			double qa = ecken[i].a - mid.a;
			double qb = ecken[i].b - mid.b;
			double k2t = Math.sqrt(qa * qa + qb * qb);
			if(k2t > k)
				k = k2t;
		}
		return k;
	}

	public boolean panelDaten()
	{
		if(ec2 == null)
			return false;
		xe = new int[ec2.length];
		ye = new int[ec2.length];
		ddiff = 0;
		for(int j = 0; j < ec2.length; j++)
		{
			double ca = ec2[j].c;
			if(ca < Staticf.nnull)
				ca = Staticf.nnull;
			xe[j] = ethaX(ec2[j].a, ca);
			ye[j] = ethaY(ec2[j].b, ca);
			ddiff += ec2[j].d;
		}
		ddiff /= ec2.length;
		return true;
	}

	public void panelDark(Graphics2D darkCopy)
	{
		darkCopy.fill(new Polygon(xe, ye, xe.length));
	}

	public void panel(Panelizer pa)
	{
		farbe.setFarb(pa.gd, ddiff, this);
		if(farbe.shownext(ddiff, this))
		{
			if(Staticf.xrmode && avkh2 < Staticf.xraywidth * Staticf.xraywidth)
				pa.gd.draw(new Polygon(xe, ye, xe.length));
			else
				pa.gd.fill(new Polygon(xe, ye, xe.length));
		}
	}
}