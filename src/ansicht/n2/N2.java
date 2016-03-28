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

	public void farbe_flaeche(Long tn, Dimension sc1)
	{
		panelDaten(sc1);
		dFarb = farbe.gibFarb(this, tn);
	}

	public boolean draw()
	{
		return true;
	}

	protected abstract void panelDaten(Dimension sc1); //Panel size
	public abstract void panelDark(Graphics2D darkCopy);
	public abstract void panel(Panelizer pa);

	static int ethaX(double a, double c, Dimension sc1) //Panel size
	{
		return (int)(sc1.width / 2d * (1 + Staticf.scaleX * a / c));
	}

	static int ethaY(double b, double c, Dimension sc1) //Panel size
	{
		return (int)(sc1.height / 2d - sc1.width / 2d * Staticf.scaleX * b / c);
	}
}