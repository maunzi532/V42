package a3;

import java.awt.*;
import java.util.*;
import k4.*;

public class Linie3 extends Anzeige3
{
	K4 p1K;
	K4 p1C;
	int p1px;
	int p1py;
	K4 p2K;
	K4 p2C;
	int p2px;
	int p2py;
	public PolyFarbe farbe;
	private Paint dFarb;

	public Linie3(TnTarget tn, LichtW lw, PolyFarbe farbe, K4 p1, K4 p2, K4 p1K, K4 p2K)
	{
		super(tn, lw);
		this.p1K = p1K;
		this.p2K = p2K;
		this.farbe = farbe;
		rMid = new K4((p1.a + p2.a) / 2, (p1.b + p2.b) / 2, (p1.c + p2.c) / 2, (p1.d + p2.d) / 2);
		kamMid = new K4((p1K.a + p2K.a) / 2, (p1K.b + p2K.b) / 2, (p1K.c + p2K.c) / 2, (p1K.d + p2K.d) / 2);
	}

	public void splittern(ArrayList<Anzeige3> dieListe)
	{
		dieListe.add(this);
	}

	public void eckenEntf(int wI, int hI, int cI)
	{
		if(!anzeigen)
			return;
		for(int v = 0; v < 4; v++)
		{
			int falsch = 0;
			if(p1K.c < (v % 2 == 0 ? p1K.a * Vor.scaleX * wI / cI :
					p1K.b * Vor.scaleX * hI / cI) * (v / 2 * 2 - 1))
				falsch++;
			if(p2K.c < (v % 2 == 0 ? p2K.a * Vor.scaleX * wI / cI :
					p2K.b * Vor.scaleX * hI / cI) * (v / 2 * 2 - 1))
				falsch++;
			if(falsch >= 2)
			{
				anzeigen = false;
				return;
			}
		}
		boolean falsch1 = p1K.c <= 0;
		boolean falsch2 = p2K.c <= 0;
		if(falsch1 && falsch2)
		{
			anzeigen = false;
			return;
		}
		if(falsch1)
			p1C = new K4(rkc(p2K.a, p1K.a, p2K.c, p1K.c), rkc(p2K.b, p1K.b, p2K.c, p1K.c),
					0, rkc(p2K.d, p1K.d, p2K.c, p1K.c));
		else
			p1C = p1K;
		if(falsch2)
			p2C = new K4(rkc(p1K.a, p2K.a, p1K.c, p2K.c), rkc(p1K.b, p2K.b, p1K.c, p2K.c),
					0, rkc(p1K.d, p2K.d, p1K.c, p2K.c));
		else
			p2C = p2K;
	}

	private double rkc(double k0, double k1, double kc0, double kc1)
	{
		return (k0 * kc1 - k1 * kc0) / (kc1 - kc0);
	}

	@Override
	public void farbeFlaeche(TnTarget tnTarget, int wI, int hI, K4 kam, double xrZone)
	{
		if(anzeigen)
		{
			ddiff = 0;
			double ca1 = p1C.c;
			if(ca1 < nnull)
				ca1 = nnull;
			p1px = ethaX(p1C.a, ca1, wI);
			p1py = ethaY(p1C.b, ca1, wI, hI);
			ddiff += p1C.d;
			double ca2 = p2C.c;
			if(ca2 < nnull)
				ca2 = nnull;
			p2px = ethaX(p2C.a, ca2, wI);
			p2py = ethaY(p2C.b, ca2, wI, hI);
			ddiff += p2C.d;
			ddiff /= 2;
			checkForVanishing(farbe.baseColor);
			dFarb = farbe.gibFarbe(this, tnTarget);
		}
	}

	@Override
	public void panel(Graphics2D gd)
	{
		gd.setPaint(dFarb);
		gd.setStroke(new BasicStroke(1));
		gd.drawLine(p1px, p1py, p2px, p2py);
	}

	@Override
	public void panelDark(Graphics2D gd, TnZuordnung tnz)
	{
		if(tnz != null)
		{
			tnz.actBounds(p1px, p1py);
			tnz.actBounds(p2px, p2py);
		}
		gd.setStroke(new BasicStroke(6));
		gd.drawLine(p1px, p1py, p2px, p2py);
	}
}