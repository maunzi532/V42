package ansicht;

import falsch.*;
import k.*;
import modell.xF.*;

import java.awt.*;

public abstract class N2
{
	public K4[] spken;
	public XFarbe farbe;
	protected boolean block;
	public K4 mid;
	public long tn;

	public boolean anzeigen()
	{
		return mid != null && mid.c >= 0 &&
				mid.a * mid.a + mid.b * mid.b + mid.c * mid.c < Staticf.sicht * Staticf.sicht &&
				mid.d > -Staticf.sichtd && mid.d < Staticf.sichtd;
	}

	public abstract boolean panelDaten();
	public abstract void panelDark(Graphics2D darkCopy);
	public abstract void panel(Panelizer pa);

	static int ethaX(double a, double c)
	{
		return (int)(Staticf.sc.width / 2d + Staticf.scaleX * a / c);
	}

	static int ethaY(double b, double c)
	{
		return (int)(Staticf.sc.height / 2d - Staticf.scaleY * b / c);
	}
}