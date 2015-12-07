package modell.xF;

import ansicht.*;

import java.awt.*;

public class XFN extends XFarbe
{
	protected Color farb;

	public XFN(Color farb)
	{
		this.farb = farb;
	}

	protected XFN(){}

	public void setFarb(Graphics2D gd, double ddiff, N2 n)
	{
		setFarb(gd, ddiff, n, farb);
	}
}