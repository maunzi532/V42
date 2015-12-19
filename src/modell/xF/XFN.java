package modell.xF;

import ansicht.*;

import java.awt.*;

public class XFN extends XFarbe
{
	Color farb;

	public XFN(Color farb)
	{
		this.farb = farb;
	}

	XFN(){}

	public void setFarb(N2 n)
	{
		setFarb(n, farb);
	}
}