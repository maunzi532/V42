package ansicht.n2;

import ansicht.*;
import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.awt.*;

public abstract class N2
{
	public K4[] spken;
	public XFarbe farbe;
	boolean block;
	public K4 mid;
	public long tn;
	Paint dFarb;
	public double ddiff;

	public int[] xse;
	public int[] yse;

	public void farbe_flaeche(Long tn, Dimension sc1)
	{
		panelDaten(sc1);
		dFarb = farbe.gibFarb(this, tn);
	}

	public boolean draw()
	{
		return true;
	}

	protected abstract void panelDaten(Dimension sc1);
	public abstract void panelDark(Graphics2D darkCopy);
	public abstract void panel(Panelizer pa);

	static int ethaX(double a, double c, Dimension sc1)
	{
		return (int)(sc1.width / 2d * (1 + Staticf.scaleX * a / c));
	}

	static int ethaY(double b, double c, Dimension sc1)
	{
		return (int)(sc1.height / 2d - sc1.width / 2d * Staticf.scaleX * b / c);
	}
}