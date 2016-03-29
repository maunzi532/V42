package ansicht.n2;

import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.awt.*;

public abstract class N3
{
	public XFarbe farbe;
	public K4 kamMid;
	public long tn;
	public boolean anzeigen;

	Paint dFarb;
	public double ddiff;

	public abstract boolean splittern(boolean gmVision);

	public void eckenEntf(Dimension gdSize){}

	public abstract void farbeFlaeche(Long tnTarget, Dimension gdSize);
}