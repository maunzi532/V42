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
	public Paint dFarb;
	public double ddiff;

	public int[] xse;
	public int[] yse;

	public boolean anzeigen()
	{
		return mid != null && mid.c >= 0 &&
				mid.a * mid.a + mid.b * mid.b + mid.c * mid.c < Staticf.sicht * Staticf.sicht &&
				mid.d > -Staticf.sichtd && mid.d < Staticf.sichtd;
	}

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