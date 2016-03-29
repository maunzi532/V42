package ansicht.n2;

import ansicht.*;
import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.awt.*;

public abstract class N2
{
	public K4[] spken; //BF2 und NF2 einzeln
	public XFarbe farbe;
	boolean block; //Weg damit, ersetze durch abstract Methode
	public K4 mid; //KMid und RMid hierher
	public long tn; //Wichtig

	//Werden ausgelesen
	Paint dFarb;
	public double ddiff;

	//Siehe spken
	public int[] xse;
	public int[] yse;

	public void farbe_flaeche(Long tn, int wI, int hI)
	{
		panelDaten(wI, hI);
		dFarb = farbe.gibFarb(this, tn);
	}

	public boolean draw()
	{
		return true;
	}

	protected abstract void panelDaten(int wI, int hI); //Panel size
	public abstract void panelDark(Graphics2D darkCopy);
	public abstract void panel(Panelizer pa);

	static int ethaX(double a, double c, int wI) //Panel size
	{
		return (int)(wI / 2d * (1 + Staticf.scaleX * a / c));
	}

	static int ethaY(double b, double c, int wI, int hI) //Panel size
	{
		return (int)(hI / 2d - wI / 2d * Staticf.scaleX * b / c);
	}
}