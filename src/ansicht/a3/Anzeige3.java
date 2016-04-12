package ansicht.a3;

import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public abstract class Anzeige3
{
	public K4 kamMid;
	public long tn;
	public boolean anzeigen;

	public double ddiff;

	protected Anzeige3(long tn)
	{
		this.tn = tn;
	}

	public abstract boolean errechneKam(K4 kamP, Drehung kamD);

	public void splittern(ArrayList<Anzeige3> rein, boolean gmVision){}

	public void eckenEntf(int wI, int hI, int cI){}

	public abstract void farbeFlaeche(Long tnTarget, int wI, int hI);

	protected int ethaX(double a1, double c1, int wI)
	{
		return (int)(wI / 2d * (1 + Staticf.scaleX * a1 / c1));
	}

	protected int ethaY(double b1, double c1, int wI, int hI)
	{
		return (int)(hI / 2d - wI / 2d * Staticf.scaleX * b1 / c1);
	}

	public abstract void Panel(Graphics2D gd);

	public abstract void PanelDark(Graphics2D gd);
}