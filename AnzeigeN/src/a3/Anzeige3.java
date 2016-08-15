package a3;

import java.awt.*;
import java.util.*;
import k4.*;

public abstract class Anzeige3
{
	//Fast Null
	public static double nnull = 0.0001;


	public K4 kamMid;
	public TnTarget tn;
	public boolean anzeigen;
	public LichtW lw;
	public K4 rMid;

	public double ddiff;

	public Anzeige3(TnTarget tn, LichtW lw)
	{
		anzeigen = true;
		this.tn = tn;
		this.lw = lw;
	}

	public void splittern(ArrayList<Anzeige3> dieListe){}

	public void eckenEntf(int wI, int hI, int cI){}

	public abstract void farbeFlaeche(TnTarget tnTarget, int wI, int hI, K4 kam, double xrZone);

	public void checkForVanishing(Color fc)
	{
		if(lw == null)
			return;
		double vanishResist = fc.getRed() + fc.getGreen() + fc.getBlue() + fc.getAlpha();
		double vanishCheck = -255;
		for(int i = 0; i < lw.licht.size(); i++)
		{
			K4 lr = K4.diff(lw.licht.get(i).lichtPosition(), rMid);
			double ld = Math.sqrt(lr.a * lr.a + lr.b * lr.b + lr.c * lr.c);
			if(ld > lw.licht.get(i).lichtRange())
				continue;
			double pow = lw.licht.get(i).lichtPower();
			pow -= ld * lw.licht.get(i).lichtPowerDecay();
			if(vanishCheck < pow)
				vanishCheck = pow;
		}
		double weg = Math.sqrt(kamMid.a * kamMid.a + kamMid.b * kamMid.b +
				kamMid.c * kamMid.c + kamMid.d * kamMid.d);
		double nah = (PolyFarbe.redEnd - weg) / PolyFarbe.redEnd; //Wenn nah 1, am Rand 0
		if(nah < 0)
			nah = 0;
		if(vanishResist / 4 + vanishCheck * 2 + nah * 63 < 0)
			anzeigen = false; //Unsichtbar
	}

	public int ethaX(double a1, double c1, int wI)
	{
		return (int)(wI / 2d * (1 + Vor.scaleX * a1 / c1));
	}

	public int ethaY(double b1, double c1, int wI, int hI)
	{
		return (int)(hI / 2d - wI / 2d * Vor.scaleX * b1 / c1);
	}

	public abstract void panel(Graphics2D gd);

	public abstract void panelDark(Graphics2D gd, TnZuordnung tnz);
}