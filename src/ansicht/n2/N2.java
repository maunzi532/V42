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

	public void farbe_flaeche()
	{
		panelDaten();
		dFarb = farbe.gibFarb(this);
	}

	public boolean draw()
	{
		return true;
	}

	protected abstract void panelDaten();
	public abstract void panelDark(Graphics2D darkCopy);
	public abstract void panel(Panelizer pa);

	static int ethaX(double a, double c)
	{
		return (int)(UIVerbunden.sc.width / 2d + Staticf.scaleX * a / c);
	}

	static int ethaY(double b, double c)
	{
		return (int)(UIVerbunden.sc.height / 2d - Staticf.scaleY * b / c);
	}
}