package ansicht.n2;

import ansicht.*;
import wahr.zugriff.*;

import java.awt.*;

public abstract class F2 extends N2
{
	public K4[] ecken; //Umbenennen in eckenK
	public K4[] eckenNK; //Umbenennen in eckenR
	public K4[] ec2; //Umbenennen in eckenNEntf
	public Boolean seite; //Passt
	public int spld; //Weg damit oder als boolean
	public int seed; //Nicht sicher
	public int splseed; //Nicht sicher
	public LichtW lw; //Hmm

	//Werden ausgelesen
	private int[] xe;
	private int[] ye;
	double avkh2;

	public abstract void mid(); //Weg

	public abstract K4 mid1(); //Weg

	public boolean vAnzeigen(boolean midTime, K4[] eckHier, boolean gmVision) //Nicht sicher
	{
		if(midTime && mid == null)
			return false;
		boolean a = false;
		for(int i = 0; i < eckHier.length; i++)
		{
			if(eckHier[i] == null)
				return false;
			if(eckHier[i].c >= 0 && eckHier[i].a * eckHier[i].a + eckHier[i].b * eckHier[i].b +
					eckHier[i].c * eckHier[i].c < Staticf.sicht * Staticf.sicht &&
					eckHier[i].d > -Staticf.sichtd && eckHier[i].d < Staticf.sichtd)
				a = true;
		}
		if(!a)
			return false;
		if(midTime)
			avkh2 = mid.a * mid.a + mid.b * mid.b + mid.c * mid.c;
		if(seite == null)
			return true;
		for(int i = 0; i < eckHier.length; i++)
			if(eckHier[i].c <= 0)
				return !gmVision;
		double a1 = eckHier[0].a / eckHier[0].c;
		double a2 = eckHier[1].a / eckHier[1].c;
		double a3 = eckHier[2].a / eckHier[2].c;
		double b1 = eckHier[0].b / eckHier[0].c;
		double b2 = eckHier[1].b / eckHier[1].c;
		double b3 = eckHier[2].b / eckHier[2].c;
		if(a2 - a1 == 0)
			return a3 != a1 && (a3 > a1) == seite;
		double bv = (b2 - b1) / (a2 - a1);
		double bs = b1 - bv * a1;
		double bx = b3 - bv * a3;
		return bs != bx && (bx > bs) == (seite == (a1 < a2));
	}

	public double maxAbs() //Nach NF2
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

	public void farbe_flaeche(Long tn, Dimension sc1)
	{
		if(ec2 != null)
		{
			panelDaten(sc1);
			if(!farbe.shownext(this))
				ec2 = null;
			else
				dFarb = farbe.gibFarb(this, tn);
		}
	}

	public boolean draw()
	{
		return ec2 != null;
	}

	protected void panelDaten(Dimension sc1) //spken checken
	{
		xe = new int[ec2.length];
		ye = new int[ec2.length];
		ddiff = 0;
		for(int j = 0; j < ec2.length; j++)
		{
			double ca = ec2[j].c;
			if(ca < Staticf.nnull)
				ca = Staticf.nnull;
			xe[j] = ethaX(ec2[j].a, ca, sc1);
			ye[j] = ethaY(ec2[j].b, ca, sc1);
			ddiff += ec2[j].d;
		}
		ddiff /= ec2.length;
		if(spken != null)
		{
			xse = new int[spken.length];
			yse = new int[spken.length];
			for(int j = 0; j < spken.length; j++)
			{
				double ca = spken[j].c;
				if(ca < Staticf.nnull)
					ca = Staticf.nnull;
				xse[j] = ethaX(spken[j].a, ca, sc1);
				yse[j] = ethaY(spken[j].b, ca, sc1);
			}
		}
	}

	public void panelDark(Graphics2D darkCopy)
	{
		darkCopy.fill(new Polygon(xe, ye, xe.length));
	}

	public void panel(Panelizer pa)
	{
		pa.gd.setPaint(dFarb);
		if(pa.xrmode && avkh2 < Staticf.xraywidth * Staticf.xraywidth)
			pa.gd.draw(new Polygon(xe, ye, xe.length));
		else
			pa.gd.fill(new Polygon(xe, ye, xe.length));
	}
}